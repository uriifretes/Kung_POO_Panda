import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReportesMorosidad implements Reportes {
    private List<Lotes> lotes;
    private LocalDate fecha;

    public ReportesMorosidad(LocalDate fecha, List<Lotes> lotes) {
        this.lotes = lotes;
        this.fecha = fecha;
    }
}
