package com.clinicware.clinicware.controlers;
import com.clinicware.clinicware.models.patient.PatientModel;
import com.clinicware.clinicware.repositories.PatientRepository;
import com.clinicware.clinicware.services.PatientService;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.data.domain.Page;



@RestController
public class PatientController {

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    PatientService patientService;

    @PostMapping("/patient")
    public ResponseEntity<?> createPatient(@RequestBody @Valid PatientModel patient){        
        return patientService.createPatient(patient);
    }

    @GetMapping("/patients")
    /*public ResponseEntity<?>getAllPatients(){
        return patientService.getAllPatients();
    } */
   public ResponseEntity<Page<PatientModel>> getAllPatients(
            @RequestParam(defaultValue =   "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "idPatient") String sortBy) {
        
        Page<PatientModel> patientsPage = patientService.getAllPatients(page, size, sortBy);
        return ResponseEntity.status(HttpStatus.OK).body(patientsPage);
    }

    @GetMapping("patients/{patientId}")
    public ResponseEntity<?> getPatient(@PathVariable UUID patientId) {
        return patientService.selectPatient(patientId);
    }

    @PutMapping("patient")
    public ResponseEntity<?> updatePaient( @RequestBody PatientModel patient) {
        return patientService.editPatient(patient);
    }

    @DeleteMapping("patients/{patientId}")
    public ResponseEntity<?> deletePatient(@PathVariable UUID patientId){
        return patientService.deletePatient(patientId);
    }
    
}
