import Enums.TipoSector;

import java.util.List;

public class VistaEquipos {
    private ControladorEquipos controlador;

    public VistaEquipos(ControladorEquipos controlador) {
        this.controlador = controlador;
    }

    public void registrarSector() {
        Consola.titulo("Registrar sector");
        try {
            String nombre = Consola.leerTexto("Nombre del sector");
            TipoSector tipo = Consola.leerEnum("Tipo de sector", TipoSector.class);
            controlador.registrarSector(nombre, tipo);
            System.out.println("Sector registrado.");
        } catch (IllegalArgumentException e) {
            Consola.error(e);
        }
    }

    public void registrarCamara() {
        Consola.titulo("Registrar camara");
        try {
            String codigo = Consola.leerTexto("Codigo");
            String sector = Consola.leerTexto("Nombre del sector");
            String resolucion = Consola.leerTexto("Resolucion");
            int dias = Consola.leerEntero("Dias de grabacion");
            controlador.registrarCamara(codigo, sector, resolucion, dias);
            System.out.println("Camara registrada.");
        } catch (IllegalArgumentException e) {
            Consola.error(e);
        }
    }

    public void registrarLuminaria() {
        Consola.titulo("Registrar luminaria");
        try {
            String codigo = Consola.leerTexto("Codigo");
            String sector = Consola.leerTexto("Nombre del sector");
            String tipoLampara = Consola.leerTexto("Tipo de lampara");
            int potencia = Consola.leerEntero("Potencia (W)");
            controlador.registrarLuminarias(codigo, sector, tipoLampara, potencia);
            System.out.println("Luminaria registrada.");
        } catch (IllegalArgumentException e) {
            Consola.error(e);
        }
    }

    public void registrarFalla(String reportadaPor) {
        Consola.titulo("Registrar falla de un equipo");
        try {
            String codigo = Consola.leerTexto("Codigo del equipo");
            String descripcion = Consola.leerTexto("Descripcion de la falla");
            controlador.registrarFalla(codigo, descripcion, reportadaPor);
            System.out.println("Falla registrada. El equipo pasa a " + controlador.tenerEquipo(codigo).getEstado() + ".");
        } catch (IllegalArgumentException | IllegalStateException e) {
            Consola.error(e);
        }
    }

    public void generarOrden() {
        Consola.titulo("Generar orden de mantenimiento");
        try {
            String codigo = Consola.leerTexto("Codigo del equipo");
            String descripcion = Consola.leerTexto("Descripcion del trabajo");
            controlador.registrarOrden(codigo, descripcion);
            Equipos e = controlador.tenerEquipo(codigo);
            OrdenesDeMantenimiento ultima = ultimaOrden(e);
            System.out.println("Orden Nro " + ultima.getNumero() + " generada con prioridad " + ultima.getPrioridad()
                    + ". El equipo pasa a " + e.getEstado() + ".");
        } catch (IllegalArgumentException | IllegalStateException e) {
            Consola.error(e);
        }
    }

    /** legajoTecnico en 0 significa que el administrador elige quien cerro la orden. */
    public void cerrarOrden(int legajoTecnico) {
        Consola.titulo("Cerrar orden de mantenimiento");
        try {
            String codigo = Consola.leerTexto("Codigo del equipo");
            mostrarOrdenes(controlador.tenerEquipo(codigo));
            int numero = Consola.leerEntero("Numero de orden");
            if (legajoTecnico == 0) {
                legajoTecnico = Consola.leerEntero("Legajo del tecnico");
            }
            String descripcion = Consola.leerTexto("Descripcion de la reparacion");
            controlador.cerrarOrden(codigo, numero, legajoTecnico, descripcion);
            System.out.println("Orden cerrada. El equipo vuelve a OPERATIVO.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            Consola.error(e);
        }
    }

    public void marcarFueraDeServicio() {
        Consola.titulo("Marcar equipo fuera de servicio");
        try {
            String codigo = Consola.leerTexto("Codigo del equipo");
            controlador.marcarFueraDeServ(codigo);
            System.out.println("Equipo marcado como FUERA_DE_SERVICIO.");
        } catch (IllegalArgumentException e) {
            Consola.error(e);
        }
    }

    public void registrarControl() {
        Consola.titulo("Registrar control de un equipo");
        try {
            String codigo = Consola.leerTexto("Codigo del equipo");
            controlador.registrarControl(codigo);
            System.out.println("Control registrado con la fecha de hoy.");
        } catch (IllegalArgumentException e) {
            Consola.error(e);
        }
    }

    public void listarEquipos() {
        Consola.titulo("Equipos del barrio");
        List<Equipos> equipos = controlador.getEquipos();
        if (equipos.isEmpty()) {
            System.out.println("No hay equipos registrados.");
        }
        for (int i = 0; i < equipos.size(); i++) {
            Equipos e = equipos.get(i);
            String control = (e.getFechaUltimoControl() == null) ? "sin control"
                    : e.getFechaUltimoControl().format(Consola.FORMATO_FECHA);
            System.out.println(e.getCodigo() + " | " + e.saberTipo() + " | Sector: " + e.getSector().getNombre()
                    + " (" + e.getSector().getTipo() + ") | " + e.getEstado() + " | Ultimo control: " + control);
            mostrarOrdenes(e);
        }
    }

    public void reporteFueraDeServicio() {
        System.out.println();
        System.out.println(controlador.reporteFueraDeServ());
    }

    // ---------- Auxiliares privados ----------

    /** Equipos no expone la lista de ordenes, pero si buscarOrden(n): se recorre 1, 2, 3... hasta que devuelva null. */
    private void mostrarOrdenes(Equipos e) {
        int n = 1;
        while (e.buscarOrden(n) != null) {
            OrdenesDeMantenimiento o = e.buscarOrden(n);
            String estado = o.estaAbierta() ? "ABIERTA" : "cerrada";
            System.out.println("     Orden " + o.getNumero() + " | " + o.getPrioridad() + " | " + estado
                    + " | " + o.getDescripcionTrabajo());
            n = n + 1;
        }
    }

    private OrdenesDeMantenimiento ultimaOrden(Equipos e) {
        int n = 1;
        while (e.buscarOrden(n + 1) != null) {
            n = n + 1;
        }
        return e.buscarOrden(n);
    }
}
