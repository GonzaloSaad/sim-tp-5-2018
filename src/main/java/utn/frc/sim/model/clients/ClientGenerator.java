package utn.frc.sim.model.clients;

import utn.frc.sim.generators.distributions.DistributionRandomGenerator;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ClientGenerator {
    private LocalDateTime nextClientEvent;
    private LocalDateTime initTime;
    private int lastClient;
    private int day;
    private DistributionRandomGenerator generator;

    public ClientGenerator(LocalDateTime initTime, DistributionRandomGenerator generator) {
        this.generator = generator;
        this.lastClient = 0;
        this.day = 1;
        this.nextClientEvent = initTime;
        this.initTime = initTime;
    }

    public Client getNextClient() {
        calculateNextEvent();
        lastClient++;
        return new Client(lastClient);
    }

    private void calculateNextEvent() {
        LocalDateTime nextEvent = nextClientEvent.plus((int) generator.random(), ChronoUnit.MINUTES);
        if (nextEvent.getHour() >= 20) {
            nextClientEvent = initTime.plus(day, ChronoUnit.DAYS);
            day++;
        } else {
            nextClientEvent = nextEvent;
        }
    }

    public LocalDateTime getNextClientEvent() {
        return nextClientEvent;
    }

    public boolean isEventFrom(LocalDateTime clock) {
        return nextClientEvent.equals(clock);
    }
}
