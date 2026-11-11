import Enums.Especialidad;
import Enums.PuestoGuardia;
import Enums.TipoResidente;
import Enums.TipoSector;
import Enums.Turno;

import java.time.LocalDate;
import java.time.YearMonth;

/** Carga datos de ejemplo usando solo los controladores, para poder probar las vistas sin cargar todo a mano. */
public class DatosDePrueba {

    public static void cargar(ControladorGeneral g) {
        g.getPersonal().registrarAdministrador(1, "Marta Gomez", LocalDate.of(2020, 3, 1));
        g.getPersonal().registrarGuardia(10, "Carlos Perez", LocalDate.of(2021, 5, 10), Turno.MANIANA, PuestoGuardia.PORTERIA);
        g.getPersonal().registrarGuardia(11, "Diego Ruiz", LocalDate.of(2022, 8, 15), Turno.NOCHE, PuestoGuardia.RONDA);
        g.getPersonal().registrarEmpleado(20, "Luis Torres", LocalDate.of(2021, 2, 1), Especialidad.ELECTRICIDAD);
        g.getPersonal().registrarEmpleado(21, "Raul Soto", LocalDate.of(2023, 1, 9), Especialidad.PLOMERIA);

        g.getLotes().registrarLote(1, 500, "Ana", "Lopez", "30111222", "1155550001", "ana@mail.com");
        g.getLotes().registrarLote(2, 800, "Bruno", "Diaz", "30333444", "1155550003", "bruno@mail.com");
        g.getLotes().registrarLote(3, 650, "Carla", "Mendez", "30555666", "1155550004", "carla@mail.com");
        g.getLotes().agregarResidente(1, "Pedro", "Lopez", "40111222", "1155550002", "pedro@mail.com", TipoResidente.FAMILIAR);
        g.getLotes().autorizarReclamos(1, "40111222", true);
        g.getLotes().habilitarVisitas(1, "40111222", true);

        g.getEquipos().registrarSector("Acceso Principal", TipoSector.ACCESO);
        g.getEquipos().registrarSector("Calle 1", TipoSector.CALLE_INTERNA);
        g.getEquipos().registrarCamara("CAM-01", "Acceso Principal", "1080p", 30);
        g.getEquipos().registrarLuminarias("LUM-01", "Calle 1", "LED", 40);

        g.getVisitas().registrarVisitante("Julia", "Ramos", "35000111", "1155550010", "julia@mail.com");
        g.getVisitas().registrarProveedor("Hugo", "Silva", "36000222", "1155550011", "hugo@mail.com", "Jardines SA");

        YearMonth hoy = YearMonth.now();
        g.getExpensas().emitirExpensas(hoy.minusMonths(2), 1500000);
        g.getExpensas().emitirExpensas(hoy.minusMonths(1), 1500000);
        g.getExpensas().emitirExpensas(hoy, 1500000);
    }
}