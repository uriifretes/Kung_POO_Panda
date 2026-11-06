import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AutorizacionesRecurrentes extends Autorizaciones {
    private LocalDate fechaDesde;
    private LocalDate fechaHasta;

    public AutorizacionesRecurrentes(Visitantes visitante, LocalTime horaHasta, LocalTime horaDesde, LocalDate fechaDesde, LocalDate fechaHasta) {
        super(visitante, horaHasta, horaDesde);
        this.fechaDesde = fechaDesde;
        this.fechaHasta = fechaHasta;
    }
}
