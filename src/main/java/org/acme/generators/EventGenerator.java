package org.acme.generators;

import org.acme.SoldierAttackResult;
import org.acme.Village;
import org.acme.creatures.Creature;
import org.acme.interfaces.Soldier;
import org.acme.villagers.Villager;
import org.acme.villagers.characteristics.Characteristic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventGenerator {

    // DATA CONSTANTS
    public static final String NAME = "name";
    public static final String SURNAME = "surname";
    public static final String AGE = "age";
    public static final String JOB = "job";
    public static final String CHARACTERISTIC = "characteristic";
    public static final String STOMACH_SIZE = "stomachSize";
    public static final String HEALTH = "health";
    public static final String BASE_HEALTH = "baseHealth";
    public static final String DAMAGE = "damage";
    public static final String MAGIC = "magic";
    public static final String WORKING_FORCE = "workingForce";
    public static final String CREATURES = "creatures";

    public static final String GUYS = "guys";
    public static final String BASE_GUY = "baseGuy";
    public static final String BASE_FOOD = "baseFood";
    public static final String NEW_AMOUNT = "newAmount";
    public static final String NEW_HEALTH = "newHealth";


    public static final String EMPTY_ARRAY = "[]";
    public static final String QUOTE = "\"";


    private EventGenerator(){
        throw new IllegalStateException("Utility class");
    }

    // General events

    public static String newVillage(Village village){
        Map<String, String> data = new HashMap<>();
        data.put(BASE_GUY, villagerToJson(village.getVillagers().getFirst()));
        data.put(BASE_FOOD, String.valueOf(village.getFoodSupplies()));
        return EventGenerator.newEvent("newVillage", data);
    }

    public static String foodProduced(Village village){
        Map<String, String> data = new HashMap<>();
        data.put(NEW_AMOUNT, String.valueOf(village.getFoodSupplies()));
        return EventGenerator.newEvent("foodProduced", data);
    }

    public static String foodEaten(Village village) {
        Map<String, String> data = new HashMap<>();
        data.put(NEW_AMOUNT, String.valueOf(village.getFoodSupplies()));
        List<Villager> guys = village.getVillagers();
        StringBuilder guysData = new StringBuilder();
        if (guys.isEmpty()) {
            data.put(GUYS, EMPTY_ARRAY);
            return EventGenerator.newEvent("foodEaten", data);
        }

        guysData.append("[");
        for (Villager guy : guys) {
            String guyData = villagerToJsonConstrained(guy, (new VillagerOption()).setName().setSurname().setHealth());
            guysData.append(guyData).append(",");
        }
        guysData.deleteCharAt(guysData.length() - 1);
        guysData.append("]");

        data.put(GUYS, guysData.toString());
        return EventGenerator.newEvent("foodEaten", data);
    }


    public static String newVillager(Villager newOne) {
        Map<String, String> data = new HashMap<>();
        data.put("newGuy", villagerToJson(newOne));
        return EventGenerator.newEvent("newVillager", data);
    }















    // utility methods

    public static String villagerToJson(Villager villager){
        return "{" +
                QUOTE + NAME + QUOTE + ": " + QUOTE + villager.getName() + QUOTE + "," +
                QUOTE + SURNAME + QUOTE + ": " + QUOTE + villager.getSurname() + QUOTE + "," +
                QUOTE + AGE + QUOTE + ": " + villager.getAge() + "," +
                QUOTE + JOB + QUOTE + ": " + QUOTE + villager.getJob() + QUOTE + "," +
                QUOTE + CHARACTERISTIC + QUOTE + ": " + characteristicToJson(villager.getCharacteristic()) + "," +
                QUOTE + STOMACH_SIZE + QUOTE + ": " + villager.getStomachSize() + "," +
                QUOTE + HEALTH + QUOTE + ": " + villager.getHealth() + "," +
                QUOTE + BASE_HEALTH + QUOTE + ": " + villager.getBaseHealth() + "," +
                QUOTE + DAMAGE + QUOTE + ": " + villager.getDamage() + "," +
                QUOTE + MAGIC + QUOTE + ": " + villager.getMagic() + "," +
                QUOTE + WORKING_FORCE + QUOTE + ": " + villager.getWorkingForce() +
                "}";
    }


    public static String newEvent(String eventName, Map<String, String> data) {
        StringBuilder json = new StringBuilder();
        json.append("{");
        appendJson(json, "eventType", eventName);
        data.forEach((key, value) -> json.append(QUOTE).append(key).append(QUOTE + ": ").append(value).append(","));
        if (!data.isEmpty()) {
            json.deleteCharAt(json.length() - 1);
        }
        json.append("}");
        return json.toString();
    }

    public static String villagerToJsonConstrained(Villager villager, VillagerOption options){
        StringBuilder json = new StringBuilder();
        json.append("{");
        if (options.isName()) {
            appendJson(json, NAME, villager.getName());
        }
        if (options.isSurname()) {
            appendJson(json, SURNAME, villager.getSurname());
        }
        if (options.isAge()) {
            appendJson(json, AGE, villager.getAge());
        }
        if (options.isJob()) {
            appendJson(json, JOB, villager.getJob().toString());
        }
        if (options.isCharacteristic()) {
            json.append(QUOTE).append(CHARACTERISTIC).append(QUOTE + ": ").append(characteristicToJson(villager.getCharacteristic())).append(",");
        }
        if (options.isStomachSize()) {
            appendJson(json, STOMACH_SIZE, villager.getStomachSize());
        }
        if (options.isHealth()) {
            appendJson(json, HEALTH, villager.getHealth());
        }
        if (options.isBaseHealth()) {
            appendJson(json, BASE_HEALTH, villager.getBaseHealth());
        }
        if (options.isDamage()) {
            appendJson(json, DAMAGE, villager.getDamage());
        }
        if (options.isMagic()) {
            appendJson(json, MAGIC, villager.getMagic());
        }
        if (options.isWorkingForce()) {
            appendJson(json, WORKING_FORCE, villager.getWorkingForce());
        }

        if (json.charAt(json.length() - 1) == ',') {
            json.deleteCharAt(json.length() - 1);
        }

        json.append("}");
        return json.toString();
    }

    public static String characteristicToJson(List<Characteristic> characteristics) {
        if (characteristics.isEmpty()) {
            return EMPTY_ARRAY;
        }

        StringBuilder json = new StringBuilder();
        json.append("[");
        for (Characteristic characteristic : characteristics) {
            appendJsonArray(json, characteristic.toString());
        }
        json.deleteCharAt(json.length() - 1);
        json.append("]");
        return json.toString();
    }

    public static String newAttack(List<Creature> creatures) {
        Map<String, String> data = new HashMap<>();
        data.put(CREATURES, creaturesToJson(creatures));
        return EventGenerator.newEvent("newAttack", data);
    }

    public static String creaturesToJson(List<Creature> creatures) {
        if (creatures.isEmpty()) {
            return EMPTY_ARRAY;
        }
        StringBuilder json = new StringBuilder();
        json.append("[");
        for (Creature creature : creatures) {
            json.append("{");
            appendJson(json, NAME, creature.getName());
            appendJson(json, HEALTH, creature.getHealth());
            appendJson(json, DAMAGE, creature.getDamage(), true);
            json.append("},");
        }
        json.deleteCharAt(json.length() - 1);
        json.append("]");
        return json.toString();
    }

    public static String fightStep(SoldierAttackResult result) {
        Map<String, String> data = new HashMap<>();



        if (result.getTargets().isEmpty()) {
            data.put(GUYS , EMPTY_ARRAY);
            return EventGenerator.newEvent("attackDamage", data);
        }

        String targetType = result.getTargets().getFirst() instanceof Villager ? "villager" : "creature";

        StringBuilder guys = new StringBuilder();
        guys.append("[");
        for (Soldier soldier : result.getTargets()) {
            if (targetType.equals("villager")) {
                Villager villager = (Villager) soldier;
                guys.append("{");
                appendJson(guys, "targetType", targetType);
                appendJson(guys, NAME, villager.getName());
                appendJson(guys, SURNAME, villager.getSurname());
                appendJson(guys, NEW_HEALTH, villager.getHealth(), true);
                guys.append("},");
            } else {
                Creature creature = (Creature) soldier;
                guys.append("{");
                appendJson(guys, "targetType", targetType);
                appendJson(guys, NAME, creature.getName());
                appendJson(guys, NEW_HEALTH, creature.getHealth(), true);
                guys.append("},");
            }

        }
        guys.deleteCharAt(guys.length() - 1);
        guys.append("]");
        data.put(GUYS, guys.toString());
        return EventGenerator.newEvent("attackDamage", data);
    }

    public static String healVillagers(List<Villager> villagers) {
        Map<String, String> data = new HashMap<>();
        StringBuilder guys = new StringBuilder();
        if (villagers.isEmpty()) {
            data.put(GUYS , EMPTY_ARRAY);
            return EventGenerator.newEvent("healVillagers", data);
        }

        guys.append("[");
        for (Villager villager : villagers) {
            guys.append("{");
            appendJson(guys, NAME, villager.getName());
            appendJson(guys, SURNAME, villager.getSurname());
            appendJson(guys, NEW_HEALTH, villager.getHealth(), true);
            guys.append("},");
        }
        guys.deleteCharAt(guys.length() - 1);
        guys.append("]");
        data.put(GUYS, guys.toString());
        return EventGenerator.newEvent("healVillagers", data);
    }


    public static void appendJson(StringBuilder json, String key, String value) {
        json.append(QUOTE).append(key).append(QUOTE + ": ").append(QUOTE).append(value).append(QUOTE).append(",");
    }

    public static void appendJson(StringBuilder json, String key, int value) {
        json.append(QUOTE).append(key).append(QUOTE + ": ").append(value).append(",");
    }

    public static void appendJson(StringBuilder json, String key, int value, boolean isLast) {
        json.append(QUOTE).append(key).append(QUOTE + ": ").append(value);
        if (!isLast) {
            json.append(",");
        }
    }


    public static void appendJsonArray(StringBuilder json, String value) {
        json.append(QUOTE).append(value).append(QUOTE).append(",");
    }

}
