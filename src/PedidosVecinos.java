import java.time.LocalDateTime;
import java.util.List;

public abstract class PedidosVecinos  {
    private int numero;
    private LocalDateTime fechaHora;
    private TiposReclamo tipo;
    private String descripcion;
    private EstadosReclamo estado;
    private boolean escalado;
    private LocalDateTime fechaResolucion;
    private List<CambiosDeEstado> historial;
    private List<Comentarios> comentarios;
}
