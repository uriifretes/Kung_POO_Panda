import java.time.LocalDate;

public class AsientosDeAjuste {
    private LocalDate fecha;
    private double monto;
    private String motivo;

    public AsientosDeAjuste(LocalDate fecha, double monto, String motivo) {
        if (fecha==null||monto==0||motivo==null){
            throw new IllegalArgumentException("Has ingresado datos invalidos");
        }
        this.fecha = fecha;
        this.monto = monto;
        this.motivo = motivo;
    }
}
