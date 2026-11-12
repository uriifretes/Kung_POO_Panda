package aplicacion;

import controladores.ControladorGeneral;

import Enums.Especialidad;
import Enums.MedioPago;
import Enums.PuestoGuardia;
import Enums.TipoReclamo;
import Enums.TipoResidente;
import Enums.TipoSector;
import Enums.Turno;
import Excepciones.LimiteReclamosException;
import Excepciones.PagoInvalidoException;
import Excepciones.TransicionInvalidaException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;

public class DatosDePrueba {

    public static void cargar(ControladorGeneral g) {
        cargarPersonal(g);
        cargarLotes(g);
        cargarEquipos(g);
        cargarVisitas(g);
        cargarPedidos(g);
        cargarExpensas(g);
    }

    private static void cargarPersonal(ControladorGeneral g) {
        g.getPersonal().registrarAdministrador(1, "Marta Gomez", LocalDate.of(2020, 3, 1));
        g.getPersonal().registrarGuardia(10, "Carlos Perez", LocalDate.of(2021, 5, 10), Turno.MANIANA, PuestoGuardia.PORTERIA);
        g.getPersonal().registrarGuardia(11, "Diego Ruiz", LocalDate.of(2022, 8, 15), Turno.NOCHE, PuestoGuardia.RONDA);
        g.getPersonal().registrarGuardia(12, "Laura Benitez", LocalDate.of(2023, 4, 3), Turno.TARDE, PuestoGuardia.PORTERIA);
        g.getPersonal().registrarEmpleado(20, "Luis Torres", LocalDate.of(2021, 2, 1), Especialidad.ELECTRICIDAD);
        g.getPersonal().registrarEmpleado(21, "Raul Soto", LocalDate.of(2023, 1, 9), Especialidad.PLOMERIA);
        g.getPersonal().registrarEmpleado(22, "Jorge Molina", LocalDate.of(2024, 6, 17), Especialidad.JARDINERIA);
    }

    private static void cargarLotes(ControladorGeneral g) {
        g.getLotes().registrarLote(1, 500, "Ana", "Lopez", "30111222", "1155550001", "ana@mail.com");
        g.getLotes().registrarLote(2, 800, "Bruno", "Diaz", "30333444", "1155550003", "bruno@mail.com");
        g.getLotes().registrarLote(3, 650, "Carla", "Mendez", "30555666", "1155550004", "carla@mail.com");
        g.getLotes().registrarLote(4, 700, "Diego", "Fernandez", "30777888", "1155550005", "diego@mail.com");
        g.getLotes().registrarLote(5, 550, "Elena", "Suarez", "30999000", "1155550006", "elena@mail.com");
        g.getLotes().registrarLote(6, 900, "Federico", "Gomez", "31111222", "1155550007", "federico@mail.com");

        g.getLotes().agregarResidente(1, "Pedro", "Lopez", "40111222", "1155550002", "pedro@mail.com", TipoResidente.FAMILIAR);
        g.getLotes().autorizarReclamos(1, "40111222", true);
        g.getLotes().habilitarVisitas(1, "40111222", true);
        g.getLotes().agregarResidente(2, "Sofia", "Diaz", "40333444", "1155550008", "sofia@mail.com", TipoResidente.INQUILINO);
        g.getLotes().agregarResidente(4, "Martin", "Fernandez", "40777888", "1155550009", "martin@mail.com", TipoResidente.FAMILIAR);
        g.getLotes().autorizarReclamos(4, "40777888", true);
    }

    private static void cargarEquipos(ControladorGeneral g) {
        g.getEquipos().registrarSector("Acceso Principal", TipoSector.ACCESO);
        g.getEquipos().registrarSector("Calle 1", TipoSector.CALLE_INTERNA);
        g.getEquipos().registrarSector("Calle 2", TipoSector.CALLE_INTERNA);
        g.getEquipos().registrarSector("Plaza Central", TipoSector.ESPACIO_COMUN);

        g.getEquipos().registrarCamara("CAM-01", "Acceso Principal", "1080p", 30);
        g.getEquipos().registrarCamara("CAM-02", "Plaza Central", "720p", 15);
        g.getEquipos().registrarLuminarias("LUM-01", "Calle 1", "LED", 40);
        g.getEquipos().registrarLuminarias("LUM-02", "Calle 1", "LED", 40);
        g.getEquipos().registrarLuminarias("LUM-03", "Calle 1", "LED", 40);
        g.getEquipos().registrarLuminarias("LUM-04", "Calle 1", "LED", 40);
        g.getEquipos().registrarLuminarias("LUM-05", "Calle 2", "LED", 40);
        g.getEquipos().registrarLuminarias("LUM-06", "Plaza Central", "Sodio", 70);

        g.getEquipos().registrarFalla("LUM-01", "No enciende", "Ana Lopez");
        g.getEquipos().registrarFalla("LUM-02", "Parpadea", "Carlos Perez");
        g.getEquipos().registrarFalla("LUM-03", "No enciende", "Diego Ruiz");

        g.getEquipos().registrarFalla("CAM-02", "Imagen borrosa", "Laura Benitez");
        g.getEquipos().registrarOrden("CAM-02", "Limpiar y ajustar el lente");

        g.getEquipos().marcarFueraDeServ("LUM-06");
    }

