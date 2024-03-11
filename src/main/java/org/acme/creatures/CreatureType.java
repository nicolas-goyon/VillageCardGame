package org.acme.creatures;

public enum CreatureType {
    GOBLIN("Goblin", 10, 3),
    ORC("Orc", 50, 20),
    SNAKE("Snake", 5, 10);

    private final String name;
    private final int health;
    private final int damage;

    private CreatureType(String name, int health, int damage) {
        this.name = name;
        this.health = health;
        this.damage = damage;
    }

    public Creature create() {

        return new Creature.Builder()
                .name(name)
                .health(health)
                .damage(damage)
                .build();
    }
}