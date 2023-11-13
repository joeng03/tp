package seedu.pharmhub.logic.commands;

import static seedu.pharmhub.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.pharmhub.logic.commands.CommandTestUtil.showMedicineAtIndex;
import static seedu.pharmhub.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.pharmhub.testutil.TypicalPersons.getTypicalPharmHub;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.pharmhub.model.Model;
import seedu.pharmhub.model.ModelManager;
import seedu.pharmhub.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListMedicineCommand.
 */
public class ListMedicineCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalPharmHub(), new UserPrefs());
        expectedModel = new ModelManager(model.getPharmHub(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        CommandResult expectedCommandResult = new CommandResult(
                ListMedicineCommand.MESSAGE_SUCCESS, CommandResult.ListPanelEffects.MEDICINE);
        assertCommandSuccess(new ListMedicineCommand(), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showMedicineAtIndex(model, INDEX_FIRST);
        CommandResult expectedCommandResult = new CommandResult(
                ListMedicineCommand.MESSAGE_SUCCESS, CommandResult.ListPanelEffects.MEDICINE);
        assertCommandSuccess(new ListMedicineCommand(), model, expectedCommandResult, expectedModel);
    }
}

