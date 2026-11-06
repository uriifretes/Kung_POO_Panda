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
}
