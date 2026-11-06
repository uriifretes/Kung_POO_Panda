package Excepciones;

public class SinAutorizacionException extends Exception {
    public SinAutorizacionException(String message) {
        super(message);
    }
}
