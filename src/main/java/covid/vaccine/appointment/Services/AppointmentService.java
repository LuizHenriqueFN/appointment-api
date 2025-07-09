package covid.vaccine.appointment.Services;

import java.util.List;

import covid.vaccine.appointment.DTOs.AppointmentDTO;
import covid.vaccine.appointment.Filters.AppointmentFilter;
import covid.vaccine.appointment.Models.AppointmentLimit;
import covid.vaccine.appointment.Models.AppointmentRegistrationModel;

/**
 * Interface para os serviços de negócio relacionados a Agendamentos.
 * Define o contrato para as operações de CRUD e lógicas de negócio específicas.
 */
public interface AppointmentService {
    List<AppointmentDTO> listAllAppointments();

    AppointmentDTO getAppointmentById(Integer id);

    List<AppointmentDTO> listAppointments(AppointmentFilter appointmentFilter);

    AppointmentDTO insertAppointment(AppointmentRegistrationModel newAppointment);

    AppointmentDTO updateAppointment(Integer id, AppointmentRegistrationModel newAppointment);

    void deleteAppointment(Integer id);

    AppointmentLimit getAppointmentLimit(AppointmentLimit appointmentLimit);
}