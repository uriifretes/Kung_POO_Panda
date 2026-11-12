package visitas;

import personal.GuardiasDeSeguridad;

import Excepciones.IngresoDuplicadoException;
import Excepciones.SinAutorizacionException;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public abstract class Autorizaciones {
    private LocalTime horaDesde;
    private LocalTime horaHasta;
    private boolean cancelada;
    private Visitantes visitante;
    private List<Ingresos> ingresos;

    public Autorizaciones(Visitantes visitante,LocalTime horaDesde, LocalTime horaHasta) {
        if (visitante==null||horaHasta==null||horaDesde==null|| !horaDesde.isBefore(horaHasta)){
            throw new IllegalArgumentException("Has ingresado datos invalidos");
        }
        this.visitante = visitante;
        this.cancelada = false;
        this.horaHasta = horaHasta;
        this.horaDesde = horaDesde;
        this.ingresos = new ArrayList<>();

        visitante.agregarAutorizacion(this);
    }

    public LocalTime getHoraDesde() {
        return horaDesde;
    }

    public LocalTime getHoraHasta() {
        return horaHasta;
    }

    public boolean isCancelada() {
        return cancelada;
    }

    public Visitantes getVisitante() {
        return visitante;
    }

    public List<Ingresos> getIngresos() {
        ArrayList<Ingresos> devuelvo = new ArrayList<>();
        for (int i = 0; i < ingresos.size(); i++) {
            devuelvo.add(ingresos.get(i));
        }
        return devuelvo;
    }

    public abstract boolean estaVigente(LocalDateTime momento);

    public boolean dentrodeFranja(LocalTime hora){
        if (!hora.isBefore(horaDesde) && !hora.isAfter(horaHasta)){
            return true;
        }
        else {
            return false;
        }
    }

    public void cancelar(){
        cancelada= true;
    }

    public boolean tieneIngresoAbierto(){
        for (int i = 0; i < ingresos.size(); i++) {
            if (ingresos.get(i).estaAdentro()){
                return true;
            }
        }
        return false;
    }

    protected void validarIngreso(GuardiasDeSeguridad guardia, LocalDateTime momento) throws SinAutorizacionException, IngresoDuplicadoException {
        if (visitante.estaAdentro()){
            throw new IngresoDuplicadoException("Ya hay un ingreso abierto");
        }
        if (!guardia.esPortero()){
            throw new  IllegalArgumentException("Solo el portero puede dar ingresos");
        }
        if (!estaVigente(momento)){
            throw new SinAutorizacionException("No posees una autorizacion");
        }
    }

    protected void agregarIngresar(Ingresos ingreso){
        ingresos.add(ingreso);
    }

    public abstract Ingresos registrarIngreso(GuardiasDeSeguridad guardia, LocalDateTime momento) throws SinAutorizacionException, IngresoDuplicadoException;

    public void registrarEgreso(LocalDateTime momento) {
        for (int i = 0; i < ingresos.size(); i++) {
            if (ingresos.get(i).estaAdentro()) {
                ingresos.get(i).registrarEgreso(momento);
                return;
            }
        }
        throw new IllegalStateException("No hay un ingreso abierto para esta autorización");
    }
}
