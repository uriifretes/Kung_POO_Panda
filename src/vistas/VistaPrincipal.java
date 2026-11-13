package vistas;

import controladores.ControladorGeneral;

// Pantalla de inicio
public class VistaPrincipal {
    private ControladorGeneral general;

    public VistaPrincipal(ControladorGeneral general) {
        this.general = general;
    }

    public void iniciar() {
        int opcion = -1;
        while (opcion != 0) {
            Consola.titulo("BARRIO CERRADO LOS AROMOS");
            System.out.println("Con que rol desea ingresar?");
            System.out.println("1. Titular");
            System.out.println("2. Residente");
            System.out.println("3. Visitante / Proveedor");
            System.out.println("4. Administrador");
            System.out.println("5. Guardia de seguridad");
            System.out.println("6. Empleado de mantenimiento");
            System.out.println("0. Salir");
            opcion = Consola.leerOpcion("Opcion", 0, 6);
            switch (opcion) {
                case 1:
                    new MenuVecino(general, true).iniciar();
                    break;
                case 2:
                    new MenuVecino(general, false).iniciar();
                    break;
                case 3:
                    new MenuVisitante(general).iniciar();
                    break;
                case 4:
                    new MenuAdministrador(general).iniciar();
                    break;
                case 5:
                    new MenuGuardia(general).iniciar();
                    break;
                case 6:
                    new MenuEmpleado(general).iniciar();
                    break;
                default:
                    break;
            }
        }
        System.out.println("Hasta luego.");
    }
}
