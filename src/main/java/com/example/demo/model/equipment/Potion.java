package com.example.demo.model.equipment;

import com.example.demo.model.entities.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Potion extends Item implements Usable {
    private int amount_heal;

    public Potion(String name, ItemType type, Rarity rarity, int amount_heal){
        super(name, type, rarity);
        validateHealingAmount(amount_heal);
        this.amount_heal = amount_heal;
    }

    public void setAmount_heal(int amount_heal) {
        validateHealingAmount(amount_heal);
        this.amount_heal = amount_heal;
    }

    private static void validateHealingAmount(int amount_heal) {
        if (amount_heal < 0) {
            throw new IllegalArgumentException("Potion amount_heal cannot be negative.");
        }
    }

    @Override
    public void use(Entity entity) {
        entity.heal(amount_heal);
    }
}
