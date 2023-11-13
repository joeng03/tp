package seedu.pharmhub.logic.parser;

import static seedu.pharmhub.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.pharmhub.logic.parser.CliSyntax.PREFIX_ALLERGY;
import static seedu.pharmhub.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.pharmhub.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.pharmhub.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.pharmhub.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.pharmhub.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.pharmhub.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.pharmhub.logic.commands.FindPersonCommand;
import seedu.pharmhub.model.allergy.Allergy;
import seedu.pharmhub.model.medicine.Medicine;
import seedu.pharmhub.model.person.Email;
import seedu.pharmhub.model.person.Phone;
import seedu.pharmhub.model.tag.Tag;

public class FindPersonCommandParserTest {

    private FindPersonCommandParser parser = new FindPersonCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPersonCommand.MESSAGE_USAGE));
    }
    @Test
    public void parse_validArgsForName_returnsFindCommand() {
        List<String> nameKeywords = List.of("Alice", "Bob");

        // no leading and trailing whitespaces

        FindPersonCommand expectedFindCommand =
                new FindPersonCommand(nameKeywords);

        assertParseSuccess(parser, " " + PREFIX_NAME + "Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " "
                + PREFIX_NAME + "\n Alice \n \t Bob  \t", expectedFindCommand);
    }


    @Test
    public void parse_validArgsForNameAndOtherAttributes_returnsFindCommand() {
        List<String> nameKeywords = List.of("Alice", "Bob");
        Phone phoneToFind = new Phone("123456");
        Email emailToFind = new Email("rachel@example.com");
        Set<Tag> tagsToFind = new HashSet<>(
                Arrays.asList(
                        new Tag("friends"),
                        new Tag("owesMoney")));

        Set<Allergy> allergiesToFind = new HashSet<>(
                Arrays.asList(
                        new Allergy(new Medicine("Aspirin")),
                        new Allergy(new Medicine("Penicillin"))));


        FindPersonCommand expectedFindCommand =
                new FindPersonCommand(nameKeywords,
                        phoneToFind,
                        emailToFind,
                        tagsToFind,
                        allergiesToFind);

        assertParseSuccess(parser, " " + PREFIX_NAME + "Alice Bob"
                        + " " + PREFIX_PHONE + "123456"
                        + " " + PREFIX_EMAIL + "rachel@example.com"
                        + " " + PREFIX_TAG + "friends owesMoney"
                        + " " + PREFIX_ALLERGY + "Aspirin Penicillin",
                expectedFindCommand);
    }

    @Test
    public void parse_validArgsExcludingName_returnsFindCommand() {
        List<String> nameKeywords = null;
        Phone phoneToFind = new Phone("123456");
        Email emailToFind = new Email("rachel@example.com");
        Set<Tag> tagsToFind = new HashSet<>(
                Arrays.asList(
                        new Tag("friends"),
                        new Tag("owesMoney")));

        Set<Allergy> allergiesToFind = new HashSet<>(
                Arrays.asList(
                        new Allergy(new Medicine("Aspirin")),
                        new Allergy(new Medicine("Penicillin"))));

        FindPersonCommand expectedFindCommand =
                new FindPersonCommand(nameKeywords,
                        phoneToFind,
                        emailToFind,
                        tagsToFind,
                        allergiesToFind);

        assertParseSuccess(parser, " " + PREFIX_PHONE + "123456"
                        + " " + PREFIX_EMAIL + "rachel@example.com"
                        + " " + PREFIX_TAG + "friends owesMoney"
                        + " " + PREFIX_ALLERGY + "Aspirin Penicillin",
                expectedFindCommand);
    }
}
