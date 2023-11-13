package seedu.pharmhub.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.pharmhub.model.Model.PREDICATE_SHOW_ALL_MEDICINES;

import seedu.pharmhub.model.Model;

/**
 * Lists all medicines in PharmHub to the user.
 */
public class ListMedicineCommand extends Command {
    public static final String COMMAND_WORD = "listm";

    public static final String MESSAGE_SUCCESS = "Listed all medicines";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredMedicineList(PREDICATE_SHOW_ALL_MEDICINES);
        return new CommandResult(
                MESSAGE_SUCCESS,
                CommandResult.ListPanelEffects.MEDICINE);
    }
}
