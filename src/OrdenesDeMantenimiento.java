import Enums.Prioridad;

import java.time.LocalDate;

public class OrdenesDeMantenimiento {
    private int numero;
    private LocalDate fechaApertura;
    private Enums.Prioridad prioridad;
    private String descripcionTrabajo;
    private LocalDate fechaCierre;
    private String descripcionReparacion;
    private EmpleadosDeMantenimiento tecnico;

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
        this.tecnico = null;
    }

    public int getNumero() {
        return numero;
    }

    public LocalDate getFechaApertura() {
        return fechaApertura;
    }

    public Prioridad getPrioridad() {
        return prioridad;
    }

    public String getDescripcionTrabajo() {
        return descripcionTrabajo;
    }

    public LocalDate getFechaCierre() {
        return fechaCierre;
    }

    public String getDescripcionReparacion() {
        return descripcionReparacion;
    }

    public EmpleadosDeMantenimiento getTecnico() {
        return tecnico;
    }

    public boolean estaAbierta(){
        if (fechaCierre==null){
            return true;
        }
        return false;
    }
    public void cerrar(LocalDate fecha, EmpleadosDeMantenimiento Tecnico, String descripcion){
        if (!estaAbierta()){
            throw new IllegalStateException("No se puede cerrrar una orden no abierta");
        }
        if (fecha==null||Tecnico==null||descripcion==null||fecha.isBefore(fechaApertura)){
            throw new IllegalArgumentException("Ingresaste datos invalidos");
        }
        fechaCierre = fecha;
        tecnico = Tecnico;
        descripcionReparacion = descripcion;
    }
}
