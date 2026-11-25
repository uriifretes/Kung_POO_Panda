import Enums.TipoReclamo;
import Excepciones.LimiteReclamosException;
import Excepciones.TransicionInvalidaException;
import configuracion.ConfiguracionBarrio;
import controladores.ControladorGeneral;
import expensas.Expensas;
import lotes.Lotes;
import personal.EmpleadosDeMantenimiento;
import personas.Titular;
import reclamos.PedidosVecinos;
import reclamos.Reclamos;
import Enums.Especialidad;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;

public class PruebasConfiguracion {

    public static void main(String[] args) {
        System.out.println("==== Pruebas de configuracion ====");
        probarInstanciaUnica();
        probarValoresPorDefecto();
        probarCambiarInteres();
        probarCambiarLimiteDePedidos();
        probarValoresInvalidos();
        probarRestablecer();
        ConfiguracionBarrio.getInstancia().restablecerValores();
        Verificar.resumen("Configuracion");
    }

    private static void probarInstanciaUnica() {
        ConfiguracionBarrio a = ConfiguracionBarrio.getInstancia();
        ConfiguracionBarrio b = ConfiguracionBarrio.getInstancia();
        Verificar.verdadero("getInstancia devuelve siempre el mismo objeto", a == b);
        ControladorGeneral general = new ControladorGeneral();
        Verificar.verdadero("El controlador general usa la misma instancia", general.getConfiguracion() == a);
    }

    private static void probarValoresPorDefecto() {
        ConfiguracionBarrio config = ConfiguracionBarrio.getInstancia();
        config.restablecerValores();
        Verificar.igual("Interes por defecto: 3 %", 3, config.getPorcentajeInteres());
        Verificar.igual("Limite por defecto: 5 en curso", 5, config.getLimitePedidosEnCurso());
        Verificar.igual("Vencimiento por defecto: dia 10", 10, config.getDiaVencimiento());
    }

    private static void probarCambiarInteres() {
        ConfiguracionBarrio config = ConfiguracionBarrio.getInstancia();
        Expensas e = new Expensas(YearMonth.of(2026, 9), 100000, LocalDate.of(2026, 9, 10));
        LocalDate fecha = LocalDate.of(2026, 12, 10);
        config.setPorcentajeInteres(4);
        Verificar.igual("Con 4 %: 100.000 x 4 % x 3 meses", 12000, e.calcularIntereses(fecha));
        config.restablecerValores();
        Verificar.igual("Con 3 % otra vez: 9.000", 9000, e.calcularIntereses(fecha));
    }

    private static void probarCambiarLimiteDePedidos() {
        ConfiguracionBarrio config = ConfiguracionBarrio.getInstancia();
        config.setLimitePedidosEnCurso(1);
        Titular t = new Titular("Ana", "Lopez", "30111222", "1155550001", "ana@mail.com");
        Lotes lote = new Lotes(1, 500, t);
        EmpleadosDeMantenimiento e = new EmpleadosDeMantenimiento(20, "Luis Torres", LocalDate.of(2021, 2, 1), Especialidad.ELECTRICIDAD);
        LocalDateTime tiempo = LocalDateTime.of(2026, 11, 1, 10, 0);
        try {
            PedidosVecinos p1 = new Reclamos(1, tiempo, TipoReclamo.MANTENIMIENTO, "Pedido 1", lote, t);
            p1.asignar(e, tiempo);
            p1.avanzar(tiempo, "Trabajando");
            PedidosVecinos p2 = new Reclamos(2, tiempo, TipoReclamo.MANTENIMIENTO, "Pedido 2", lote, t);
            p2.asignar(e, tiempo);
            Verificar.falla("Con limite 1 no se asigna un segundo pedido", "no lanzo excepcion");
        } catch (LimiteReclamosException ex) {
            Verificar.ok("Con limite 1 no se asigna un segundo pedido");
        } catch (TransicionInvalidaException ex) {
            Verificar.falla("Con limite 1 no se asigna un segundo pedido", ex.getMessage());
        }
        config.restablecerValores();
    }

    private static void probarValoresInvalidos() {
        ConfiguracionBarrio config = ConfiguracionBarrio.getInstancia();
        try {
            config.setPorcentajeInteres(-1);
            Verificar.falla("No acepta un interes negativo", "no lanzo excepcion");
        } catch (IllegalArgumentException e) {
            Verificar.ok("No acepta un interes negativo");
        }
        try {
            config.setLimitePedidosEnCurso(0);
            Verificar.falla("No acepta un limite de 0", "no lanzo excepcion");
        } catch (IllegalArgumentException e) {
            Verificar.ok("No acepta un limite de 0");
        }
        try {
            config.setDiaVencimiento(31);
            Verificar.falla("No acepta el dia 31 como vencimiento", "no lanzo excepcion");
        } catch (IllegalArgumentException e) {
            Verificar.ok("No acepta el dia 31 como vencimiento");
        }
        Verificar.igual("Si falla, el valor no cambia", 3, config.getPorcentajeInteres());
    }

    private static void probarRestablecer() {
        ConfiguracionBarrio config = ConfiguracionBarrio.getInstancia();
        config.setHorasParaEscalar(48);
        config.restablecerValores();
        Verificar.igual("Restablecer vuelve a 24 horas", 24, config.getHorasParaEscalar());
    }
}
