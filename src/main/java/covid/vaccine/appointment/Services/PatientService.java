package covid.vaccine.appointment.Services;

import java.util.List;

import covid.vaccine.appointment.DTOs.PatientDTO;
import covid.vaccine.appointment.Filters.PatientFilter;
import covid.vaccine.appointment.Models.PatientRegistrationModel;

/**
 * Interface para os serviços de negócio relacionados a Pacientes.
 * Define o contrato para as operações de CRUD e consultas.
 */
public interface PatientService {
    PatientDTO getPatientById(Integer id);

    List<PatientDTO> listPatients(PatientFilter patientFilter);

    PatientDTO insertPatient(PatientRegistrationModel newPatient);

    PatientDTO updatePatient(Integer id, PatientRegistrationModel newPatient);

    void deletePatient(Integer id);
}