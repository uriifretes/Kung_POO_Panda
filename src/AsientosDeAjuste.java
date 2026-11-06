import java.time.LocalDate;

public class AsientosDeAjuste {
    private LocalDate fecha;
    private double monto;
    private String motivo;

    public AsientosDeAjuste(LocalDate fecha, double monto, String motivo) {
        this.fecha = fecha;
        this.monto = monto;
        this.motivo = motivo;
    }
}
