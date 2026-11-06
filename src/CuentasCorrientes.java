import java.util.ArrayList;
import java.util.List;

public class CuentasCorrientes {
    private boolean moroso;
    private double saldoAFavor;
    private List<Expensas> expensas;

    public CuentasCorrientes() {
        this.moroso = false;
       this.saldoAFavor = 0;
        this.expensas = new ArrayList<>();
    }
}
