package seedu.pharmhub.logic.parser;

import static seedu.pharmhub.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.pharmhub.logic.parser.CliSyntax.PREFIX_MEDICINE_NAME;
import static seedu.pharmhub.logic.parser.CliSyntax.PREFIX_STATUS;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import seedu.pharmhub.logic.commands.FindOrderCommand;
import seedu.pharmhub.logic.parser.exceptions.ParseException;
import seedu.pharmhub.model.medicine.Medicine;
import seedu.pharmhub.model.order.Status;
import seedu.pharmhub.model.order.exceptions.InvalidStatusException;

/**
 * Parses input arguments and creates a new FindOrderCommand object
 */
public class FindOrderCommandParser implements Parser<FindOrderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindOrderCommand
     * and returns a FindOrderCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindOrderCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindOrderCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_STATUS, PREFIX_MEDICINE_NAME);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindOrderCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_STATUS, PREFIX_MEDICINE_NAME);

        Status statusToFind = null;
        Set<Medicine> medicineToFind = null;

        if (argMultimap.getValue(PREFIX_STATUS).isPresent()) {
            try {
                String s = argMultimap.getValue(PREFIX_STATUS).get();
                statusToFind = ParserUtil.parseStatus(Status.shortFormToFull(s));
                assert Status.isValidOrderStatus(statusToFind.toString()) : "Status should be valid";
            } catch (InvalidStatusException se) {
                throw new ParseException(se.getMessage());
            }
        }
        if (argMultimap.getValue(PREFIX_MEDICINE_NAME).isPresent()) {
            String medicineArg = argMultimap.getValue(PREFIX_MEDICINE_NAME).get();
            List<String> list = Arrays.asList(medicineArg.split("\\s+"));
            medicineToFind = ParserUtil.parseMedicines(list);
        }

        if (statusToFind == null && medicineToFind == null) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindOrderCommand.MESSAGE_USAGE));
        }

        return new FindOrderCommand(statusToFind, medicineToFind);
    }

}
