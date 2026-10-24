# Diagrama de clases inicial

## Clases

- LOTE
- PERSONA -> RESIDENTE | VISITANTE -> PROVEEDOR)
- PERSONAL (abstracta) -> ADMINISTRADOR | GUARDIA DE SEGURIDAD | EMPLEADO DE MANTENIMIENTO
- RECLAMO
- CAMBIO DE ESTADO
- COMENTARIO
- EXPENSA
- PAGO
- ASIENTO DE AJUSTE
- SECTOR
- EQUIPO (abstracta) -> LUMINARIA | CÁMARA
- FALLA
- ORDEN DE MANTENIMIENTO
- AUTORIZACIÓN (abstracta) -> AUTORIZACIÓN ÚNICA | AUTORIZACIÓN RECURRENTE
- INGRESO
- NOTIFICACIÓN
- GENERADOR DE REPORTES

## Enumeraciones

- TIPO DE RECLAMO (SEGURIDAD, MANTENIMIENTO, CONVIVENCIA, ADMINISTRATIVO)
- PRIORIDAD (ALTA, MEDIA, BAJA)
- ESTADO DE RECLAMO (ABIERTO, ASIGNADO, EN CURSO, RESUELTO, CERRADO)
- ESTADO DE EXPENSA (PENDIENTE, PARCIAL, PAGADA)
- MEDIO DE PAGO (EFECTIVO, TRANSFERENCIA, TARJETA)
- ESTADO DE EQUIPO (OPERATIVO, CON FALLA, EN REPARACIÓN, FUERA DE SERVICIO)
- TIPO DE RESIDENTE (FAMILIAR, INQUILINO)
- TIPO DE SECTOR (CALLE INTERNA, ACCESO, ESPACIO COMÚN)
- TURNO (MAÑANA, TARDE, NOCHE)
- PUESTO DE GUARDIA (PORTERÍA, RONDA, MONITOREO)
- ESPECIALIDAD (ELECTRICIDAD, PLOMERÍA, JARDINERÍA)
- TIPO DE NOTIFICACIÓN (ESCALAMIENTO, MOROSIDAD, CÁMARA SIN FUNCIONAR)

## Relaciones

### Herencia

- RESIDENTE HEREDA DE PERSONA
- ADMINISTRADOR HEREDA DE PERSONAL
- GUARDIA DE SEGURIDAD HEREDA DE PERSONAL
- EMPLEADO DE MANTENIMIENTO HEREDA DE PERSONAL
- LUMINARIA HEREDA DE EQUIPO
- CÁMARA HEREDA DE EQUIPO
- PROVEEDOR HEREDA DE VISITANTE
- AUTORIZACIÓN ÚNICA HEREDA DE AUTORIZACIÓN
- AUTORIZACIÓN RECURRENTE HEREDA DE AUTORIZACIÓN
- VISITANTE HEREDA DE PERSONA

### Composición

- LOTE 1 <> 0..* EXPENSA
- EXPENSA 1 <> 0..* PAGO
- RECLAMO 1 <> 1..* CAMBIO DE ESTADO
- RECLAMO 1 <> 0..* COMENTARIO
- EQUIPO 1 <> 0..* FALLA
- ADMINISTRADOR 1 <> 0..* NOTIFICACIÓN
- PERSONA 1 <> 0..* NOTIFICACIÓN

### Agregación

- LOTE 1 <> 0..* RESIDENTE
- SECTOR 1 <> 0..* EQUIPO

### Asociación

- LOTE 0..* -> 1 PERSONA (titular)
- LOTE 1 <-> 0..* RECLAMO
- RECLAMO 0..* -> 1 PERSONA (presentado por)
- RECLAMO 0..* <-> 0..1 PERSONAL (responsable)
- ASIENTO DE AJUSTE 0..* -> 1 PAGO
- EQUIPO 1 <-> 0..* ORDEN DE MANTENIMIENTO
- ORDEN DE MANTENIMIENTO 0..* -> 0..1 EMPLEADO DE MANTENIMIENTO (técnico)
- AUTORIZACIÓN 0..* -> 1 VISITANTE
- AUTORIZACIÓN 0..* -> 1 LOTE
- AUTORIZACIÓN 0..* -> 1 PERSONA (autorizante)
- AUTORIZACIÓN ÚNICA 1 <-> 0..1 INGRESO
- AUTORIZACIÓN RECURRENTE 1 <-> 0..* INGRESO
- INGRESO 0..* -> 1 GUARDIA DE SEGURIDAD

