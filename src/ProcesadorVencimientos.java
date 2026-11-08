import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class ProcesadorVencimientos {

    private static final long HORAS_PARA_ESCALAR = 24;
    private static final long DIAS_PARA_CERRAR = 5;
    private static final long HORAS_CAMARA_SIN_FUNCIONAR = 48;

    public void procesar(
            List<PedidosVecinos> pedidos,
            List<Lotes> lotes,
            List<Equipos> equipos,
            Administrador administrador,
            LocalDateTime ahora) {

        if (pedidos == null || lotes == null || equipos == null
                || administrador == null || ahora == null) {
            throw new IllegalArgumentException("Los datos no pueden ser nulos");
        }

        procesarPedidos(pedidos, administrador, ahora);
        procesarMorosidad(lotes, ahora);
        procesarCamaras(equipos, administrador, ahora);
    }

    private void procesarPedidos(
            List<PedidosVecinos> pedidos,
            Administrador administrador,
            LocalDateTime ahora) {
    }

    private void procesarMorosidad(
            List<Lotes> lotes,
            LocalDateTime ahora) {

        for (Lotes lote : lotes) {
            lote.getCuentaCorriente().actualizarMorosidad(ahora);
        }
    }

    private void procesarCamaras(
            List<Equipos> equipos,
            Administrador administrador,
            LocalDateTime ahora) {
    }
}