import Enums.EstadoReclamo;
import Enums.Prioridad;
import Enums.TipoReclamo;
import Excepciones.LimiteReclamosException;
import Excepciones.TransicionInvalidaException;

import java.time.LocalDate;
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
    private Lotes lote;
    private Personas presentadoPor;
    private Personal responsable;

    public PedidosVecinos(int numero, LocalDateTime fechaHora, TipoReclamo tipo, String descripcion, Lotes lotes, Personas presentadoPor) {
        if (numero<=0||fechaHora==null||tipo==null||descripcion==null|| lotes == null|| presentadoPor == null){
            throw new IllegalArgumentException("Has ingresado datos invalidos");
        }
        if (!lotes.puedePresentarReclamos(presentadoPor)) {
            throw new IllegalArgumentException("Solo el titular o un residente autorizado puede presentar reclamos");
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
        this.lote = lotes;
        this.presentadoPor = presentadoPor;
        this.responsable = null;

        lote.agregarPedidos(this);
        historial.add(new CambiosDeEstado(fechaHora,"Pedido creado", EstadoReclamo.ABIERTO));
    }

    public int getNumero() {
        return numero;
    }

    public Prioridad getPrioridad() {
        return tipo.getPrioridad();
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public TipoReclamo getTipo() {
        return tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public EstadoReclamo getEstado() {
        return estado;
    }

    public boolean isEscalado() {
        return escalado;
    }

    public LocalDateTime getFechaResolucion() {
        return fechaResolucion;
    }

    public List<CambiosDeEstado> getHistorial() {
        List<CambiosDeEstado> devuelvo = new ArrayList<>();
        for (int i = 0; i < historial.size(); i++) {
            devuelvo.add(historial.get(i));
        }
        return devuelvo;
    }

    public List<Comentarios> getComentarios() {
        List<Comentarios> devuelvo = new ArrayList<>();
        for (int i = 0; i < comentarios.size(); i++) {
            devuelvo.add(comentarios.get(i));
        }
        return devuelvo;
    }

    public Lotes getLote() {
        return lote;
    }

    public Personas getPresentadoPor() {
        return presentadoPor;
    }

    public Personal getResponsable() {
        return responsable;
    }

    private void cambiarEstado(EstadoReclamo nuevo, LocalDateTime momento, String observacion){
        estado = nuevo;
        historial.add(new CambiosDeEstado(momento, observacion, nuevo));
    }

    public void asignar(Personal p, LocalDateTime momento) throws TransicionInvalidaException, LimiteReclamosException{
        if (estado!=EstadoReclamo.ABIERTO){
            throw new TransicionInvalidaException("Solo se pueden asignar reclamos abiertos");
        }
        if (p==null){
            throw new IllegalArgumentException("Has ingresado datos incorrectos");
        }
        if (p.cantidadenCurso()>=5){
            throw new LimiteReclamosException("Alcanzo el limite de reclamos");
        }
        if (!p.puedeAtender(tipo)) {
            throw new IllegalArgumentException("Ese personal no atiende reclamos de tipo " + tipo);
        }
        responsable = p;
        p.asignarReclamo(this);
        cambiarEstado(EstadoReclamo.ASIGNADO, momento, "Asignado a " + p.getNombre());
    }
    public void avanzar(LocalDateTime momento, String observacion) throws TransicionInvalidaException{
        if (estado==EstadoReclamo.ABIERTO){
            throw new TransicionInvalidaException("El reclamo primero debe ser asignado");
        }
        if (estado==EstadoReclamo.ASIGNADO){
            estado= EstadoReclamo.EN_CURSO;
            historial.add(new CambiosDeEstado(momento, observacion, estado));
        }
        else if (estado==EstadoReclamo.EN_CURSO){
            estado= EstadoReclamo.RESUELTO;
            fechaResolucion = momento;
            historial.add(new CambiosDeEstado(momento, observacion, estado));
        }
        else if (estado==EstadoReclamo.RESUELTO){
            estado= EstadoReclamo.CERRADO;
            historial.add(new CambiosDeEstado(momento, observacion, estado));
        }
        else if (estado==EstadoReclamo.CERRADO){
            throw new TransicionInvalidaException("Es reclamo ya termino");
        }
    }

    public void cancelar(LocalDateTime momento) throws TransicionInvalidaException {
        if (estado != EstadoReclamo.ABIERTO) {
            throw new TransicionInvalidaException("Solo se puede cancelar un pedido abierto");
        }
        cambiarEstado(EstadoReclamo.CERRADO, momento, "Cancelada por el vecino");
    }


}
