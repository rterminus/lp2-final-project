package com.example.demo.model.entities.enemies;

import com.example.demo.model.entities.Combatant;

public class Skeleton extends Enemy {
    public Skeleton(String name, int coins) {
        super(name, coins);
    }

    @Override
    public void attack(Combatant target) {
        System.out.println("*Barulhos de esqueleto...*");
        System.out.println("*Barulhos de... FLECHA!*");
        target.receiveDmg(this.getDamage());
    }
}