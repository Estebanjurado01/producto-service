package co.empresa.clienteservice.delivery.rest;

import co.empresa.clienteservice.domain.exception.NoHayClientesException;
import co.empresa.clienteservice.domain.exception.PaginaSinClientesException;
import co.empresa.clienteservice.domain.exception.ClienteNoEncontradoException;
import co.empresa.clienteservice.domain.exception.ValidationException;
import co.empresa.clienteservice.domain.model.Cliente;
import co.empresa.clienteservice.domain.service.IClienteService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteRestController {

    // Declaramos como final el servicio para mejorar la inmutabilidad
    private final IClienteService clienteService;

    // Constantes para los mensajes de respuesta
    private static final String MENSAJE = "mensaje";
    private static final String CLIENTE = "cliente";
    private static final String CLIENTES = "clientes";

    // Inyección de dependencia del servicio que proporciona servicios de CRUD
    public ClienteRestController(IClienteService clienteService) {
        this.clienteService = clienteService;
    }

    /**
     * Listar todos los clientes.
     */
    @GetMapping("/clientes")
    public ResponseEntity<Map<String, Object>> getClientes() {
        List<Cliente> clientes = clienteService.findAll();
        if (clientes.isEmpty()) {
            throw new NoHayClientesException();
        }
        Map<String, Object> response = new HashMap<>();
        response.put(CLIENTES, clientes);
        return ResponseEntity.ok(response);
    }

    /**
     * Listar clientes con paginación.
     */
    @GetMapping("/clientes/page/{page}")
    public ResponseEntity<Object> index(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 4);
        Page<Cliente> clientes = clienteService.findAll(pageable);
        if (clientes.isEmpty()) {
            throw new PaginaSinClientesException(page);
        }
        return ResponseEntity.ok(clientes);
    }

    /**
     * Crear un nuevo cliente pasando el objeto en el cuerpo de la petición, usando validaciones
     */
    @PostMapping("/clientes")
    public ResponseEntity<Map<String, Object>> save(@Valid @RequestBody Cliente cliente, BindingResult result) {
        if (result.hasErrors()) {
            throw new ValidationException(result);
        }
        Map<String, Object> response = new HashMap<>();
        Cliente nuevoCliente = clienteService.save(cliente);
        response.put(MENSAJE, "El cliente ha sido creado con éxito!");
        response.put(CLIENTE, nuevoCliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    /**
     * Eliminar un cliente pasando el objeto en el cuerpo de la petición.
     */
    @DeleteMapping("/clientes")
    public ResponseEntity<Map<String, Object>> delete(@RequestBody Cliente cliente) {
        clienteService.findById(cliente.getId())
            .orElseThrow(() -> new ClienteNoEncontradoException(cliente.getId()));
        clienteService.delete(cliente);
        Map<String, Object> response = new HashMap<>();
        response.put(MENSAJE, "El cliente ha sido eliminado con éxito!");
        response.put(CLIENTE, null);
        return ResponseEntity.ok(response);
    }

    /**
     * Actualizar un cliente pasando el objeto en el cuerpo de la petición.
     * @param cliente: Objeto Cliente que se va a actualizar
     */
    @PutMapping("/clientes")
    public ResponseEntity<Map<String, Object>> update(@Valid @RequestBody Cliente cliente, BindingResult result) {
        if (result.hasErrors()) {
            throw new ValidationException(result);
        }
        clienteService.findById(cliente.getId())
                .orElseThrow(() -> new ClienteNoEncontradoException(cliente.getId()));
        Map<String, Object> response = new HashMap<>();
        Cliente clienteActualizado = clienteService.update(cliente);
        response.put(MENSAJE, "El cliente ha sido actualizado con éxito!");
        response.put(CLIENTE, clienteActualizado);
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener un cliente por su ID.
     */
    @GetMapping("/clientes/{id}")
    public ResponseEntity<Map<String, Object>> findById(@PathVariable Long id) {
        Cliente cliente = clienteService.findById(id)
                .orElseThrow(() -> new ClienteNoEncontradoException(id));
        Map<String, Object> response = new HashMap<>();
        response.put(MENSAJE, "El cliente ha sido encontrado con éxito!");
        response.put(CLIENTE, cliente);
        return ResponseEntity.ok(response);
    }
}
