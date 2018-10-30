package utn.frc.sim.simulation;

import utn.frc.sim.util.DoubleUtils;

import java.time.LocalDateTime;

public class SimulationWrapper {

    private static final String NONE_SYMBOL = "-";
    private final Simulation simulation;


    private SimulationWrapper(Simulation simulation) {
        this.simulation = simulation;
    }

    public static SimulationWrapper ofType(SimulationType type, int days) {
        return new SimulationWrapper(Simulation.ofType(type, days));
    }

    public void step() throws SimulationFinishedException {
        simulation.step();
    }


    /*
    Reloj y eventos.
     */

    public String getLastEvent() {
        return simulation.getLastEventDescription().toString();
    }

    public String getClock() {
        return simulation.getClock().toString();
    }

    public String getNumberClient(){
        return simulation.getClientOfEvent()
                .map(client -> Integer.toString(client.getClientNumber()))
                .orElse(NONE_SYMBOL);
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
        return simulation.getDarsena1().getState().toString();
    }

    public String getDarsena1Client() {
        return simulation.getDarsena1()
                .getServingClient()
                .map(client -> Integer.toString(client.getClientNumber()))
                .orElse(NONE_SYMBOL);
    }

    public String getgetDarsena1NextEvent() {
        return simulation.getDarsena1().getNextEnd().map(LocalDateTime::toString).orElse(NONE_SYMBOL);
    }

    /*
    Datos para Darsena 2.
     */
    public String getDarsena2State() {
        return simulation.getDarsena2().getState().toString();
    }

    public String getDarsena2Client() {
        return simulation.getDarsena2()
                .getServingClient()
                .map(client -> Integer.toString(client.getClientNumber()))
                .orElse(NONE_SYMBOL);
    }

    public String getgetDarsena2NextEvent() {
        return simulation.getDarsena2().getNextEnd().map(LocalDateTime::toString).orElse(NONE_SYMBOL);
    }

    /*
    Cola de darsenas.
     */

    public String getDarsenaQueueLenght() {
        return Integer.toString(simulation.getDarsenaQueue().size());
    }


    /*
    Cola de afuera.
     */

    public String getOutsideQueueLenght(){
        return Integer.toString(simulation.getOutsideQueue().size());
    }

    /*
    Estatisticas y conteos.
     */

    public String getNumberOfTrucksServed() {
        return Integer.toString(simulation.getTrucksServed());
    }

    public String getDay() {
        return Integer.toString(simulation.getDay());
    }

    public String getAverageDurationOfService() {
        return DoubleUtils.getDoubleWithFourPlaces(simulation.getAvgMinutesPerTruck());
    }

    public String getAverageTrucksOutside(){
        return DoubleUtils.getDoubleWithFourPlaces(simulation.getAvgTrucksOutside());
    }

    public String getTrucksServedPerDay() {
        return DoubleUtils.getDoubleWithFourPlaces(simulation.getTrucksServedPerDay());
    }


}