### Dependencia

- GENERADOR DE REPORTES - - -> RECLAMO (reporte de reclamos pendientes)
- GENERADOR DE REPORTES - - -> LOTE (reporte de morosidad)
- GENERADOR DE REPORTES - - -> EQUIPO (reporte de equipos fuera de servicio)

## Atributos y métodos de las clases

### LOTE

**Atributos:**

```
- numero: int
```

```
- superficie: double
```

```
- moroso: boolean
```

```
- saldoAFavor: double
```

```
- titular: Persona
```

```
- residentes: List<Residente>
```

```
- expensas: List<Expensa>
```

```
- reclamos: List<Reclamo>
```

**Métodos:**

```
+ agregarResidente(r: Residente): void
```

```
+ puedePresentarReclamo(p: Persona): boolean
```

```
+ calcularCoeficiente(superficieTotal: double): double
```

```
+ emitirExpensa(periodo: YearMonth, importe: double, vencimiento: LocalDate): Expensa
```

```
+ registrarPago(importe: double, medio: MedioPago, numeroOperacion: String, fecha: LocalDate): List<Pago>
```

```
+ expensasImpagas(fecha: LocalDate): List<Expensa>
```

```
+ cantidadPeriodosImpagos(fecha: LocalDate): int
```

```
+ actualizarMorosidad(fecha: LocalDate): boolean
```

```
+ deudaTotal(fecha: LocalDate): double
```

```
+ agregarReclamo(r: Reclamo): void
```

```
- verificarOperacion(numero: String): boolean
```

### PERSONA

**Atributos:**

```
# nombre: String
```

```
# apellido: String
```

```
# documento: String
```

```
# telefono: String
```

```
# correo: String
```

```
- notificaciones: List<Notificacion>
```

**Métodos:**

```
+ getDocumento(): String
```

```
+ agregarNotificacion(n: Notificacion): void
```

```
+ verNotificaciones(): List<Notificacion>
```

```
+ mostrarDatos(): String
```

### RESIDENTE (hereda de PERSONA)

**Atributos:**

```
- esInquilino: boolean
```

```
- autorizadoReclamos: boolean
```

```
- puedeAutorizarVisitas: boolean
```

**Métodos:**

```
+ mostrarDatos(): String (sobrescribe)
```

```
+ setAutorizadoReclamos(valor: boolean): void
```

```
+ darPermisoVisitas(): void
```

```
+ quitarPermisoVisitas(): void
```

### VISITANTE (hereda de PERSONA)

**Atributos:**

```
- autorizaciones: List<Autorizacion>
```

**Métodos:**

```
+ mostrarDatos(): String (sobrescribe)
```

```
+ agregarAutorizacion(a: Autorizacion): void
```

```
+ autorizacionesVigentes(momento: LocalDateTime): List<Autorizacion>
```

### PROVEEDOR (hereda de VISITANTE)

**Atributos:**

```
- empresa: String
```

**Métodos:**

```
+ mostrarDatos(): String
```

### PERSONAL (abstracta)

**Atributos:**

```
- legajo: int
```

```
- nombre: String
```

```
- fechaIngreso: LocalDate
```

```
- reclamosAsignados: List<Reclamo>
```

**Métodos:**

```
+ puedeAtender(notipo: TipoReclamo): boolean (abstracto)
```

```
+ cantidadEnCurso(): int
```

```
+ agregarReclamo(r: Reclamo): void
```

```
+ verReclamosAsignados(): List<Reclamo>
```

### ADMINISTRADOR

**Atributos:**

```
- notificaciones: List<Notificacion>
```

**Métodos:**

```
+ puedeAtender(tipo: TipoReclamo): boolean
```

```
+ asignarReclamo(r: Reclamo, p: Personal): void
```

```
+ emitirExpensas(periodo: YearMonth, presupuesto: double, lotes: List<Lote>): void
```