    private static void cargarVisitas(ControladorGeneral g) {
        LocalDate hoy = LocalDate.now();

        g.getVisitas().registrarVisitante("Julia", "Ramos", "35000111", "1155550010", "julia@mail.com");
        g.getVisitas().registrarProveedor("Hugo", "Silva", "36000222", "1155550011", "hugo@mail.com", "Jardines SA");
        g.getVisitas().registrarVisitante("Marcos", "Vega", "35000333", "1155550012", "marcos@mail.com");
        g.getVisitas().registrarProveedor("Lucia", "Paz", "36000444", "1155550013", "lucia@mail.com", "Agua Pura SRL");

        g.getVisitas().autorizacionUnica(1, "30111222", "35000111", LocalTime.of(0, 0), LocalTime.of(23, 59), hoy);
        g.getVisitas().aRecurrente(1, "40111222", "36000222", LocalTime.of(7, 0), LocalTime.of(20, 0), hoy, hoy.plusDays(29));
        g.getVisitas().autorizacionUnica(2, "30333444", "35000333", LocalTime.of(0, 0), LocalTime.of(23, 59), hoy.minusDays(1));
        g.getVisitas().aRecurrente(4, "30777888", "36000444", LocalTime.of(0, 0), LocalTime.of(23, 59), hoy, hoy.plusDays(10));
    }

    private static void cargarPedidos(ControladorGeneral g) {
        try {
            g.getReclamos().crearReclamo(1, "30111222", TipoReclamo.SEGURIDAD, "Porton del fondo abierto de noche");
            g.getReclamos().comentar(1, "Ana Lopez", "Por favor revisarlo hoy");

            g.getReclamos().crearSolicitud(1, "40111222", TipoReclamo.MANTENIMIENTO, "Cambiar la lampara del poste de la esquina");
            g.getReclamos().crearReclamo(2, "30333444", TipoReclamo.CONVIVENCIA, "Ruidos molestos despues de las 23");
            g.getReclamos().asignar(3, 10);

            g.getReclamos().crearReclamo(3, "30555666", TipoReclamo.MANTENIMIENTO, "Perdida de agua en la vereda");
            g.getReclamos().asignar(4, 21);
            g.getReclamos().avanzar(4, "El plomero empezo el arreglo");
            g.getReclamos().comentar(4, "Raul Soto", "Falta un repuesto, se termina manana");
            g.getReclamos().crearSolicitud(4, "40777888", TipoReclamo.ADMINISTRATIVO, "Pedido de copia del reglamento");
            g.getReclamos().asignar(5, 1);
            g.getReclamos().avanzar(5, "En revision");
            g.getReclamos().avanzar(5, "Se envio por mail");

            g.getReclamos().crearReclamo(5, "30999000", TipoReclamo.SEGURIDAD, "Camara de la entrada apuntando mal");
            g.getReclamos().asignar(6, 11);
            g.getReclamos().avanzar(6, "Se reviso la camara");
            g.getReclamos().avanzar(6, "Camara reorientada");
            g.getReclamos().avanzar(6, "Cerrado por el administrador");

            g.getReclamos().crearSolicitud(2, "30333444", TipoReclamo.MANTENIMIENTO, "Podar el arbol del frente");
            g.getReclamos().cancelar(7);
        } catch (TransicionInvalidaException | LimiteReclamosException e) {
            System.out.println("No se pudieron cargar todos los pedidos: " + e.getMessage());
        }
    }

    private static void cargarExpensas(ControladorGeneral g) {
        YearMonth mes = YearMonth.now();
        g.getExpensas().emitirExpensas(mes.minusMonths(4), 1400000);
        g.getExpensas().emitirExpensas(mes.minusMonths(3), 1450000);
        g.getExpensas().emitirExpensas(mes.minusMonths(2), 1500000);
        g.getExpensas().emitirExpensas(mes.minusMonths(1), 1500000);
        g.getExpensas().emitirExpensas(mes, 1550000);

        g.getExpensas().registrarAsientos(2, mes.minusMonths(4), -10000, "Descuento por error de facturacion", LocalDate.now());

        try {
            LocalDateTime ahora = LocalDateTime.now();
            g.getExpensas().registrarPago(1, 1200000, MedioPago.TRANSFERENCIA, null, ahora);
            g.getExpensas().registrarPago(2, 650000, MedioPago.EFECTIVO, null, ahora);
            g.getExpensas().registrarPago(4, 600000, MedioPago.TARJETA, "OP-1001", ahora);
            g.getExpensas().registrarPago(5, 450000, MedioPago.TRANSFERENCIA, null, ahora);
        } catch (PagoInvalidoException e) {
            System.out.println("No se pudieron cargar todos los pagos: " + e.getMessage());
        }
    }
}
