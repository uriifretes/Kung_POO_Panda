package reclamos;

import java.time.LocalDateTime;

public class Comentarios {
    private LocalDateTime fechaHora;
    private String autor;
    private String texto;

    public Comentarios(LocalDateTime fechaHora, String autor, String texto) {
        if (fechaHora==null||autor==null||texto==null){
            throw new IllegalArgumentException("Has ingresado datos invalidos");
        }
        this.fechaHora = fechaHora;
        this.autor = autor;
        this.texto = texto;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public String getAutor() {
        return autor;
    }

    public String getTexto() {
        return texto;
    }
}
