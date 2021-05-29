### Kafka Consumer and Producer in Spring Boot

* Build and Run

  * `mvn clean install`
  * Run the generated jar in target directory

* Configure

  * `src/main/resources/application.properties`
  * For SSL set`kafka.ssl.enable=true` and provide keystore and truststore properties for kafka

* API Usage

  * Avro

    * Post request `http://localhost:8082/kafka/publish-avro`

    * ```
        {
            "name": "Tony",
            "id": "Iron Man",
            "age": 50,
            "birthDate": 16710165700
        }
      ```

  * Pojo

    * Post request `http://localhost:8082/kafka/publish-pojo`

    * ```
        {
            "name": "Tony",
            "id": "Iron Man",
            "age": 50,
            "birthDate": 16710165700
        }
      ```

* Requirements (tested on)
  * Java 1.8
  * Maven 3.6.3
  * Spring boot 2.4.5

* Others
  * Multiple consumers and producers implementation
  * SSL implementation
  * Avro tool used for creating  `PersonAvro.java` from `person.avsc`, Use only the avro tool to create any avro object class from avro schema.
    * usage `java -jar avro-tools-1.10.2.jar compile schema person.avsc .`  

