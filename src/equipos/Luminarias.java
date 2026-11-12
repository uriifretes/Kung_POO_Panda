package equipos;

import Enums.Prioridad;

import java.time.LocalDate;

public class Luminarias extends Equipos{
    private String tiposLamparas;
    private int potencia;

    public Luminarias(String codigo, LocalDate fechaInstalacion, Sectores sector, String tiposLamparas, int potencia) {
        super(codigo, fechaInstalacion, sector);
        if (tiposLamparas==null||potencia<=0){
            throw new IllegalArgumentException("Has ingresado datos invalidos");
        }
        this.tiposLamparas = tiposLamparas;
        this.potencia = potencia;
    }

    public String getTiposLamparas() {
        return tiposLamparas;
    }

    public int getPotencia() {
        return potencia;
    }

    @Override
    public String saberTipo(){
        return ("Luminaria");
    }
    @Override
    public Prioridad prioridaddeFalla(){
        if (getSector().cantidadLuminariasFallando()>3){
            return Prioridad.ALTA;
        }
        return Prioridad.MEDIA;
    }
}
