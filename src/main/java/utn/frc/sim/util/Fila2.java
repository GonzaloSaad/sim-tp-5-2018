package utn.frc.sim.util;


import javafx.beans.property.SimpleStringProperty;

public class Fila2 {

    private final SimpleStringProperty truckServed;
    private final SimpleStringProperty day;
    private final SimpleStringProperty avg;
    private final SimpleStringProperty truckXDayServed;

    public Fila2(String truckServed, String day, String avg, String truckXDayServed) {
        this.truckServed = new SimpleStringProperty(truckServed);
        this.day = new SimpleStringProperty(day);
        this.avg = new SimpleStringProperty(avg);
        this.truckXDayServed = new SimpleStringProperty(truckXDayServed);
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

    public String getAvg() {
        return avg.get();
    }

    public SimpleStringProperty avgProperty() {
        return avg;
    }

    public void setAvg(String avg) {
        this.avg.set(avg);
    }

    public String getTruckXDayServed() {
        return truckXDayServed.get();
    }

    public SimpleStringProperty truckXDayServedProperty() {
        return truckXDayServed;
    }

    public void setTruckXDayServed(String truckXDayServed) {
        this.truckXDayServed.set(truckXDayServed);
    }
}
