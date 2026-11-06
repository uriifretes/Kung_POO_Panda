import java.time.LocalDate;
import java.time.LocalTime;

public class AutorizacionesUnicas extends Autorizaciones{
    private LocalDate fecha;

    public AutorizacionesUnicas(Visitantes visitante, LocalTime horaDesde, LocalTime horaHasta, LocalDate fecha) {
        super(visitante, horaDesde, horaHasta);
        if (fecha==null){
            throw new IllegalArgumentException("Has ingresado datos invalidos");
        }
        this.fecha = fecha;
    }
}
