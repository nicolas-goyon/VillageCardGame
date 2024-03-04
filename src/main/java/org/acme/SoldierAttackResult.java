package org.acme;

import org.acme.interfaces.Soldier;

import java.util.ArrayList;

public class SoldierAttackResult {

    private Soldier attacker;

    private ArrayList<Soldier> targets;

    public SoldierAttackResult(Soldier attacker) {
        this.attacker = attacker;
        this.targets = new ArrayList<>();
    }

    public Soldier getAttacker() {
        return attacker;
    }

    public ArrayList<Soldier> getTargets() {
        return targets;
    }

    public void addTarget(Soldier target) {
        targets.add(target);
    }
}
