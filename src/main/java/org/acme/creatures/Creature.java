package org.acme.creatures;

import org.acme.interfaces.Soldier;

public class Creature extends Soldier {
    private final String name;

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
