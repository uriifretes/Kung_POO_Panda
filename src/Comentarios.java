import java.time.LocalDateTime;

public class Comentarios {
    private LocalDateTime fechaHora;
    private String autor;
    private String texto;

    public Comentarios(LocalDateTime fechaHora, String autor, String texto) {
        this.fechaHora = fechaHora;
        this.autor = autor;
        this.texto = texto;
    }
}
