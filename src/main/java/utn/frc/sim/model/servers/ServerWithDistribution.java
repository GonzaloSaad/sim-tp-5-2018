package utn.frc.sim.model.servers;

import utn.frc.sim.generators.distributions.DistributionRandomGenerator;
import utn.frc.sim.generators.distributions.NormalDistributionGenerator;
import utn.frc.sim.model.TimeEventEuler;
import utn.frc.sim.model.clients.Client;

import java.time.LocalDateTime;

public class ServerWithDistribution extends ServerWithInterruptions {

    private TimeEventEuler eventEuler;

    public ServerWithDistribution(String serverName, DistributionRandomGenerator generator, int interruptions, DistributionRandomGenerator generatorForInterruptions) {
        super(serverName, generator, interruptions, generatorForInterruptions);
        initEventEuler();
    }

    private void initEventEuler() {
        eventEuler = new TimeEventEuler();
    }

    @Override
    protected LocalDateTime calculateNextEventForClient(LocalDateTime clock, Client client) {
        double l = client.getCapacity();
        return eventEuler.calculateNextEventFromDistribution(clock, client);
    }


}
