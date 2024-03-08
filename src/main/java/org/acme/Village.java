package org.acme;

import io.quarkus.logging.Log;
import org.acme.creatures.Creature;
import org.acme.villagers.Job;
import org.acme.villagers.Villager;

import java.util.ArrayList;
import java.util.List;

public class Village {
    // Error messages
    private static final String VILLAGER_NULL = "Villager cannot be null";
    private static final String VILLAGER_EXISTS = "Villager already exists";
    private static final String VILLAGER_NAME_NULL = "Villager must have a name and a surname";
    private static final String VILLAGER_NAME_EXISTS = "Villager with the same name and surname already exists";
    private static final String CREATURE_NULL = "Creature cannot be null";
    private static final String CREATURE_EMPTY = "Creature list cannot be empty";
    private static final String WARRIOR_ATTACK = "Only warriors can attack";



    private static final int BASEFOOD = 100;
    private static final Villager BASEVILLAGER = new Villager.Builder()
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


    private final String name;
    private final List<Villager> villagers;
    private int foodSupplies;

    public Village(String name) {
        this.name = name;
        this.villagers = new ArrayList<>();
        villagers.add(BASEVILLAGER);
        this.foodSupplies = BASEFOOD;
    }

    // Getters

    public String getName() {
        return name;
    }

    public List<Villager> getVillagers() {
        return villagers;
    }

    public int getFoodSupplies() {
        return foodSupplies;
    }






    // Methods


    public void addVillager(Villager villager) {
        if (villager == null) {
            throw new IllegalArgumentException(VILLAGER_NULL);
        }

        if (villagers.contains(villager)) {
            throw new IllegalArgumentException(VILLAGER_EXISTS);
        }

        if (villager.getName() == null || villager.getSurname() == null) {
            throw new IllegalArgumentException(VILLAGER_NAME_NULL);
        }

        if (villagers.stream().anyMatch(v -> v.getName().equals(villager.getName()) && v.getSurname().equals(villager.getSurname()))) {
            throw new IllegalArgumentException(VILLAGER_NAME_EXISTS);
        }

        villagers.add(villager);
    }

    public void removeVillager(String name, String surname) {
        villagers.removeIf(villager -> villager.getName().equals(name) && villager.getSurname().equals(surname));
    }


    public int foodProduction() {
        int foodProduced = 0;
        for (Villager villager : villagers) {
            if (villager.getJob() == Job.FARMER) {
                foodProduced += villager.produceFood();
            }
        }
        return foodProduced;
    }

    public void produceFood() {
        foodSupplies += foodProduction();
    }

    public void eatFood() {
        for (Villager villager : villagers) {
            foodSupplies = villager.eat(foodSupplies);
        }
    }




    // Attack methods
    public SoldierAttackResult creatureAttack(Creature creature) {
        if (creature == null) {
            throw new IllegalArgumentException(CREATURE_NULL);
        }
        // Attack warriors first, if no warriors attack anyone
        List<Villager> warriors = new ArrayList<>();

        for (Villager villager : villagers) {
            if (villager.getJob() == Job.WARRIOR) {
                warriors.add(villager);
            }
        }
        SoldierAttackResult result;
        if (!warriors.isEmpty()) {
            result = creature.attack(warriors);
        } else {
            result = creature.attack(villagers);
        }

        // Remove dead villagers
        villagers.removeIf(villager -> villager.getHealth() <= 0);

        return result;
    }

    public SoldierAttackResult villagerAttack(List<Creature> creatures, int index) {
        if (creatures == null) {
            throw new IllegalArgumentException(CREATURE_NULL);
        }
        if (index < 0 || index >= villagers.size()) {
            throw new IllegalArgumentException("Index out of bounds");
        }
        if (villagers.get(index).getJob() != Job.WARRIOR) {
            throw new IllegalArgumentException(WARRIOR_ATTACK);
        }

        SoldierAttackResult result = villagers.get(index).attack(creatures);
        // Remove dead creatures
        creatures.removeIf(creature -> creature.getHealth() <= 0);

        return result;
    }


    private List<Creature> listCreatures = new ArrayList<>();
    private int creatureIndex = 0;
    private int villagerIndex = 0;
    private boolean creatureTurn = true;

    public void initNewAttack(List<Creature> creatures) {
        if (creatures == null) {
            throw new IllegalArgumentException(CREATURE_NULL);
        }
        if (creatures.isEmpty()) {
            throw new IllegalArgumentException(CREATURE_EMPTY);
        }
        listCreatures = creatures;
        villagerIndex = getNextWarriorIndex();
    }

    public SoldierAttackResult fightStep() {
        if (listCreatures == null || listCreatures.isEmpty()) {
            throw new IllegalArgumentException(CREATURE_EMPTY);
        }

        SoldierAttackResult result = null;
        // Alternate attacks between creatures and villagers
        if (creatureTurn) {
            result = creatureAttack(listCreatures.get(creatureIndex));
            creatureIndex = (creatureIndex + 1) % listCreatures.size();
        } else if (villagerIndex != -1) {
            result = villagerAttack(listCreatures, villagerIndex);
            villagerIndex = getNextWarriorIndex();
        }

        creatureTurn = !creatureTurn;
        return result;
    }

    private int getNextWarriorIndex() {
        if (villagers == null || villagers.isEmpty()) {
            throw new IllegalArgumentException("No villagers in the village");
        }

        int previousIndex = villagerIndex;
        int nextIndex = -1;
        // Same as the two previous loops, but in only one loop
         for (int i = 0; i < villagers.size(); i++) {
             if (villagers.get( (i + previousIndex + 1) % villagers.size() ).getJob() == Job.WARRIOR) {
                 nextIndex = (i + previousIndex + 1) % villagers.size();
                 break;
             }
         }


        return nextIndex;
    }

    public List<Creature> getListCreatures() {
        return listCreatures;
    }



    public void changeVillagerJob(String name, String surname, Job job) {
        Villager villager = villagers.stream()
                .filter(v -> v.getName().equals(name) && v.getSurname().equals(surname))
                .findFirst()
                .orElse(null);
        if (villager == null) {
            throw new IllegalArgumentException(VILLAGER_NULL);
        }
        villager.setJob(job);
    }

}
