package seedu.pharmhub.logic.parser;

import static seedu.pharmhub.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.pharmhub.logic.Messages.MESSAGE_ORDERS_LISTED_OVERVIEW;
import static seedu.pharmhub.logic.commands.CommandTestUtil.INVALID_MEDICINE;
import static seedu.pharmhub.logic.commands.CommandTestUtil.INVALID_MEDICINE_DESC;
import static seedu.pharmhub.logic.commands.CommandTestUtil.INVALID_SHORT_STATUS_DESC;
import static seedu.pharmhub.logic.commands.CommandTestUtil.INVALID_STATUS_DESC;
import static seedu.pharmhub.logic.commands.CommandTestUtil.MEDICINE_DESC_IBUPROFEN;
import static seedu.pharmhub.logic.commands.CommandTestUtil.MEDICINE_DESC_PANADOL;
import static seedu.pharmhub.logic.commands.CommandTestUtil.SHORT_MEDICINE_DESC_IBUPROFEN;
import static seedu.pharmhub.logic.commands.CommandTestUtil.SHORT_MEDICINE_DESC_PANADOL;
import static seedu.pharmhub.logic.commands.CommandTestUtil.STATUS_DESC_PENDING;
import static seedu.pharmhub.logic.commands.CommandTestUtil.VALID_MEDICINE_PANADOL;
import static seedu.pharmhub.logic.commands.CommandTestUtil.VALID_SHORT_STATUS_COMPLETED;
import static seedu.pharmhub.logic.commands.CommandTestUtil.VALID_STATUS_COMPLETED;
import static seedu.pharmhub.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.pharmhub.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.pharmhub.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.pharmhub.testutil.TypicalPersons.getTypicalPharmHub;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.pharmhub.logic.commands.CommandResult;
import seedu.pharmhub.logic.commands.FindOrderCommand;
import seedu.pharmhub.model.Model;
import seedu.pharmhub.model.ModelManager;
import seedu.pharmhub.model.UserPrefs;
import seedu.pharmhub.model.medicine.Medicine;
import seedu.pharmhub.model.order.Order;
import seedu.pharmhub.model.order.Status;

public class FindOrderCommandParserTest {
    private static Model model = new ModelManager(getTypicalPharmHub(), new UserPrefs());
    private static Model expectedModel = new ModelManager(getTypicalPharmHub(), new UserPrefs());

    private static final String MESSAGE_CONSTRIANT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindOrderCommand.MESSAGE_USAGE);
    private FindOrderCommandParser parser = new FindOrderCommandParser();

    @Test
    public void parse_invalidArg_throwsParseException() {
        // empty argument
        assertParseFailure(parser, "     ", MESSAGE_CONSTRIANT);

        // invalid argument type
        assertParseFailure(parser, "1", MESSAGE_CONSTRIANT);

        // invalid prefix
        assertParseFailure(parser, "n/pending", MESSAGE_CONSTRIANT);

        // no prefix
        assertParseFailure(parser, "pending", MESSAGE_CONSTRIANT);

        // extra prefix
        assertParseFailure(parser, VALID_MEDICINE_PANADOL + VALID_SHORT_STATUS_COMPLETED
                + VALID_STATUS_COMPLETED, MESSAGE_CONSTRIANT);
    }
    @Test
    public void parse_invalidStatusMedicine_throwsParseException() {
        // invalid status
        assertParseFailure(parser, INVALID_STATUS_DESC, Status.MESSAGE_CONSTRAINTS);

        // invalid short status
        assertParseFailure(parser, INVALID_SHORT_STATUS_DESC, Status.MESSAGE_CONSTRAINTS);

        // invalid status with valid medicine
        assertParseFailure(parser, INVALID_STATUS_DESC + MEDICINE_DESC_PANADOL, Status.MESSAGE_CONSTRAINTS);

        // invalid short status with valid medicine
        assertParseFailure(parser, INVALID_SHORT_STATUS_DESC
                + MEDICINE_DESC_IBUPROFEN, Status.MESSAGE_CONSTRAINTS);

        // invalid status with valid medicine type 2
        assertParseFailure(parser, INVALID_STATUS_DESC + SHORT_MEDICINE_DESC_PANADOL
                + "IBUPROFEN", Status.MESSAGE_CONSTRAINTS);

        // invalid short status with valid medicine type 2
        assertParseFailure(parser, INVALID_SHORT_STATUS_DESC + MEDICINE_DESC_PANADOL
                + "IBUPROFEN", Status.MESSAGE_CONSTRAINTS);

        // invalid short status with valid short medicine
        assertParseFailure(parser, INVALID_SHORT_STATUS_DESC
                + SHORT_MEDICINE_DESC_IBUPROFEN, Status.MESSAGE_CONSTRAINTS);
    }
    @Test
    public void parse_invalidMedicine_zeroOrderListed() {
        // invalid medicine with valid status
        // no leading and trailing whitespaces
        Set<Medicine> set = new HashSet<>();
        set.add(new Medicine(INVALID_MEDICINE));
        FindOrderCommand expectedFindCommand =
                new FindOrderCommand(new Status(Status.OrderStatus.PENDING), set);
        assertParseSuccess(parser, INVALID_MEDICINE_DESC + STATUS_DESC_PENDING, expectedFindCommand);
        String expectedMessage = String.format(MESSAGE_ORDERS_LISTED_OVERVIEW, 0);
        Predicate<Order> predicate = Order -> false;
        expectedModel.updateFilteredOrderList(predicate);
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, CommandResult.ListPanelEffects.ORDER);
        assertCommandSuccess(expectedFindCommand, model, expectedCommandResult, expectedModel);
    }
}
