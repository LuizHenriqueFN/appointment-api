package covid.vaccine.appointment.DTOs;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO para representar os dados de um Agendamento.
 */
@Data
public class AppointmentDTO {

    private int id;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String statusDescription;

    @JsonBackReference
    private PatientDTO patient;
}