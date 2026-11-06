import java.util.ArrayList;
import java.util.List;

public class ReportesReclamosPendientes implements Reportes {
    private List<Reclamos> reclamos;

    public ReportesReclamosPendientes(List<Reclamos> reclamos) {
        if (reclamos==null){
            throw new IllegalArgumentException("Has ingresado datos invalidos");
        }
        this.reclamos = reclamos;
    }
}
