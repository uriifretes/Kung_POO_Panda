import Enums.TipoSector;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ControladorEquipos {
    private ControladorPersonal controladorPersonal;
    private List<Sectores> sectores;
    private List<Equipos> equipos;

    public ControladorEquipos(ControladorPersonal controladorPersonal) {
        this.controladorPersonal = controladorPersonal;
        this.sectores = new ArrayList<>();
        this.equipos = new ArrayList<>();
    }

    public List<Equipos> getEquipos() {
        List<Equipos> devolver = new ArrayList<>();
        for (int i = 0; i < equipos.size(); i++) {
            devolver.add(equipos.get(i));
        }

        return devolver;
    }
    public Sectores buscarSector(String nombre){
        for (int i = 0; i < sectores.size(); i++) {
            if (sectores.get(i).getNombre().equals(nombre)){
                return sectores.get(i);
            }
        }
        return null;
    }

    public Equipos buscarEquipo(String codigo){
        for (int i = 0; i < equipos.size(); i++) {
            if (equipos.get(i).getCodigo().equals(codigo)){
                return equipos.get(i);
            }
        }
        return null;
    }

    public Sectores tenerSector(String nombre){
        for (int i = 0; i < sectores.size(); i++) {
            if (sectores.get(i).getNombre().equals(nombre)){
                return sectores.get(i);
            }
        }
        throw new IllegalArgumentException("No existe ese sector");
    }

    public Equipos tenerEquipo(String codigo){
        for (int i = 0; i < equipos.size(); i++) {
            if (equipos.get(i).getCodigo().equals(codigo)){
                return equipos.get(i);
            }
        }
        throw new IllegalArgumentException("No existe ese equipo");
    }

    public void registrarSector(String nombre, TipoSector tipo){
        if (buscarSector(nombre)!=null){
            throw new IllegalArgumentException("Ya existe un sector con ese nombre");
        }
        Sectores s = new Sectores(nombre,tipo);
        sectores.add(s);
    }

    public void registrarCamara(String codigo, String nombreSec, String resolucion, int diasGrabacion){
        if (buscarSector(nombreSec)==null){
            throw new IllegalArgumentException("No existe un sector con ese nombre");
        }
        if (buscarEquipo(codigo)!=null){
            throw new IllegalArgumentException("Ya existe un equipo con ese codigo");
        }
        Sectores s = tenerSector(nombreSec);
        Equipos c = new Camaras(codigo, LocalDate.now(),s,resolucion,diasGrabacion);
        equipos.add(c);
    }
    public void registrarLuminarias(String codigo, String nombreSec, String tipo, int potencia){
        if (buscarSector(nombreSec)==null){
            throw new IllegalArgumentException("No existe un sector con ese nombre");
        }
        if (buscarEquipo(codigo)!=null){
            throw new IllegalArgumentException("Ya existe un equipo con ese codigo");
        }
        Sectores s = tenerSector(nombreSec);
        Equipos l = new Luminarias(codigo,LocalDate.now(),s, tipo, potencia );
        equipos.add(l);
    }

    public void registrarFalla(String cod, String descripcion, String reportadapor){
        if (buscarEquipo(cod)==null){
            throw new IllegalArgumentException("El equipo no existe");
        }
        tenerEquipo(cod).registrarFalla(LocalDateTime.now(),descripcion,reportadapor);
    }

    public void registrarOrden(String cod, String descripcion){
        if (buscarEquipo(cod)==null){
            throw new IllegalArgumentException("El equipo no existe");
        }
        tenerEquipo(cod).generarOrden(descripcion);
    }

    public void marcarFueraDeServ(String codigo) {
        if (buscarEquipo(codigo) == null) {
            throw new IllegalArgumentException("El equipo no existe");
        }
        tenerEquipo(codigo).marcarFueraDeServicio();
    }

    public void registrarControl(String codigo){
        if (buscarEquipo(codigo) == null) {
            throw new IllegalArgumentException("El equipo no existe");
        }
        tenerEquipo(codigo).registrarControl(LocalDate.now());
    }

    public String reporteFueraDeServ(){
        Reportes r = new ReportesEquiposFueraDeServicio(equipos);
        return r.generar();
    }

    public void cerrarOrden(String codigo, int numeroOrden, int legajoTecnico, String descripcion){
        EmpleadosDeMantenimiento tecnico = controladorPersonal.obtenerEmpleado(legajoTecnico);
        tenerEquipo(codigo).cerrarOrden(numeroOrden, LocalDate.now(), tecnico, descripcion);
    }

}
