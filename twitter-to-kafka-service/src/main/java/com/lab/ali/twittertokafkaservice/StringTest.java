package com.lab.ali.twittertokafkaservice;

import java.util.HashSet;
import java.util.Set;

public class StringTest {
	
	
	public static void main(String[] args) {
		
		String str = "I am Vijay and i am cloud developer";
		
		String[] words = str.split(" ");
		
		Set<String> unique = new HashSet<>();
		
		//O(N)
		for(String word : words) {
			
			//O(1)
			if(unique.contains(word.toLowerCase())) {
				System.out.println(word);
			} else {
				unique.add(word.toLowerCase());
			}
		}
	}

}
