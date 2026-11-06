import java.util.ArrayList;
import java.util.List;

public class Titular extends Personas implements Notificable {
    private List<Notificaciones> notificaciones;

    public Titular(String nombre, String apellido, String documento, String telefono, String correo) {
        super(nombre, apellido, documento, telefono, correo);
        this.notificaciones = new ArrayList<>();
    }
}
