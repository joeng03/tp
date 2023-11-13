package seedu.pharmhub.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.pharmhub.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.pharmhub.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.pharmhub.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.pharmhub.testutil.Assert.assertThrows;
import static seedu.pharmhub.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.pharmhub.testutil.TypicalOrders.ORDER_NUMBER_FIRST_ORDER;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.pharmhub.logic.commands.AddPersonCommand;
import seedu.pharmhub.logic.commands.ClearCommand;
import seedu.pharmhub.logic.commands.DeletePersonCommand;
import seedu.pharmhub.logic.commands.EditPersonCommand;
import seedu.pharmhub.logic.commands.EditPersonCommand.EditPersonDescriptor;
import seedu.pharmhub.logic.commands.ExitCommand;
import seedu.pharmhub.logic.commands.FindPersonCommand;
import seedu.pharmhub.logic.commands.HelpCommand;
import seedu.pharmhub.logic.commands.ListOrderCommand;
import seedu.pharmhub.logic.commands.ListPeopleCommand;
import seedu.pharmhub.logic.commands.ViewOrderCommand;
import seedu.pharmhub.logic.commands.ViewPersonCommand;
import seedu.pharmhub.logic.parser.exceptions.ParseException;
import seedu.pharmhub.model.person.Person;
import seedu.pharmhub.testutil.EditPersonDescriptorBuilder;
import seedu.pharmhub.testutil.PersonBuilder;
import seedu.pharmhub.testutil.PersonUtil;

public class PharmHubParserTest {

    private final PharmHubParser parser = new PharmHubParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddPersonCommand command = (AddPersonCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddPersonCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeletePersonCommand command = (DeletePersonCommand) parser.parseCommand(
                DeletePersonCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased());
        assertEquals(new DeletePersonCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditPersonCommand command = (EditPersonCommand) parser.parseCommand(EditPersonCommand.COMMAND_WORD + " "
                + INDEX_FIRST.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditPersonCommand(INDEX_FIRST, descriptor, false), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> nameKeywords = Arrays.asList("foo", "bar", "baz");
        FindPersonCommand command = (FindPersonCommand) parser.parseCommand(
                FindPersonCommand.COMMAND_WORD + " " + PREFIX_NAME
                        + nameKeywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindPersonCommand(nameKeywords), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_listPeople() throws Exception {
        assertTrue(parser.parseCommand(ListPeopleCommand.COMMAND_WORD) instanceof ListPeopleCommand);
        assertTrue(parser.parseCommand(ListPeopleCommand.COMMAND_WORD + " 3") instanceof ListPeopleCommand);
    }

    @Test
    public void parseCommand_listOrders() throws Exception {
        assertTrue(parser.parseCommand(ListOrderCommand.COMMAND_WORD) instanceof ListOrderCommand);
        assertTrue(parser.parseCommand(ListOrderCommand.COMMAND_WORD + " 3") instanceof ListOrderCommand);
    }

    @Test
    public void parseCommand_viewOrder() throws Exception {
        ViewOrderCommand command = (ViewOrderCommand) parser.parseCommand(
                ViewOrderCommand.COMMAND_WORD + " " + ORDER_NUMBER_FIRST_ORDER);
        assertEquals(new ViewOrderCommand(ORDER_NUMBER_FIRST_ORDER), command);
    }

    @Test
    public void parseCommand_viewPerson() throws Exception {
        ViewPersonCommand command = (ViewPersonCommand) parser.parseCommand(
                ViewPersonCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased());
        assertEquals(new ViewPersonCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
