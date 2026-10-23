# Casos de uso

## Listado de casos de uso

| **N°** | **Caso de uso** | **Actor/es** | **Relación** |
|---|---|---|---|
| CU01 | Crear solicitud | Titular, Residente | include CU03 |
| CU02 | Hacer reclamo | Titular, Residente | include CU03 |
| CU03 | Validar presentante | (sistema) | — |
| CU04 | Ver estado de solicitud | Titular, Residente | — |
| CU05 | Reabrir reclamo | Titular, Residente | extend de CU04 |
| CU06 | Comentar solicitud | Titular, Residente, Guardia, Empleado, Administrador | — |
| CU07 | Cancelar solicitud | Titular, Residente | — |
| CU08 | Gestionar solicitudes | Administrador | — |
| CU09 | Asignar solicitud | Administrador | include CU10 |
| CU10 | Verificar límite de solicitudes en curso | (sistema) | — |
| CU11 | Ver solicitudes asignadas | Guardia, Empleado | — |
| CU12 | Actualizar estado de solicitud | Guardia, Empleado, Administrador | include CU10 |
| CU13 | Cerrar solicitud | Administrador | — |
| CU14 | Cambiar presupuesto del mes | Administrador | — |
| CU15 | Emitir expensas | Administrador | — |
| CU16 | Registrar pago | Titular, Administrador | include CU17, include CU19 |
| CU17 | Calcular intereses | (sistema) | — |
| CU18 | Verificar tarjeta | (sistema) | extend de CU16 |
| CU19 | Actualizar morosidad | (sistema) | — |
| CU20 | Consultar estado de cuenta | Titular | include CU17 |
| CU21 | Apelar deuda | Titular | — |
| CU22 | Disminuir intereses | Administrador | include CU23 |
| CU23 | Registrar asiento de ajuste | Administrador | — |
| CU24 | Controlar equipos | Empleado | CU26 extiende a este |
| CU25 | Ver cámaras de seguridad | Guardia | CU26 extiende a este |
| CU26 | Registrar falla | Titular, Residente, Guardia, Empleado | extend de CU24 y de CU25 |
| CU27 | Generar orden de mantenimiento | Administrador | — |
| CU28 | Cerrar orden de mantenimiento | Empleado, Administrador | — |
| CU29 | Consultar estado de equipos | Administrador, Guardia, Empleado | — |
| CU30 | Marcar equipo fuera de servicio | Administrador | — |
| CU31 | Modificar sector | Administrador | — |
| CU32 | Dar autorización | Titular, Residente | — |
| CU33 | Dar autorización recurrente | Titular, Residente | extend de CU32 |
| CU34 | Ver autorización | Visitante, Proveedor | — |
| CU35 | Ver autorizaciones | Administrador | — |
| CU36 | Cancelar autorización | Administrador | — |
| CU37 | Gestionar permiso de autorizaciones | Administrador | — |
| CU38 | Cancelar autorización a vecino | Administrador | extend de CU37 |
| CU39 | Guardar ingreso | Guardia | include CU40 |
| CU40 | Verificar autorización vigente | (sistema) | — |
| CU41 | Guardar salida | Guardia | — |
| CU42 | Emitir reportes | Administrador | include CU43 |
| CU43 | Elegir tipo de reporte | Administrador | — |
| CU44 | Registrar lote | Administrador | — |
| CU45 | Agregar residente | Titular | — |
| CU46 | Registrar personal | Administrador | — |
| CU47 | Procesar vencimientos | Administrador | include CU19 |
| CU48 | Ver notificaciones | Administrador, Titular | — |

En el diagrama, los casos que dicen "(sistema)" no se conectan con ningún actor: solo se unen con la flecha de include o extend al caso que los usa. Guardia = Guardia de seguridad; Empleado = Empleado de mantenimiento.

## Solicitudes y reclamos

### CU01 - Crear solicitud

| **NOMBRE:** | **Crear solicitud** |
|---|---|
| **NÚMERO:** | CU01 |
| **ACTOR/ES** | Titular, Residente |
| **PRECONDICIONES** | El vecino está identificado en el sistema con su documento. |
| **FLUJO PRINCIPAL** | 1. El vecino elige "Crear solicitud".<br>2. Ingresa el número de lote.<br>3. El sistema valida que sea el titular o un residente autorizado del lote (include CU03).<br>4. El vecino elige el tipo (seguridad, mantenimiento, convivencia o administrativo) y escribe qué pide.<br>5. El sistema crea la solicitud con número, fecha, prioridad según el tipo y estado ABIERTO, y registra el primer cambio de estado.<br>6. El sistema muestra el número de solicitud. |
| **FLUJO ALTERNATIVO** | 3a. **Error:** no es titular ni residente autorizado del lote. El sistema informa "No tiene permiso para presentar solicitudes en este lote" y termina.<br>4a. La descripción está vacía. El sistema la vuelve a pedir. |

### CU02 - Hacer reclamo

| **NOMBRE:** | **Hacer reclamo** |
|---|---|
| **NÚMERO:** | CU02 |
| **ACTOR/ES** | Titular, Residente |
| **PRECONDICIONES** | El vecino está identificado en el sistema con su documento. |
| **FLUJO PRINCIPAL** | 1. El vecino elige "Hacer reclamo".<br>2. Ingresa el número de lote.<br>3. El sistema valida que sea el titular o un residente autorizado (include CU03).<br>4. El vecino elige el tipo y describe el problema.<br>5. El sistema crea el reclamo con número, fecha y hora, prioridad según el tipo y estado ABIERTO, y registra el primer cambio de estado.<br>6. El sistema muestra el número de reclamo. |
| **FLUJO ALTERNATIVO** | 3a. **Error:** no es titular ni residente autorizado. Se informa y termina.<br>4a. El tipo es SEGURIDAD: el sistema avisa que el reclamo queda con prioridad alta y que, si no se asigna en 24 horas, se escala al administrador. Sigue en el paso 5. |

