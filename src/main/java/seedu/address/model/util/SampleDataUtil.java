package seedu.address.model.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.medicalhistory.Diagnosis;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.visitor.Visitor;
import seedu.address.model.visitor.VisitorList;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Nric("S1234567A"), new Name("Alex Yeoh"), new Phone("87438807"),
                    new Email("alexyeoh@example.com"), new Address("Blk 30 Geylang Street 29, #06-40"),
                    getTagSet("friends")),
            new Person(new Nric("S2345678B"), new Name("Bernice Yu"), new Phone("99272758"),
                    new Email("berniceyu@example.com"), new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    getTagSet("colleagues", "friends")),
            new Person(new Nric("S3456789C"), new Name("Charlotte Oliveiro"), new Phone("93210283"),
                    new Email("charlotte@example.com"), new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    getTagSet("neighbours")),
            new Person(new Nric("S4567890D"), new Name("David Li"), new Phone("91031282"),
                    new Email("lidavid@example.com"), new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    getTagSet("family")),
            new Person(new Nric("S5678901E"), new Name("Irfan Ibrahim"), new Phone("92492021"),
                    new Email("irfan@example.com"), new Address("Blk 47 Tampines Street 20, #17-35"),
                    getTagSet("classmates")),
            new Person(new Nric("S6789012F"), new Name("Roy Balakrishnan"), new Phone("92624417"),
                    new Email("royb@example.com"), new Address("Blk 45 Aljunied Street 85, #11-31"),
                    getTagSet("colleagues")) };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings).map(Tag::new).collect(Collectors.toSet());
    }

    /**
     * Returns a {@code MedicalHistory} containing an {@code ArrayList<Diagnosis>}, made from a pre-made list of
     * Diagnoses.
     */
    public static List<Diagnosis> getSampleMedicalHistory() {
        Diagnosis[] records = {
            new Diagnosis("Diagnosed with acute bronchitis, drink more water", "Dr. Zhang"),
            new Diagnosis("Patient shows symptoms of dengue fever, monitor carefully", "Dr Jeff"),
            new Diagnosis("Patient is ill", "Dr Ling")
        };
        ArrayList<Diagnosis> recordList = Arrays.stream(records)
                    .collect(Collectors.toCollection(ArrayList::new));
        return recordList;
    }
    //@@author GAO JIAXIN
    public static VisitorList getSampleVisitorList() {
        String[] visitors = {"jack", "daniel"};
        ArrayList<Visitor> vl = Arrays.stream(visitors)
                .map(Visitor::new)
                .collect(Collectors.toCollection(ArrayList::new));
        VisitorList test = new VisitorList(vl);
        return test;
    }
}
