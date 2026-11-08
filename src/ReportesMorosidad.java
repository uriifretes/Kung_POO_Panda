import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReportesMorosidad implements Reportes {
    private List<Lotes> lotes;
    private LocalDate fecha;

    public ReportesMorosidad(LocalDate fecha, List<Lotes> lotes) {
        if (lotes==null||fecha==null){
            throw new IllegalArgumentException("Has ingresado datos invalidos");
        }
        this.lotes = lotes;
        this.fecha = fecha;
    }
    @Override
    public String generar() {
        String texto = "REPORTE DE MOROSIDAD: " + "\n";
        int cantidad = 0;
        for (int i = 0; i < lotes.size(); i++) {
            Lotes l = lotes.get(i);
            if (l.estaMoroso()) {
                cantidad = cantidad + 1;
                texto = texto + cantidad + "- Lote: " + l.getNumero()+ "\n";
                texto = texto + "Cantidad de periodos impagos: " + l.getCuentaCorriente().cantidadPeriodosImpagos(fecha)+ "\n";
                texto = texto + "Deuda Total: " + l.getCuentaCorriente().deudaTotal(fecha) + "\n";
                texto = texto + "\n";
            }
        }
        if (cantidad == 0) {
            texto = texto + "No hay lotes morosos\n";
        }

        return texto;
    }
}
