import java.time.LocalDateTime;

public class Ingresos {
    private LocalDateTime fechaHoraIngreso;
    private LocalDateTime fechaHoraEgreso;
    private GuardiasDeSeguridad guardia;

    public Ingresos(LocalDateTime fechaHoraIngreso, GuardiasDeSeguridad guardia) {
        this.fechaHoraIngreso = fechaHoraIngreso;
        this.fechaHoraEgreso = null;
        this.guardia = guardia;
    }
}
