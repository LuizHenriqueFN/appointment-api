package covid.vaccine.appointment.Services.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import covid.vaccine.appointment.DTOs.PatientDTO;
import covid.vaccine.appointment.Entities.Patient;
import covid.vaccine.appointment.Filters.PatientFilter;
import covid.vaccine.appointment.Mappers.PatientMapper;
import covid.vaccine.appointment.Models.PatientRegistrationModel;
import covid.vaccine.appointment.Repositories.PatientRepository;
import covid.vaccine.appointment.Repositories.Specifications.PatientSpecifications;
import covid.vaccine.appointment.Services.PatientService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PatientSpecifications patientSpecifications;
    private final PatientMapper patientMapper;

    @Override
    @Transactional(readOnly = true)
    public PatientDTO getPatientById(Integer id) {
        return patientRepository.findById(id)
                .map(patientMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Paciente não encontrado com o ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PatientDTO> listPatients(PatientFilter filter) {
        log.info("Buscando pacientes com o filtro: {}", filter);
        Specification<Patient> spec = patientSpecifications.createSpecification(filter);
        List<Patient> patients = patientRepository.findAll(spec);
        return patientMapper.toDTOList(patients);
    }

    @Override
    @Transactional
    public PatientDTO insertPatient(PatientRegistrationModel newPatient) {
        // Verifica se já existe um paciente com o mesmo nome e data de nascimento
        PatientFilter filter = new PatientFilter(null, newPatient.getName(), newPatient.getBirthDate());
        Specification<Patient> spec = patientSpecifications.createSpecification(filter);
        if (patientRepository.exists(spec)) {
            log.warn("Tentativa de inserir paciente duplicado com nome: {}", newPatient.getName());
            throw new IllegalArgumentException("Já existe um paciente com este nome e data de nascimento.");
        }

        Patient patient = new Patient();
        patient.setName(newPatient.getName());
        patient.setBirthDate(newPatient.getBirthDate());
        patient.setCreationDate(LocalDateTime.now());

        Patient savedPatient = patientRepository.save(patient);
        log.info("Paciente inserido com sucesso. ID: {}", savedPatient.getId());
        return patientMapper.toDTO(savedPatient);
    }

    @Override
    @Transactional
    public PatientDTO updatePatient(Integer id, PatientRegistrationModel newPatient) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Paciente não encontrado com o ID: " + id));

        patient.setName(newPatient.getName());
        patient.setBirthDate(newPatient.getBirthDate());

        Patient updatedPatient = patientRepository.save(patient);
        log.info("Paciente atualizado com sucesso. ID: {}", updatedPatient.getId());
        return patientMapper.toDTO(updatedPatient);
    }

    @Override
    @Transactional
    public void deletePatient(Integer id) {
        if (!patientRepository.existsById(id)) {
            throw new EntityNotFoundException("Paciente não encontrado com o ID: " + id);
        }
        patientRepository.deleteById(id);
        log.info("Paciente deletado com sucesso. ID: {}", id);
    }
}