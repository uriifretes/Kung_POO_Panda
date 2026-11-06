import Enums.Prioridad;

import java.time.LocalDate;

public class OrdenesDeMantenimiento {
    private int numero;
    private LocalDate fechaApertura;
    private Enums.Prioridad prioridad;
    private String descripcionTrabajo;
    private LocalDate fechaCierre;
    private String descripcionReparacion;

    public OrdenesDeMantenimiento(int numero, LocalDate fechaApertura, Prioridad prioridad, String descripcionTrabajo) {
        if (numero<=0||fechaApertura==null||prioridad==null||descripcionTrabajo==null ){
            throw new IllegalArgumentException("Has ingresado datos invalidos");
        }
        this.numero = numero;
        this.fechaApertura = fechaApertura;
        this.prioridad = prioridad;
        this.descripcionTrabajo = descripcionTrabajo;
        this.fechaCierre = null;
        this.descripcionReparacion = null;
    }
}
