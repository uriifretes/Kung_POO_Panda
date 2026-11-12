package reclamos;

import lotes.Lotes;
import personas.Personas;

import Enums.TipoReclamo;

import java.time.LocalDateTime;

public class Reclamos extends PedidosVecinos{

    public Reclamos(int numero, LocalDateTime fechaHora, TipoReclamo tipo, String descripcion, Lotes lote, Personas presentado) {
        super(numero, fechaHora, tipo, descripcion, lote, presentado);
    }
}
