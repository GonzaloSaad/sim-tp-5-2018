package utn.frc.sim.util;


import javafx.beans.property.SimpleStringProperty;

public class Fila {

    private final SimpleStringProperty event;
    private final SimpleStringProperty clock;
    private final SimpleStringProperty trucks;
    private final SimpleStringProperty nextArrival;
    private final SimpleStringProperty stateReception;
    private final SimpleStringProperty truckRec;
    private final SimpleStringProperty endRec;
    private final SimpleStringProperty queueRec;
    private final SimpleStringProperty stateBal;
    private final SimpleStringProperty truckBal;
    private final SimpleStringProperty endBal;
    private final SimpleStringProperty queueBal;
    private final SimpleStringProperty stateDar1;
    private final SimpleStringProperty truckDar1;
    private final SimpleStringProperty endDar1;
    private final SimpleStringProperty stateDar2;
    private final SimpleStringProperty truckDar2;
    private final SimpleStringProperty endDar2;
    private final SimpleStringProperty queueDar;
    private final SimpleStringProperty truckServed;
    private final SimpleStringProperty day;
    private final SimpleStringProperty litrosCamionD1;
    private final SimpleStringProperty litrosCamionD2;

    public Fila(String event, String clock,String trucks, String nextArrival, String stateReception, String truckRec, String endRec, String queueRec, String stateBal, String truckBal, String endBal, String queueBal, String stateDar1, String truckDar1, String endDar1, String stateDar2, String truckDar2, String endDar2, String queueDar, String truckServed, String day, String lD1, String lD2) {
        this.event =  new SimpleStringProperty(event);
        this.clock = new SimpleStringProperty(clock);
        this.trucks = new SimpleStringProperty(trucks);
        this.nextArrival = new SimpleStringProperty(nextArrival);
        this.stateReception = new SimpleStringProperty(stateReception);
        this.truckRec = new SimpleStringProperty(truckRec);
        this.endRec =new SimpleStringProperty(endRec);
        this.queueRec = new SimpleStringProperty(queueRec);
        this.stateBal = new SimpleStringProperty(stateBal);
        this.truckBal = new SimpleStringProperty(truckBal);
        this.endBal = new SimpleStringProperty(endBal);
        this.queueBal = new SimpleStringProperty(queueBal);
        this.stateDar1 = new SimpleStringProperty(stateDar1);
        this.truckDar1 = new SimpleStringProperty(truckDar1);
        this.endDar1 = new SimpleStringProperty(endDar1);
        this.stateDar2 = new SimpleStringProperty(stateDar2);
        this.truckDar2 = new SimpleStringProperty(truckDar2);
        this.endDar2 = new SimpleStringProperty(endDar2);
        this.queueDar = new SimpleStringProperty(queueDar);
        this.truckServed = new SimpleStringProperty(truckServed);
        this.day = new SimpleStringProperty(day);
        this.litrosCamionD1 = new SimpleStringProperty(lD1);
        this.litrosCamionD2 = new SimpleStringProperty(lD2);
    }

    public String getEvent() {
        return event.get();
    }

    public String getLitrosCamionD1() {
        return litrosCamionD1.get();
    }

    public SimpleStringProperty litrosCamionD1Property() {
        return litrosCamionD1;
    }

    public void setLitrosCamionD1(String litrosCamionD1) {
        this.litrosCamionD1.set(litrosCamionD1);
    }

    public String getLitrosCamionD2() {
        return litrosCamionD2.get();
    }

    public SimpleStringProperty litrosCamionD2Property() {
        return litrosCamionD2;
    }

    public void setLitrosCamionD2(String litrosCamionD2) {
        this.litrosCamionD2.set(litrosCamionD2);
    }

    public SimpleStringProperty eventProperty() {
        return event;
    }

    public void setEvent(String event) {
        this.event.set(event);
    }

    public String getClock() {
        return clock.get();
    }

    public SimpleStringProperty clockProperty() {
        return clock;
    }

    public void setClock(String clock) {
        this.clock.set(clock);
    }

    public String getTrucks() {
        return trucks.get();
    }

    public SimpleStringProperty trucksProperty() {
        return trucks;
    }

    public void setTrucks(String trucks) {
        this.trucks.set(trucks);
    }

