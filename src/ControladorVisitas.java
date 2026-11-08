import Excepciones.IngresoDuplicadoException;
import Excepciones.SinAutorizacionException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ControladorVisitas {

    private ControladorLotes controladorLotes;
    private ControladorPersonal controladorPersonal;
    private List<Visitantes> visitantes;

    public ControladorVisitas(ControladorLotes controladorLotes, ControladorPersonal controladorPersonal) {
        this.controladorLotes = controladorLotes;
        this.controladorPersonal = controladorPersonal;
        this.visitantes = new ArrayList<>();
    }

    public Visitantes buscarVisitante(String doc){
        for (int i = 0; i < visitantes.size(); i++) {
            if (visitantes.get(i).getDocumento().equals(doc)){
                return visitantes.get(i);
            }
        }
        return null;
    }

    public Visitantes tenerVisitante(String doc){
        if (buscarVisitante(doc)==null){
            throw new IllegalArgumentException("El visitante no existe");
        }
        return buscarVisitante(doc);
    }


    public void registrarVisitante(String nombre, String apellido, String doc, String tel, String correo){
        if (buscarVisitante(doc)!=null){
            throw new IllegalArgumentException("El visitante ya existe");
        }
        Visitantes v = new Visitantes(nombre,apellido,doc,tel,correo);
        visitantes.add(v);
    }

    public void registrarProveedor(String nombre, String apellido, String doc, String tel, String correo, String empresa){
        if (buscarVisitante(doc)!=null){
            throw new IllegalArgumentException("El visitante ya existe");
        }
        Visitantes v = new Proveedor(nombre,apellido,doc,tel, correo, empresa);
        visitantes.add(v);
    }

    public void autorizacionUnica(int numeroLote, String docTi, String docVi,
                                  LocalTime desde, LocalTime hasta, LocalDate fecha) {
        Lotes lote = controladorLotes.tenerLote(numeroLote);
        Personas autorizador = lote.getTitular();
        if (!autorizador.getDocumento().equals(docTi)) {
            autorizador = lote.buscarResidente(docTi);
        }
        if (autorizador == null || !lote.puedeAutorizarVisitas(autorizador)) {
            throw new IllegalArgumentException("Esa persona no puede autorizar visitas en el lote");
        }
        Visitantes v = tenerVisitante(docVi);
        new AutorizacionesUnicas(v, desde, hasta, fecha);
    }

    public void registrarIngreso(String docVi, int legajo) throws SinAutorizacionException, IngresoDuplicadoException {
        Visitantes v = tenerVisitante(docVi);
        GuardiasDeSeguridad guardia = controladorPersonal.tenerGuardia(legajo);
        LocalDateTime ahora = LocalDateTime.now();
        List<Autorizaciones> a  = v.getAutorizaciones();
        for (int i = 0; i < a.size(); i++) {
            if (a.get(i).estaVigente(ahora)) {
                a.get(i).registrarIngreso(guardia, ahora);
                return;
            }
        }
        throw new SinAutorizacionException("No tiene ninguna autorizacion vigente");
    }

    public void aRecurrente(int numeroLote, String docTi, String docVi,
                                       LocalTime desde, LocalTime hasta, LocalDate fechaDesde, LocalDate fechaHasta) {
        Lotes lote = controladorLotes.tenerLote(numeroLote);
        Personas autorizador = lote.getTitular();
        if (!autorizador.getDocumento().equals(docTi)) {
            autorizador = lote.buscarResidente(docTi);
        }
        if (autorizador == null || !lote.puedeAutorizarVisitas(autorizador)) {
            throw new IllegalArgumentException("Esa persona no puede autorizar visitas");
        }
        Visitantes v = tenerVisitante(docVi);
        new AutorizacionesRecurrentes(v, desde, hasta, fechaDesde, fechaHasta);
    }


    public void rEgreso(String docVi) {
        Visitantes v = tenerVisitante(docVi);
        List<Autorizaciones> a = v.getAutorizaciones();
        for (int i = 0; i < a.size(); i++) {
            if (a.get(i).tieneIngresoAbierto()) {
                a.get(i).registrarEgreso(LocalDateTime.now());
                return;
            }
        }
        throw new IllegalStateException("La persona no esta adentro");
    }
}
