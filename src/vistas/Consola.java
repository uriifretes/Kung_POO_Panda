package vistas;

import personas.Personas;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;


public class Consola {
    private static final Scanner teclado = new Scanner(System.in);

    public static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("HH:mm");
    public static final DateTimeFormatter FORMATO_FECHA_HORA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    public static final DateTimeFormatter FORMATO_PERIODO = DateTimeFormatter.ofPattern("MM/yyyy");

    public static String leerTexto(String mensaje) {
        String texto = "";
        while (texto.isEmpty()) {
            System.out.print(mensaje + ": ");
            texto = teclado.nextLine().trim();
            if (texto.isEmpty()) {
                System.out.println("El dato no puede quedar vacio.");
            }
        }
        return texto;
    }

    public static int leerEntero(String mensaje) {
        while (true) {
            String texto = leerTexto(mensaje);
            try {
                return Integer.parseInt(texto);
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un numero entero valido.");
            }
        }
    }

    public static double leerDouble(String mensaje) {
        while (true) {
            String texto = leerTexto(mensaje);
            try {
                return Double.parseDouble(texto.replace(',', '.'));
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un numero valido (ej: 1500.50).");
            }
        }
    }

    public static int leerOpcion(String mensaje, int minimo, int maximo) {
        int opcion = leerEntero(mensaje);
        while (opcion < minimo || opcion > maximo) {
            System.out.println("La opcion debe estar entre " + minimo + " y " + maximo + ".");
            opcion = leerEntero(mensaje);
        }
        return opcion;
    }

    public static boolean leerSiNo(String mensaje) {
        while (true) {
            String texto = leerTexto(mensaje + " (s/n)").toLowerCase();
            if (texto.equals("s")) {
                return true;
            }
            if (texto.equals("n")) {
                return false;
            }
            System.out.println("Responda con s o n.");
        }
    }

    public static LocalDate leerFecha(String mensaje) {
        while (true) {
            String texto = leerTexto(mensaje + " (dd/mm/aaaa)");
            try {
                return LocalDate.parse(texto, FORMATO_FECHA);
            } catch (DateTimeParseException e) {
                System.out.println("Fecha invalida.");
            }
        }
    }

    public static LocalTime leerHora(String mensaje) {
        while (true) {
            String texto = leerTexto(mensaje + " (hh:mm)");
            try {
                return LocalTime.parse(texto, FORMATO_HORA);
            } catch (DateTimeParseException e) {
                System.out.println("Hora invalida.");
            }
        }
    }

    public static YearMonth leerPeriodo(String mensaje) {
        while (true) {
            String texto = leerTexto(mensaje + " (mm/aaaa)");
            try {
                return YearMonth.parse(texto, FORMATO_PERIODO);
            } catch (DateTimeParseException e) {
                System.out.println("Periodo invalido.");
            }
        }
    }

    /** Muestra las constantes de cualquier enum numeradas y devuelve la elegida. */
    public static <T extends Enum<T>> T leerEnum(String titulo, Class<T> tipo) {
        T[] valores = tipo.getEnumConstants();
        System.out.println(titulo + ":");
        for (int i = 0; i < valores.length; i++) {
            System.out.println("  " + (i + 1) + ". " + valores[i]);
        }
        int opcion = leerOpcion("Opcion", 1, valores.length);
        return valores[opcion - 1];
    }

    public static void titulo(String texto) {
        System.out.println();
        System.out.println("=== " + texto + " ===");
    }

    public static void error(Exception e) {
        System.out.println("No se pudo completar la operacion: " + e.getMessage());
    }

    public static String dinero(double valor) {
        return String.format("$%.2f", valor);
    }

    public static String fechaHora(LocalDateTime momento) {
        return momento.format(FORMATO_FECHA_HORA);
    }

    public static String nombreCompleto(Personas p) {
        return p.getNombre() + " " + p.getApellido() + " (DNI " + p.getDocumento() + ")";
    }
}
