package com.example.demo.model.entities;

import com.example.demo.model.equipment.*;

import lombok.Setter;

@Setter 
public abstract class Entity {
    private static final int MAX_LIFE = 100;

    private String name;
    private int damage;
    private int life;
    private int level;
    private Inventory<Item> inventory;
    private int energy;
    private int coins;

    public Entity(String name, int coins) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("entities.Adventurer name cannot be empty or null.");
        }
        if (coins < 0) {
            throw new IllegalArgumentException("Coin balance cannot be negative.");
        }

        this.name = name;
        this.level = 1;
        this.life = MAX_LIFE;
        this.energy = 0;
        this.damage = 10;
        this.inventory = new Inventory<Item>();
        this.coins = coins;
    }

    public void heal(int amount_heal) {
        if (amount_heal < 0) {
            throw new IllegalArgumentException("Healing amount cannot be negative.");
        }
        this.life += Math.min(amount_heal, MAX_LIFE - this.life);
    }

    public void receiveDmg(int amount_dmg) {
        if (amount_dmg < 0) {
            throw new IllegalArgumentException("Damage amount cannot be negative.");
        }
        this.life = Math.max(0, this.life - amount_dmg);
    }

    public void addCoins(int amount) {
        long newBalance = (long) this.coins + amount;
        if (newBalance < 0 || newBalance > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Coin balance must be between 0 and " + Integer.MAX_VALUE + ".");
        }
        this.coins = (int) newBalance;
    }

    public String getName() {
        return name;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        if (life < 0 || life > MAX_LIFE) {
            throw new IllegalArgumentException("Life must be between 0 and " + MAX_LIFE + ".");
        }
        this.life = life;
    }

    public int getLevel() {
        return level;
    }

    public int getCoins() {
        return this.coins;
    }

    public void setCoins(int coins) {
        if (coins < 0) {
            throw new IllegalArgumentException("Coin balance cannot be negative.");
        }
        this.coins = coins;
    }

    public int getEnergy() {
        return this.energy;
    }

    public Inventory<Item> getInventory() {
        return inventory;
    }

    public void setInventory(Inventory<Item> inventory) {
        if (inventory == null) {
            throw new IllegalArgumentException("Inventory cannot be null.");
        }
        this.inventory = inventory;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        if (damage < 0) {
            throw new IllegalArgumentException("Damage cannot be negative.");
        }
        this.damage = damage;
    }
}
