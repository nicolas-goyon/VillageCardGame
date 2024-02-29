package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import org.acme.villagers.Job;
import org.acme.villagers.Villager;
import org.acme.villagers.characteristics.VillagerCharacteristic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
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
                .characteristic(List.of(VillagerCharacteristic.STRONG, VillagerCharacteristic.HARDWORKING))
                .stomachSize(10)
                .health(100)
                .baseHealth(100)
                .damage(10)
                .magic(10)
                .workingForce(10)
                .build();
    }

    @Test
    void testVillager() {
        assertEquals("John", villager.getName());
        assertEquals("Doe", villager.getSurname());
        assertEquals(25, villager.getAge());
        assertEquals(Job.FARMER, villager.getJob());
        assertEquals(List.of(VillagerCharacteristic.STRONG, VillagerCharacteristic.HARDWORKING), villager.getCharacteristic());
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
        testBuilderException(builder -> builder.name("John").surname("Doe").age(25).job(Job.FARMER).characteristic(List.of(VillagerCharacteristic.STRONG, VillagerCharacteristic.HARDWORKING)));
        testBuilderException(builder -> builder.name("John").surname("Doe").age(25).job(Job.FARMER).characteristic(List.of(VillagerCharacteristic.STRONG, VillagerCharacteristic.HARDWORKING)).stomachSize(10));
        testBuilderException(builder -> builder.name("John").surname("Doe").age(25).job(Job.FARMER).characteristic(List.of(VillagerCharacteristic.STRONG, VillagerCharacteristic.HARDWORKING)).stomachSize(10).health(100));
        testBuilderException(builder -> builder.name("John").surname("Doe").age(25).job(Job.FARMER).characteristic(List.of(VillagerCharacteristic.STRONG, VillagerCharacteristic.HARDWORKING)).stomachSize(10).health(100).baseHealth(100));
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
        villager.setCharacteristic(List.of(VillagerCharacteristic.STRONG, VillagerCharacteristic.HARDWORKING, VillagerCharacteristic.SMART));
        assertEquals(List.of(VillagerCharacteristic.STRONG, VillagerCharacteristic.HARDWORKING, VillagerCharacteristic.SMART), villager.getCharacteristic());
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

    @Test
    void characteristicEnumTest(){
        assertEquals("Small stomach", VillagerCharacteristic.SMALL_STOMACH.getName());
        assertEquals("Big stomach", VillagerCharacteristic.BIG_STOMACH.getName());

        assertEquals("Weak", VillagerCharacteristic.WEAK.getName());
        assertEquals("Strong", VillagerCharacteristic.STRONG.getName());

        assertEquals("Thin skin", VillagerCharacteristic.THIN_SKIN.getName());
        assertEquals("Healthy", VillagerCharacteristic.HEALTHY.getName());

        assertEquals("Smart", VillagerCharacteristic.SMART.getName());
        assertEquals("Dumb", VillagerCharacteristic.DUMB.getName());

        assertEquals("Hardworking", VillagerCharacteristic.HARDWORKING.getName());
        assertEquals("Lazybones", VillagerCharacteristic.LAZYBONES.getName());
    }

    @Test
    void characteristicApply(){
        Villager villager = new Villager.Builder()
                .name("John")
                .surname("Doe")
                .age(25)
                .job(Job.FARMER)
                .characteristic(List.of(
                        VillagerCharacteristic.STRONG,
                        VillagerCharacteristic.HARDWORKING,
                        VillagerCharacteristic.HEALTHY,
                        VillagerCharacteristic.SMART,
                        VillagerCharacteristic.BIG_STOMACH
                ))
                .stomachSize(10)
                .health(100)
                .baseHealth(100)
                .damage(10)
                .magic(10)
                .workingForce(10)
                .build();

        villager.applyCharacteristics();

        assertEquals(12, villager.getDamage());
        assertEquals(12, villager.getWorkingForce());
        assertEquals(120, villager.getBaseHealth());
        assertEquals(120, villager.getHealth());
        assertEquals(12, villager.getMagic());
        assertEquals(12, villager.getStomachSize());


        villager = new Villager.Builder()
                .name("John")
                .surname("Doe")
                .age(25)
                .job(Job.FARMER)
                .characteristic(List.of(
                        VillagerCharacteristic.WEAK,
                        VillagerCharacteristic.LAZYBONES,
                        VillagerCharacteristic.THIN_SKIN,
                        VillagerCharacteristic.DUMB,
                        VillagerCharacteristic.SMALL_STOMACH
                ))
                .stomachSize(10)
                .health(100)
                .baseHealth(100)
                .damage(10)
                .magic(10)
                .workingForce(10)
                .build();

        villager.applyCharacteristics();

        assertEquals(8, villager.getDamage());
        assertEquals(8, villager.getWorkingForce());
        assertEquals(80, villager.getBaseHealth());
        assertEquals(80, villager.getHealth());
        assertEquals(8, villager.getMagic());
        assertEquals(8, villager.getStomachSize());


        villager = new Villager.Builder()
                .name("John")
                .surname("Doe")
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

        villager.applyCharacteristics();

        assertEquals(10, villager.getDamage());
        assertEquals(10, villager.getWorkingForce());
        assertEquals(100, villager.getBaseHealth());
        assertEquals(100, villager.getHealth());
        assertEquals(10, villager.getMagic());
    }

    @Test
    void produceFood(){
        assertEquals(10, villager.produceFood());

        Villager villager = new Villager.Builder()
                .name("John")
                .surname("Doe")
                .age(25)
                .job(Job.FARMER)
                .characteristic(List.of(VillagerCharacteristic.HARDWORKING))
                .stomachSize(10)
                .health(100)
                .baseHealth(100)
                .damage(10)
                .magic(10)
                .workingForce(10)
                .build();

        villager.applyCharacteristics();

        assertEquals(12, villager.produceFood());


        villager = new Villager.Builder()
                .name("John")
                .surname("Doe")
                .age(25)
                .job(Job.UNEMPLOYED)
                .characteristic(List.of(VillagerCharacteristic.LAZYBONES))
                .stomachSize(10)
                .health(100)
                .baseHealth(100)
                .damage(10)
                .magic(10)
                .workingForce(10)
                .build();

        villager.applyCharacteristics();

        assertThrows(IllegalStateException.class, villager::produceFood);
    }


    @Test
    void healing(){
        Villager healer = new Villager.Builder()
                .name("John")
                .surname("Doe")
                .age(25)
                .job(Job.HEALER)
                .characteristic(List.of(VillagerCharacteristic.SMART))
                .stomachSize(10)
                .health(100)
                .baseHealth(100)
                .damage(10)
                .magic(150)
                .workingForce(10)
                .build();

        List<Villager> villagers = new ArrayList<>();
        villagers.add(
                new Villager.Builder()
                        .name("John")
                        .surname("Doe")
                        .age(25)
                        .job(Job.FARMER)
                        .characteristic(List.of(VillagerCharacteristic.HARDWORKING))
                        .stomachSize(10)
                        .health(100)
                        .baseHealth(100)
                        .damage(10)
                        .magic(10)
                        .workingForce(10)
                        .build()
        );

        villagers.add(
                new Villager.Builder()
                        .name("John")
                        .surname("Doe")
                        .age(25)
                        .job(Job.FARMER)
                        .characteristic(List.of(VillagerCharacteristic.HARDWORKING))
                        .stomachSize(10)
                        .health(100)
                        .baseHealth(100)
                        .damage(10)
                        .magic(10)
                        .workingForce(10)
                        .build()
        );

        villagers.getFirst().takeDamage(50);

        healer.heal(villagers);

        assertEquals(100, villagers.getFirst().getHealth());

        villagers.getFirst().takeDamage(50);
        villagers.getLast().takeDamage(50);

        healer.heal(villagers);

        assertEquals(100, villagers.getFirst().getHealth());
        assertEquals(100, villagers.getLast().getHealth());

        healer.setMagic(70);

        villagers.getFirst().takeDamage(50);
        villagers.getLast().takeDamage(50);

        healer.heal(villagers);

        assertEquals(100, villagers.getFirst().getHealth());
        assertEquals(70, villagers.getLast().getHealth());


        Villager notHealer = new Villager.Builder()
                .name("John")
                .surname("Doe")
                .age(25)
                .job(Job.FARMER)
                .characteristic(List.of(VillagerCharacteristic.SMART))
                .stomachSize(10)
                .health(100)
                .baseHealth(100)
                .damage(10)
                .magic(150)
                .workingForce(10)
                .build();

        assertThrows(IllegalStateException.class, () -> notHealer.heal(villagers));

        villagers.add(notHealer);

        healer.setMagic(10);

        villagers.getFirst().takeDamage(50);

        healer.heal(villagers);

        assertEquals(60, villagers.getFirst().getHealth());


        assertEquals(0, healer.heal(0));
    }
}
