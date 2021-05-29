package me.rahul.kafka.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import me.rahul.kafka.model.PersonAvro;
import me.rahul.kafka.model.PersonPojo;
import me.rahul.kafka.serializer.AvroDeserializer;
import me.rahul.kafka.serializer.AvroSerializer;
import me.rahul.kafka.serializer.JsonPOJODeserializer;
import me.rahul.kafka.serializer.JsonPOJOSerializer;

@Configuration
public class KafkaConfig {

	@Value(value = "${kafka.bootstrap.address}")
	private String bootstrapAddress;
	
	@Value(value = "${kafka.consumer.avro}")
	private String groupIdAvro;	
	
	@Value(value = "${kafka.consumer.pojo}")
	private String groupIdPojo;	
	
	@Value(value = "${kafka.ssl.enable}")
	private String isSSL;	
	
	@Value(value = "${kafka.ssl.trust-store}")
	private String truststore;	
	
	@Value(value = "${kafka.ssl.trust-store-password}")
	private String truststorePassword;	
	
	@Value(value = "${kafka.ssl.key-store}")
	private String keystore;
	
	@Value(value = "${kafka.ssl.key-store-password}")
	private String keystorePassword;
	
	@Value(value = "${kafka.ssl.key-store-password}")
	private String keyPassword;


	// Avro consumer
	@Bean
	public ConsumerFactory<String, PersonAvro> consumerFactoryAvro() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, groupIdAvro);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, AvroDeserializer.class);
		if (Boolean.parseBoolean(isSSL)) {
			props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SSL");
			props.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, truststore);
			props.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, truststorePassword);
			props.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, keystore);
			props.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, keystorePassword);
			props.put(SslConfigs.SSL_KEY_PASSWORD_CONFIG, keyPassword);
	    }
		return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new AvroDeserializer<>(PersonAvro.class));
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, PersonAvro> kafkaListenerContainerFactoryAvro() {
		ConcurrentKafkaListenerContainerFactory<String, PersonAvro> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactoryAvro());
		return factory;
	}
	
	// Pojo consumer
	@Bean
	public ConsumerFactory<String, PersonPojo> consumerFactoryPojo() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, groupIdPojo);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonPOJODeserializer.class);
		if (Boolean.parseBoolean(isSSL)) {
			props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SSL");
			props.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, truststore);
			props.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, truststorePassword);
			props.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, keystore);
			props.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, keystorePassword);
			props.put(SslConfigs.SSL_KEY_PASSWORD_CONFIG, keyPassword);
	    }
		return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonPOJODeserializer<>(PersonPojo.class));
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, PersonPojo> kafkaListenerContainerFactoryPojo() {
		ConcurrentKafkaListenerContainerFactory<String, PersonPojo> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactoryPojo());
		return factory;
	}

	// Avro producer
	@Bean
	public ProducerFactory<String, PersonAvro> producerFactoryAvro() {
		Map<String, Object> props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, AvroSerializer.class);
		if (Boolean.parseBoolean(isSSL)) {
			props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SSL");
			props.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, truststore);
			props.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, truststorePassword);
			props.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, keystore);
			props.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, keystorePassword);
			props.put(SslConfigs.SSL_KEY_PASSWORD_CONFIG, keyPassword);
	    }
		return new DefaultKafkaProducerFactory<>(props);
	}

	@Bean
	public KafkaTemplate<String, PersonAvro> kafkaTemplateAvro() {
		return new KafkaTemplate<>(producerFactoryAvro());
	}
	
	// Pojo producer
	@Bean
	public ProducerFactory<String, PersonPojo> producerFactoryPojo() {
		Map<String, Object> props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonPOJOSerializer.class);
		if (Boolean.parseBoolean(isSSL)) {
			props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SSL");
			props.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, truststore);
			props.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, truststorePassword);
			props.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, keystore);
			props.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, keystorePassword);
			props.put(SslConfigs.SSL_KEY_PASSWORD_CONFIG, keyPassword);
	    }
		return new DefaultKafkaProducerFactory<>(props);
	}

	@Bean
	public KafkaTemplate<String, PersonPojo> kafkaTemplatePojo() {
		return new KafkaTemplate<>(producerFactoryPojo());
	}

}