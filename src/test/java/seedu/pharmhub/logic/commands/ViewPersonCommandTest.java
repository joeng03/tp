package seedu.pharmhub.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.pharmhub.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.pharmhub.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.pharmhub.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.pharmhub.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.pharmhub.testutil.TypicalPersons.getTypicalPharmHub;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.pharmhub.commons.core.index.Index;
import seedu.pharmhub.logic.Messages;
import seedu.pharmhub.model.Model;
import seedu.pharmhub.model.ModelManager;
import seedu.pharmhub.model.UserPrefs;
import seedu.pharmhub.model.person.Person;


class ViewPersonCommandTest {
    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalPharmHub(), new UserPrefs());
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        ViewPersonCommand command = new ViewPersonCommand(INDEX_FIRST);
        Person expectedPerson = model.getPharmHub().getPersonList().get(INDEX_FIRST.getZeroBased());
        String expectedMessage = String.format(
                ViewPersonCommand.MESSAGE_VIEW_ORDER_SUCCESS,
                expectedPerson.getName().fullName);
        CommandResult expectedCommandResult = new CommandResult(
                expectedMessage,
                expectedPerson);
        assertCommandSuccess(command, model, expectedCommandResult, model);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ViewPersonCommand viewPersonCommand = new ViewPersonCommand(outOfBoundIndex);

        assertCommandFailure(viewPersonCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ViewPersonCommand viewFirstPersonCommand = new ViewPersonCommand(INDEX_FIRST);

        // same object -> returns true
        assertTrue(viewFirstPersonCommand.equals(viewFirstPersonCommand));

        // same values -> returns true
        ViewPersonCommand viewFirstPersonCommandCopy = new ViewPersonCommand(INDEX_FIRST);
        assertTrue(viewFirstPersonCommand.equals(viewFirstPersonCommandCopy));

        // different types -> returns false
        assertFalse(viewFirstPersonCommand.equals(1));

        // null -> returns false
        assertFalse(viewFirstPersonCommand.equals(null));

        // different person -> returns false
        assertFalse(viewFirstPersonCommand.equals(new ViewPersonCommand(INDEX_SECOND)));
    }

    @Test
    public void toStringMethod() {
        ViewPersonCommand viewPersonCommand = new ViewPersonCommand(INDEX_FIRST);
        String expected = ViewPersonCommand.class.getCanonicalName() + "{targetIndex=" + INDEX_FIRST + "}";
        assertEquals(expected, viewPersonCommand.toString());
    }

}
