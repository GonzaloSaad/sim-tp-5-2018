package utn.frc.sim.model;

import utn.frc.sim.model.clients.Client;

import java.time.LocalDateTime;

public class Event {
    private final Client client;

    public Event(Client client) {
        this.client = client;
    }

    public Event(){
        this(null);
    }

    public Client getClient() {
        return client;
    }

    public boolean hasClient(){
        return client != null;
    }
}
