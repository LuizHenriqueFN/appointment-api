package covid.vaccine.appointment.Filters;

import lombok.Data;
import java.time.LocalDate;

/**
 * Filtro para pesquisa de Pacientes.
 * Cada campo pode ser nulo para indicar que o filtro não deve ser aplicado.
 */
@Data
public class PatientFilter {

    private Integer id;
    private String name;
    private LocalDate birthDate;
}