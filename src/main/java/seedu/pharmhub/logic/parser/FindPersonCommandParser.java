package seedu.pharmhub.logic.parser;

import static seedu.pharmhub.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.pharmhub.logic.parser.CliSyntax.PREFIX_ALLERGY;
import static seedu.pharmhub.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.pharmhub.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.pharmhub.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.pharmhub.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import seedu.pharmhub.logic.commands.FindPersonCommand;
import seedu.pharmhub.logic.parser.exceptions.ParseException;
import seedu.pharmhub.model.allergy.Allergy;
import seedu.pharmhub.model.person.Email;
import seedu.pharmhub.model.person.Name;
import seedu.pharmhub.model.person.Phone;
import seedu.pharmhub.model.tag.Tag;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindPersonCommandParser implements Parser<FindPersonCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindPersonCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPersonCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_TAG, PREFIX_ALLERGY);
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_TAG, PREFIX_ALLERGY);

        Name nameToFind = null;
        List<String> nameKeywords = null;
        Phone phoneToFind = null;
        Email emailToFind = null;
        Set<Tag> tagsToFind = null;
        Set<Allergy> allergiesToFind = null;

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            nameToFind = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
            nameKeywords = Arrays.asList(nameToFind.toString().split("\\s+"));
        }

        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            phoneToFind = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        }

        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            emailToFind = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        }

        if (argMultimap.getValue(PREFIX_TAG).isPresent()) {
            String tagArg = argMultimap.getValue(PREFIX_TAG).get();
            List<String> list = Arrays.asList(tagArg.split("\\s+"));
            tagsToFind = ParserUtil.parseTags(list);
        }

        if (argMultimap.getValue(PREFIX_ALLERGY).isPresent()) {
            String allergyArg = argMultimap.getValue(PREFIX_ALLERGY).get();
            List<String> list = Arrays.asList(allergyArg.split("\\s+"));
            allergiesToFind = ParserUtil.parseAllergies(list);
        }

        if (nameToFind == null && phoneToFind == null && emailToFind == null && tagsToFind == null
                && allergiesToFind == null) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPersonCommand.MESSAGE_USAGE));
        }


        return new FindPersonCommand(nameKeywords,
                phoneToFind, emailToFind, tagsToFind, allergiesToFind
        );
    }

}
