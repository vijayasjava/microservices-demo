package com.lab.ali.twittertokafkaservice.runner;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.lab.ali.appconfigdata.TwitterToKafkaConfiguration;
import com.lab.ali.twittertokafkaservice.exception.TwitterToKafkaServiceException;
import com.lab.ali.twittertokafkaservice.listener.TwitterStreamListener;

import lombok.AllArgsConstructor;
import lombok.Data;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterObjectFactory;

@Component
@ConditionalOnProperty(name = "enable-mock-tweets",
		prefix = "twitter-to-kafka-service", havingValue = "true", matchIfMissing = true)
@Data
public class MockKafkaStreamRunner implements StreamRunnable {

	public MockKafkaStreamRunner(TwitterToKafkaConfiguration twitterToKafkaConfiguration,
	TwitterStreamListener twitterStreamListener) {
		this.twitterToKafkaConfiguration = twitterToKafkaConfiguration;
		this.twitterStreamListener = twitterStreamListener;
	}
	
	TwitterToKafkaConfiguration twitterToKafkaConfiguration;
	TwitterStreamListener twitterStreamListener;
	
    private static final Logger LOG = LoggerFactory.
    		getLogger(MockKafkaStreamRunner.class);

    private static final Random RANDOM = new Random();

    private static final String[] WORDS = new String[]{
            "Lorem",
            "ipsum",
            "dolor",
            "sit",
            "amet",
            "consectetuer",
            "adipiscing",
            "elit",
            "Maecenas",
            "porttitor",
            "congue",
            "massa",
            "Fusce",
            "posuere",
            "magna",
            "sed",
            "pulvinar",
            "ultricies",
            "purus",
            "lectus",
            "malesuada",
            "libero"
    };

	
    private static final String tweetAsRawJson = "{" +
            "\"created_at\":\"{0}\"," +
            "\"id\":\"{1}\"," +
            "\"text\":\"{2}\"," +
            "\"user\":{\"id\":\"{3}\"}" +
            "}";

    private static final String TWITTER_STATUS_DATE_FORMAT = 
    		"EEE MMM dd HH:mm:ss zzz yyyy";

    
	@Override
	public void run() {
		
        final String[] keywords = this.getTwitterToKafkaConfiguration().
        		getFilterkeys().toArray(new String[0]);
        final int minTweetLength = this.getTwitterToKafkaConfiguration().
        		getMockMinTweetLength();
        final int maxTweetLength = this.getTwitterToKafkaConfiguration().
        		getMockMaxTweetLength();
        long sleepTimeMs = this.getTwitterToKafkaConfiguration().getMockSleepMs();
        LOG.info("Starting mock filtering twitter streams for keywords {}",
        		Arrays.toString(keywords));
        simulateTwitterStream(keywords, minTweetLength, maxTweetLength, sleepTimeMs);
		
	}

    private void simulateTwitterStream(String[] keywords, int minTweetLength, 
    		int maxTweetLength, long sleepTimeMs) {
        Executors.newSingleThreadExecutor().submit(() -> {
            try {
                while (true) {
                    String formattedTweetAsRawJson = 
                    		getFormattedTweet(keywords, 
                    				minTweetLength, maxTweetLength);
                    Status status = TwitterObjectFactory.
                    		createStatus(formattedTweetAsRawJson);
                    this.getTwitterStreamListener().onStatus(status);
                    sleep(sleepTimeMs);
                }
            } catch (TwitterException e) {
                LOG.error("Error creating twitter status!", e);
            }
        });
    }	
    private void sleep(long sleepTimeMs) {
        try {
            Thread.sleep(sleepTimeMs);
        } catch (InterruptedException e) {
            throw new TwitterToKafkaServiceException(
            		"Error while sleeping for waiting new status to create!!");
        }
    }

    private String getFormattedTweet(String[] keywords, int minTweetLength, int maxTweetLength) {
        String[] params = new String[]{
                ZonedDateTime.now().
                format(DateTimeFormatter.ofPattern(TWITTER_STATUS_DATE_FORMAT,
                			Locale.ENGLISH)),
                String.valueOf(ThreadLocalRandom.current().nextLong(Long.MAX_VALUE)),
                getRandomTweetContent(keywords, minTweetLength, maxTweetLength),
                String.valueOf(ThreadLocalRandom.current().nextLong(Long.MAX_VALUE))
        };
        return formatTweetAsJsonWithParams(params);
    }

    private String formatTweetAsJsonWithParams(String[] params) {
        String tweet = tweetAsRawJson;

        for (int i = 0; i < params.length; i++) {
            tweet = tweet.replace("{" + i + "}", params[i]);
        }
        return tweet;
    }

    private String getRandomTweetContent(String[] keywords, int minTweetLength, int maxTweetLength) {
        StringBuilder tweet = new StringBuilder();
        int tweetLength = RANDOM.nextInt(maxTweetLength - minTweetLength + 1) + minTweetLength;
        return constructRandomTweet(keywords, tweet, tweetLength);
    }

    private String constructRandomTweet(String[] keywords, StringBuilder tweet, int tweetLength) {
        for (int i = 0; i < tweetLength; i++) {
            tweet.append(WORDS[RANDOM.nextInt(WORDS.length)]).append(" ");
            if (i == tweetLength / 2) {
                tweet.append(keywords[RANDOM.nextInt(keywords.length)]).append(" ");
            }
        }
        return tweet.toString().trim();
    }

}
