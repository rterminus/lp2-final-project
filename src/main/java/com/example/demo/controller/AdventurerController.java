package com.example.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.entities.Adventurer;
import com.example.demo.service.AdventurerService;

@RestController
@RequestMapping("/adventurer")
public class AdventurerController {

    public record CreateAdventurerRequest(String name, int coins) {}

    private final AdventurerService adventurerService;

    public AdventurerController(AdventurerService adventurerService) {
        this.adventurerService = adventurerService;
    }

    @GetMapping("/status")
    public ResponseEntity<Adventurer> getStatus() {
        return ResponseEntity.ok(adventurerService.getActiveAdventurer());
    }

    @GetMapping
    public ResponseEntity<List<Adventurer>> getAllAdventurers() {
        return ResponseEntity.ok(adventurerService.findAll());
    }

    @GetMapping("/alive")
    public ResponseEntity<List<Adventurer>> getAliveAdventurers() {
        return ResponseEntity.ok(adventurerService.findAliveAdventurers());
    }

    @GetMapping("/{name}")
    public ResponseEntity<Adventurer> getByName(@PathVariable String name) {
        return ResponseEntity.ok(adventurerService.findByName(name));
    }

    @PostMapping
    public ResponseEntity<Adventurer> createAdventurer(@RequestBody CreateAdventurerRequest request) {
        Adventurer adventurer = new Adventurer(request.name(), request.coins());
        return ResponseEntity.ok(adventurerService.registerAdventurer(adventurer));
    }

    @PostMapping("/select/{name}")
    public ResponseEntity<Adventurer> selectActive(@PathVariable String name) {
        return ResponseEntity.ok(adventurerService.selectActiveAdventurer(name));
    }
}
