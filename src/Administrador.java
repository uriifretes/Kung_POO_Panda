import java.util.ArrayList;
import java.util.List;

public class Administrador implements Notificable {
    private List<Notificaciones> notificaciones;

    public Administrador() {
        this.notificaciones = new ArrayList<>();
    }
}
