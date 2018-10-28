package utn.frc.sim.model.clients;

import utn.frc.sim.generators.distributions.DistributionRandomGenerator;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ClientGenerator {
    private LocalDateTime nextClientEvent;
    private int lastClient;
    private DistributionRandomGenerator generator;

    public ClientGenerator(LocalDateTime initTime, DistributionRandomGenerator generator) {
        this.generator = generator;
        this.lastClient = 0;
        nextClientEvent = initTime;
    }

    public Client getNextClient(){
        nextClientEvent = nextClientEvent.plus((int) generator.random(), ChronoUnit.MINUTES);
        lastClient++;
        return new Client(lastClient);
    }

    public LocalDateTime getNextClientEvent() {
        return nextClientEvent;
    }

    public boolean isEventFrom(LocalDateTime clock){
        return nextClientEvent.equals(clock);
    }
}
