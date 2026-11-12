package vistas;

import controladores.ControladorReclamos;
import personal.Personal;
import reclamos.CambiosDeEstado;
import reclamos.Comentarios;
import reclamos.PedidosVecinos;
import reclamos.Reclamos;

import Enums.TipoReclamo;
import Excepciones.LimiteReclamosException;
import Excepciones.TransicionInvalidaException;

import java.util.ArrayList;
import java.util.List;

public class VistaReclamos {
    private ControladorReclamos controlador;

    public VistaReclamos(ControladorReclamos controlador) {
        this.controlador = controlador;
    }


    public void presentarReclamo(int nLote, String doc) {
        Consola.titulo("Presentar reclamo");
        try {
            TipoReclamo tipo = Consola.leerEnum("Tipo de reclamo", TipoReclamo.class);
            String descripcion = Consola.leerTexto("Descripcion");
            PedidosVecinos p = controlador.crearReclamo(nLote, doc, tipo, descripcion);
            System.out.println("Reclamo Nro " + p.getNumero() + " registrado (prioridad " + p.getPrioridad() + ").");
        } catch (IllegalArgumentException e) {
            Consola.error(e);
        }
    }

    public void presentarSolicitud(int nLote, String doc) {
        Consola.titulo("Presentar solicitud");
        try {
            TipoReclamo tipo = Consola.leerEnum("Tipo de solicitud", TipoReclamo.class);
            String descripcion = Consola.leerTexto("Descripcion");
            PedidosVecinos p = controlador.crearSolicitud(nLote, doc, tipo, descripcion);
            System.out.println("Solicitud Nro " + p.getNumero() + " registrada (prioridad " + p.getPrioridad() + ").");
        } catch (IllegalArgumentException e) {
            Consola.error(e);
        }
    }

    // Una apelacion de deuda es una solicitud de tipo ADMINISTRATIVO
    public void apelarDeuda(int nLote, String doc) {
        Consola.titulo("Apelar una deuda");
        try {
            String descripcion = Consola.leerTexto("Motivo de la apelacion");
            PedidosVecinos p = controlador.crearSolicitud(nLote, doc, TipoReclamo.ADMINISTRATIVO, descripcion);
            System.out.println("Apelacion registrada como solicitud Nro " + p.getNumero() + ".");
        } catch (IllegalArgumentException e) {
            Consola.error(e);
        }
    }

    // Consultas

    public void listarTodos() {
        Consola.titulo("Todos los reclamos y solicitudes");
        mostrarLista(controlador.getPedidos());
    }

    public void listarDeLote(int nLote) {
        Consola.titulo("Reclamos y solicitudes del lote " + nLote);
        List<PedidosVecinos> todos = controlador.getPedidos();
        List<PedidosVecinos> filtrados = new ArrayList<>();
        for (int i = 0; i < todos.size(); i++) {
            if (todos.get(i).getLote().getNumero() == nLote) {
                filtrados.add(todos.get(i));
            }
        }
        mostrarLista(filtrados);
    }

    public void listarAsignados(int legajo) {
        Consola.titulo("Pedidos asignados a usted");
        List<PedidosVecinos> todos = controlador.getPedidos();
        List<PedidosVecinos> filtrados = new ArrayList<>();
        for (int i = 0; i < todos.size(); i++) {
            Personal r = todos.get(i).getResponsable();
            if (r != null && r.getLegajo() == legajo) {
                filtrados.add(todos.get(i));
            }
        }
        mostrarLista(filtrados);
    }

    // nLote y legajo en 0 significan sin restriccion o sea lo usa el administrador
    public void verDetalle(int nLote, int legajo) {
        try {
            PedidosVecinos p = pedirPedido(nLote, legajo);
            Consola.titulo("Detalle del pedido " + p.getNumero());
            String clase = (p instanceof Reclamos) ? "Reclamo" : "Solicitud";
            System.out.println(clase + " | " + p.getTipo() + " | prioridad " + p.getPrioridad());
            System.out.println("Fecha: " + Consola.fechaHora(p.getFechaHora()) + " | Lote " + p.getLote().getNumero());
            System.out.println("Presentado por: " + Consola.nombreCompleto(p.getPresentadoPor()));
            System.out.println("Estado: " + p.getEstado() + (p.isEscalado() ? " (escalado al administrador)" : ""));
            System.out.println("Responsable: " + (p.getResponsable() == null ? "sin asignar" : p.getResponsable().getNombre()));
            System.out.println("Descripcion: " + p.getDescripcion());
            System.out.println("-- Historial --");
            List<CambiosDeEstado> historial = p.getHistorial();
            for (int i = 0; i < historial.size(); i++) {
                CambiosDeEstado c = historial.get(i);
                System.out.println(Consola.fechaHora(c.getFechaHora()) + " | " + c.getEstadoNuevo() + " | " + c.getObservacion());
            }
            System.out.println("-- Comentarios --");
            List<Comentarios> comentarios = p.getComentarios();
            if (comentarios.isEmpty()) {
                System.out.println("Sin comentarios.");
            }
            for (int i = 0; i < comentarios.size(); i++) {
                Comentarios c = comentarios.get(i);
                System.out.println(Consola.fechaHora(c.getFechaHora()) + " | " + c.getAutor() + ": " + c.getTexto());
            }
        } catch (IllegalArgumentException e) {
            Consola.error(e);
        }
    }

