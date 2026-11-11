public class MenuGuardia {
    private ControladorGeneral general;
    private VistaVisitas vistaVisitas;
    private VistaEquipos vistaEquipos;
    private VistaReclamos vistaReclamos;

    public MenuGuardia(ControladorGeneral general) {
        this.general = general;
        this.vistaVisitas = new VistaVisitas(general.getVisitas());
        this.vistaEquipos = new VistaEquipos(general.getEquipos());
        this.vistaReclamos = new VistaReclamos(general.getReclamos());
    }

    public void iniciar() {
        GuardiasDeSeguridad guardia;
        try {
            guardia = general.getPersonal().tenerGuardia(Consola.leerEntero("Ingrese su legajo"));
        } catch (IllegalArgumentException e) {
            Consola.error(e);
            return;
        }
        int legajo = guardia.getLegajo();
        System.out.println("Bienvenido/a " + guardia.getNombre() + (guardia.esPortero() ? " (porteria)" : ""));

        int opcion = -1;
        while (opcion != 0) {
            Consola.titulo("MENU GUARDIA");
            System.out.println("1. Registrar ingreso (solo porteria)");
            System.out.println("2. Registrar egreso");
            System.out.println("3. Registrar visitante");
            System.out.println("4. Registrar proveedor");
            System.out.println("5. Ver autorizaciones de una persona");
            System.out.println("6. Registrar falla de un equipo");
            System.out.println("7. Ver equipos");
            System.out.println("8. Ver mis pedidos asignados");
            System.out.println("9. Avanzar estado de un pedido asignado");
            System.out.println("10. Comentar un pedido asignado");
            System.out.println("0. Volver");
            opcion = Consola.leerOpcion("Opcion", 0, 10);
            switch (opcion) {
                case 1:
                    vistaVisitas.registrarIngreso(legajo);
                    break;
                case 2:
                    vistaVisitas.registrarEgreso();
                    break;
                case 3:
                    vistaVisitas.registrarVisitante();
                    break;
                case 4:
                    vistaVisitas.registrarProveedor();
                    break;
                case 5:
                    vistaVisitas.verAutorizaciones();
                    break;
                case 6:
                    vistaEquipos.registrarFalla(guardia.getNombre());
                    break;
                case 7:
                    vistaEquipos.listarEquipos();
                    break;
                case 8:
                    vistaReclamos.listarAsignados(legajo);
                    break;
                case 9:
                    vistaReclamos.avanzar(legajo);
                    break;
                case 10:
                    vistaReclamos.comentar(guardia.getNombre(), 0, legajo);
                    break;
                default:
                    break;
            }
        }
    }
}