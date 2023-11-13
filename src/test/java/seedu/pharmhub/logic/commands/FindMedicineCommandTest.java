package seedu.pharmhub.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.pharmhub.logic.Messages.MESSAGE_MEDICINES_LISTED_OVERVIEW;
import static seedu.pharmhub.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.pharmhub.testutil.TypicalOrders.PANADOL_MEDICINE;
import static seedu.pharmhub.testutil.TypicalOrders.PARACETAMOL_MEDICINE;
import static seedu.pharmhub.testutil.TypicalOrders.PENICILLIN_MEDICINE;
import static seedu.pharmhub.testutil.TypicalPersons.getTypicalPharmHub;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import seedu.pharmhub.model.Model;
import seedu.pharmhub.model.ModelManager;
import seedu.pharmhub.model.UserPrefs;
import seedu.pharmhub.model.medicine.Medicine;

/**
 * Contains integration tests (interaction with the Model) for {@code FindMedicineCommand}.
 */
public class FindMedicineCommandTest {
    private Model model = new ModelManager(getTypicalPharmHub(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalPharmHub(), new UserPrefs());

    @Test
    public void equals() {
        String[] keyWords1 = {"ol", "en"};
        String[] keyWords2 = {"formin"};

        FindMedicineCommand findFirstCommand = new FindMedicineCommand(keyWords1);
        FindMedicineCommand findSecondCommand = new FindMedicineCommand(keyWords2);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindMedicineCommand findFirstCommandCopy = new FindMedicineCommand(keyWords1);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different medicine -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }


    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_MEDICINES_LISTED_OVERVIEW, 3);
        String s = "ol en ";
        String[] keyWords = s.split("\\s+");
        FindMedicineCommand command = new FindMedicineCommand(keyWords);
        Predicate<Medicine> predicate = m -> Stream.of(keyWords).anyMatch(x ->
                m.getMedicineName().toLowerCase().contains(x.toLowerCase()));
        expectedModel.updateFilteredMedicineList(predicate);
        CommandResult expectedCommandResult = new CommandResult(expectedMessage,
                CommandResult.ListPanelEffects.MEDICINE);
        assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
        assertEquals(Arrays.asList(PANADOL_MEDICINE, PENICILLIN_MEDICINE, PARACETAMOL_MEDICINE),
                model.getFilteredMedicineList());
    }

    @Test
    public void toStringMethod() {
        String s = "ol en ";
        String[] keyWords = s.split("\\s+");
        FindMedicineCommand findCommand = new FindMedicineCommand(keyWords);
        String expected = FindMedicineCommand.class.getCanonicalName() + "{keyWords=" + keyWords + "}";
        assertEquals(expected, findCommand.toString());
    }



}
