package covid.vaccine.appointment.DTOs;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO para representar os dados de um Paciente.
 */
@Data
public class PatientDTO {

    private int id;
    private String name;
    private LocalDate birthDate;

    @JsonManagedReference
    private List<AppointmentDTO> appointments = new ArrayList<>();
}