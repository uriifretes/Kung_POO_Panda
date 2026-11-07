import Enums.TipoResidente;

public class Residentes extends Personas {
    private TipoResidente tipo;
    private boolean autorizadoReclamos;
    private boolean puedeAutorizarVisitas;

    public Residentes(String nombre, String apellido, String documento, String telefono, String correo, TipoResidente tipo) {
        super(nombre, apellido, documento, telefono, correo);
        if (tipo==null){
            throw new IllegalArgumentException("Has ingresado datos invalidos");
        }
        this.tipo = tipo;
        this.autorizadoReclamos = false;
        this.puedeAutorizarVisitas = false;
    }

    public boolean isAutorizadoReclamos() {
        return autorizadoReclamos;
    }

    public boolean isPuedeAutorizarVisitas() {
        return puedeAutorizarVisitas;
    }

    public void setAutorizadoReclamos(boolean autorizadoReclamos) {
        this.autorizadoReclamos = autorizadoReclamos;
    }

    public void setPuedeAutorizarVisitas(boolean puedeAutorizarVisitas) {
        this.puedeAutorizarVisitas = puedeAutorizarVisitas;
    }
}