```
+ generarOrden(e: Equipo, empleado: EmpleadoMantenimiento, descripcion: String): OrdenMantenimiento
```

```
+ agregarNotificacion(n: Notificacion): void
```

### GUARDIA DE SEGURIDAD (hereda de PERSONAL)

**Atributos:**

```
- turno: Turno
```

```
- puesto: PuestoGuardia
```

**Métodos:**

```
+ puedeAtender(tipo: TipoReclamo): boolean
```

```
+ esDePorteria(): boolean
```

### EMPLEADO DE MANTENIMIENTO (hereda de PERSONAL)

**Atributos:**

```
- especialidad: Especialidad
```

**Métodos:**

```
+ puedeAtender(tipo: TipoReclamo): boolean
```

### RECLAMO

**Atributos:**

```
- numero: int
```

```
- fechaHora: LocalDateTime
```

```
- tipo: TipoReclamo
```

```
- descripcion: String
```

```
- estado: EstadoReclamo
```

```
- esSolicitud: boolean
```

```
- escalado: boolean
```

```
- fechaResolucion: LocalDateTime
```

```
- lote: Lote
```

```
- presentadoPor: Persona
```

```
- responsable: Personal
```

```
- historial: List<CambioEstado>
```

```
- comentarios: List<Comentario>
```

**Métodos:**

```
+ getPrioridad(): Prioridad
```

```
+ getEstado(): EstadoReclamo
```

```
+ asignar(p: Personal): void
```

```
+ avanzarEstado(observacion: String): void
```

```
+ reabrir(motivo: String): void
```

```
+ cancelar(): void
```

```
+ cerrar(): void
```

```
+ agregarComentario(autor: String, texto: String): void
```

```
+ debeEscalarse(ahora: LocalDateTime): boolean
```

```
+ marcarEscalado(): void
```

```
+ debeCerrarse(ahora: LocalDateTime): boolean
```

```
+ compareTo(otro: Reclamo): int
```

### CAMBIO DE ESTADO

**Atributos:**

```
- fechaHora: LocalDateTime
```

```
- estadoNuevo: EstadoReclamo
```

```
- observacion: String
```

### COMENTARIO

**Atributos:**

```
- fechaHora: LocalDateTime
```

```
- autor: String
```

```
- texto: String
```

### EXPENSA

**Atributos:**

```
- periodo: YearMonth
```

```
- importe: double
```

```
- vencimiento: LocalDate
```

```
- estado: EstadoExpensa
```

```
- saldoCapital: double
```

```
- interesesPagados: double
```

```
- pagos: List<Pago>
```

**Métodos:**

```
+ estaVencida(fecha: LocalDate): boolean
```

```
+ calcularInteres(fecha: LocalDate): double
```

```
+ registrarPago(monto: double, medio: MedioPago, fecha: LocalDate): Pago
```

```
+ estaImpaga(fecha: LocalDate): boolean
```

### PAGO

**Atributos:**

```
- fecha: LocalDate
```

```
- importe: double
```

```
- medio: MedioPago
```

```
- numeroOperacion: String
```

```
- montoInteres: double
```

```
- montoCapital: double
```

### ASIENTO DE AJUSTE

**Atributos:**

```
- fecha: LocalDate
```

```
- monto: double
```

```
- motivo: String
```

```
- pago: Pago
```

```
- expensa: Expensa
```

### SECTOR

**Atributos:**

```
- nombre: String
```

```
- tipo: TipoSector
```

```
- equipos: List<Equipo>
```

**Métodos:**

```
+ agregarEquipo(e: Equipo): void
```

```
+ quitarEquipo(e: Equipo): void
```

```
+ luminariasConFalla(): int
```

### EQUIPO (abstracta)

**Atributos:**

```
- codigo: String
```

```
- fechaInstalacion: LocalDate
```

```
- estado: EstadoEquipo
```

```
- fechaUltimoControl: LocalDate
```

```
- sector: Sector
```

```
- fallas: List<Falla>
```

```
- ordenes: List<OrdenMantenimiento>
```

**Métodos:**

```
+ registrarFalla(descripcion: String, reportadaPor: String): void
```

```
+ generarOrden(descripcion: String, empleado: EmpleadoMantenimiento): OrdenMantenimiento
```

