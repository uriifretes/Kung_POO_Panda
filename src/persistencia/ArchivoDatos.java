package persistencia;

import controladores.ControladorGeneral;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
public class ArchivoDatos {
    private String nombreArchivo;

    public ArchivoDatos(String nombreArchivo) {
        if (nombreArchivo == null || nombreArchivo.isEmpty()) {
            throw new IllegalArgumentException("El nombre del archivo no puede estar vacio");
        }
        this.nombreArchivo = nombreArchivo;
    }

    public boolean existe() {
        File archivo = new File(nombreArchivo);
        return archivo.exists();
    }

    public void guardar(ControladorGeneral general) {
        ObjectOutputStream salida = null;
        try {
            salida = new ObjectOutputStream(new FileOutputStream(nombreArchivo));
            salida.writeObject(general);
            System.out.println("Datos guardados en " + nombreArchivo);
        } catch (IOException e) {
            System.out.println("No se pudieron guardar los datos: " + e.getMessage());
        } finally {
            if (salida != null) {
                try {
                    salida.close();
                } catch (IOException e) {
                    System.out.println("No se pudo cerrar el archivo: " + e.getMessage());
                }
            }
        }
    }
    public ControladorGeneral cargar() {
        ObjectInputStream entrada = null;
        ControladorGeneral general = null;
        try {
            entrada = new ObjectInputStream(new FileInputStream(nombreArchivo));
            general = (ControladorGeneral) entrada.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No se pudieron cargar los datos guardados: " + e.getMessage());
        } finally {
            if (entrada != null) {
                try {
                    entrada.close();
                } catch (IOException e) {
                    System.out.println("No se pudo cerrar el archivo: " + e.getMessage());
                }
            }
        }
        return general;
    }
}
