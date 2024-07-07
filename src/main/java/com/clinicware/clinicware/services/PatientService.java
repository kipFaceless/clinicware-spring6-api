package com.clinicware.clinicware.services;
import com.clinicware.clinicware.controlers.PatientController;
import com.clinicware.clinicware.models.FeedBackMessage;
import com.clinicware.clinicware.models.patient.PatientModel;
import com.clinicware.clinicware.repositories.PatientRepository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;



@Service
public class PatientService {

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    FeedBackMessage feedBackMessage;


    public ResponseEntity<?> createPatient(PatientModel patient){
        if (patient.getFirstName().isEmpty()){
            feedBackMessage.setMessage("First name is Mandatory!");
            return  new ResponseEntity<FeedBackMessage>(feedBackMessage, HttpStatus.BAD_REQUEST);           
        }else{
            return new ResponseEntity<PatientModel>(patientRepository.save(patient), HttpStatus.CREATED);
            //return ResponseEntity.status(HttpStatus.CREATED).body(patientRepository.save(patient));
        }
    }

   /* public ResponseEntity<List<PatientModel>> getAllPatients(int page, int size, String sortBy){
        //return new ResponseEntity<>(patientRepository.findAll(), HttpStatus.OK);
        
        List<PatientModel> patientsList = patientRepository.findAll();
        if(!patientsList.isEmpty()){
            for(PatientModel patient : patientsList){
                UUID id = patient.getIdPatient();
                patient.add(linkTo(methodOn(PatientController.class).getPatient(id)).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(patientsList);
    }
    */
    public Page<PatientModel> getAllPatients(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<PatientModel> patientsPage = patientRepository.findAll(pageable);

        if (patientsPage.hasContent()) {
            for (PatientModel patient : patientsPage.getContent()) {
                UUID id = patient.getIdPatient();
                patient.add(linkTo(methodOn(PatientController.class).getPatient(id)).withSelfRel());
            }
        }
        return patientsPage;
    }


    public ResponseEntity<?> selectPatient(UUID idPatient){
        Optional<PatientModel> selectedPatient = patientRepository.findById(idPatient);
        if(!selectedPatient.isPresent()){
            feedBackMessage.setMessage("No valid Patient ID!");
            return new ResponseEntity<FeedBackMessage>(feedBackMessage, HttpStatus.BAD_REQUEST);
        }
        selectedPatient.get().add(linkTo(methodOn(PatientController.class).getAllPatients(0, 10, "idPatient")).withRel("Patients List"));
        return new ResponseEntity<>(patientRepository.findById(idPatient), HttpStatus.OK);  
    }

   /* public ResponseEntity<?> editPatient(PatientModel patient){
       
        if (patientRepository.countByIdPatient(patient.getIdPatient()) == 0) {
            feedBackMessage.setMessage("Patient not found");
            return new ResponseEntity<>(feedBackMessage, HttpStatus.NOT_FOUND);
        } 

        else if(patient.getFirstName().isEmpty()){
            feedBackMessage.setMessage("Name is mandatory!");
            return new ResponseEntity<>(feedBackMessage, HttpStatus.NOT_FOUND);
        }
        else{
            return new ResponseEntity<>(patientRepository.save(patient), HttpStatus.OK);
        }
    } */

    public ResponseEntity<?> editPatient(PatientModel patient) {
        Optional<PatientModel> existingPatient = patientRepository.findById(patient.getIdPatient());
        
        if (!existingPatient.isPresent()) {
            feedBackMessage.setMessage("Patient not found");
            return new ResponseEntity<FeedBackMessage>(feedBackMessage, HttpStatus.NOT_FOUND);
        }
       
        if (!StringUtils.hasText(patient.getFirstName())) {
            feedBackMessage.setMessage("Name is mandatory!");
            return new ResponseEntity<FeedBackMessage>(feedBackMessage, HttpStatus.BAD_REQUEST);
        }
        
        //patient.setUpdatedAt(LocalDate.now());
        //LocalDate dataAtual = LocalDate.now();
        //ZonedDateTime dataHoraAtualComFuso = ZonedDateTime.now();
        //LocalDateTime dataHoraAtual = LocalDateTime.now();
        PatientModel updatedPatient = patientRepository.save(patient);
        return new ResponseEntity<PatientModel>(updatedPatient, HttpStatus.OK);
    }


    public ResponseEntity<FeedBackMessage> deletePatient(UUID patientId){
      
        Optional<PatientModel> patientToBeDeleted = patientRepository.findById(patientId);
        
        if(!patientToBeDeleted.isPresent()){
            feedBackMessage.setMessage("Patient Id not found");
            return new ResponseEntity<FeedBackMessage>(feedBackMessage, HttpStatus.NOT_FOUND);
        }
        patientRepository.delete(patientToBeDeleted.get());
        feedBackMessage.setMessage("Patient deleted successfully.");
        return new ResponseEntity<FeedBackMessage>(feedBackMessage, HttpStatus.OK);
    }
}
