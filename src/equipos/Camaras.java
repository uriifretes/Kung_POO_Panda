package equipos;

import notificaciones.Notificaciones;
import personal.Administrador;

import Enums.Prioridad;
import Enums.TipoNotificacion;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Camaras extends Equipos{
    private String resolucion;
    private int diasGrabacion;
    private boolean avisado;

    public Camaras(String codigo, LocalDate fechaInstalacion, Sectores sector, String resolucion, int diasGrabacion) {
        super(codigo, fechaInstalacion, sector);
        if (resolucion==null||diasGrabacion<=0){
            throw new IllegalArgumentException("Has ingresado datos invalidos");
        }
        this.resolucion = resolucion;
        this.diasGrabacion = diasGrabacion;
        this.avisado = false;
    }
    @Override
    public Prioridad prioridaddeFalla(){
        return Prioridad.ALTA;
    }
    public String saberTipo(){
        return "Camara";
    }
    public boolean superaHorasSinFuncionar(LocalDateTime ahora){
        if (!estaFallando()){
            return false;
        }
        if (ultimaFalla() == null){
            return false;
        }
        long horas = ChronoUnit.HOURS.between(ultimaFalla().getFechaHora(), ahora);
        if (horas>48){
            return true;
        }
        else {
            return false;
        }


    }
    public boolean avisarSiCorresponde(LocalDateTime ahora, Administrador admin) {
        if (!avisado && superaHorasSinFuncionar(ahora)) {
            avisado = true;
            admin.agregarNotificacion(new Notificaciones(ahora, TipoNotificacion.CAMARA_SIN_FUNCIONAR,
                    "La camara " + getCodigo() + " lleva mas de 48 horas sin funcionar", admin));
            return true;
        }
        return false;
    }

}
