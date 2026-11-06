import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Personal {
    private int legajo;
    private String nombre;
    private LocalDate fechaIngreso;
    private List<PedidosVecinos> reclamosAsignados;

    public Personal(int legajo, String nombre, LocalDate fechaIngreso) {
        if (legajo<=0||nombre==null||fechaIngreso==null){
            throw new IllegalArgumentException("Has ingresado datos invalidos");
        }
        this.legajo = legajo;
        this.nombre = nombre;
        this.fechaIngreso = fechaIngreso;
        this.reclamosAsignados = new ArrayList<>();
    }
}
