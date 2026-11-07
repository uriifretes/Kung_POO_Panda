import Enums.Prioridad;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Camaras extends Equipos{
    private String resolucion;
    private int diasGrabacion;

    public Camaras(String codigo, LocalDate fechaInstalacion, Sectores sector, String resolucion, int diasGrabacion) {
        super(codigo, fechaInstalacion, sector);
        if (resolucion==null||diasGrabacion<=0){
            throw new IllegalArgumentException("Has ingresado datos invalidos");
        }
        this.resolucion = resolucion;
        this.diasGrabacion = diasGrabacion;
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

}
