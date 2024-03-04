package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import org.acme.creatures.Creature;
import org.acme.creatures.CreatureType;
import org.acme.villagers.Job;
import org.acme.villagers.Villager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
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


    @Test
    void attackMethods(){
        List<Creature> creatures = new ArrayList<>(List.of(CreatureType.GOBLIN.create(), CreatureType.GOBLIN.create(), CreatureType.GOBLIN.create()));

        village.creatureAttack(creatures.getFirst());
        assertEquals(3, creatures.size());
        assertEquals(1, village.getVillagers().size());
        assertEquals(97, village.getVillagers().getFirst().getHealth());

        village.getVillagers().getFirst().setHealth(100);

        Villager villager = new Villager.Builder()
                .name("Ali")
                .surname("Gator")
                .age(25)
                .job(Job.WARRIOR)
                .characteristic(List.of())
                .stomachSize(10)
                .health(100)
                .baseHealth(100)
                .damage(10)
                .magic(10)
                .workingForce(10)
                .build();
        village.addVillager(villager);

        village.creatureAttack(creatures.getFirst());


        assertEquals(3, creatures.size());
        assertEquals(2, village.getVillagers().size());
        assertEquals(97, village.getVillagers().get(1).getHealth()); // Warrior has 3 damage
        assertEquals(100, village.getVillagers().getFirst().getHealth()); // Non warrior has 0 damage

        village.villagerAttack(creatures, 1);
        assertEquals(2, creatures.size());
        assertEquals(2, village.getVillagers().size());
        assertEquals(10, creatures.getFirst().getHealth());
        assertEquals(10, creatures.get(1).getHealth());

    }

    @Test
    void creatureAttackExceptions() {
        assertThrows(IllegalArgumentException.class, () -> village.creatureAttack(null));
    }

    @Test
    void villagerAttackExceptions() {


        Villager villager = new Villager.Builder()
                .name("Ali")
                .surname("Gator")
                .age(25)
                .job(Job.WARRIOR)
                .characteristic(List.of())
                .stomachSize(10)
                .health(100)
                .baseHealth(100)
                .damage(10)
                .magic(10)
                .workingForce(10)
                .build();

        village.addVillager(villager);

        testVillagerAttackException(null, 1);

        List<Creature> creatures = new ArrayList<>(List.of(CreatureType.GOBLIN.create(), CreatureType.GOBLIN.create(), CreatureType.GOBLIN.create()));

        testVillagerAttackException(creatures, 3);

        testVillagerAttackException(creatures, -1);

        testVillagerAttackException(creatures, 0);
    }

    private void testVillagerAttackException(List<Creature> creatures, int index) {
        assertThrows(IllegalArgumentException.class, () -> village.villagerAttack(creatures, index));
    }


    @Test
    void villageAttack(){
        List<Creature> creatures = new ArrayList<>(List.of(CreatureType.GOBLIN.create(), CreatureType.GOBLIN.create(), CreatureType.GOBLIN.create()));

        Villager villager = new Villager.Builder()
                .name("Ali")
                .surname("Gator")
                .age(25)
                .job(Job.WARRIOR)
                .characteristic(List.of())
                .stomachSize(10)
                .health(100)
                .baseHealth(100)
                .damage(10)
                .magic(10)
                .workingForce(10)
                .build();

        village.addVillager(villager);

        village.initNewAttack(creatures);


        testStepFightException(List.of(10, 10, 10), List.of(100, 100));

        // Creature attack
        village.fightStep();
        testStepFightException(List.of(10, 10, 10), List.of(100, 97));

        // Villager attack
        village.fightStep();
        testStepFightException(List.of(10, 10), List.of(100, 97));

        // Creature attack
        village.fightStep();
        testStepFightException(List.of(10, 10), List.of(100, 94));

        // Villager attack
        village.fightStep();
        testStepFightException(List.of(10), List.of(100, 94));

        // Creature attack
        village.fightStep();
        testStepFightException(List.of(10), List.of(100, 91));

        // Villager attack
        village.fightStep();
        testStepFightException(List.of(), List.of(100, 91));
    }

    private void testStepFightException(List<Integer> creatureHealthExpected, List<Integer> villagerHealthExpected) {
        assertEquals(creatureHealthExpected.size(), village.getListCreatures().size());
        assertEquals(villagerHealthExpected.size(), village.getVillagers().size());
        for (int i = 0; i < creatureHealthExpected.size(); i++) {
            assertEquals(creatureHealthExpected.get(i), village.getListCreatures().get(i).getHealth());
        }
        for (int i = 0; i < villagerHealthExpected.size(); i++) {
            assertEquals(villagerHealthExpected.get(i), village.getVillagers().get(i).getHealth());
        }
    }

    @Test
    void initNewAttackExceptions() {
        testInitNewAttackException(null);
        testInitNewAttackException(new ArrayList<>());
    }

    private void testInitNewAttackException(List<Creature> creatures) {
        assertThrows(IllegalArgumentException.class, () -> village.initNewAttack(creatures));
    }

    @Test
    void villageAttackStepResult(){
        List<Creature> creatures = new ArrayList<>(List.of(CreatureType.GOBLIN.create(), CreatureType.GOBLIN.create(), CreatureType.GOBLIN.create()));

        Villager villager = new Villager.Builder()
                .name("Ali")
                .surname("Gator")
                .age(25)
                .job(Job.WARRIOR)
                .characteristic(List.of())
                .stomachSize(10)
                .health(100)
                .baseHealth(100)
                .damage(10)
                .magic(10)
                .workingForce(10)
                .build();

        village.addVillager(villager);

        village.initNewAttack(creatures);

        SoldierAttackResult results = village.fightStep();

        assertEquals(creatures.getFirst(), results.getAttacker());
        assertEquals(1, results.getTargets().size());
        assertEquals(97, results.getTargets().getFirst().getHealth());
    }



}
