package org.acme;

import jakarta.websocket.Session;
import org.acme.generators.EventGenerator;
import org.acme.generators.VillagerGenerator;
import org.acme.villagers.Villager;

public class VillageManagement {

    private Village village;
    private Session session;
    private Thread villageThread;
    private volatile boolean running;



    public VillageManagement(SynchronizedVillage village, Session session) {
        this.village = village;
        this.session = session;
        this.running = false;
    }

    public void startVillage(){
        // Create a new thread to loop the village
        if (villageThread != null) {
            villageThread.interrupt();
        }
        this.running = true;
        villageThread = new Thread(this::loopVillage);
        villageThread.start();
    }

    private static final int TICKTIME = 1000;
    private static final int TICKS = 6; // 6 ticks per day

    private int step = 0;

    private int days = 0;


    private void loopVillage(){
        // Vilage loop
        while (running) {
            try {
                Thread.sleep(TICKTIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            switch (step){
                case 1:
                    village.produceFood();
                    session.getAsyncRemote().sendText(EventGenerator.foodProduced(village));
                    break;
                case 2:
                    if (days % 3 == 0){
                        Villager newOne = VillagerGenerator.newVillager(village);
                        newOne.applyCharacteristics();
                        village.addVillager(newOne);
                        session.getAsyncRemote().sendText(EventGenerator.newVillager(village, newOne));
                    }
                    break;
                case 4:
                    village.eatFood();
                    session.getAsyncRemote().sendText(EventGenerator.foodEaten(village));
                    break;
            }

            step += 1;
            if (step > TICKS){
                step = 0;
                days += 1;
            }

        }
    }

    public void stopVillage(){
        // Stop the village loop
        this.running = false;
        try {
            villageThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        villageThread = null;
    }

    public Village getVillage() {
        return village;
    }
}
