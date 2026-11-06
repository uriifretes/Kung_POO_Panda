import Enums.TipoReclamo;

import java.time.LocalDateTime;

public class Reclamos extends PedidosVecinos{

    public Reclamos(int numero, LocalDateTime fechaHora, TipoReclamo tipo, String descripcion) {
        super(numero, fechaHora, tipo, descripcion);
    }
}
