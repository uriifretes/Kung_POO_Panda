package reportes;

import reclamos.PedidosVecinos;

import Enums.EstadoReclamo;

import java.util.ArrayList;
import java.util.List;

public class ReportesReclamosPendientes implements Reportes {
    private List<PedidosVecinos> reclamos;

    public ReportesReclamosPendientes(List<PedidosVecinos> reclamos) {
        if (reclamos==null){
            throw new IllegalArgumentException("Has ingresado datos invalidos");
        }

        this.reclamos = new ArrayList<>(reclamos);
    }

    private void ordenarPorFecha() {
        for (int i = 0; i < reclamos.size() - 1; i++) {
            for (int j = 0; j < reclamos.size() - 1 - i; j++) {
                PedidosVecinos actual = reclamos.get(j);
                PedidosVecinos siguiente = reclamos.get(j + 1);   ////es por burbujeo
                if (actual.getFechaHora().isAfter(siguiente.getFechaHora())) {
                    reclamos.set(j, siguiente);
                    reclamos.set(j + 1, actual);
                }
            }
        }
    }

    @Override
    public String generar() {
        ordenarPorFecha();
        String texto = "REPORTE DE RECLAMOS PENDIENTES: " + "\n";
        int cantidad = 0;
        for (int i = 0; i < reclamos.size(); i++) {
            PedidosVecinos p = reclamos.get(i);
            if (p.getEstado() != EstadoReclamo.CERRADO) {
                cantidad = cantidad + 1;
                texto = texto + cantidad + "- Reclamo: " + p.getNumero() + "\n";
                texto = texto + "Fecha: " + p.getFechaHora() + "\n";
                texto = texto + "Tipo: " + p.getTipo() + " (prioridad " + p.getPrioridad() + ")\n";
                texto = texto + "Estado: " + p.getEstado() + "\n";
                if (p.isEscalado()) {
                    texto = texto + "Es un reporte escalado al administrador" +"\n";

                }


                texto = texto + "\n";
            }
        }

        if (cantidad == 0) {
            texto = texto + "No hay reclamos pendientes" +"\n";
        }
        return texto;


    }
}
