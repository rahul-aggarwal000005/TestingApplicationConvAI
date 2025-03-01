package com.testing.application.TestingApplication.controllers;

import com.testing.application.TestingApplication.models.ClinicDetails;
import com.testing.application.TestingApplication.services.BotService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class BotController {

    private final BotService botService;

    public BotController(BotService botService) {
        this.botService = botService;
    }

    @GetMapping("/")
    public String sayHelloBot() {
        return "Hello !!";
    }

    // get hardcoded clinic details
    @GetMapping("/getAllClinics")
    public List<ClinicDetails> getAllClinics() {
        return botService.getAllClinics();
    }

}
