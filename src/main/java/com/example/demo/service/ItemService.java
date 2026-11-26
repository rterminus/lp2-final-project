package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.exception.InsufficientHealthException;
import com.example.demo.exception.ItemNotFoundException;
import com.example.demo.model.entities.Adventurer;
import com.example.demo.model.equipment.Item;
import com.example.demo.model.equipment.Usable;

@Service
public class ItemService {

    private final AdventurerService adventurerService;
    private final InventoryService inventoryService;

    public ItemService(AdventurerService adventurerService, InventoryService inventoryService) {
        this.adventurerService = adventurerService;
        this.inventoryService = inventoryService;
    }

    // Searches for the item inside the active adventurer's real inventory
    public Item searchItem(String itemName) {
        if (itemName == null || itemName.isBlank()) {
            throw new IllegalArgumentException("Item name cannot be null or empty.");
        }

        return inventoryService.getItems().stream()
                .filter(item -> item.getName().equalsIgnoreCase(itemName.trim()))
                .findFirst()
                .orElseThrow(() -> new ItemNotFoundException("Item '" + itemName + "' is not available in the inventory."));
    }

    // Applies item effect (e.g., heals adventurer) and consumes 1 unit from inventory
    public String useItem(String itemName) {
        Adventurer activeAdventurer = adventurerService.getActiveAdventurer();

        if (activeAdventurer.getLife() <= 0) {
            throw new InsufficientHealthException("Adventurer '" + activeAdventurer.getName() + "' is dead and cannot use items.");
        }

        Item item = searchItem(itemName);

        if (!(item instanceof Usable usableItem)) {
            throw new IllegalArgumentException("You cannot use the item '" + item.getName() + "', as it is not usable!");
        }

        usableItem.use(activeAdventurer);
        inventoryService.removeItem(item, 1);

        return "The item '" + item.getName() + "' was used successfully! Current HP: " + activeAdventurer.getLife() + ".";
    }
}
