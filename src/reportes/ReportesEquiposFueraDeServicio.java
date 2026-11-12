package reportes;

import equipos.Equipos;

import Enums.EstadoEquipo;

import java.util.ArrayList;
import java.util.List;

public class ReportesEquiposFueraDeServicio implements Reportes {
    private List<Equipos> equipos;

    public ReportesEquiposFueraDeServicio(List<Equipos> equipos) {
        if (equipos==null){
            throw new IllegalArgumentException("Has ingresado datos invalidos");
        }
        this.equipos = new ArrayList<>(equipos);
    }
    @Override
    public String generar() {
        String texto = "REPORTE DE EQUIPOS FUERA DE SERVICIO: " +"\n";
        int cantidad = 0;
        for (int i = 0; i < equipos.size(); i++) {
            Equipos e = equipos.get(i);
            if (e.getEstado() == EstadoEquipo.FUERA_DE_SERVICIO) {
                cantidad = cantidad + 1;
                texto = texto + cantidad + "- Equipo: " + e.getCodigo() + "\n";
                texto = texto + "Tipo: " + e.saberTipo() + "\n";
                texto = texto + "Sector: " + e.getSector().getNombre() + "\n";
                texto = texto + "\n";
            }
        }

        if (cantidad == 0) {
            texto = texto + "No hay equipos fuera de servicio"+"\n";
        }
        return texto;


    }
}
