import java.time.LocalDate;
import java.time.LocalDateTime;

public class Fallas {
    private LocalDateTime fechaHora;
    private String descripcion;
    private String reportadaPor;

    public Fallas(LocalDateTime fechaHora, String descripcion, String reportadaPor) {
        if (fechaHora==null||descripcion==null||reportadaPor==null){
            throw new IllegalArgumentException("Has ingresado datos invalidos");
        }
        this.fechaHora = fechaHora;
        this.descripcion = descripcion;
        this.reportadaPor = reportadaPor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getReportadaPor() {
        return reportadaPor;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }
}
