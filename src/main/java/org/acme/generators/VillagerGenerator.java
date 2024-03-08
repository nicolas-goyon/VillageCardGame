package org.acme.generators;

import org.acme.Village;
import org.acme.villagers.Job;
import org.acme.villagers.Villager;
import org.acme.villagers.characteristics.Characteristic;
import org.acme.villagers.characteristics.VillagerCharacteristic;

import java.util.List;
import java.util.stream.Collector;

public class VillagerGenerator {

    public VillagerGenerator(){
        throw new IllegalStateException("Utility class");
    }

    public static Villager newVillager(Village village){
        Villager villager = generateVillager();
        boolean isVillagerAvailable = false;
        do {
            // Name check
            isVillagerAvailable = village.getVillagers().stream().filter(v -> v.getName().equals(villager.getName()) && v.getSurname().equals(villager.getSurname())).count() == 0;
        }while (!isVillagerAvailable);


        return villager;
    }

    public static Villager generateVillager(){
        return new Villager.Builder()
                .name(generateName())
                .surname(generateSurname())
                .age(generateAge())
                .job(Job.UNEMPLOYED)
                .characteristic(List.of(generateCharacteristic()))
                .stomachSize(generateStomachSize())
                .baseHealth(generateBaseHealth())
                .health(generateBaseHealth())
                .damage(generateDamage())
                .magic(generateMagic())
                .workingForce(generateWorkingForce())
                .build();
    }


    private static String generateName(){
        String[] names = {"John", "Jane", "Jack", "Jill", "James", "Jenny", "Jasper", "Jasmine", "Jared", "Jade"};
        return names[(int) (Math.random() * names.length)];
    }

    private static String generateSurname(){
        String[] surnames = {"Doe", "Smith", "Johnson", "Brown", "Williams", "Miller", "Taylor", "Wilson", "Davis", "White"};
        return surnames[(int) (Math.random() * surnames.length)];
    }

    private static int generateAge(){
        return NumbersGenerator.generateNormalDistributedNumber(40.0, 20.0).intValue();
    }


    private static Characteristic generateCharacteristic(){
        VillagerCharacteristic[] characteristics = VillagerCharacteristic.values();
        return characteristics[(int) (Math.random() * characteristics.length)];
    }

    private static int generateStomachSize(){
        return NumbersGenerator.generateNormalDistributedNumber(20, 8).intValue();
    }

    private static int generateBaseHealth(){
        return NumbersGenerator.generateNormalDistributedNumber(50, 20).intValue();
    }

    private static int generateDamage(){
        return NumbersGenerator.generateNormalDistributedNumber(8, 5).intValue();
    }

    private static int generateMagic(){
        return NumbersGenerator.generateNormalDistributedNumber(20, 10).intValue();
    }

    private static int generateWorkingForce(){
        return NumbersGenerator.generateNormalDistributedNumber(35, 15).intValue();
    }




}
