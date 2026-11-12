package personal;

import reclamos.PedidosVecinos;

import Enums.EstadoReclamo;
import Enums.TipoReclamo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public abstract class Personal implements Serializable {
    private int legajo;
    private String nombre;
    private LocalDate fechaIngreso;
    private List<PedidosVecinos> reclamosAsignados;

    public Personal(int legajo, String nombre, LocalDate fechaIngreso) {
        if (legajo<=0||nombre==null||fechaIngreso==null){
            throw new IllegalArgumentException("Has ingresado datos invalidos");
        }
        this.legajo = legajo;
        this.nombre = nombre;
        this.fechaIngreso = fechaIngreso;
        this.reclamosAsignados = new ArrayList<>();
    }

    public int getLegajo() {
        return legajo;
    }

    public String getNombre() {
        return nombre;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public abstract boolean puedeAtender(TipoReclamo tipo);

    public void asignarReclamo(PedidosVecinos p){
        if (p ==null){
            throw new IllegalArgumentException("Ingresaste datos incorrectos");
        }
        reclamosAsignados.add(p);
    }

    public int cantidadenCurso(){
        int cant = 0;
        for (int i = 0; i < reclamosAsignados.size(); i++) {
            if (reclamosAsignados.get(i).getEstado()== EstadoReclamo.EN_CURSO){
                cant=cant+1;
            }

        }
        return cant;
    }
}
