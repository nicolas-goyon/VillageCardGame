package org.acme.villagers;

public enum Job {
    UNEMPLOYED("Unemployed"),
    FARMER("Farmer"),
    WARRIOR("Warrior"),
    HEALER("Healer");

    private final String name;

    private Job(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
