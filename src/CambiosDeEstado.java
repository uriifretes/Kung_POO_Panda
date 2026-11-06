import Enums.EstadoReclamo;

import java.time.LocalDateTime;

public class CambiosDeEstado {
    private LocalDateTime fechaHora;
    private Enums.EstadoReclamo estadoNuevo;
    private String observacion;

    public CambiosDeEstado(LocalDateTime fechaHora, String observacion, EstadoReclamo estadoNuevo) {
        this.fechaHora = fechaHora;
        this.estadoNuevo = estadoNuevo;
        this.observacion = observacion;
    }
}
