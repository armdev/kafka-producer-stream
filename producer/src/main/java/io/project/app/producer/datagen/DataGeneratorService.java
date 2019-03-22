/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.project.app.producer.datagen;

import io.project.app.data.model.GeoData;
import io.project.app.producer.sender.Telegram;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 *
 * @author armena
 */
@Service
@Component
@Slf4j
public class DataGeneratorService {
    
    @Autowired
    private Telegram telegram;
    
    @Scheduled(fixedDelay = 5000)
    public void dataGenerator(){
        GeoData geoData = new GeoData(37.8, 33.9);
        geoData = new GeoData(37.8, 41.9);
        log.info("Sending next data");
        telegram.send(geoData);
        
        geoData = new GeoData(39.8, 43.9);
        log.info("Sending next data");
        telegram.send(geoData);
        
        geoData = new GeoData(41.8, 44.5);
        log.info("Sending next data");
        telegram.send(geoData);
        
    }
    
}
