package org.acme;

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


    private final String name;
    private final List<Villager> villagers;
    private int foodSupplies;

    public Village(String name) {
        Villager baseVillager = new Villager.Builder()
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
                .workingForce(20)
                .build();



        this.name = name;
        this.villagers = new ArrayList<>();
        villagers.add(baseVillager);
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

        // remove dead villagers
        villagers.removeIf(villager -> villager.getHealth() <= 0);

        return result;
    }

    public SoldierAttackResult villagerAttack(int index) {
        if (listCreatures == null || listCreatures.isEmpty()) {
            throw new IllegalArgumentException(CREATURE_NULL);
        }
        if (index < 0 || index >= villagers.size()) {
            throw new IllegalArgumentException("Index out of bounds");
        }
        if (villagers.get(index).getJob() != Job.WARRIOR) {
            throw new IllegalArgumentException(WARRIOR_ATTACK);
        }



        SoldierAttackResult result = villagers.get(index).attack(listCreatures);

        // remove dead creatures
        listCreatures = listCreatures.stream().filter(creature -> creature.getHealth() > 0).toList();

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

    // Scope : tests
    public void setCreatureIndex(int index) {
        creatureIndex = index;
    }

    // Scope : tests
    public void setVillagerIndex(int index) {
        villagerIndex = index;
    }

    public SoldierAttackResult fightStep() {
        if (isFightOver())
            throw new IllegalStateException("The fight is over");

        if (creatureIndex >= listCreatures.size()) {
            throw new IllegalStateException("Creature index out of bounds");
        }

        if (villagerIndex >= villagers.size()) {
            throw new IllegalStateException("Villager index out of bounds");
        }

        if (villagerIndex == -1) villagerIndex = getFirstWarriorIndex();

        // Alternate attacks between creatures and villagers
        SoldierAttackResult result = null;

        // HACK : needs to be refactored
        if (!creatureTurn){
            if (villagerIndex != -1) {
                result = villagerTurn();
                creatureTurn = !creatureTurn;
            }else {
                result = creatureTurn();
            }
        }
        else {
            result = creatureTurn();
            creatureTurn = !creatureTurn;
        }


        // remove dead creatures
        listCreatures = listCreatures.stream().filter(creature -> creature.getHealth() > 0).toList();

        // remove dead villagers
        villagers.removeIf(villager -> villager.getHealth() <= 0);


        return result;
    }

    public int getFirstWarriorIndex(){
        for (int i = 0; i < villagers.size(); i++) {
            if (villagers.get(i).getJob() == Job.WARRIOR) {
                return i;
            }
        }
        return -1;
    }

    public SoldierAttackResult villagerTurn(){
        SoldierAttackResult result = villagerAttack(villagerIndex);
        villagerIndex = getNextWarriorIndex();

        if (creatureIndex >= listCreatures.size()) {
            creatureIndex = 0;
        }

        return result;
    }

    public SoldierAttackResult creatureTurn(){
        SoldierAttackResult result = creatureAttack(listCreatures.get(creatureIndex));
        creatureIndex = (creatureIndex + 1) % listCreatures.size();


        // check if the villagerIndex is still to a warrior else set to -1
        if (villagerIndex != -1 && villagers.get(villagerIndex).getJob() != Job.WARRIOR) {
            villagerIndex = -1;
        }

        return result;
    }

    public boolean isFightOver() {
        if (listCreatures == null || listCreatures.isEmpty() || villagers == null || villagers.isEmpty()) {
            return true;
        }
        return listCreatures.stream().allMatch(creature -> creature.getHealth() <= 0) || villagers.stream().allMatch(villager -> villager.getHealth() <= 0);
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

    public List<Villager> healVillagers() {
        List<Villager> villagersHealed = new ArrayList<>();
        for (Villager villager : villagers) {
            if (villager.getJob() == Job.HEALER) {
                villagersHealed.addAll(villager.heal(villagers));
            }
        }

        // Remove double entries
        villagersHealed = villagersHealed.stream().distinct().toList();


        return villagersHealed;
    }
}