### CU03 - Validar presentante

| **NOMBRE:** | **Validar presentante** |
|---|---|
| **NÚMERO:** | CU03 |
| **ACTOR/ES** | Sistema (incluido en CU01 y CU02) |
| **PRECONDICIONES** | Se conoce la persona y el lote. |
| **FLUJO PRINCIPAL** | 1. El sistema verifica si la persona es el titular del lote.<br>2. Si no lo es, verifica si es un residente del lote marcado como autorizado.<br>3. Devuelve si puede o no presentar. |
| **FLUJO ALTERNATIVO** | 1a. El lote no existe. El sistema informa "Lote inexistente" y termina. |

### CU04 - Ver estado de solicitud

| **NOMBRE:** | **Ver estado de solicitud** |
|---|---|
| **NÚMERO:** | CU04 |
| **ACTOR/ES** | Titular, Residente |
| **PRECONDICIONES** | El vecino presentó al menos una solicitud o reclamo. |
| **FLUJO PRINCIPAL** | 1. El vecino elige "Ver mis solicitudes".<br>2. El sistema muestra solo las solicitudes y reclamos que presentó esa persona, con número, tipo y estado.<br>3. El vecino elige una.<br>4. El sistema muestra el responsable, el historial de estados (fecha, estado y observación) y los comentarios. |
| **FLUJO ALTERNATIVO** | 2a. No presentó ninguna. El sistema informa "No tiene solicitudes" y termina.<br>4a. Es un reclamo RESUELTO hace 5 días o menos: el sistema ofrece reabrirlo (extend CU05). |

### CU05 - Reabrir reclamo

| **NOMBRE:** | **Reabrir reclamo** |
|---|---|
| **NÚMERO:** | CU05 |
| **ACTOR/ES** | Titular, Residente |
| **PRECONDICIONES** | El reclamo está RESUELTO hace 5 días o menos (extiende CU04). |
| **FLUJO PRINCIPAL** | 1. El vecino elige "Reabrir" e ingresa el motivo.<br>2. El sistema pasa el reclamo a EN CURSO con el mismo responsable.<br>3. El sistema registra el cambio de estado con el motivo. |
| **FLUJO ALTERNATIVO** | 1a. **Error:** pasaron más de 5 días desde que se resolvió. Se informa "Ya no se puede reabrir".<br>2a. **Error:** el responsable ya tiene 5 reclamos en curso. Se informa y el reclamo sigue RESUELTO. |

### CU06 - Comentar solicitud

| **NOMBRE:** | **Comentar solicitud** |
|---|---|
| **NÚMERO:** | CU06 |
| **ACTOR/ES** | Titular, Residente, Guardia de seguridad, Empleado de mantenimiento, Administrador |
| **PRECONDICIONES** | La solicitud o reclamo existe y no está CERRADO. |
| **FLUJO PRINCIPAL** | 1. El usuario elige la solicitud.<br>2. Escribe el comentario.<br>3. El sistema lo guarda con fecha y autor. |
| **FLUJO ALTERNATIVO** | 1a. **Error:** la solicitud está CERRADA. Se informa "No se puede comentar una solicitud cerrada".<br>2a. El comentario está vacío. No se guarda y se pide de nuevo. |

### CU07 - Cancelar solicitud

| **NOMBRE:** | **Cancelar solicitud** |
|---|---|
| **NÚMERO:** | CU07 |
| **ACTOR/ES** | Titular, Residente |
| **PRECONDICIONES** | La solicitud o reclamo fue presentado por ese vecino. |
| **FLUJO PRINCIPAL** | 1. El vecino elige la solicitud y "Cancelar".<br>2. El sistema verifica que esté ABIERTA (todavía sin responsable).<br>3. El sistema la pasa a CERRADA y registra el cambio con la observación "Cancelada por el vecino". |
| **FLUJO ALTERNATIVO** | 2a. **Error:** ya fue asignada. El sistema informa "No se puede cancelar: ya tiene un responsable" y termina. |

### CU08 - Gestionar solicitudes

| **NOMBRE:** | **Gestionar solicitudes** |
|---|---|
| **NÚMERO:** | CU08 |
| **ACTOR/ES** | Administrador |
| **PRECONDICIONES** | Hay solicitudes o reclamos cargados. |
| **FLUJO PRINCIPAL** | 1. El administrador elige "Gestionar solicitudes".<br>2. El sistema muestra todas las solicitudes y reclamos no cerrados, ordenados por prioridad y fecha.<br>3. El administrador elige una y ve su detalle e historial.<br>4. Desde ahí puede asignarla (CU09), actualizar su estado (CU12) o cerrarla (CU13). |
| **FLUJO ALTERNATIVO** | 2a. El administrador filtra por estado o por tipo. El sistema muestra solo esas.<br>2b. No hay solicitudes pendientes. Se informa y termina. |

### CU09 - Asignar solicitud

