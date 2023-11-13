package seedu.pharmhub.logic.commands;

import static seedu.pharmhub.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.pharmhub.testutil.TypicalPersons.getTypicalPharmHub;

import org.junit.jupiter.api.Test;

import seedu.pharmhub.model.Model;
import seedu.pharmhub.model.ModelManager;
import seedu.pharmhub.model.PharmHub;
import seedu.pharmhub.model.UserPrefs;

public class ClearCommandTest {
    @Test
    public void execute_emptyPharmHub_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyPharmHub_success() {
        Model model = new ModelManager(getTypicalPharmHub(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalPharmHub(), new UserPrefs());
        expectedModel.setPharmHub(new PharmHub());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
