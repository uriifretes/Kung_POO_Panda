import java.time.LocalDate;
import java.time.LocalDateTime;

public class Fallas {
    private LocalDateTime fechaHora;
    private String descripcion;
    private String reportadaPor;

    public Fallas(LocalDateTime fechaHora, String descripcion, String reportadaPor) {
        this.fechaHora = fechaHora;
        this.descripcion = descripcion;
        this.reportadaPor = reportadaPor;
    }
}
