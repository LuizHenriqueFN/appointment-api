package covid.vaccine.appointment.Repositories;

import covid.vaccine.appointment.DTOs.PatientDTO;
import covid.vaccine.appointment.Entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositório para gerenciar as operações de banco de dados para a entidade
 * Patient.
 * Fornece métodos CRUD básicos através do JpaRepository e consultas complexas
 * via JpaSpecificationExecutor.
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer>, JpaSpecificationExecutor<Patient> {

    /**
     * Busca um paciente pelo ID e o projeta diretamente para um PatientDTO.
     * Esta abordagem é otimizada para evitar o tráfego de dados desnecessários da
     * entidade completa.
     *
     * @param patientId O ID do paciente a ser buscado.
     * @return um Optional contendo o PatientDTO se encontrado.
     */
    @Query("SELECT new covid.vaccine.appointment.DTOs.PatientDTO(p.id, p.name, p.birthDate) FROM Patient p WHERE p.id = :patientId")
    Optional<PatientDTO> findPatientDTOById(@Param("patientId") int patientId);

    /**
     * Retorna uma lista de todos os pacientes, projetados como PatientDTO,
     * incluindo seus respectivos agendamentos.
     * Utiliza 'LEFT JOIN FETCH' para carregar os agendamentos de forma otimizada e
     * evitar o problema de N+1 queries.
     *
     * @return Uma lista de PatientDTO com seus agendamentos.
     */
    @Query("SELECT DISTINCT p FROM Patient p LEFT JOIN FETCH p.appointments")
    List<Patient> findAllWithAppointments();
}