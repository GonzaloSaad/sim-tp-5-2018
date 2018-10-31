package utn.frc.sim.model.clients;

import utn.frc.sim.generators.distributions.DistributionRandomGenerator;
import utn.frc.sim.model.TimeEvent;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ClientGenerator {
    private LocalDateTime nextClientEvent;
    private LocalDateTime initTime;
    private int lastClient;
    private int day;
    private TimeEvent timeEvent;

    public ClientGenerator(LocalDateTime initTime, DistributionRandomGenerator generator) {
        this.timeEvent = new TimeEvent(generator);
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
        LocalDateTime nextEvent = timeEvent.calculateNextEventFromRandom(nextClientEvent);
        if (nextEvent.getHour() >= 18) {
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
