package co.empresa.clienteservice.domain.repository;

import co.empresa.clienteservice.domain.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface que hereda de JpaRepository para realizar las
 * operaciones de CRUD paginacion y ordenamiento sobre la entidad Cliente
 */
public interface IClienteRepository extends JpaRepository<Cliente, Long> {
}
