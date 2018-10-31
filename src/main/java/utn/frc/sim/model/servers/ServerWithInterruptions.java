package utn.frc.sim.model.servers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utn.frc.sim.generators.distributions.DistributionRandomGenerator;
import utn.frc.sim.generators.distributions.NormalDistributionGenerator;
import utn.frc.sim.model.Event;
import utn.frc.sim.model.clients.Client;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ServerWithInterruptions extends Server {

    private static final Logger logger = LogManager.getLogger(ServerWithInterruptions.class);
    private static final double K_LOW_LIMIT = 0.15;
    private static final double K_HIGH_LIMIT = 0.25;
    private DistributionRandomGenerator generator;
    private final int interruptPeriod;
    private int clientsWithoutInterruptions;
    private DistributionRandomGenerator kGenerator;


    public ServerWithInterruptions(String serverName, DistributionRandomGenerator generator, int interruptions, DistributionRandomGenerator generatorForInterruptions) {
        super(serverName, generator);
        initKGenerator();
        this.interruptPeriod = interruptions;
        this.clientsWithoutInterruptions = 0;
        this.generator = generatorForInterruptions;
    }

    private void initKGenerator() {
        kGenerator = NormalDistributionGenerator.createOf(0.2, Math.sqrt(0.7));
    }

    @Override
    public Event getEvent() {
        Event event;
        if (state == ServerState.OCP) {
            event = new Event(servingClient);
            clientsWithoutInterruptions++;
            if (clientsWithoutInterruptions == interruptPeriod) {
                logger.info("{}: {} clients without interruptions. Next event is calibration.",
                        getServerName(),
                        clientsWithoutInterruptions);
                nextEnd = timeEvent.calculateNextEventFromRandom(nextEnd);
                state = ServerState.OUT;
            } else {
                logger.info("{}: {} clients without interruptions.", getServerName(), clientsWithoutInterruptions);
                nextEnd = null;
                state = ServerState.LBR;
            }

        } else {
            clientsWithoutInterruptions = 0;
            nextEnd = null;
            servingClient = null;
            state = ServerState.LBR;
            event = new Event();
        }
        servingClient = null;
        return event;
    }
}
