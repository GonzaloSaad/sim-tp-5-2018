package utn.frc.sim.simulation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utn.frc.sim.generators.distributions.DistributionRandomGenerator;
import utn.frc.sim.generators.distributions.NegativeExponentialDistributionGenerator;
import utn.frc.sim.generators.distributions.NormalDistributionGenerator;
import utn.frc.sim.generators.distributions.UniformDistributionGenerator;
import utn.frc.sim.model.Event;
import utn.frc.sim.model.clients.Client;
import utn.frc.sim.model.clients.ClientGenerator;
import utn.frc.sim.model.servers.Server;
import utn.frc.sim.model.servers.ServerWithDistribution;
import utn.frc.sim.model.servers.ServerWithInterruptions;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

public class Simulation {

    private static final Logger logger = LogManager.getLogger(Simulation.class);
    private LocalDateTime clock;
    private LocalDateTime dayFirstEvent;
    private Server recepcion;
    private Queue<Client> recepcionQueue;
    private Server balanza;
    private Queue<Client> balanzaQueue;
    private Server darsena_1;
    private Server darsena_2;
    private Queue<Client> darsenaQueue;
    private ClientGenerator clientGenerator;
    private double avgMinutesPerTruck;
    private int avgTrucksOutside;
    private int trucksServed;
    private Events lastEventDescription;
    private Client clientOfEvent;
    private int day;
    private int limitOfSimulations;
    private DistributionRandomGenerator capacityGenerator;


    private Simulation(SimulationType type, int days) {
        initSimulation(type, days);
    }

    public static Simulation ofType(SimulationType type, int days) {
        return new Simulation(type, days);
    }

    private void initSimulation(SimulationType type, int days) {
        limitOfSimulations = days;
        initFirstEventOfDay();
        initStatisticsValues();
        initRecepcion();
        initBalanza();
        initDarsenas();
        initClientGenerator(type);
        initEvent();
    }

    private void initEvent() {
        lastEventDescription = Events.INICIO;
    }

    private void initFirstEventOfDay() {
        day = 0;
        dayFirstEvent = LocalDateTime.of(2018, 1, 1, 5, 0);
    }

    private void initStatisticsValues() {
        avgMinutesPerTruck = 0;
        avgTrucksOutside = 0;
        trucksServed = 0;
    }

    private void initRecepcion() {
        recepcionQueue = new LinkedList<>();
        DistributionRandomGenerator generator = UniformDistributionGenerator.createOf(3, 7);
        recepcion = new Server("Recepcion", generator);
    }

    private void initBalanza() {
        balanzaQueue = new LinkedList<>();
        capacityGenerator = UniformDistributionGenerator.createOf(15000,22000);
        DistributionRandomGenerator generator = UniformDistributionGenerator.createOf(5, 7);
        balanza = new Server("Balanza", generator);
    }

    private void initDarsenas() {
        darsenaQueue = new LinkedList<>();
        darsena_1 = createDarsenaForNumber(1);
        darsena_2 = createDarsenaForNumber(2);
    }

    private Server createDarsenaForNumber(int number) {
        DistributionRandomGenerator generator = UniformDistributionGenerator.createOf(15, 20);
        NormalDistributionGenerator generatorForInterruptions = NormalDistributionGenerator.createOf(10, Math.sqrt(1.2));
        return new ServerWithDistribution("Darsena " + number, generator, 15, generatorForInterruptions);
    }

    private void initClientGenerator(SimulationType type) {
        DistributionRandomGenerator generator;
        int hourOfStart;
        if (type == SimulationType.Type1) {
            generator = NegativeExponentialDistributionGenerator.createOf(7.5);
            hourOfStart = 12;
        } else {
            generator = UniformDistributionGenerator.createOf(7, 8);
            hourOfStart = 5;
        }
        LocalDateTime clientsInitial = LocalDateTime.of(2018, 1, 1, hourOfStart, 0);
        clientGenerator = new ClientGenerator(clientsInitial, generator);
    }

    public void step() throws SimulationFinishedException {
        clock = getNextEvent();
        handleEventFromFirstEvent(clock);
    }

    private void handleEventFromFirstEvent(LocalDateTime clock) throws SimulationFinishedException {
        if (dayFirstEvent.isEqual(clock)) {

            day++;
            if (day > limitOfSimulations) {
                throw new SimulationFinishedException();
            }
            logger.debug("{} - Day start.", clock);
            lastEventDescription = Events.INICIO_DEL_DIA;
            dayFirstEvent = dayFirstEvent.plus(1, ChronoUnit.DAYS);
            clientOfEvent = null;
            calculateAvgTrucksOutside();

            if (recepcion.isFree() && !recepcionQueue.isEmpty()) {
                Client client = recepcionQueue.poll();
                logger.debug("{} - Ingesting outside client. Client: {}.", clock, client);
                client.setInTime(clock);
                recepcion.serveToClient(clock, client);
            }
        } else {
            handleEventFromClients(clock);
        }
    }

    private void handleEventFromClients(LocalDateTime clock) {
        if (clientGenerator.isEventFrom(clock)) {

            Client nextClient = clientGenerator.getNextClient();
            clientOfEvent = nextClient;
            lastEventDescription = Events.LLEGADA_CLIENTE;
            logger.debug("{} - New client into the system. Client: {}.", clock, nextClient);
            if (recepcion.isFree()) {
                nextClient.setInTime(clock);
                recepcion.serveToClient(clock, nextClient);
            } else {
                recepcionQueue.add(nextClient);
            }
        } else {
            handleEventFromRecepcion(clock);
        }
    }

