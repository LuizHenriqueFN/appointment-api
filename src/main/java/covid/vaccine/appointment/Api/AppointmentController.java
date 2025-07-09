package covid.vaccine.appointment.Api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import covid.vaccine.appointment.DTOs.AppointmentDTO;
import covid.vaccine.appointment.Filters.AppointmentFilter;
import covid.vaccine.appointment.Models.AppointmentLimit;
import covid.vaccine.appointment.Models.AppointmentRegistrationModel;
import covid.vaccine.appointment.Services.AppointmentService;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @GetMapping
    public ResponseEntity<List<AppointmentDTO>> listAllAppointments() {
        List<AppointmentDTO> appointments = appointmentService.listAllAppointments();
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDTO> getAppointmentById(@PathVariable Integer id) {
        AppointmentDTO appointment = appointmentService.getAppointmentById(id);
        return ResponseEntity.ok(appointment);
    }

    @PostMapping
    public ResponseEntity<AppointmentDTO> insertAppointment(
            @Valid @RequestBody AppointmentRegistrationModel newAppointment) {
        AppointmentDTO createdAppointment = appointmentService.insertAppointment(newAppointment);
        return new ResponseEntity<>(createdAppointment, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentDTO> updateAppointment(@PathVariable Integer id,
            @Valid @RequestBody AppointmentRegistrationModel appointment) {
        AppointmentDTO updatedAppointment = appointmentService.updateAppointment(id, appointment);
        return ResponseEntity.ok(updatedAppointment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Integer id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/limit")
    public ResponseEntity<AppointmentLimit> getAppointmentLimit(@RequestBody AppointmentLimit appointmentLimit) {
        AppointmentLimit limit = appointmentService.getAppointmentLimit(appointmentLimit);
        return ResponseEntity.ok(limit);
    }

    @PostMapping("/filter")
    public ResponseEntity<List<AppointmentDTO>> listAppointmentsByFilter(
            @RequestBody AppointmentFilter appointmentFilter) {
        List<AppointmentDTO> appointments = appointmentService.listAppointments(appointmentFilter);
        return ResponseEntity.ok(appointments);
    }
}