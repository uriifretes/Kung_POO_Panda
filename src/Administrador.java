import Enums.TipoReclamo;
import Excepciones.LimiteReclamosException;
import Excepciones.TransicionInvalidaException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Administrador extends Personal implements Notificable {
    private List<Notificaciones> notificaciones;

    public Administrador(int legajo, String nombre, LocalDate fechaIngreso) {
        super(legajo, nombre, fechaIngreso);
        this.notificaciones = new ArrayList<>();
    }

    public boolean puedeAtender(TipoReclamo tipo) {
        if (tipo == TipoReclamo.ADMINISTRATIVO) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void agregarNotificacion(Notificaciones n) {
        if (n == null) {
            throw new IllegalArgumentException("La notificacion no puede ser nula");
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

    public void asignarReclamo(PedidosVecinos pedido, Personal personal, LocalDateTime momento) throws TransicionInvalidaException, LimiteReclamosException {
        pedido.asignar(personal, momento);
    }
}




