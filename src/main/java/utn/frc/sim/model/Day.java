package utn.frc.sim.model;

public class Day {
    private int day;
    private double avgMinutesPerTruck;
    private double avgTrucksOutside;
    private int trucksServed;

    public Day(int day, double avgMinutesPerTruck, double avgTrucksOutside, int trucksServed) {
        this.day = day;
        this.avgMinutesPerTruck = avgMinutesPerTruck;
        this.avgTrucksOutside = avgTrucksOutside;
        this.trucksServed = trucksServed;
    }

    public double getAvgMinutesPerTruck() {
        return avgMinutesPerTruck;
    }

    public void setAvgMinutesPerTruck(double avgMinutesPerTruck) {
        this.avgMinutesPerTruck = avgMinutesPerTruck;
    }

    public double getAvgTrucksOutside() {
        return avgTrucksOutside;
    }

    public void setAvgTrucksOutside(double avgTrucksOutside) {
        this.avgTrucksOutside = avgTrucksOutside;
    }

    public int getTrucksServed() {
        return trucksServed;
    }

    public void setTrucksServed(int trucksServed) {
        this.trucksServed = trucksServed;
    }

    @Override
    public String toString() {
        return "Day{" +
                "day=" + day +
                ", avgMinutesPerTruck=" + avgMinutesPerTruck +
                ", avgTrucksOutside=" + avgTrucksOutside +
                ", trucksServed=" + trucksServed +
                '}';
    }
}
