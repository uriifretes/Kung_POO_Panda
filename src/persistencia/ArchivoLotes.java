package persistencia;

import controladores.ControladorLotes;
import lotes.Lotes;
import personas.Titular;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.List;

public class ArchivoLotes {

    private final Path ruta;

    public ArchivoLotes(Path ruta) {
        if (ruta == null) {
            throw new IllegalArgumentException("La ruta no puede ser nula");
        }

        this.ruta = ruta;
    }

    public void guardar(List<Lotes> lotes) throws IOException {
        if (lotes == null) {
            throw new IllegalArgumentException("La lista no puede ser nula");
        }

        Path carpeta = ruta.toAbsolutePath().getParent();
        Files.createDirectories(carpeta);

        try (BufferedWriter escritor =
                     Files.newBufferedWriter(ruta, StandardCharsets.UTF_8)) {

            for (Lotes lote : lotes) {
                Titular titular = lote.getTitular();

                String linea =
                        lote.getNumero() + ";" +
                        lote.getSuperficie() + ";" +
                        codificar(titular.getNombre()) + ";" +
                        codificar(titular.getApellido()) + ";" +
                        codificar(titular.getDocumento()) + ";" +
                        codificar(titular.getTelefono()) + ";" +
                        codificar(titular.getCorreo());

                escritor.write(linea);
                escritor.newLine();
            }
        }
    }

    public void cargar(ControladorLotes controlador) throws IOException {
        if (controlador == null) {
            throw new IllegalArgumentException(
                    "El controlador no puede ser nulo"
            );
        }

        if (!controlador.getLotes().isEmpty()) {
            throw new IllegalStateException(
                    "Los lotes deben cargarse en un controlador vacío"
            );
        }

        if (!Files.exists(ruta)) {
            return;
        }

        try (BufferedReader lector =
                     Files.newBufferedReader(ruta, StandardCharsets.UTF_8)) {

            String linea;
            int numeroLinea = 0;

            while ((linea = lector.readLine()) != null) {
                numeroLinea++;

                if (linea.isBlank()) {
                    continue;
                }

                try {
                    String[] campos = linea.split(";", -1);

                    if (campos.length != 7) {
                        throw new IllegalArgumentException(
                                "Cantidad incorrecta de campos"
                        );
                    }

                    int numero = Integer.parseInt(campos[0]);
                    double superficie = Double.parseDouble(campos[1]);

                    String nombre = decodificar(campos[2]);
                    String apellido = decodificar(campos[3]);
                    String documento = decodificar(campos[4]);
                    String telefono = decodificar(campos[5]);
                    String correo = decodificar(campos[6]);

                    controlador.registrarLote(
                            numero,
                            superficie,
                            nombre,
                            apellido,
                            documento,
                            telefono,
                            correo
                    );

                } catch (IllegalArgumentException error) {
                    throw new IOException(
                            "Error en la línea " + numeroLinea
                                    + " del archivo de lotes",
                            error
                    );
                }
            }
        }
    }

    private String codificar(String texto) {
        byte[] bytes = texto.getBytes(StandardCharsets.UTF_8);
        return Base64.getEncoder().encodeToString(bytes);
    }

    private String decodificar(String texto) {
        byte[] bytes = Base64.getDecoder().decode(texto);
        return new String(bytes, StandardCharsets.UTF_8);
    }
}