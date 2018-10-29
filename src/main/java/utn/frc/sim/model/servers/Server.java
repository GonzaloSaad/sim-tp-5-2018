package utn.frc.sim.model.servers;

import utn.frc.sim.generators.distributions.DistributionRandomGenerator;
import utn.frc.sim.model.clients.Client;
import utn.frc.sim.model.Event;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

public class Server {

    private static final String END_OF_ATTENTION = "FA";
    private static final String SEPARATOR = "-";
    private final String serverName;
    protected LocalDateTime nextEnd;
    protected Client servingClient;
    private DistributionRandomGenerator generator;
    protected ServerState state;

    public Server(String serverName, DistributionRandomGenerator generator) {
        this.serverName = serverName;
        this.generator = generator;
        this.state = ServerState.LBR;
    }

    public Optional<LocalDateTime> getNextEnd() {
        return Optional.ofNullable(nextEnd);
    }

    public void serveToClient(LocalDateTime clock, Client client){
        servingClient = client;
        nextEnd = clock.plus((int) generator.random(), ChronoUnit.MINUTES);
        state = ServerState.OCP;
    }

    public Event getEvent(){
        nextEnd = null;
        state = ServerState.LBR;
        return new Event(servingClient);
    }

    public boolean isFree(){
        return state == ServerState.LBR;
    }

    public boolean isEventFrom(LocalDateTime clock) {
        return nextEnd != null && nextEnd.equals(clock);
    }

    private String getFinalEventDescription(){
        return END_OF_ATTENTION + SEPARATOR + serverName;
    }
}