| **NOMBRE:** | **Asignar solicitud** |
|---|---|
| **NÚMERO:** | CU09 |
| **ACTOR/ES** | Administrador |
| **PRECONDICIONES** | La solicitud o reclamo está ABIERTO. |
| **FLUJO PRINCIPAL** | 1. El administrador elige la solicitud.<br>2. El sistema muestra el personal que puede atender ese tipo (seguridad y convivencia: guardias; mantenimiento: empleados de mantenimiento; administrativo: el administrador).<br>3. El administrador elige al responsable.<br>4. El sistema verifica que tenga menos de 5 en curso (include CU10).<br>5. El sistema asigna la solicitud, la pasa a ASIGNADA y registra el cambio.<br>6. El sistema confirma la asignación. |
| **FLUJO ALTERNATIVO** | 1a. **Error:** la solicitud no está ABIERTA (ya tiene responsable). Se informa y termina.<br>3a. **Error:** el empleado elegido no atiende ese tipo. Se informa y vuelve al paso 2.<br>4a. **Error:** el empleado ya tiene 5 en curso. Se informa "El empleado alcanzó el límite" y vuelve al paso 2. |

### CU10 - Verificar límite de solicitudes en curso

| **NOMBRE:** | **Verificar límite de solicitudes en curso** |
|---|---|
| **NÚMERO:** | CU10 |
| **ACTOR/ES** | Sistema (incluido en CU09 y CU12) |
| **PRECONDICIONES** | Se conoce el empleado. |
| **FLUJO PRINCIPAL** | 1. El sistema recorre las solicitudes del empleado.<br>2. Cuenta las que están EN CURSO.<br>3. Devuelve si son menos de 5. |
| **FLUJO ALTERNATIVO** | No tiene. |

### CU11 - Ver solicitudes asignadas

| **NOMBRE:** | **Ver solicitudes asignadas** |
|---|---|
| **NÚMERO:** | CU11 |
| **ACTOR/ES** | Guardia de seguridad, Empleado de mantenimiento |
| **PRECONDICIONES** | El trabajador está identificado con su legajo. |
| **FLUJO PRINCIPAL** | 1. El trabajador elige "Mis trabajos".<br>2. El sistema muestra sus solicitudes ASIGNADAS y EN CURSO, ordenadas por prioridad.<br>3. El trabajador elige una y ve el detalle. |
| **FLUJO ALTERNATIVO** | 2a. No tiene trabajos pendientes. Se informa y termina. |

### CU12 - Actualizar estado de solicitud

| **NOMBRE:** | **Actualizar estado de solicitud** |
|---|---|
| **NÚMERO:** | CU12 |
| **ACTOR/ES** | Guardia de seguridad, Empleado de mantenimiento (el responsable), Administrador |
| **PRECONDICIONES** | La solicitud está asignada a quien la actualiza (o es el administrador). |
| **FLUJO PRINCIPAL** | 1. El usuario elige la solicitud.<br>2. Ingresa una observación.<br>3. El sistema la avanza al estado siguiente (ASIGNADA → EN CURSO → RESUELTA) y registra fecha, estado y observación.<br>4. Si pasó a RESUELTA, guarda la fecha de resolución. |
| **FLUJO ALTERNATIVO** | 3a. Pasa a EN CURSO: el sistema verifica el límite (include CU10). **Error:** si ya tiene 5 en curso, se informa y no cambia.<br>3b. **Error:** ya está RESUELTA o CERRADA. Se informa que no puede avanzar más desde acá. |

### CU13 - Cerrar solicitud

| **NOMBRE:** | **Cerrar solicitud** |
|---|---|
| **NÚMERO:** | CU13 |
| **ACTOR/ES** | Administrador |
| **PRECONDICIONES** | La solicitud está RESUELTA. |
| **FLUJO PRINCIPAL** | 1. El administrador elige la solicitud.<br>2. El sistema verifica que sea una solicitud (pedido) y no un reclamo.<br>3. El sistema la pasa a CERRADA sin esperar los 5 días y registra el cambio. |
| **FLUJO ALTERNATIVO** | 1a. **Error:** no está RESUELTA. Se informa y termina.<br>2a. Es un reclamo: el sistema informa que los reclamos se cierran solos 5 días después de resueltos, para que el vecino pueda reabrirlos (CU47). |

## Expensas y pagos

### CU14 - Cambiar presupuesto del mes

| **NOMBRE:** | **Cambiar presupuesto del mes** |
|---|---|
| **NÚMERO:** | CU14 |
| **ACTOR/ES** | Administrador |
| **PRECONDICIONES** | Las expensas de ese período todavía no se emitieron. |
| **FLUJO PRINCIPAL** | 1. El administrador elige "Cambiar presupuesto".<br>2. Ingresa el período (mes y año) y el presupuesto total del mes.<br>3. El sistema guarda el presupuesto de ese período, reemplazando el anterior si había uno.<br>4. El sistema confirma. |
| **FLUJO ALTERNATIVO** | 2a. **Error:** el presupuesto es menor o igual a cero. Se informa y se pide de nuevo.<br>3a. **Error:** las expensas de ese período ya se emitieron. Se informa que no se puede cambiar; las correcciones se hacen con un asiento de ajuste (CU23). |

### CU15 - Emitir expensas

| **NOMBRE:** | **Emitir expensas** |
|---|---|
| **NÚMERO:** | CU15 |
| **ACTOR/ES** | Administrador |
| **PRECONDICIONES** | Hay lotes cargados y el presupuesto del período está cargado (CU14). |
| **FLUJO PRINCIPAL** | 1. El administrador elige "Emitir expensas" e ingresa el período.<br>2. El sistema suma la superficie de todos los lotes.<br>3. Para cada lote, calcula el coeficiente (superficie del lote / superficie total).<br>4. Crea una expensa por lote con importe = presupuesto × coeficiente, vencimiento el día 10 y estado PENDIENTE.<br>5. El sistema informa cuántas expensas emitió. |
| **FLUJO ALTERNATIVO** | 1a. **Error:** las expensas de ese período ya fueron emitidas. Se informa y termina.<br>1b. No hay presupuesto cargado para ese período. El sistema pide cargarlo primero (CU14). |

