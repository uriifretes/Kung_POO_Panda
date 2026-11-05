import java.time.LocalDate;
import java.time.YearMonth;

public class Expensas {
    private YearMonth periodo;
    private double importe;
    private LocalDate expensas;
    private EstadosExpensa estado;
    private double saldoCapital;
    private double interesesPagados;
    private List<Pagos> pagos;
}
