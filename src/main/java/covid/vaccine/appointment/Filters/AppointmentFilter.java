package covid.vaccine.appointment.Filters;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Filtro para pesquisa de Agendamentos.
 * Permite filtrar por dados do agendamento e também aninhar um filtro de
 * paciente.
 */
@Data
@AllArgsConstructor
public class AppointmentFilter {

    private Integer id;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String statusDescription;
    private PatientFilter patientFilter;
}