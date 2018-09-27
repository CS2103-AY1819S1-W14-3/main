package seedu.address.model.medicine;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

//@@author snajef
/**
 * Wrapper class to hold all medication for a given patient.
 *
 * @author Darien Chong
 *
 */
public class PrescriptionList {
    private List<Prescription> medicineList;

    public PrescriptionList() {
        medicineList = new ArrayList<Prescription>();
    }

    public PrescriptionList(List<Prescription> medicineList) {
        Objects.requireNonNull(medicineList);
        this.medicineList = new ArrayList<>(medicineList);
    }

    public PrescriptionList(PrescriptionList prescriptionList) {
        Objects.requireNonNull(prescriptionList);
        this.medicineList = new ArrayList<>(Objects.requireNonNull(prescriptionList.medicineList));
    }

    /**
     * Adds a medication to the list of medications.
     *
     * @param med
     *            The medication to add.
     */
    public void add(Prescription med) {
        medicineList.add(med);
    }

    /**
     * Removes a medication from the list of medications.
     *
     * @param med
     *            The medication to remove.
     */
    public void remove(Prescription med) {
        medicineList.remove(med);
    }

    /**
     * Checks if a given medication exists in the list.
     * @param med The prescription to check against.
     * @return true iff the prescription is contained in the list, false otherwise.
     */
    public boolean contains(Prescription med) {
        for (Prescription p : medicineList) {
            if (p.equals(med)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Prescription m : medicineList) {
            sb.append(m.toString()).append("\n");
        }

        return sb.toString().trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o instanceof PrescriptionList) {
            PrescriptionList ml = (PrescriptionList) o;
            return medicineList.size() == ml.medicineList.size()
                    && medicineList.containsAll(ml.medicineList);
        }

        return false;
    }

    /** Wrapper method for List::stream */
    public Stream<Prescription> stream() {
        return medicineList.stream();
    }

    /** Helper method to return an unmodifiable view of the medication list. */
    public ObservableList<Prescription> getReadOnlyList() {
        return FXCollections.unmodifiableObservableList(FXCollections.observableArrayList(medicineList));
    }
}
