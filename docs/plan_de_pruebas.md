# Plan de pruebas

## Pruebas funcionales

Se ejecutaron el 12/11/2026 a las 17:33, corriendo el programa (`aplicacion.Main`) y escribiendo los datos por consola. Cada caso arranca desde cero con los datos de ejemplo cargados (sin archivo `datos_aromos.dat`), salvo el PF26, que prueba justamente que los datos se guarden entre ejecuciones.

| ID | Caso de uso | Qué se prueba | Dato de entrada | Resultado esperado | Resultado obtenido | Pasó |
| --- | --- | --- | --- | --- | --- | --- |
| PF01 | CU02 Hacer reclamo | El titular presenta un reclamo de seguridad | Titular DNI 30111222, reclamo SEGURIDAD «Luz de la entrada apagada» | Se registra con prioridad ALTA | Reclamo Nro 8 registrado (prioridad ALTA). | Sí |
| PF02 | CU01 y CU03 Crear solicitud / Validar presentante | Una residente sin permiso presenta una solicitud | Residente DNI 40333444 (lote 2, sin permiso), solicitud MANTENIMIENTO | Se rechaza | No se pudo completar la operacion: Solo el titular o un residente autorizado puede presentar reclamos | Sí |
| PF03 | CU09 Asignar solicitud | El administrador asigna una solicitud de mantenimiento a un empleado | Admin legajo 1, pedido 2, legajo 20 (empleado) | Queda ASIGNADO | Pedido asignado. | Sí |
| PF04 | CU09 Asignar solicitud (alternativo) | Se asigna un pedido de mantenimiento a un guardia | Admin legajo 1, pedido 2, legajo 10 (guardia) | Se rechaza porque el guardia no atiende ese tipo | No se pudo completar la operacion: Ese personal no atiende reclamos de tipo MANTENIMIENTO | Sí |
| PF05 | CU09 Asignar solicitud (alternativo) | Se asigna un pedido que ya está ASIGNADO | Admin legajo 1, pedido 3, legajo 11 | Se rechaza por el estado | No se pudo completar la operacion: Solo se pueden asignar reclamos abiertos | Sí |
| PF06 | CU12 Actualizar estado | El empleado avanza un pedido EN_CURSO | Empleado legajo 21, pedido 4, observación «Arreglado» | Pasa a RESUELTO | Nuevo estado: RESUELTO | Sí |
| PF07 | CU05 Reabrir reclamo | El titular reabre un pedido resuelto hace menos de 5 días | Titular DNI 30777888 (lote 4), pedido 5 | Vuelve a EN_CURSO | Reclamo reabierto, vuelve a EN_CURSO. | Sí |
| PF08 | CU07 Cancelar solicitud | El titular cancela un pedido ABIERTO | Titular DNI 30111222 (lote 1), pedido 1 | Queda cancelado | Pedido cancelado. | Sí |
| PF09 | CU07 Cancelar solicitud (alternativo) | Se cancela un pedido CERRADO | Titular DNI 30999000 (lote 5), pedido 6 | Se rechaza | No se pudo completar la operacion: Solo se puede cancelar un pedido abierto | Sí |
| PF10 | CU15 Emitir expensas (alternativo) | Se emiten otra vez las expensas del mes actual | Admin, período 11/2026, presupuesto 1.550.000 | Se rechaza | No se pudo completar la operacion: Ya existe una expensa en ese periodo | Sí |
| PF11 | CU15 Emitir expensas | Se emiten las expensas del mes siguiente | Admin, período 12/2026, presupuesto 1.600.000 | Se emiten con vencimiento el día 10 | Expensas emitidas para todos los lotes. Vencimiento: 10/12/2026 | Sí |
| PF12 | CU16 y CU17 Registrar pago / Calcular intereses | Pago parcial de un lote moroso | Admin, lote 3, $300.000, TRANSFERENCIA | Se aplica a la expensa más vieja, primero a intereses y después a capital | Se imputo en 2 expensa(s): $255.243,90 (intereses $33.292,68 + capital $221.951,22) y $44.756,10 (intereses $27.585,37 + capital $17.170,73) | Sí |
| PF13 | CU16 Registrar pago (alternativo) | Pago con importe 0 | Admin, lote 3, $0, EFECTIVO | Se rechaza | No se pudo completar la operacion: El importe tiene que ser mas que 0 | Sí |
| PF14 | CU18 Verificar tarjeta | Pago con tarjeta con un número de operación ya usado | Admin, lote 6, $100.000, TARJETA, operación OP-1001 | Se rechaza | No se pudo completar la operacion: Ya se uso el numero de operacion | Sí |
| PF15 | CU19 y CU42 Actualizar morosidad / Emitir reportes | Reporte de morosidad | Admin, Reportes, Morosidad por lote | Aparecen los lotes con más de 3 períodos impagos (3 y 6) | Lote 3: 5 períodos impagos. Lote 6: 5 períodos impagos | Sí |
| PF16 | CU42 Emitir reportes | Reporte de reclamos pendientes | Admin, Reportes, Reclamos pendientes | Primero los de prioridad ALTA, sin los CERRADO | Orden: 1 (ALTA), 2 y 4 (MEDIA), 3 y 5 (BAJA) | Sí |
| PF17 | CU27 Generar orden | Orden para una luminaria con 3 luminarias fallando en su calle | Admin, orden para LUM-01 | Prioridad MEDIA | Orden Nro 1 generada con prioridad MEDIA. El equipo pasa a EN_REPARACION. | Sí |
| PF18 | CU26 y CU27 Registrar falla / Generar orden | Falla una cuarta luminaria de la calle y se genera la orden | Admin, falla LUM-04 y orden para LUM-02 | Prioridad ALTA (más de 3 fallando) | Falla registrada. El equipo pasa a CON_FALLA. Orden Nro 1 generada con prioridad ALTA. | Sí |
| PF19 | CU26 Registrar falla (alternativo) | Falla de un equipo dado de baja | Admin, falla LUM-06 (fuera de servicio) | Se rechaza | No se pudo completar la operacion: El equipo esta dado de baja | Sí |
| PF20 | CU39 y CU40 Guardar ingreso / Verificar autorización | El guardia de portería registra una visita autorizada | Guardia legajo 10, visitante DNI 35000111 | Se registra el ingreso | Ingreso registrado a las 17:33. | Sí |
| PF21 | CU39 Guardar ingreso (alternativo) | La misma visita intenta entrar sin haber salido | Guardia legajo 10, DNI 35000111 dos veces | El segundo ingreso se rechaza | No se pudo completar la operacion: Ya hay un ingreso abierto | Sí |
| PF22 | CU40 Verificar autorización vigente | Visita con una autorización de ayer | Guardia legajo 10, visitante DNI 35000333 | Se rechaza | No se pudo completar la operacion: No tiene ninguna autorizacion vigente | Sí |
| PF23 | CU39 Guardar ingreso (alternativo) | Un guardia de ronda registra un ingreso | Guardia legajo 11 (ronda), DNI 35000111 | Se rechaza | No se pudo completar la operacion: Solo el portero puede dar ingresos | Sí |
| PF24 | CU41 Guardar salida | Salida de una visita que está adentro | Guardia legajo 10, ingreso y salida de DNI 36000222 | Se registra la salida | Egreso registrado a las 17:33. | Sí |
| PF25 | CU48 Ver notificaciones | El titular de un lote moroso ve su aviso | Titular DNI 30555666 (lote 3) | Aparece la notificación de morosidad | [NUEVA] 12/11/2026 17:33 \| MOROSIDAD \| El lote 3 tiene más de 3 expensas impagas | Sí |
| PF26 | Persistencia | Los datos se conservan entre ejecuciones | 1.ª ejecución: el titular DNI 30111222 crea un reclamo y sale. 2.ª ejecución: ve los pedidos de su lote | Al salir se guardan los datos y al volver a entrar se cargan | 1.ª: «Datos guardados en datos_aromos.dat». 2.ª: «Se cargaron los datos guardados.» y aparece el reclamo #8 | Sí |

