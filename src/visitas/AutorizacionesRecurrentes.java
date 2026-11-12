package visitas;

import personal.GuardiasDeSeguridad;

import Excepciones.IngresoDuplicadoException;
import Excepciones.SinAutorizacionException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class AutorizacionesRecurrentes extends Autorizaciones {
    private LocalDate fechaDesde;
    private LocalDate fechaHasta;

    public AutorizacionesRecurrentes(Visitantes visitante, LocalTime horaDesde, LocalTime horaHasta, LocalDate fechaDesde, LocalDate fechaHasta) {
        super(visitante, horaDesde, horaHasta);
        if (fechaDesde==null|| fechaHasta==null|| !fechaDesde.isBefore(fechaHasta)){
            throw new IllegalArgumentException("Has ingresado datos invalidos");
        }
        long dias = ChronoUnit.DAYS.between(fechaDesde, fechaHasta) + 1; ///Para que a partir de 30 dias ya no sirven las autorizaciones
        if (dias > 30) {
            throw new IllegalArgumentException("La autorización recurrente puede durar como máximo 30 días");
        }
        this.fechaDesde = fechaDesde;
        this.fechaHasta = fechaHasta;
    }

    public LocalDate getFechaDesde() {
        return fechaDesde;
    }

    public LocalDate getFechaHasta() {
        return fechaHasta;
    }

    @Override
    public boolean estaVigente(LocalDateTime momento) {
        if (isCancelada()){
            return false;
        }
        if (momento.toLocalDate().isBefore(fechaDesde)|| momento.toLocalDate().isAfter(fechaHasta)){
            return false;
        }
        return dentrodeFranja(momento.toLocalTime());
    }

    @Override
    public Ingresos registrarIngreso(GuardiasDeSeguridad guardia, LocalDateTime momento) throws SinAutorizacionException, IngresoDuplicadoException {
        validarIngreso(guardia,momento);
        Ingresos n = new Ingresos(momento, guardia);
        agregarIngresar(n);
        return n;
    }
}
