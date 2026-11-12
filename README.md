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


Al iniciar, el programa pregunta si se quieren cargar datos de ejemplo. Conviene responder que sí para poder probarlo.

## Estructura

- `docs`: enunciado y documento de diseño
- `docs/uml`: diagramas
- `src`: código
- `test`: pruebas
