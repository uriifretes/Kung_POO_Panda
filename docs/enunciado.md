# Propuesta A: Gestión de un barrio cerrado

## Contexto

Un barrio cerrado con 150 lotes necesita un sistema para ordenar la relación entre la administración y los vecinos, que hoy se resuelve con planillas y mensajes sueltos.

### Casos de uso mínimos

- Registrar y gestionar solicitudes y reclamos de vecinos (alta, asignación, seguimiento de estado, cierre).
- Emitir expensas, registrar pagos y calcular intereses por mora.
- Controlar luminarias y cámaras de seguridad: registrar fallas, generar órdenes de mantenimiento y consultar el estado de cada equipo.
- Registrar el ingreso de visitas y proveedores con autorización previa del vecino.
- Emitir reportes (reclamos pendientes, morosidad, equipos fuera de servicio).

## Reglas de negocio

En las tres propuestas las reglas son de cumplimiento obligatorio y los valores numéricos son de referencia: el grupo puede ajustarlos si deja el supuesto documentado.

### Reclamos y solicitudes

- Cada reclamo tiene un tipo (seguridad, mantenimiento, convivencia, administrativo) y una prioridad: seguridad es alta, mantenimiento es media, y convivencia y administrativo son bajas.
- Los estados son abierto, asignado, en curso, resuelto y cerrado, y solo se puede avanzar al estado siguiente. El vecino puede reabrir un reclamo resuelto dentro de los 5 días; pasado ese plazo sin respuesta, se cierra.
- Cada reclamo tiene un único responsable asignado, y ningún empleado puede tener más de 5 reclamos en curso a la vez.
- Un reclamo de prioridad alta que no fue atendido en 24 horas se escala al administrador.
- Solo el titular del lote o una persona autorizada por él puede presentar reclamos.

### Expensas y pagos

- La expensa mensual de cada lote es el presupuesto del mes multiplicado por el coeficiente de participación del lote (según su superficie).
- El vencimiento es el día 10 de cada mes. Desde esa fecha se cobra un interés punitorio del 3 % mensual sobre el saldo impago.
- Los pagos parciales se imputan primero a los intereses y después al capital, empezando por el período más antiguo.
- Un lote con más de tres períodos impagos se marca como moroso y se genera una notificación formal al titular. La condición se quita al regularizar la deuda.
- Un pago registrado no se elimina: si hubo un error, se corrige con un asiento de ajuste.

### Luminarias y cámaras

- Cada equipo tiene un estado: operativo, con falla, en reparación o fuera de servicio.
- Una falla en una cámara genera una orden de mantenimiento de prioridad alta. Una falla en una luminaria genera una de prioridad normal, que pasa a alta si fallan más de 3 luminarias del mismo sector.
- Si una cámara está sin funcionar más de 48 horas, se informa al administrador.
- Una orden de mantenimiento no se puede cerrar sin registrar la fecha, el técnico y la descripción de la reparación.

### Ingreso de visitas

- Toda visita requiere una autorización previa del vecino, con fecha y franja horaria de vigencia. Sin autorización, seguridad no habilita el ingreso.
- Los proveedores habituales pueden tener una autorización recurrente de hasta 30 días.
- Cada ingreso se registra con su egreso, y una misma persona no puede registrar un segundo ingreso sin haber salido.

## Enunciado para el modelado

El barrio cerrado «Los Aromos» está compuesto por 150 lotes. Cada lote tiene un número, una superficie en metros cuadrados y un titular, que es una persona con nombre, apellido, documento, teléfono y correo electrónico. Un lote tiene un único titular, pero una misma persona puede ser titular de más de un lote. Además del titular, en un lote pueden vivir otros residentes, que pueden ser familiares o inquilinos y de quienes se registran los mismos datos personales.

El barrio está a cargo de un administrador que coordina al personal. El personal es de dos tipos: los guardias de seguridad, que tienen un turno y un puesto asignado (portería, ronda o monitoreo), y los empleados de mantenimiento, que tienen una especialidad (electricidad, plomería o jardinería). De todos se conoce el legajo, el nombre y la fecha de ingreso.

Los vecinos presentan reclamos y solicitudes al administrador. Cada reclamo tiene un número, una fecha, un tipo, una descripción, una prioridad y un estado, y corresponde a un lote y a la persona que lo presentó. El administrador asigna cada reclamo a un empleado. Cada vez que cambia el estado de un reclamo queda registrada la fecha, el nuevo estado y una observación, de modo que se pueda consultar toda su evolución. Los reclamos también pueden tener comentarios del vecino y del personal.

Cada mes se emite una expensa por lote, que tiene un período, un importe, una fecha de vencimiento y un estado. Una expensa puede cancelarse con uno o varios pagos, de los cuales se registra la fecha, el importe y el medio de pago (efectivo, transferencia o tarjeta). Si el pago se realiza después del vencimiento, se calcula un interés por mora.

El barrio tiene luminarias y cámaras de seguridad ubicadas en distintos sectores (calles internas, accesos y espacios comunes). De cada equipo se conoce un código, su sector, la fecha de instalación y su estado. Las cámaras tienen además una resolución y una cantidad de días de grabación que almacenan, y las luminarias tienen un tipo de lámpara y una potencia. Cuando un equipo falla, un vecino o un guardia registra la falla y el administrador genera una orden de mantenimiento para un empleado, con fecha de apertura, prioridad, descripción del trabajo y fecha de cierre. A lo largo de su vida, un mismo equipo puede tener muchas órdenes.

Para que una visita pueda ingresar, el vecino debe autorizarla indicando el nombre y el documento del visitante, el lote que visita y la fecha y franja horaria permitidas. Los proveedores habituales pueden tener una autorización recurrente por varios días. Cuando la persona llega, el guardia de la portería consulta la autorización y registra la hora de ingreso y, más tarde, la de egreso.

A partir de los datos del sistema, el administrador debe poder emitir reportes de reclamos pendientes, de morosidad por lote y de equipos fuera de servicio. Todo dato necesario que no figure en este enunciado debe completarse como un supuesto y quedar documentado.
