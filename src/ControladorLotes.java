import Enums.TipoReclamo;
import Enums.TipoResidente;

import java.util.ArrayList;
import java.util.List;

public class ControladorLotes {
    private List<Lotes> lotes;

    public ControladorLotes(){
        this.lotes = new ArrayList<>();
    }

    public List<Lotes> getLotes() {
        List<Lotes> devolver = new ArrayList<>();
        for (int i = 0; i < lotes.size(); i++) {
            devolver.add(lotes.get(i));
        }
        return devolver;
    }

    public Lotes buscarLote(int numero){
        for (int i = 0; i < lotes.size(); i++) {
            if (lotes.get(i).getNumero()==numero){
                return  lotes.get(i);
            }
        }
        return null;

    }

    public Lotes tenerLote(int numero){
        Lotes l = buscarLote(numero);
        if (l==null){
            throw new IllegalArgumentException("El lote no existe");
        }
        return l;
    }

    public Lotes registrarLote(int n, double superfie, String nombre, String apellido,  String documetn, String telefono, String correo){
        if (buscarLote(n)!=null){
            throw new IllegalArgumentException("El lote ya existe");
        }
        Titular t = new Titular(nombre,apellido,documetn,telefono,correo);
        Lotes l = new Lotes(n,superfie,t);
        lotes.add(l);
        return l;
    }

    public void agregarResidente(int nLote, String nombre, String apellido, String documento, String telefono, String correo, TipoResidente tipo){
        Lotes l = tenerLote(nLote);
        Residentes r = new Residentes(nombre,apellido,documento,telefono,correo,tipo);
        l.agregarResidente(r);
    }

    public void autorizarReclamos(int nLote, String doc, boolean valor){
        Lotes l = tenerLote(nLote);
        Residentes r = l.buscarResidente(doc);
        if (r==null){
            throw new IllegalArgumentException("No existe ese residente");
        }
        r.setAutorizadoReclamos(valor);
    }

    public void habilitarVisitas(int nLote, String documento, boolean valor){
        Lotes l = tenerLote(nLote);
        Residentes r = l.buscarResidente(documento);
        if (r==null){
            throw new IllegalArgumentException("No existe ese residente");
        }
        r.setPuedeAutorizarVisitas(valor);

    }


}
