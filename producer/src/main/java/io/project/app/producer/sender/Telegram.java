package io.project.app.producer.sender;

import io.project.app.data.model.GeoData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * The Kafka sender
 *
 * @author armena
 */
@Service
@Component
@Slf4j
public class Telegram {

    private final KafkaTemplate<String, GeoData> kafkaTemplate;

    private final String topic = "telegram";

    @Autowired
    public Telegram(KafkaTemplate<String, GeoData> kafkaTemplate) {
        super();
        this.kafkaTemplate = kafkaTemplate;
    }

    @Async
    public ListenableFuture<SendResult<String, GeoData>> send(GeoData message) {
        
        ListenableFuture<SendResult<String, GeoData>> future = this.kafkaTemplate.send(topic, message);        
        
        future.addCallback(new ListenableFutureCallback<SendResult<String, GeoData>>() {
            
            @Override
            public void onSuccess(SendResult<String, GeoData> result) {
                log.info("KAFKA: Success sent message='{}' with offset={}", message, result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error("KAFKA Fail: unable to send message='{}'", message, ex);
            }
        });
        
        return future;
    }

}
