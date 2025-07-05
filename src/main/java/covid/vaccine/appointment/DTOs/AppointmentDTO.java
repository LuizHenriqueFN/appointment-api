package covid.vaccine.appointment.DTOs;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
public class AppointmentDTO {

    private int id;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String statusDescription;

    @JsonBackReference
    private PatientDTO patient;

    // Construtor específico para a projeção do Spring Data JPA
    public AppointmentDTO(int id, LocalDate appointmentDate, LocalTime appointmentTime, String statusDescription,
            int patientId, String patientName) {
        this.id = id;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.statusDescription = statusDescription;

        this.patient = new PatientDTO();
        this.patient.setId(patientId);
        this.patient.setName(patientName);
    }
}