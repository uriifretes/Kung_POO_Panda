package controladores;

import expensas.AsientosDeAjuste;
import expensas.Expensas;
import expensas.GestorExpensas;
import expensas.Pagos;
import lotes.Lotes;
import reportes.GeneradorReportes;
import reportes.ReportesMorosidad;

import Enums.MedioPago;
import Excepciones.PagoInvalidoException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class ControladorExpensas implements Serializable {
    private ControladorLotes controladorLotes;
    private GestorExpensas gestor;

    public ControladorExpensas(ControladorLotes controladorLotes) {
        this.controladorLotes = controladorLotes;
        this.gestor = new GestorExpensas();
    }

    public void emitirExpensas(YearMonth periodo, double presupuesto){
        gestor.emitirExpensas(periodo,presupuesto,controladorLotes.getLotes());
    }

    public List<Pagos> registrarPago(int numeroLote, double importe, MedioPago medio, String numOperacion, LocalDateTime fecha) throws PagoInvalidoException{
        if (medio.equals(MedioPago.TARJETA)&& !gestor.verificarOperacion(numOperacion,controladorLotes.getLotes())){
            throw new PagoInvalidoException("Ya se uso el numero de operacion");
        }

        Lotes l =controladorLotes.tenerLote(numeroLote);
        return (l.getCuentaCorriente().registrarPago(importe,medio,numOperacion,fecha));
    }

    public void registrarAsientos(int numeroLote, YearMonth periodo, double monto, String motivo, LocalDate fecha){
        Lotes l = controladorLotes.tenerLote(numeroLote);
        List<Expensas> expensas =  l.getCuentaCorriente().getExpensas();
        Expensas encontrada= null;
        for (int i = 0; i < expensas.size(); i++) {
            if (expensas.get(i).getPeriodo().equals(periodo)) {
                encontrada = expensas.get(i);
            }
        }

        if (encontrada == null) {
            throw new IllegalArgumentException("El lote " + numeroLote + " no tiene expensa del periodo " + periodo);
        }

        encontrada.agregarAsiento(new AsientosDeAjuste(fecha, monto, motivo));

    }
    public double consultarDeuda(int numeroLote, LocalDate fecha){
        Lotes l = controladorLotes.tenerLote(numeroLote);
        return l.getCuentaCorriente().deudaTotal(fecha);
    }

    public List<Expensas> verExpensas(int numeroL){
        Lotes l = controladorLotes.tenerLote(numeroL);
        return  l.getCuentaCorriente().getExpensas();
    }

    public String reporteMorosidad(LocalDate fecha){
        GeneradorReportes generador = new GeneradorReportes();
        return generador.emitir(new ReportesMorosidad(fecha, controladorLotes.getLotes()));
    }
}


