package vistas;

import controladores.ControladorVisitas;
import visitas.Autorizaciones;
import visitas.AutorizacionesRecurrentes;
import visitas.AutorizacionesUnicas;
import visitas.Ingresos;
import visitas.Proveedor;
import visitas.Visitantes;

import Excepciones.IngresoDuplicadoException;
import Excepciones.SinAutorizacionException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class VistaVisitas {
    private ControladorVisitas controlador;

    public VistaVisitas(ControladorVisitas controlador) {
        this.controlador = controlador;
    }

    public void registrarVisitante() {
        Consola.titulo("Registrar visitante");
        try {
            String nombre = Consola.leerTexto("Nombre");
            String apellido = Consola.leerTexto("Apellido");
            String doc = Consola.leerTexto("Documento");
            String tel = Consola.leerTexto("Telefono");
            String correo = Consola.leerTexto("Correo");
            controlador.registrarVisitante(nombre, apellido, doc, tel, correo);
            System.out.println("Visitante registrado.");
        } catch (IllegalArgumentException e) {
            Consola.error(e);
        }
    }

    public void registrarProveedor() {
        Consola.titulo("Registrar proveedor");
        try {
            String nombre = Consola.leerTexto("Nombre");
            String apellido = Consola.leerTexto("Apellido");
            String doc = Consola.leerTexto("Documento");
            String tel = Consola.leerTexto("Telefono");
            String correo = Consola.leerTexto("Correo");
            String empresa = Consola.leerTexto("Empresa");
            controlador.registrarProveedor(nombre, apellido, doc, tel, correo, empresa);
            System.out.println("Proveedor registrado.");
        } catch (IllegalArgumentException e) {
            Consola.error(e);
        }
    }

    public void autorizacionUnica(int nLote, String docAutorizante) {
        Consola.titulo("Autorizar una visita (un solo ingreso)");
        try {
            String docVisitante = Consola.leerTexto("Documento del visitante");
            LocalDate fecha = Consola.leerFecha("Fecha de la visita");
            LocalTime desde = Consola.leerHora("Hora desde");
            LocalTime hasta = Consola.leerHora("Hora hasta");
            controlador.autorizacionUnica(nLote, docAutorizante, docVisitante, desde, hasta, fecha);
            System.out.println("Autorizacion registrada.");
        } catch (IllegalArgumentException e) {
            Consola.error(e);
        }
    }

    public void autorizacionRecurrente(int nLote, String docAutorizante) {
        Consola.titulo("Autorizar un proveedor/visita recurrente (maximo 30 dias)");
        try {
            String docVisitante = Consola.leerTexto("Documento del visitante");
            LocalDate fechaDesde = Consola.leerFecha("Fecha desde");
            LocalDate fechaHasta = Consola.leerFecha("Fecha hasta");
            LocalTime desde = Consola.leerHora("Hora desde");
            LocalTime hasta = Consola.leerHora("Hora hasta");
            controlador.aRecurrente(nLote, docAutorizante, docVisitante, desde, hasta, fechaDesde, fechaHasta);
            System.out.println("Autorizacion recurrente registrada.");
        } catch (IllegalArgumentException e) {
            Consola.error(e);
        }
    }

    public void registrarIngreso(int legajoGuardia) {
        Consola.titulo("Registrar ingreso");
        try {
            String doc = Consola.leerTexto("Documento del visitante");
            controlador.registrarIngreso(doc, legajoGuardia);
            System.out.println("Ingreso registrado a las " + LocalDateTime.now().format(Consola.FORMATO_HORA) + ".");
        } catch (SinAutorizacionException | IngresoDuplicadoException | IllegalArgumentException e) {
            Consola.error(e);
        }
    }

    public void registrarEgreso() {
        Consola.titulo("Registrar egreso");
        try {
            String doc = Consola.leerTexto("Documento del visitante");
            controlador.rEgreso(doc);
            System.out.println("Egreso registrado a las " + LocalDateTime.now().format(Consola.FORMATO_HORA) + ".");
        } catch (IllegalArgumentException | IllegalStateException e) {
            Consola.error(e);
        }
    }

    public void verAutorizaciones() {
        String doc = Consola.leerTexto("Documento del visitante");
        verAutorizaciones(doc);
    }

    public void verAutorizaciones(String doc) {
        try {
            Visitantes v = controlador.tenerVisitante(doc);
            Consola.titulo("Autorizaciones de " + v.getNombre() + " " + v.getApellido());
            if (v instanceof Proveedor) {
                Proveedor p = (Proveedor) v;
                System.out.println("Proveedor de la empresa: " + p.getEmpresa());
            }
            System.out.println(v.estaAdentro() ? "Actualmente esta DENTRO del barrio." : "Actualmente esta fuera del barrio.");
            List<Autorizaciones> lista = v.getAutorizaciones();
            if (lista.isEmpty()) {
                System.out.println("No tiene autorizaciones.");
            }
            for (int i = 0; i < lista.size(); i++) {
                mostrarAutorizacion(lista.get(i));
            }
        } catch (IllegalArgumentException e) {
            Consola.error(e);
        }
    }

    private void mostrarAutorizacion(Autorizaciones a) {
        String tipo;
        if (a instanceof AutorizacionesUnicas) {
            AutorizacionesUnicas u = (AutorizacionesUnicas) a;
            tipo = "Unica el " + u.getFecha().format(Consola.FORMATO_FECHA);
        } else {
            AutorizacionesRecurrentes r = (AutorizacionesRecurrentes) a;
            tipo = "Recurrente del " + r.getFechaDesde().format(Consola.FORMATO_FECHA)
                    + " al " + r.getFechaHasta().format(Consola.FORMATO_FECHA);
        }
        String estado;
        if (a.isCancelada()) {
            estado = "CANCELADA";
        } else if (a.estaVigente(LocalDateTime.now())) {
            estado = "VIGENTE AHORA";
        } else {
            estado = "no vigente ahora";
        }
        System.out.println("- " + tipo + " | " + a.getHoraDesde().format(Consola.FORMATO_HORA) + " a "
                + a.getHoraHasta().format(Consola.FORMATO_HORA) + " | " + estado);
        List<Ingresos> ingresos = a.getIngresos();
        for (int i = 0; i < ingresos.size(); i++) {
            Ingresos in = ingresos.get(i);
            String salida = (in.getFechaHoraEgreso() == null) ? "aun adentro" : Consola.fechaHora(in.getFechaHoraEgreso());
            System.out.println("     Ingreso " + Consola.fechaHora(in.getFechaHoraIngreso()) + " | Egreso: " + salida);
        }
    }
}
