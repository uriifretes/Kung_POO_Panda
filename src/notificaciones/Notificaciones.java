package notificaciones;

import Enums.TipoNotificacion;

import java.time.LocalDateTime;
import java.io.Serializable;

public class Notificaciones implements Serializable {
    private LocalDateTime fechaHora;
    private Enums.TipoNotificacion tipo;
    private String mensajes;
    private boolean leida;
    private Notificable destinatario;

    public Notificaciones(LocalDateTime fechaHora, TipoNotificacion tipo, String mensajes, Notificable destinatario) {
        if (fechaHora==null||tipo==null||mensajes==null||destinatario==null){
            throw new IllegalArgumentException("Has ingresado datos invalidos");
        }
        this.fechaHora = fechaHora;
        this.tipo = tipo;
        this.mensajes = mensajes;
        this.leida = false;
        this.destinatario = destinatario;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public TipoNotificacion getTipo() {
        return tipo;
    }

    public String getMensajes() {
        return mensajes;
    }

    public boolean isLeida() {
        return leida;
    }

    public Notificable getDestinatario() {
        return destinatario;
    }

    public void marcarcomoLeida() {
        leida = true;
    }
}