    public String getNextArrival() {
        return nextArrival.get();
    }

    public SimpleStringProperty nextArrivalProperty() {
        return nextArrival;
    }

    public void setNextArrival(String nextArrival) {
        this.nextArrival.set(nextArrival);
    }

    public String getStateReception() {
        return stateReception.get();
    }

    public SimpleStringProperty stateReceptionProperty() {
        return stateReception;
    }

    public void setStateReception(String stateReception) {
        this.stateReception.set(stateReception);
    }

    public String getTruckRec() {
        return truckRec.get();
    }

    public SimpleStringProperty truckRecProperty() {
        return truckRec;
    }

    public void setTruckRec(String truckRec) {
        this.truckRec.set(truckRec);
    }

    public String getEndRec() {
        return endRec.get();
    }

    public SimpleStringProperty endRecProperty() {
        return endRec;
    }

    public void setEndRec(String endRec) {
        this.endRec.set(endRec);
    }

    public String getQueueRec() {
        return queueRec.get();
    }

    public SimpleStringProperty queueRecProperty() {
        return queueRec;
    }

    public void setQueueRec(String queueRec) {
        this.queueRec.set(queueRec);
    }

    public String getStateBal() {
        return stateBal.get();
    }

    public SimpleStringProperty stateBalProperty() {
        return stateBal;
    }

    public void setStateBal(String stateBal) {
        this.stateBal.set(stateBal);
    }

    public String getTruckBal() {
        return truckBal.get();
    }

    public SimpleStringProperty truckBalProperty() {
        return truckBal;
    }

    public void setTruckBal(String truckBal) {
        this.truckBal.set(truckBal);
    }

    public String getEndBal() {
        return endBal.get();
    }

    public SimpleStringProperty endBalProperty() {
        return endBal;
    }

    public void setEndBal(String endBal) {
        this.endBal.set(endBal);
    }

    public String getQueueBal() {
        return queueBal.get();
    }

    public SimpleStringProperty queueBalProperty() {
        return queueBal;
    }

    public void setQueueBal(String queueBal) {
        this.queueBal.set(queueBal);
    }

    public String getStateDar1() {
        return stateDar1.get();
    }

    public SimpleStringProperty stateDar1Property() {
        return stateDar1;
    }

    public void setStateDar1(String stateDar1) {
        this.stateDar1.set(stateDar1);
    }

    public String getTruckDar1() {
        return truckDar1.get();
    }

    public SimpleStringProperty truckDar1Property() {
        return truckDar1;
    }

    public void setTruckDar1(String truckDar1) {
        this.truckDar1.set(truckDar1);
    }

    public String getEndDar1() {
        return endDar1.get();
    }

    public SimpleStringProperty endDar1Property() {
        return endDar1;
    }

    public void setEndDar1(String endDar1) {
        this.endDar1.set(endDar1);
    }

    public String getStateDar2() {
        return stateDar2.get();
    }

    public SimpleStringProperty stateDar2Property() {
        return stateDar2;
    }

    public void setStateDar2(String stateDar2) {
        this.stateDar2.set(stateDar2);
    }

    public String getTruckDar2() {
        return truckDar2.get();
    }

    public SimpleStringProperty truckDar2Property() {
        return truckDar2;
    }

    public void setTruckDar2(String truckDar2) {
        this.truckDar2.set(truckDar2);
    }

    public String getEndDar2() {
        return endDar2.get();
    }

    public SimpleStringProperty endDar2Property() {
        return endDar2;
    }

    public void setEndDar2(String endDar2) {
        this.endDar2.set(endDar2);
    }

    public String getQueueDar() {
        return queueDar.get();
    }

    public SimpleStringProperty queueDarProperty() {
        return queueDar;
    }

    public void setQueueDar(String queueDar) {
        this.queueDar.set(queueDar);
    }

    public String getTruckServed() {
        return truckServed.get();
    }

    public SimpleStringProperty truckServedProperty() {
        return truckServed;
    }

    public void setTruckServed(String truckServed) {
        this.truckServed.set(truckServed);
    }

    public String getDay() {
        return day.get();
    }

    public SimpleStringProperty dayProperty() {
        return day;
    }

    public void setDay(String day) {
        this.day.set(day);
    }


}
