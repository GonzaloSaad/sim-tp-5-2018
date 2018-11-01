package utn.frc.sim.model.clients;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Client {
    private int clientNumber;
    private LocalDateTime inTime;
    private LocalDateTime outTime;
    private double capacity;
    private double k;


    public Client(int clientNumber) {
        this.clientNumber = clientNumber;
    }

    public double getK() {
        return k;
    }

    public void setK(double k) {
        this.k = k;
    }

    public int getClientNumber() {
        return clientNumber;
    }

    public LocalDateTime getInTime() {
        return inTime;
    }

    public void setInTime(LocalDateTime inTime) {
        this.inTime = inTime;
    }

    public LocalDateTime getOutTime() {
        return outTime;
    }

    public void setOutTime(LocalDateTime outTime) {
        this.outTime = outTime;
    }

    public long getMinutesOfAttention(){
        if (inTime == null || outTime == null){
            throw new IllegalStateException();
        }
        return ChronoUnit.MINUTES.between(inTime, outTime);
    }

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "Client{" +
                "clientNumber=" + clientNumber +
                ", inTime=" + inTime +
                ", outTime=" + outTime +
                '}';
    }
}
