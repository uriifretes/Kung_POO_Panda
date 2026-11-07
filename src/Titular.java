import Enums.TipoReclamo;

import java.util.ArrayList;
import java.util.List;

public class Titular extends Personas implements Notificable {
    private List<Notificaciones> notificaciones;

    public Titular(String nombre, String apellido, String documento, String telefono, String correo) {
        super(nombre, apellido, documento, telefono, correo);
        this.notificaciones = new ArrayList<>();
    }
    public boolean puedeAtender(TipoReclamo tipo){
        if (tipo== TipoReclamo.ADMINISTRATIVO){
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public void agregarNotificacion(Notificaciones n){
        if (n==null){
            throw new IllegalArgumentException("El pedido no puede ser nulo");
        }
        notificaciones.add(n);
    }
    @Override
    public List<Notificaciones> verNotificaciones() {
        List<Notificaciones> resultado = new ArrayList<>();
        for (int i = notificaciones.size() - 1; i >= 0; i--) {
            resultado.add(notificaciones.get(i));
        }
        return resultado;
    }

}
