package org.acme;

import org.acme.villagers.Job;
import org.acme.villagers.Villager;

import java.util.ArrayList;
import java.util.List;

public class Village {
    private static final int BASEFOOD = 100;
    private static final Villager BASEVILLAGER = new Villager.Builder()
            .name("John")
            .surname("Doe")
            .age(20)
            .job(Job.UNEMPLOYED)
            .characteristic(List.of())
            .stomachSize(10)
            .health(100)
            .baseHealth(100)
            .damage(10)
            .magic(0)
            .workingForce(10)
            .build();


    private final String name;
    private final List<Villager> villagers;
    private int foodSupplies;

    public Village(String name) {
        this.name = name;
        this.villagers = new ArrayList<>();
        villagers.add(BASEVILLAGER);
        this.foodSupplies = BASEFOOD;
    }

    // Getters

    public String getName() {
        return name;
    }

    public List<Villager> getVillagers() {
        return villagers;
    }

    public int getFoodSupplies() {
        return foodSupplies;
    }






    // Methods


    public void addVillager(Villager villager) {
        if (villager == null) {
            throw new IllegalArgumentException("Villager cannot be null");
        }

        if (villagers.contains(villager)) {
            throw new IllegalArgumentException("Villager already exists");
        }

        if (villager.getName() == null || villager.getSurname() == null) {
            throw new IllegalArgumentException("Villager must have a name and a surname");
        }

        if (villagers.stream().anyMatch(v -> v.getName().equals(villager.getName()) && v.getSurname().equals(villager.getSurname()))) {
            throw new IllegalArgumentException("Villager with the same name and surname already exists");
        }

        villagers.add(villager);
    }

    public void removeVillager(String name, String surname) {
        villagers.removeIf(villager -> villager.getName().equals(name) && villager.getSurname().equals(surname));
    }


    public int foodProduction() {
        int foodProduced = 0;
        for (Villager villager : villagers) {
            if (villager.getJob() == Job.FARMER) {
                foodProduced += villager.produceFood();
            }
        }
        return foodProduced;
    }

    public void produceFood() {
        foodSupplies += foodProduction();
    }

    public void eatFood() {
        for (Villager villager : villagers) {
            foodSupplies = villager.eat(foodSupplies);
        }
    }


}
