import Enums.EstadoReclamo;

import java.time.LocalDateTime;

public class CambiosDeEstado {
    private LocalDateTime fechaHora;
    private Enums.EstadoReclamo estadoNuevo;
    private String observacion;

    public CambiosDeEstado(LocalDateTime fechaHora, String observacion, EstadoReclamo estadoNuevo) {
        if (fechaHora==null||estadoNuevo==null||observacion==null){
            throw new IllegalArgumentException("Has ingresado datos invalidos");
        }
        this.fechaHora = fechaHora;
        this.estadoNuevo = estadoNuevo;
        this.observacion = observacion;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public EstadoReclamo getEstadoNuevo() {
        return estadoNuevo;
    }

    public String getObservacion() {
        return observacion;
    }
}