    // Gestion

    public void asignar() {
        Consola.titulo("Asignar pedido");
        System.out.println("(Seguridad y convivencia: guardia | Mantenimiento: empleado | Administrativo: administrador)");
        try {
            int numero = Consola.leerEntero("Numero de pedido");
            int legajo = Consola.leerEntero("Legajo del responsable");
            controlador.asignar(numero, legajo);
            System.out.println("Pedido asignado.");
        } catch (TransicionInvalidaException | LimiteReclamosException | IllegalArgumentException e) {
            Consola.error(e);
        }
    }

    public void avanzar(int legajo) {
        Consola.titulo("Avanzar estado");
        try {
            PedidosVecinos p = pedirPedido(0, legajo);
            String obs = Consola.leerTexto("Observacion");
            controlador.avanzar(p.getNumero(), obs);
            System.out.println("Nuevo estado: " + p.getEstado());
        } catch (TransicionInvalidaException | IllegalArgumentException e) {
            Consola.error(e);
        }
    }

    public void cancelar(int nLote) {
        Consola.titulo("Cancelar pedido");
        try {
            PedidosVecinos p = pedirPedido(nLote, 0);
            controlador.cancelar(p.getNumero());
            System.out.println("Pedido cancelado.");
        } catch (TransicionInvalidaException | IllegalArgumentException e) {
            Consola.error(e);
        }
    }

    public void reabrir(int nLote) {
        Consola.titulo("Reabrir reclamo");
        try {
            PedidosVecinos p = pedirPedido(nLote, 0);
            controlador.reabrir(p.getNumero());
            System.out.println("Reclamo reabierto, vuelve a EN_CURSO.");
        } catch (TransicionInvalidaException | IllegalArgumentException e) {
            Consola.error(e);
        }
    }

    public void comentar(String autor, int nLote, int legajo) {
        Consola.titulo("Agregar comentario");
        try {
            PedidosVecinos p = pedirPedido(nLote, legajo);
            String texto = Consola.leerTexto("Comentario");
            controlador.comentar(p.getNumero(), autor, texto);
            System.out.println("Comentario agregado.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            Consola.error(e);
        }
    }

    public void reportePendientes() {
        System.out.println();
        System.out.println(controlador.reportePendientes());
    }

    // Auxiliares privados

    private PedidosVecinos pedirPedido(int nLote, int legajo) {
        int numero = Consola.leerEntero("Numero de pedido");
        PedidosVecinos p = controlador.tenerPedido(numero);
        if (nLote != 0 && p.getLote().getNumero() != nLote) {
            throw new IllegalArgumentException("Ese pedido no pertenece a su lote");
        }
        if (legajo != 0 && (p.getResponsable() == null || p.getResponsable().getLegajo() != legajo)) {
            throw new IllegalArgumentException("Ese pedido no esta asignado a usted");
        }
        return p;
    }

    private void mostrarLista(List<PedidosVecinos> lista) {
        if (lista.isEmpty()) {
            System.out.println("No hay pedidos para mostrar.");
        }
        for (int i = 0; i < lista.size(); i++) {
            PedidosVecinos p = lista.get(i);
            String clase = (p instanceof Reclamos) ? "Reclamo" : "Solicitud";
            String responsable = (p.getResponsable() == null) ? "sin asignar" : p.getResponsable().getNombre();
            System.out.println("#" + p.getNumero() + " | " + clase + " | Lote " + p.getLote().getNumero()
                    + " | " + p.getTipo() + " (" + p.getPrioridad() + ") | " + p.getEstado()
                    + " | Resp: " + responsable);
        }
    }
}
