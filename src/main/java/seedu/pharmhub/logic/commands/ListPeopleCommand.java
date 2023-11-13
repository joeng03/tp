package seedu.pharmhub.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.pharmhub.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.pharmhub.model.Model;

/**
 * Lists all persons in the PharmHub to the user.
 */
public class ListPeopleCommand extends Command {

    public static final String COMMAND_WORD = "listp";

    public static final String MESSAGE_SUCCESS = "Listed all persons";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS, CommandResult.ListPanelEffects.PERSON);
    }
}
