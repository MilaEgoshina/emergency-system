package com.example.recipient.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.RoundRobinPartitioner;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Конфигурационный класс для настройки Kafka Producer.
 * Определяет бины для настройки и создания Kafka Producer.
 */
@Configuration
public class KafkaProducerConfiguration {

    /**
     * Устанавливает значение для адреса сервера Kafka.
     */
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    /**
     * Создает свойства для конфигурации Kafka Producer.
     *
     * @return Map- свойств для конфигурации Kafka Producer.
     */
    @Bean
    public Map<String, Object> producerProperties() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, RoundRobinPartitioner.class);
        return properties;
    }

    /**
     * Создает фабрику для Kafka Producer.
     *
     * @return Фабрика для Kafka Producer.
     */
    @Bean
    public ProducerFactory<String, ?> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerProperties());
    }

    /**
     * Создает шаблон Kafka Template для отправки сообщений.
     *
     * @return Kafka Template для отправки сообщений.
     */
    @Bean
    public KafkaTemplate<String, ?> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
