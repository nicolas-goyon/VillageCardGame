package org.acme.generators;

import io.quarkus.test.junit.QuarkusTest;
import org.acme.*;
import org.acme.creatures.*;
import org.acme.villagers.*;
import org.acme.villagers.characteristics.VillagerCharacteristic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class EventGeneratorTest {

    private Village village;



    @BeforeEach
    void setUp(){
        village = new Village("TestVillage");
    }

    @Test
    void testNewVillage(){
        String result = EventGenerator.newVillage(village);
        assertNotNull(result);
        String expectedResult = "{\"eventType\": \"newVillage\",\"baseFood\": 100,\"baseGuy\": {\"name\": \"John\",\"surname\": \"Doe\",\"age\": 20,\"job\": \"UNEMPLOYED\",\"characteristic\": [],\"stomachSize\": 10,\"health\": 100,\"baseHealth\": 100,\"damage\": 10,\"magic\": 0,\"workingForce\": 20}}";
        assertEquals(expectedResult, result);
        assertTrue(result.contains("newVillage"));
    }

    @Test
    void testFoodProduced(){
        String result = EventGenerator.foodProduced(village);
        assertNotNull(result);
        assertTrue(result.contains("foodProduced"));
        assertTrue(result.contains("100"));
    }

    @Test
    void testFoodEaten(){
        String result = EventGenerator.foodEaten(village);
        String expectedResult = "{\"eventType\": \"foodEaten\",\"guys\": [{\"name\": \"John\",\"surname\": \"Doe\",\"health\": 100}],\"newAmount\": 100}";
        assertNotNull(result);
        assertEquals(expectedResult, result);
        assertTrue(result.contains("foodEaten"));

        // Remove the base villager
        village.removeVillager(village.getVillagers().getFirst().getName(), village.getVillagers().getFirst().getSurname());
        result = EventGenerator.foodEaten(village);
        expectedResult = "{\"eventType\": \"foodEaten\",\"guys\": [],\"newAmount\": 100}";
        assertNotNull(result);
        assertEquals(expectedResult, result);
    }

    @Test
    void testNewVillager(){
        Villager villager = new Villager.Builder()
                .name("Test")
                .surname("Guy")
                .age(20)
                .job(Job.UNEMPLOYED)
                .characteristic(List.of())
                .stomachSize(10)
                .health(100)
                .baseHealth(100)
                .damage(10)
                .magic(0)
                .workingForce(20)
                .build();
        String result = EventGenerator.newVillager(villager);
        assertNotNull(result);
        String expectedResult = "{\"eventType\": \"newVillager\",\"newGuy\": {\"name\": \"Test\",\"surname\": \"Guy\",\"age\": 20,\"job\": \"UNEMPLOYED\",\"characteristic\": [],\"stomachSize\": 10,\"health\": 100,\"baseHealth\": 100,\"damage\": 10,\"magic\": 0,\"workingForce\": 20}}";
        assertEquals(expectedResult, result);
        assertTrue(result.contains("newVillager"));
        assertTrue(result.contains("Test"));
        assertTrue(result.contains("Guy"));
    }

    @Test
    void testVillagerToJson(){
        Villager villager = new Villager.Builder()
                .name("Test")
                .surname("Guy")
                .age(20)
                .job(Job.UNEMPLOYED)
                .characteristic(List.of())
                .stomachSize(10)
                .health(100)
                .baseHealth(100)
                .damage(10)
                .magic(0)
                .workingForce(20)
                .build();

        String result = EventGenerator.villagerToJson(villager);
        assertNotNull(result);
        String expectedResult = "{\"name\": \"Test\",\"surname\": \"Guy\",\"age\": 20,\"job\": \"UNEMPLOYED\",\"characteristic\": [],\"stomachSize\": 10,\"health\": 100,\"baseHealth\": 100,\"damage\": 10,\"magic\": 0,\"workingForce\": 20}";
        assertEquals(expectedResult, result);

        // Villager with characteristics
        villager = new Villager.Builder()
                .name("Test")
                .surname("Guy")
                .age(20)
                .job(Job.UNEMPLOYED)
                .characteristic(List.of(VillagerCharacteristic.STRONG, VillagerCharacteristic.HARDWORKING))
                .stomachSize(10)
                .health(100)
                .baseHealth(100)
                .damage(10)
                .magic(0)
                .workingForce(20)
                .build();

        result = EventGenerator.villagerToJson(villager);
        assertNotNull(result);
        expectedResult = "{\"name\": \"Test\",\"surname\": \"Guy\",\"age\": 20,\"job\": \"UNEMPLOYED\",\"characteristic\": [\"STRONG\",\"HARDWORKING\"],\"stomachSize\": 10,\"health\": 100,\"baseHealth\": 100,\"damage\": 10,\"magic\": 0,\"workingForce\": 20}";
        assertEquals(expectedResult, result);
    }

    @Test
    void testVillagerToJsonConstrained(){
        Villager villager = new Villager.Builder()
                .name("Test")
                .surname("Guy")
                .age(20)
                .job(Job.UNEMPLOYED)
                .characteristic(List.of())
                .stomachSize(10)
                .health(100)
                .baseHealth(100)
                .damage(10)
                .magic(0)
                .workingForce(20)
                .build();

        String result = EventGenerator.villagerToJsonConstrained(villager, (new VillagerOption()).setAll());
        assertNotNull(result);
        String expectedResult = "{\"name\": \"Test\",\"surname\": \"Guy\",\"age\": 20,\"job\": \"UNEMPLOYED\",\"characteristic\": [],\"stomachSize\": 10,\"health\": 100,\"baseHealth\": 100,\"damage\": 10,\"magic\": 0,\"workingForce\": 20}";
        assertEquals(expectedResult, result);

        result = EventGenerator.villagerToJsonConstrained(villager, (new VillagerOption()));
        assertNotNull(result);
        expectedResult = "{}";
        assertEquals(expectedResult, result);

    }

    @Test
    void testCreaturesToJson(){
        List<Creature> creatures = List.of(CreatureType.GOBLIN.create(), CreatureType.GOBLIN.create(), CreatureType.GOBLIN.create());

        String result = EventGenerator.creaturesToJson(creatures);
        assertNotNull(result);
        String expectedResult = "[{\"name\": \"Goblin\",\"health\": 10,\"damage\": 3},{\"name\": \"Goblin\",\"health\": 10,\"damage\": 3},{\"name\": \"Goblin\",\"health\": 10,\"damage\": 3}]";
        assertEquals(expectedResult, result);

        // Empty list
        creatures = List.of();
        result = EventGenerator.creaturesToJson(creatures);
        assertNotNull(result);
        expectedResult = "[]";
        assertEquals(expectedResult, result);
    }

    @Test
    void testFightStep(){
        List<Creature> creatures = List.of(CreatureType.GOBLIN.create(), CreatureType.GOBLIN.create(), CreatureType.GOBLIN.create());

        // Change Villager's job
        village.changeVillagerJob(village.getVillagers().getFirst().getName(), village.getVillagers().getFirst().getSurname(), Job.WARRIOR);

        village.initNewAttack(creatures);

        SoldierAttackResult result = village.fightStep();
        String event = EventGenerator.fightStep(result);
        assertNotNull(event);
        String expectedResult = "{\"eventType\": \"attackDamage\",\"guys\": [{\"targetType\": \"villager\",\"name\": \"John\",\"surname\": \"Doe\",\"newHealth\": 97}]}";
        assertEquals(expectedResult, event);


        // Villager's turn
        result = village.fightStep();
        event = EventGenerator.fightStep(result);
        assertNotNull(event);
        expectedResult = "{\"eventType\": \"attackDamage\",\"guys\": [{\"targetType\": \"creature\",\"name\": \"Goblin\",\"newHealth\": 0}]}";
        assertEquals(expectedResult, event);

        // Test targets empty
        result = new SoldierAttackResult(creatures.getFirst());
        event = EventGenerator.fightStep(result);
        assertNotNull(event);
        expectedResult = "{\"eventType\": \"attackDamage\",\"guys\": []}";
        assertEquals(expectedResult, event);
    }


}
