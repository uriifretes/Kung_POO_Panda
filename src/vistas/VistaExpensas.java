package vistas;

import controladores.ControladorExpensas;
import expensas.Expensas;
import expensas.Pagos;

import Enums.MedioPago;
import Excepciones.PagoInvalidoException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

public class VistaExpensas {
    private ControladorExpensas controlador;

    public VistaExpensas(ControladorExpensas controlador) {
        this.controlador = controlador;
    }

    public void emitirExpensas() {
        Consola.titulo("Emitir expensas del mes");
        try {
            YearMonth periodo = Consola.leerPeriodo("Periodo");
            double presupuesto = Consola.leerDouble("Presupuesto total del mes");
            controlador.emitirExpensas(periodo, presupuesto);
            System.out.println("Expensas emitidas para todos los lotes. Vencimiento: " + periodo.atDay(10).format(Consola.FORMATO_FECHA));
        } catch (IllegalArgumentException e) {
            Consola.error(e);
        }
    }

    public void registrarPago() {
        Consola.titulo("Registrar pago");
        try {
            int nLote = Consola.leerEntero("Numero de lote");
            double importe = Consola.leerDouble("Importe");
            MedioPago medio = Consola.leerEnum("Medio de pago", MedioPago.class);
            String numOperacion = null;
            if (medio == MedioPago.TARJETA) {
                numOperacion = Consola.leerTexto("Numero de operacion del posnet");
            }
            List<Pagos> pagos = controlador.registrarPago(nLote, importe, medio, numOperacion, LocalDateTime.now());
            System.out.println("Pago registrado. Se imputo en " + pagos.size() + " expensa(s):");
            for (int i = 0; i < pagos.size(); i++) {
                Pagos p = pagos.get(i);
                System.out.println("- " + Consola.dinero(p.getImporte()) + " (intereses " + Consola.dinero(p.getMontoInteres())
                        + " + capital " + Consola.dinero(p.getMontoCapital()) + ")");
            }
            if (pagos.isEmpty()) {
                System.out.println("- No habia deuda: el importe queda como saldo a favor del lote.");
            }
        } catch (PagoInvalidoException | IllegalArgumentException e) {
            Consola.error(e);
        }
    }

    public void registrarAsiento() {
        Consola.titulo("Registrar asiento de ajuste");
        try {
            int nLote = Consola.leerEntero("Numero de lote");
            YearMonth periodo = Consola.leerPeriodo("Periodo de la expensa");
            double monto = Consola.leerDouble("Monto (negativo para disminuir la deuda)");
            String motivo = Consola.leerTexto("Motivo");
            controlador.registrarAsientos(nLote, periodo, monto, motivo, LocalDate.now());
            System.out.println("Asiento registrado.");
        } catch (IllegalArgumentException e) {
            Consola.error(e);
        }
    }

    public void consultarDeuda() {
        int nLote = Consola.leerEntero("Numero de lote");
        consultarDeuda(nLote);
    }

    public void consultarDeuda(int nLote) {
        try {
            double deuda = controlador.consultarDeuda(nLote, LocalDate.now());
            System.out.println("Deuda total del lote " + nLote + " a hoy: " + Consola.dinero(deuda)
                    + " (negativo = saldo a favor)");
        } catch (IllegalArgumentException e) {
            Consola.error(e);
        }
    }

    public void verExpensas() {
        int nLote = Consola.leerEntero("Numero de lote");
        verExpensas(nLote);
    }

    public void verExpensas(int nLote) {
        Consola.titulo("Expensas del lote " + nLote);
        try {
            List<Expensas> lista = controlador.verExpensas(nLote);
            if (lista.isEmpty()) {
                System.out.println("El lote no tiene expensas emitidas.");
            }
            LocalDate hoy = LocalDate.now();
            for (int i = 0; i < lista.size(); i++) {
                Expensas e = lista.get(i);
                System.out.println(e.getPeriodo().format(Consola.FORMATO_PERIODO)
                        + " | Importe " + Consola.dinero(e.getImporte())
                        + " | Vence " + e.getVencimiento().format(Consola.FORMATO_FECHA)
                        + " | " + e.getEstado()
                        + " | Intereses " + Consola.dinero(e.calcularIntereses(hoy))
                        + " | Deuda " + Consola.dinero(e.deuda(hoy))
                        + " | Pagos: " + e.getPagos().size() + " | Ajustes: " + e.getAsientos().size());
            }
        } catch (IllegalArgumentException e) {
            Consola.error(e);
        }
    }

    public void reporteMorosidad() {
        System.out.println();
        System.out.println(controlador.reporteMorosidad(LocalDate.now()));
    }
}