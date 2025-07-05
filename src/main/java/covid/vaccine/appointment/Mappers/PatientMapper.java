package covid.vaccine.appointment.Mappers;

import org.mapstruct.Mapper;

import covid.vaccine.appointment.DTOs.PatientDTO;
import covid.vaccine.appointment.Entities.Patient;

import java.util.List;

/**
 * Mapper para conversão entre a entidade Patient e seus DTOs.
 */
@Mapper(componentModel = "spring", uses = { AppointmentMapper.class })
public interface PatientMapper {

    /**
     * Converte uma entidade Patient para um PatientDTO.
     *
     * @param patient A entidade a ser convertida.
     * @return O DTO correspondente.
     */
    PatientDTO toDTO(Patient patient);

    /**
     * Converte uma lista de entidades Patient para uma lista de PatientDTOs.
     *
     * @param patients A lista de entidades.
     * @return A lista de DTOs.
     */
    List<PatientDTO> toDTOList(List<Patient> patients);
}