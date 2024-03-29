package org.acme.generators;

import org.acme.Village;
import org.acme.villagers.Job;
import org.acme.villagers.Villager;
import org.acme.villagers.characteristics.Characteristic;
import org.acme.villagers.characteristics.VillagerCharacteristic;

import java.security.SecureRandom;
import java.util.List;

public class VillagerGenerator {

    private static SecureRandom random = new SecureRandom();

    private VillagerGenerator(){
        throw new IllegalStateException("Utility class");
    }

    public static void setRandom(SecureRandom random){
        // This method is not used in the code
        // It is used for testing purposes
        VillagerGenerator.random = random;
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


    public static String generateName(){
        String[] names = {"John", "Jane", "Jack", "Jill", "James", "Jenny", "Jasper", "Jasmine", "Jared", "Jade"};
        return names[(int) (random.nextFloat(0,1) * names.length)];
    }

    public static String generateSurname(){
        String[] surnames = {"Doe", "Smith", "Johnson", "Brown", "Williams", "Miller", "Taylor", "Wilson", "Davis", "White"};
        return surnames[(int) (random.nextFloat(0,1) * surnames.length)];
    }

    public static int generateAge(){
        return NumbersGenerator.generateNormalDistributedNumber(40.0, 20.0).intValue();
    }


    public static Characteristic generateCharacteristic(){
        VillagerCharacteristic[] characteristics = VillagerCharacteristic.values();
        return characteristics[(int) (random.nextFloat(0,1) * characteristics.length)];
    }

    public static int generateStomachSize(){
        return NumbersGenerator.generateNormalDistributedNumber(20, 8).intValue();
    }

    public static int generateBaseHealth(){
        return NumbersGenerator.generateNormalDistributedNumber(50, 20).intValue();
    }

    public static int generateDamage(){
        return NumbersGenerator.generateNormalDistributedNumber(8, 5).intValue();
    }

    public static int generateMagic(){
        return NumbersGenerator.generateNormalDistributedNumber(20, 10).intValue();
    }

    public static int generateWorkingForce(){
        return NumbersGenerator.generateNormalDistributedNumber(35, 15).intValue();
    }




}
