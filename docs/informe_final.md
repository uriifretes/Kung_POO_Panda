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
- **Persistencia.** Agregamos la clase ArchivoDatos (paquete persistencia), que guarda y carga todo el sistema en un único archivo binario, datos_aromos.dat, usando serialización de Java (ObjectOutputStream / ObjectInputStream). Como ControladorGeneral contiene a todos los controladores y estos al modelo completo, alcanza con guardar ese único objeto. Para eso, las clases del modelo y los controladores implementan Serializable. Al iniciar, Main carga el archivo si existe. Si no existe, o si no se puede leer, ofrece cargar los datos de ejemplo. Los datos se guardan al salir con la opción 0 del menú principal.
- **Limitaciones y mejoras futuras:** 
  - Reclamos y Solicitud no se comportan distinto. Solo se distinguen por la clase
  - Autorizaciones no guarda el lote ni quién autorizó la visita. 
  - Los datos se guardan solo al salir con la opción 0. Si se cierra el programa de golpe, se pierde lo hecho en esa sesión. 
  - Las clases no declaran serialVersionUID. Si se modifica una clase, el archivo viejo puede dejar de cargarse (en ese caso el programa avisa y arranca desde cero). 
  - El programa es solo de consola y hay un único administrador. 
  - Posible mejora: reemplazar la serialización por una base de datos.
## 2. Dificultades encontradas

- A pesar de haber desarrollado los diagramas, nos fue muy dificil plasmar lo diseñado en el código, especialmente al inicio.
- Tuvimos dificultades en la administracion del tiempo, ya que con otras responsabilidades quedamos justos con la entrega de la etapa 2
- No dimensionamos el alcance y complejidad del trabajo respecto al desarrollo de la etapa 2, por lo que cuando comenzamos a desarrollarla nos dimos cuenta que nos iba a tomar un mayor esfuerzo del que esperabamos
- Cuando comenzamos a codificar, nos costo mucho unir las diferentes clases y metodos. Especialmente en las primeras clases nos costo empezar a escribir los métodos uno por uno y se nos dificultó relacionar los métodos entre diferentes clases
- Cuando estabamos avanzados en el trabajo nos dimos cuenta que habiamos omitido varias cosas del dominio, lo que nos obligó a retroceder en lo que ya habíamos hecho y tuvimos que replantear esas partes del código.


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