### CU16 - Registrar pago

| **NOMBRE:** | **Registrar pago** |
|---|---|
| **NÚMERO:** | CU16 |
| **ACTOR/ES** | Titular (paga desde el sistema), Administrador (registra pagos en efectivo) |
| **PRECONDICIONES** | El lote tiene expensas emitidas. |
| **FLUJO PRINCIPAL** | 1. El usuario elige "Registrar pago" e ingresa el número de lote.<br>2. El sistema muestra las expensas impagas, de la más antigua a la más nueva.<br>3. El usuario ingresa el importe y el medio de pago (efectivo, transferencia o tarjeta).<br>4. Para cada expensa impaga, empezando por la más antigua, el sistema calcula el interés a la fecha (include CU17).<br>5. Aplica el importe primero a los intereses y después al capital, y registra un pago para esa expensa con fecha, importe y medio.<br>6. Repite los pasos 4 y 5 mientras quede importe por aplicar.<br>7. El sistema actualiza la morosidad del lote (include CU19).<br>8. El sistema muestra el comprobante con cómo se repartió el pago. |
| **FLUJO ALTERNATIVO** | 1a. **Error:** el titular quiere pagar un lote que no es suyo. Se informa y termina.<br>3a. **Error:** el importe es menor o igual a cero. Se informa "Importe inválido" y se pide de nuevo.<br>3b. El medio es TARJETA: se verifica la tarjeta (extend CU18). Si se rechaza, el pago no se registra.<br>6a. Sobra importe después de cubrir toda la deuda: el excedente queda como saldo a favor del lote. |

### CU17 - Calcular intereses

| **NOMBRE:** | **Calcular intereses** |
|---|---|
| **NÚMERO:** | CU17 |
| **ACTOR/ES** | Sistema (incluido en CU16 y CU20) |
| **PRECONDICIONES** | Se conoce la expensa y la fecha de cálculo. |
| **FLUJO PRINCIPAL** | 1. El sistema verifica si la expensa está vencida (pasó el día 10).<br>2. Cuenta los meses de atraso desde el vencimiento (mes o fracción cuenta como un mes).<br>3. Calcula el interés: saldo de capital × 3 % × meses de atraso, menos los intereses ya pagados.<br>4. Devuelve el interés pendiente. |
| **FLUJO ALTERNATIVO** | 1a. La expensa no está vencida: el interés es cero. |

### CU18 - Verificar tarjeta

| **NOMBRE:** | **Verificar tarjeta** |
|---|---|
| **NÚMERO:** | CU18 |
| **ACTOR/ES** | Sistema (extiende CU16) |
| **PRECONDICIONES** | El medio de pago elegido es TARJETA. |
| **FLUJO PRINCIPAL** | 1. El sistema pide el número de operación del posnet (no se guardan datos de la tarjeta).<br>2. Verifica que no esté vacío y que no se haya usado en otro pago.<br>3. Si es válido, el pago sigue en CU16. |
| **FLUJO ALTERNATIVO** | 2a. **Error:** el número de operación está vacío o ya fue usado. Se informa "Tarjeta rechazada" y el pago no se registra. |

### CU19 - Actualizar morosidad

| **NOMBRE:** | **Actualizar morosidad** |
|---|---|
| **NÚMERO:** | CU19 |
| **ACTOR/ES** | Sistema (incluido en CU16 y CU47) |
| **PRECONDICIONES** | Se conoce el lote. |
| **FLUJO PRINCIPAL** | 1. El sistema cuenta los períodos impagos del lote (expensas vencidas con saldo de capital).<br>2. Si son más de 3 y el lote no estaba moroso, lo marca como moroso y le envía una notificación formal al titular.<br>3. Si son 3 o menos y el lote estaba moroso, le quita la marca. |
| **FLUJO ALTERNATIVO** | 2a. El lote ya estaba moroso: sigue moroso y no se vuelve a notificar. |

### CU20 - Consultar estado de cuenta

| **NOMBRE:** | **Consultar estado de cuenta** |
|---|---|
| **NÚMERO:** | CU20 |
| **ACTOR/ES** | Titular |
| **PRECONDICIONES** | El titular está identificado con su documento. |
| **FLUJO PRINCIPAL** | 1. El titular elige "Estado de cuenta".<br>2. El sistema muestra sus lotes y el titular elige uno.<br>3. Para cada expensa, el sistema muestra período, importe, lo pagado, el interés a hoy (include CU17) y el saldo.<br>4. Muestra el total adeudado, el saldo a favor y si el lote es moroso. |
| **FLUJO ALTERNATIVO** | 2a. El titular tiene un solo lote: se muestra directamente.<br>3a. El lote no tiene expensas emitidas. Se informa y termina. |

### CU21 - Apelar deuda

| **NOMBRE:** | **Apelar deuda** |
|---|---|
| **NÚMERO:** | CU21 |
| **ACTOR/ES** | Titular |
| **PRECONDICIONES** | El lote tiene alguna expensa con intereses por mora. |
| **FLUJO PRINCIPAL** | 1. El titular elige "Apelar deuda" y elige la expensa.<br>2. Escribe el motivo (por ejemplo, un problema con el medio de pago o con el lote).<br>3. El sistema registra la apelación como una solicitud de tipo ADMINISTRATIVO y le muestra el número.<br>4. La apelación queda para que el administrador la revise (CU22). |
| **FLUJO ALTERNATIVO** | 1a. **Error:** la expensa no tiene intereses. Se informa "No hay intereses para apelar".<br>2a. El motivo está vacío. Se pide de nuevo. |

### CU22 - Disminuir intereses

