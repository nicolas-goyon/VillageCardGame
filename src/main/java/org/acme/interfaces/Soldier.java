package org.acme.interfaces;

import java.util.List;

public abstract class Soldier extends Damageable{

    protected int damage;


    public void attack(List<? extends Damageable> damageables) {
        int damageLeft = this.damage;
        for (Damageable damageable : damageables) {
            if (damageLeft <= 0) {
                break;
            }
            damageLeft = damageable.takeDamage(damageLeft);
        }
    }


}
