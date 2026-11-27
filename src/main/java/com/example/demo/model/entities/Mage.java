package com.example.demo.model.entities;

public class Mage extends Adventurer {

    private int mana = 50;

    public Mage(String name, int coins) {
        super(name, coins);
    }

    @Override
    public void attack(Combatant target) {
        int custoMana = 10;
        if (mana >= custoMana) {
            mana -= custoMana;
            target.receiveDmg(25);
        } else {
            target.receiveDmg(5);
        }
    }

    public int getMana() {
        return mana;
    }
}
