package seedu.address.storage;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;

public class XmlAdaptedPrescriptionListTest {
    private XmlAdaptedPrescriptionList xmlAdaptedPrescriptionList;
    private XmlAdaptedPrescription xmlAdaptedPrescription;

    @Before
    public void setUp() throws IllegalValueException {
        xmlAdaptedPrescriptionList = new XmlAdaptedPrescriptionList();
        xmlAdaptedPrescription = new XmlAdaptedPrescription("Paracetamol", 2,
                "tablets", 4, "01-01-1990", "02-01-1990", 8.64 * Math.pow(10, 7));
    }

    @Test
    public void setPrescription() {
        List<XmlAdaptedPrescription> newList = new ArrayList<>();
        newList.add(xmlAdaptedPrescription);

        xmlAdaptedPrescriptionList.setPrescription(newList);
        assertTrue(xmlAdaptedPrescriptionList.equals(new XmlAdaptedPrescriptionList(newList)));
    }
}
