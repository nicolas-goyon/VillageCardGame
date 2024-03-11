package org.acme;

import jakarta.websocket.Session;
import org.acme.creatures.Creature;
import org.acme.generators.CreatureGenerator;
import org.acme.generators.EventGenerator;
import org.acme.generators.VillagerGenerator;
import org.acme.villagers.Villager;

import java.util.List;

public class VillageManagement {

    private final Village village;
    private final Session session;
    private Thread villageThread;
    private volatile boolean running;
    private final CreatureGenerator creatureGenerator;



    public VillageManagement(SynchronizedVillage village, Session session) {
        this.village = village;
        this.session = session;
        this.running = false;
        this.creatureGenerator = new CreatureGenerator();
    }

    public void startVillage(){
        // Create a new thread to loop the village
        if (villageThread != null) {
            villageThread.interrupt();
        }
        this.running = true;
        villageThread = new Thread(this::runVillage);
        villageThread.start();
    }

    private static final int TICKTIME = 1000;
    private static final int TICKS = 6; // 6 ticks per day

    private int step = 0;

    private int days = 0;


    public void runVillage(){
        try {
            loopVillage();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    private void loopVillage() throws InterruptedException {
        // Village loop
        while (running) {
            Thread.sleep(TICKTIME);

            stepExecution();

            // Increment the step
            step += 1;
            if (step > TICKS){
                step = 0;
                days += 1;
            }

        }
    }

    private void stepExecution(){
        switch (step){
            case 1:
                foodProductionStep();
                break;
            case 2:
                villagerStep();
                break;
            case 3:
                attackStep();
                break;
            case 4:
                eatingStep();
                break;
            case 5:
                healingStep();
                break;
            default:
                break;
        }
    }


    private void foodProductionStep(){
        village.produceFood();
        session.getAsyncRemote().sendText(EventGenerator.foodProduced(village));
    }

    private void villagerStep(){
        if (days % 3 == 0){
            Villager newOne = VillagerGenerator.newVillager(village);
            newOne.applyCharacteristics();
            village.addVillager(newOne);
            session.getAsyncRemote().sendText(EventGenerator.newVillager(newOne));
        }
    }

    private void attackStep(){
        if (days % 6 == 0 && village.isFightOver()){
            // Attack the village

            List<Creature> creatures = creatureGenerator.generateCreatures();

            village.initNewAttack(creatures);
            session.getAsyncRemote().sendText(EventGenerator.newAttack(creatures));
        }
        else if (!village.isFightOver()) {
            // Fight step
            SoldierAttackResult result = village.fightStep();
            session.getAsyncRemote().sendText(EventGenerator.fightStep(result));
        }

    }

    private void eatingStep(){
        village.eatFood();
        session.getAsyncRemote().sendText(EventGenerator.foodEaten(village));
    }

    private void healingStep(){
        List<Villager> healed = village.healVillagers();
        session.getAsyncRemote().sendText(EventGenerator.healVillagers(healed));
    }

    public void stopVillage() throws InterruptedException {
        // Stop the village loop
        this.running = false;
        villageThread.join();
        villageThread = null;
    }

    public Village getVillage() {
        return village;
    }
}
