package equipos;

import personal.EmpleadosDeMantenimiento;

import Enums.EstadoEquipo;
import Enums.Prioridad;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class Equipos {
    private String codigo;
    private LocalDate fechaInstalacion;
    private Enums.EstadoEquipo estado;
    private LocalDate fechaUltimoControl;
    private Sectores sector;
    private List<Fallas> fallas;
    private List<OrdenesDeMantenimiento> ordenes;

    public Equipos(String codigo, LocalDate fechaInstalacion, Sectores sector) {
        if (codigo==null||fechaInstalacion==null||sector==null){
            throw new IllegalArgumentException("Has ingresado datos invalidos");
        }
        this.codigo = codigo;
        this.fechaInstalacion = fechaInstalacion;
        this.estado = EstadoEquipo.OPERATIVO;
        this.fechaUltimoControl = null;
        this.sector = sector;
        this.fallas = new ArrayList<>();
        this.ordenes = new ArrayList<>();
        sector.agregarEquipos(this);
    }

    public String getCodigo() {
        return codigo;
    }

    public LocalDate getFechaInstalacion() {
        return fechaInstalacion;
    }

    public EstadoEquipo getEstado() {
        return estado;
    }

    public LocalDate getFechaUltimoControl() {
        return fechaUltimoControl;
    }

    public Sectores getSector() {
        return sector;
    }

    public abstract Prioridad prioridaddeFalla();

    public abstract String saberTipo();

    public void registrarFalla(LocalDateTime momento , String descripcion, String reportadapor){
        if (estado == EstadoEquipo.FUERA_DE_SERVICIO){
            throw new IllegalStateException("El equipo esta dado de baja");
        }
        Fallas f = new Fallas(momento, descripcion,reportadapor);
        fallas.add(f);
        if(estado== EstadoEquipo.OPERATIVO){
            estado= EstadoEquipo.CON_FALLA;
        }
    }

    public OrdenesDeMantenimiento generarOrden(String descripcion){
        if (estado != EstadoEquipo.CON_FALLA){
            throw new IllegalStateException("El equipo no falla");
        }
        OrdenesDeMantenimiento orden = new OrdenesDeMantenimiento(ordenes.size()+1, LocalDate.now(), prioridaddeFalla(), descripcion);
        ordenes.add(orden);
        estado = EstadoEquipo.EN_REPARACION;
        return orden;
    }

    public Fallas ultimaFalla(){
        if (fallas.isEmpty()){
            return null;
        }
        else {
            return fallas.get(fallas.size()-1);
        }
    }

    public void marcarFueraDeServicio(){
        estado = EstadoEquipo.FUERA_DE_SERVICIO;
    }

    public void registrarControl(LocalDate fecha){
        if (fecha==null){
            throw new IllegalArgumentException("Fecha invalida");
        }
        fechaUltimoControl = fecha;
    }

    public boolean estaFallando(){
        if (estado == EstadoEquipo.CON_FALLA || estado == EstadoEquipo.EN_REPARACION){
            return true;
        }
        else{
            return false;
        }
    }

    public OrdenesDeMantenimiento buscarOrden(int numero){
        for (int i = 0; i < ordenes.size(); i++) {
            if (ordenes.get(i).getNumero()==numero){
                return ordenes.get(i);
            }
        }
        return null;
    }

    public void cerrarOrden(int numero, LocalDate fecha, EmpleadosDeMantenimiento tecnico, String descripcion){
        OrdenesDeMantenimiento o = buscarOrden(numero);
        if (o==null){
            throw new IllegalArgumentException("No se encontro la orden");
        }
        o.cerrar(fecha,tecnico,descripcion);
        estado = EstadoEquipo.OPERATIVO;
    }



}
