# TPO POO - Los Aromos

TPO de Programación Orientada a Objetos (UADE). Propuesta A: gestión del barrio cerrado Los Aromos.

El enunciado está en `docs/enunciado.md` y los requisitos y supuestos en `docs/requisitos.md`.

## Descripción

Programa de consola en Java para administrar el barrio cerrado Los Aromos. Permite:

- Registrar lotes con su titular y sus residentes.
- Presentar reclamos y solicitudes, asignarlos al personal y seguir sus estados hasta que se cierran.
- Emitir expensas, registrar pagos (efectivo, transferencia o tarjeta), calcular intereses y marcar a los lotes morosos.
- Informar fallas de luminarias y cámaras y generar órdenes de mantenimiento.
- Autorizar visitas y proveedores, y registrar sus ingresos y egresos en la portería.
- Avisar al titular y al administrador con notificaciones, y generar reportes de reclamos pendientes, morosidad y equipos fuera de servicio.

Al entrar se elige un rol (titular, residente, visitante/proveedor, administrador, guardia de seguridad o empleado de mantenimiento) y cada uno tiene su propio menú.

## Integrantes

- Uriel Alejandro Fretes Giuffre
- Ezequiel Santiago Leguiza
- Juan Ignacio Alvarez Marcantoni

## Requisitos

- JDK 17 o superior.
- No usa librerías externas.
- Opcional: IntelliJ IDEA y Git.

## Instalación y ejecución

Primero hay que descargar el proyecto: `git clone https://github.com/uriifretes/Kung_POO_Panda.git`, o desde GitHub con Code > Download ZIP.

### Con IntelliJ IDEA

1. Abrir la carpeta `Kung_POO_Panda` con File > Open.
2. En File > Project Structure > Project, elegir un SDK 17 o superior.
3. Si la carpeta `src` no aparece en azul, hacer clic derecho sobre ella > Mark Directory as > Sources Root.
4. Abrir `src/aplicacion/Main.java` y ejecutarlo con el botón verde al lado de `main`.

### Desde la terminal

Desde la carpeta del proyecto, compilar y ejecutar:

En Windows (PowerShell):

```
javac -encoding UTF-8 -d out (Get-ChildItem src -Recurse -Filter *.java).FullName
java -cp out aplicacion.Main
```


La primera vez, el programa pregunta si se quieren cargar datos de ejemplo. Conviene responder que sí para poder probarlo.

Al salir con la opción 0 del menú principal, todos los datos se guardan en el archivo `datos_aromos.dat`, en la carpeta desde donde se ejecuta (en IntelliJ, la carpeta del proyecto). La próxima vez se cargan solos. Para empezar de cero, hay que borrar ese archivo.

## Datos de prueba

Se cargan al responder «s» a «Desea cargar datos de ejemplo?». Al entrar, cada rol pide un legajo o un DNI.

### Con qué entrar a cada rol

**Administrador**
- Legajo 1: Marta Gomez.

**Guardia de seguridad**
- Legajo 10: Carlos Perez, de portería (puede registrar ingresos y salidas).
- Legajo 11: Diego Ruiz, de ronda (no puede registrar ingresos).
- Legajo 12: Laura Benitez, de portería.

**Empleado de mantenimiento**
- Legajo 20: Luis Torres, electricidad.
- Legajo 21: Raul Soto, plomería.
- Legajo 22: Jorge Molina, jardinería.

**Titular**
- DNI 30111222: Ana Lopez, lote 1. Tiene todo pagado y saldo a favor.
- DNI 30333444: Bruno Diaz, lote 2. Debe 3 expensas.
- DNI 30555666: Carla Mendez, lote 3. Es morosa (debe 5 expensas).
- DNI 30777888: Diego Fernandez, lote 4. Debe 3 expensas. Pagó con tarjeta (operación OP-1001).
- DNI 30999000: Elena Suarez, lote 5. Debe 3 expensas.
- DNI 31111222: Federico Gomez, lote 6. Es moroso (debe 5 expensas).

**Residente**
- DNI 40111222: Pedro Lopez, lote 1. Puede presentar reclamos y autorizar visitas.
- DNI 40333444: Sofia Diaz, lote 2. No tiene permisos.
- DNI 40777888: Martin Fernandez, lote 4. Puede presentar reclamos.

**Visitante o proveedor**
- DNI 35000111: Julia Ramos. Tiene autorización para hoy, todo el día.
- DNI 36000222: Hugo Silva, de Jardines SA. Tiene autorización por 30 días, de 7 a 20 h.
- DNI 35000333: Marcos Vega. Su autorización era de ayer, así que no puede entrar.
- DNI 36000444: Lucia Paz, de Agua Pura SRL. Tiene autorización por 10 días, todo el día.

### Qué más viene cargado

**Pedidos**
- Nro 1: reclamo de seguridad del lote 1, ABIERTO.
- Nro 2: solicitud de mantenimiento del lote 1, ABIERTO (sirve para asignarla al legajo 20).
- Nro 3: reclamo de convivencia del lote 2, ASIGNADO al guardia 10.
- Nro 4: reclamo de mantenimiento del lote 3, EN_CURSO con el empleado 21.
- Nro 5: solicitud administrativa del lote 4, RESUELTO (se puede reabrir).
- Nro 6: reclamo de seguridad del lote 5, CERRADO.
- Nro 7: solicitud de mantenimiento del lote 2, cancelada.

**Expensas**
- Están emitidas las de los últimos 5 meses, incluido el actual.

**Equipos**
- Acceso Principal: cámara CAM-01, funcionando.
- Calle 1: luminarias LUM-01, LUM-02 y LUM-03 con falla, y LUM-04 funcionando. Si falla una más, la orden sale con prioridad ALTA.
- Calle 2: luminaria LUM-05, funcionando.
- Plaza Central: cámara CAM-02 en reparación y luminaria LUM-06 fuera de servicio.

## Pruebas

Las pruebas unitarias están en la carpeta `test`. Son clases con un `main` que muestran `OK` o `FALLA` por cada prueba. `PruebasTodas` las corre todas.

- **En IntelliJ:** clic derecho en la carpeta `test` > Mark Directory as > Test Sources Root. Después, abrir `test/PruebasTodas.java` y ejecutarlo con el botón verde.
- **Desde la terminal (PowerShell):**

```
javac -encoding UTF-8 -d out (Get-ChildItem src,test -Recurse -Filter *.java).FullName
java -cp out PruebasTodas
```

El plan de pruebas funcionales, con los resultados, está en `docs/plan_de_pruebas.md`.

## Estructura

- `docs`: enunciado, requisitos, documento de diseño, notas de cambios, plan de pruebas e informe
- `docs/uml`: diagramas
- `src`: código, separado en paquetes (`persistencia` guarda y carga los datos)
- `test`: pruebas unitarias
