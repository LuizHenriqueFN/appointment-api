package covid.vaccine.appointment.DTOs;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class PatientDTO {

    private int id;
    private String name;
    private LocalDate birthDate;

    @JsonManagedReference
    private List<AppointmentDTO> appointments = new ArrayList<>();

    /**
     * Construtor específico para ser usado por projeções JPQL.
     * Este construtor não inicializa a lista de agendamentos,
     * pois as queries que o utilizam focam apenas nos dados do paciente.
     *
     * @param id        O ID do paciente.
     * @param name      O nome do paciente.
     * @param birthDate A data de nascimento do paciente.
     */
    public PatientDTO(int id, String name, LocalDate birthDate) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
    }
}