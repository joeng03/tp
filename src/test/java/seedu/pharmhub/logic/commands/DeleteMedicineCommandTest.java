package seedu.pharmhub.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.pharmhub.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.pharmhub.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.pharmhub.logic.commands.CommandTestUtil.showMedicineAtIndex;
import static seedu.pharmhub.testutil.TypicalIndexes.INDEX_FIFTH;
import static seedu.pharmhub.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.pharmhub.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.pharmhub.testutil.TypicalIndexes.INDEX_THIRD;
import static seedu.pharmhub.testutil.TypicalPersons.getTypicalPharmHub;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.pharmhub.commons.core.index.Index;
import seedu.pharmhub.logic.Messages;
import seedu.pharmhub.model.Model;
import seedu.pharmhub.model.ModelManager;
import seedu.pharmhub.model.UserPrefs;
import seedu.pharmhub.model.medicine.Medicine;



/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteMedicineCommand}.
 */
public class DeleteMedicineCommandTest {

    private Model model;
    @BeforeEach
    public void init() {
        model = new ModelManager(getTypicalPharmHub(), new UserPrefs());
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Medicine medicineToDelete = model.getFilteredMedicineList().get(INDEX_FIFTH.getZeroBased());
        DeleteMedicineCommand deleteCommand = new DeleteMedicineCommand(INDEX_FIFTH);

        String expectedMessage = String.format(DeleteMedicineCommand.MESSAGE_DELETE_MEDICINE_SUCCESS,
                Messages.format(medicineToDelete));

        ModelManager expectedModel = new ModelManager(model.getPharmHub(), new UserPrefs());
        expectedModel.deleteMedicine(medicineToDelete);
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);


        assertCommandSuccess(deleteCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteMedicineCommand deleteCommand = new DeleteMedicineCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_MEDICINE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showMedicineAtIndex(model, INDEX_FIFTH);

        Medicine medicineToDelete = model.getFilteredMedicineList().get(INDEX_FIRST.getZeroBased());
        DeleteMedicineCommand deleteCommand = new DeleteMedicineCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteMedicineCommand.MESSAGE_DELETE_MEDICINE_SUCCESS,
                Messages.format(medicineToDelete));

        Model expectedModel = new ModelManager(model.getPharmHub(), new UserPrefs());
        expectedModel.deleteMedicine(medicineToDelete);
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(deleteCommand, model, expectedCommandResult, expectedModel);
    }


    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showMedicineAtIndex(model, INDEX_FIFTH);

        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of PharmHub list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getPharmHub().getMedicineList().size());

        DeleteMedicineCommand deleteCommand = new DeleteMedicineCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_MEDICINE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_deleteMedicineWithAllergy_throwsCommandException() {
        DeleteMedicineCommand deleteCommand = new DeleteMedicineCommand(INDEX_SECOND);
        Medicine medicine = model.getFilteredMedicineList().get(INDEX_SECOND.getZeroBased());
        String expectedMessage = String.format(DeleteMedicineCommand.MESSAGE_DELETE_MEDICINE_FAILURE,
                Messages.format(medicine));
        assertCommandFailure(deleteCommand, model, expectedMessage);
    }

    @Test
    public void execute_deleteMedicineWithOrder_throwsCommandException() {
        DeleteMedicineCommand deleteCommand = new DeleteMedicineCommand(INDEX_THIRD);
        Medicine medicine = model.getFilteredMedicineList().get(INDEX_THIRD.getZeroBased());
        String expectedMessage = String.format(DeleteMedicineCommand.MESSAGE_DELETE_MEDICINE_FAILURE,
                Messages.format(medicine));
        assertCommandFailure(deleteCommand, model, expectedMessage);
    }

    @Test
    public void equals() {
        DeleteMedicineCommand deleteFirstCommand = new DeleteMedicineCommand(INDEX_FIRST);
        DeleteMedicineCommand deleteSecondCommand = new DeleteMedicineCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteMedicineCommand deleteFirstCommandCopy = new DeleteMedicineCommand(INDEX_FIRST);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteMedicineCommand deleteCommand = new DeleteMedicineCommand(targetIndex);
        String expected = DeleteMedicineCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, deleteCommand.toString());
    }
}
