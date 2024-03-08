package org.acme;

import org.acme.creatures.Creature;
import org.acme.villagers.Job;
import org.acme.villagers.Villager;

import java.util.ArrayList;
import java.util.List;

public class SynchronizedVillage extends Village{
    public SynchronizedVillage(String name) {
        super(name);
    }

    public String getName(){
        synchronized (this) {
            return super.getName();
        }
    }

    public List<Villager> getVillagers(){
        synchronized (this) {
            return new ArrayList<>(super.getVillagers());
        }
    }

    public int getFoodSupplies(){
        synchronized (this) {
            return super.getFoodSupplies();
        }
    }

    public void addVillager(Villager villager){
        synchronized (this) {
            super.addVillager(villager);
        }
    }

    public void removeVillager(String name, String surname){
        synchronized (this) {
            super.removeVillager(name, surname);
        }
    }

    public int foodProduction(){
        synchronized (this) {
            return super.foodProduction();
        }
    }

    public void produceFood(){
        synchronized (this) {
            super.produceFood();
        }
    }

    public void eatFood(){
        synchronized (this) {
            super.eatFood();
        }
    }

    public SoldierAttackResult creatureAttack(Creature creature){
        synchronized (this) {
            return super.creatureAttack(creature);
        }
    }

    public SoldierAttackResult villagerAttack(List<Creature> creatures, int index){
        synchronized (this) {
            return super.villagerAttack(creatures, index);
        }
    }

    public void initNewAttack(List<Creature> creatures){
        synchronized (this) {
            super.initNewAttack(creatures);
        }
    }

    public SoldierAttackResult fightStep(){
        synchronized (this) {
            return super.fightStep();
        }
    }

    public List<Creature> getListCreatures(){
        synchronized (this) {
            return new ArrayList<>(super.getListCreatures());
        }
    }

    public void changeVillagerJob(String name, String surname, Job job){
        synchronized (this) {
            super.changeVillagerJob(name, surname, job);
        }
    }

}
