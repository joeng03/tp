package seedu.pharmhub.logic.parser;

import static seedu.pharmhub.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.pharmhub.logic.parser.CliSyntax.PREFIX_MEDICINE_NAME;
import static seedu.pharmhub.logic.parser.CliSyntax.PREFIX_ORDER_NUMBER;
import static seedu.pharmhub.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.pharmhub.logic.commands.AddMedicineCommand;


public class AddMedicineCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMedicineCommand.MESSAGE_USAGE);


    private final Parser<AddMedicineCommand> parser = new AddMedicineCommandParser();

    @Test
    public void parse_missingParts_failure() {


        //Missing medicineName
        assertParseFailure(parser, " ", MESSAGE_INVALID_FORMAT);

        //Wrong prefix
        assertParseFailure(parser, PREFIX_ORDER_NUMBER + " panadol" , MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_preamblePresent_failure() {
        //preamble present
        assertParseFailure(parser, "1 " + PREFIX_MEDICINE_NAME + " panadol" , MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_multipleValue_failure() {
        //empty medicine name
        assertParseFailure(parser, PREFIX_MEDICINE_NAME + "panadol" + PREFIX_MEDICINE_NAME + "panadol",
                MESSAGE_INVALID_FORMAT);
    }


}
