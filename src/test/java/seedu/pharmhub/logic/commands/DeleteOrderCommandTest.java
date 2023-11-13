package seedu.pharmhub.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.pharmhub.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.pharmhub.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.pharmhub.logic.commands.CommandTestUtil.showOrderAtIndex;
import static seedu.pharmhub.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.pharmhub.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.pharmhub.testutil.TypicalPersons.getTypicalPharmHub;

import org.junit.jupiter.api.Test;

import seedu.pharmhub.commons.core.index.Index;
import seedu.pharmhub.logic.Messages;
import seedu.pharmhub.model.Model;
import seedu.pharmhub.model.ModelManager;
import seedu.pharmhub.model.UserPrefs;
import seedu.pharmhub.model.order.Order;



class DeleteOrderCommandTest {
    private Model model = new ModelManager(getTypicalPharmHub(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Order orderToDelete = model.getFilteredOrderList().get(INDEX_FIRST.getZeroBased());
        DeleteOrderCommand deleteOrderCommand = new DeleteOrderCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteOrderCommand.MESSAGE_DELETE_ORDER_SUCCESS,
                Messages.format(orderToDelete));
        ModelManager expectedModel = new ModelManager(model.getPharmHub(), new UserPrefs());
        expectedModel.deleteOrder(orderToDelete);
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, orderToDelete);

        assertCommandSuccess(deleteOrderCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredOrderList().size() + 1);
        DeleteOrderCommand deleteOrderCommand = new DeleteOrderCommand(outOfBoundIndex);

        assertCommandFailure(deleteOrderCommand, model, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showOrderAtIndex(model, INDEX_FIRST);

        Order orderToDelete = model.getFilteredOrderList().get(INDEX_FIRST.getZeroBased());
        DeleteOrderCommand deleteOrderCommand = new DeleteOrderCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteOrderCommand.MESSAGE_DELETE_ORDER_SUCCESS,
                Messages.format(orderToDelete));
        Model expectedModel = new ModelManager(model.getPharmHub(), new UserPrefs());
        expectedModel.deleteOrder(orderToDelete);
        showNoOrder(expectedModel);
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, orderToDelete);

        assertCommandSuccess(deleteOrderCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showOrderAtIndex(model, INDEX_FIRST);

        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of PharmHub list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getPharmHub().getOrderList().size());

        DeleteOrderCommand deleteOrderCommand = new DeleteOrderCommand(outOfBoundIndex);

        assertCommandFailure(deleteOrderCommand, model, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteOrderCommand deleteFirstOrderCommand = new DeleteOrderCommand(INDEX_FIRST);
        DeleteOrderCommand deleteSecondOrderCommand = new DeleteOrderCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(deleteFirstOrderCommand.equals(deleteFirstOrderCommand));

        // same values -> returns true
        DeleteOrderCommand deleteFirstOrderCommandCopy = new DeleteOrderCommand(INDEX_FIRST);
        assertTrue(deleteFirstOrderCommand.equals(deleteFirstOrderCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstOrderCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstOrderCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstOrderCommand.equals(deleteSecondOrderCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteOrderCommand deleteOrderCommand = new DeleteOrderCommand(targetIndex);
        String expected = DeleteOrderCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, deleteOrderCommand.toString());
    }


    /**
     * Updates {@code model}'s filtered order list to show nothing.
     */
    private void showNoOrder(Model model) {
        model.updateFilteredOrderList(p -> false);

        assertTrue(model.getFilteredOrderList().isEmpty());
    }

}
