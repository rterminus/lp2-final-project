package com.example.demo.model.equipment;

import java.util.Objects;
import com.example.demo.model.entities.Entity;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type", visible = true)
@JsonSubTypes({
    @JsonSubTypes.Type(value = Potion.class, name = "CONSUMABLE"),
    @JsonSubTypes.Type(value = Weapon.class, name = "EQUIPMENT"),
    @JsonSubTypes.Type(value = Collectible.class, name = "COLLECTIBLE")
})
public abstract class Item {
    private String name;
    private ItemType type;
    private Rarity rarity;

    public Item(String name, ItemType type, Rarity rarity) {
        validateName(name);
        validateType(type);
        validateRarity(rarity);

        this.name = name;
        this.type = type;
        this.rarity = rarity;
    }

    public void setName(String name) {
        validateName(name);
        this.name = name;
    }

    public void setType(ItemType type) {
        validateType(type);
        this.type = type;
    }

    public void setRarity(Rarity rarity) {
        validateRarity(rarity);
        this.rarity = rarity;
    }

    private static void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("equipment.Item name cannot be empty or null.");
        }
    }

    private static void validateType(ItemType type) {
        if (type == null) {
            throw new IllegalArgumentException("equipment.Item type cannot be null.");
        }
    }

    private static void validateRarity(Rarity rarity) {
        if (rarity == null) {
            throw new IllegalArgumentException("equipment.Item rarity cannot be null.");
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Item otherItem = (Item) obj;

        return Objects.equals(this.name, otherItem.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name);
    }

    @Override
    public String toString() {
        return this.getName();
    }

    public void use(Entity entity) {
        throw new UnsupportedOperationException("Unimplemented method 'use'");
    }
}
