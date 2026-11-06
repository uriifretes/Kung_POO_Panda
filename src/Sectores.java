import Enums.TipoSector;

import java.util.List;

public class Sectores {
    private String nombre;
    private Enums.TipoSector tipo;

    public Sectores(String nombre, TipoSector tipo) {
        if (nombre==null||tipo==null){
            throw new IllegalArgumentException("Has ingresado datos invalidos");
        }
        this.nombre = nombre;
        this.tipo = tipo;
    }
}
