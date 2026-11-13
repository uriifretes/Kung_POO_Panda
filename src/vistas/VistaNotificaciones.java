package vistas;

import notificaciones.Notificable;
import notificaciones.Notificaciones;

import java.util.List;

public class VistaNotificaciones {

    // Muestra las notificaciones (las mas nuevas primero) y las marca como leidas
    public void mostrar(Notificable destinatario) {
        Consola.titulo("Notificaciones");
        List<Notificaciones> lista = destinatario.verNotificaciones();
        if (lista.isEmpty()) {
            System.out.println("No tiene notificaciones.");
        }
        for (int i = 0; i < lista.size(); i++) {
            Notificaciones n = lista.get(i);
            String marca = n.isLeida() ? "      " : "[NUEVA]";
            System.out.println(marca + " " + Consola.fechaHora(n.getFechaHora()) + " | " + n.getTipo()
                    + " | " + n.getMensajes());
            n.marcarcomoLeida();
        }
    }
}