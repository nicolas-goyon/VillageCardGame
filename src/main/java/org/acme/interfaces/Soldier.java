package org.acme.interfaces;

import org.acme.SoldierAttackResult;

import java.util.List;

public abstract class Soldier extends Damageable{

    protected int damage;
    public int getDamage() {
        return damage;
    }
    public void setDamage(int damage) {
        this.damage = damage;
    }


    public SoldierAttackResult attack(List<? extends Damageable> damageables) {
        SoldierAttackResult result = new SoldierAttackResult(this);
        int damageLeft = this.damage;
        for (Damageable damageable : damageables) {
            if (damageLeft <= 0) {
                break;
            }
            damageLeft = damageable.takeDamage(damageLeft);
            result.addTarget((Soldier) damageable);
        }
        return result;
    }


}
