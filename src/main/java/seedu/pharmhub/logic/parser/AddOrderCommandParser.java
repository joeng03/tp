package seedu.pharmhub.logic.parser;

import static seedu.pharmhub.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.pharmhub.logic.parser.CliSyntax.PREFIX_IGNORE_ALLERGY;
import static seedu.pharmhub.logic.parser.CliSyntax.PREFIX_MEDICINE_NAME;
import static seedu.pharmhub.logic.parser.CliSyntax.PREFIX_ORDER_NUMBER;

import java.util.Set;

import seedu.pharmhub.commons.core.index.Index;
import seedu.pharmhub.logic.commands.AddOrderCommand;
import seedu.pharmhub.logic.parser.exceptions.ParseException;
import seedu.pharmhub.model.medicine.Medicine;
import seedu.pharmhub.model.order.OrderNumber;


/**
 * Parses input arguments and creates a new AddOrderCommand object.
 */
public class AddOrderCommandParser implements Parser<AddOrderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddOrderCommand
     * and returns an AddOrderCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddOrderCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ORDER_NUMBER, PREFIX_MEDICINE_NAME, PREFIX_IGNORE_ALLERGY);

        if (argMultimap.getPreamble().isEmpty()
            || argMultimap.getValue(PREFIX_MEDICINE_NAME).orElse("").isEmpty()
            || argMultimap.getValue(PREFIX_ORDER_NUMBER).orElse("").isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddOrderCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_ORDER_NUMBER);
        Index index = ParserUtil.parseIndex(argMultimap.getPreamble());
        OrderNumber orderNumber = ParserUtil.parseOrderNumber(argMultimap.getValue(PREFIX_ORDER_NUMBER).get());
        Set<Medicine> medicines = ParserUtil.parseMedicines(argMultimap.getAllValues(PREFIX_MEDICINE_NAME));

        Boolean ignoreAllergy = false;
        if (argMultimap.getValue(PREFIX_IGNORE_ALLERGY).isPresent()) {
            ignoreAllergy = true;
        }

        return new AddOrderCommand(index, orderNumber, medicines, ignoreAllergy);
    }

}
