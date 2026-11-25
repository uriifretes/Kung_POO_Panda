package vistas;

import configuracion.ConfiguracionBarrio;

public class VistaConfiguracion {
    private ConfiguracionBarrio configuracion;

    public VistaConfiguracion(ConfiguracionBarrio configuracion) {
        this.configuracion = configuracion;
    }

    public void mostrar() {
        Consola.titulo("Reglas del barrio");
        System.out.println("1. Interes mensual por mora: " + configuracion.getPorcentajeInteres() + " %");
        System.out.println("2. Dia de vencimiento de las expensas: " + configuracion.getDiaVencimiento());
        System.out.println("3. Moroso con mas de " + configuracion.getPeriodosParaMorosidad() + " expensas impagas");
        System.out.println("4. Maximo de pedidos en curso por responsable: " + configuracion.getLimitePedidosEnCurso());
        System.out.println("5. Horas para escalar un reclamo urgente: " + configuracion.getHorasParaEscalar());
        System.out.println("6. Dias para reabrir un reclamo resuelto: " + configuracion.getDiasParaReabrir());
        System.out.println("7. Horas de una camara sin funcionar antes de avisar: " + configuracion.getHorasCamaraSinFuncionar());
        System.out.println("8. Prioridad alta con mas de " + configuracion.getLuminariasParaPrioridadAlta() + " luminarias fallando en el sector");
        System.out.println("9. Dias maximos de una autorizacion recurrente: " + configuracion.getDiasMaximosAutorizacion());
    }

    public void modificar() {
        mostrar();
        int opcion = Consola.leerOpcion("Regla a modificar (0 para volver)", 0, 9);
        if (opcion == 0) {
            return;
        }
        try {
            switch (opcion) {
                case 1:
                    configuracion.setPorcentajeInteres(Consola.leerDouble("Nuevo interes mensual (%)"));
                    break;
                case 2:
                    configuracion.setDiaVencimiento(Consola.leerEntero("Nuevo dia de vencimiento"));
                    break;
                case 3:
                    configuracion.setPeriodosParaMorosidad(Consola.leerEntero("Nueva cantidad de expensas impagas"));
                    break;
                case 4:
                    configuracion.setLimitePedidosEnCurso(Consola.leerEntero("Nuevo maximo de pedidos en curso"));
                    break;
                case 5:
                    configuracion.setHorasParaEscalar(Consola.leerEntero("Nuevas horas para escalar"));
                    break;
                case 6:
                    configuracion.setDiasParaReabrir(Consola.leerEntero("Nuevos dias para reabrir"));
                    break;
                case 7:
                    configuracion.setHorasCamaraSinFuncionar(Consola.leerEntero("Nuevas horas de camara sin funcionar"));
                    break;
                case 8:
                    configuracion.setLuminariasParaPrioridadAlta(Consola.leerEntero("Nueva cantidad de luminarias"));
                    break;
                case 9:
                    configuracion.setDiasMaximosAutorizacion(Consola.leerEntero("Nuevos dias maximos"));
                    break;
                default:
                    break;
            }
            System.out.println("Regla actualizada.");
        } catch (IllegalArgumentException e) {
            Consola.error(e);
        }
    }

    public void restablecer() {
        if (Consola.leerSiNo("Volver todas las reglas a los valores por defecto?")) {
            configuracion.restablecerValores();
            System.out.println("Se restablecieron los valores por defecto.");
        }
    }
}
