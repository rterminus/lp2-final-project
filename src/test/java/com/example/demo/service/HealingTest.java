package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.example.demo.exception.ItemNotFoundException;
import com.example.demo.model.entities.Adventurer;
import com.example.demo.model.equipment.ItemType;
import com.example.demo.model.equipment.Potion;
import com.example.demo.model.equipment.Rarity;

class HealingTest {

    private final Potion potion = new Potion("Potion of Health", ItemType.CONSUMABLE, Rarity.COMMON, 40);

    @Test
    void potionRestoresHp() {
        Adventurer hero = new Adventurer("Hero", 0);
        hero.receiveDmg(50); // 50 HP

        potion.use(hero);

        assertEquals(90, hero.getLife());
    }

    @Test
    void healingNeverExceedsMaxHp() {
        Adventurer hero = new Adventurer("Hero", 0);
        hero.receiveDmg(10); // 90 HP

        potion.use(hero); // 90 + 40 = 130 -> limitado a 100

        assertEquals(100, hero.getLife());
    }

    @Test
    void throwsItemNotFoundWhenPotionsRunOut() {
        AdventurerService adventurerService = new AdventurerService();
        InventoryService inventoryService = new InventoryService(adventurerService);
        ItemService itemService = new ItemService(adventurerService, inventoryService);

        // O Arthur começa com 2 poções
        itemService.useItem("Potion of Health");
        itemService.useItem("Potion of Health");

        assertThrows(ItemNotFoundException.class, () -> itemService.useItem("Potion of Health"));
    }
}