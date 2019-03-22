package io.project.app.producer;

import io.project.app.data.model.GeoData;
import io.project.app.producer.sender.Telegram;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan("io.project")
@EnableAsync
@EnableKafka
@EnableScheduling
public class ProducerApplication {

    @Value("${spring.kafka.producer.bootstrap-servers}")
    private String bootstrapServers;

    public static void main(String[] args) {
        final SpringApplication application = new SpringApplication(ProducerApplication.class);
        application.setBannerMode(Banner.Mode.CONSOLE);
        application.setWebApplicationType(WebApplicationType.REACTIVE);
        application.run(args);
    }

    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, 1048570000);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 20971520);
        props.put(ProducerConfig.RETRIES_CONFIG, 10);
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return props;
    }

    public ProducerFactory<String, GeoData> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, GeoData> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    public Telegram kafkaSender() {
        return new Telegram(kafkaTemplate());
    }
}
