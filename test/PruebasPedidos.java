import Enums.Especialidad;
import Enums.EstadoReclamo;
import Enums.Prioridad;
import Enums.PuestoGuardia;
import Enums.TipoReclamo;
import Enums.TipoResidente;
import Enums.Turno;
import Excepciones.LimiteReclamosException;
import Excepciones.TransicionInvalidaException;
import lotes.Lotes;
import personal.Administrador;
import personal.EmpleadosDeMantenimiento;
import personal.GuardiasDeSeguridad;
import personas.Residentes;
import personas.Titular;
import reclamos.ComparadorPorPrioridad;
import reclamos.PedidosVecinos;
import reclamos.Reclamos;
import reclamos.Solicitud;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PruebasPedidos {
    private static final LocalDateTime TIEMPO = LocalDateTime.of(2026, 11, 1, 10, 0);

    public static void main(String[] args) {
        System.out.println("==== Pruebas de pedidos ====");
        probarPedidoNuevo();
        probarResidenteSinAutorizacion();
        probarAsignarAEmpleado();
        probarAsignarAGuardiaNoCorresponde();
        probarAsignarPedidoNoAbierto();
        probarLimiteDeCincoEnCurso();
        probarAvanzarHastaResuelto();
        probarReabrirDentroDelPlazo();
        probarReabrirFueraDelPlazo();
        probarCierreAutomatico();
        probarSolicitudNoSeReabreNiSeCierraSola();
        probarEscalamiento();
        probarCancelar();
        probarOrdenPorFechaYPorPrioridad();
        Verificar.resumen("Pedidos");
    }

    private static Titular titular() {
        return new Titular("Ana", "Lopez", "30111222", "1155550001", "ana@mail.com");
    }

    private static EmpleadosDeMantenimiento empleado() {
        return new EmpleadosDeMantenimiento(20, "Luis Torres", LocalDate.of(2021, 2, 1), Especialidad.ELECTRICIDAD);
    }

    private static void probarPedidoNuevo() {
        Titular t = titular();
        Lotes lote = new Lotes(1, 500, t);
        PedidosVecinos p = new Reclamos(1, TIEMPO, TipoReclamo.MANTENIMIENTO, "Luz quemada", lote, t);
        Verificar.igual("Pedido nuevo: estado ABIERTO", EstadoReclamo.ABIERTO, p.getEstado());
        Verificar.igual("Pedido nuevo: historial con 1 cambio", 1, p.getHistorial().size());
        Verificar.igual("Pedido nuevo: MANTENIMIENTO es prioridad MEDIA", Prioridad.MEDIA, p.getPrioridad());
    }

    private static void probarResidenteSinAutorizacion() {
        Titular t = titular();
        Lotes lote = new Lotes(1, 500, t);
        Residentes r = new Residentes("Sofia", "Diaz", "40333444", "1155550008", "sofia@mail.com", TipoResidente.INQUILINO);
        lote.agregarResidente(r);
        try {
            new Solicitud(1, TIEMPO, TipoReclamo.MANTENIMIENTO, "Podar el arbol", lote, r);
            Verificar.falla("Residente sin permiso no presenta pedidos", "no lanzo excepcion");
        } catch (IllegalArgumentException e) {
            Verificar.ok("Residente sin permiso no presenta pedidos");
        }
    }

    private static void probarAsignarAEmpleado() {
        Titular t = titular();
        Lotes lote = new Lotes(1, 500, t);
        EmpleadosDeMantenimiento e = empleado();
        PedidosVecinos p = new Reclamos(1, TIEMPO, TipoReclamo.MANTENIMIENTO, "Luz quemada", lote, t);
        try {
            p.asignar(e, TIEMPO.plusHours(1));
            Verificar.igual("Asignar a empleado: estado ASIGNADO", EstadoReclamo.ASIGNADO, p.getEstado());
            Verificar.verdadero("Asignar a empleado: queda como responsable", p.getResponsable() == e);
        } catch (TransicionInvalidaException | LimiteReclamosException ex) {
            Verificar.falla("Asignar a empleado", ex.getMessage());
        }
    }

    private static void probarAsignarAGuardiaNoCorresponde() {
        Titular t = titular();
        Lotes lote = new Lotes(1, 500, t);
        GuardiasDeSeguridad g = new GuardiasDeSeguridad(10, "Carlos Perez", LocalDate.of(2021, 5, 10), Turno.MANIANA, PuestoGuardia.PORTERIA);
        PedidosVecinos p = new Reclamos(1, TIEMPO, TipoReclamo.MANTENIMIENTO, "Luz quemada", lote, t);
        try {
            p.asignar(g, TIEMPO.plusHours(1));
            Verificar.falla("Guardia no atiende MANTENIMIENTO", "no lanzo excepcion");
        } catch (IllegalArgumentException e) {
            Verificar.ok("Guardia no atiende MANTENIMIENTO");
        } catch (TransicionInvalidaException | LimiteReclamosException e) {
            Verificar.falla("Guardia no atiende MANTENIMIENTO", "lanzo otra excepcion: " + e.getMessage());
        }
    }

    private static void probarAsignarPedidoNoAbierto() {
        Titular t = titular();
        Lotes lote = new Lotes(1, 500, t);
        EmpleadosDeMantenimiento e = empleado();
        PedidosVecinos p = new Reclamos(1, TIEMPO, TipoReclamo.MANTENIMIENTO, "Luz quemada", lote, t);
        try {
            p.asignar(e, TIEMPO.plusHours(1));
            p.asignar(e, TIEMPO.plusHours(2));
            Verificar.falla("No se asigna un pedido que no esta ABIERTO", "no lanzo excepcion");
        } catch (TransicionInvalidaException ex) {
            Verificar.ok("No se asigna un pedido que no esta ABIERTO");
        } catch (LimiteReclamosException ex) {
            Verificar.falla("No se asigna un pedido que no esta ABIERTO", "lanzo LimiteReclamosException");
        }
    }

    private static void probarLimiteDeCincoEnCurso() {
        Titular t = titular();
        Lotes lote = new Lotes(1, 500, t);
        EmpleadosDeMantenimiento e = empleado();
        try {
            for (int i = 1; i <= 5; i++) {
                PedidosVecinos p = new Reclamos(i, TIEMPO, TipoReclamo.MANTENIMIENTO, "Pedido " + i, lote, t);
                p.asignar(e, TIEMPO.plusHours(1));
                p.avanzar(TIEMPO.plusHours(2), "Trabajando");
            }
            Verificar.igual("Limite: el empleado tiene 5 en curso", 5, e.cantidadenCurso());
            PedidosVecinos sexto = new Reclamos(6, TIEMPO, TipoReclamo.MANTENIMIENTO, "Pedido 6", lote, t);
            sexto.asignar(e, TIEMPO.plusHours(3));
            Verificar.falla("Limite de 5 pedidos en curso", "no lanzo excepcion");
        } catch (LimiteReclamosException ex) {
            Verificar.ok("Limite de 5 pedidos en curso");
        } catch (TransicionInvalidaException ex) {
            Verificar.falla("Limite de 5 pedidos en curso", ex.getMessage());
        }
    }

    private static void probarAvanzarHastaResuelto() {
        Titular t = titular();
        Lotes lote = new Lotes(1, 500, t);
        PedidosVecinos p = new Reclamos(1, TIEMPO, TipoReclamo.MANTENIMIENTO, "Luz quemada", lote, t);
        try {
            p.asignar(empleado(), TIEMPO.plusHours(1));
            p.avanzar(TIEMPO.plusHours(2), "Empezo el arreglo");
            Verificar.igual("Avanzar: ASIGNADO pasa a EN_CURSO", EstadoReclamo.EN_CURSO, p.getEstado());
            p.avanzar(TIEMPO.plusHours(5), "Lampara cambiada");
            Verificar.igual("Avanzar: EN_CURSO pasa a RESUELTO", EstadoReclamo.RESUELTO, p.getEstado());
            Verificar.igual("Avanzar: el historial guarda los 4 cambios", 4, p.getHistorial().size());
        } catch (TransicionInvalidaException | LimiteReclamosException ex) {
            Verificar.falla("Avanzar hasta RESUELTO", ex.getMessage());
        }
    }

    private static PedidosVecinos pedidoResuelto(LocalDateTime resuelto) throws TransicionInvalidaException, LimiteReclamosException {
        Titular t = titular();
        Lotes lote = new Lotes(1, 500, t);
        PedidosVecinos p = new Reclamos(1, TIEMPO, TipoReclamo.MANTENIMIENTO, "Luz quemada", lote, t);
        p.asignar(empleado(), TIEMPO.plusHours(1));
        p.avanzar(TIEMPO.plusHours(2), "Empezo el arreglo");
        p.avanzar(resuelto, "Lampara cambiada");
        return p;
    }

    private static void probarReabrirDentroDelPlazo() {
        try {
            PedidosVecinos p = pedidoResuelto(TIEMPO.plusDays(1));
            p.reabrir(TIEMPO.plusDays(4));
            Verificar.igual("Reabrir a los 3 dias de resuelto: vuelve a EN_CURSO", EstadoReclamo.EN_CURSO, p.getEstado());
        } catch (TransicionInvalidaException | LimiteReclamosException ex) {
            Verificar.falla("Reabrir dentro del plazo", ex.getMessage());
        }
    }

    private static void probarReabrirFueraDelPlazo() {
        try {
            PedidosVecinos p = pedidoResuelto(TIEMPO.plusDays(1));
            p.reabrir(TIEMPO.plusDays(8));
            Verificar.falla("No se reabre despues de 5 dias", "no lanzo excepcion");
        } catch (TransicionInvalidaException ex) {
            Verificar.ok("No se reabre despues de 5 dias");
        } catch (LimiteReclamosException ex) {
            Verificar.falla("No se reabre despues de 5 dias", ex.getMessage());
        }
    }

    private static void probarSolicitudNoSeReabreNiSeCierraSola() {
        Titular t = titular();
        Lotes lote = new Lotes(1, 500, t);
        PedidosVecinos s = new Solicitud(1, TIEMPO, TipoReclamo.MANTENIMIENTO, "Podar el arbol", lote, t);
        try {
            s.asignar(empleado(), TIEMPO.plusHours(1));
            s.avanzar(TIEMPO.plusHours(2), "Empezo el trabajo");
            s.avanzar(TIEMPO.plusDays(1), "Arbol podado");
            Verificar.verdadero("Solicitud: a los 6 dias de resuelta no se cierra sola", !s.cerrarsiVencio(TIEMPO.plusDays(7)));
            s.reabrir(TIEMPO.plusDays(2));
            Verificar.falla("Una solicitud no se reabre", "no lanzo excepcion");
        } catch (TransicionInvalidaException ex) {
            Verificar.ok("Una solicitud no se reabre");
        } catch (LimiteReclamosException ex) {
            Verificar.falla("Una solicitud no se reabre", ex.getMessage());
        }
    }

    private static void probarCierreAutomatico() {
        try {
            PedidosVecinos p = pedidoResuelto(TIEMPO.plusDays(1));
            Verificar.verdadero("A los 3 dias de resuelto no se cierra solo", !p.cerrarsiVencio(TIEMPO.plusDays(4)));
            Verificar.verdadero("A los 6 dias de resuelto se cierra solo", p.cerrarsiVencio(TIEMPO.plusDays(7)));
            Verificar.igual("Cierre automatico: queda CERRADO", EstadoReclamo.CERRADO, p.getEstado());
        } catch (TransicionInvalidaException | LimiteReclamosException ex) {
            Verificar.falla("Cierre automatico", ex.getMessage());
        }
    }
    private static void probarEscalamiento() {
        Titular t = titular();
        Lotes lote = new Lotes(1, 500, t);
        Administrador admin = new Administrador(1, "Marta Gomez", LocalDate.of(2020, 3, 1));
        PedidosVecinos p = new Reclamos(1, TIEMPO, TipoReclamo.SEGURIDAD, "Porton abierto", lote, t);
        Verificar.verdadero("A las 10 horas no se escala", !p.escalarPortiempo(TIEMPO.plusHours(10), admin));
        Verificar.verdadero("A las 25 horas se escala", p.escalarPortiempo(TIEMPO.plusHours(25), admin));
        Verificar.verdadero("Escalamiento: queda marcado", p.isEscalado());
        Verificar.verdadero("La segunda vez ya no se escala", !p.escalarPortiempo(TIEMPO.plusHours(30), admin));
        Verificar.igual("Escalamiento: el admin recibe una sola notificacion", 1, admin.verNotificaciones().size());
    }

    private static void probarCancelar() {
        Titular t = titular();
        Lotes lote = new Lotes(1, 500, t);
        PedidosVecinos p = new Solicitud(1, TIEMPO, TipoReclamo.MANTENIMIENTO, "Podar el arbol", lote, t);
        try {
            p.cancelar(TIEMPO.plusHours(1));
            Verificar.igual("Cancelar un pedido ABIERTO lo deja CERRADO", EstadoReclamo.CERRADO, p.getEstado());
            p.cancelar(TIEMPO.plusHours(2));
            Verificar.falla("No se cancela un pedido CERRADO", "no lanzo excepcion");
        } catch (TransicionInvalidaException ex) {
            Verificar.ok("No se cancela un pedido CERRADO");
        }
    }
    private static void probarOrdenPorFechaYPorPrioridad() {
        Titular t = titular();
        Lotes lote = new Lotes(1, 500, t);
        LocalDateTime dia = LocalDateTime.of(2026, 11, 2, 0, 0);
        List<PedidosVecinos> pedidos = new ArrayList<>();
        pedidos.add(new Reclamos(1, dia.plusHours(10), TipoReclamo.CONVIVENCIA, "Ruidos", lote, t));
        pedidos.add(new Reclamos(2, dia.plusHours(11), TipoReclamo.SEGURIDAD, "Porton", lote, t));
        pedidos.add(new Reclamos(3, dia.plusHours(9), TipoReclamo.MANTENIMIENTO, "Luz", lote, t));
        pedidos.add(new Reclamos(4, dia.plusHours(8), TipoReclamo.SEGURIDAD, "Camara", lote, t));

        Collections.sort(pedidos);
        Verificar.igual("Orden natural por fecha", "4 3 1 2", numeros(pedidos));

        pedidos.sort(new ComparadorPorPrioridad());
        Verificar.igual("Orden por prioridad, ALTA primero", "4 2 3 1", numeros(pedidos));
    }

    private static String numeros(List<PedidosVecinos> pedidos) {
        String texto = "";
        for (int i = 0; i < pedidos.size(); i++) {
            if (i > 0) {
                texto = texto + " ";
            }
            texto = texto + pedidos.get(i).getNumero();
        }
        return texto;
    }
}
