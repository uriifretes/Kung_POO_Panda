import Enums.EstadoReclamo;
import Enums.TipoReclamo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class PedidosVecinos  {
    private int numero;
    private LocalDateTime fechaHora;
    private Enums.TipoReclamo tipo;
    private String descripcion;
    private Enums.EstadoReclamo estado;
    private boolean escalado;
    private LocalDateTime fechaResolucion;
    private List<CambiosDeEstado> historial;
    private List<Comentarios> comentarios;

    public PedidosVecinos(int numero, LocalDateTime fechaHora, TipoReclamo tipo, String descripcion) {
        if (numero<=0||fechaHora==null||tipo==null||descripcion==null){
            throw new IllegalArgumentException("Has ingresado datos invalidos");
        }
        this.numero = numero;
        this.fechaHora = fechaHora;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.estado = EstadoReclamo.ABIERTO;
        this.escalado = false;
        this.fechaResolucion = null;
        this.historial = new ArrayList<>();
        this.comentarios = new ArrayList<>();
    }
}
