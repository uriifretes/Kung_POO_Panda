import java.time.LocalDate;

public abstract class Equipos {
    private String codigo;
    private LocalDate fechaInstalacion;
    private EstadosEquipo estado;
    private LocalDate fechaUltimoControl;
    private Sectores sector;
    private List<Fallas> fallas ;
    private List<OrdenesDeMantenimiento> ordenes ;
}
