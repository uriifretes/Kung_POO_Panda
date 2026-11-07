import Enums.Especialidad;
import Enums.TipoReclamo;

import java.time.LocalDate;

public class EmpleadosDeMantenimiento extends Personal{
    private Enums.Especialidad especialidad;

    public EmpleadosDeMantenimiento(int legajo, String nombre, LocalDate fechaIngreso, Especialidad especialidad) {
        super(legajo, nombre, fechaIngreso);
        if (especialidad==null){
            throw new IllegalArgumentException("Has ingresado datos invalidos");
        }
        this.especialidad = especialidad;
    }
    @Override
    public boolean puedeAtender(TipoReclamo tipo){
        if (tipo== TipoReclamo.MANTENIMIENTO){
            return true;
        }
        return false;
    }
}
