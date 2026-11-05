import com.sun.source.doctree.SeeTree;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Reclamos {
    private int numero;
    private LocalDateTime fechaHora;
    private TipoReclamo tipo;
    private String descripcion;
    private EstadoReclamo estado;
    private boolean esSolicitud;
    private boolean escalado;
    private LocalDateTime fechaResolucion;
    private Lotes lote;
    private Personas presentadoPor;
    private Personal responsable;
    private List<CambiosEstado> historial;
    private List<Comentarios> comentarios;
}
