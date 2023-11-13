package seedu.pharmhub.logic.commands;

import static seedu.pharmhub.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.pharmhub.testutil.TypicalPersons.getTypicalPharmHub;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.pharmhub.model.Model;
import seedu.pharmhub.model.ModelManager;
import seedu.pharmhub.model.UserPrefs;


/**
 * Contains integration tests (interaction with the Model) and unit tests for ListOrderCommand.
 */
class ListOrderCommandTest {

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
                ListOrderCommand.MESSAGE_SUCCESS, CommandResult.ListPanelEffects.ORDER);
        assertCommandSuccess(new ListOrderCommand(), model, expectedCommandResult, expectedModel);
    }

}
