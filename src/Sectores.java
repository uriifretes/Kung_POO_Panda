import Enums.TipoSector;

import java.util.List;

public class Sectores {
    private String nombre;
    private Enums.TipoSector tipo;

    public Sectores(String nombre, TipoSector tipo) {
        this.nombre = nombre;
        this.tipo = tipo;
    }
}