```
+ cerrarOrden(numero: int, fecha: LocalDate, tecnico: EmpleadoMantenimiento, descripcion: String): void
```

```
+ marcarFueraDeServicio(motivo: String): void
```

```
+ registrarControl(fecha: LocalDate): void
```

```
+ prioridadDeFalla(): Prioridad (abstracto)
```

```
+ getTipo(): String (abstracto)
```

### LUMINARIA (hereda de EQUIPO)

**Atributos:**

```
- tipoLampara: String
```

```
- potencia: int
```

**Métodos:**

```
+ prioridadDeFalla(): Prioridad
```

```
+ getTipo(): String
```

### CÁMARA (hereda de EQUIPO)

**Atributos:**

```
- resolucion: String
```

```
- diasGrabacion: int
```

**Métodos:**

```
+ prioridadDeFalla(): Prioridad
```

```
+ getTipo(): String (sobrescribe)
```

```
+ superaHorasSinFuncionar(ahora: LocalDateTime): boolean
```

### FALLA

**Atributos:**

```
- fechaHora: LocalDateTime
```

```
- descripcion: String
```

```
- reportadaPor: String
```

### ORDEN DE MANTENIMIENTO

**Atributos:**

```
- numero: int
```

```
- fechaApertura: LocalDate
```

```
- prioridad: Prioridad
```

```
- descripcionTrabajo: String
```

```
- fechaCierre: LocalDate
```

```
- tecnico: EmpleadoMantenimiento
```

```
- descripcionReparacion: String
```

**Métodos:**

```
+ cerrar(fecha: LocalDate, tecnico: EmpleadoMantenimiento, descripcion: String): void
```

```
+ estaAbierta(): boolean
```

### AUTORIZACIÓN (abstracta)

**Atributos:**

```
- visitante: Visitante
```

```
- lote: Lote
```

```
- autorizante: Persona
```

```
- horaDesde: LocalTime
```

```
- horaHasta: LocalTime
```

```
- cancelada: boolean
```

**Métodos:**

```
+ estaVigente(momento: LocalDateTime): boolean (abstracto)
```

```
+ registrarIngreso(guardia: GuardiaSeguridad, momento: LocalDateTime): Ingreso (abstracto)
```

```
+ dentroDeFranja(hora: LocalTime): boolean
```

```
+ cancelar(): void
```

### AUTORIZACIÓN ÚNICA (hereda de AUTORIZACIÓN)

**Atributos:**

```
- fecha: LocalDate
```

```
- ingreso: Ingreso
```

**Métodos:**

```
+ estaVigente(momento: LocalDateTime): boolean
```

```
+ registrarIngreso(guardia: GuardiaSeguridad, momento: LocalDateTime): Ingreso (sobrescribe: solo uno)
```

### AUTORIZACIÓN RECURRENTE (hereda de AUTORIZACIÓN)

**Atributos:**

```
- fechaDesde: LocalDate
```

```
- fechaHasta: LocalDate
```

```
- ingresos: List<Ingreso>
```

**Métodos:**

```
+ estaVigente(momento: LocalDateTime): boolean
```

```
+ registrarIngreso(guardia: GuardiaSeguridad, momento: LocalDateTime): Ingreso (sobrescribe: agrega a la lista)
```

### INGRESO

**Atributos:**

```
- fechaHoraIngreso: LocalDateTime
```

```
- fechaHoraEgreso: LocalDateTime
```

```
- guardia: GuardiaSeguridad
```

**Métodos:**

```
+ registrarEgreso(momento: LocalDateTime): void
```

```
+ estaAdentro(): boolean
```

### NOTIFICACIÓN

**Atributos:**

```
- fechaHora: LocalDateTime
```

```
- tipo: TipoNotificacion
```

```
- mensaje: String
```

```
- leida: boolean
```

**Métodos:**

```
+ marcarLeida(): void
```

```
+ enviar(): void
```

### GENERADOR DE REPORTES

**Métodos:**

```
+ reclamosPendientes(reclamos: List<Reclamo>): String
```

```
+ morosidad(lotes: List<Lote>, fecha: LocalDate): String
```

```
+ equiposFueraDeServicio(equipos: List<Equipo>): String
```

##
