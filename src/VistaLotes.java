import Enums.TipoResidente;

import java.util.List;

public class VistaLotes {
    private ControladorLotes controlador;

    public VistaLotes(ControladorLotes controlador) {
        this.controlador = controlador;
    }

    public void registrarLote() {
        Consola.titulo("Registrar lote");
        try {
            int numero = Consola.leerEntero("Numero de lote");
            double superficie = Consola.leerDouble("Superficie (m2)");
            System.out.println("Datos del titular:");
            String nombre = Consola.leerTexto("Nombre");
            String apellido = Consola.leerTexto("Apellido");
            String documento = Consola.leerTexto("Documento");
            String telefono = Consola.leerTexto("Telefono");
            String correo = Consola.leerTexto("Correo");
            controlador.registrarLote(numero, superficie, nombre, apellido, documento, telefono, correo);
            System.out.println("Lote " + numero + " registrado correctamente.");
        } catch (IllegalArgumentException e) {
            Consola.error(e);
        }
    }

    public void agregarResidente(int nLote) {
        Consola.titulo("Agregar residente al lote " + nLote);
        try {
            String nombre = Consola.leerTexto("Nombre");
            String apellido = Consola.leerTexto("Apellido");
            String documento = Consola.leerTexto("Documento");
            String telefono = Consola.leerTexto("Telefono");
            String correo = Consola.leerTexto("Correo");
            TipoResidente tipo = Consola.leerEnum("Tipo de residente", TipoResidente.class);
            controlador.agregarResidente(nLote, nombre, apellido, documento, telefono, correo, tipo);
            System.out.println("Residente agregado.");
        } catch (IllegalArgumentException e) {
            Consola.error(e);
        }
    }

    public void agregarResidente() {
        int nLote = Consola.leerEntero("Numero de lote");
        agregarResidente(nLote);
    }

    /** El titular marca si un residente de su lote puede presentar reclamos (S29). */
    public void autorizarReclamos(int nLote) {
        Consola.titulo("Autorizar reclamos a un residente");
        try {
            String documento = Consola.leerTexto("Documento del residente");
            boolean valor = Consola.leerSiNo("Autorizar a presentar reclamos?");
            controlador.autorizarReclamos(nLote, documento, valor);
            System.out.println("Permiso actualizado.");
        } catch (IllegalArgumentException e) {
            Consola.error(e);
        }
    }

    /** El administrador habilita a un residente a autorizar visitas (S30). */
    public void habilitarVisitas() {
        Consola.titulo("Habilitar a un residente a autorizar visitas");
        try {
            int nLote = Consola.leerEntero("Numero de lote");
            String documento = Consola.leerTexto("Documento del residente");
            boolean valor = Consola.leerSiNo("Habilitar a autorizar visitas?");
            controlador.habilitarVisitas(nLote, documento, valor);
            System.out.println("Permiso actualizado.");
        } catch (IllegalArgumentException e) {
            Consola.error(e);
        }
    }

    public void listarLotes() {
        Consola.titulo("Lotes del barrio");
        List<Lotes> lotes = controlador.getLotes();
        if (lotes.isEmpty()) {
            System.out.println("Todavia no hay lotes cargados.");
        }
        for (int i = 0; i < lotes.size(); i++) {
            Lotes l = lotes.get(i);
            String moroso = l.estaMoroso() ? " | MOROSO" : "";
            System.out.println("Lote " + l.getNumero() + " | " + l.getSuperficie() + " m2 | Titular: "
                    + Consola.nombreCompleto(l.getTitular()) + " | Residentes: " + l.getResidentes().size() + moroso);
        }
    }

    public void verResidentes(int nLote) {
        Consola.titulo("Residentes del lote " + nLote);
        try {
            Lotes l = controlador.tenerLote(nLote);
            System.out.println("Titular: " + Consola.nombreCompleto(l.getTitular()));
            List<Residentes> residentes = l.getResidentes();
            if (residentes.isEmpty()) {
                System.out.println("No hay otros residentes.");
            }
            for (int i = 0; i < residentes.size(); i++) {
                Residentes r = residentes.get(i);
                System.out.println("- " + Consola.nombreCompleto(r)
                        + " | Reclamos: " + (r.isAutorizadoReclamos() ? "si" : "no")
                        + " | Autoriza visitas: " + (r.isPuedeAutorizarVisitas() ? "si" : "no"));
            }
        } catch (IllegalArgumentException e) {
            Consola.error(e);
        }
    }
}