Resultado: **26 de 26 casos pasaron.**

Las reglas que dependen del paso del tiempo (escalamiento a las 24 horas, cierre automático a los 5 días, aviso de cámara a las 48 horas) no se pueden probar por consola sin esperar ese tiempo. Por eso se prueban en las pruebas unitarias, con fechas fijas.

## Pruebas unitarias

Están en la carpeta `test`, en Java puro: cada clase tiene un `main` que crea los objetos, llama a los métodos y compara el resultado esperado con el obtenido, mostrando `OK` o `FALLA`.

| Clase | Qué prueba | Pruebas |
| --- | --- | --- |
| `PruebasPedidos` | Estados, asignación, límite de 5 en curso, reabrir, cierre automático, escalamiento, cancelar, orden con Comparable y Comparator | 27 |
| `PruebasExpensas` | Intereses, pagos (primero intereses y después capital), saldo a favor, morosidad, emisión según superficie, número de operación, asientos de ajuste | 27 |
| `PruebasEquipos` | Prioridad de cámaras y luminarias, falla, orden, cierre, equipo dado de baja, aviso de cámara a las 48 horas | 12 |
| `PruebasVisitas` | Vigencia de autorizaciones únicas y recurrentes, ingreso, salida, ingreso duplicado, guardia de ronda | 13 |

`PruebasTodas` corre las cuatro seguidas. Resultado: **79 de 79 pruebas pasaron.**

Una de las pruebas (`probarPagoTotalConDecimales`) encontró un error: al pagar el total de una expensa con un importe con muchos decimales, quedaba un saldo de fracciones de centavo y la expensa seguía como PARCIAL. Se corrigió en `Expensas.registrarPago()`.
