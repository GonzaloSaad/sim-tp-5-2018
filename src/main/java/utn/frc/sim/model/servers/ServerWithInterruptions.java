package utn.frc.sim.model.servers;

import utn.frc.sim.generators.distributions.DistributionRandomGenerator;
import utn.frc.sim.model.Event;

import java.time.temporal.ChronoUnit;

public class ServerWithInterruptions extends Server {

    private DistributionRandomGenerator generator;
    private final int interruptPeriod;
    private int clientsWithoutInterruptions;

    public ServerWithInterruptions(String serverName, DistributionRandomGenerator generator, int interruptions, DistributionRandomGenerator generatorForInterruptions) {
        super(serverName, generator);
        this.interruptPeriod = interruptions;
        this.clientsWithoutInterruptions = 0;
        this.generator = generatorForInterruptions;
    }

    @Override
    public Event getEvent() {

        if (state == ServerState.OCP) {
            clientsWithoutInterruptions++;
            if (clientsWithoutInterruptions == interruptPeriod) {
                nextEnd = nextEnd.plus((int) generator.random(), ChronoUnit.MINUTES);
                state = ServerState.OUT;
            } else {
                nextEnd = null;
                state = ServerState.LBR;
            }
            return new Event(servingClient);
        } else {
            clientsWithoutInterruptions = 0;
            nextEnd = null;
            state = ServerState.LBR;
            return new Event();
        }
    }
}
