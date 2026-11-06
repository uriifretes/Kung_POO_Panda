import Enums.TipoNotificacion;

import java.time.LocalDateTime;

public class Notificaciones {
    private LocalDateTime fechaHora;
    private Enums.TipoNotificacion tipo;
    private String mensajes;
    private boolean leida;
    private Notificable destinatario;

    public Notificaciones(LocalDateTime fechaHora, TipoNotificacion tipo, String mensajes, Notificable destinatario) {
        this.fechaHora = fechaHora;
        this.tipo = tipo;
        this.mensajes = mensajes;
        this.leida = false;
        this.destinatario = destinatario;
    }
}
