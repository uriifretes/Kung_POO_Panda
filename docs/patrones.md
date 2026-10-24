# Patrones SOLID y GRASP

## Principios SOLID

### S – Responsabilidad única

**Problema:** Lote y Administrador hacen demasiadas cosas de temas distintos. Lote maneja sus residentes y sus reclamos, pero además emite expensas, registra pagos, calcula la deuda y la morosidad. Administrador asigna reclamos, emite expensas y genera órdenes de mantenimiento. Si cambia, por ejemplo, la forma de calcular los intereses, hay que tocar Lote, y si cambia quién puede presentar reclamos, también. Tienen más de un motivo para cambiar.

**Decisión:** conviene separar la parte de plata de Lote en una clase nueva, CuentaCorriente, que tenga las expensas, el saldo a favor, la morosidad y los métodos de pagos y deuda. Lote queda con número, superficie, titular y residentes. A Administrador se le saca emitirExpensas(), que pasa a una clase GestorExpensas, y generarOrden(), que queda solo en Equipo.

**Clases:** Lote, CuentaCorriente (nueva), Administrador, GestorExpensas (nueva), Equipo.

**Ventaja:** cada clase tiene un solo motivo para cambiar. Si cambia la regla de intereses, no se toca el código de residentes ni de reclamos, y hay menos riesgo de romper algo que ya funcionaba.

### O – Abierto/cerrado

**Problema:** hay dos lugares donde agregar algo nuevo obliga a modificar código que ya funciona:

- Reclamo diferencia si es una solicitud o un reclamo solo con el atributo esSolicitud. Pero son cosas que se comportan distinto: la solicitud la cierra el administrador y no se puede reabrir, y el reclamo se cierra solo a los 5 días de resuelto y se puede reabrir. Por eso los métodos cerrar(), reabrir() y debeCerrarse() tienen que preguntar con un if qué es. Si se quisiera agregar otro tipo, habría que modificar todos esos métodos.
- GeneradorReportes tiene un método fijo para cada reporte (reclamosPendientes(), morosidad() y equiposFueraDeServicio()). Para agregar un reporte nuevo hay que abrir esa clase y sumarle otro método.
**Decisión:** conviene hacer subclases. Se crea una clase abstracta PedidoVecino con todo lo que comparten (número, estado, historial, comentarios) y dos subclases, Reclamo y Solicitud, que sobrescriben esos métodos a su manera; se elimina el boolean. Para los reportes se crea la interfaz Reporte con el método generar(), y cada reporte es una clase propia que la implementa (ReporteReclamosPendientes, ReporteMorosidad y ReporteEquiposFueraDeServicio). GeneradorReportes queda con un único método, emitir(r: Reporte).

**Clases:** PedidoVecino (nueva), Reclamo, Solicitud (nueva), Reporte (nueva), ReporteReclamosPendientes, ReporteMorosidad, ReporteEquiposFueraDeServicio (nuevas), GeneradorReportes.

**Ventaja:** permite agregar más subclases o más reportes si se quiere expandir, sin modificar las clases que ya existen. El sistema queda abierto para agregar y cerrado para modificar.

### L – Sustitución de Liskov

**Problema:** en las jerarquías de Equipo (Luminaria, Cámara) y de Autorización (Única, Recurrente), el resto del sistema trabaja con la clase padre, sin saber cuál es la hija. Hay que asegurar que cualquier hija pueda usarse en lugar del padre sin que nada falle.

**Decisión:** las hijas implementan todos los métodos abstractos del padre respetando lo que el padre promete: prioridadDeFalla() y getTipo() en los equipos, estaVigente(), registrarIngreso() y tieneIngresoAbierto() en las autorizaciones. Ninguna hija deja un método vacío ni tira un error por no soportarlo.

**Clases:** Equipo, Luminaria, Cámara, Autorización, AutorizaciónÚnica, AutorizaciónRecurrente.

**Ventaja:** el guardia o el administrador pueden trabajar con cualquier equipo o autorización sin preguntar de qué tipo es, y todo funciona igual.

### I – Segregación de interfaces

**Problema:** Visitante hereda de Persona los métodos de notificaciones (agregarNotificacion(), verNotificaciones()), pero no los va a utilizar nunca, porque las notificaciones solo le llegan al titular (morosidad) y al administrador (escalamiento y cámaras). Lo mismo pasa con Proveedor, que hereda de Visitante.

**Decisión:** se sacan las notificaciones de Persona y se ponen en una interfaz Notificable, que implementan solo las clases que realmente reciben notificaciones: una clase nueva Titular (que hereda de Persona) y Administrador.

**Clases:** Persona, Visitante, Proveedor, Titular (nueva), Administrador, Notificable (nueva).

**Ventaja:** cada clase tiene solo los métodos que usa, y no se le puede mandar por error una notificación a un visitante.

### D – Inversión de dependencias

**Problema:** Persona y Administrador utilizan notificaciones (las dos tienen su lista y su agregarNotificacion()), pero no tienen nada en común, porque Personal no hereda de Persona. Además, el método enviar() de Notificación no tiene destinatario, y si se le agregara uno concreto tendría que conocer a las dos clases por separado.

**Decisión:** es necesaria una interfaz, Notificable, que implementan las clases que reciben notificaciones. Notificación pasa a tener el atributo destinatario: Notificable, y enviar() llama a destinatario.agregarNotificacion(this).

**Clases:** Notificable (nueva), Notificación, Titular, Administrador.

**Ventaja:** quien notifica depende de la interfaz y no de las clases concretas. Si en el futuro otra clase tiene que recibir notificaciones, implementa Notificable y no hay que modificar nada más.

