import java.util.ArrayList;
import java.util.List;

public class ReportesReclamosPendientes implements Reportes {
    private List<Reclamos> reclamos;

    public ReportesReclamosPendientes(List<Reclamos> reclamos) {
        this.reclamos = reclamos;
    }
}
