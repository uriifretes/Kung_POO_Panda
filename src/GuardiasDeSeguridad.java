import Enums.PuestoGuardia;
import Enums.Turno;

import java.time.LocalDate;

public class GuardiasDeSeguridad extends Personal {
    private Enums.Turno turno;
    private Enums.PuestoGuardia puesto;

    public GuardiasDeSeguridad(int legajo, String nombre, LocalDate fechaIngreso, Turno turno, PuestoGuardia puesto) {
        super(legajo, nombre, fechaIngreso);
        this.turno = turno;
        this.puesto = puesto;
    }
}
