package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import org.acme.villagers.Job;
import org.acme.villagers.Villager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class VillageTest {
    private Village village;

    @BeforeEach
    void setUp() {
        village = new Village("TestVillage");

    }

    @Test
    void testVillage() {
        assertEquals("TestVillage", village.getName());
        assertEquals(1, village.getVillagers().size());
        assertEquals(100, village.getFoodSupplies());
    }

    @Test
    void testAddVillager() {
        village.addVillager(new Villager.Builder()
                .name("Ali")
                .surname("Gator")
                .age(25)
                .job(Job.FARMER)
                .characteristic(List.of())
                .stomachSize(10)
                .health(100)
                .baseHealth(100)
                .damage(10)
                .magic(10)
                .workingForce(10)
                .build());
        assertEquals(2, village.getVillagers().size());
    }


    @Test
    void testRemoveVillager() {
        Villager villagerToRemove = village.getVillagers().getFirst();
        village.removeVillager(villagerToRemove.getName(), villagerToRemove.getSurname());

        assertEquals(0, village.getVillagers().size());
    }

    @Test
    void testFoodProduction() {
        assertEquals(0, village.foodProduction());

        Villager villager = new Villager.Builder()
                .name("Ali")
                .surname("Gator")
                .age(25)
                .job(Job.FARMER)
                .characteristic(List.of())
                .stomachSize(10)
                .health(100)
                .baseHealth(100)
                .damage(10)
                .magic(10)
                .workingForce(10)
                .build();

        village.addVillager(villager);

        assertEquals(10, village.foodProduction());

        Villager farmer = new Villager.Builder()
                .name("AlicO")
                .surname("PaysDesMerveilles")
                .age(25)
                .job(Job.FARMER)
                .characteristic(List.of())
                .stomachSize(10)
                .health(100)
                .baseHealth(100)
                .damage(10)
                .magic(10)
                .workingForce(10)
                .build();

        village.addVillager(farmer);

        assertEquals(20, village.foodProduction());

        Villager notFarmer = new Villager.Builder()
                .name("John")
                .surname("Attend")
                .age(25)
                .job(Job.UNEMPLOYED)
                .characteristic(List.of())
                .stomachSize(10)
                .health(100)
                .baseHealth(100)
                .damage(10)
                .magic(10)
                .workingForce(10)
                .build();

        village.addVillager(notFarmer);

        assertEquals(20, village.foodProduction());
        village.produceFood();
        assertEquals(120, village.getFoodSupplies());
    }

    @Test
    void testEating() {
        village.eatFood();
        assertEquals(90, village.getFoodSupplies());

        Villager villager = new Villager.Builder()
                .name("Ali")
                .surname("Gator")
                .age(25)
                .job(Job.FARMER)
                .characteristic(List.of())
                .stomachSize(10)
                .health(100)
                .baseHealth(100)
                .damage(10)
                .magic(10)
                .workingForce(10)
                .build();

        village.addVillager(villager);
        village.eatFood();
        assertEquals(70, village.getFoodSupplies());
    }

    @Test
    void addVillagerExceptions() {
        testAddVillagerException(null);
        Villager jhonDoe = new Villager.Builder()
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
        testAddVillagerException(jhonDoe);
        jhonDoe.setName(null);
        testAddVillagerException(jhonDoe);
        jhonDoe.setName("John");
        jhonDoe.setSurname(null);
        testAddVillagerException(jhonDoe);
        jhonDoe.setSurname("Doe");
        Villager firstVillager = village.getVillagers().getFirst();
        testAddVillagerException(firstVillager);
    }

    private void testAddVillagerException(Villager villager) {
        assertThrows(IllegalArgumentException.class, () -> village.addVillager(villager));
    }

}
