package seedu.pharmhub.logic.parser;

import static seedu.pharmhub.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.pharmhub.commons.core.index.Index;
import seedu.pharmhub.logic.commands.DeleteMedicineCommand;
import seedu.pharmhub.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new DeleteMedicineCommand object
 */
public class DeleteMedicineCommandParser implements Parser<DeleteMedicineCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteMedicineCommand
     * and returns a DeleteMedicineCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteMedicineCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteMedicineCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteMedicineCommand.MESSAGE_USAGE), pe);
        }
    }
}
