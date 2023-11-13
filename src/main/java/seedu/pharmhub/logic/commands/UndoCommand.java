package seedu.pharmhub.logic.commands;

import seedu.pharmhub.logic.Messages;
import seedu.pharmhub.logic.commands.exceptions.CommandException;
import seedu.pharmhub.model.Model;

/**
 * Undoes the last action that modified PharmHub
 */
public class UndoCommand extends Command {
    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Undo the last change made to PharmHub\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_UNDO_SUCCESS = "Undo successful.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (!model.canUndo()) {
            throw new CommandException(
                    "Undo failed: " + Messages.MESSAGE_NO_VALID_PREVIOUS_STATE);
        }

        model.undo();
        return new CommandResult(MESSAGE_UNDO_SUCCESS);
    }
}
