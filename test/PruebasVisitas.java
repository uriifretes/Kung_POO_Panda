import Enums.PuestoGuardia;
import Enums.Turno;
import Excepciones.IngresoDuplicadoException;
import Excepciones.SinAutorizacionException;
import personal.GuardiasDeSeguridad;
import visitas.Autorizaciones;
import visitas.AutorizacionesRecurrentes;
import visitas.AutorizacionesUnicas;
import visitas.Visitantes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class PruebasVisitas {
    private static final LocalDate DIA = LocalDate.of(2026, 11, 20);

    public static void main(String[] args) {
        System.out.println("==== Pruebas de visitas ====");
        probarUnicaVigente();
        probarRecurrenteVigente();
        probarRecurrenteMasDe30Dias();
        probarIngresoYSalida();
        probarIngresoDuplicado();
        probarUnicaUnSoloIngreso();
        probarFueraDeFranja();
        probarGuardiaDeRonda();
        Verificar.resumen("Visitas");
    }

    private static Visitantes visitante() {
        return new Visitantes("Julia", "Ramos", "35000111", "1155550010", "julia@mail.com");
    }

    private static GuardiasDeSeguridad portero() {
        return new GuardiasDeSeguridad(10, "Carlos Perez", LocalDate.of(2021, 5, 10), Turno.MANIANA, PuestoGuardia.PORTERIA);
    }

    private static void probarUnicaVigente() {
        Autorizaciones a = new AutorizacionesUnicas(visitante(), LocalTime.of(9, 0), LocalTime.of(18, 0), DIA);
        Verificar.verdadero("Unica: vigente ese dia a las 10", a.estaVigente(DIA.atTime(10, 0)));
        Verificar.verdadero("Unica: no vigente ese dia a las 20", !a.estaVigente(DIA.atTime(20, 0)));
        Verificar.verdadero("Unica: no vigente al dia siguiente", !a.estaVigente(DIA.plusDays(1).atTime(10, 0)));
    }
    private static void probarRecurrenteVigente() {
        Autorizaciones a = new AutorizacionesRecurrentes(visitante(), LocalTime.of(8, 0), LocalTime.of(20, 0), DIA, DIA.plusDays(14));
        Verificar.verdadero("Recurrente: vigente a la semana", a.estaVigente(DIA.plusDays(7).atTime(9, 0)));
        Verificar.verdadero("Recurrente: no vigente despues de la fecha final", !a.estaVigente(DIA.plusDays(15).atTime(9, 0)));
    }

    private static void probarRecurrenteMasDe30Dias() {
        try {
            new AutorizacionesRecurrentes(visitante(), LocalTime.of(8, 0), LocalTime.of(20, 0), DIA, DIA.plusDays(40));
            Verificar.falla("Una recurrente no puede durar mas de 30 dias", "no lanzo excepcion");
        } catch (IllegalArgumentException e) {
            Verificar.ok("Una recurrente no puede durar mas de 30 dias");
        }
    }

    private static void probarIngresoYSalida() {
        Visitantes v = visitante();
        Autorizaciones a = new AutorizacionesRecurrentes(v, LocalTime.of(8, 0), LocalTime.of(20, 0), DIA, DIA.plusDays(14));
        try {
            a.registrarIngreso(portero(), DIA.atTime(10, 0));
            Verificar.verdadero("Ingreso: el visitante queda adentro", v.estaAdentro());
            a.registrarEgreso(DIA.atTime(11, 0));
            Verificar.verdadero("Salida: el visitante ya no esta adentro", !v.estaAdentro());
            a.registrarIngreso(portero(), DIA.plusDays(1).atTime(10, 0));
            Verificar.igual("Recurrente: permite varios ingresos", 2, a.getIngresos().size());
        } catch (SinAutorizacionException | IngresoDuplicadoException e) {
            Verificar.falla("Ingreso y salida", e.getMessage());
        }
    }

    private static void probarIngresoDuplicado() {
        Visitantes v = visitante();
        Autorizaciones a = new AutorizacionesRecurrentes(v, LocalTime.of(8, 0), LocalTime.of(20, 0), DIA, DIA.plusDays(14));
        try {
            a.registrarIngreso(portero(), DIA.atTime(10, 0));
            a.registrarIngreso(portero(), DIA.atTime(12, 0));
            Verificar.falla("No se entra dos veces sin salir", "no lanzo excepcion");
        } catch (IngresoDuplicadoException e) {
            Verificar.ok("No se entra dos veces sin salir");
        } catch (SinAutorizacionException e) {
            Verificar.falla("No se entra dos veces sin salir", "lanzo SinAutorizacionException");
        }
    }

    private static void probarUnicaUnSoloIngreso() {
        Visitantes v = visitante();
        Autorizaciones a = new AutorizacionesUnicas(v, LocalTime.of(9, 0), LocalTime.of(18, 0), DIA);
        try {
            a.registrarIngreso(portero(), DIA.atTime(10, 0));
            a.registrarEgreso(DIA.atTime(11, 0));
            a.registrarIngreso(portero(), DIA.atTime(15, 0));
            Verificar.falla("Una unica sirve para un solo ingreso", "no lanzo excepcion");
        } catch (SinAutorizacionException e) {
            Verificar.ok("Una unica sirve para un solo ingreso");
        } catch (IngresoDuplicadoException e) {
            Verificar.falla("Una unica sirve para un solo ingreso", "lanzo IngresoDuplicadoException");
        }
    }

    private static void probarFueraDeFranja() {
        Autorizaciones a = new AutorizacionesUnicas(visitante(), LocalTime.of(9, 0), LocalTime.of(18, 0), DIA);
        try {
            a.registrarIngreso(portero(), DIA.atTime(20, 0));
            Verificar.falla("No se entra fuera de la franja horaria", "no lanzo excepcion");
        } catch (SinAutorizacionException e) {
            Verificar.ok("No se entra fuera de la franja horaria");
        } catch (IngresoDuplicadoException e) {
            Verificar.falla("No se entra fuera de la franja horaria", "lanzo IngresoDuplicadoException");
        }
    }
    private static void probarGuardiaDeRonda() {
        GuardiasDeSeguridad ronda = new GuardiasDeSeguridad(11, "Diego Ruiz", LocalDate.of(2022, 8, 15), Turno.NOCHE, PuestoGuardia.RONDA);
        Autorizaciones a = new AutorizacionesUnicas(visitante(), LocalTime.of(9, 0), LocalTime.of(18, 0), DIA);
        try {
            a.registrarIngreso(ronda, DIA.atTime(10, 0));
            Verificar.falla("Un guardia de ronda no registra ingresos", "no lanzo excepcion");
        } catch (IllegalArgumentException e) {
            Verificar.ok("Un guardia de ronda no registra ingresos");
        } catch (SinAutorizacionException | IngresoDuplicadoException e) {
            Verificar.falla("Un guardia de ronda no registra ingresos", "lanzo otra excepcion: " + e.getMessage());
        }
    }
}