| **NOMBRE:** | **Disminuir intereses** |
|---|---|
| **NÚMERO:** | CU22 |
| **ACTOR/ES** | Administrador |
| **PRECONDICIONES** | La expensa tiene intereses por mora. |
| **FLUJO PRINCIPAL** | 1. El administrador elige el lote y la expensa (normalmente desde una apelación).<br>2. Ingresa el monto de intereses a descontar y el motivo.<br>3. El sistema registra un asiento de ajuste negativo con ese motivo (include CU23).<br>4. El sistema recalcula el saldo del lote y su morosidad.<br>5. Si venía de una apelación, la pasa a RESUELTA. |
| **FLUJO ALTERNATIVO** | 2a. **Error:** el monto es mayor que los intereses de la expensa. Se informa y se pide de nuevo.<br>2b. El administrador rechaza la apelación: no descuenta nada, comenta el motivo y la pasa a RESUELTA. |

### CU23 - Registrar asiento de ajuste

| **NOMBRE:** | **Registrar asiento de ajuste** |
|---|---|
| **NÚMERO:** | CU23 |
| **ACTOR/ES** | Administrador (también incluido en CU22) |
| **PRECONDICIONES** | Existe el pago o la expensa a corregir. |
| **FLUJO PRINCIPAL** | 1. El administrador elige el pago (o la expensa) a corregir.<br>2. Ingresa el monto del ajuste (positivo o negativo) y el motivo.<br>3. El sistema registra el asiento con fecha, monto, motivo y a qué corrige. El pago original no se modifica ni se borra.<br>4. El sistema recalcula el saldo del lote. |
| **FLUJO ALTERNATIVO** | 1a. **Error:** el administrador intenta eliminar un pago. El sistema no lo permite e indica que se use un asiento de ajuste.<br>2a. **Error:** el motivo está vacío. Los ajustes tienen que estar justificados: se pide de nuevo. |

## Luminarias y cámaras

### CU24 - Controlar equipos

| **NOMBRE:** | **Controlar equipos** |
|---|---|
| **NÚMERO:** | CU24 |
| **ACTOR/ES** | Empleado de mantenimiento |
| **PRECONDICIONES** | Hay equipos cargados en el sector a controlar. |
| **FLUJO PRINCIPAL** | 1. El empleado elige "Controlar equipos" y el sector.<br>2. El sistema muestra las luminarias y cámaras del sector con su estado.<br>3. El empleado revisa cada equipo y marca si funciona bien.<br>4. El sistema guarda la fecha del control. |
| **FLUJO ALTERNATIVO** | 1a. **Error:** el sector no existe o no tiene equipos. Se informa y termina.<br>3a. Un equipo no funciona: el empleado registra la falla (extend CU26) y sigue con el resto. |

### CU25 - Ver cámaras de seguridad

| **NOMBRE:** | **Ver cámaras de seguridad** |
|---|---|
| **NÚMERO:** | CU25 |
| **ACTOR/ES** | Guardia de seguridad |
| **PRECONDICIONES** | El guardia está identificado con su legajo. |
| **FLUJO PRINCIPAL** | 1. El guardia elige "Ver cámaras".<br>2. El sistema muestra todas las cámaras con su código, sector y estado.<br>3. El guardia elige una y ve su detalle (resolución, días de grabación, última falla). |
| **FLUJO ALTERNATIVO** | 2a. El guardia filtra por sector. Se muestran solo esas cámaras.<br>3a. El guardia detecta que una cámara no anda: registra la falla (extend CU26). |

### CU26 - Registrar falla

| **NOMBRE:** | **Registrar falla** |
|---|---|
| **NÚMERO:** | CU26 |
| **ACTOR/ES** | Titular, Residente, Guardia de seguridad, Empleado de mantenimiento (extiende CU24 y CU25) |
| **PRECONDICIONES** | El equipo existe y no está FUERA DE SERVICIO. |
| **FLUJO PRINCIPAL** | 1. El usuario ingresa el código del equipo.<br>2. Escribe la descripción de la falla.<br>3. El sistema registra la falla con fecha, hora y quién la reportó.<br>4. El sistema pasa el equipo a CON FALLA.<br>5. El sistema confirma. |
| **FLUJO ALTERNATIVO** | 1a. **Error:** el código no existe. Se informa y se pide de nuevo.<br>4a. El equipo ya estaba CON FALLA o EN REPARACIÓN: se agrega la descripción a la falla abierta y no se crea otra. |

### CU27 - Generar orden de mantenimiento

| **NOMBRE:** | **Generar orden de mantenimiento** |
|---|---|
| **NÚMERO:** | CU27 |
| **ACTOR/ES** | Administrador |
| **PRECONDICIONES** | El equipo está CON FALLA. |
| **FLUJO PRINCIPAL** | 1. El administrador elige el equipo con falla.<br>2. Elige el empleado de mantenimiento y escribe la descripción del trabajo.<br>3. El sistema calcula la prioridad: cámara, ALTA; luminaria, MEDIA, o ALTA si hay más de 3 luminarias con falla en el mismo sector.<br>4. El sistema crea la orden con fecha de apertura y prioridad, y pasa el equipo a EN REPARACIÓN.<br>5. El sistema confirma. |
| **FLUJO ALTERNATIVO** | 1a. **Error:** el equipo no tiene una falla registrada. Se informa y termina.<br>2a. **Error:** la persona elegida no es empleado de mantenimiento. Se informa y se pide de nuevo. |

### CU28 - Cerrar orden de mantenimiento

