package seedu.address.model.medicalhistory;

import java.util.ArrayList;
import java.util.Objects;

/**
 * The data structure that stores the medical diagnoses of a patient.
 */
public class MedicalHistory extends ArrayList<Diagnosis> {

    public MedicalHistory() {
        super();
    }

    public MedicalHistory(ArrayList<Diagnosis> records) {
        Objects.requireNonNull(records);
        this.addAll(records);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 1; i <= this.size(); i++) {
            String recordEntry = String.format("%s | %s\n", i, this.get(i - 1));
            sb.append(recordEntry);
        }

        return sb.toString().trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        return (o instanceof MedicalHistory)
                && super.equals(o);
    }
}
