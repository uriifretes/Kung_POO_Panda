package controladores;

import lotes.Lotes;
import personal.Personal;
import personas.Personas;
import reclamos.PedidosVecinos;
import reclamos.Reclamos;
import reclamos.Solicitud;
import reportes.Reportes;
import reportes.ReportesReclamosPendientes;

import Enums.TipoReclamo;
import Excepciones.LimiteReclamosException;
import Excepciones.TransicionInvalidaException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ControladorReclamos {
    private ControladorLotes controladorLotes;
    private ControladorPersonal controladorPersonal;
    private List<PedidosVecinos> pedidos;
    private int proximoNumero;

    public ControladorReclamos(ControladorLotes controladorLotes, ControladorPersonal controladorPersonal) {
        this.controladorLotes = controladorLotes;
        this.controladorPersonal = controladorPersonal;
        this.pedidos = new ArrayList<>();
        this.proximoNumero = 1;
    }

    public List<PedidosVecinos> getPedidos() {
        List<PedidosVecinos> devolver = new ArrayList<>();
        for (int i = 0; i < pedidos.size(); i++) {
            devolver.add(pedidos.get(i));
        }
        return devolver;
    }


    private Personas buscarPersona(Lotes l, String doc){
        if (l.getTitular().getDocumento().equals(doc)){
            return  l.getTitular();
        }
        if (l.buscarResidente(doc)!=null){
            return l.buscarResidente(doc);
        }
        throw new IllegalArgumentException("Esa persona no es de ese lote");
    }

    public PedidosVecinos crearReclamo(int nLote, String doc, TipoReclamo tipo, String descripcion){
        Lotes l = controladorLotes.tenerLote(nLote);
        Personas p = buscarPersona(l,doc);
        Reclamos r = new Reclamos(proximoNumero, LocalDateTime.now(),tipo,descripcion,l,p);
        pedidos.add(r);
        proximoNumero= proximoNumero +1;
        return r;
    }


    public PedidosVecinos crearSolicitud(int nLote, String doc, TipoReclamo tipo, String descripcion){
        Lotes l = controladorLotes.tenerLote(nLote);
        Personas p = buscarPersona(l,doc);
        Solicitud r = new Solicitud(proximoNumero, LocalDateTime.now(),tipo,descripcion,l,p);
        pedidos.add(r);
        proximoNumero= proximoNumero +1;
        return r;
    }

    public PedidosVecinos tenerPedido(int num){
        for (int i = 0; i < pedidos.size(); i++) {
            if (pedidos.get(i).getNumero()==num){
                return pedidos.get(i);
            }
        }
        throw new IllegalArgumentException("No existe ese pedido");
    }

    public void asignar(int numeropedido, int legajo) throws TransicionInvalidaException, LimiteReclamosException {
        Personal p = controladorPersonal.buscarPersonal(legajo);
        if (p==null){
            throw new IllegalArgumentException("Esa persona no existe");
        }
        tenerPedido(numeropedido).asignar(p,LocalDateTime.now());
    }

    public void avanzar(int numero, String obs) throws TransicionInvalidaException{
        tenerPedido(numero).avanzar(LocalDateTime.now(),obs);
    }

    public void cancelar(int numero) throws TransicionInvalidaException{
        tenerPedido(numero).cancelar(LocalDateTime.now());
    }

    public void reabrir(int numero) throws TransicionInvalidaException{
        tenerPedido(numero).reabrir(LocalDateTime.now());
    }
    public void  comentar(int npedido, String autor, String com){
        tenerPedido(npedido).agregarComentario(LocalDateTime.now(),autor,com);
    }

    public String reportePendientes(){
        Reportes r = new ReportesReclamosPendientes(pedidos);
        return r.generar();
    }

}