| **NOMBRE:** | **Cerrar orden de mantenimiento** |
|---|---|
| **NÚMERO:** | CU28 |
| **ACTOR/ES** | Empleado de mantenimiento, Administrador |
| **PRECONDICIONES** | La orden está abierta. |
| **FLUJO PRINCIPAL** | 1. El usuario elige la orden.<br>2. Ingresa la fecha de cierre, el técnico que hizo el trabajo y la descripción de la reparación.<br>3. El sistema cierra la orden.<br>4. El sistema pasa el equipo a OPERATIVO. |
| **FLUJO ALTERNATIVO** | 2a. **Error:** falta la fecha, el técnico o la descripción. El sistema no cierra la orden e informa qué falta.<br>2b. **Error:** la fecha de cierre es anterior a la de apertura. Se pide de nuevo. |

### CU29 - Consultar estado de equipos

| **NOMBRE:** | **Consultar estado de equipos** |
|---|---|
| **NÚMERO:** | CU29 |
| **ACTOR/ES** | Administrador, Guardia de seguridad, Empleado de mantenimiento |
| **PRECONDICIONES** | Hay equipos cargados. |
| **FLUJO PRINCIPAL** | 1. El usuario elige "Estado de equipos".<br>2. El sistema muestra cada equipo con código, tipo (luminaria o cámara), sector y estado.<br>3. El usuario elige uno y ve sus fallas y órdenes. |
| **FLUJO ALTERNATIVO** | 2a. El usuario filtra por sector, por tipo o por estado. Se muestran solo esos. |

### CU30 - Marcar equipo fuera de servicio

| **NOMBRE:** | **Marcar equipo fuera de servicio** |
|---|---|
| **NÚMERO:** | CU30 |
| **ACTOR/ES** | Administrador |
| **PRECONDICIONES** | El equipo existe y no se puede reparar. |
| **FLUJO PRINCIPAL** | 1. El administrador elige el equipo.<br>2. Ingresa el motivo.<br>3. El sistema pasa el equipo a FUERA DE SERVICIO. |
| **FLUJO ALTERNATIVO** | 3a. El equipo tenía una orden abierta: el sistema la cierra con la descripción "Equipo fuera de servicio". |

### CU31 - Modificar sector

| **NOMBRE:** | **Modificar sector** |
|---|---|
| **NÚMERO:** | CU31 |
| **ACTOR/ES** | Administrador |
| **PRECONDICIONES** | — |
| **FLUJO PRINCIPAL** | 1. El administrador elige "Modificar sector".<br>2. Elige un sector existente o crea uno nuevo con nombre y tipo (calle interna, acceso o espacio común).<br>3. Agrega equipos nuevos (luminarias o cámaras) o quita equipos del sector.<br>4. El sistema guarda los cambios. |
| **FLUJO ALTERNATIVO** | 2a. **Error:** ya existe un sector con ese nombre. Se informa y se pide otro.<br>3a. **Error:** el código del equipo nuevo ya existe. Se informa y se pide otro.<br>3b. **Error:** se quiere quitar un equipo que tiene una orden abierta. No se permite hasta cerrar la orden. |

## Visitas y autorizaciones

### CU32 - Dar autorización

| **NOMBRE:** | **Dar autorización** |
|---|---|
| **NÚMERO:** | CU32 |
| **ACTOR/ES** | Titular, Residente |
| **PRECONDICIONES** | El vecino está identificado. Si es residente, tiene permiso para autorizar (CU37). |
| **FLUJO PRINCIPAL** | 1. El vecino elige "Autorizar visita".<br>2. Ingresa nombre y documento de la visita, el lote, la fecha y la franja horaria (desde y hasta).<br>3. El sistema verifica los datos.<br>4. El sistema registra una autorización única, válida para ese día y un solo ingreso.<br>5. El sistema confirma. |
| **FLUJO ALTERNATIVO** | 1a. **Error:** es un residente sin permiso para autorizar. Se informa y termina.<br>2a. La visita es un proveedor habitual y el vecino quiere varios días: autorización recurrente (extend CU33).<br>3a. **Error:** la hora "desde" es posterior a la hora "hasta". Se pide de nuevo.<br>3b. **Error:** la fecha ya pasó. Se pide de nuevo.<br>3c. **Error:** el lote no es del vecino. Se informa y termina. |

### CU33 - Dar autorización recurrente

| **NOMBRE:** | **Dar autorización recurrente** |
|---|---|
| **NÚMERO:** | CU33 |
| **ACTOR/ES** | Titular, Residente (extiende CU32) |
| **PRECONDICIONES** | La persona autorizada es un proveedor. |
| **FLUJO PRINCIPAL** | 1. El vecino ingresa la empresa del proveedor.<br>2. Ingresa la fecha desde, la fecha hasta y la franja horaria.<br>3. El sistema verifica que el período no supere los 30 días.<br>4. El sistema registra la autorización recurrente, válida todos los días del período y para muchos ingresos. |
| **FLUJO ALTERNATIVO** | 2a. **Error:** la fecha hasta es anterior a la fecha desde. Se pide de nuevo.<br>3a. **Error:** el período supera los 30 días. Se informa y se pide de nuevo. |

### CU34 - Ver autorización

| **NOMBRE:** | **Ver autorización** |
|---|---|
| **NÚMERO:** | CU34 |
| **ACTOR/ES** | Visitante, Proveedor |
| **PRECONDICIONES** | La persona ingresó al sistema con su documento. |
| **FLUJO PRINCIPAL** | 1. La persona elige "Mis autorizaciones".<br>2. El sistema muestra sus autorizaciones vigentes: lote, fecha (o período) y franja horaria. |
| **FLUJO ALTERNATIVO** | 1a. **Error:** el documento no está registrado como visitante. Se informa y termina.<br>2a. No tiene autorizaciones vigentes. Se informa "No tiene autorizaciones". |

