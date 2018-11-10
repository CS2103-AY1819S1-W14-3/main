package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.AddApptCommand;
import seedu.address.logic.commands.AddDietCommand;
import seedu.address.logic.commands.AddmedsCommand;
import seedu.address.logic.commands.AddmhCommand;
import seedu.address.logic.commands.CheckinCommand;
import seedu.address.logic.commands.CheckoutCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RegisterCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.VisitorinCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.diet.Diet;
import seedu.address.model.diet.DietCollection;
import seedu.address.model.medicalhistory.Diagnosis;
import seedu.address.model.medicine.Prescription;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.visitor.Visitor;
import seedu.address.testutil.AppointmentBuilder;
import seedu.address.testutil.AppointmentUtil;
import seedu.address.testutil.DiagnosisBuilder;
import seedu.address.testutil.DiagnosisUtil;
import seedu.address.testutil.DietBuilder;
import seedu.address.testutil.DietUtil;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;
import seedu.address.testutil.PrescriptionBuilder;
import seedu.address.testutil.PrescriptionUtil;
import seedu.address.testutil.VisitorBuilder;
import seedu.address.testutil.VisitorUtil;

public class AddressBookParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_addmeds() throws Exception {
        Person person = new PersonBuilder().build();
        Prescription prescription = new PrescriptionBuilder().build();
        AddmedsCommand command = (AddmedsCommand) parser.parseCommand(
                PrescriptionUtil.getAddmedsCommand(person.getNric(), prescription));
        assertEquals(new AddmedsCommand(person.getNric(), prescription), command);
    }

    @Test
    public void parseCommand_addAppt() throws Exception {
        Person person = new PersonBuilder().build();
        Appointment appt = new AppointmentBuilder().build();
        AddApptCommand command = (AddApptCommand) parser.parseCommand(
                AppointmentUtil.getAddApptCommand(person.getNric(), appt));
        assertEquals(new AddApptCommand(person.getNric(), appt), command);
    }

    @Test
    public void parseCommand_register() throws Exception {
        Person person = new PersonBuilder().build();
        RegisterCommand command = (RegisterCommand) parser.parseCommand(PersonUtil.getRegisterCommand(person));
        assertEquals(new RegisterCommand(person), command);
    }

    @Test
    public void parseCommand_checkout() throws Exception {
        Person person = new PersonBuilder().build();
        CheckoutCommand command = (CheckoutCommand) parser.parseCommand(PersonUtil.getCheckoutCommand(person));
        assertEquals(new CheckoutCommand(person.getNric()), command);
    }

    @Test
    public void parseCommand_checkin() throws Exception {
        Person person = new PersonBuilder().build();
        CheckinCommand command = (CheckinCommand) parser.parseCommand(PersonUtil.getCheckinCommand(person));
        assertEquals(new CheckinCommand(person.getNric()), command);
    }

    @Test
    public void parseCommand_adddiet() throws Exception {
        Person person = new PersonBuilder().build();
        Diet diet = new DietBuilder().build();
        Set<Diet> dietSet = new HashSet<>();
        dietSet.add(diet);
        DietCollection dietCollection = new DietCollection(dietSet);
        AddDietCommand cmd = (AddDietCommand) parser.parseCommand(DietUtil.getAddDietCommand(person.getNric(), diet));
        assertEquals(new AddDietCommand(person.getNric(), dietCollection), cmd);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_addmh() throws Exception {
        Person person = new PersonBuilder().build();
        Diagnosis diagnosis = new DiagnosisBuilder().build();
        AddmhCommand command = (AddmhCommand) parser.parseCommand(
            DiagnosisUtil.getAddmhCommand(person.getNric(), diagnosis));
        assertEquals(new AddmhCommand(person.getNric(), diagnosis), command);
    }

    @Test
    public void parseCommand_visitorin() throws Exception {
        Person person = new PersonBuilder().build();
        Visitor visitor = new VisitorBuilder().build();
        VisitorinCommand command = (VisitorinCommand) parser.parseCommand(
                VisitorUtil.getVisitorInCommand(person.getNric(), visitor));
        assertEquals(new VisitorinCommand(person.getNric(), visitor), command);
        // test that typing visitorin returns an instance of visiterinCommand
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_history() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD + " 3") instanceof HistoryCommand);

        try {
            parser.parseCommand("histories");
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_select() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        parser.parseCommand("");
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand("unknownCommand");
    }
}
