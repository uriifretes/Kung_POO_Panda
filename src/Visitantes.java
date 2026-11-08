import java.util.ArrayList;
import java.util.List;

public class Visitantes extends Personas {
    private List<Autorizaciones> autorizaciones;
    public Visitantes(String nombre, String apellido, String documento, String telefono, String correo) {
        super(nombre, apellido, documento, telefono, correo);
        autorizaciones = new ArrayList<>();
    }

    public List<Autorizaciones> getAutorizaciones() {
        List<Autorizaciones> devuelvo = new ArrayList<>();
        for (int i = 0; i < autorizaciones.size(); i++) {
            devuelvo.add(autorizaciones.get(i));
        }
        return devuelvo;
    }

    public void agregarAutorizacion(Autorizaciones a){
        if (a==null){
            throw new IllegalArgumentException("La autorizacion no debe ser nula");
        }
        autorizaciones.add(a);
    }

    public boolean estaAdentro(){
        for (int i = 0; i < autorizaciones.size(); i++) {
            if (autorizaciones.get(i).tieneIngresoAbierto()){
                return true;
            }
        }
        return  false;
    }


}
