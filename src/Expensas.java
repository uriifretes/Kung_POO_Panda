import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public class Expensas {
    private YearMonth periodo;
    private double importe;
    private LocalDate vencimiento;
    private EstadosExpensa estado;
    private double saldoCapital;
    private double interesesPagados;
    private List<Pagos> pagos;
    private List<AsientosDeAjuste> asientos;
}
