package lotes;

import expensas.CuentasCorrientes;
import personas.Personas;
import personas.Residentes;
import personas.Titular;
import reclamos.PedidosVecinos;

import java.util.*;
import java.io.Serializable;

public class Lotes implements Serializable {
    private int numero;
    private double superficie;
    private Titular titular;
    private List<Residentes> residentes;
    private CuentasCorrientes cuentaCorriente;
    private List<PedidosVecinos> pedidos;

    public Lotes(int numero, double superficie, Titular titular) {
        if (numero <= 0) {
            throw new IllegalArgumentException("El numero de lote debe ser mayor a 0");
        }
        if (superficie <= 0) {
            throw new IllegalArgumentException("La superficie debe ser mayor a 0");
        }
        if (titular == null) {
            throw new IllegalArgumentException("El lote debe tener un titular");
        }
        this.numero = numero;
        this.superficie = superficie;
        this.titular = titular;
        this.residentes = new ArrayList<>();
        this.pedidos = new ArrayList<>();
        this.cuentaCorriente = new CuentasCorrientes(this);
    }

    public Titular getTitular() {
        return titular;
    }

    public int getNumero() {
        return numero;
    }

    public double getSuperficie() {
        return superficie;
    }

    public List<Residentes> getResidentes() {
        List<Residentes> devuelvo = new ArrayList<>();
        for (int i = 0; i < residentes.size(); i++) {
            devuelvo.add(residentes.get(i));
        }
        return devuelvo;
    }

    public List<PedidosVecinos> getPedidos() {
        List<PedidosVecinos> devuelvo = new ArrayList<>();
        for (int i = 0; i < pedidos.size(); i++) {
            devuelvo.add(pedidos.get(i));
        }
        return devuelvo;
    }

    public CuentasCorrientes getCuentaCorriente() {
        return cuentaCorriente;
    }

    public Residentes buscarResidente(String documento){
        for (int i = 0; i < residentes.size(); i++) {
            if (residentes.get(i).getDocumento().equals(documento)){
                return residentes.get(i);
            }
        }
        return null;
    }

    public void agregarResidente(Residentes r){
        if (r==null|| r.getDocumento().equals(titular.getDocumento())|| buscarResidente(r.getDocumento())!=null){
            throw new IllegalArgumentException("Ingresaste un dato erroneo o ya pertenece al lote");
        }
        else {
            residentes.add(r);
        }

    }

    public boolean puedePresentarReclamos(Personas p){
        if (p.getDocumento().equals(titular.getDocumento())){
            return true;
        }

        Residentes r = buscarResidente(p.getDocumento());
        if (r!=null && r.isAutorizadoReclamos()){
            return true;
        }
        else {
            return false;
        }
    }

    public boolean puedeAutorizarVisitas(Personas p){
        if (p.getDocumento().equals(titular.getDocumento())){
            return true;
        }

        Residentes r = buscarResidente(p.getDocumento());
        if (r!=null && r.isPuedeAutorizarVisitas()){
            return true;
        }
        else {
            return false;
        }
    }

    public void agregarPedidos(PedidosVecinos pedido){
        if (pedido!=null){
            pedidos.add(pedido);
        }
        else {
            throw  new IllegalArgumentException("El pedido no puede ser nulo");
        }
    }

    public boolean estaMoroso(){
        if (cuentaCorriente.isMoroso()){
            return true;
        }
        else{
            return false;
        }
    }
}
