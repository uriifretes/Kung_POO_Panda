package controladores;

import personal.Administrador;
import personal.EmpleadosDeMantenimiento;
import personal.GuardiasDeSeguridad;
import personal.Personal;

import Enums.Especialidad;
import Enums.PuestoGuardia;
import Enums.Turno;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class ControladorPersonal implements Serializable {
    private Administrador admin;
    private List<GuardiasDeSeguridad> guardias;
    private List<EmpleadosDeMantenimiento> empleados;


    public ControladorPersonal() {
        this.admin = null;
        this.guardias = new ArrayList<>();
        this.empleados = new ArrayList<>();
    }


    public Administrador getAdmin() {
        if (admin==null){
            throw new IllegalArgumentException("No hay admin");
        }
        return admin;
    }

    public List<GuardiasDeSeguridad> getGuardias() {
        List<GuardiasDeSeguridad> devolver = new ArrayList<>();
        for (int i = 0; i < guardias.size(); i++) {
            devolver.add(guardias.get(i));
        }
        return devolver;
    }

    public List<EmpleadosDeMantenimiento> getEmpleados() {
        List<EmpleadosDeMantenimiento> devolver = new ArrayList<>();
        for (int i = 0; i < empleados.size(); i++) {
            devolver.add(empleados.get(i));
        }
        return devolver;
    }

    public Personal buscarPersonal(int legajo){
        if (admin != null && admin.getLegajo() == legajo) {
            return admin;
        }

        for (int i = 0; i < guardias.size(); i++) {
            if (guardias.get(i).getLegajo()==legajo){
                return guardias.get(i);
            }
        }
        for (int i = 0; i < empleados.size(); i++) {
            if (empleados.get(i).getLegajo()==legajo){
                return empleados.get(i);
            }
        }
        return null;
    }

    public GuardiasDeSeguridad tenerGuardia(int legajo){
        for (int i = 0; i < guardias.size(); i++) {
            if (guardias.get(i).getLegajo()==legajo){
                return guardias.get(i);
            }
        }
        throw new IllegalArgumentException("No existe un guardia con ese legajo");
    }

    public EmpleadosDeMantenimiento obtenerEmpleado(int legajo){
        for (int i = 0; i < empleados.size(); i++) {
            if (empleados.get(i).getLegajo()==legajo){
                return empleados.get(i);
            }
        }
        throw new IllegalArgumentException("No existe un empleado con ese legajo");

    }

    public void registrarAdministrador(int legajo, String nombre, LocalDate fechaIngreso){
        if (admin!=null){
            throw new IllegalArgumentException("Ya hay un admin");
        }

        if (buscarPersonal(legajo)!=null){
            throw new IllegalArgumentException("Ya existe el legajo");
        }
        Administrador a = new Administrador(legajo,nombre,fechaIngreso);
        admin = a;
    }

    public void  registrarGuardia(int legajo, String nombre, LocalDate fechaing, Turno turno, PuestoGuardia puesto){
        if (buscarPersonal(legajo)!=null){
            throw new IllegalArgumentException("Ya hay un personal con ese legajo");
        }

        GuardiasDeSeguridad g = new GuardiasDeSeguridad(legajo,nombre,fechaing,turno,puesto);
        guardias.add(g);
    }

    public void  registrarEmpleado(int legajo, String nombre, LocalDate fechaing, Especialidad esp){
        if (buscarPersonal(legajo)!=null){
            throw new IllegalArgumentException("Ya hay un personal con ese legajo");
        }

        EmpleadosDeMantenimiento g = new EmpleadosDeMantenimiento(legajo,nombre,fechaing,esp);
        empleados.add(g);
    }
}
