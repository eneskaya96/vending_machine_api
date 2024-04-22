package com.vendingmachine.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class TemperatureService {

    private static final double DESIRED_TEMPERATURE = 5.0; 
    private double currentTemperature = 5.0; 

    @Scheduled(fixedRate = 30000) // 30 second
    public void checkAndAdjustTemperature() {
        double sensorTemperature = simulateSensorData();

        if (sensorTemperature > DESIRED_TEMPERATURE) {
            coolDown();
            currentTemperature = DESIRED_TEMPERATURE;
        }
    }

    private double simulateSensorData() {
        return Math.random() * 10 + 1;
    }

    private void coolDown() {
        System.out.println("Cooling down the products...");
    }
}
