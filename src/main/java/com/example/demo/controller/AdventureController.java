package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.AdventureService;
import com.example.demo.service.AdventureService.AdventureState;

@RestController
@RequestMapping("/adventure")
public class AdventureController {

    private final AdventureService adventureService;

    public AdventureController(AdventureService adventureService) {
        this.adventureService = adventureService;
    }

    @GetMapping("/current")
    public ResponseEntity<AdventureState> current() {
        return ResponseEntity.ok(adventureService.getCurrent());
    }

    @PostMapping("/choose/left")
    public ResponseEntity<AdventureState> chooseLeft() {
        return ResponseEntity.ok(adventureService.chooseLeft());
    }

    @PostMapping("/choose/right")
    public ResponseEntity<AdventureState> chooseRight() {
        return ResponseEntity.ok(adventureService.chooseRight());
    }

    @PostMapping("/reset")
    public ResponseEntity<AdventureState> reset() {
        return ResponseEntity.ok(adventureService.reset());
    }
}