package io.project.app.stream.listener;

import io.project.app.data.model.GeoData;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Component
@Service
@Slf4j
@Data
public class DataListener {
    
    private Flux<GeoData> fluxList;

    /**
     *
     * @param msgString
     * @param acknowledgment
     */
    @KafkaListener(topics = "telegram")
    public void receive(GeoData msgString, Acknowledgment acknowledgment) {
        log.info("Received " + msgString);
        try {
            // if (msgString != null) {
            log.info("Received data:from telegram");
            
            acknowledgment.acknowledge();
            fluxList = Flux.just(msgString);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
       
    }
    
    

}
