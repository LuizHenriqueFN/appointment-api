package covid.vaccine.appointment.Models;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Modelo para registrar um novo Agendamento.
 */
@Data
public class AppointmentRegistrationModel {

    @NotNull(message = "A data do agendamento é obrigatória.")
    @FutureOrPresent(message = "A data do agendamento não pode ser no passado.")
    private LocalDate appointmentDate;

    @NotNull(message = "A hora do agendamento é obrigatória.")
    private LocalTime appointmentTime;

    @NotBlank(message = "A descrição do status não pode estar em branco.")
    private String statusDescription;

    @NotNull(message = "O ID do paciente é obrigatório.")
    private Integer patientId;
}
