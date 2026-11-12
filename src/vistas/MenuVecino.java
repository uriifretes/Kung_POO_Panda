package vistas;

import controladores.ControladorGeneral;
import lotes.Lotes;
import personas.Personas;

import java.util.ArrayList;
import java.util.List;

//Menu del titular y del residente. Comparten casi todo pero titular
// gestiona a los residentes de su lote y recibe notificaciones.

public class MenuVecino {
    private ControladorGeneral general;
    private boolean esTitular;
    private VistaLotes vistaLotes;
    private VistaReclamos vistaReclamos;
    private VistaExpensas vistaExpensas;
    private VistaVisitas vistaVisitas;
    private VistaEquipos vistaEquipos;
    private VistaNotificaciones vistaNotificaciones;

    public MenuVecino(ControladorGeneral general, boolean esTitular) {
        this.general = general;
        this.esTitular = esTitular;
        this.vistaLotes = new VistaLotes(general.getLotes());
        this.vistaReclamos = new VistaReclamos(general.getReclamos());
        this.vistaExpensas = new VistaExpensas(general.getExpensas());
        this.vistaVisitas = new VistaVisitas(general.getVisitas());
        this.vistaEquipos = new VistaEquipos(general.getEquipos());
        this.vistaNotificaciones = new VistaNotificaciones();
    }

    public void iniciar() {
        String doc = Consola.leerTexto("Ingrese su documento");
        Lotes lote = elegirLote(doc);
        if (lote == null) {
            return;
        }
        Personas persona;
        if (esTitular) {
            persona = lote.getTitular();
        } else {
            persona = lote.buscarResidente(doc);
        }
        int nLote = lote.getNumero();
        String autor = persona.getNombre() + " " + persona.getApellido();
        System.out.println("Bienvenido/a " + autor + " - Lote " + nLote);

        int maximo = esTitular ? 15 : 11;
        int opcion = -1;
        while (opcion != 0) {
            mostrarMenu();
            opcion = Consola.leerOpcion("Opcion", 0, maximo);
            switch (opcion) {
                case 1:
                    vistaReclamos.presentarReclamo(nLote, doc);
                    break;
                case 2:
                    vistaReclamos.presentarSolicitud(nLote, doc);
                    break;
                case 3:
                    vistaReclamos.listarDeLote(nLote);
                    break;
                case 4:
                    vistaReclamos.verDetalle(nLote, 0);
                    break;
                case 5:
                    vistaReclamos.comentar(autor, nLote, 0);
                    break;
                case 6:
                    vistaReclamos.cancelar(nLote);
                    break;
                case 7:
                    vistaReclamos.reabrir(nLote);
                    break;
                case 8:
                    vistaExpensas.verExpensas(nLote);
                    vistaExpensas.consultarDeuda(nLote);
                    break;
                case 9:
                    vistaReclamos.apelarDeuda(nLote, doc);
                    break;
                case 10:
                    menuVisitas(nLote, doc);
                    break;
                case 11:
                    vistaEquipos.registrarFalla(autor);
                    break;
                case 12:
                    vistaNotificaciones.mostrar(lote.getTitular());
                    break;
                case 13:
                    vistaLotes.verResidentes(nLote);
                    break;
                case 14:
                    vistaLotes.agregarResidente(nLote);
                    break;
                case 15:
                    vistaLotes.autorizarReclamos(nLote);
                    break;
                default:
                    break;
            }
        }
    }

    private void mostrarMenu() {
        Consola.titulo(esTitular ? "MENU TITULAR" : "MENU RESIDENTE");
        System.out.println("1. Presentar reclamo");
        System.out.println("2. Presentar solicitud");
        System.out.println("3. Ver los pedidos de mi lote");
        System.out.println("4. Ver detalle de un pedido");
        System.out.println("5. Comentar un pedido");
        System.out.println("6. Cancelar un pedido (solo si esta ABIERTO)");
        System.out.println("7. Reabrir un reclamo resuelto (hasta 5 dias)");
        System.out.println("8. Ver expensas y deuda");
        System.out.println("9. Apelar una deuda");
        System.out.println("10. Visitas y proveedores");
        System.out.println("11. Informar falla de luminaria o camara");
        if (esTitular) {
            System.out.println("12. Mis notificaciones");
            System.out.println("13. Ver residentes del lote");
            System.out.println("14. Agregar residente");
            System.out.println("15. Autorizar a un residente a presentar reclamos");
        }
        System.out.println("0. Volver");
    }

    private void menuVisitas(int nLote, String doc) {
        int opcion = -1;
        while (opcion != 0) {
            Consola.titulo("VISITAS Y PROVEEDORES");
            System.out.println("1. Registrar visitante");
            System.out.println("2. Registrar proveedor");
            System.out.println("3. Autorizar visita (un solo ingreso)");
            System.out.println("4. Autorizar visita recurrente (hasta 30 dias)");
            System.out.println("5. Ver autorizaciones de una persona");
            System.out.println("0. Volver");
            opcion = Consola.leerOpcion("Opcion", 0, 5);
            switch (opcion) {
                case 1:
                    vistaVisitas.registrarVisitante();
                    break;
                case 2:
                    vistaVisitas.registrarProveedor();
                    break;
                case 3:
                    vistaVisitas.autorizacionUnica(nLote, doc);
                    break;
                case 4:
                    vistaVisitas.autorizacionRecurrente(nLote, doc);
                    break;
                case 5:
                    vistaVisitas.verAutorizaciones();
                    break;
                default:
                    break;
            }
        }
    }

    // Una persona puede ser titular de varios lotes: si pasa, elige con cual trabajar.
    private Lotes elegirLote(String doc) {
        List<Lotes> propios = new ArrayList<>();
        List<Lotes> todos = general.getLotes().getLotes();
        for (int i = 0; i < todos.size(); i++) {
            Lotes l = todos.get(i);
            boolean pertenece;
            if (esTitular) {
                pertenece = l.getTitular().getDocumento().equals(doc);
            } else {
                pertenece = l.buscarResidente(doc) != null;
            }
            if (pertenece) {
                propios.add(l);
            }
        }
        if (propios.isEmpty()) {
            System.out.println("No hay un " + (esTitular ? "titular" : "residente") + " con ese documento.");
            return null;
        }
        if (propios.size() == 1) {
            return propios.get(0);
        }
        System.out.println("Sus lotes:");
        for (int i = 0; i < propios.size(); i++) {
            System.out.println("- Lote " + propios.get(i).getNumero());
        }
        while (true) {
            int numero = Consola.leerEntero("Numero de lote con el que desea trabajar");
            for (int i = 0; i < propios.size(); i++) {
                if (propios.get(i).getNumero() == numero) {
                    return propios.get(i);
                }
            }
            System.out.println("Ese lote no esta en su lista.");
        }
    }
}