import java.time.LocalDate;
import java.time.LocalTime;

public class AutorizacionesUnicas extends Autorizaciones{
    private LocalDate fecha;

    public AutorizacionesUnicas(Visitantes visitante, LocalTime horaHasta, LocalTime horaDesde, LocalDate fecha) {
        super(visitante, horaHasta, horaDesde);
        this.fecha = fecha;
    }
}
