package personal;

import Enums.PuestoGuardia;
import Enums.TipoReclamo;
import Enums.Turno;

import java.time.LocalDate;

public class GuardiasDeSeguridad extends Personal {
    private Enums.Turno turno;
    private Enums.PuestoGuardia puesto;

    public GuardiasDeSeguridad(int legajo, String nombre, LocalDate fechaIngreso, Turno turno, PuestoGuardia puesto) {
        super(legajo, nombre, fechaIngreso);
        if (turno==null||puesto==null){
            throw new IllegalArgumentException("Has ingresado datos invalidos");
        }
        this.turno = turno;
        this.puesto = puesto;
    }

    @Override
    public boolean puedeAtender(TipoReclamo tipo){
        if (tipo== TipoReclamo.SEGURIDAD||tipo== TipoReclamo.CONVIVENCIA){
            return true;
        }
        return false;
    }

    public boolean esPortero(){
        if (puesto== PuestoGuardia.PORTERIA){
            return true;
        }
        else {
            return false;
        }
    }
}
