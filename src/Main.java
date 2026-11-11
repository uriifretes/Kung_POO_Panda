public class Main {

    public static void main(String[] args) {
        ControladorGeneral general = new ControladorGeneral();

        if (Consola.leerSiNo("Desea cargar datos de ejemplo?")) {
            DatosDePrueba.cargar(general);
            System.out.println("Datos de ejemplo cargados (administrador legajo 1, guardia porteria 10, empleado 20, lotes 1 a 3).");
        }

        // S03: los controles que dependen del tiempo tambien corren al iniciar
        try {
            general.procesarVencimientos();
        } catch (IllegalArgumentException e) {
            System.out.println("Aun no hay administrador: se omite el procesamiento de vencimientos.");
        }

        new VistaPrincipal(general).iniciar();
    }
}