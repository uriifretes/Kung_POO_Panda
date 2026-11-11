public class MenuAdministrador {
    private ControladorGeneral general;
    private VistaLotes vistaLotes;
    private VistaPersonal vistaPersonal;
    private VistaReclamos vistaReclamos;
    private VistaExpensas vistaExpensas;
    private VistaEquipos vistaEquipos;
    private VistaVisitas vistaVisitas;
    private VistaNotificaciones vistaNotificaciones;

    public MenuAdministrador(ControladorGeneral general) {
        this.general = general;
        this.vistaLotes = new VistaLotes(general.getLotes());
        this.vistaPersonal = new VistaPersonal(general.getPersonal());
        this.vistaReclamos = new VistaReclamos(general.getReclamos());
        this.vistaExpensas = new VistaExpensas(general.getExpensas());
        this.vistaEquipos = new VistaEquipos(general.getEquipos());
        this.vistaVisitas = new VistaVisitas(general.getVisitas());
        this.vistaNotificaciones = new VistaNotificaciones();
    }

    public void iniciar() {
        Administrador admin = identificar();
        if (admin == null) {
            return;
        }
        System.out.println("Bienvenido/a " + admin.getNombre());
        int opcion = -1;
        while (opcion != 0) {
            Consola.titulo("MENU ADMINISTRADOR");
            System.out.println("1. Lotes y residentes");
            System.out.println("2. Personal");
            System.out.println("3. Reclamos y solicitudes");
            System.out.println("4. Expensas y pagos");
            System.out.println("5. Luminarias y camaras");
            System.out.println("6. Visitas");
            System.out.println("7. Reportes");
            System.out.println("8. Mis notificaciones");
            System.out.println("9. Procesar vencimientos");
            System.out.println("0. Volver");
            opcion = Consola.leerOpcion("Opcion", 0, 9);
            switch (opcion) {
                case 1:
                    menuLotes();
                    break;
                case 2:
                    menuPersonal();
                    break;
                case 3:
                    menuReclamos();
                    break;
                case 4:
                    menuExpensas();
                    break;
                case 5:
                    menuEquipos();
                    break;
                case 6:
                    menuVisitas();
                    break;
                case 7:
                    menuReportes();
                    break;
                case 8:
                    vistaNotificaciones.mostrar(admin);
                    break;
                case 9:
                    procesarVencimientos();
                    break;
                default:
                    break;
            }
        }
    }

    /** Si todavia no hay administrador, deja registrar el primero (S04). */
    private Administrador identificar() {
        try {
            general.getPersonal().getAdmin();
        } catch (IllegalArgumentException e) {
            System.out.println("Todavia no hay un administrador cargado. Registrelo ahora.");
            vistaPersonal.registrarAdministrador();
        }
        try {
            Administrador admin = general.getPersonal().getAdmin();
            int legajo = Consola.leerEntero("Ingrese su legajo");
            if (admin.getLegajo() != legajo) {
                System.out.println("El legajo no corresponde al administrador.");
                return null;
            }
            return admin;
        } catch (IllegalArgumentException e) {
            Consola.error(e);
            return null;
        }
    }

    private void procesarVencimientos() {
        try {
            general.procesarVencimientos();
            System.out.println("Vencimientos procesados: escalamientos, cierres automaticos, morosidad y alerta de camaras.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            Consola.error(e);
        }
    }

    private void menuLotes() {
        int opcion = -1;
        while (opcion != 0) {
            Consola.titulo("LOTES Y RESIDENTES");
            System.out.println("1. Registrar lote");
            System.out.println("2. Agregar residente a un lote");
            System.out.println("3. Habilitar a un residente a autorizar visitas");
            System.out.println("4. Listar lotes");
            System.out.println("5. Ver residentes de un lote");
            System.out.println("0. Volver");
            opcion = Consola.leerOpcion("Opcion", 0, 5);
            switch (opcion) {
                case 1:
                    vistaLotes.registrarLote();
                    break;
                case 2:
                    vistaLotes.agregarResidente();
                    break;
                case 3:
                    vistaLotes.habilitarVisitas();
                    break;
                case 4:
                    vistaLotes.listarLotes();
                    break;
                case 5:
                    vistaLotes.verResidentes(Consola.leerEntero("Numero de lote"));
                    break;
                default:
                    break;
            }
        }
    }

    private void menuPersonal() {
        int opcion = -1;
        while (opcion != 0) {
            Consola.titulo("PERSONAL");
            System.out.println("1. Registrar guardia de seguridad");
            System.out.println("2. Registrar empleado de mantenimiento");
            System.out.println("3. Listar personal");
            System.out.println("0. Volver");
            opcion = Consola.leerOpcion("Opcion", 0, 3);
            switch (opcion) {
                case 1:
                    vistaPersonal.registrarGuardia();
                    break;
                case 2:
                    vistaPersonal.registrarEmpleado();
                    break;
                case 3:
                    vistaPersonal.listarPersonal();
                    break;
                default:
                    break;
            }
        }
    }

    private void menuReclamos() {
        int opcion = -1;
        while (opcion != 0) {
            Consola.titulo("RECLAMOS Y SOLICITUDES");
            System.out.println("1. Listar todos");
            System.out.println("2. Ver detalle de un pedido");
            System.out.println("3. Asignar pedido");
            System.out.println("4. Avanzar estado");
            System.out.println("5. Agregar comentario");
            System.out.println("0. Volver");
            opcion = Consola.leerOpcion("Opcion", 0, 5);
            switch (opcion) {
                case 1:
                    vistaReclamos.listarTodos();
                    break;
                case 2:
                    vistaReclamos.verDetalle(0, 0);
                    break;
                case 3:
                    vistaReclamos.asignar();
                    break;
                case 4:
                    vistaReclamos.avanzar(0);
                    break;
                case 5:
                    vistaReclamos.comentar("Administrador", 0, 0);
                    break;
                default:
                    break;
            }
        }
    }

    private void menuExpensas() {
        int opcion = -1;
        while (opcion != 0) {
            Consola.titulo("EXPENSAS Y PAGOS");
            System.out.println("1. Emitir expensas del mes");
            System.out.println("2. Registrar pago");
            System.out.println("3. Registrar asiento de ajuste");
            System.out.println("4. Ver expensas de un lote");
            System.out.println("5. Consultar deuda de un lote");
            System.out.println("0. Volver");
            opcion = Consola.leerOpcion("Opcion", 0, 5);
            switch (opcion) {
                case 1:
                    vistaExpensas.emitirExpensas();
                    break;
                case 2:
                    vistaExpensas.registrarPago();
                    break;
                case 3:
                    vistaExpensas.registrarAsiento();
                    break;
                case 4:
                    vistaExpensas.verExpensas();
                    break;
                case 5:
                    vistaExpensas.consultarDeuda();
                    break;
                default:
                    break;
            }
        }
    }

    private void menuEquipos() {
        int opcion = -1;
        while (opcion != 0) {
            Consola.titulo("LUMINARIAS Y CAMARAS");
            System.out.println("1. Registrar sector");
            System.out.println("2. Registrar camara");
            System.out.println("3. Registrar luminaria");
            System.out.println("4. Registrar falla");
            System.out.println("5. Generar orden de mantenimiento");
            System.out.println("6. Cerrar orden de mantenimiento");
            System.out.println("7. Marcar equipo fuera de servicio");
            System.out.println("8. Registrar control de un equipo");
            System.out.println("9. Listar equipos");
            System.out.println("0. Volver");
            opcion = Consola.leerOpcion("Opcion", 0, 9);
            switch (opcion) {
                case 1:
                    vistaEquipos.registrarSector();
                    break;
                case 2:
                    vistaEquipos.registrarCamara();
                    break;
                case 3:
                    vistaEquipos.registrarLuminaria();
                    break;
                case 4:
                    vistaEquipos.registrarFalla("Administrador");
                    break;
                case 5:
                    vistaEquipos.generarOrden();
                    break;
                case 6:
                    vistaEquipos.cerrarOrden(0);
                    break;
                case 7:
                    vistaEquipos.marcarFueraDeServicio();
                    break;
                case 8:
                    vistaEquipos.registrarControl();
                    break;
                case 9:
                    vistaEquipos.listarEquipos();
                    break;
                default:
                    break;
            }
        }
    }

    private void menuVisitas() {
        int opcion = -1;
        while (opcion != 0) {
            Consola.titulo("VISITAS");
            System.out.println("1. Registrar visitante");
            System.out.println("2. Registrar proveedor");
            System.out.println("3. Ver autorizaciones de una persona");
            System.out.println("0. Volver");
            opcion = Consola.leerOpcion("Opcion", 0, 3);
            switch (opcion) {
                case 1:
                    vistaVisitas.registrarVisitante();
                    break;
                case 2:
                    vistaVisitas.registrarProveedor();
                    break;
                case 3:
                    vistaVisitas.verAutorizaciones();
                    break;
                default:
                    break;
            }
        }
    }

    private void menuReportes() {
        int opcion = -1;
        while (opcion != 0) {
            Consola.titulo("REPORTES");
            System.out.println("1. Reclamos pendientes");
            System.out.println("2. Morosidad por lote");
            System.out.println("3. Equipos fuera de servicio");
            System.out.println("0. Volver");
            opcion = Consola.leerOpcion("Opcion", 0, 3);
            switch (opcion) {
                case 1:
                    vistaReclamos.reportePendientes();
                    break;
                case 2:
                    vistaExpensas.reporteMorosidad();
                    break;
                case 3:
                    vistaEquipos.reporteFueraDeServicio();
                    break;
                default:
                    break;
            }
        }
    }
}
