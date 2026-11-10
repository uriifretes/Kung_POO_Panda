import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReportesMorosidad implements Reportes {

    private final List<Lotes> lotes;
    private final LocalDate fecha;

    public ReportesMorosidad(LocalDate fecha, List<Lotes> lotes) {
        if (lotes == null || fecha == null) {
            throw new IllegalArgumentException(
                "La fecha y la lista de lotes son obligatorias"
            );
        }

        this.lotes = new ArrayList<>(lotes);
        this.fecha = fecha;
    }

    @Override
    public String generar() {
        StringBuilder resultado = new StringBuilder();
        resultado.append("REPORTE DE MOROSIDAD \n");
        resultado.append("Fecha de consulta: ").append(fecha).append('\n');

        boolean hayResultados = false;

        for (Lotes lote : lotes) {
            if (lote == null) {
                continue;
            }

            CuentasCorrientes cuenta = lote.getCuentaCorriente();
            double deudaTotal = cuenta.deudaTotal(fecha);

            if (deudaTotal > 0) {
                hayResultados = true;

                double capitalPendiente = 0;
                for (Expensas expensa : cuenta.getExpensas()) {
                    capitalPendiente += expensa.getSaldoCapital();
                }

                double intereses = deudaTotal - capitalPendiente;
                if (intereses < 0) {
                    intereses = 0;
                }

                resultado.append("Lote: ")
                        .append(lote.getNumero())
                        .append(" | Períodos impagos: ")
                        .append(cuenta.cantidadPeriodosImpagos(fecha))
                        .append(" | Capital: ")
                        .append(String.format("%.2f", capitalPendiente))
                        .append(" | Intereses: ")
                        .append(String.format("%.2f", intereses))
                        .append(" | Deuda total: ")
                        .append(String.format("%.2f", deudaTotal))
                        .append(" | Moroso: ")
                        .append(lote.estaMoroso() ? "Sí" : "No")
                        .append('\n');
            }
        }

        if (!hayResultados) {
            resultado.append("Sin resultados.\n");
        }

        return resultado.toString();
    }
}