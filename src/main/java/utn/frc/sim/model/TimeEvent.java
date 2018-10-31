package utn.frc.sim.model;

import utn.frc.sim.generators.distributions.DistributionRandomGenerator;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class TimeEvent {
    private DistributionRandomGenerator generator;

    public TimeEvent(DistributionRandomGenerator generator) {
        this.generator = generator;
    }

    /**
     * Metodo que calcula el proximo evento desde una fecha, basado en una distribucion.
     * Si el random es menor a 0, se supone que esta en cantidad de horas de un dia, es
     * decir, si el random es 0.5 quiere decir que son 12 horas. El valor 1 refiere a 24 horas.
     * Si el random es mayor a 1, se supone que la cantidad esta en minutos.
     */
    public LocalDateTime calculateNextEventFromRandom(LocalDateTime timeFrom) {
        double random = generator.random();
        if (random < 1) {
            return calculateNextEventFromDays(timeFrom, random);
        }
        return calculateNextEventFromMinutes(timeFrom, random);
    }

    private LocalDateTime calculateNextEventFromMinutes(LocalDateTime timeFrom, double random) {

        int minutes = (int) random;
        int seconds = (int) ((random - minutes) * 60);

        LocalDateTime nextEvent = timeFrom.plus(minutes, ChronoUnit.MINUTES);
        return nextEvent.plus(seconds, ChronoUnit.SECONDS);

    }

    private LocalDateTime calculateNextEventFromDays(LocalDateTime timeFrom, double random) {
        return calculateNextEventFromMinutes(timeFrom, (random * 60));
    }
}
