package utn.frc.sim.simulation;

public enum Events {
    INICIO("Inicio."),
    INICIO_DEL_DIA("Inicio del dia."),
    LLEGADA_CLIENTE("Llegada de camion."),
    FIN_RECEPCION("Fin de recepcion."),
    FIN_BALANZA("Fin de balanza."),
    FIN_DARSENA_1("Fin de atencion darsena 1."),
    FIN_DARSENA_2("Fin de atencion darsena 2."),
    FIN_DARSENA_1_CALIBRACION("Fin de calibracion darsena 1."),
    FIN_DARSENA_2_CALIBRACION("Fin de calibracion darsena 2.");

    private final String text;

    Events(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
