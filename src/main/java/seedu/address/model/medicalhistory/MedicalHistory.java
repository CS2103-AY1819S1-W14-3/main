package seedu.address.model.medicalhistory;

import java.util.ArrayList;
import java.util.Objects;

/**
 * The data structure that stores the medical diagnoses of a patient.
 */
public class MedicalHistory extends ArrayList {
    private ArrayList<Diagnosis> medicalRecords;

    public MedicalHistory() {
        this.medicalRecords = new ArrayList<>();
    }

    public MedicalHistory(ArrayList<Diagnosis> mr) {
        Objects.requireNonNull(mr);
        this.medicalRecords = mr;
    }

    public ArrayList<Diagnosis> getMedicalRecords() {
        return medicalRecords;
    }

    /**
     * Adds an additional diagnosis to an existing medical record.
     *
     * @param d The newest diagnosis addition.
     */
    public void addRecord(Diagnosis d) {
        this.medicalRecords.add(d);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Diagnosis d : medicalRecords) {
            sb.append(d.toString()).append("\n");
        }

        return sb.toString().trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        return (o instanceof MedicalHistory)
                && medicalRecords.equals(o); //todo check implementation here of equals
    }
}
