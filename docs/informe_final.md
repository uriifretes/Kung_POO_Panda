# Informe final - TPO POO Los Aromos

## 1. Cambios respecto del diseño original

Durante la codificación nos apartamos del diseño de la etapa 1 en varios puntos. El detalle de cada diagrama está en `docs/cambios_delfinalalresultado.md` (clases) y `docs/cambios_diagramassecalresultado.md` (secuencia). Los cambios más importantes fueron:

- **Vistas y controladores.** El diseño de la etapa 1 solo tenía las clases del modelo. Para usar el programa desde la consola agregamos vistas (un menú por rol) y controladores (uno por área, más un ControladorGeneral que los reúne). Así la entrada y salida por consola queda separada de la lógica.
- **Paquetes.** Ordenamos el código en paquetes por tema: lotes, personas, personal, reclamos, expensas, equipos, visitas, notificaciones, reportes, controladores, vistas, aplicacion, Enums y Excepciones.
- **Nombres en plural.** La mayoría de las clases quedaron en plural en el código (Lotes, PedidosVecinos, Expensas, etc.) y actualizamos el diagrama para que coincida.
- **Excepciones propias.** Los errores que en los diagramas de secuencia eran mensajes pasaron a ser excepciones: TransicionInvalidaException, LimiteReclamosException, PagoInvalidoException, SinAutorizacionException e IngresoDuplicadoException.
- **El momento como parámetro.** Los métodos que registran algo con fecha (asignar, avanzar, cancelar, reabrir, registrarFalla, etc.) reciben el momento (LocalDateTime) en lugar de tomar la hora adentro. El controlador les pasa la hora actual, y para probar se pueden usar fechas fijas.
- **Reglas que dependen del tiempo.** En la etapa 1 había métodos que solo consultaban, como debeEscalarse() y debeCerrarse(). En el código los reemplazamos por métodos que aplican el cambio: escalarPortiempo() (reclamo de prioridad ALTA abierto más de 24 horas), cerrarsiVencio() (pedido resuelto hace más de 5 días) y avisarSiCorresponde() (cámara más de 48 horas sin funcionar). ControladorGeneral los ejecuta con procesarVencimientos(), al iniciar el programa y desde el menú del administrador.
- **Orden de los reclamos.** PedidosVecinos implementa Comparable (ordena por fecha) y agregamos ComparadorPorPrioridad, que implementa Comparator. El reporte de reclamos pendientes lo usa para mostrar primero los de prioridad ALTA.
- **Supuestos.** Sacamos la cancelación de autorizaciones de visita (S34) y el supuesto S36, porque no coincidían con el código. También ajustamos S15 y S32: el asiento de ajuste ya no queda asociado a un pago.
- **Persistencia.** [COMPLETAR cuando esté: qué se guarda, en qué archivos y qué clases se agregaron.]

## 2. Dificultades encontradas




## 3. Distribución de tareas

Etapa 1:

- **Uriel:** README inicial, enunciado, requisitos y supuestos; diagramas de secuencia.
- **Ezequiel:** casos de uso y diagrama de casos de uso; diagrama de clases final con los patrones aplicados.
- **Juan Ignacio:** casos de uso; diagrama de clases inicial; documento de diseño en PDF.

Etapa 2:

- **Uriel:** clases del modelo (constructores, validaciones y excepciones), controladores, orden en paquetes, Comparable y Comparator.
- **Ezequiel:** enums y vistas de consola, diagramas y notas de cambios de la etapa 2
- **Juan Ignacio:** reportes (GeneradorReportes y los tres reportes) y el procesamiento de vencimientos, persistencia, plan de pruebas y README

## 4. Reflexión del grupo


