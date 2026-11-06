import java.time.LocalDate;
import java.util.List;

public abstract class Equipos {
    private String codigo;
    private LocalDate fechaInstalacion;
    private EstadosEquipo estado;
    private LocalDate fechaUltimoControl;
    private Sectores sector;
    private List<Fallas> fallas;
}
