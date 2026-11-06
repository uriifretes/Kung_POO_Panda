import Enums.Especialidad;

import java.time.LocalDate;

public class EmpleadosDeMantenimiento extends Personal{
    private Enums.Especialidad especialidad;

    public EmpleadosDeMantenimiento(int legajo, String nombre, LocalDate fechaIngreso, Especialidad especialidad) {
        super(legajo, nombre, fechaIngreso);
        this.especialidad = especialidad;
    }
}
