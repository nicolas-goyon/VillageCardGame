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
class SynchronizedVillageTest {
    private SynchronizedVillage village;

    @BeforeEach
    void setUp() {
        village = new SynchronizedVillage("TestVillage");

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

        village.initNewAttack(creatures);

        village.creatureAttack(creatures.getFirst());
        assertEquals(3, village.getListCreatures().size());
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


        assertEquals(3, village.getListCreatures().size());
        assertEquals(2, village.getVillagers().size());
        assertEquals(97, village.getVillagers().get(1).getHealth()); // Warrior has 3 damage
        assertEquals(100, village.getVillagers().getFirst().getHealth()); // Non warrior has 0 damage

        assertEquals(3, village.getListCreatures().size());
        assertEquals(10, village.getListCreatures().getFirst().getHealth());
        assertEquals(10, village.getListCreatures().get(1).getHealth());
        assertEquals(10, village.getListCreatures().get(2).getHealth());
        village.villagerAttack(1);
        assertEquals(2, village.getListCreatures().size());
        assertEquals(2, village.getVillagers().size());
        assertEquals(10, village.getListCreatures().getFirst().getHealth());
        assertEquals(10, village.getListCreatures().get(1).getHealth());

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

        testVillagerAttackException(1);

        List<Creature> creatures = new ArrayList<>(List.of(CreatureType.GOBLIN.create(), CreatureType.GOBLIN.create(), CreatureType.GOBLIN.create()));


        village.initNewAttack(creatures);

        testVillagerAttackException(3);

        testVillagerAttackException(-1);

        testVillagerAttackException(0);
    }

    private void testVillagerAttackException(int index) {
        assertThrows(IllegalArgumentException.class, () -> village.villagerAttack(index));
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

    @Test
    void testFightOver(){
        List<Creature> creatures = new ArrayList<>(List.of(CreatureType.GOBLIN.create(), CreatureType.GOBLIN.create()));

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

        assertFalse(village.isFightOver());

        village.fightStep(); // Creature attack
        assertFalse(village.isFightOver());

        village.fightStep(); // Villager attack
        assertFalse(village.isFightOver());

        village.fightStep(); // Creature attack
        assertFalse(village.isFightOver());

        village.fightStep(); // Villager attack
        assertTrue(village.isFightOver());
    }

    @Test
    void testVillagerTurn(){
        List<Creature> creatures = new ArrayList<>(List.of(CreatureType.GOBLIN.create(), CreatureType.GOBLIN.create()));

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

        SoldierAttackResult results = village.villagerTurn();

        assertEquals(Villager.class, results.getAttacker().getClass());
        assertEquals(1, results.getTargets().size());
    }

    @Test
    void testCreatureTurn() {
        List<Creature> creatures = new ArrayList<>(List.of(CreatureType.GOBLIN.create(), CreatureType.GOBLIN.create()));

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

        SoldierAttackResult results = village.creatureTurn();

        assertEquals(Creature.class, results.getAttacker().getClass());
        assertEquals(1, results.getTargets().size());
        assertEquals(97, results.getTargets().getFirst().getHealth());
    }




    @Test
    void testChangeVillagerJob(){
        List<Creature> creatures = new ArrayList<>(List.of(CreatureType.GOBLIN.create(), CreatureType.GOBLIN.create()));

        village.initNewAttack(creatures);
        SoldierAttackResult results = village.fightStep(); // Creature attack

        assertEquals(creatures.getFirst().getClass(), results.getAttacker().getClass());
        assertEquals(1, results.getTargets().size());
        assertEquals(97, results.getTargets().getFirst().getHealth());

        SoldierAttackResult results2 = village.fightStep(); // Still creature attack because no warrior

        assertEquals(creatures.getFirst().getClass(), results2.getAttacker().getClass());
        assertEquals(1, results2.getTargets().size());
        assertEquals(94, results2.getTargets().getFirst().getHealth());

        village.changeVillagerJob(village.getVillagers().getFirst().getName(), village.getVillagers().getFirst().getSurname(), Job.WARRIOR);

        results = village.fightStep();

        assertEquals(Villager.class, results.getAttacker().getClass());
        assertEquals(1, results.getTargets().size());
        assertEquals(0, results.getTargets().getFirst().getHealth());

        assertEquals(1, village.getListCreatures().size());
        assertEquals(10, village.getListCreatures().getFirst().getHealth());

    }

    @Test
    void testFightStepExceptions() {
        assertThrows(IllegalStateException.class, () -> village.fightStep());

        List<Creature> creatures = new ArrayList<>(List.of(CreatureType.GOBLIN.create(), CreatureType.GOBLIN.create()));

        village.initNewAttack(creatures);

        village.setCreatureIndex(creatures.size());

        assertThrows(IllegalStateException.class, () -> village.fightStep());

        village.setCreatureIndex(0);

        village.setVillagerIndex(village.getVillagers().size());

        assertThrows(IllegalStateException.class, () -> village.fightStep());
    }

    @Test
    void testHealVillagers(){
        Villager villager = new Villager.Builder()
                .name("Ali")
                .surname("Gator")
                .age(25)
                .job(Job.HEALER)
                .characteristic(List.of())
                .stomachSize(10)
                .health(50)
                .baseHealth(100)
                .damage(10)
                .magic(10)
                .workingForce(10)
                .build();

        village.addVillager(villager);

        List<Villager> villagers = village.getVillagers();

        villagers.getFirst().setHealth(50);

        village.healVillagers();

        assertEquals(60, villagers.getFirst().getHealth());

    }


}
