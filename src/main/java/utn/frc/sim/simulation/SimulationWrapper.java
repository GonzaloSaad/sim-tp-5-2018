package utn.frc.sim.simulation;

import utn.frc.sim.util.DoubleUtils;

import java.time.LocalDateTime;

public class SimulationWrapper {

    private static final String NONE_SYMBOL = "-";
    private final Simulation simulation;


    private SimulationWrapper(Simulation simulation) {
        this.simulation = simulation;
    }

    public static SimulationWrapper ofType(SimulationType type) {
        return new SimulationWrapper(Simulation.ofType(type));
    }

    public void step() {
        simulation.step();
    }


    /*
    Reloj y eventos.
     */

    public String getLastEvent() {
        return simulation.getLastEvent().toString();
    }

    public String getClock() {
        return simulation.getClock().toString();
    }

    public String getNumberClient(){
        return  Integer.toString(simulation.getClientOfEvent());
    }
    /*
    Datos para clientes.
     */

    public String getNextClientEvent() {
        LocalDateTime nextEvent = simulation.getClientGenerator().getNextClientEvent();
        if (nextEvent == null) {
            return NONE_SYMBOL;
        }
        return nextEvent.toString();
    }
   // public  String getNumberCliente(){
    //    return Integer.toString(simulation.;
   // }


    /*
    Datos para recepcion.
     */

    public String getRecepcionState() {
        return simulation.getRecepcion().getState().toString();
    }

    public String getRecepcionClient() {
        return simulation.getRecepcion()
                .getServingClient()
                .map(client -> Integer.toString(client.getClientNumber()))
                .orElse(NONE_SYMBOL);
    }

    public String getRecepcionNextEvent() {
        return simulation.getRecepcion().getNextEnd().map(LocalDateTime::toString).orElse(NONE_SYMBOL);
    }

    public String getRecepcionQueueLenght() {
        return Integer.toString(simulation.getRecepcionQueue().size());
    }

    /*
    Datos para Balanza.
     */

    public String getBalanzaState() {
        return simulation.getBalanza().getState().toString();
    }

    public String getBalanzaClient() {
        return simulation.getBalanza()
                .getServingClient()
                .map(client -> Integer.toString(client.getClientNumber()))
                .orElse(NONE_SYMBOL);
    }

    public String getBalanzaNextEvent() {
        return simulation.getBalanza().getNextEnd().map(LocalDateTime::toString).orElse(NONE_SYMBOL);
    }

    public String getBalanzaQueueLenght() {
        return Integer.toString(simulation.getBalanzaQueue().size());
    }

    /*
    Datos para Darsena 1.
     */

    public String getDarsena1State() {
        return simulation.getDarsena_1().getState().toString();
    }

    public String getDarsena1Client() {
        return simulation.getDarsena_1()
                .getServingClient()
                .map(client -> Integer.toString(client.getClientNumber()))
                .orElse(NONE_SYMBOL);
    }

    public String getgetDarsena1NextEvent() {
        return simulation.getDarsena_1().getNextEnd().map(LocalDateTime::toString).orElse(NONE_SYMBOL);
    }

    /*
    Datos para Darsena 2.
     */
    public String getDarsena2State() {
        return simulation.getDarsena_2().getState().toString();
    }

    public String getDarsena2Client() {
        return simulation.getDarsena_2()
                .getServingClient()
                .map(client -> Integer.toString(client.getClientNumber()))
                .orElse(NONE_SYMBOL);
    }

    public String getgetDarsena2NextEvent() {
        return simulation.getDarsena_2().getNextEnd().map(LocalDateTime::toString).orElse(NONE_SYMBOL);
    }

    /*
    Cola de darsenas.
     */

    public String getDarsenaQueueLenght() {
        return Integer.toString(simulation.getDarsenaQueue().size());
    }

    /*
    Estatisticas y conteos.
     */

    public String getNumberOfTrucksServed() {
        return Integer.toString(simulation.getTrucksServed());
    }

    public String getDay() {
        return Integer.toString(simulation.getDays());
    }

    public String getAverageDurationOfService() {
        return DoubleUtils.getDoubleWithFourPlaces(simulation.getAvgMinutesPerTruck());
    }

    public String getTrucksServedPerDay() {
        return DoubleUtils.getDoubleWithFourPlaces(simulation.getTrucksServedPerDay());
    }


}
