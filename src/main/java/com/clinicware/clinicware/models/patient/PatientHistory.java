package com.clinicware.clinicware.models.patient;

public class PatientHistory {
    private String detailedMedicalHistory;
    private String knownAllergies;
    private String currentMedications;
    private String oralHealthHabits;

    public String getDetailedMedicalHistory() {
        return detailedMedicalHistory;
    }

    public void setDetailedMedicalHistory(String detailedMedicalHistory) {
        this.detailedMedicalHistory = detailedMedicalHistory;
    }

    public String getKnownAllergies() {
        return knownAllergies;
    }

    public void setKnownAllergies(String knownAllergies) {
        this.knownAllergies = knownAllergies;
    }

    public String getCurrentMedications() {
        return currentMedications;
    }

    public void setCurrentMedications(String currentMedications) {
        this.currentMedications = currentMedications;
    }

    public String getOralHealthHabits() {
        return oralHealthHabits;
    }

    public void setOralHealthHabits(String oralHealthHabits) {
        this.oralHealthHabits = oralHealthHabits;
    }
}
