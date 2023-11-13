package seedu.pharmhub.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.pharmhub.testutil.TypicalOrders.getTypicalOrders;

import org.junit.jupiter.api.Test;

import seedu.pharmhub.model.InfoObject;

public class CommandResultTest {
    @Test
    public void equals() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns true
        assertTrue(commandResult.equals(new CommandResult("feedback")));
        assertTrue(commandResult.equals(new CommandResult("feedback", false, false)));
        assertTrue(commandResult.equals(new CommandResult("feedback",
                CommandResult.ListPanelEffects.NO_EFFECT)));
        assertTrue(commandResult.equals(new CommandResult("feedback", (InfoObject) null)));
        assertTrue(commandResult.equals(new CommandResult("feedback", false, false,
                CommandResult.ListPanelEffects.NO_EFFECT, null)));

        // same object -> returns true
        assertTrue(commandResult.equals(commandResult));

        // null -> returns false
        assertFalse(commandResult.equals(null));

        // different types -> returns false
        assertFalse(commandResult.equals(0.5f));

        // different feedbackToUser value -> returns false
        assertFalse(commandResult.equals(new CommandResult("different")));

        // different showHelp value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", true, false)));

        // different exit value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", false, true)));

        // different listPanelEffects value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback",
                CommandResult.ListPanelEffects.PERSON)));
        assertFalse(commandResult.equals(new CommandResult("feedback",
                CommandResult.ListPanelEffects.ORDER)));

        // different InfoObjectValue -> returns false
        CommandResult withDifferentInfoObjectValue = new CommandResult(
                "feedback", getTypicalOrders().get(0));
        assertFalse(commandResult.equals(withDifferentInfoObjectValue));
        assertFalse(withDifferentInfoObjectValue.equals(commandResult));

    }

    @Test
    public void hashcode() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns same hashcode
        assertEquals(commandResult.hashCode(), new CommandResult("feedback").hashCode());

        // different feedbackToUser value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("different").hashCode());

        // different showHelp value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", true,
                false, CommandResult.ListPanelEffects.NO_EFFECT, null).hashCode());

        // different exit value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", false,
                true, CommandResult.ListPanelEffects.NO_EFFECT, null).hashCode());

        // different InfoObject -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", false,
                false, CommandResult.ListPanelEffects.NO_EFFECT, getTypicalOrders().get(0)).hashCode());
    }

    @Test
    public void toStringMethod() {
        CommandResult commandResult = new CommandResult("feedback");
        String expected = CommandResult.class.getCanonicalName() + "{feedbackToUser="
                + commandResult.getFeedbackToUser() + ", showHelp=" + commandResult.isShowHelp()
                + ", exit=" + commandResult.isExit() + ", listPanelEffects=" + commandResult.getListPanelEffects()
                + ", infoObject=" + commandResult.getInfoObject() + "}";
        assertEquals(expected, commandResult.toString());
    }

    @Test
    public void hasInfoObjectMethod() {
        CommandResult commandResultWithoutInfoObject = new CommandResult("feedback");
        assertFalse(commandResultWithoutInfoObject.hasInfoObject());

        CommandResult commandWithInfoObject = new CommandResult("feedback", false, false,
                CommandResult.ListPanelEffects.NO_EFFECT, getTypicalOrders().get(0));
        assertTrue(commandWithInfoObject.hasInfoObject());
    }
}
