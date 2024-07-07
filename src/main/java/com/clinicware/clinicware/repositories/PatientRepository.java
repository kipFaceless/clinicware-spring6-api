package com.clinicware.clinicware.repositories;

import com.clinicware.clinicware.models.patient.PatientModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<PatientModel, UUID> {
    //int countByIdPatient(UUID id);
    PatientModel findByIdPatient(UUID id);
}
