package com.example.demo.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.demo.exception.CharacterNotFoundException;
import com.example.demo.exception.InsufficientHealthException;
import com.example.demo.model.entities.Adventurer;
import com.example.demo.model.equipment.ItemType;
import com.example.demo.model.equipment.Potion;
import com.example.demo.model.equipment.Rarity;

@Service
public class AdventurerService {

    private final Map<String, Adventurer> adventurers = new LinkedHashMap<>();
    private String activeAdventurerKey;

    public AdventurerService() {
        // Default adventurer initialized in memory with starting potions for testing
        // and API usage
        Adventurer defaultHero = new Adventurer("Arthur", 50);
        defaultHero.getInventory().insertItem(
                new Potion("Potion of Health", ItemType.CONSUMABLE, Rarity.COMMON, 40), 2);
        registerAdventurer(defaultHero);
    }

    public Adventurer registerAdventurer(Adventurer adventurer) {
        if (adventurer == null || adventurer.getName() == null || adventurer.getName().isBlank()) {
            throw new IllegalArgumentException("Adventurer and its name cannot be null or empty.");
        }

        String key = adventurer.getName().trim().toLowerCase();
        if (adventurers.containsKey(key)) {
            throw new IllegalArgumentException(
                    "An adventurer with the name '" + adventurer.getName() + "' already exists.");
        }

        adventurers.put(key, adventurer);
        this.activeAdventurerKey = key;
        return adventurer;
    }

    public Adventurer findByName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Adventurer name cannot be empty.");
        }

        Adventurer found = adventurers.get(name.trim().toLowerCase());
        if (found == null) {
            throw new CharacterNotFoundException("Adventurer '" + name + "' not found.");
        }
        return found;
    }

    public Adventurer getActiveAdventurer() {
        if (activeAdventurerKey == null || !adventurers.containsKey(activeAdventurerKey)) {
            throw new CharacterNotFoundException("No active adventurer available.");
        }
        return adventurers.get(activeAdventurerKey);
    }

    public Adventurer selectActiveAdventurer(String name) {
        Adventurer adventurer = findByName(name);
        this.activeAdventurerKey = adventurer.getName().trim().toLowerCase();
        return adventurer;
    }

    public List<Adventurer> findAll() {
        return new ArrayList<>(adventurers.values());
    }

    // Java Streams & Lambdas requirement: filters only living adventurers
    public List<Adventurer> findAliveAdventurers() {
        return adventurers.values().stream()
                .filter(a -> a.getLife() > 0)
                .toList();
    }

    public Adventurer receiveDamage(int damage) {
        if (damage < 0) {
            throw new IllegalArgumentException("Damage received cannot be negative.");
        }
        Adventurer active = getActiveAdventurer();
        active.receiveDmg(damage);
        return active;
    }

    public Adventurer heal(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Heal amount cannot be negative.");
        }
        Adventurer active = getActiveAdventurer();
        if (active.getLife() <= 0) {
            throw new InsufficientHealthException(
                    "Adventurer '" + active.getName() + "' is dead and cannot be healed.");
        }
        active.heal(amount);
        return active;
    }
}
