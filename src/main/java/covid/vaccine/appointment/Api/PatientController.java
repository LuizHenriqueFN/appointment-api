package covid.vaccine.appointment.Api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import covid.vaccine.appointment.DTOs.PatientDTO;
import covid.vaccine.appointment.Filters.PatientFilter;
import covid.vaccine.appointment.Models.PatientRegistrationModel;
import covid.vaccine.appointment.Services.PatientService;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @PostMapping
    public ResponseEntity<PatientDTO> insertPatient(@Valid @RequestBody PatientRegistrationModel newPatient) {
        PatientDTO createdPatient = patientService.insertPatient(newPatient);
        return new ResponseEntity<>(createdPatient, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientDTO> updatePatient(@PathVariable Integer id,
            @Valid @RequestBody PatientRegistrationModel patient) {
        PatientDTO updatedPatient = patientService.updatePatient(id, patient);
        return ResponseEntity.ok(updatedPatient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Integer id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<PatientDTO>> listAllPatients() {
        List<PatientDTO> patients = patientService.listPatients(new PatientFilter());
        return ResponseEntity.ok(patients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> getPatientById(@PathVariable Integer id) {
        PatientDTO patient = patientService.getPatientById(id);
        return ResponseEntity.ok(patient);
    }

    @PostMapping("/filter")
    public ResponseEntity<List<PatientDTO>> listPatientsByFilter(@RequestBody PatientFilter patientFilter) {
        List<PatientDTO> patients = patientService.listPatients(patientFilter);
        return ResponseEntity.ok(patients);
    }
}