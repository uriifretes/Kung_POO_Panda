package controladores;

import equipos.Camaras;
import equipos.Equipos;
import lotes.Lotes;
import personal.Administrador;
import reclamos.PedidosVecinos;

import java.time.LocalDateTime;
import java.util.List;

public class ControladorGeneral {
    private ControladorLotes lotes;
    private ControladorPersonal personal;
    private ControladorExpensas expensas;
    private ControladorReclamos reclamos;
    private ControladorEquipos equipos;
    private ControladorVisitas visitas;

    public ControladorGeneral() {
        lotes = new ControladorLotes();
        personal = new ControladorPersonal();
        expensas = new ControladorExpensas(lotes);
        reclamos = new ControladorReclamos(lotes, personal);
        equipos = new ControladorEquipos(personal);
        visitas = new ControladorVisitas(lotes, personal);
    }

    public ControladorLotes getLotes() {
        return lotes;
    }

    public ControladorPersonal getPersonal() {
        return personal;
    }

    public ControladorExpensas getExpensas() {
        return expensas;
    }

    public ControladorReclamos getReclamos() {
        return reclamos;
    }

    public ControladorEquipos getEquipos() {
        return equipos;
    }

    public ControladorVisitas getVisitas() {
        return visitas;
    }

    public void procesarVencimientos() {
        LocalDateTime ahora = LocalDateTime.now();
        Administrador admin = personal.getAdmin();

        List<PedidosVecinos> pedidos = reclamos.getPedidos();
        for (int i = 0; i < pedidos.size(); i++) {
            pedidos.get(i).escalarPortiempo(ahora, admin);
            pedidos.get(i).cerrarsiVencio(ahora);
        }

        List<Lotes> listaLotes = lotes.getLotes();
        for (int i = 0; i < listaLotes.size(); i++) {
            listaLotes.get(i).getCuentaCorriente().actualizarMorosidad(ahora);
        }

        List<Equipos> listaEquipos = equipos.getEquipos();
        for (int i = 0; i < listaEquipos.size(); i++) {
            if (listaEquipos.get(i).saberTipo().equals("Camara")) {
                Camaras c = (Camaras) listaEquipos.get(i);
                c.avisarSiCorresponde(ahora, admin);
            }
        }
    }
}
