package sn.armande.kafkademo.Config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicsConfig {

    @Value("${spring.Kafka.topics.orders}") ///  pour referencier nottre topics.
    private String ordersTopics;

    @Bean
    public NewTopic ordersTopics(){
        return TopicBuilder.name(ordersTopics)
                .partitions(3)
                .replicas(1)
                .build();
    }
}
