package seedu.address.model.medicine;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

//@@author snajef
/**
 * Wrapper class to hold all prescription for a given patient.
 *
 * @author Darien Chong
 *
 */
public class PrescriptionList {
    private List<Prescription> prescriptionList;

    /**
     * Zero-arg c'tor for an empty prescription list.
     */
    public PrescriptionList() {
        prescriptionList = new ArrayList<Prescription>();
    }

    /**
     * Defensive copy c'tor.
     */
    public PrescriptionList(List<Prescription> medicineList) {
        Objects.requireNonNull(medicineList);
        this.prescriptionList = new ArrayList<>(medicineList);
    }

    /**
     * Defensive copy c'tor.
     */
    public PrescriptionList(PrescriptionList prescriptionList) {
        Objects.requireNonNull(prescriptionList);
        this.prescriptionList = new ArrayList<>(Objects.requireNonNull(prescriptionList.prescriptionList));
    }

    /**
     * Adds a prescription to the list of prescriptions.
     *
     * @param prescription The prescription to add.
     */
    public void add(Prescription prescription) {
        prescriptionList.add(prescription);
    }

    /**
     * Removes a prescription from the list of prescriptions.
     *
     * @param med The prescription to remove.
     */
    public void remove(Prescription med) {
        prescriptionList.remove(med);
    }

    /**
     * Checks if a given prescription exists in the list.
     * @param med The prescription to check against.
     * @return true iff the prescription is contained in the list, false otherwise.
     */
    public boolean contains(Prescription med) {
        for (Prescription p : prescriptionList) {
            if (p.equals(med)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Prescription m : prescriptionList) {
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
            return prescriptionList.size() == ml.prescriptionList.size()
                && prescriptionList.containsAll(ml.prescriptionList);
        }

        return false;
    }

    /** Wrapper method for List::stream */
    public Stream<Prescription> stream() {
        return prescriptionList.stream();
    }

    /**
     * Helper method to return a copy of the prescription list.
     *
     * This is a sort of compromise due to the requirements of JavaFX's TableView class,
     * which requires the backing list to be modifiable to allow sorting by column.
     * However, we don't want to allow a UI component to have modify-access to an internal of
     * the PrescriptionList class.
     * Hence, we provide an observable copy of the internal list.
     */
    public ObservableList<Prescription> getObservableCopyOfPrescriptionList() {
        return FXCollections.observableArrayList(new ArrayList<>(prescriptionList));
    }
}
