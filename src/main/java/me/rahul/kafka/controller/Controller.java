package me.rahul.kafka.controller;


import me.rahul.kafka.model.PersonAvro;
import me.rahul.kafka.model.PersonPojo;
import me.rahul.kafka.service.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/kafka")
public class Controller {
	private final Producer producer;

	@Autowired
	public Controller(Producer producer) {
	this.producer = producer;
	}
	
	@PostMapping(value = "/publish-avro")
	public void sendAvroMessageToKafkaTopic(@RequestBody PersonAvro message){
	this.producer.sendMessageAvro(message);
	}
	
	@PostMapping(value = "/publish-pojo")
	public void sendPojoMessageToKafkaTopic(@RequestBody PersonPojo message){
	this.producer.sendMessagePojo(message);
	}
}
