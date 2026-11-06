import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Personal {
    private int legajo;
    private String nombre;
    private LocalDate fechaIngreso;
    private List<PedidosVecinos> reclamosAsignados;

    public Personal(int legajo, String nombre, LocalDate fechaIngreso) {
        this.legajo = legajo;
        this.nombre = nombre;
        this.fechaIngreso = fechaIngreso;
        this.reclamosAsignados = new ArrayList<>();
    }
}
