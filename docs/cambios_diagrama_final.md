# Cambios en el diagrama de clases respecto del inicial

- Se crea CuentaCorriente y se le pasa a ella la parte de expensas, pagos, deuda y morosidad de Lote.
- Se crea GestorExpensas, que emite las expensas y verifica los números de operación de los pagos con tarjeta.
- Administrador pierde emitirExpensas() y generarOrden(); las órdenes se generan solo desde Equipo.
- Se crea la interfaz Notificable, que implementan Titular y Administrador. Persona pierde las notificaciones.
- Se crea la clase Titular, que hereda de Persona. Lote pasa a tener titular: Titular.
- Notificación suma el atributo destinatario: Notificable.
- Se elimina el atributo esSolicitud: se crea la clase abstracta PedidoVecino con las subclases Reclamo y Solicitud.
- GeneradorReportes deja de tener un método por reporte: se crea la interfaz Reporte con tres implementaciones (ReporteReclamosPendientes, ReporteMorosidad y ReporteEquiposFueraDeServicio).
- Expensa conoce sus asientos de ajuste (relación en los dos sentidos y método agregarAsiento()) para poder descontarlos al calcular intereses.
- Visitante suma estaAdentro(), para controlar que una persona no entre dos veces sin haber salido.
- Autorización suma tieneIngresoAbierto() (abstracto), que sobrescriben Única y Recurrente.
