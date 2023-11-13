package seedu.pharmhub.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.pharmhub.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.pharmhub.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.pharmhub.testutil.TypicalOrders.ORDER_NUMBER_FIRST_ORDER;
import static seedu.pharmhub.testutil.TypicalOrders.ORDER_NUMBER_SECOND_ORDER;
import static seedu.pharmhub.testutil.TypicalPersons.getTypicalPharmHub;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.pharmhub.model.Model;
import seedu.pharmhub.model.ModelManager;
import seedu.pharmhub.model.UserPrefs;
import seedu.pharmhub.model.order.Order;

class ViewOrderCommandTest {
    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalPharmHub(), new UserPrefs());
    }

    @Test
    public void execute_validOrderNumber_orderIsFound() {
        ViewOrderCommand command = new ViewOrderCommand(ORDER_NUMBER_FIRST_ORDER);
        Order expectedOrder = model.getPharmHub().getOrder(ORDER_NUMBER_FIRST_ORDER).get();
        CommandResult expectedCommandResult = new CommandResult(
                String.format(ViewOrderCommand.MESSAGE_VIEW_ORDER_SUCCESS, ORDER_NUMBER_FIRST_ORDER),
                expectedOrder);
        assertCommandSuccess(command, model, expectedCommandResult, model);
    }

    @Test
    public void execute_nonExistentOrderNumber_throwsCommandException() {
        String orderNumber = "51920341";
        ViewOrderCommand command = new ViewOrderCommand(orderNumber);
        assertCommandFailure(command, model, String.format(ViewOrderCommand.MESSAGE_ORDER_NOT_FOUND, orderNumber));
    }

    @Test
    public void equals() {
        ViewOrderCommand viewFirstOrderCommand = new ViewOrderCommand(ORDER_NUMBER_FIRST_ORDER);
        ViewOrderCommand viewSecondOrderCommand = new ViewOrderCommand(ORDER_NUMBER_SECOND_ORDER);

        // same object -> returns true
        assertTrue(viewFirstOrderCommand.equals(viewFirstOrderCommand));

        // same values -> returns true
        ViewOrderCommand viewFirstOrderCommandCopy = new ViewOrderCommand(ORDER_NUMBER_FIRST_ORDER);
        assertTrue(viewFirstOrderCommand.equals(viewFirstOrderCommandCopy));

        // different types -> returns false
        assertFalse(viewFirstOrderCommand.equals(1));

        // null -> returns false
        assertFalse(viewFirstOrderCommand.equals(null));

        // different person -> returns false
        assertFalse(viewFirstOrderCommand.equals(viewSecondOrderCommand));
    }

    @Test
    public void toStringMethod() {
        String orderNumber = "54321";
        ViewOrderCommand viewOrderCommand = new ViewOrderCommand(orderNumber);
        String expected = ViewOrderCommand.class.getCanonicalName() + "{orderNumber=" + orderNumber + "}";
        assertEquals(expected, viewOrderCommand.toString());
    }

}
