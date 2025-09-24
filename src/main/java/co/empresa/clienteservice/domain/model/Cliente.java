package co.empresa.clienteservice.domain.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message ="No puede estar vacio")
    @Size(min=2, max=80, message="El tamaño tiene que estar entre 2 y 80")
    @Column(nullable=false)
    private String nombre;

    @Size(max = 255, message = "El correo no puede tener más de 100 caracteres")
    private String correo;

    @NotNull(message = "El telefono no puede ser nulo")
    @Min(value = 0, message = "El telefono no puede ser negativo")
    private Integer telefono;
}
