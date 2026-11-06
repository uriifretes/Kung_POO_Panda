import Enums.EstadoExpensa;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class Expensas {
    private YearMonth periodo;
    private double importe;
    private LocalDate vencimiento;
    private Enums.EstadoExpensa estado;
    private double saldoCapital;
    private double interesesPagados;
    private List<Pagos> pagos;
    private List<AsientosDeAjuste> asientos;

    public Expensas(YearMonth periodo, double importe, LocalDate vencimiento) {
        this.periodo = periodo;
        this.importe = importe;
        this.vencimiento = vencimiento;
        this.estado = EstadoExpensa.PENDIENTE;
        this.saldoCapital = 0;
        this.interesesPagados = 0;
        this.pagos = new ArrayList<>();
        this.asientos = new ArrayList<>();
    }
}
