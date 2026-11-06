import Enums.TipoReclamo;

import java.time.LocalDateTime;

public class Solicitud extends PedidosVecinos{
    public Solicitud(int numero, LocalDateTime fechaHora, TipoReclamo tipo, String descripcion) {
        super(numero, fechaHora, tipo, descripcion);
    }
}
