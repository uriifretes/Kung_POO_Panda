public class Proveedor extends Visitantes{
    private String empresa;

    public Proveedor(String nombre, String apellido, String documento, String telefono, String correo, String empresa) {
        super(nombre, apellido, documento, telefono, correo);
        if (empresa==null){
            throw new IllegalArgumentException("Has ingresado datos invalidos");
        }
        this.empresa = empresa;
    }
}
