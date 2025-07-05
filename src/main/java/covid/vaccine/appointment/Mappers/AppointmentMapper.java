package covid.vaccine.appointment.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import covid.vaccine.appointment.DTOs.AppointmentDTO;
import covid.vaccine.appointment.Entities.Appointment;

import java.util.List;

/**
 * Mapper para conversão entre a entidade Appointment e seus DTOs.
 * Utiliza MapStruct para gerar a implementação em tempo de compilação.
 */
@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    /**
     * Converte uma entidade Appointment para um AppointmentDTO.
     *
     * @param appointment A entidade a ser convertida.
     * @return O DTO correspondente.
     */
    @Mapping(target = "patient", source = "patient")
    AppointmentDTO toDTO(Appointment appointment);

    /**
     * Converte uma lista de entidades Appointment para uma lista de
     * AppointmentDTOs.
     *
     * @param appointments A lista de entidades.
     * @return A lista de DTOs.
     */
    List<AppointmentDTO> toDTOList(List<Appointment> appointments);
}