package com.example.demo.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.demo.model.entities.Adventurer;
import com.example.demo.model.equipment.Inventory;
import com.example.demo.model.equipment.Item;
import com.example.demo.model.quests.Quest;
import com.example.demo.model.quests.Reward;

@Service
public class QuestService {

    private final Map<String, Quest> quests = new HashMap<>();
    private final InventoryService inventoryService;
    private final AdventurerService adventurerService;

    public QuestService(InventoryService inventoryService, AdventurerService adventurerService) {
        this.inventoryService = inventoryService;
        this.adventurerService = adventurerService;
    }

    public void addQuest(Quest quest) {
        if (quest == null) {
            throw new IllegalArgumentException("Quest cannot be null.");
        }
        quests.put(quest.getTitle(), quest);
    }

    public void acceptQuest(String title) {
        Quest quest = getQuestByTitle(title);
        if (quest == null) {
            throw new IllegalArgumentException("Quest '" + title + "' not found.");
        }
        quest.obtainQuest();
    }

    public Quest getQuestByTitle(String title) {
        return quests.get(title);
    }

    public Reward completeQuest(String title) {
        Quest quest = getQuestByTitle(title);
        if (quest == null) {
            throw new IllegalArgumentException("Quest '" + title + "' not found.");
        }

        Adventurer activeAdventurer = adventurerService.getActiveAdventurer();
        Inventory<Item> inventory = inventoryService.getPlayerInventory();

        Reward reward = quest.completeQuest(inventory);

        if (reward.getItemReward() != null) {
            inventoryService.addItem(reward.getItemReward(), 1);
        }
        if (reward.getCoin() > 0) {
            activeAdventurer.addCoins(reward.getCoin());
        }

        return reward;
    }
}
