import Enums.MedioPago;

import java.time.LocalDate;

public class Pagos {
    private LocalDate fecha;
    private double importe;
    private Enums.MedioPago medio;
    private String numeroOperacion;
    private double montoInteres;
    private double montoCapital;

    public Pagos(LocalDate fecha, double importe, MedioPago medio, String numeroOperacion, double montoInteres, double montoCapital) {
        if (fecha==null||importe<=0||medio==null||numeroOperacion==null){
            throw new IllegalArgumentException("Has ingresado datos invalidos");
        }
        if (medio == MedioPago.TARJETA && (numeroOperacion == null || numeroOperacion.isEmpty())) {
            throw new IllegalArgumentException("El pago con tarjeta necesita número de operación");
        }
        this.fecha = fecha;
        this.importe = importe;
        this.medio = medio;
        this.numeroOperacion = numeroOperacion;
        this.montoInteres = montoInteres;
        this.montoCapital = montoCapital;
    }
}
