package visitas;

import personal.GuardiasDeSeguridad;

import java.time.LocalDateTime;

public class Ingresos {
    private LocalDateTime fechaHoraIngreso;
    private LocalDateTime fechaHoraEgreso;
    private GuardiasDeSeguridad guardia;

    public Ingresos(LocalDateTime fechaHoraIngreso, GuardiasDeSeguridad guardia) {
        if (fechaHoraIngreso==null||guardia==null){
            throw new IllegalArgumentException("Has ingresado datos invalidos");
        }
        this.fechaHoraIngreso = fechaHoraIngreso;
        this.fechaHoraEgreso = null;
        this.guardia = guardia;
    }

    public LocalDateTime getFechaHoraIngreso() {
        return fechaHoraIngreso;
    }

    public LocalDateTime getFechaHoraEgreso() {
        return fechaHoraEgreso;
    }

    public boolean estaAdentro(){
        if (fechaHoraEgreso==null){
            return true;
        }
        else {
            return false;
        }
    }
    public void registrarEgreso(LocalDateTime momento) {
        if (momento == null || momento.isBefore(fechaHoraIngreso)) {
            throw new IllegalArgumentException("La salida no puede ser anterior a la entrada");
        }
        if (fechaHoraEgreso != null) {
            throw new IllegalStateException("La salida ya fue registrada");
        }
        fechaHoraEgreso = momento;
    }
}
