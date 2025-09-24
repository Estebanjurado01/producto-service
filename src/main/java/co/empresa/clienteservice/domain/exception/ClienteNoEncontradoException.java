package co.empresa.clienteservice.domain.exception;

public class ClienteNoEncontradoException extends RuntimeException {
    public ClienteNoEncontradoException(Long id) {
        super("El cliente con ID " + id + " no fue encontrado.");
    }
}