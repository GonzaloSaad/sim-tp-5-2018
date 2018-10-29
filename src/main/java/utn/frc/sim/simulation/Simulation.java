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
import utn.frc.sim.model.servers.ServerWithInterruptions;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Queue;

public class Simulation {

    private static final Logger logger = LogManager.getLogger(Simulation.class);
    private LocalDateTime clock;
    private Server recepcion;
    private Queue<Client> recepcionQueue;
    private Server balanza;
    private Queue<Client> balanzaQueue;
    private Server darsena_1;
    private Server darsena_2;
    private Queue<Client> darsenaQueue;
    private Queue<Client> outsideQueue;
    private ClientGenerator clientGenerator;
    private double avgMinutesPerTruck;
    private int trucksServed;
    private Events lastEvent;


    private Simulation(SimulationType type) {
        initSimulation(type);
    }

    public static Simulation ofType(SimulationType type){
        return new Simulation(type);
    }

    private void initSimulation(SimulationType type) {
        initClock(type);
        initStatisticsValues();
        initRecepcion();
        initBalanza();
        initDarsenas();
        initClientGenerator(type);
        initEvent();
    }

    private void initEvent() {
        lastEvent = Events.INICIO;
    }

    private void initClock(SimulationType type) {
        int hourOfStart;
        if(type == SimulationType.Type1){
            hourOfStart = 12;
        } else{
            hourOfStart = 5;
        }
        clock = LocalDateTime.of(2018, 1, 1, hourOfStart, 0);
    }

    private void initStatisticsValues() {
        avgMinutesPerTruck = 0;
        trucksServed = 0;
    }

    private void initRecepcion() {
        recepcionQueue = new LinkedList<>();
        DistributionRandomGenerator generator = UniformDistributionGenerator.createOf(3, 7);
        recepcion = new Server("RECEPCION", generator);
    }

    private void initBalanza() {
        balanzaQueue = new LinkedList<>();
        DistributionRandomGenerator generator = UniformDistributionGenerator.createOf(5, 7);
        balanza = new Server("BALANZA", generator);
    }

    private void initDarsenas() {
        darsenaQueue = new LinkedList<>();
        darsena_1 = createDarsenaForNumber(1);
        darsena_2 = createDarsenaForNumber(2);
    }

    private Server createDarsenaForNumber(int number) {
        DistributionRandomGenerator generator = UniformDistributionGenerator.createOf(15, 20);
        NormalDistributionGenerator generatorForInterruptions = NormalDistributionGenerator.createOf(10, Math.sqrt(1.2));
        return new ServerWithInterruptions("DARSENA_" + number, generator, 15, generatorForInterruptions);
    }

    private void initClientGenerator(SimulationType type) {
        DistributionRandomGenerator generator;
        if(type == SimulationType.Type1){
            generator = NegativeExponentialDistributionGenerator.createOf(1 / 7.5);
        } else {
            generator = UniformDistributionGenerator.createOf(7, 8);
        }
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
            lastEvent = Events.LLEGADA_CLIENTE;
            Client nextClient = clientGenerator.getNextClient();
            logger.info("{} - New client into the system. Client: {}.", clock, nextClient);
            //outsideQueue.add(nextClient);
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
            lastEvent = Events.FIN_RECEPCION;
            Event event = recepcion.getEvent();
            if (event.hasClient()) {
                Client finishedClient = event.getClient();
                logger.info("{} - Reception finished. Client: {}.", clock, finishedClient);
                if (balanza.isFree()) {
                    balanza.serveToClient(clock, finishedClient);
                } else {
                    balanzaQueue.add(finishedClient);
                }

                if (!recepcionQueue.isEmpty()) {
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
            lastEvent = Events.FIN_BALANZA;
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
            calculateAvgMinutesForTrucks(finishedClient);
            trucksServed++;
            logger.info("{} - Darsena finished. Client out: {}.", clock, finishedClient);

            if (darsenaNumber == 1) {
                lastEvent = Events.FIN_DARSENA_1;
            } else {
                lastEvent = Events.FIN_DARSENA_2;
            }

        } else {
            logger.info("{} - Darsena finished. No client.", clock);
            if (darsenaNumber == 1) {
                lastEvent = Events.FIN_DARSENA_1_CALIBRACION;
            } else {
                lastEvent = Events.FIN_DARSENA_2_CALIBRACION;
            }
        }
        if (!darsenaQueue.isEmpty() && darsena.isFree()) {
            Client nextClient = darsenaQueue.poll();
            darsena.serveToClient(clock, nextClient);
        }
    }

    private void calculateAvgMinutesForTrucks(Client client) {
        int n = client.getClientNumber();
        long duration = client.getMinutesOfAttention();
        avgMinutesPerTruck = ((double) 1 / n) * ((n - 1) * avgMinutesPerTruck + duration);
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

    public double getAvgMinutesPerTruck() {
        return avgMinutesPerTruck;
    }

    public double getTrucksServedPerDay() {
        return (double) trucksServed / clientGenerator.getDays();
    }

    public int getDays() {
        return clientGenerator.getDays();
    }

    public int getTrucksServed() {
        return trucksServed;
    }

    public LocalDateTime getClock() {
        return clock;
    }

    public Events getLastEvent() {
        return lastEvent;
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

    public Server getDarsena_1() {
        return darsena_1;
    }

    public Server getDarsena_2() {
        return darsena_2;
    }

    public Queue<Client> getDarsenaQueue() {
        return darsenaQueue;
    }

    public Queue<Client> getOutsideQueue() {
        return outsideQueue;
    }

    public ClientGenerator getClientGenerator() {
        return clientGenerator;
    }
}
