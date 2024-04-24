package com.kafka.quickstart;

import com.kafka.quickstart.adapters.Ingestor;
import com.kafka.quickstart.domain.Consumer;
import com.kafka.quickstart.domain.Producer;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaExample {

	public static void main(String[] args) throws Exception {
		Dotenv env = Dotenv.load();
		Ingestor ingestor = new Ingestor();

		ingestor.addDataOrigin(env.get("DATA_PROVIDER_API"));
		ingestor.addListener(new Producer(env.get("BOOTSTRAP_SERVERS")));
		ingestor.getThreadInstance().start();

		new Consumer(env.get("KAFKA_TOPIC_NAME"),env.get("BOOTSTRAP_SERVERS")).lookupAndConsumeData();

		SpringApplication.run(KafkaExample.class, args);

	}

}
