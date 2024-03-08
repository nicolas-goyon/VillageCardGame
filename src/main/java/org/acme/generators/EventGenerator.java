package org.acme.generators;

import org.acme.Village;
import org.acme.villagers.Villager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventGenerator {

    public EventGenerator(){
        throw new IllegalStateException("Utility class");
    }

    // General events

    public static String newVillage(Village village){
        Map<String, String> data = new HashMap<>();
        data.put("baseGuy", villagerToJson(village.getVillagers().getFirst()));
        data.put("baseFood", String.valueOf(village.getFoodSupplies()));
        return EventGenerator.newEvent("newVillage", data);
    }

    public static String foodProduced(Village village){
        Map<String, String> data = new HashMap<>();
        data.put("newAmount", String.valueOf(village.getFoodSupplies()));
        return EventGenerator.newEvent("foodProduced", data);
    }

    public static String foodEaten(Village village) {
        Map<String, String> data = new HashMap<>();
        data.put("newAmount", String.valueOf(village.getFoodSupplies()));
        List<Villager> guys = village.getVillagers();
        StringBuilder guysData = new StringBuilder();

        guysData.append("[");
        for (Villager guy : guys) {
            String guyData = villagerToJsonConstrained(guy, true, true, false, false, false, false, true, false, false, false, false);
            guysData.append(guyData).append(",");
        }
        guysData.deleteCharAt(guysData.length() - 1);
        guysData.append("]");

        data.put("guys", guysData.toString());
        return EventGenerator.newEvent("foodEaten", data);
    }


    public static String newVillager(Village village, Villager newOne) {
        Map<String, String> data = new HashMap<>();
        data.put("newGuy", villagerToJson(newOne));
        return EventGenerator.newEvent("newVillager", data);
    }















    // utility methods

    public static String villagerToJson(Villager villager){
        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"name\": \"").append(villager.getName()).append("\",");
        json.append("\"surname\": \"").append(villager.getSurname()).append("\",");
        json.append("\"age\": ").append(villager.getAge()).append(",");
        json.append("\"job\": \"").append(villager.getJob()).append("\",");
        json.append("\"characteristic\": [");
        for (int i = 0; i < villager.getCharacteristic().size(); i++) {
            json.append("\"").append(villager.getCharacteristic().get(i)).append("\"");
            if (i < villager.getCharacteristic().size() - 1) {
                json.append(",");
            }
        }
        json.append("],");
        json.append("\"stomachSize\": ").append(villager.getStomachSize()).append(",");
        json.append("\"health\": ").append(villager.getHealth()).append(",");
        json.append("\"baseHealth\": ").append(villager.getBaseHealth()).append(",");
        json.append("\"damage\": ").append(villager.getDamage()).append(",");
        json.append("\"magic\": ").append(villager.getMagic()).append(",");
        json.append("\"workingForce\": ").append(villager.getWorkingForce());
        json.append("}");
        return json.toString();
    }


    public static String newEvent(String eventName, Map<String, String> data) {
        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"eventType\": \"").append(eventName).append("\",");
        data.forEach((key, value) -> json.append("\"").append(key).append("\": ").append(value).append(","));
        if (!data.isEmpty()) {
            json.deleteCharAt(json.length() - 1);
        }
        json.append("}");
        return json.toString();
    }

    private static String villagerToJsonConstrained(Villager villager, boolean name, boolean surname, boolean age, boolean job, boolean characteristic, boolean stomachSize, boolean health, boolean baseHealth, boolean damage, boolean magic, boolean workingForce){
        StringBuilder json = new StringBuilder();
        json.append("{");
        if (name) {
            json.append("\"name\": \"").append(villager.getName()).append("\",");
        }
        if (surname) {
            json.append("\"surname\": \"").append(villager.getSurname()).append("\",");
        }
        if (age) {
            json.append("\"age\": ").append(villager.getAge()).append(",");
        }
        if (job) {
            json.append("\"job\": \"").append(villager.getJob()).append("\",");
        }
        if (characteristic) {
            json.append("\"characteristic\": [");
            for (int i = 0; i < villager.getCharacteristic().size(); i++) {
                json.append("\"").append(villager.getCharacteristic().get(i)).append("\"");
                if (i < villager.getCharacteristic().size() - 1) {
                    json.append(",");
                }
            }
            json.append("],");
        }
        if (stomachSize) {
            json.append("\"stomachSize\": ").append(villager.getStomachSize()).append(",");
        }
        if (health) {
            json.append("\"health\": ").append(villager.getHealth()).append(",");
        }
        if (baseHealth) {
            json.append("\"baseHealth\": ").append(villager.getBaseHealth()).append(",");
        }
        if (damage) {
            json.append("\"damage\": ").append(villager.getDamage()).append(",");
        }
        if (magic) {
            json.append("\"magic\": ").append(villager.getMagic()).append(",");
        }
        if (workingForce) {
            json.append("\"workingForce\": ").append(villager.getWorkingForce());
        }

        if (json.charAt(json.length() - 1) == ',') {
            json.deleteCharAt(json.length() - 1);
        }

        json.append("}");
        return json.toString();
    }

}
