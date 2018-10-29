package utn.frc.sim.simulation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utn.frc.sim.generators.distributions.NegativeExponentialDistributionGenerator;
import utn.frc.sim.generators.distributions.NormalDistributionGenerator;
import utn.frc.sim.generators.distributions.UniformDistributionGenerator;
import utn.frc.sim.model.Event;
import utn.frc.sim.model.clients.Client;
import utn.frc.sim.model.clients.ClientGenerator;
import utn.frc.sim.model.servers.Server;
import utn.frc.sim.model.servers.ServerWithInterruptions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Simulation {

    private static final Logger logger = LogManager.getLogger(Simulation.class);
    private LocalDateTime clock;
    private Server recepcion;
    private Queue<Client> receptionQueue;
    private Server balanza;
    private Queue<Client> balanzaQueue;
    private Server darsena_1;
    private Server darsena_2;
    private Queue<Client> darsenaQueue;
    private Queue<Client> outsideQueue;
    private ClientGenerator clientGenerator;
    private double avgMinutesPerTruck;

    public Simulation() {
        initSimulation();
    }

    private void initSimulation() {
        initClock();
        initAvgMinutes();
        initRecepcion();
        initBalanza();
        initDarsenas();
        initClientGenerator();
    }

    private void initClock() {
        clock = LocalDateTime.of(2018, 1, 1, 12, 0);
    }

    private void initAvgMinutes() {
        avgMinutesPerTruck = 0;
    }

    private void initRecepcion() {
        receptionQueue = new LinkedList<>();
        UniformDistributionGenerator generator = UniformDistributionGenerator.createOf(3, 7);
        recepcion = new Server("RECEPCION", generator);
    }

    private void initBalanza() {
        balanzaQueue = new LinkedList<>();
        UniformDistributionGenerator generator = UniformDistributionGenerator.createOf(5, 7);
        balanza = new Server("BALANZA", generator);
    }

    private void initDarsenas() {
        darsenaQueue = new LinkedList<>();
        darsena_1 = createDarsenaForNumber(1);
        darsena_2 = createDarsenaForNumber(2);
    }

    private Server createDarsenaForNumber(int number) {
        UniformDistributionGenerator generator = UniformDistributionGenerator.createOf(15, 20);
        NormalDistributionGenerator generatorForInterruptions = NormalDistributionGenerator.createOf(10, Math.sqrt(1.2));
        return new ServerWithInterruptions("DARSENA_" + number, generator, 15, generatorForInterruptions);
    }

    private void initClientGenerator() {
        NegativeExponentialDistributionGenerator generator = NegativeExponentialDistributionGenerator.createOf(1 / 7.5);
        clientGenerator = new ClientGenerator(clock, generator);
    }

    public void step() {
        LocalDateTime clock = getNextEvent();
        handleEventForClock(clock);
    }

    private void handleEventForClock(LocalDateTime clock) {
        handleEventFromClients(clock);
    }

    private void handleEventFromClients(LocalDateTime clock) {
        if (clientGenerator.isEventFrom(clock)) {
            Client nextClient = clientGenerator.getNextClient();
            logger.info("{} - New client into the system. Client: {}.", clock, nextClient);
            //outsideQueue.add(nextClient);
            if (recepcion.isFree()) {
                nextClient.setInTime(clock);
                recepcion.serveToClient(clock, nextClient);
            } else {
                receptionQueue.add(nextClient);
            }

        } else {
            handleEventFromRecepcion(clock);
        }
    }

    private void handleEventFromRecepcion(LocalDateTime clock) {
        if (recepcion.isEventFrom(clock)) {
            Event event = recepcion.getEvent();
            if (event.hasClient()) {
                Client finishedClient = event.getClient();
                logger.info("{} - Reception finished. Client: {}.", clock, finishedClient);
                if (balanza.isFree()) {
                    balanza.serveToClient(clock, finishedClient);
                } else {
                    balanzaQueue.add(finishedClient);
                }

                if (!receptionQueue.isEmpty()) {
                    Client nextClientForRecepcion = receptionQueue.poll();
                    nextClientForRecepcion.setInTime(clock);
                    recepcion.serveToClient(clock, nextClientForRecepcion);
                }
            }
        } else {
            handleEventFromBalanza(clock);
        }
    }

    private void handleEventFromBalanza(LocalDateTime clock) {

        if (balanza.isEventFrom(clock)) {
            Event event = balanza.getEvent();
            if (event.hasClient()) {
                Client finishedClient = event.getClient();
                logger.info("{} - Balanza finished. Client: {}.", clock, finishedClient);
                if (darsena_1.isFree()) {
                    darsena_1.serveToClient(clock, finishedClient);
                } else if (darsena_2.isFree()) {
                    darsena_2.serveToClient(clock, finishedClient);
                } else {
                    darsenaQueue.add(finishedClient);
                }
            }

            if (!balanzaQueue.isEmpty()) {
                Client nextClientForBalanza = balanzaQueue.poll();
                balanza.serveToClient(clock, nextClientForBalanza);
            }
        } else {
            handleEventFromDarsenas(clock);
        }
    }

    private void handleEventFromDarsenas(LocalDateTime clock) {
        if (darsena_1.isEventFrom(clock)) {
            handleEventForDarsena(clock, darsena_1);
        } else if (darsena_2.isEventFrom(clock)) {
            handleEventForDarsena(clock, darsena_2);
        } else {
            throw new RuntimeException();
        }
    }

    private void handleEventForDarsena(LocalDateTime clock, Server darsena) {
        Event event = darsena.getEvent();
        if (event.hasClient()) {
            Client finishedClient = event.getClient();
            finishedClient.setOutTime(clock);
            calculateAvgMinutesForTrucks(finishedClient);
            logger.info("{} - Darsena finished. Client out: {}.", clock, finishedClient);
        } else {
            logger.info("{} - Darsena finished. No client.", clock);
        }
        if (!darsenaQueue.isEmpty() && darsena.isFree()) {
            Client nextClient = darsenaQueue.poll();
            darsena.serveToClient(clock, nextClient);
        }
    }

    private void calculateAvgMinutesForTrucks(Client client) {
        int n = client.getClientNumber();
        long duration = client.getMinutesOfAttention();
        avgMinutesPerTruck = ((double) 1/n) * ((n - 1) * avgMinutesPerTruck + duration);
    }

    private LocalDateTime getNextEvent() {

        LocalDateTime firstTime = clientGenerator.getNextClientEvent();

        if (clock.getDayOfYear() < firstTime.getDayOfYear()) {

        }

        if (recepcion.getNextEnd().isPresent()) {
            LocalDateTime recepcionTime = recepcion.getNextEnd().get();
            if (recepcionTime.isBefore(firstTime)) {
                firstTime = recepcionTime;
            }
        }

        if (balanza.getNextEnd().isPresent()) {
            LocalDateTime balanzaTime = balanza.getNextEnd().get();
            if (balanzaTime.isBefore(firstTime)) {
                firstTime = balanzaTime;
            }
        }

        if (darsena_1.getNextEnd().isPresent()) {
            LocalDateTime darsena_1_time = darsena_1.getNextEnd().get();
            if (darsena_1_time.isBefore(firstTime)) {
                firstTime = darsena_1_time;
            }
        }

        if (darsena_2.getNextEnd().isPresent()) {
            LocalDateTime darsena_2_Time = darsena_2.getNextEnd().get();
            if (darsena_2_Time.isBefore(firstTime)) {
                firstTime = darsena_2_Time;
            }
        }

        return firstTime;
    }
}
