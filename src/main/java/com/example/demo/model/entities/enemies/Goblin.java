package com.example.demo.model.entities.enemies;

import com.example.demo.model.entities.Combatant;

public class Goblin extends Enemy {
    public Goblin(String name, int coins) {
        super(name, coins);
    }

    @Override
    public void attack(Combatant target) {
        System.out.println("Hahaha sou um Goblin malvado atacando!!!");
        target.receiveDmg(this.getDamage());
    }
}