import Enums.EstadoExpensa;
import Enums.MedioPago;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
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
        if (periodo==null|| importe<=0|| vencimiento==null){
            throw new IllegalArgumentException("Has ingresado datos invalidos");
        }
        this.periodo = periodo;
        this.importe = importe;
        this.vencimiento = vencimiento;
        this.estado = EstadoExpensa.PENDIENTE;
        this.saldoCapital = importe;
        this.interesesPagados = 0;
        this.pagos = new ArrayList<>();
        this.asientos = new ArrayList<>();
    }

    public YearMonth getPeriodo() {
        return periodo;
    }

    public double getImporte() {
        return importe;
    }

    public LocalDate getVencimiento() {
        return vencimiento;
    }

    public EstadoExpensa getEstado() {
        return estado;
    }

    public double getSaldoCapital() {
        return saldoCapital;
    }

    public double getInteresesPagados() {
        return interesesPagados;
    }

    public List<Pagos> getPagos() {
        List<Pagos> devuelvo = new ArrayList<>();
        for (int i = 0; i < pagos.size(); i++) {
            devuelvo.add(pagos.get(i));
        }
        return devuelvo;
    }

    public List<AsientosDeAjuste> getAsientos() {
        List<AsientosDeAjuste> devuelvo = new ArrayList<>();
        for (int i = 0; i < asientos.size(); i++) {
            devuelvo.add(asientos.get(i));
        }
        return devuelvo;
    }

    public boolean estaVencida(LocalDate fecha){
        if (fecha==null){
            throw new IllegalArgumentException("Debes ingresar una fecha valida");
        }

        if (fecha.isBefore(vencimiento)){
            return true;
        }
        return false;
    }

    public long mesesDeAtraso(LocalDate fecha) {
        if (!estaVencida(fecha)) {
            return 0;
        }
        long meses = ChronoUnit.MONTHS.between(vencimiento, fecha); ////El months between cuenta meses completos, y el
        if (vencimiento.plusMonths(meses).isBefore(fecha)) { /// if hace que si es un mes y un poco mas suma 1 mes mas
            meses = meses + 1;
        }
        return meses;
    }

    public double calcularIntereses(LocalDate fecha){
        if (saldoCapital*0.03*(mesesDeAtraso(fecha))-interesesPagados<0){
            return 0;
        }
        else {
            return (saldoCapital*0.03*(mesesDeAtraso(fecha))-interesesPagados);
        }
    }

    public double deuda(LocalDate fecha){
        return (saldoCapital+ calcularIntereses(fecha));
    }

    public boolean estaImpaga(LocalDate fecha){
        if (fecha.isAfter(vencimiento)&&saldoCapital>0){
            return true;
        }
        else {
            return false;
        }
    }

    public void registrarPago(double monto, MedioPago medio, String numeroOperacion, LocalDate fecha){
        double interes = calcularIntereses(fecha);
        double aInteres = monto;
        if (aInteres > interes) {
            aInteres = interes;
        }

        double sobrante = monto - aInteres;
        double aCapital = sobrante;
        if (aCapital > saldoCapital) {
            aCapital = saldoCapital;
        }

        Pagos p = new Pagos (fecha,aInteres + aCapital, medio, numeroOperacion, aInteres, aCapital);
        interesesPagados = interesesPagados + aInteres;
        saldoCapital = saldoCapital- aCapital;
        actualizarEstado();

    }

    private void actualizarEstado(){
        if (saldoCapital<=0){
            estado= EstadoExpensa.PAGADA;
        }
        else {
            estado= EstadoExpensa.PARCIAL;
        }
    }

    public void agregarAsiento(AsientosDeAjuste asiento){
        asientos.add(asiento);
        saldoCapital= saldoCapital + asiento.getMonto();
        actualizarEstado();
        if (saldoCapital<0){
            throw new IllegalArgumentException("El descuento no puede ser mejor que deuda");
        }
    }

}
