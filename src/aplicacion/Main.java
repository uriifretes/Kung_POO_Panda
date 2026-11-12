package aplicacion;

import controladores.ControladorGeneral;
import persistencia.ArchivoDatos;
import vistas.Consola;
import vistas.VistaPrincipal;

public class Main {

    public static void main(String[] args) {
        ArchivoDatos archivo = new ArchivoDatos("datos_aromos.dat");
        ControladorGeneral general = null;

        if (archivo.existe()) {
            general = archivo.cargar();
            if (general != null) {
                System.out.println("Se cargaron los datos guardados.");
            }
        }

        if (general == null) {
            general = new ControladorGeneral();
            if (Consola.leerSiNo("Desea cargar datos de ejemplo?")) {
                DatosDePrueba.cargar(general);
                System.out.println("Datos de ejemplo cargados (los usuarios de prueba estan en el README).");
            }
        }

        try {
            general.procesarVencimientos();
        } catch (IllegalArgumentException e) {
            System.out.println("Aun no hay administrador: se omite el procesamiento de vencimientos.");
        }

        new VistaPrincipal(general).iniciar();

        archivo.guardar(general);
    }
}
