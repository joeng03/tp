package seedu.pharmhub.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.pharmhub.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.pharmhub.logic.parser.CliSyntax.PREFIX_STATUS;

import seedu.pharmhub.commons.core.index.Index;
import seedu.pharmhub.logic.commands.UpdateStatusCommand;
import seedu.pharmhub.logic.commands.UpdateStatusCommand.EditOrderDescriptor;
import seedu.pharmhub.logic.parser.exceptions.ParseException;
import seedu.pharmhub.model.order.Status;
import seedu.pharmhub.model.order.exceptions.InvalidStatusException;

/**
 * Parses input arguments and creates a new UpdateStatusCommand object
 */
public class UpdateStatusCommandParser implements Parser<UpdateStatusCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UpdateStatusCommand
     * and returns an UpdateStatusCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UpdateStatusCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_STATUS);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateStatusCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_STATUS);

        EditOrderDescriptor editOrderDescriptor = new EditOrderDescriptor();

        if (argMultimap.getValue(PREFIX_STATUS).isPresent()) {
            String s = argMultimap.getValue(PREFIX_STATUS).get();
            try {
                String inputStatus = Status.shortFormToFull(s);
                editOrderDescriptor.setStatus(ParserUtil.parseStatus(inputStatus));
                assert Status.isValidOrderStatus(inputStatus) : "Status should be valid";
            } catch (InvalidStatusException se) {
                throw new ParseException(se.getMessage());
            }
        }

        if (!editOrderDescriptor.isAnyFieldEdited()) {
            throw new ParseException(UpdateStatusCommand.MESSAGE_NOT_EDITED);
        }

        return new UpdateStatusCommand(index, editOrderDescriptor);
    }
}
