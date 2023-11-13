package seedu.pharmhub.logic.parser;

import static seedu.pharmhub.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.pharmhub.logic.parser.CliSyntax.PREFIX_MEDICINE_NAME;
import static seedu.pharmhub.logic.parser.CliSyntax.PREFIX_ORDER_NUMBER;
import static seedu.pharmhub.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.pharmhub.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.pharmhub.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.pharmhub.testutil.TypicalOrders.PANADOL_MEDICINE;

import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.pharmhub.commons.core.index.Index;
import seedu.pharmhub.logic.commands.AddOrderCommand;
import seedu.pharmhub.model.order.OrderNumber;


class AddOrderCommandParserTest {

    private static final String VALID_ORDERNUMBER = PREFIX_ORDER_NUMBER + " 1 ";
    private static final String INVALID_ORDERNUMBER_NEGATIVE = PREFIX_ORDER_NUMBER + "  -1 ";

    private static final String INVALID_ORDERNUMBER = PREFIX_ORDER_NUMBER + " asd ";

    private static final String VALID_MEDICINE_NAME = PREFIX_MEDICINE_NAME + " Panadol ";

    private static final String INVALID_MEDICINE_NAME = PREFIX_MEDICINE_NAME + " ";


    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddOrderCommand.MESSAGE_USAGE);

    private final Parser<AddOrderCommand> parser = new AddOrderCommandParser();

    @Test
    public void parse_missingParts_failure() {

        //Missing index
        assertParseFailure(parser, VALID_ORDERNUMBER + VALID_MEDICINE_NAME, MESSAGE_INVALID_FORMAT);

        //Missing orderNumber
        assertParseFailure(parser, "1 " + VALID_MEDICINE_NAME, MESSAGE_INVALID_FORMAT);

        //Missing medicineName
        assertParseFailure(parser, "1 " + VALID_MEDICINE_NAME, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5 " + VALID_ORDERNUMBER + VALID_MEDICINE_NAME, MESSAGE_INVALID_INDEX);

        // zero index
        assertParseFailure(parser, "0 " + VALID_ORDERNUMBER + VALID_MEDICINE_NAME, MESSAGE_INVALID_INDEX);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string " + VALID_ORDERNUMBER + VALID_MEDICINE_NAME,
                MESSAGE_INVALID_INDEX);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string ", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1 " + INVALID_ORDERNUMBER_NEGATIVE + VALID_MEDICINE_NAME,
                OrderNumber.MESSAGE_CONSTRAINTS); // invalid orderNumber

        assertParseFailure(parser, "1 " + VALID_ORDERNUMBER + INVALID_MEDICINE_NAME,
                            MESSAGE_INVALID_FORMAT); // invalid medicineName

        assertParseFailure(parser, "1 " + INVALID_ORDERNUMBER + VALID_MEDICINE_NAME,
                OrderNumber.MESSAGE_CONSTRAINTS); // invalid orderNumber

        AddOrderCommand expectedCommand = new AddOrderCommand(
                Index.fromOneBased(1),
                new OrderNumber("1"), new HashSet<>(List.of(PANADOL_MEDICINE)), false);



        assertParseSuccess(parser, "1 " + VALID_ORDERNUMBER + VALID_MEDICINE_NAME, expectedCommand);

    }

    @Test
    public void parse_containsIgnoreAllergy_success() {
        AddOrderCommand expectedCommand = new AddOrderCommand(
                Index.fromOneBased(1),
                new OrderNumber("1"), new HashSet<>(List.of(PANADOL_MEDICINE)), true);
        assertParseSuccess(parser, "1 " + VALID_ORDERNUMBER + VALID_MEDICINE_NAME + " ia/", expectedCommand);
    }

}
