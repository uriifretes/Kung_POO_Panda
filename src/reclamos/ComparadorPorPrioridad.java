package reclamos;

import java.util.Comparator;

public class ComparadorPorPrioridad implements Comparator<PedidosVecinos> {

    @Override
    public int compare(PedidosVecinos p1, PedidosVecinos p2) {
        int resultado = p2.getPrioridad().compareTo(p1.getPrioridad());
        if (resultado == 0) {
            resultado = p1.compareTo(p2);
        }
        return resultado;
    }
}
