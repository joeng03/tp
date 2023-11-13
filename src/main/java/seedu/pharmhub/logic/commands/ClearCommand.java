package seedu.pharmhub.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.pharmhub.model.Model;

/**
 * Clears the data in PharmHub.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "PharmHub has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.clearPharmHub();

        assert model.getPharmHub().getPersonList().isEmpty() : "PharmHub PersonList should be empty";
        assert model.getPharmHub().getMedicineList().isEmpty() : "PharmHub MedicineList should be empty";
        assert model.getPharmHub().getOrderList().isEmpty() : "PharmHub OrderList should be empty";
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
