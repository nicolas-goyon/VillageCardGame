package org.acme.interfaces;

public abstract class Damageable {
    protected int health;


    /**
     *
     * @param damage
     * @return the damage not taken (the amount of damage that is above the health of the villager)
     */
    public int takeDamage(int damage) {
        if (damage < 0) throw new IllegalArgumentException("Damage must be a positive number");
        int damageLeft = damage - this.health;
        this.health -= damage;

        if (this.health < 0) {
            this.health = 0;
        }

        return Math.max(damageLeft, 0);

    }
}
