package vistas;

import controladores.ControladorPersonal;
import personal.Administrador;
import personal.EmpleadosDeMantenimiento;
import personal.GuardiasDeSeguridad;

import Enums.Especialidad;
import Enums.PuestoGuardia;
import Enums.Turno;

import java.time.LocalDate;
import java.util.List;

public class VistaPersonal {
    private ControladorPersonal controlador;

    public VistaPersonal(ControladorPersonal controlador) {
        this.controlador = controlador;
    }

    public void registrarAdministrador() {
        Consola.titulo("Registrar administrador");
        try {
            int legajo = Consola.leerEntero("Legajo");
            String nombre = Consola.leerTexto("Nombre");
            controlador.registrarAdministrador(legajo, nombre, Consola.leerFecha("Fecha de ingreso"));
            System.out.println("Administrador registrado.");
        } catch (IllegalArgumentException e) {
            Consola.error(e);
        }
    }

    public void registrarGuardia() {
        Consola.titulo("Registrar guardia de seguridad");
        try {
            int legajo = Consola.leerEntero("Legajo");
            String nombre = Consola.leerTexto("Nombre");
            LocalDate ingreso = Consola.leerFecha("Fecha de ingreso");
            Turno turno = Consola.leerEnum("Turno", Turno.class);
            PuestoGuardia puesto = Consola.leerEnum("Puesto", PuestoGuardia.class);
            controlador.registrarGuardia(legajo, nombre, ingreso, turno, puesto);
            System.out.println("Guardia registrado.");
        } catch (IllegalArgumentException e) {
            Consola.error(e);
        }
    }

    public void registrarEmpleado() {
        Consola.titulo("Registrar empleado de mantenimiento");
        try {
            int legajo = Consola.leerEntero("Legajo");
            String nombre = Consola.leerTexto("Nombre");
            LocalDate ingreso = Consola.leerFecha("Fecha de ingreso");
            Especialidad esp = Consola.leerEnum("Especialidad", Especialidad.class);
            controlador.registrarEmpleado(legajo, nombre, ingreso, esp);
            System.out.println("Empleado registrado.");
        } catch (IllegalArgumentException e) {
            Consola.error(e);
        }
    }

    public void listarPersonal() {
        Consola.titulo("Personal del barrio");
        try {
            Administrador a = controlador.getAdmin();
            System.out.println("Administrador: legajo " + a.getLegajo() + " - " + a.getNombre());
        } catch (IllegalArgumentException e) {
            System.out.println("Administrador: sin registrar");
        }
        List<GuardiasDeSeguridad> guardias = controlador.getGuardias();
        for (int i = 0; i < guardias.size(); i++) {
            GuardiasDeSeguridad g = guardias.get(i);
            System.out.println("Guardia: legajo " + g.getLegajo() + " - " + g.getNombre()
                    + (g.esPortero() ? " (porteria)" : ""));
        }
        List<EmpleadosDeMantenimiento> empleados = controlador.getEmpleados();
        for (int i = 0; i < empleados.size(); i++) {
            EmpleadosDeMantenimiento e = empleados.get(i);
            System.out.println("Mantenimiento: legajo " + e.getLegajo() + " - " + e.getNombre()
                    + " | reclamos en curso: " + e.cantidadenCurso());
        }
    }
}