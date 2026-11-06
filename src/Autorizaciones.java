import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public abstract class Autorizaciones {
    private LocalTime horaDesde;
    private LocalTime horaHasta;
    private boolean cancelada;
    private Visitantes visitante;
    private List<Ingresos> ingresos;

    public Autorizaciones(Visitantes visitante,LocalTime horaDesde, LocalTime horaHasta) {
        this.visitante = visitante;
        this.cancelada = false;
        this.horaHasta = horaHasta;
        this.horaDesde = horaDesde;
        this.ingresos = new ArrayList<>();
    }
}
