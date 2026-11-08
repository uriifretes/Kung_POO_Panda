import Enums.EstadoEquipo;
import java.util.ArrayList;
import java.util.List;

public class ReportesEquiposFueraDeServicio implements Reportes {

    private final List<Equipos> equipos;

    public ReportesEquiposFueraDeServicio(List<Equipos> equipos) {
        if (equipos == null) {
            throw new IllegalArgumentException("La lista de equipos no puede ser nula");
        }

        this.equipos = new ArrayList<>(equipos);
    }

    @Override
    public String generar() {
        StringBuilder resultado = new StringBuilder();
        resultado.append("EQUIPOS FUERA DE SERVICIO \n");

        boolean hayResultados = false;

        for (Equipos equipo : equipos) {
            if (equipo != null && equipo.getEstado() == EstadoEquipo.FUERA_DE_SERVICIO) {
                resultado.append("Codigo: ")
                         .append(equipo.getCodigo())
                         .append("  Tipo: ")
                         .append(equipo.saberTipo())
                         .append("  Sector: ")
                         .append(equipo.getSector().getNombre())
                         .append('\n');

                hayResultados = true;
            }
        }

        if (!hayResultados) {
            resultado.append("Sin resultados.\n");
        }

        return resultado.toString();
    }
}