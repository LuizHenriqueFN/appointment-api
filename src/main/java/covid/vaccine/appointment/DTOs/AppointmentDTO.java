package covid.vaccine.appointment.DTOs;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class AppointmentDTO {

    private int id;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String statusDescription;

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