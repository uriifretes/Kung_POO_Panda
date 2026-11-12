package equipos;

import Enums.TipoSector;

import java.util.ArrayList;
import java.util.List;

public class Sectores {
    private String nombre;
    private Enums.TipoSector tipo;
    private List<Equipos> equipos;

    public Sectores(String nombre, TipoSector tipo) {
        if (nombre==null||tipo==null){
            throw new IllegalArgumentException("Has ingresado datos invalidos");
        }
        this.nombre = nombre;
        this.tipo = tipo;
        this.equipos = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public TipoSector getTipo() {
        return tipo;
    }

    public List<Equipos> getEquipos() {
        List<Equipos> devuelvo = new ArrayList<>();
        for (int i = 0; i < equipos.size(); i++) {
            devuelvo.add(equipos.get(i));
        }

        return devuelvo;
    }

    public void agregarEquipos(Equipos e){
        if (e==null){
            throw new IllegalArgumentException("El equipo no debe ser null");
        }
        else {
            equipos.add(e);
        }
    }

    public int cantidadLuminariasFallando(){
        int cant = 0;
        for (int i = 0; i < equipos.size(); i++) {
            if (equipos.get(i).saberTipo().equals("Luminaria")&& equipos.get(i).estaFallando()){
                cant = cant+1;
            }
        }
        return cant;
    }


}
