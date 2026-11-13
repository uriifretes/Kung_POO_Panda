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
  - El límite de 5 pedidos en curso se controla solo al asignar: si a un empleado se le asignan más de 5 y después se avanzan, puede quedar con más de 5 en curso.
  - Después de un pago parcial, la deuda que queda deja de generar intereses por unos meses, porque los intereses se recalculan sobre el capital actual y se les resta lo ya pagado.
  - El aviso de una cámara que lleva más de 48 horas sin funcionar sale una sola vez: si se arregla y vuelve a fallar, no se avisa de nuevo.
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

Al principio el trabajo se nos hizo muy largo y nos costó imaginarnos cómo llevar al código todas las relaciones del diagrama de clases. A medida que avanzamos, cada clase nos salía con más soltura. El trabajo nos sirvió para cerrar conceptos que ya conocíamos, como las relaciones entre clases, la herencia y el polimorfismo, y para reforzarlos usándolos de verdad.

También entendimos por completo cómo funciona la separación en vista, controlador y modelo: la vista le pide algo al controlador, el controlador lo resuelve con el modelo, y si algo falla el error sube como excepción hasta la vista, que es la que lo muestra. Verlo funcionar en nuestro propio programa nos ayudó a entender por qué conviene separar así las responsabilidades.

El trabajo también nos mostró que, aunque el modelado se haga con cuidado y teniendo en cuenta todos los requisitos, al empezar a codificar aparecen cambios que no estaban previstos, e incluso hay que modificar o sacar partes del dominio. Lo vimos con SOLID y GRASP: en el diagrama de clases inicial no los habíamos aplicado, y recién al pensar el diseño final y, sobre todo, al programar, nos dimos cuenta de que el modelo no funcionaba como lo habíamos imaginado. Al codificar agregamos vistas, controladores, excepciones propias y la persistencia, sacamos algunos casos de uso y supuestos, y cambiamos varios métodos. Entendimos que el diseño va cambiando durante todo el desarrollo: el diagrama con el que se empieza no es el mismo con el que se termina.

Probar el programa también nos enseñó mucho: las pruebas unitarias y las pruebas del programa funcionando nos mostraron errores que no habíamos visto al escribir el código, como un saldo de fracciones de centavo que dejaba una expensa sin pagar del todo o una autorización rechazada que después hacía que el programa se cerrara. En cuanto al trabajo en grupo, coordinarnos entre los tres no siempre fue fácil por los tiempos de cada uno, y buena parte de la integración del código terminó concentrada en los últimos días.

Algo que haríamos distinto es la forma de encarar la etapa 1. La pensamos como un sistema completo y no tanto como algo que después íbamos a tener que programar. Nos entusiasmamos y sumamos mucho dominio y muchos casos de uso, y al momento de codificar nos costó bastante plasmar todo eso. Creemos que nos dimos más complejidad de la necesaria: se podían aplicar los mismos contenidos de la materia con un sistema más simple.

Como mejora, nos quedó pendiente una interfaz gráfica. No nos animamos a hacerla y tampoco llegábamos con el tiempo; con un poco más de margen la hubiéramos intentado.
