package vistas;

import controladores.ControladorGeneral;

/** Visitantes y proveedores solo pueden consultar sus autorizaciones (S23). */
public class MenuVisitante {
    private VistaVisitas vistaVisitas;

    public MenuVisitante(ControladorGeneral general) {
        this.vistaVisitas = new VistaVisitas(general.getVisitas());
    }

    public void iniciar() {
        String doc = Consola.leerTexto("Ingrese su documento");
        vistaVisitas.verAutorizaciones(doc);
    }
}