package configuracion;

import java.io.Serializable;

public class ConfiguracionBarrio implements Serializable {
    private static ConfiguracionBarrio instancia;

    private double porcentajeInteres;
    private int diaVencimiento;
    private int periodosParaMorosidad;
    private int limitePedidosEnCurso;
    private int horasParaEscalar;
    private int diasParaReabrir;
    private int horasCamaraSinFuncionar;
    private int luminariasParaPrioridadAlta;
    private int diasMaximosAutorizacion;

    private ConfiguracionBarrio() {
        restablecerValores();
    }

    public static ConfiguracionBarrio getInstancia() {
        if (instancia == null) {
            instancia = new ConfiguracionBarrio();
        }
        return instancia;
    }

    public static void cargarGuardada(ConfiguracionBarrio guardada) {
        if (guardada == null) {
            throw new IllegalArgumentException("La configuracion guardada no puede ser nula");
        }
        instancia = guardada;
    }

    public void restablecerValores() {
        porcentajeInteres = 3;
        diaVencimiento = 10;
        periodosParaMorosidad = 3;
        limitePedidosEnCurso = 5;
        horasParaEscalar = 24;
        diasParaReabrir = 5;
        horasCamaraSinFuncionar = 48;
        luminariasParaPrioridadAlta = 3;
        diasMaximosAutorizacion = 30;
    }

    private void validarMayorACero(int valor, String regla) {
        if (valor <= 0) {
            throw new IllegalArgumentException(regla + " tiene que ser mayor a 0");
        }
    }

    public double getPorcentajeInteres() {
        return porcentajeInteres;
    }

    public void setPorcentajeInteres(double porcentajeInteres) {
        if (porcentajeInteres < 0 || porcentajeInteres > 100) {
            throw new IllegalArgumentException("El porcentaje de interes tiene que estar entre 0 y 100");
        }
        this.porcentajeInteres = porcentajeInteres;
    }

    public int getDiaVencimiento() {
        return diaVencimiento;
    }

    public void setDiaVencimiento(int diaVencimiento) {
        if (diaVencimiento < 1 || diaVencimiento > 28) {
            throw new IllegalArgumentException("El dia de vencimiento tiene que estar entre 1 y 28");
        }
        this.diaVencimiento = diaVencimiento;
    }

    public int getPeriodosParaMorosidad() {
        return periodosParaMorosidad;
    }

    public void setPeriodosParaMorosidad(int periodosParaMorosidad) {
        validarMayorACero(periodosParaMorosidad, "La cantidad de expensas para ser moroso");
        this.periodosParaMorosidad = periodosParaMorosidad;
    }

    public int getLimitePedidosEnCurso() {
        return limitePedidosEnCurso;
    }

    public void setLimitePedidosEnCurso(int limitePedidosEnCurso) {
        validarMayorACero(limitePedidosEnCurso, "El limite de pedidos en curso");
        this.limitePedidosEnCurso = limitePedidosEnCurso;
    }

    public int getHorasParaEscalar() {
        return horasParaEscalar;
    }

    public void setHorasParaEscalar(int horasParaEscalar) {
        validarMayorACero(horasParaEscalar, "Las horas para escalar un reclamo");
        this.horasParaEscalar = horasParaEscalar;
    }

    public int getDiasParaReabrir() {
        return diasParaReabrir;
    }

    public void setDiasParaReabrir(int diasParaReabrir) {
        validarMayorACero(diasParaReabrir, "Los dias para reabrir un reclamo");
        this.diasParaReabrir = diasParaReabrir;
    }

    public int getHorasCamaraSinFuncionar() {
        return horasCamaraSinFuncionar;
    }

    public void setHorasCamaraSinFuncionar(int horasCamaraSinFuncionar) {
        validarMayorACero(horasCamaraSinFuncionar, "Las horas de una camara sin funcionar");
        this.horasCamaraSinFuncionar = horasCamaraSinFuncionar;
    }

    public int getLuminariasParaPrioridadAlta() {
        return luminariasParaPrioridadAlta;
    }

    public void setLuminariasParaPrioridadAlta(int luminariasParaPrioridadAlta) {
        validarMayorACero(luminariasParaPrioridadAlta, "La cantidad de luminarias para prioridad alta");
        this.luminariasParaPrioridadAlta = luminariasParaPrioridadAlta;
    }

    public int getDiasMaximosAutorizacion() {
        return diasMaximosAutorizacion;
    }

    public void setDiasMaximosAutorizacion(int diasMaximosAutorizacion) {
        validarMayorACero(diasMaximosAutorizacion, "Los dias maximos de una autorizacion recurrente");
        this.diasMaximosAutorizacion = diasMaximosAutorizacion;
    }
}
