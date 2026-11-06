import java.time.LocalTime;
import java.util.List;

public abstract class Autorizaciones {
    private LocalTime horaDesde;
    private LocalTime horaHasta;
    private boolean cancelada;
    private Visitantes visitante;
    private List<Ingresos> ingresos;
}
