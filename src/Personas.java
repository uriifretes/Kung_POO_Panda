import java.util.List;

public class Personas {
    protected String nombre;
    protected String apellido;
    protected String documento;
    protected String telefono;
    protected String correo;

    public Personas(String nombre, String apellido, String documento, String telefono, String correo) {
        if (nombre==null||apellido==null||documento==null||telefono==null||correo==null){
            throw new IllegalArgumentException("Has ingresado datos invalidos");
        }
        this.nombre = nombre;
        this.apellido = apellido;
        this.documento = documento;
        this.telefono = telefono;
        this.correo = correo;
    }
}
