package com.example.demo.model.entities;

public interface Combatant {
    void attack(Combatant target);
    void receiveDmg(int dmg);
    int getLife();
    boolean isAlive();
}