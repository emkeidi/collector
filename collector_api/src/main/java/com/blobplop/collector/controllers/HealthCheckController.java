package com.blobplop.collector.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/healthy")
public class HealthCheckController {

    private final Logger logger = LoggerFactory.getLogger(HealthCheckController.class);

    List<String> healthMessages = new ArrayList<>();
    Random random = new Random();
    String lastMessage = null;
    String randomMessage = null;

    public HealthCheckController() {
        healthMessages.add("The Collector is healthy and ready to collect.");
        healthMessages.add("The Collector is ready to collect.");
        healthMessages.add("Collector is ready to bloom.");
        healthMessages.add("Collector is awake.");
        healthMessages.add("Collector is firing up the grill.");
        healthMessages.add("You've reached the Collector.");
        healthMessages.add("The Collector is ready to collect.");
        healthMessages.add("Collect calls are ready to be made.");
        healthMessages.add("What will you collect today?");
        healthMessages.add("Show off your collection.");

    }

    @GetMapping()
    public ResponseEntity<Map<String, String>> healthCheck() {
        Map<String, String> response = new HashMap<>();

        // Ensure the new message is not the same as the last one
        do {
            randomMessage = healthMessages.get(random.nextInt(healthMessages.size()));
        } while (randomMessage.equals(lastMessage));

        // Update the last message
        lastMessage = randomMessage;

        logger.info("Chosen message: \"{}\"", randomMessage);

        response.put("collectorMsg", randomMessage);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}