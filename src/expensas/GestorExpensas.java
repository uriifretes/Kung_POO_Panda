package expensas;

import lotes.Lotes;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public class GestorExpensas {

    public void emitirExpensas(YearMonth periodo, double presupuesto, List<Lotes> lotes){
       if (periodo==null|| presupuesto<=0||lotes.isEmpty()){
           throw new IllegalArgumentException("Se ingresaron datos invalidos");
       }
       double superficie = 0;
       for (int i = 0; i < lotes.size(); i++) {
               superficie = superficie + lotes.get(i).getSuperficie();
       }
        LocalDate vencimiento = periodo.atDay(10);
        for (int i = 0; i < lotes.size(); i++) {
            Lotes l = lotes.get(i);
            double coeficiente = l.getSuperficie() / superficie;
            double importe = presupuesto * coeficiente;
            l.getCuentaCorriente().emitirExpensa(periodo, importe, vencimiento);
        }
    }
    public boolean verificarOperacion(String numeroOp, List<Lotes> lotes){
        for (int i = 0; i < lotes.size(); i++) {
            List<Expensas> expensas = lotes.get(i).getCuentaCorriente().getExpensas();
            for (int j = 0; j < expensas.size(); j++) {
                List<Pagos> pagos = expensas.get(j).getPagos();
                for (int k = 0; k < pagos.size(); k++) {
                    if (pagos.get(k).getNumeroOperacion()!=null&&pagos.get(k).getNumeroOperacion().equals(numeroOp)){
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
