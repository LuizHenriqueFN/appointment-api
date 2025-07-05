package covid.vaccine.appointment.Models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import java.time.LocalDate;

/**
 * Modelo para registrar um novo Paciente.
 * Inclui validações para garantir a qualidade dos dados de entrada.
 */
@Data
public class PatientRegistrationModel {

    @NotBlank(message = "O nome não pode estar em branco.")
    private String name;

    @NotNull(message = "A data de nascimento é obrigatória.")
    @PastOrPresent(message = "A data de nascimento não pode ser no futuro.")
    private LocalDate birthDate;
}