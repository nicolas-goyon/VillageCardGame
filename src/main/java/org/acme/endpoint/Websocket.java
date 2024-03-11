package org.acme.endpoint;

import io.quarkus.logging.Log;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.websocket.server.ServerEndpoint;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.Session;
import org.acme.SynchronizedVillage;
import org.acme.Village;
import org.acme.VillageManagement;
import org.acme.generators.EventGenerator;
import org.acme.villagers.Job;


@ServerEndpoint("/chat/{username}")
@ApplicationScoped
public class Websocket {

    Map<String, Session> sessions = new ConcurrentHashMap<>();
    Map<String, VillageManagement> villages = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        sessions.put(username, session);
        VillageManagement villageManagement = new VillageManagement(new SynchronizedVillage("message"), session);
        villages.put(username, villageManagement);
        session.getAsyncRemote().sendText(EventGenerator.newVillage(villageManagement.getVillage()));
        villages.get(username).startVillage();
        Log.info("New session: " + username);
    }

    @OnClose
    public void onClose(Session session, @PathParam("username") String username) throws InterruptedException {
        sessions.remove(username);
        villages.get(username).stopVillage();
        villages.remove(username);
    }

    @OnError
    public void onError(Session session, @PathParam("username") String username, Throwable throwable) throws InterruptedException {
        sessions.remove(username);
        villages.get(username).stopVillage();
        villages.remove(username);
    }

    @OnMessage
    public void onMessage(String message, @PathParam("username") String username) {
        // Parse the message (JSON)
        Log.info("Received message: " + message);
        JsonObject json = new JsonObject(message);
        // Get the event type
        String eventType = json.getString("eventType");

        /********************************************************** JOB CHANGE **********************************************************/
        if (eventType.equals("jobChange")) {
            Log.info("Job change event");
            Village village = villages.get(username).getVillage();

            // Get the new job
            JsonObject guy = json.getJsonObject("guy");
            // Change the job
            String newJob = guy.getString("newJob");
            Job job = Job.fromString(newJob);
            village.changeVillagerJob(guy.getString("name"), guy.getString("surname"), job);


            // End of event
            Log.info("Job change event done");
        }


    }


}