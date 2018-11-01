package utn.frc.sim.model;

import java.util.Objects;

public class KeyOfEuler {
    private double k;
    private double l;

    public KeyOfEuler(double k, double l) {
        this.k = k;
        this.l = l;
    }

    public double getK() {
        return k;
    }

    public void setK(double k) {
        this.k = k;
    }

    public double getL() {
        return l;
    }

    public void setL(double l) {
        this.l = l;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KeyOfEuler that = (KeyOfEuler) o;
        return Double.compare(that.k, k) == 0 &&
                Double.compare(that.l, l) == 0;
    }

    @Override
    public int hashCode() {

        return Objects.hash(k, l);
    }
}
