package covid.vaccine.appointment.Services.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import covid.vaccine.appointment.DTOs.AppointmentDTO;
import covid.vaccine.appointment.Entities.Appointment;
import covid.vaccine.appointment.Entities.Patient;
import covid.vaccine.appointment.Filters.AppointmentFilter;
import covid.vaccine.appointment.Mappers.AppointmentMapper;
import covid.vaccine.appointment.Models.AppointmentLimit;
import covid.vaccine.appointment.Models.AppointmentRegistrationModel;
import covid.vaccine.appointment.Repositories.AppointmentRepository;
import covid.vaccine.appointment.Repositories.PatientRepository;
import covid.vaccine.appointment.Repositories.Specifications.AppointmentSpecifications;
import covid.vaccine.appointment.Services.AppointmentService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final AppointmentSpecifications appointmentSpecifications;
    private final AppointmentMapper appointmentMapper;

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentDTO> listAppointments(AppointmentFilter filter) {
        log.info("Buscando agendamentos com o filtro: {}", filter);
        Specification<Appointment> spec = appointmentSpecifications.createSpecification(filter);
        List<Appointment> appointments = appointmentRepository.findAll(spec);
        return appointmentMapper.toDTOList(appointments);
    }

    @Override
    @Transactional
    public AppointmentDTO insertAppointment(AppointmentRegistrationModel newAppointment) {
        Patient patient = patientRepository.findById(newAppointment.getPatientId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Paciente não encontrado com o ID: " + newAppointment.getPatientId()));

        // Regra de negócio: Máximo 2 agendamentos por horário
        AppointmentFilter timeFilter = new AppointmentFilter(null, newAppointment.getAppointmentDate(),
                newAppointment.getAppointmentTime(), null, null);
        Specification<Appointment> timeSpec = appointmentSpecifications.createSpecification(timeFilter);
        long appointmentsInSameTime = appointmentRepository.count(timeSpec);
        if (appointmentsInSameTime >= 2) {
            log.warn("Limite de agendamentos excedido para o horário: {} {}", newAppointment.getAppointmentDate(),
                    newAppointment.getAppointmentTime());
            throw new IllegalStateException("Limite de 2 agendamentos por horário já foi atingido.");
        }

        // Regra de negócio: Máximo 20 agendamentos por dia
        AppointmentFilter dayFilter = new AppointmentFilter(null, newAppointment.getAppointmentDate(), null, null,
                null);
        Specification<Appointment> daySpec = appointmentSpecifications.createSpecification(dayFilter);
        long appointmentsInSameDay = appointmentRepository.count(daySpec);
        if (appointmentsInSameDay >= 20) {
            log.warn("Limite de agendamentos excedido para o dia: {}", newAppointment.getAppointmentDate());
            throw new IllegalStateException("Limite de 20 agendamentos por dia já foi atingido.");
        }

        Appointment appointment = new Appointment();
        appointment.setAppointmentDate(newAppointment.getAppointmentDate());
        appointment.setAppointmentTime(newAppointment.getAppointmentTime());
        appointment.setStatusDescription("Pendente");
        appointment.setCreationDate(LocalDateTime.now());
        appointment.setPatient(patient);

        Appointment savedAppointment = appointmentRepository.save(appointment);
        log.info("Agendamento inserido com sucesso. ID: {}", savedAppointment.getId());

        return appointmentMapper.toDTO(savedAppointment);
    }

    @Override
    @Transactional
    public AppointmentDTO updateAppointment(Integer id, AppointmentRegistrationModel newAppointment) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agendamento não encontrado com o ID: " + id));

        Patient patient = patientRepository.findById(newAppointment.getPatientId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Paciente não encontrado com o ID: " + newAppointment.getPatientId()));

        appointment.setAppointmentDate(newAppointment.getAppointmentDate());
        appointment.setAppointmentTime(newAppointment.getAppointmentTime());
        appointment.setStatusDescription(newAppointment.getStatusDescription());
        appointment.setPatient(patient);

        Appointment updatedAppointment = appointmentRepository.save(appointment);
        log.info("Agendamento atualizado com sucesso. ID: {}", updatedAppointment.getId());
        return appointmentMapper.toDTO(updatedAppointment);
    }

    @Override
    @Transactional
    public void deleteAppointment(Integer id) {
        if (!appointmentRepository.existsById(id)) {
            throw new EntityNotFoundException("Agendamento não encontrado com o ID: " + id);
        }
        appointmentRepository.deleteById(id);
        log.info("Agendamento deletado com sucesso. ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public AppointmentLimit getAppointmentLimit(AppointmentLimit appointmentLimit) {
        // Limite por dia
        AppointmentFilter dayFilter = new AppointmentFilter(null, appointmentLimit.getAppointmentDate(), null, null,
                null);
        Specification<Appointment> daySpec = appointmentSpecifications.createSpecification(dayFilter);
        appointmentLimit.setDayLimit((int) appointmentRepository.count(daySpec));

        // Limite por horário
        AppointmentFilter timeFilter = new AppointmentFilter(null, appointmentLimit.getAppointmentDate(),
                appointmentLimit.getAppointmentTime(), null, null);
        Specification<Appointment> timeSpec = appointmentSpecifications.createSpecification(timeFilter);
        appointmentLimit.setTimeLimit((int) appointmentRepository.count(timeSpec));

        return appointmentLimit;
    }
}