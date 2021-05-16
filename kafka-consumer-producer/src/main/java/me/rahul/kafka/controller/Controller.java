package me.rahul.kafka.controller;


import me.rahul.kafka.model.Person;
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
	
	@PostMapping(value = "/publish")
	public void sendMessageToKafkaTopic(@RequestBody Person message){
	this.producer.sendMessage(message);
	}
}
