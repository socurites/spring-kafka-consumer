package com.socurites.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;

@SpringBootApplication
public class SpringKafkaConsumerApplication {
	private static final Logger log = LoggerFactory.getLogger(SpringKafkaConsumerApplication.class);

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(SpringKafkaConsumerApplication.class);
		application.run(args);
	}
	
	@KafkaListener(topics = "test", groupId = "test-group-00")
	public void recordListener(ConsumerRecord<String, String> record) {
		log.info(record.toString());
	}
	
	@KafkaListener(topics = "test", groupId = "test-group-01")
	public void singleTopicListener(String messageValue) {
		log.info(messageValue);
	}
	
	@KafkaListener(topics = "test", groupId = "test-group-02",
			properties = {
					"max.poll.interval.ms:60000",
					"auto.offset.reset:earliest"
			})
	public void singleTopicWithPropertiesListener(String messageValue) {
		log.info(messageValue);
	}
	
	@KafkaListener(topics = "test", groupId = "test-group-03",
			concurrency = "3")
	public void concurrentTopicListener(String messageValue) {
		log.info(messageValue);
	}
	
	@KafkaListener(groupId = "test-group-04",
			topicPartitions = {
					@TopicPartition(topic="test01", partitions = {"0", "1"}),
					@TopicPartition(topic="test02", 
						partitionOffsets = @PartitionOffset(partition="0", initialOffset = "3"))
			})
	public void listenSpecificPartition(ConsumerRecord<String, String> record) {
		log.info(record.toString());
	}
}