    private void handleEventFromRecepcion(LocalDateTime clock) {
        if (recepcion.isEventFrom(clock)) {
            lastEventDescription = Events.FIN_RECEPCION;
            Event event = recepcion.getEvent();
            if (event.hasClient()) {
                Client finishedClient = event.getClient();
                logger.debug("{} - Reception finished. Client: {}.", clock, finishedClient);
                clientOfEvent = finishedClient;
                if (balanza.isFree()) {
                    balanza.serveToClient(clock, finishedClient);
                } else {
                    balanzaQueue.add(finishedClient);
                }

                if (!recepcionQueue.isEmpty() && isPlantOpenToServe()) {
                    Client nextClientForRecepcion = recepcionQueue.poll();
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
            lastEventDescription = Events.FIN_BALANZA;
            Event event = balanza.getEvent();
            if (event.hasClient()) {
                Client finishedClient = event.getClient();
                finishedClient.setCapacity(capacityGenerator.random());
                logger.debug("{} - Balanza finished. Client: {}.", clock, finishedClient);
                clientOfEvent = finishedClient;
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
            handleEventForDarsena(clock, darsena_1, 1);
        } else if (darsena_2.isEventFrom(clock)) {
            handleEventForDarsena(clock, darsena_2, 2);
        } else {
            throw new RuntimeException();
        }
    }

    private void handleEventForDarsena(LocalDateTime clock, Server darsena, int darsenaNumber) {
        Event event = darsena.getEvent();
        if (event.hasClient()) {
            Client finishedClient = event.getClient();
            finishedClient.setOutTime(clock);
            trucksServed++;
            calculateAvgMinutesForTrucks(finishedClient);
            clientOfEvent = finishedClient;
            logger.debug("{} - Darsena {} finished. Client out: {}.", clock, darsenaNumber, finishedClient);

            if (darsenaNumber == 1) {
                lastEventDescription = Events.FIN_DARSENA_1;
            } else {
                lastEventDescription = Events.FIN_DARSENA_2;
            }

        } else {
            clientOfEvent = null;
            logger.debug("{} - Darsena {} finished. No client. Just calibration.", clock, darsenaNumber);
            if (darsenaNumber == 1) {
                lastEventDescription = Events.FIN_DARSENA_1_CALIBRACION;
            } else {
                lastEventDescription = Events.FIN_DARSENA_2_CALIBRACION;
            }
        }
        if (!darsenaQueue.isEmpty() && darsena.isFree()) {
            Client nextClient = darsenaQueue.poll();
            darsena.serveToClient(clock, nextClient);
        }
    }

    private void calculateAvgMinutesForTrucks(Client client) {
        int n = trucksServed;
        long duration = client.getMinutesOfAttention();
        avgMinutesPerTruck = ((double) 1 / n) * ((n - 1) * avgMinutesPerTruck + duration);
    }

    private void calculateAvgTrucksOutside() {
        avgTrucksOutside += recepcionQueue.size();
    }

    private LocalDateTime getNextEvent() {

        LocalDateTime firstEvent = dayFirstEvent;

        if (clientGenerator.getNextClientEvent().isBefore(firstEvent)) {
            firstEvent = clientGenerator.getNextClientEvent();
        }

        if (recepcion.getNextEnd().isPresent()) {
            LocalDateTime recepcionTime = recepcion.getNextEnd().get();
            if (recepcionTime.isBefore(firstEvent)) {
                firstEvent = recepcionTime;
            }
        }

        if (balanza.getNextEnd().isPresent()) {
            LocalDateTime balanzaTime = balanza.getNextEnd().get();
            if (balanzaTime.isBefore(firstEvent)) {
                firstEvent = balanzaTime;
            }
        }

        if (darsena_1.getNextEnd().isPresent()) {
            LocalDateTime darsena_1_time = darsena_1.getNextEnd().get();
            if (darsena_1_time.isBefore(firstEvent)) {
                firstEvent = darsena_1_time;
            }
        }

        if (darsena_2.getNextEnd().isPresent()) {
            LocalDateTime darsena_2_Time = darsena_2.getNextEnd().get();
            if (darsena_2_Time.isBefore(firstEvent)) {
                firstEvent = darsena_2_Time;
            }
        }

        return firstEvent;
    }

    private boolean isPlantOpenToServe() {
        return clock.getHour() < 18;
    }

    public double getAvgMinutesPerTruck() {
        return avgMinutesPerTruck;
    }

    public int getAvgTrucksOutside() {
        return avgTrucksOutside;
    }

    public double getTrucksServedPerDay() {
        return (double) trucksServed / (day - 1);
    }

    public int getDay() {
        return day;
    }

    public int getTrucksServed() {
        return trucksServed;
    }

    public LocalDateTime getClock() {
        return clock;
    }

    public Events getLastEventDescription() {
        return lastEventDescription;
    }

    public Server getRecepcion() {
        return recepcion;
    }

    public Queue<Client> getRecepcionQueue() {
        return recepcionQueue;
    }

    public Server getBalanza() {
        return balanza;
    }

    public Queue<Client> getBalanzaQueue() {
        return balanzaQueue;
    }

    public Server getDarsena1() {
        return darsena_1;
    }

    public Server getDarsena2() {
        return darsena_2;
    }

    public Queue<Client> getDarsenaQueue() {
        return darsenaQueue;
    }

    public ClientGenerator getClientGenerator() {
        return clientGenerator;
    }

    public Optional<Client> getClientOfEvent() {
        return Optional.ofNullable(clientOfEvent);
    }
}