### CU35 - Ver autorizaciones

| **NOMBRE:** | **Ver autorizaciones** |
|---|---|
| **NÚMERO:** | CU35 |
| **ACTOR/ES** | Administrador |
| **PRECONDICIONES** | — |
| **FLUJO PRINCIPAL** | 1. El administrador elige "Ver autorizaciones".<br>2. El sistema muestra todas las autorizaciones vigentes con visita, lote, quién autorizó, fecha y franja. |
| **FLUJO ALTERNATIVO** | 2a. El administrador filtra por lote o por fecha. Se muestran solo esas.<br>2b. No hay autorizaciones vigentes. Se informa. |

### CU36 - Cancelar autorización

| **NOMBRE:** | **Cancelar autorización** |
|---|---|
| **NÚMERO:** | CU36 |
| **ACTOR/ES** | Administrador |
| **PRECONDICIONES** | La autorización está vigente. |
| **FLUJO PRINCIPAL** | 1. El administrador elige la autorización (desde CU35).<br>2. Ingresa el motivo.<br>3. El sistema la marca como cancelada; desde ese momento no habilita ingresos. |
| **FLUJO ALTERNATIVO** | 3a. La visita está adentro del barrio en ese momento: se cancela igual, pero el sistema avisa que todavía tiene que registrarse su salida (CU41). |

### CU37 - Gestionar permiso de autorizaciones

| **NOMBRE:** | **Gestionar permiso de autorizaciones** |
|---|---|
| **NÚMERO:** | CU37 |
| **ACTOR/ES** | Administrador |
| **PRECONDICIONES** | El residente está registrado en un lote. |
| **FLUJO PRINCIPAL** | 1. El administrador elige el lote y el residente.<br>2. El sistema muestra si el residente puede autorizar visitas.<br>3. El administrador le da el permiso.<br>4. El sistema lo guarda y confirma. |
| **FLUJO ALTERNATIVO** | 1a. **Error:** la persona es el titular. Se informa que el titular siempre puede autorizar.<br>2a. El residente ya tiene el permiso: el administrador puede sacárselo (extend CU38). |

### CU38 - Cancelar autorización a vecino

| **NOMBRE:** | **Cancelar autorización a vecino** |
|---|---|
| **NÚMERO:** | CU38 |
| **ACTOR/ES** | Administrador (extiende CU37) |
| **PRECONDICIONES** | El residente tiene permiso para autorizar visitas. |
| **FLUJO PRINCIPAL** | 1. El administrador elige "Quitar permiso".<br>2. El sistema le saca al residente el permiso para autorizar visitas.<br>3. Las autorizaciones que ya había dado siguen vigentes. |
| **FLUJO ALTERNATIVO** | 3a. El administrador elige también cancelar las autorizaciones que dio ese residente: se cancelan una por una (CU36). |

### CU39 - Guardar ingreso

| **NOMBRE:** | **Guardar ingreso** |
|---|---|
| **NÚMERO:** | CU39 |
| **ACTOR/ES** | Guardia de seguridad (de portería) |
| **PRECONDICIONES** | La visita llegó a la portería. |
| **FLUJO PRINCIPAL** | 1. El guardia ingresa el documento de la visita.<br>2. El sistema busca una autorización vigente para ese momento (include CU40).<br>3. El sistema verifica que la persona no tenga un ingreso sin salida.<br>4. El sistema guarda el ingreso con fecha, hora y el guardia que lo registró.<br>5. El sistema muestra "Ingreso habilitado" y el lote que visita. |
| **FLUJO ALTERNATIVO** | 1a. **Error:** el guardia no es de portería. Se informa que solo el guardia de portería registra ingresos.<br>2a. **Error:** no hay autorización vigente. El sistema muestra "Sin autorización: no se habilita el ingreso" y termina.<br>3a. **Error:** la persona ya está adentro (tiene un ingreso sin salida). Se informa y no se registra otro ingreso. |

### CU40 - Verificar autorización vigente

| **NOMBRE:** | **Verificar autorización vigente** |
|---|---|
| **NÚMERO:** | CU40 |
| **ACTOR/ES** | Sistema (incluido en CU39) |
| **PRECONDICIONES** | Se conoce el documento de la visita. |
| **FLUJO PRINCIPAL** | 1. El sistema busca las autorizaciones de esa persona.<br>2. Para cada una, verifica que no esté cancelada, que hoy esté dentro de la fecha (o del período, si es recurrente) y que la hora esté dentro de la franja.<br>3. Si es única, verifica que no se haya usado ya.<br>4. Devuelve la autorización vigente o ninguna. |
| **FLUJO ALTERNATIVO** | No tiene. |

### CU41 - Guardar salida

| **NOMBRE:** | **Guardar salida** |
|---|---|
| **NÚMERO:** | CU41 |
| **ACTOR/ES** | Guardia de seguridad (de portería) |
| **PRECONDICIONES** | La visita está por salir del barrio. |
| **FLUJO PRINCIPAL** | 1. El guardia ingresa el documento de la visita.<br>2. El sistema busca su ingreso sin salida.<br>3. El sistema guarda la hora de salida. |
| **FLUJO ALTERNATIVO** | 2a. **Error:** la persona no tiene un ingreso abierto. Se informa "No figura adentro del barrio" y termina. |

## Reportes

### CU42 - Emitir reportes

