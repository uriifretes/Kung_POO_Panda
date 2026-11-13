package reclamos;

import lotes.Lotes;
import personas.Personas;

import Enums.TipoReclamo;

import java.time.LocalDateTime;

public class Solicitud extends PedidosVecinos{
    public Solicitud(int numero, LocalDateTime fechaHora, TipoReclamo tipo, String descripcion, Lotes lotes, Personas presentadop) {
        super(numero, fechaHora, tipo, descripcion, lotes, presentadop);
    }

    @Override
    public boolean puedeReabrirse(){
        return false;
    }

    @Override
    public boolean seCierraSolo(){
        return false;
    }
}
