package covid.vaccine.appointment.Repositories;

import covid.vaccine.appointment.DTOs.AppointmentDTO;
import covid.vaccine.appointment.Entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositório para gerenciar as operações de banco de dados para a entidade
 * Appointment.
 */
@Repository
public interface AppointmentRepository
                extends JpaRepository<Appointment, Integer>, JpaSpecificationExecutor<Appointment> {

        /**
         * Retorna uma lista de todos os agendamentos, projetados como AppointmentDTO,
         * incluindo os dados do paciente associado.
         * Utiliza 'JOIN FETCH' para carregar os dados do paciente de forma otimizada.
         *
         * @return Uma lista de AppointmentDTO.
         */
        @Query("SELECT new covid.vaccine.appointment.DTOs.AppointmentDTO(a.id, a.appointmentDate, a.appointmentTime, a.statusDescription, a.patient.id, a.patient.name) "
                        + "FROM Appointment a JOIN a.patient")
        List<AppointmentDTO> findAllAppointmentsWithPatientDTO();

        /**
         * Busca um agendamento pelo seu ID, garantindo que a entidade Patient associada
         * seja carregada na mesma consulta (JOIN FETCH).
         *
         * @param id O ID do agendamento a ser buscado.
         * @return um Optional contendo o Appointment se encontrado.
         */
        @Query("SELECT a FROM Appointment a LEFT JOIN FETCH a.patient WHERE a.id = :id")
        Optional<Appointment> findByIdWithPatient(@Param("id") Integer id);
}