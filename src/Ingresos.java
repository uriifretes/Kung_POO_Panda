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

    public boolean estaAdentro(){
        if (fechaHoraEgreso==null){
            return true;
        }
        else {
            return false;
        }
    }
}
