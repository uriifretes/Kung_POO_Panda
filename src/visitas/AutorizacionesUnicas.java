package visitas;

import personal.GuardiasDeSeguridad;

import Excepciones.IngresoDuplicadoException;
import Excepciones.SinAutorizacionException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AutorizacionesUnicas extends Autorizaciones{
    private LocalDate fecha;

    public AutorizacionesUnicas(Visitantes visitante, LocalTime horaDesde, LocalTime horaHasta, LocalDate fecha) {
        super(visitante, horaDesde, horaHasta);
        if (fecha==null){
            throw new IllegalArgumentException("Has ingresado datos invalidos");
        }
        this.fecha = fecha;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    @Override
    public boolean estaVigente(LocalDateTime momento){
        if (momento.toLocalDate().equals(fecha)){
            return dentrodeFranja(momento.toLocalTime());
        }
        else {
            return false;
        }
    }

    @Override
    public Ingresos registrarIngreso(GuardiasDeSeguridad guardia, LocalDateTime momento) throws SinAutorizacionException, IngresoDuplicadoException {
        validarIngreso(guardia,momento);
        if(getIngresos().size()>0){
            throw new SinAutorizacionException("La autorizacion ya se uso");
        }
        else {
            Ingresos n = new Ingresos(momento, guardia);
            agregarIngresar(n);
            return n;
        }

    }
}
