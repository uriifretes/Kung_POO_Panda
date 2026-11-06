package Enums;

public enum TipoReclamo {
    SEGURIDAD(Prioridad.ALTA),
    CONVIVENCIA(Prioridad.MEDIA),
    MANTENIMIENTO(Prioridad.BAJA),
    ADMINISTRATIVO(Prioridad.BAJA);

    private final Prioridad prioridadPorDefecto;

    TipoReclamo(Prioridad prioridadPorDefecto){
        this.prioridadPorDefecto = prioridadPorDefecto;
    }

    public Prioridad getPrioridad(){
        return prioridadPorDefecto;
    }
}
