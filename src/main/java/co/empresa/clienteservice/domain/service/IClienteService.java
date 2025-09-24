package co.empresa.clienteservice.domain.service;


import co.empresa.clienteservice.domain.model.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Interface que define los métodos que se pueden realizar sobre la entidad Cliente
 */
public interface IClienteService {
    Cliente save(Cliente cliente);
    void delete(Cliente cliente);
    Optional<Cliente> findById(Long id);
    Cliente update(Cliente cliente);
    List<Cliente> findAll();
    Page<Cliente> findAll(Pageable pageable);
}
