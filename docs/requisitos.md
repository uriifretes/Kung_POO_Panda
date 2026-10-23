# Requisitos y supuestos

## Actores

- TITULAR
- RESIDENTE
- VISITANTE
- PROVEEDOR
- ADMINISTRADOR
- GUARDIA DE SEGURIDAD
- EMPLEADO DE MANTENIMIENTO

## Requerimientos funcionales

RF01 - El sistema deberá permitir al titular de un lote, o a una persona autorizada por él, registrar un reclamo indicando el tipo y la descripción.

RF02 - El sistema deberá permitir al administrador asignar cada reclamo a un empleado, sin superar los 5 reclamos en curso por empleado.

RF03 - El sistema deberá registrar cada cambio de estado de un reclamo con la fecha, el nuevo estado y una observación, y permitir consultar su evolución completa.

RF04 - El sistema deberá escalar al administrador los reclamos de prioridad alta que no fueron atendidos en 24 horas.

RF05 - El sistema deberá emitir la expensa mensual de cada lote según el presupuesto del mes y el coeficiente de participación del lote.

RF06 - El sistema deberá registrar pagos totales o parciales, calcular los intereses por mora e imputar cada pago primero a intereses y después a capital, desde el período más antiguo.

RF07 - El sistema deberá marcar como moroso a un lote con más de tres períodos impagos y notificar formalmente al titular.

RF08 - El sistema deberá permitir registrar la falla de un equipo y generar una orden de mantenimiento con la prioridad que corresponda según el tipo de equipo.

RF09 - El sistema deberá permitir al vecino autorizar visitas y proveedores, y al guardia de portería registrar el ingreso y el egreso de cada persona autorizada.

RF10 - El sistema deberá emitir los reportes de reclamos pendientes, de morosidad por lote y de equipos fuera de servicio.

## Reglas de negocio

Las reglas de negocio son las del enunciado (ver `enunciado.md`).

## Supuestos

### Generales

**S01** - No hay usuario ni contraseña: al iniciar, el sistema pregunta con qué rol se trabaja (titular, residente, visitante, proveedor, administrador, guardia o empleado) y la persona se identifica con su documento o su legajo.

**S02** - Los datos se guardan en archivos de texto y se cargan al iniciar el programa.

**S03** - Los controles que dependen del tiempo (escalamiento de reclamos, cierre automático, morosidad y alerta de cámaras) los ejecuta el administrador desde la opción "Procesar vencimientos" del menú, y también se ejecutan al iniciar el sistema.

**S04** - El administrador es parte del personal: tiene legajo, nombre y fecha de ingreso.

**S05** - El personal no hereda de Persona, porque el enunciado le da otros datos (legajo, nombre y fecha de ingreso).

### Personas y lotes

**S06** - Una persona se identifica por su documento. El titular puede vivir o no en el lote.

**S07** - Las visitas poseen menos permisos que los residentes.

### Reclamos

**S08** - Un reclamo se considera "atendido" cuando se asigna. Escalar significa enviarle una notificación al administrador; se escala una sola vez y el estado del reclamo no cambia.

**S09** - Al reabrir, el reclamo vuelve a EN CURSO con el mismo responsable y queda registrado en el historial.

**S10** - Los comentarios guardan la fecha, el autor y el texto. No se puede comentar un reclamo cerrado.

### Expensas y pagos

**S11** - El administrador carga el presupuesto total de cada mes. Coeficiente del lote = superficie del lote / suma de las superficies de todos los lotes.

**S12** - Si un pago cubre varias expensas, se registra un pago por cada una.

**S13** - Si el pago supera toda la deuda, el excedente queda como saldo a favor del lote.

**S14** - Un período impago es una expensa vencida con saldo de capital mayor a cero.

**S15** - Un asiento de ajuste tiene fecha, monto (positivo o negativo), motivo y el pago que corrige.

### Luminarias y cámaras

**S16** - Un sector tiene nombre y tipo (calle interna, acceso o espacio común). No tiene relación con los lotes.

**S17** - Una falla guarda la fecha, la descripción y quien la reportó, y pasa el equipo a CON FALLA.

**S18** - Al generar la orden de mantenimiento el equipo pasa a EN REPARACIÓN, y al cerrarla vuelve a OPERATIVO. FUERA DE SERVICIO lo marca el administrador cuando el equipo no se puede reparar.

**S19** - Para la regla de las luminarias se cuentan las del mismo sector que están CON FALLA o EN REPARACIÓN.

**S20** - La prioridad "normal" de las órdenes se representa con la prioridad MEDIA.

**S21** - Las 48 horas de una cámara sin funcionar se cuentan desde la fecha de la falla.

### Visitas

**S22** - Un proveedor se toma en cuenta como un tipo de visitante.

**S23** - El visitante y el proveedor pueden ingresar al sistema con su documento solo para consultar sus autorizaciones.

**S24** - Una autorización única vale para un día y un solo ingreso.

### Notificaciones

**S25** - Una notificación pertenece a su destinatario.

### Otros supuestos

S26 - El vecino puede cancelar una solicitud o reclamo solo mientras está ABIERTO. Pasa a CERRADO con la observación "Cancelada por el vecino". Es la otra excepción a "solo avanzar al estado siguiente".

S27 - Las solicitudes se asignan según el tipo: seguridad y convivencia a un guardia, mantenimiento a un empleado de mantenimiento y administrativas al administrador.

S28 - El límite de 5 cuenta solo las solicitudes EN CURSO de cada empleado.

S29 - Las "personas autorizadas" para presentar reclamos son residentes del lote que el titular marcó como autorizados.

S30 - El titular siempre puede autorizar visitas. Un residente solo puede si el administrador le dio el permiso.

S31 - Una apelación de deuda se registra como una solicitud de tipo ADMINISTRATIVO.

S32 - Para disminuir intereses se registra un asiento de ajuste negativo sobre la expensa. Un asiento corrige un pago o una expensa.

S33 - En los pagos con tarjeta se registra el número de operación del posnet. No se guardan datos de la tarjeta.

S34 - Una autorización cancelada por el administrador deja de habilitar ingresos.

S35 - Las notificaciones se marcan como leídas cuando el destinatario las abre.

S36 - Las solicitudes (pedidos) y los reclamos (quejas) usan la misma clase y el mismo circuito de estados. Se distinguen con un atributo. Las solicitudes las cierra el administrador cuando están resueltas, sin esperar los 5 días, y no se pueden reabrir.
