package org.acme.generators;

import io.quarkus.test.junit.QuarkusTest;
import org.acme.Village;
import org.acme.villagers.Job;
import org.acme.villagers.Villager;
import org.acme.villagers.characteristics.Characteristic;
import org.acme.villagers.characteristics.VillagerCharacteristic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.SecureRandom;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@QuarkusTest
class VillagersGeneratorTest {


    @BeforeEach
    void setUp(){
        SecureRandom random = mock(SecureRandom.class);
        when(random.nextGaussian()).thenReturn(1.0);
        when(random.nextFloat(0,1)).thenReturn(0.5F);
        VillagerGenerator.setRandom(random);
        NumbersGenerator.setRandom(random);

    }

    @Test
    void testGenerateVillagers(){
        String name = VillagerGenerator.generateName();
        assertEquals("Jenny", name);

        String surname = VillagerGenerator.generateSurname();
        assertEquals("Miller", surname);

        int age = VillagerGenerator.generateAge();
        assertEquals(44, age);

        int stomachSize = VillagerGenerator.generateStomachSize();
        assertEquals(22, stomachSize);

        int baseHealth = VillagerGenerator.generateBaseHealth();
        assertEquals(54, baseHealth);

        int damage = VillagerGenerator.generateDamage();
        assertEquals(10, damage);

        int magic = VillagerGenerator.generateMagic();
        assertEquals(23, magic);

        int workingForce = VillagerGenerator.generateWorkingForce();
        assertEquals(38, workingForce);

        Characteristic characteristic = VillagerGenerator.generateCharacteristic();
        assertEquals(VillagerCharacteristic.STRONG, characteristic);
    }

    @Test
    void testGenerateVillager(){
        Villager villager = VillagerGenerator.generateVillager();
        assertEquals("Jenny", villager.getName());
        assertEquals("Miller", villager.getSurname());
        assertEquals(44, villager.getAge());
        assertEquals(Job.UNEMPLOYED, villager.getJob());
        assertEquals(1, villager.getCharacteristic().size());
        assertEquals(VillagerCharacteristic.STRONG, villager.getCharacteristic().getFirst());
        assertEquals(22, villager.getStomachSize());
        assertEquals(54, villager.getBaseHealth());
        assertEquals(54, villager.getHealth());
        assertEquals(10, villager.getDamage());
        assertEquals(23, villager.getMagic());
        assertEquals(38, villager.getWorkingForce());
    }

    @Test
    void testGenerateVillager2(){
        Village village = new Village("Test");

        Villager villager = VillagerGenerator.newVillager(village);

        assertEquals("Jenny", villager.getName());
        assertEquals("Miller", villager.getSurname());
        assertEquals(44, villager.getAge());
        assertEquals(Job.UNEMPLOYED, villager.getJob());
        assertEquals(1, villager.getCharacteristic().size());
        assertEquals(VillagerCharacteristic.STRONG, villager.getCharacteristic().getFirst());
        assertEquals(22, villager.getStomachSize());
        assertEquals(54, villager.getBaseHealth());
        assertEquals(54, villager.getHealth());
        assertEquals(10, villager.getDamage());
        assertEquals(23, villager.getMagic());
        assertEquals(38, villager.getWorkingForce());
    }

    @Test
    void testVillagerOption(){
        VillagerOption villager =  new VillagerOption().setName().setSurname().setAge().setJob().setCharacteristic().setStomachSize().setHealth().setBaseHealth().setMagic().setDamage().setWorkingForce();
        assertTrue(villager.isName());
        assertTrue(villager.isSurname());
        assertTrue(villager.isAge());
        assertTrue(villager.isJob());
        assertTrue(villager.isCharacteristic());
        assertTrue(villager.isStomachSize());
        assertTrue(villager.isHealth());
        assertTrue(villager.isBaseHealth());
        assertTrue(villager.isMagic());
        assertTrue(villager.isDamage());
        assertTrue(villager.isWorkingForce());


        assertTrue(villager.isAll());
    }

}
