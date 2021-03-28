

import java.util.Map;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.admin.AdminClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

import com.lab.ali.appconfigdata.KafkaConfigData;

@EnableRetry
@Configuration
public class KafkaConfiguration {

	KafkaConfigData kafkaConfigData;
	
	public KafkaConfiguration(KafkaConfigData kafkaConfigData) {
		this.kafkaConfigData = kafkaConfigData;
	}
	
	@Bean
	public AdminClient getAdminClient() {
		
		return AdminClient.create(Map.of(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG,
				kafkaConfigData.getBootstrapServers()));
	}
}
