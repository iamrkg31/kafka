package me.rahul.kafka.service;

import me.rahul.kafka.model.PersonAvro;
import me.rahul.kafka.model.PersonPojo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {
	private final Logger logger = LoggerFactory.getLogger(Consumer.class);	
	
	@KafkaListener(topics = "${kafka.topic.avro}", 
			groupId = "${kafka.consumer.avro}",
			containerFactory = "kafkaListenerContainerFactoryAvro")
	public void listenAvro(PersonAvro message) {
		logger.info("Received avro messasge in group : {}", message.toString());
	}
	
	@KafkaListener(topics = "${kafka.topic.pojo}", 
			groupId = "${kafka.consumer.pojo}",
			containerFactory = "kafkaListenerContainerFactoryPojo")
	public void listenPojo(PersonPojo message) {
		logger.info("Received pojo messasge in group : {}", message.toString());
	}
}