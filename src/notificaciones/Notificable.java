package notificaciones;

import java.util.List;

public interface Notificable {
    void agregarNotificacion(Notificaciones notificacion);
    List<Notificaciones> verNotificaciones();
}
