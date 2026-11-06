import java.util.*;

public class Lotes {
    private int numero;
    private double superficie;
    private Titular titular;
    private List<Residentes> residentes;
    private CuentasCorrientes cuentaCorriente;
    private List<PedidosVecinos> pedidos;

    public Lotes(int numero, double superficie, Titular titular) {
        if (numero <= 0) {
            throw new IllegalArgumentException("El numero de lote debe ser mayor a 0");
        }
        if (superficie <= 0) {
            throw new IllegalArgumentException("La superficie debe ser mayor a 0");
        }
        if (titular == null) {
            throw new IllegalArgumentException("El lote debe tener un titular");
        }
        this.numero = numero;
        this.superficie = superficie;
        this.titular = titular;
        this.residentes = new ArrayList<>();
        this.pedidos = new ArrayList<>();
        this.cuentaCorriente = new CuentasCorrientes();
    }
}
