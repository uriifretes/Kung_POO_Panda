/** Ayuda para las pruebas: compara lo esperado con lo obtenido y cuenta cuantas pasan. */
public class Verificar {
    private static int correctas = 0;
    private static int fallidas = 0;

    public static void ok(String prueba) {
        correctas = correctas + 1;
        System.out.println("OK     " + prueba);
    }
    public static void falla(String prueba, String detalle) {
        fallidas = fallidas + 1;
        System.out.println("FALLA  " + prueba + " -> " + detalle);
    }
    public static void igual(String prueba, Object esperado, Object obtenido) {
        if (esperado.equals(obtenido)) {
            ok(prueba);
        } else {
            falla(prueba, "se esperaba " + esperado + " y se obtuvo " + obtenido);
        }
    }
    public static void igual(String prueba, double esperado, double obtenido) {
        if (Math.abs(esperado - obtenido) < 0.01) {
            ok(prueba);
        } else {
            falla(prueba, "se esperaba " + esperado + " y se obtuvo " + obtenido);
        }
    }
    public static void verdadero(String prueba, boolean condicion) {
        if (condicion) {
            ok(prueba);
        } else {
            falla(prueba, "la condicion dio false");
        }
    }
    public static void resumen(String nombre) {
        System.out.println("---- " + nombre + ": " + correctas + " correctas, " + fallidas + " fallidas ----");
        System.out.println();
        correctas = 0;
        fallidas = 0;
    }
}
