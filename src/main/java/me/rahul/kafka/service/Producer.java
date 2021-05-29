package me.rahul.kafka.service;

import me.rahul.kafka.model.PersonAvro;
import me.rahul.kafka.model.PersonPojo;

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

    @Value(value = "${kafka.topic.avro}")
    private String topicAvro;

    @Value(value = "${kafka.topic.pojo}")
    private String topicPojo;


    @Autowired
    private KafkaTemplate<String, PersonAvro> kafkaTemplateAvro;
    
    @Autowired
    private KafkaTemplate<String, PersonPojo> kafkaTemplatePojo;
    

    public void sendMessageAvro(PersonAvro message) {
        ListenableFuture<SendResult<String, PersonAvro>> future = kafkaTemplateAvro.send(topicAvro, message);
        future.addCallback(
                new ListenableFutureCallback<SendResult<String, PersonAvro>>() {
                    @Override
                    public void onSuccess(SendResult<String, PersonAvro> result) {
                    	logger.info(
                                "Sent message=[{}] with offset=[{}]", message, result.getRecordMetadata().offset());
                    }

                    @Override
                    public void onFailure(Throwable ex) {
                    	logger.info("Unable to send message=[{}] due to : {}", message, ex.getMessage());
                    }
                });
    }
    
    public void sendMessagePojo(PersonPojo message) {
        ListenableFuture<SendResult<String, PersonPojo>> future = kafkaTemplatePojo.send(topicPojo, message);
        future.addCallback(
                new ListenableFutureCallback<SendResult<String, PersonPojo>>() {
                    @Override
                    public void onSuccess(SendResult<String, PersonPojo> result) {
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
