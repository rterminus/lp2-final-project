package com.example.demo.model.entities;

public class Warrior extends Adventurer {

    public Warrior(String name, int coins) {
        super(name, coins);
    }

    @Override
    public void attack(Combatant target) {
        int dano = 15;
        target.receiveDmg(dano);
    }
}
