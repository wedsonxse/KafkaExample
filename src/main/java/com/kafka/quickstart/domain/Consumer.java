package com.kafka.quickstart.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class Consumer {
    KafkaConsumer<String,String> consumer;
    ObjectMapper jsonMapper;

    public Consumer(String topicName, String bootstrapServers){
        this.jsonMapper = new ObjectMapper();
        String groupId = "quickstart-application";

        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        this.consumer = new KafkaConsumer<>(properties);
        this.consumer.subscribe(Arrays.asList(topicName));
    }

    public void lookupAndConsumeData() throws JsonProcessingException {
        while(true){
            ConsumerRecords<String,String> records = this.consumer.poll(Duration.ofMillis(3000));
            for (ConsumerRecord<String,String> record: records){
                System.out.println("Value: " + record.value());
                System.out.println("Partition: " + record.partition() + ", Offset:" + record.offset());
                System.out.println("\n\n");
            }
        }
    }
}
