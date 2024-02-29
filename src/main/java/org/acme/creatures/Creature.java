package org.acme.creatures;

import org.acme.Villager;

import java.util.List;

public class Creature {


    private final String name;
    private int health;
    private final int damage;

    private Creature(Creature.Builder builder) {
        this.name = builder.name;
        this.health = builder.health;
        this.damage = builder.damage;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getDamage() {
        return damage;
    }

    public int takeDamage(int damage) {
        if (damage < 0) {
            throw new IllegalArgumentException("Damage cannot be negative");
        }
        int damageLeft = damage - this.health;
        this.health -= damage;

        if (this.health < 0) {
            this.health = 0;
        }

        return Math.max(damageLeft, 0);
    }

    public void attack(List<Villager> villagers) {
        int damageLeft = this.damage;
        for (Villager villager : villagers) {
            if (damageLeft <= 0) {
                break;
            }
            damageLeft = villager.takeDamage(damageLeft);
        }
    }

    public static class Builder {
        private String name;
        private Integer health;
        private Integer damage;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder health(int health) {
            this.health = health;
            return this;
        }

        public Builder damage(int damage) {
            this.damage = damage;
            return this;
        }

        public Creature build() {
            if (name == null) {
                throw new IllegalStateException("Name cannot be null");
            }
            if (health == null) {
                throw new IllegalStateException("Health cannot be null");
            }
            if (damage == null) {
                throw new IllegalStateException("Damage cannot be null");
            }

            return new Creature(this);
        }
    }


}
