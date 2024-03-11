package org.acme;

import org.acme.interfaces.Soldier;

import java.util.ArrayList;
import java.util.List;

public class SoldierAttackResult {

    private final Soldier attacker;

    private final ArrayList<Soldier> targets;

    public SoldierAttackResult(Soldier attacker) {
        this.attacker = attacker;
        this.targets = new ArrayList<>();
    }

    public Soldier getAttacker() {
        return attacker;
    }

    public List<Soldier> getTargets() {
        return targets;
    }

    public void addTarget(Soldier target) {
        targets.add(target);
    }
}
