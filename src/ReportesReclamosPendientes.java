import Enums.EstadoReclamo;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ReportesReclamosPendientes implements Reportes {

    private final List<Reclamos> reclamos;

    public ReportesReclamosPendientes(List<Reclamos> reclamos) {
        if (reclamos == null) {
            throw new IllegalArgumentException("La lista de reclamos no puede ser nula");
        }

        this.reclamos = new ArrayList<>(reclamos);
    }

    @Override
    public String generar() {
        List<Reclamos> pendientes = new ArrayList<>();

        for (Reclamos reclamo : reclamos) {
            if (reclamo != null
                    && reclamo.getEstado() != EstadoReclamo.CERRADO) {
                pendientes.add(reclamo);
            }
        }

        pendientes.sort(
            Comparator
                .comparingInt((Reclamos r) -> r.getPrioridad().ordinal())
                .reversed()
                .thenComparing(Reclamos::getFechaHora)
        );

        StringBuilder resultado = new StringBuilder();
        resultado.append("RECLAMOS PENDIENTES \n");

        if (pendientes.isEmpty()) {
            resultado.append("Sin resultados.\n");
            return resultado.toString();
        }

        for (Reclamos reclamo : pendientes) {
            resultado.append("N.º: ")
                    .append(reclamo.getNumero())
                    .append(" | Prioridad: ")
                    .append(reclamo.getPrioridad())
                    .append(" | Fecha: ")
                    .append(reclamo.getFechaHora())
                    .append(" | Estado: ")
                    .append(reclamo.getEstado())
                    .append(" | Lote: ")
                    .append(reclamo.getLote().getNumero());

            if (reclamo.getResponsable() != null) {
                resultado.append(" | Responsable: ")
                        .append(reclamo.getResponsable().getNombre());
            } else {
                resultado.append(" | Sin responsable");
            }

            resultado.append('\n');
        }

        return resultado.toString();
    }
}