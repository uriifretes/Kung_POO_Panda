Cambios en los diagramas de secuencia respecto de la etapa 1

 ## En los dos diagramas
- Se agregan la vista y los controladores: el actor ya no le habla directo a las clases del modelo, sino a la vista, y la vista al controlador, como funciona el programa.
- Los errores pasan a ser excepciones (TransicionInvalidaException, LimiteReclamosException, PagoInvalidoException, etc.) en lugar de mensajes de error.

 ## CU09 - Asignar solicitud
 - Se agregan VistaReclamos, ControladorReclamos y ControladorPersonal
- El controlador busca el personal por legajo (buscarPersonal()) y el pedido por número (tenerPedido()).
- Las tres validaciones (estado, límite de 5 y tipo de reclamo) quedan en un solo alt, en lugar de alt anidados.
- Se saca el loop que contaba los reclamos en curso: se usa directamente p.cantidadenCurso().
- agregarReclamo(this) pasa a ser asignarReclamo(this) en Personal.
- El cambio de estado y la creación del CambiosDeEstado se hacen con cambiarEstado(ASIGNADO, ahora, observacion).

 ## CU16 - Registrar pago
- El actor es solo el Administrador
- Se agregan VistaExpensas, ControladorExpensas y ControladorLotes. El lote se busca por número con tenerLote().
- El pago lo registra CuentasCorrientes en lugar de Lote.
- La verificación del número de operación la hace GestorExpensas
- Pagos se crea también con el número de operación.
- La parte de morosidad se resume en actualizarMorosidad(fecha) con una nota. Se sacan cantidadPeriodosImpagos(), la creación de la Notificacion y enviar().
