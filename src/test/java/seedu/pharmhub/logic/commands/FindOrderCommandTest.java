package seedu.pharmhub.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.pharmhub.logic.Messages.MESSAGE_ORDERS_LISTED_OVERVIEW;
import static seedu.pharmhub.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.pharmhub.testutil.TypicalOrders.ASPIRIN_MEDICINE;
import static seedu.pharmhub.testutil.TypicalOrders.BENSON_PANADOL_ORDER;
import static seedu.pharmhub.testutil.TypicalOrders.CARL_PANADOL_ORDER;
import static seedu.pharmhub.testutil.TypicalOrders.PANADOL_MEDICINE;
import static seedu.pharmhub.testutil.TypicalOrders.PARACETAMOL_MEDICINE;
import static seedu.pharmhub.testutil.TypicalOrders.STATUS_COMPLETED;
import static seedu.pharmhub.testutil.TypicalOrders.STATUS_PENDING;
import static seedu.pharmhub.testutil.TypicalPersons.getTypicalPharmHub;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.pharmhub.model.Model;
import seedu.pharmhub.model.ModelManager;
import seedu.pharmhub.model.UserPrefs;
import seedu.pharmhub.model.medicine.Medicine;
import seedu.pharmhub.model.order.Order;
import seedu.pharmhub.model.order.Status;


/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindOrderCommandTest {
    private Model model = new ModelManager(getTypicalPharmHub(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalPharmHub(), new UserPrefs());
    private Set<Medicine> medicineSetPanadol = new HashSet<>();
    private Set<Medicine> medicineSetAspirin = new HashSet<>();

    @Test
    public void equals() {
        medicineSetPanadol.add(PANADOL_MEDICINE);
        medicineSetPanadol.add(PARACETAMOL_MEDICINE);
        medicineSetAspirin.add(ASPIRIN_MEDICINE);
        FindOrderCommand findPendingCommand = new FindOrderCommand(STATUS_PENDING, medicineSetPanadol);
        FindOrderCommand findCompletedCommand = new FindOrderCommand(STATUS_COMPLETED, medicineSetPanadol);
        FindOrderCommand findPanadolCommand = new FindOrderCommand(STATUS_COMPLETED, medicineSetPanadol);
        FindOrderCommand findAspirinCommand = new FindOrderCommand(STATUS_COMPLETED, medicineSetAspirin);

        // same object -> returns true
        assertTrue(findPendingCommand.equals(findPendingCommand));

        // same values -> returns true
        FindOrderCommand findPendingCommandCopy = new FindOrderCommand(STATUS_PENDING, medicineSetPanadol);
        assertTrue(findPendingCommand.equals(findPendingCommandCopy));

        // different types -> returns false
        assertFalse(findPendingCommand.equals(1));

        // null -> returns false
        assertFalse(findPanadolCommand.equals(null));

        // different status -> returns false
        assertFalse(findPendingCommand.equals(findCompletedCommand));

        // different medicine -> returns false
        assertFalse(findPanadolCommand.equals(findAspirinCommand));
    }

    @Test
    public void execute_multipleKeywords_singleOrderFound() {
        Status statusToFind = STATUS_PENDING;
        Set<Medicine> medicineToFind = new HashSet<>(List.of(PANADOL_MEDICINE, ASPIRIN_MEDICINE));

        String expectedMessage = String.format(MESSAGE_ORDERS_LISTED_OVERVIEW, 1);
        FindOrderCommand command = new FindOrderCommand(statusToFind, medicineToFind);

        Predicate<Order> statusMatches = order -> statusToFind == null
                || order.getStatus().getStatus() == statusToFind.getStatus();
        Predicate<Order> medicineMatches = order -> medicineToFind == null
                || order.getMedicines().stream()
                .anyMatch(medicine -> medicineToFind.stream()
                        .anyMatch(checkMedicine -> checkMedicine.isSameMedicine(medicine)));

        Predicate<Order> combined = statusMatches.and(medicineMatches);

        expectedModel.updateFilteredOrderList(combined);
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, CommandResult.ListPanelEffects.ORDER);
        assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
        assertEquals(Arrays.asList(CARL_PANADOL_ORDER), model.getFilteredOrderList());
    }

    @Test
    public void execute_singleKeyword_multipleOrdersFound() {
        Status statusToFind = null;
        Set<Medicine> medicineToFind = new HashSet<>(List.of(PANADOL_MEDICINE));

        String expectedMessage = String.format(MESSAGE_ORDERS_LISTED_OVERVIEW, 2);
        FindOrderCommand command = new FindOrderCommand(statusToFind, medicineToFind);

        Predicate<Order> statusMatches = order -> statusToFind == null
                || order.getStatus().getStatus() == statusToFind.getStatus();
        Predicate<Order> medicineMatches = order -> medicineToFind == null
                || order.getMedicines().stream()
                .anyMatch(medicine -> medicineToFind.stream()
                        .anyMatch(checkMedicine -> checkMedicine.isSameMedicine(medicine)));

        Predicate<Order> combined = statusMatches.and(medicineMatches);

        expectedModel.updateFilteredOrderList(combined);
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, CommandResult.ListPanelEffects.ORDER);
        assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
        assertEquals(Arrays.asList(CARL_PANADOL_ORDER, BENSON_PANADOL_ORDER), model.getFilteredOrderList());
    }

    @Test
    public void toStringMethod() {
        medicineSetPanadol.add(PARACETAMOL_MEDICINE);
        medicineSetPanadol.add(PANADOL_MEDICINE);
        medicineSetAspirin.add(ASPIRIN_MEDICINE);
        FindOrderCommand findPendingPanadolCommand = new FindOrderCommand(STATUS_PENDING, medicineSetPanadol);
        FindOrderCommand findCompletedAspirinCommand = new FindOrderCommand(STATUS_COMPLETED, medicineSetAspirin);

        String expectedFindPendingPanadolCommand = FindOrderCommand.class.getCanonicalName()
                + "{status=PENDING, medicine=[[ Paracetamol ], [ Panadol ]]}";
        assertEquals(expectedFindPendingPanadolCommand, findPendingPanadolCommand.toString());

        String expectedFindCompletedAspirinCommand = FindOrderCommand.class.getCanonicalName()
                + "{status=COMPLETED, medicine=[[ Aspirin ]]}";
        assertEquals(expectedFindCompletedAspirinCommand, findCompletedAspirinCommand.toString());
    }
}
