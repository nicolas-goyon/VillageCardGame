package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import org.acme.villagers.Job;
import org.acme.villagers.Villager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class VillagerTest {

    private Villager villager;

    @BeforeEach
    void setUp() {
        villager = new Villager.Builder()
                .name("John")
                .surname("Doe")
                .age(25)
                .job(Job.FARMER)
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
        assertEquals(Job.FARMER, villager.getJob());
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
        testBuilderException(builder -> {});
        testBuilderException(builder -> builder.name("John"));
        testBuilderException(builder -> builder.name("John").surname("Doe"));
        testBuilderException(builder -> builder.name("John").surname("Doe").age(25));
        testBuilderException(builder -> builder.name("John").surname("Doe").age(25).job(Job.FARMER));
        testBuilderException(builder -> builder.name("John").surname("Doe").age(25).job(Job.FARMER).characteristic(List.of("Strong", "Hardworking")));
        testBuilderException(builder -> builder.name("John").surname("Doe").age(25).job(Job.FARMER).characteristic(List.of("Strong", "Hardworking")).stomachSize(10));
        testBuilderException(builder -> builder.name("John").surname("Doe").age(25).job(Job.FARMER).characteristic(List.of("Strong", "Hardworking")).stomachSize(10).health(100));
        testBuilderException(builder -> builder.name("John").surname("Doe").age(25).job(Job.FARMER).characteristic(List.of("Strong", "Hardworking")).stomachSize(10).health(100).baseHealth(100));
    }

    private void testBuilderException(Consumer<Villager.Builder> builderConsumer) {
        Villager.Builder builder = new Villager.Builder();
        builderConsumer.accept(builder);
        assertThrows(IllegalStateException.class, builder::build);
    }

    @Test
    void testSetters(){
        villager.setName("Jane");
        assertEquals("Jane", villager.getName());
        villager.setSurname("Smith");
        assertEquals("Smith", villager.getSurname());
        villager.setAge(30);
        assertEquals(30, villager.getAge());
        villager.setJob(Job.WARRIOR);
        assertEquals(Job.WARRIOR, villager.getJob());
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
        assertEquals(0, villager.takeDamage(10));
        assertEquals(90, villager.getHealth());
        assertEquals(10, villager.takeDamage(100));
        assertEquals(0, villager.getHealth());
        assertEquals(10, villager.takeDamage(10));
        assertEquals(0, villager.getHealth());

        assertThrows(IllegalArgumentException.class, () -> villager.takeDamage(-10));
    }

    @Test
    void jobEnumTest(){
        assertEquals("Farmer", Job.FARMER.getName());
        assertEquals("Warrior", Job.WARRIOR.getName());
        assertEquals("Healer", Job.HEALER.getName());
        assertEquals("Unemployed", Job.UNEMPLOYED.getName());

    }
}
