package com.clinicware.clinicware.services;
import com.clinicware.clinicware.models.FeedBackMessage;
import com.clinicware.clinicware.models.patient.PatientModel;
import com.clinicware.clinicware.repositories.PatientRepository;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    FeedBackMessage feedBackMessage;


    public ResponseEntity<?> createPatient(PatientModel patient){
        if (patient.getFirstName().isEmpty()){
            feedBackMessage.setMessage("First name is Mandatory!");
            return  new ResponseEntity<>(feedBackMessage, HttpStatus.BAD_REQUEST);           
        }else{
            return new ResponseEntity<PatientModel>(patientRepository.save(patient), HttpStatus.CREATED);
            //return ResponseEntity.status(HttpStatus.CREATED).body(patientRepository.save(patient));
        }
    }

    public ResponseEntity<?> getAllPatients(){
        return new ResponseEntity<>(patientRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<?> selectPatient(UUID idPatient){
        if(patientRepository.countByIdPatient(idPatient) == 0){
            feedBackMessage.setMessage("No valid Patient ID!");
            return new ResponseEntity<>(feedBackMessage, HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity<>(patientRepository.findById(idPatient), HttpStatus.OK);
        }
        
    }

    public ResponseEntity<?> editPatient(PatientModel patient){
        if (patientRepository.countByIdPatient(patient.getIdPatient()) == 0) {
            feedBackMessage.setMessage("Patient not found");
            return new ResponseEntity<>(feedBackMessage, HttpStatus.NOT_FOUND);
        }else if(patient.getFirstName().isEmpty()){
            feedBackMessage.setMessage("Name is mandatory!");
            return new ResponseEntity<>(feedBackMessage, HttpStatus.NOT_FOUND);
        }
        else{
            return new ResponseEntity<>(patientRepository.save(patient), HttpStatus.OK);
        }
    }

    public ResponseEntity<?> deletePatient(UUID patientId){
        if(patientRepository.countByIdPatient(patientId) == 0){
            feedBackMessage.setMessage("Patient Id not found");
            return new ResponseEntity<>(feedBackMessage, HttpStatus.NOT_FOUND);
        }else{
            PatientModel patient = patientRepository.findByIdPatient(patientId);
            patientRepository.delete(patient);
            feedBackMessage.setMessage("Patient deleted successfully.");
            return new ResponseEntity<>(feedBackMessage, HttpStatus.OK);
        }
    }
}
