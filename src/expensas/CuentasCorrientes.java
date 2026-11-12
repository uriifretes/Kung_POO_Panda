package expensas;

import lotes.Lotes;
import notificaciones.Notificaciones;

import Enums.MedioPago;
import Excepciones.PagoInvalidoException;
import Enums.TipoNotificacion;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class CuentasCorrientes implements Serializable {
    private boolean moroso;
    private double saldoAFavor;
    private List<Expensas> expensas;
    private Lotes lote;

    public CuentasCorrientes(Lotes lote) {
        this.moroso = false;
        this.saldoAFavor = 0;
        this.expensas = new ArrayList<>();
        this.lote = lote;
    }

    public boolean isMoroso() {
        return moroso;
    }

    public double getSaldoAFavor() {
        return saldoAFavor;
    }

    public List<Expensas> getExpensas() {
        List<Expensas> devuelvo = new ArrayList<>();
        for (int i = 0; i < expensas.size(); i++) {
            devuelvo.add(expensas.get(i));
        }
        return devuelvo;
    }

    public Expensas emitirExpensa(YearMonth periodo, double importe, LocalDate vencimiento){
        for (int i = 0; i < expensas.size(); i++) {
            if (expensas.get(i).getPeriodo().equals(periodo)){
                throw new IllegalArgumentException("Ya existe una expensa en ese periodo");
            }
        }
        Expensas e = new Expensas(periodo,importe,vencimiento);
        expensas.add(e);
        if (saldoAFavor > 0) {
            double aplicar = saldoAFavor;
            if (aplicar > importe) {
                aplicar = importe;
            }
            e.agregarAsiento(new AsientosDeAjuste(vencimiento, -aplicar, "Saldo a favor aplicado"));
            saldoAFavor = saldoAFavor - aplicar;
        }
        return e;
    }

    public List<Expensas> expensasImpagas(LocalDate fecha){
        List<Expensas> impagas = new ArrayList<>();
        for (int i = 0; i < expensas.size(); i++) {
            if (expensas.get(i).deuda(fecha)>0){
                impagas.add(expensas.get(i));
            }
        }
        return impagas;
    }

    public List<Pagos> registrarPago(double importe, MedioPago medio, String numeroOperacion, LocalDateTime fecha) throws PagoInvalidoException {
        if (importe<=0){
            throw new PagoInvalidoException("El importe tiene que ser mas que 0");
        }

        List<Pagos> pagosRealizados = new ArrayList<>();
        double resto = importe;
        List<Expensas> impagas = expensasImpagas(fecha.toLocalDate());

        for (int i = 0; i < impagas.size() && resto > 0; i++) {
            Expensas e = impagas.get(i);

            double aPagar = resto;
            if (aPagar > e.deuda(fecha.toLocalDate())) {
                aPagar = e.deuda(fecha.toLocalDate());
            }

            Pagos p = e.registrarPago(aPagar, medio, numeroOperacion, fecha.toLocalDate());
            pagosRealizados.add(p);
            resto = resto - aPagar;
        }

        if (resto > 0) {
            saldoAFavor = saldoAFavor + resto;
        }
        actualizarMorosidad(fecha);

        return pagosRealizados;
    }
    public int cantidadPeriodosImpagos(LocalDate fecha){
        int cant = 0;
        for (int i = 0; i < expensas.size(); i++) {
            if (expensas.get(i).estaImpaga(fecha)){
                cant= cant +1;
            }
        }

        return cant;
    }

    public void actualizarMorosidad(LocalDateTime fecha){
        if (cantidadPeriodosImpagos(fecha.toLocalDate())>3 && !moroso){
            moroso = true;
            Notificaciones n = new Notificaciones(fecha, TipoNotificacion.MOROSIDAD,
                    "El lote " + lote.getNumero() + " tiene más de 3 expensas impagas", lote.getTitular());
            lote.getTitular().agregarNotificacion(n);
        } else if (cantidadPeriodosImpagos(fecha.toLocalDate())>3){
            moroso = true;
        }
        else {
            moroso = false;
        }
    }

    public double deudaTotal(LocalDate fecha){
        double deudatotal = 0;
        for (int i = 0; i < expensas.size(); i++) {
            deudatotal = deudatotal + expensas.get(i).deuda(fecha);
        }

        deudatotal = deudatotal-saldoAFavor;

        return deudatotal;
    }

}