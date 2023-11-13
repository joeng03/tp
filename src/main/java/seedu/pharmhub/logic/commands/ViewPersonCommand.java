package seedu.pharmhub.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.pharmhub.commons.core.index.Index;
import seedu.pharmhub.commons.util.ToStringBuilder;
import seedu.pharmhub.logic.Messages;
import seedu.pharmhub.logic.commands.exceptions.CommandException;
import seedu.pharmhub.model.Model;
import seedu.pharmhub.model.person.Person;


/**
 * Finds and displays the order with the specified order number.
 */
public class ViewPersonCommand extends Command {

    public static final String COMMAND_WORD = "viewp";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays the person identified by the index number "
            + "in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 3";

    public static final String MESSAGE_VIEW_ORDER_SUCCESS = "Displayed Person: %1$s";

    private final Index targetIndex;

    public ViewPersonCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        assert targetIndex.getZeroBased() >= 0 : "Index should be positive";
        assert targetIndex.getZeroBased() < lastShownList.size() : "Index should be within bounds of person list";

        Person personToDisplay = lastShownList.get(targetIndex.getZeroBased());
        return new CommandResult(
                String.format(MESSAGE_VIEW_ORDER_SUCCESS, personToDisplay.getName()), personToDisplay);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ViewPersonCommand)) {
            return false;
        }

        ViewPersonCommand otherViewPersonCommand = (ViewPersonCommand) other;
        return targetIndex.equals(otherViewPersonCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
