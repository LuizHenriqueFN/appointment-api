package covid.vaccine.appointment.Entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.io.Serializable;

/**
 * Classe base abstrata para entidades com um identificador genérico.
 * 
 * @param <T> O tipo do identificador.
 */
@MappedSuperclass
@Data
public abstract class EntityId<T extends Serializable> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private T id;
}
