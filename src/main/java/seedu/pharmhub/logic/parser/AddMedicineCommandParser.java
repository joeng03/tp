package seedu.pharmhub.logic.parser;

import static seedu.pharmhub.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.pharmhub.logic.parser.CliSyntax.PREFIX_MEDICINE_NAME;

import seedu.pharmhub.logic.commands.AddMedicineCommand;
import seedu.pharmhub.logic.parser.exceptions.ParseException;
import seedu.pharmhub.model.medicine.Medicine;


/**
 * Parses input arguments and creates a new AddMedicineCommand object.
 */
public class AddMedicineCommandParser implements Parser<AddMedicineCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddMedicineCommand
     * and returns an AddMedicineCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddMedicineCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_MEDICINE_NAME);

        if (!argMultimap.getPreamble().isEmpty()
                || argMultimap.getValue(PREFIX_MEDICINE_NAME).orElse("").isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMedicineCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_MEDICINE_NAME);

        String medicineName = argMultimap.getValue(PREFIX_MEDICINE_NAME).get();
        if (!Medicine.isValidMedicineName(medicineName)) {
            throw new ParseException(Medicine.MESSAGE_MEDICINE_NAME_CONSTRAINTS);
        }

        return new AddMedicineCommand(new Medicine(medicineName));

    }

}
