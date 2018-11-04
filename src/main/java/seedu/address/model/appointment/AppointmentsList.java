package seedu.address.model.appointment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * List of appointments of a patient
 */
public class AppointmentsList {
    private ArrayList<Appointment> appointmentsList;

    public AppointmentsList() {
        this.appointmentsList = new ArrayList<>();
    }

    public AppointmentsList(List<Appointment> appointmentsList) {
        Objects.requireNonNull(appointmentsList);
        this.appointmentsList = new ArrayList<>(appointmentsList);
    }

    public AppointmentsList(AppointmentsList appointmentsList) {
        Objects.requireNonNull(appointmentsList);
        this.appointmentsList = new ArrayList<>(Objects.requireNonNull(appointmentsList.appointmentsList));
    }

    /**
     * Adds an appointment to a patient's list of appointments
     * @param appt the appointment to be added
     */
    public void add(Appointment appt) {
        this.appointmentsList.add(appt);
    }

    /**
     * Removes an appointment from a patient's list of appointments
     * @param appt the appointment to be removed
     */
    public void remove(Appointment appt) {
        this.appointmentsList.remove(appt);
    }

    /**
     * Checks if the appointments list contains a particular appointment
     * @param appt the appointment to check
     * @return true if the appointment is contained in the list, false otherwise
     */
    public boolean contains(Appointment appt) {
        for (Appointment a : this.appointmentsList) {
            if (a.equals(appt)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Changes an appointment in a patient's list of appointments
     * @param oldAppt the old appointment
     * @param newAppt the new appointment which includes the required changes
     */
    public void change(Appointment oldAppt, Appointment newAppt) {
        this.appointmentsList.remove(oldAppt);
        this.appointmentsList.add(newAppt);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Appointment appointment : appointmentsList) {
            sb.append(appointment.toString()).append("\n");
        }
        return sb.toString().trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof AppointmentsList) {
            AppointmentsList appointmentsList1l = (AppointmentsList) o;
            return appointmentsList.size() == appointmentsList1l.appointmentsList.size()
                    && appointmentsList.containsAll(appointmentsList1l.appointmentsList);
        }
        return false;
    }

    /** Wrapper method for List::stream */
    public Stream<Appointment> stream() {
        return appointmentsList.stream();
    }

    /**
     * Helper method to return a copy of the appointments list
     */
    public ObservableList<Appointment> getObservableCopyOfAppointmentsList() {
        return FXCollections.observableArrayList(new ArrayList<>(appointmentsList));
    }
}
