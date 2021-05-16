package me.rahul.kafka.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import me.rahul.kafka.model.Person;
import me.rahul.kafka.serializer.AvroDeserializer;
import me.rahul.kafka.serializer.AvroSerializer;

@Configuration
public class KafkaConfig {

	@Value(value = "${kafka.bootstrap.address}")
	private String bootstrapAddress;

	@Value(value = "${kafka.topic.name}")
	private String topicName;

	@Value(value = "${kafka.consumer}")
	private String groupId;	

	@Bean
	public KafkaAdmin kafkaAdmin() {
		Map<String, Object> configs = new HashMap<>();
		configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		return new KafkaAdmin(configs);
	}

	@Bean
	public NewTopic topic() {
		return new NewTopic(topicName, 1, (short) 1);
	}

	@Bean
	public ConsumerFactory<String, Person> consumerFactory() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, AvroDeserializer.class);
		return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new AvroDeserializer<>(Person.class));
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, Person> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, Person> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		return factory;
	}

	@Bean
	public ProducerFactory<String, Person> producerFactory() {
		Map<String, Object> configProps = new HashMap<>();
		configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, AvroSerializer.class);
		return new DefaultKafkaProducerFactory<>(configProps);
	}

	@Bean
	public KafkaTemplate<String, Person> kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}

}