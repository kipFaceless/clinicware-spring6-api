package com.clinicware.clinicware.services;
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

    public ResponseEntity<Object> getAllPatients(){
        return new ResponseEntity<>(patientRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<?> selectPatient(UUID idPatient){
        Optional<PatientModel> selectedPatient = patientRepository.findById(idPatient);
        if(!selectedPatient.isPresent()){
            feedBackMessage.setMessage("No valid Patient ID!");
            return new ResponseEntity<FeedBackMessage>(feedBackMessage, HttpStatus.BAD_REQUEST);
        }

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
