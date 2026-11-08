public class GeneradorReportes {
    public String emitir(Reportes reporte){
        if(reporte == null){
            throw new IllegalArgumentException("El reporte no puede ser nulo");
        }
        return reporte.generar();
    }

}
