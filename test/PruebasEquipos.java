import Enums.Especialidad;
import Enums.EstadoEquipo;
import Enums.Prioridad;
import Enums.TipoSector;
import equipos.Camaras;
import equipos.Equipos;
import equipos.Luminarias;
import equipos.OrdenesDeMantenimiento;
import equipos.Sectores;
import personal.Administrador;
import personal.EmpleadosDeMantenimiento;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PruebasEquipos {
    private static final LocalDateTime TIEMPO = LocalDateTime.of(2026, 11, 1, 10, 0);
    private static final LocalDate INSTALACION = LocalDate.of(2025, 1, 1);

    public static void main(String[] args) {
        System.out.println("==== Pruebas de equipos ====");
        probarPrioridadCamara();
        probarPrioridadLuminaria();
        probarCicloDeUnEquipo();
        probarFueraDeServicio();
        probarAvisoDeCamara();
        Verificar.resumen("Equipos");
    }
    private static void probarPrioridadCamara() {
        Sectores s = new Sectores("Acceso", TipoSector.ACCESO);
        Equipos c = new Camaras("CAM-01", INSTALACION, s, "1080p", 30);
        Verificar.igual("Camara: prioridad ALTA", Prioridad.ALTA, c.prioridaddeFalla());
    }

    private static void probarPrioridadLuminaria() {
        Sectores s = new Sectores("Calle 1", TipoSector.CALLE_INTERNA);
        Equipos l1 = new Luminarias("LUM-01", INSTALACION, s, "LED", 40);
        Equipos l2 = new Luminarias("LUM-02", INSTALACION, s, "LED", 40);
        Equipos l3 = new Luminarias("LUM-03", INSTALACION, s, "LED", 40);
        Equipos l4 = new Luminarias("LUM-04", INSTALACION, s, "LED", 40);
        l1.registrarFalla(TIEMPO, "No enciende", "Ana Lopez");
        l2.registrarFalla(TIEMPO, "No enciende", "Ana Lopez");
        l3.registrarFalla(TIEMPO, "No enciende", "Ana Lopez");
        Verificar.igual("Luminaria con 3 fallando en el sector: MEDIA", Prioridad.MEDIA, l1.prioridaddeFalla());
        l4.registrarFalla(TIEMPO, "No enciende", "Ana Lopez");
        Verificar.igual("Luminaria con 4 fallando en el sector: ALTA", Prioridad.ALTA, l1.prioridaddeFalla());
    }

    private static void probarCicloDeUnEquipo() {
        Sectores s = new Sectores("Calle 2", TipoSector.CALLE_INTERNA);
        Equipos l = new Luminarias("LUM-05", INSTALACION, s, "LED", 40);
        EmpleadosDeMantenimiento tecnico = new EmpleadosDeMantenimiento(20, "Luis Torres", LocalDate.of(2021, 2, 1), Especialidad.ELECTRICIDAD);

        l.registrarFalla(TIEMPO, "Parpadea", "Carlos Perez");
        Verificar.igual("Registrar falla: queda CON_FALLA", EstadoEquipo.CON_FALLA, l.getEstado());

        OrdenesDeMantenimiento o = l.generarOrden("Cambiar la lampara");
        Verificar.igual("Generar orden: queda EN_REPARACION", EstadoEquipo.EN_REPARACION, l.getEstado());
        Verificar.igual("Generar orden: la orden toma la prioridad del equipo", Prioridad.MEDIA, o.getPrioridad());
        l.cerrarOrden(o.getNumero(), LocalDate.now(), tecnico, "Lampara cambiada");
        Verificar.igual("Cerrar orden: vuelve a OPERATIVO", EstadoEquipo.OPERATIVO, l.getEstado());
    }

    private static void probarFueraDeServicio() {
        Sectores s = new Sectores("Plaza", TipoSector.ESPACIO_COMUN);
        Equipos l = new Luminarias("LUM-06", INSTALACION, s, "Sodio", 70);
        l.marcarFueraDeServicio();
        try {
            l.registrarFalla(TIEMPO, "No enciende", "Ana Lopez");
            Verificar.falla("Un equipo dado de baja no acepta fallas", "no lanzo excepcion");
        } catch (IllegalStateException e) {
            Verificar.ok("Un equipo dado de baja no acepta fallas");
        }
    }

    private static void probarAvisoDeCamara() {
        Sectores s = new Sectores("Plaza", TipoSector.ESPACIO_COMUN);
        Camaras c = new Camaras("CAM-02", INSTALACION, s, "720p", 15);
        Administrador admin = new Administrador(1, "Marta Gomez", LocalDate.of(2020, 3, 1));
        c.registrarFalla(TIEMPO, "Sin imagen", "Laura Benitez");
        Verificar.verdadero("Camara: a las 10 horas no avisa", !c.avisarSiCorresponde(TIEMPO.plusHours(10), admin));
        Verificar.verdadero("Camara: a las 49 horas avisa", c.avisarSiCorresponde(TIEMPO.plusHours(49), admin));
        Verificar.verdadero("Camara: no vuelve a avisar", !c.avisarSiCorresponde(TIEMPO.plusHours(60), admin));
        Verificar.igual("Camara: el admin recibe una sola notificacion", 1, admin.verNotificaciones().size());
    }
}
