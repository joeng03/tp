package seedu.pharmhub.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.pharmhub.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.pharmhub.logic.parser.CliSyntax.PREFIX_DELETE_SHORT_FORM;
import static seedu.pharmhub.logic.parser.CliSyntax.PREFIX_MEDICINE_NAME;

import seedu.pharmhub.commons.core.index.Index;
import seedu.pharmhub.logic.commands.ShortFormCommand;
import seedu.pharmhub.logic.parser.exceptions.ParseException;
import seedu.pharmhub.model.medicine.Medicine;

/**
 * Parses input arguments and creates a new AddShortFormCommand object
 */
public class ShortFormCommandParser implements Parser<ShortFormCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddShortFormCommand
     * and returns an AddShortFormCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ShortFormCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_MEDICINE_NAME, PREFIX_DELETE_SHORT_FORM);
        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShortFormCommand.MESSAGE_USAGE), pe);
        }

        if (argMultimap.getValue(PREFIX_DELETE_SHORT_FORM).isPresent()) {
            return new ShortFormCommand(index);
        }

        if (argMultimap.getValue(PREFIX_MEDICINE_NAME).orElse("").isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShortFormCommand.MESSAGE_USAGE));
        }
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_MEDICINE_NAME);
        String shortForm = argMultimap.getValue(PREFIX_MEDICINE_NAME).get().trim();
        if (!Medicine.isValidShortForm(shortForm)) {
            throw new ParseException(Medicine.MESSAGE_SHORT_FORM_CONSTRAINTS);
        }

        return new ShortFormCommand(index, new Medicine(shortForm));
    }

}
