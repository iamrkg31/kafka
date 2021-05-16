package me.rahul.kafka.listener;

import me.rahul.kafka.model.Person;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class AvroMessageConsumer {
	private final Logger logger = LoggerFactory.getLogger(AvroMessageConsumer.class);

	@KafkaListener(topics = "${kafka.topic.name}", groupId = "${kafka.consumer}")
	public void listen(Person message) {
		logger.info("Received Messasge in group : {}", message);
	}
}