## Patrones GRASP

### Experto en información

**Problema:** hay métodos que están en clases que no tienen los datos para hacerlos:

- Administrador genera órdenes de mantenimiento (generarOrden()), pero no tiene los datos: la prioridad, la falla, el estado y el sector los tiene Equipo, que además ya tiene su propio generarOrden(). El método está duplicado.
- Lote tiene verificarOperacion(), que controla que el número de operación del posnet no se haya usado en otro pago, pero Lote solo conoce sus propios pagos, no los de los otros lotes.
- Notificación tiene enviar(), pero no sabe a quién va dirigida.
**Decisión:** cada tarea la tiene que hacer la clase que tiene la información. generarOrden() queda solo en Equipo, y el administrador lo usa a raíz del equipo. verificarOperacion() pasa a GestorExpensas, que conoce todos los lotes y puede recorrer todos los pagos. Notificación recibe el atributo destinatario.

**Clases:** Administrador, Equipo, Lote, GestorExpensas, Notificación.

**Ventaja:** no hay código duplicado, cada clase usa sus propios datos, y si cambia una regla (por ejemplo, la de prioridad) se cambia en un solo lugar.

### Polimorfismo

**Problema:** en Reclamo, el comportamiento cambia según si es solicitud o reclamo, y para saberlo hay que preguntar con un if (esSolicitud) en cada método. Además, cuando se agrega una notificación no se sabe quién la recibe, porque los destinatarios son de clases distintas, y GeneradorReportes necesita un método distinto para cada reporte.

**Decisión:** en lugar de preguntar el tipo, cada subclase resuelve el método a su manera: Reclamo y Solicitud sobrescriben puedeReabrirse() y debeCerrarse(). Para las notificaciones, la interfaz Notificable hace que cada destinatario reciba la notificación a su manera sin que quien la envía sepa de qué clase es. Para los reportes, GeneradorReportes llama a generar() sobre cualquier Reporte, y cada reporte lo resuelve a su manera. El mismo patrón ya estaba aplicado en prioridadDeFalla() de Equipo y en estaVigente() de Autorización.

**Clases:** PedidoVecino, Reclamo, Solicitud, Notificable, Titular, Administrador, Reporte y sus tres implementaciones.

**Ventaja:** se eliminan los if por tipo, el código queda más claro, y se pueden agregar tipos nuevos sin tocar los métodos existentes.

### Creador

**Problema:** cada vez que cambia el estado de un reclamo o solicitud hay que crear un CambioEstado, y cada comentario es un objeto Comentario. Hay que decidir qué clase los crea.

**Decisión:** los crea PedidoVecino, dentro de sus propios métodos (asignar(), avanzarEstado(), reabrir(), cancelar() y agregarComentario()), porque los contiene por composición y tiene los datos para crearlos (el estado nuevo y la fecha). Lo mismo pasa con Expensa, que crea sus Pagos, y con Equipo, que crea sus Fallas y sus órdenes.

**Clases:** PedidoVecino, CambioEstado, Comentario, Expensa, Pago, Equipo.

**Ventaja:** nadie de afuera puede crear un cambio de estado suelto o agregarlo mal, así que el historial siempre coincide con lo que realmente pasó.

### Alta cohesión

**Problema:** Lote y Administrador tienen métodos de temas que no tienen nada que ver entre sí (residentes, reclamos y plata en Lote; reclamos, expensas y equipos en Administrador). Son clases difíciles de entender y de probar.

**Decisión:** la misma separación que en Responsabilidad única: la parte de plata de Lote pasa a CuentaCorriente, y Administrador pierde emitirExpensas() y generarOrden().

**Clases:** Lote, CuentaCorriente, Administrador.

**Ventaja:** cada clase hace pocas cosas, todas del mismo tema. Se entiende sola y es más fácil encontrar dónde está cada cosa.

### Fabricación pura

**Problema:** el sistema tiene que emitir reportes de reclamos pendientes, morosidad y equipos fuera de servicio, pero ninguna clase del dominio debería saber armar reportes. Si se pusieran en Lote, Reclamo o Equipo, esas clases mezclarían su responsabilidad con la de mostrar información.

**Decisión:** se crea GeneradorReportes, una clase que no existe en el enunciado, inventada solo para emitir los reportes. Cada reporte es una clase propia que implementa la interfaz Reporte, y GeneradorReportes los emite sin conocer cuál es.

**Clases:** GeneradorReportes, Reporte, ReporteReclamosPendientes, ReporteMorosidad, ReporteEquiposFueraDeServicio.

**Ventaja:** las clases del dominio no se ensucian con lógica de reportes, y todo lo relacionado con reportes está en un solo lugar.

### Bajo acoplamiento

**Problema:** si las clases dependen mucho unas de otras, cualquier cambio en una rompe las demás.

**Decisión:** cada reporte recibe sus datos (reclamos, lotes o equipos) en lugar de ir a buscarlos, y GeneradorReportes solo conoce la interfaz Reporte. En Comentario y en Falla, el autor se guarda como texto, porque puede ser un vecino o alguien del personal, que son jerarquías distintas, y así no quedan atadas a ninguna de las dos. Además, separar la plata de Lote (CuentaCorriente) reduce la cantidad de clases de las que depende Lote.

**Clases:** GeneradorReportes, Reporte, Comentario, Falla, Lote, CuentaCorriente.

**Ventaja:** los cambios en una clase afectan a pocas otras, así que el sistema es más fácil de mantener.
