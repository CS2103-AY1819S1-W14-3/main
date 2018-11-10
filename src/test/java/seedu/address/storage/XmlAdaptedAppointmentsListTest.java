package seedu.address.storage;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;

public class XmlAdaptedAppointmentsListTest {
    private XmlAdaptedAppointmentsList xmlAdaptedAppointmentsList;
    private XmlAdaptedAppointment xmlAdaptedAppointment;

    @Before
    public void setUp() throws IllegalValueException {
        xmlAdaptedAppointmentsList = new XmlAdaptedAppointmentsList();
        xmlAdaptedAppointment = new XmlAdaptedAppointment("SRG", "Heart Bypass",
                "12-12-2022 12:00", "Dr. Pepper");
    }

    @Test
    public void setAppointment() {
        List<XmlAdaptedAppointment> newList = new ArrayList<>();
        newList.add(xmlAdaptedAppointment);

        xmlAdaptedAppointmentsList.setAppt(newList);
        assertTrue(xmlAdaptedAppointmentsList.equals(new XmlAdaptedAppointmentsList(newList)));
    }
}
