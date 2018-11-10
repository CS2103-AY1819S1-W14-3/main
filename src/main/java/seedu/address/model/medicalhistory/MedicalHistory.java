package seedu.address.model.medicalhistory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The data structure that stores the medical diagnoses of a patient.
 */
public class MedicalHistory {
    private List<Diagnosis> medicalHistory;

    public MedicalHistory() {
        this.medicalHistory = new ArrayList<>();
    }

    public MedicalHistory(List<Diagnosis> records) {
        Objects.requireNonNull(records);
        this.medicalHistory = new ArrayList<>(records);
    }

    public MedicalHistory(MedicalHistory mh) {
        Objects.requireNonNull(mh);
        this.medicalHistory = new ArrayList<>(Objects.requireNonNull(mh.medicalHistory));
    }

    public void add(Diagnosis diagnosis) {
        this.medicalHistory.add(diagnosis);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (Diagnosis d : medicalHistory) {
            sb.append(d).append("\n");
        }

        return sb.toString().trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o instanceof MedicalHistory) {
            MedicalHistory mh = (MedicalHistory) o;
            return medicalHistory.equals(mh);
        }

        return false;
    }

    public Stream<Diagnosis> stream() {
        return medicalHistory.stream();
    }

    /**
     * Helper method to return a copy of the medical history.
     */
    public ObservableList<Diagnosis> getObservableCopyOfMedicalHistory() {
        return FXCollections.observableArrayList(new ArrayList<>(medicalHistory));
    }
}
