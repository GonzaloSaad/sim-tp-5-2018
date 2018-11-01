package utn.frc.sim.simulation;

import utn.frc.sim.util.DoubleUtils;

import java.text.SimpleDateFormat;
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

    public String getDarsena1ClientK() {
        return simulation.getDarsena1()
                .getServingClient()
                .map(client -> DoubleUtils.getDoubleWithFourPlaces(client.getK()))
                .orElse(NONE_SYMBOL);
    }

    public String getDarsena1ClientCapacity() {
        return simulation.getDarsena1()
                .getServingClient()
                .map(client -> DoubleUtils.getDoubleWithFourPlaces(client.getCapacity()))
                .orElse(NONE_SYMBOL);
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

    public String getDarsena2ClientK() {
        return simulation.getDarsena2()
                .getServingClient()
                .map(client -> DoubleUtils.getDoubleWithFourPlaces(client.getK()))
                .orElse(NONE_SYMBOL);
    }

    public String getDarsena2ClientCapacity() {
        return simulation.getDarsena2()
                .getServingClient()
                .map(client -> DoubleUtils.getDoubleWithFourPlaces(client.getCapacity()))
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
    Estatisticas y conteos.
     */

    public String getNumberOfTrucksServed() {
        return String.valueOf(simulation.getTrucksServed());
    }

    public String getDay() {
        return Integer.toString(simulation.getDay());
    }

    public String getAverageDurationOfService() {
        return String.valueOf(DoubleUtils.round(simulation.getAvgMinutesPerTruck(), 2)) + " min"; //Ya se que es una negrada gonza, no jodas =D
    }

    public String getAmountOfTrucksOutside(){
        return String.valueOf(simulation.getAvgTrucksOutside());
    }

    public String getTrucksServedPerDay() {
        return DoubleUtils.getDoubleWithFourPlaces(simulation.getTrucksServedPerDay());
    }

    public boolean verifyRowToAddToTable(String txtFromDay, String txtToDay, String txtFromHour, String txtToHour){
        if(txtFromDay.equals("")) txtFromDay = "1";
        if(txtToDay.equals("")) txtToDay = "30";
        if(txtToHour.equals("")) txtToHour = "20";
        if(txtFromHour.equals("")) txtFromHour = "5";
        return  isInDayRange(Integer.parseInt(txtFromDay), Integer.parseInt(txtToDay)) &&
                isInHourRange(Integer.parseInt(txtFromHour), Integer.parseInt(txtToHour));

    }

    private boolean isInDayRange(Integer fromDay, Integer toDay){
        return simulation.getDay() >= fromDay && simulation.getDay() <= toDay;
    }
    private boolean isInHourRange(Integer fromHour, Integer toHour){
        toHour--;
        return simulation.getClock().getHour() >= fromHour && simulation.getClock().getHour() <= toHour;
    }

}
