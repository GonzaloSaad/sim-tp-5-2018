package utn.frc.sim.model.clients;

import java.time.LocalDateTime;

public class Client {
    private int clientNumber;
    private LocalDateTime inTime;
    private LocalDateTime outTime;


    public Client(int clientNumber) {
        this.clientNumber = clientNumber;
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

    @Override
    public String toString() {
        return "Client{" +
                "clientNumber=" + clientNumber +
                ", inTime=" + inTime +
                ", outTime=" + outTime +
                '}';
    }
}
