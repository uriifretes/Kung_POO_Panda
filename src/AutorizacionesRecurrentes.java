import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AutorizacionesRecurrentes extends Autorizaciones {
    private LocalDate fechaDesde;
    private LocalDate fechaHasta;

    public AutorizacionesRecurrentes(Visitantes visitante, LocalTime horaDesde, LocalTime horaHasta, LocalDate fechaDesde, LocalDate fechaHasta) {
        super(visitante, horaDesde, horaHasta);
        if (fechaDesde==null|| fechaHasta==null|| !fechaDesde.isBefore(fechaHasta)){
            throw new IllegalArgumentException("Has ingresado datos invalidos");
        }
        this.fechaDesde = fechaDesde;
        this.fechaHasta = fechaHasta;
    }
}
