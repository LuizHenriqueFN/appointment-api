package covid.vaccine.appointment.Repositories.Specifications;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import covid.vaccine.appointment.Entities.Appointment;
import covid.vaccine.appointment.Entities.Patient;
import covid.vaccine.appointment.Filters.AppointmentFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe utilitária para construir Specifications dinâmicas para a entidade
 * Appointment.
 * Suporta filtros nos campos de Appointment e também nos campos da entidade
 * Patient associada.
 */
@Component
public class AppointmentSpecifications {

    public Specification<Appointment> createSpecification(AppointmentFilter filter) {
        return (root, _, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getAppointmentDate() != null) {
                predicates.add(criteriaBuilder.equal(root.get("appointmentDate"), filter.getAppointmentDate()));
            }
            if (filter.getAppointmentTime() != null) {
                predicates.add(criteriaBuilder.equal(root.get("appointmentTime"), filter.getAppointmentTime()));
            }
            if (filter.getStatusDescription() != null && !filter.getStatusDescription().isEmpty()) {
                predicates.add(criteriaBuilder.equal(criteriaBuilder.lower(root.get("statusDescription")),
                        filter.getStatusDescription().toLowerCase()));
            }

            if (filter.getPatientFilter() != null) {
                Join<Appointment, Patient> patientJoin = root.join("patient");

                if (filter.getPatientFilter().getId() != null) {
                    predicates.add(criteriaBuilder.equal(patientJoin.get("id"), filter.getPatientFilter().getId()));
                }
                if (filter.getPatientFilter().getName() != null && !filter.getPatientFilter().getName().isEmpty()) {
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(patientJoin.get("name")),
                            "%" + filter.getPatientFilter().getName().toLowerCase() + "%"));
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}