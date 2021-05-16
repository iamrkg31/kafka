package me.rahul.kafka.service;

import me.rahul.kafka.model.Person;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class Producer {	
	private final Logger logger = LoggerFactory.getLogger(Producer.class);

    @Value("${kafka.topic.name}")
    private String topicName;

    @Autowired
    private KafkaTemplate<String, Person> kafkaTemplate;

    public void sendMessage(Person message) {
        ListenableFuture<SendResult<String, Person>> future = kafkaTemplate.send(topicName, message);
        future.addCallback(
                new ListenableFutureCallback<SendResult<String, Person>>() {
                    @Override
                    public void onSuccess(SendResult<String, Person> result) {
                    	logger.info(
                                "Sent message=[{}] with offset=[{}]", message, result.getRecordMetadata().offset());
                    }

                    @Override
                    public void onFailure(Throwable ex) {
                    	logger.info("Unable to send message=[{}] due to : {}", message, ex.getMessage());
                    }
                });
    }
}
