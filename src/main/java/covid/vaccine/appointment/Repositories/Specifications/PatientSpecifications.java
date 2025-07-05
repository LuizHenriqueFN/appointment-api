package covid.vaccine.appointment.Repositories.Specifications;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import covid.vaccine.appointment.Entities.Patient;
import covid.vaccine.appointment.Filters.PatientFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe utilitária para construir Specifications dinâmicas para a entidade
 * Patient.
 * Permite a criação de consultas baseadas em múltiplos critérios de filtro de
 * forma segura e moderna.
 */
@Component
public class PatientSpecifications {

    public Specification<Patient> createSpecification(PatientFilter filter) {
        return (root, _, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getName() != null && !filter.getName().isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
                        "%" + filter.getName().toLowerCase() + "%"));
            }
            if (filter.getBirthDate() != null) {
                predicates.add(criteriaBuilder.equal(root.get("birthDate"), filter.getBirthDate()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}