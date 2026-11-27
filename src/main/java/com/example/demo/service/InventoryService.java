package com.example.demo.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.example.demo.model.equipment.Inventory;
import com.example.demo.model.equipment.Item;
import com.example.demo.model.equipment.ItemType;
import com.example.demo.model.equipment.Rarity;

@Service
public class InventoryService {

    private final AdventurerService adventurerService;

    public InventoryService(AdventurerService adventurerService) {
        this.adventurerService = adventurerService;
    }

    public Inventory<Item> getPlayerInventory() {
        return adventurerService.getActiveAdventurer().getInventory();
    }

    public void addItem(Item item, Integer quantity) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null.");
        }
        item.validate();
        getPlayerInventory().insertItem(item, quantity);
    }

    public void removeItem(Item item, Integer quantity) {
        getPlayerInventory().removeItem(item, quantity);
    }

    public Boolean hasItem(Item item) {
        return getPlayerInventory().hasItem(item);
    }

    public Set<Item> getItems() {
        return getPlayerInventory().getItemsKeys();
    }

    // Java Streams & Lambdas requirement: filters inventory items by rarity
    public List<Item> filterByRarity(Rarity rarity) {
        if (rarity == null) {
            throw new IllegalArgumentException("Rarity cannot be null.");
        }
        return getItems().stream()
                .filter(item -> item.getRarity() == rarity)
                .toList();
    }

    // Java Streams & Lambdas requirement: filters inventory items by type
    public List<Item> filterByType(ItemType type) {
        if (type == null) {
            throw new IllegalArgumentException("ItemType cannot be null.");
        }
        return getItems().stream()
                .filter(item -> item.getType() == type)
                .toList();
    }
}
