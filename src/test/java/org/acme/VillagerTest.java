package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class VillagerTest {

    private Villager villager;

    @BeforeEach
    void setUp() {
        villager = new Villager.VillagerBuilder()
                .name("John")
                .surname("Doe")
                .age(25)
                .job("Farmer")
                .characteristic(List.of("Strong", "Hardworking"))
                .stomachSize(10)
                .health(100)
                .baseHealth(100)
                .damage(10)
                .magic(10)
                .build();
    }

    @Test
    void testVillager() {
        assertEquals("John", villager.getName());
        assertEquals("Doe", villager.getSurname());
        assertEquals(25, villager.getAge());
        assertEquals("Farmer", villager.getJob());
        assertEquals(List.of("Strong", "Hardworking"), villager.getCharacteristic());
        assertEquals(10, villager.getStomachSize());
        assertEquals(100, villager.getHealth());
        assertEquals(100, villager.getBaseHealth());
    }

    @Test
    void testEat() {
        villager.setHealth(50);
        villager.eat(10);
        assertEquals(100, villager.getHealth());

        villager.setHealth(50);
        villager.eat(100);
        assertEquals(100, villager.getHealth());

        villager.setHealth(50);
        villager.eat(200);
        assertEquals(100, villager.getHealth());

        villager.setHealth(50);
        villager.eat(0);
        assertEquals(50, villager.getHealth());

        villager.setHealth(50);
        villager.eat(-10);
        assertEquals(50, villager.getHealth());
    }

    @Test
    void testEatReturns(){
        assertEquals(0, villager.eat(0));
        assertEquals(5, villager.eat(5));
        assertEquals(0, villager.eat(10));
        assertEquals(90, villager.eat(100));
        assertEquals(190, villager.eat(200));
        assertEquals(0, villager.eat(-10));
    }

    @Test
    void testBuilderExceptions() {
        assertThrows(IllegalStateException.class, () -> new Villager.VillagerBuilder().build());
        assertThrows(IllegalStateException.class, () -> new Villager.VillagerBuilder().name("John").build());
        assertThrows(IllegalStateException.class, () -> new Villager.VillagerBuilder().name("John").surname("Doe").build());
        assertThrows(IllegalStateException.class, () -> new Villager.VillagerBuilder().name("John").surname("Doe").age(25).build());
        assertThrows(IllegalStateException.class, () -> new Villager.VillagerBuilder().name("John").surname("Doe").age(25).job("Farmer").build());
        assertThrows(IllegalStateException.class, () -> new Villager.VillagerBuilder().name("John").surname("Doe").age(25).job("Farmer").characteristic(List.of("Strong", "Hardworking")).build());
        assertThrows(IllegalStateException.class, () -> new Villager.VillagerBuilder().name("John").surname("Doe").age(25).job("Farmer").characteristic(List.of("Strong", "Hardworking")).stomachSize(10).build());
        assertThrows(IllegalStateException.class, () -> new Villager.VillagerBuilder().name("John").surname("Doe").age(25).job("Farmer").characteristic(List.of("Strong", "Hardworking")).stomachSize(10).health(100).build());
        assertThrows(IllegalStateException.class, () -> new Villager.VillagerBuilder().name("John").surname("Doe").age(25).job("Farmer").characteristic(List.of("Strong", "Hardworking")).stomachSize(10).health(100).baseHealth(100).build());
    }

    @Test
    void testSetters(){
        villager.setName("Jane");
        assertEquals("Jane", villager.getName());
        villager.setSurname("Smith");
        assertEquals("Smith", villager.getSurname());
        villager.setAge(30);
        assertEquals(30, villager.getAge());
        villager.setJob("Blacksmith");
        assertEquals("Blacksmith", villager.getJob());
        villager.setCharacteristic(List.of("Strong", "Hardworking", "Smart"));
        assertEquals(List.of("Strong", "Hardworking", "Smart"), villager.getCharacteristic());
        villager.setStomachSize(20);
        assertEquals(20, villager.getStomachSize());
        villager.setHealth(200);
        assertEquals(200, villager.getHealth());
        villager.setBaseHealth(200);
        assertEquals(200, villager.getBaseHealth());
        villager.setDamage(20);
        assertEquals(20, villager.getDamage());
        villager.setMagic(20);
        assertEquals(20, villager.getMagic());
    }

    @Test
    void  takeDamage(){
        villager.takeDamage(10);
        assertEquals(90, villager.getHealth());
        villager.takeDamage(100);
        assertEquals(0, villager.getHealth());
        villager.takeDamage(100);
        assertEquals(0, villager.getHealth());
        assertThrows(IllegalArgumentException.class, () -> villager.takeDamage(-10));
    }
}