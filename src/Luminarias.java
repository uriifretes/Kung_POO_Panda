import java.time.LocalDate;

public class Luminarias extends Equipos{
    private String tiposLamparas;
    private int potencia;

    public Luminarias(String codigo, LocalDate fechaInstalacion, Sectores sector, String tiposLamparas, int potencia) {
        super(codigo, fechaInstalacion, sector);
        this.tiposLamparas = tiposLamparas;
        this.potencia = potencia;
    }
}
