import Enums.EstadoEquipo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Equipos {
    private String codigo;
    private LocalDate fechaInstalacion;
    private Enums.EstadoEquipo estado;
    private LocalDate fechaUltimoControl;
    private Sectores sector;
    private List<Fallas> fallas;

    public Equipos(String codigo, LocalDate fechaInstalacion, Sectores sector) {
        this.codigo = codigo;
        this.fechaInstalacion = fechaInstalacion;
        this.estado = EstadoEquipo.OPERATIVO;
        this.fechaUltimoControl = null;
        this.sector = sector;
        this.fallas = new ArrayList<>();
    }
}
