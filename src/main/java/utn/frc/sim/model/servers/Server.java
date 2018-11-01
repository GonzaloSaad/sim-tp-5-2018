package utn.frc.sim.model.servers;

import utn.frc.sim.generators.distributions.DistributionRandomGenerator;
import utn.frc.sim.generators.distributions.NormalDistributionGenerator;
import utn.frc.sim.model.TimeEvent;
import utn.frc.sim.model.clients.Client;
import utn.frc.sim.model.Event;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

public class Server {

    private final String serverName;
    protected LocalDateTime nextEnd;
    protected Client servingClient;
    protected TimeEvent timeEvent;
    protected ServerState state;

    public Server(String serverName, DistributionRandomGenerator generator) {
        this.serverName = serverName;
        this.timeEvent = new TimeEvent(generator);
        this.state = ServerState.LBR;
    }

    public void serveToClient(LocalDateTime clock, Client client) {
        servingClient = client;
        nextEnd = calculateNextEventForClient(clock, client);
        state = ServerState.OCP;
    }

    protected LocalDateTime calculateNextEventForClient(LocalDateTime clock, Client client) {
        return timeEvent.calculateNextEventFromRandom(clock);
    }

    public Event getEvent() {
        Event event = new Event(servingClient);
        state = ServerState.LBR;
        nextEnd = null;
        servingClient = null;
        return event;
    }

    public Optional<LocalDateTime> getNextEnd() {
        return Optional.ofNullable(nextEnd);
    }

    public Optional<Client> getServingClient() {
        return Optional.ofNullable(servingClient);
    }

    public ServerState getState() {
        return state;
    }

    public String getServerName() {
        return serverName;
    }

    public boolean isFree() {
        return state == ServerState.LBR;
    }

    public boolean isEventFrom(LocalDateTime clock) {
        return nextEnd != null && nextEnd.equals(clock);
    }

}
