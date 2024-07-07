package com.clinicware.clinicware.controlers;
import com.clinicware.clinicware.models.FeedBackMessage;
import com.clinicware.clinicware.models.patient.PatientModel;
import com.clinicware.clinicware.repositories.PatientRepository;
import com.clinicware.clinicware.services.PatientService;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.data.domain.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;




@RestController
@CrossOrigin(origins = "*") // (origins = "4200")
public class PatientController {

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    PatientService patientService;

    @PostMapping("clinicware/api/patient")
    @Operation(summary = "Route responsible for patient registration")
    @ApiResponses(value = {
    @ApiResponse(
        responseCode = "201", 
        description = "Patient successfully registered",
        content = {
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = PatientModel.class)
            )
        }
    ),

    @ApiResponse(
        responseCode = "400", 
        description = "Invalid information",
        content = {
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = FeedBackMessage.class)
            )
        }
    )
})
    public ResponseEntity<?> createPatient(@RequestBody @Valid PatientModel patient){                
        return patientService.createPatient(patient);
    }

    @GetMapping("clinicware/api/patients")
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

    @GetMapping("clinicware/api/patients/{patientId}")
    public ResponseEntity<?> getPatient(@PathVariable UUID patientId) {
        return patientService.selectPatient(patientId);
    }

    @PutMapping("clinicware/api/patient")
    public ResponseEntity<?> updatePaient( @RequestBody PatientModel patient) {
        return patientService.editPatient(patient);
    }

    @DeleteMapping("clinicware/api/patients/{patientId}")
    public ResponseEntity<?> deletePatient(@PathVariable UUID patientId){
        return patientService.deletePatient(patientId);
    }
    
}
