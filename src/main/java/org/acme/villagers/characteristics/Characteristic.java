package org.acme.villagers.characteristics;

import org.acme.villagers.Villager;

public interface Characteristic {
    void apply(Villager villager);
    String getName();
}
