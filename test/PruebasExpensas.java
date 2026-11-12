import Enums.EstadoExpensa;
import Enums.MedioPago;
import Excepciones.PagoInvalidoException;
import expensas.AsientosDeAjuste;
import expensas.CuentasCorrientes;
import expensas.Expensas;
import expensas.GestorExpensas;
import expensas.Pagos;
import lotes.Lotes;
import personas.Titular;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class PruebasExpensas {

    public static void main(String[] args) {
        System.out.println("==== Pruebas de expensas ====");
        probarInteresesConAtraso();
        probarSinAtrasoNoHayInteres();
        probarPagoParcialPrimeroIntereses();
        probarPagoTotal();
        probarPagoTotalConDecimales();
        probarPagoEmpiezaPorLaMasVieja();
        probarImporteInvalido();
        probarMorosidad();
        probarEmisionSegunSuperficie();
        probarEmisionDuplicada();
        probarNumeroDeOperacionRepetido();
        probarAsientoDeAjuste();
        Verificar.resumen("Expensas");
    }

    private static Lotes lote(int numero, double superficie) {
        Titular t = new Titular("Ana", "Lopez", "3011122" + numero, "1155550001", "ana@mail.com");
        return new Lotes(numero, superficie, t);
    }
    private static void probarInteresesConAtraso() {
        Expensas e = new Expensas(YearMonth.of(2026, 9), 100000, LocalDate.of(2026, 9, 10));
        LocalDate fecha = LocalDate.of(2026, 11, 15);
        Verificar.igual("Meses de atraso del 10/09 al 15/11", 3L, e.mesesDeAtraso(fecha));
        Verificar.igual("Intereses: 100.000 x 3 % x 3 meses", 9000, e.calcularIntereses(fecha));
        Verificar.igual("Deuda: capital mas intereses", 109000, e.deuda(fecha));
    }

    private static void probarSinAtrasoNoHayInteres() {
        Expensas e = new Expensas(YearMonth.of(2026, 9), 100000, LocalDate.of(2026, 9, 10));
        Verificar.igual("Antes del vencimiento no hay intereses", 0, e.calcularIntereses(LocalDate.of(2026, 9, 5)));
    }

    private static void probarPagoParcialPrimeroIntereses() {
        Expensas e = new Expensas(YearMonth.of(2026, 9), 100000, LocalDate.of(2026, 9, 10));
        Pagos p = e.registrarPago(50000, MedioPago.EFECTIVO, null, LocalDate.of(2026, 11, 15));
        Verificar.igual("Pago parcial: 9.000 van a intereses", 9000, p.getMontoInteres());
        Verificar.igual("Pago parcial: 41.000 van a capital", 41000, p.getMontoCapital());
        Verificar.igual("Pago parcial: quedan 59.000 de capital", 59000, e.getSaldoCapital());
        Verificar.igual("Pago parcial: estado PARCIAL", EstadoExpensa.PARCIAL, e.getEstado());
    }

    private static void probarPagoTotal() {
        Expensas e = new Expensas(YearMonth.of(2026, 9), 384615.38, LocalDate.of(2026, 9, 10));
        LocalDate fecha = LocalDate.of(2026, 11, 15);
        e.registrarPago(e.deuda(fecha), MedioPago.TRANSFERENCIA, null, fecha);
        Verificar.igual("Pago total: el capital queda en 0", 0, e.getSaldoCapital());
        Verificar.igual("Pago total: estado PAGADA", EstadoExpensa.PAGADA, e.getEstado());
    }

    private static void probarPagoTotalConDecimales() {
        Lotes l = lote(1, 500);
        CuentasCorrientes c = l.getCuentaCorriente();
        double importe = 1450000.0 * 500 / 4100;
        c.emitirExpensa(YearMonth.of(2026, 8), importe, LocalDate.of(2026, 8, 10));
        try {
            c.registrarPago(500000, MedioPago.EFECTIVO, null, LocalDateTime.of(2026, 11, 12, 17, 0));
            Verificar.igual("Pago total con decimales: estado PAGADA", EstadoExpensa.PAGADA, c.getExpensas().get(0).getEstado());
            Verificar.igual("Pago total con decimales: no cuenta como impaga", 0, c.cantidadPeriodosImpagos(LocalDate.of(2026, 11, 12)));
        } catch (PagoInvalidoException ex) {
            Verificar.falla("Pago total con decimales", ex.getMessage());
        }
    }

    // Un pago se aplica de la expensa mas vieja a la mas nueva, y lo que sobra queda a favor
    private static void probarPagoEmpiezaPorLaMasVieja() {
        Lotes l = lote(1, 500);
        CuentasCorrientes c = l.getCuentaCorriente();
        c.emitirExpensa(YearMonth.of(2026, 9), 100000, LocalDate.of(2026, 9, 10));
        c.emitirExpensa(YearMonth.of(2026, 10), 100000, LocalDate.of(2026, 10, 10));
        LocalDateTime fecha = LocalDateTime.of(2026, 10, 5, 12, 0);
        try {
            List<Pagos> pagos = c.registrarPago(150000, MedioPago.EFECTIVO, null, fecha);
            Verificar.igual("Pago: se imputa en 2 expensas", 2, pagos.size());
            Verificar.igual("Pago: la de septiembre queda PAGADA", EstadoExpensa.PAGADA, c.getExpensas().get(0).getEstado());
            Verificar.igual("Pago: la de octubre queda PARCIAL", EstadoExpensa.PARCIAL, c.getExpensas().get(1).getEstado());

            c.registrarPago(100000, MedioPago.EFECTIVO, null, fecha);
            Verificar.igual("Pago de mas: sobran 47.000 a favor", 47000, c.getSaldoAFavor());
        } catch (PagoInvalidoException ex) {
            Verificar.falla("Pago empieza por la mas vieja", ex.getMessage());
        }
    }

    private static void probarImporteInvalido() {
        Lotes l = lote(1, 500);
        try {
            l.getCuentaCorriente().registrarPago(0, MedioPago.EFECTIVO, null, LocalDateTime.of(2026, 11, 15, 10, 0));
            Verificar.falla("Un pago de 0 no se acepta", "no lanzo excepcion");
        } catch (PagoInvalidoException ex) {
            Verificar.ok("Un pago de 0 no se acepta");
        }
    }

    // Mas de 3 periodos vencidos impagos: moroso y se avisa al titular
    private static void probarMorosidad() {
        Lotes conTres = lote(1, 500);
        Lotes conCuatro = lote(2, 500);
        for (int mes = 7; mes <= 10; mes++) {
            if (mes > 7) {
                conTres.getCuentaCorriente().emitirExpensa(YearMonth.of(2026, mes), 100000, LocalDate.of(2026, mes, 10));
            }
            conCuatro.getCuentaCorriente().emitirExpensa(YearMonth.of(2026, mes), 100000, LocalDate.of(2026, mes, 10));
        }
        LocalDateTime fecha = LocalDateTime.of(2026, 11, 5, 10, 0);
        conTres.getCuentaCorriente().actualizarMorosidad(fecha);
        conCuatro.getCuentaCorriente().actualizarMorosidad(fecha);
        Verificar.verdadero("Con 3 periodos impagos no es moroso", !conTres.getCuentaCorriente().isMoroso());
        Verificar.verdadero("Con 4 periodos impagos es moroso", conCuatro.getCuentaCorriente().isMoroso());
        Verificar.igual("Morosidad: el titular recibe la notificacion", 1, conCuatro.getTitular().verNotificaciones().size());
    }
    private static void probarEmisionSegunSuperficie() {
        List<Lotes> lotes = new ArrayList<>();
        lotes.add(lote(1, 500));
        lotes.add(lote(2, 800));
        lotes.add(lote(3, 650));
        new GestorExpensas().emitirExpensas(YearMonth.of(2026, 11), 1500000, lotes);
        Expensas e = lotes.get(0).getCuentaCorriente().getExpensas().get(0);
        Verificar.igual("Emision: 1.500.000 x 500 / 1.950", 384615.38, e.getImporte());
        Verificar.igual("Emision: vence el dia 10", LocalDate.of(2026, 11, 10), e.getVencimiento());
    }

    private static void probarEmisionDuplicada() {
        Lotes l = lote(1, 500);
        l.getCuentaCorriente().emitirExpensa(YearMonth.of(2026, 11), 100000, LocalDate.of(2026, 11, 10));
        try {
            l.getCuentaCorriente().emitirExpensa(YearMonth.of(2026, 11), 100000, LocalDate.of(2026, 11, 10));
            Verificar.falla("No se emite dos veces el mismo periodo", "no lanzo excepcion");
        } catch (IllegalArgumentException ex) {
            Verificar.ok("No se emite dos veces el mismo periodo");
        }
    }

    private static void probarNumeroDeOperacionRepetido() {
        List<Lotes> lotes = new ArrayList<>();
        lotes.add(lote(1, 500));
        lotes.add(lote(2, 800));
        lotes.get(0).getCuentaCorriente().emitirExpensa(YearMonth.of(2026, 11), 100000, LocalDate.of(2026, 11, 10));
        try {
            lotes.get(0).getCuentaCorriente().registrarPago(50000, MedioPago.TARJETA, "OP-1", LocalDateTime.of(2026, 11, 5, 10, 0));
        } catch (PagoInvalidoException ex) {
            Verificar.falla("Numero de operacion repetido", ex.getMessage());
        }
        GestorExpensas gestor = new GestorExpensas();
        Verificar.verdadero("Un numero de operacion ya usado no esta libre", !gestor.verificarOperacion("OP-1", lotes));
        Verificar.verdadero("Un numero de operacion nuevo esta libre", gestor.verificarOperacion("OP-2", lotes));
    }

    private static void probarAsientoDeAjuste() {
        Expensas e = new Expensas(YearMonth.of(2026, 11), 100000, LocalDate.of(2026, 11, 10));
        e.agregarAsiento(new AsientosDeAjuste(LocalDate.of(2026, 11, 2), -10000, "Descuento"));
        Verificar.igual("Asiento negativo: baja el capital a 90.000", 90000, e.getSaldoCapital());
        try {
            e.agregarAsiento(new AsientosDeAjuste(LocalDate.of(2026, 11, 3), -200000, "Descuento grande"));
            Verificar.falla("Un asiento no puede dejar el capital negativo", "no lanzo excepcion");
        } catch (IllegalArgumentException ex) {
            Verificar.ok("Un asiento no puede dejar el capital negativo");
        }
    }
}
