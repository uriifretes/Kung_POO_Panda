import java.time.LocalDate;

public class Camaras extends Equipos{
    private String resolucion;
    private int diasGrabacion;

    public Camaras(String codigo, LocalDate fechaInstalacion, Sectores sector, String resolucion, int diasGrabacion) {
        super(codigo, fechaInstalacion, sector);
        this.resolucion = resolucion;
        this.diasGrabacion = diasGrabacion;
    }
}
