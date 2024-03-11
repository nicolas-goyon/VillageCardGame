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

    @Override
    public String getName(){
        synchronized (this) {
            return super.getName();
        }
    }

    @Override
    public List<Villager> getVillagers(){
        synchronized (this) {
            return new ArrayList<>(super.getVillagers());
        }
    }

    @Override
    public int getFoodSupplies(){
        synchronized (this) {
            return super.getFoodSupplies();
        }
    }

    @Override
    public void addVillager(Villager villager){
        synchronized (this) {
            super.addVillager(villager);
        }
    }

    @Override
    public void removeVillager(String name, String surname){
        synchronized (this) {
            super.removeVillager(name, surname);
        }
    }

    @Override
    public int foodProduction(){
        synchronized (this) {
            return super.foodProduction();
        }
    }

    @Override
    public void produceFood(){
        synchronized (this) {
            super.produceFood();
        }
    }

    @Override
    public void eatFood(){
        synchronized (this) {
            super.eatFood();
        }
    }

    @Override
    public SoldierAttackResult creatureAttack(Creature creature){
        synchronized (this) {
            return super.creatureAttack(creature);
        }
    }

    @Override
    public SoldierAttackResult villagerAttack(int index){
        synchronized (this) {
            return super.villagerAttack(index);
        }
    }

    @Override
    public void initNewAttack(List<Creature> creatures){
        synchronized (this) {
            super.initNewAttack(creatures);
        }
    }

    @Override
    public SoldierAttackResult fightStep(){
        synchronized (this) {
            return super.fightStep();
        }
    }

    @Override
    public List<Creature> getListCreatures(){
        synchronized (this) {
            return new ArrayList<>(super.getListCreatures());
        }
    }

    @Override
    public void changeVillagerJob(String name, String surname, Job job){
        synchronized (this) {
            super.changeVillagerJob(name, surname, job);
        }
    }

}
