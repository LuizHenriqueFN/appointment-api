package covid.vaccine.appointment.Models;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Modelo para representar os limites de agendamento em um determinado dia e
 * hora.
 */
@Data
public class AppointmentLimit {

    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private int dayLimit;
    private int timeLimit;
}