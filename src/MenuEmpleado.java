public class MenuEmpleado {
    private ControladorGeneral general;
    private VistaReclamos vistaReclamos;
    private VistaEquipos vistaEquipos;

    public MenuEmpleado(ControladorGeneral general) {
        this.general = general;
        this.vistaReclamos = new VistaReclamos(general.getReclamos());
        this.vistaEquipos = new VistaEquipos(general.getEquipos());
    }

    public void iniciar() {
        EmpleadosDeMantenimiento empleado;
        try {
            empleado = general.getPersonal().obtenerEmpleado(Consola.leerEntero("Ingrese su legajo"));
        } catch (IllegalArgumentException e) {
            Consola.error(e);
            return;
        }
        int legajo = empleado.getLegajo();
        System.out.println("Bienvenido/a " + empleado.getNombre() + " | Reclamos en curso: " + empleado.cantidadenCurso() + " (maximo 5)");

        int opcion = -1;
        while (opcion != 0) {
            Consola.titulo("MENU EMPLEADO DE MANTENIMIENTO");
            System.out.println("1. Ver mis pedidos asignados");
            System.out.println("2. Ver detalle de un pedido asignado");
            System.out.println("3. Avanzar estado de un pedido");
            System.out.println("4. Comentar un pedido");
            System.out.println("5. Ver equipos y ordenes");
            System.out.println("6. Registrar control de un equipo");
            System.out.println("7. Cerrar orden de mantenimiento");
            System.out.println("0. Volver");
            opcion = Consola.leerOpcion("Opcion", 0, 7);
            switch (opcion) {
                case 1:
                    vistaReclamos.listarAsignados(legajo);
                    break;
                case 2:
                    vistaReclamos.verDetalle(0, legajo);
                    break;
                case 3:
                    vistaReclamos.avanzar(legajo);
                    break;
                case 4:
                    vistaReclamos.comentar(empleado.getNombre(), 0, legajo);
                    break;
                case 5:
                    vistaEquipos.listarEquipos();
                    break;
                case 6:
                    vistaEquipos.registrarControl();
                    break;
                case 7:
                    vistaEquipos.cerrarOrden(legajo);
                    break;
                default:
                    break;
            }
        }
    }
}