| **NOMBRE:** | **Emitir reportes** |
|---|---|
| **NÚMERO:** | CU42 |
| **ACTOR/ES** | Administrador |
| **PRECONDICIONES** | Hay datos cargados en el sistema. |
| **FLUJO PRINCIPAL** | 1. El administrador elige "Reportes".<br>2. Elige el tipo de reporte (include CU43).<br>3. El sistema junta los datos de ese tipo y arma el reporte:<br>4. Reclamos pendientes: los que no están cerrados, ordenados por prioridad y fecha, con responsable y estado.<br>5. Morosidad: los lotes con deuda, con períodos impagos, deuda de capital, intereses y si es moroso, ordenados de mayor a menor deuda.<br>6. Equipos fuera de servicio: los que no están operativos, por sector, con la fecha de la última falla.<br>7. El sistema muestra el reporte. |
| **FLUJO ALTERNATIVO** | 3a. No hay datos para ese reporte (por ejemplo, ningún lote con deuda). El sistema muestra "Sin resultados".<br>4a. El administrador elige guardar el reporte: el sistema lo guarda en un archivo de texto. |

### CU43 - Elegir tipo de reporte

| **NOMBRE:** | **Elegir tipo de reporte** |
|---|---|
| **NÚMERO:** | CU43 |
| **ACTOR/ES** | Administrador (incluido en CU42) |
| **PRECONDICIONES** | — |
| **FLUJO PRINCIPAL** | 1. El sistema muestra los tipos: reclamos pendientes, morosidad y equipos fuera de servicio.<br>2. El administrador elige uno.<br>3. El sistema devuelve el tipo elegido. |
| **FLUJO ALTERNATIVO** | 2a. **Error:** la opción elegida no existe. Se pide de nuevo. |

## Lotes y personal

### CU44 - Registrar lote

| **NOMBRE:** | **Registrar lote** |
|---|---|
| **NÚMERO:** | CU44 |
| **ACTOR/ES** | Administrador |
| **PRECONDICIONES** | — |
| **FLUJO PRINCIPAL** | 1. El administrador elige "Registrar lote".<br>2. Ingresa el número de lote y la superficie en m².<br>3. Ingresa el documento del titular.<br>4. El sistema busca a la persona y la asigna como titular.<br>5. El sistema guarda el lote y confirma. |
| **FLUJO ALTERNATIVO** | 2a. **Error:** ya existe un lote con ese número. Se informa y se pide otro.<br>2b. **Error:** la superficie es menor o igual a cero. Se pide de nuevo.<br>4a. La persona no está registrada: el administrador carga sus datos (nombre, apellido, documento, teléfono y correo) y sigue en el paso 5. |

### CU45 - Agregar residente

| **NOMBRE:** | **Agregar residente** |
|---|---|
| **NÚMERO:** | CU45 |
| **ACTOR/ES** | Titular |
| **PRECONDICIONES** | El titular está identificado y tiene al menos un lote. |
| **FLUJO PRINCIPAL** | 1. El titular elige "Agregar residente" y el lote.<br>2. Ingresa los datos del residente (nombre, apellido, documento, teléfono y correo) y si es familiar o inquilino.<br>3. Indica si el residente puede presentar reclamos.<br>4. El sistema agrega el residente al lote y confirma. |
| **FLUJO ALTERNATIVO** | 1a. **Error:** el lote no es de ese titular. Se informa y termina.<br>2a. **Error:** esa persona ya es residente de ese lote. Se informa y termina. |

### CU46 - Registrar personal

| **NOMBRE:** | **Registrar personal** |
|---|---|
| **NÚMERO:** | CU46 |
| **ACTOR/ES** | Administrador |
| **PRECONDICIONES** | — |
| **FLUJO PRINCIPAL** | 1. El administrador elige "Registrar personal".<br>2. Ingresa legajo, nombre y fecha de ingreso.<br>3. Elige el tipo: guardia de seguridad (con turno y puesto) o empleado de mantenimiento (con especialidad).<br>4. El sistema guarda al empleado y confirma. |
| **FLUJO ALTERNATIVO** | 2a. **Error:** el legajo ya existe. Se informa y se pide otro. |

## Generales

### CU47 - Procesar vencimientos

| **NOMBRE:** | **Procesar vencimientos** |
|---|---|
| **NÚMERO:** | CU47 |
| **ACTOR/ES** | Administrador (también se ejecuta al iniciar el sistema, S03) |
| **PRECONDICIONES** | Hay datos cargados. |
| **FLUJO PRINCIPAL** | 1. El administrador elige "Procesar vencimientos".<br>2. Para cada reclamo de prioridad alta que sigue sin asignar hace 24 horas o más y no fue escalado, el sistema lo marca como escalado y le envía una notificación al administrador.<br>3. Para cada reclamo RESUELTO hace más de 5 días, el sistema lo pasa a CERRADO y registra el cambio.<br>4. Para cada lote, actualiza la morosidad (include CU19).<br>5. Para cada cámara que no funciona hace más de 48 horas, le envía una notificación al administrador.<br>6. El sistema muestra un resumen de lo que procesó. |
| **FLUJO ALTERNATIVO** | 2a. No hay nada vencido: el sistema informa "No hay vencimientos para procesar".<br>5a. Ya se había avisado por esa cámara: no se vuelve a notificar. |

### CU48 - Ver notificaciones

| **NOMBRE:** | **Ver notificaciones** |
|---|---|
| **NÚMERO:** | CU48 |
| **ACTOR/ES** | Administrador, Titular |
| **PRECONDICIONES** | El usuario está identificado. |
| **FLUJO PRINCIPAL** | 1. El usuario elige "Notificaciones".<br>2. El sistema muestra sus notificaciones, de la más nueva a la más vieja, con fecha, tipo y mensaje.<br>3. El usuario elige una y el sistema la marca como leída. |
| **FLUJO ALTERNATIVO** | 2a. No tiene notificaciones. Se informa.<br>2b. El usuario elige ver solo las no leídas. |
