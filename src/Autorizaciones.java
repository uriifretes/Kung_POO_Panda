import java.time.LocalTime;

public abstract class Autorizaciones {
    private Visitantes visitante;
    private Lotes lote;
    private Personas autorizante;
    private LocalTime horaDesde;
    private LocalTime horaHasta;
    private boolean cancelada;
}
