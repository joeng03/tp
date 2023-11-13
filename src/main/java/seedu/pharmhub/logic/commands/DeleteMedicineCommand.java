package seedu.pharmhub.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.pharmhub.commons.core.index.Index;
import seedu.pharmhub.commons.util.ToStringBuilder;
import seedu.pharmhub.logic.Messages;
import seedu.pharmhub.logic.commands.exceptions.CommandException;
import seedu.pharmhub.model.Model;
import seedu.pharmhub.model.medicine.Medicine;



/**
 * Deletes a medicine identified using it's displayed index from the PharmHub.
 */
public class DeleteMedicineCommand extends Command {
    public static final String COMMAND_WORD = "deletem";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the medicine identified by the index number used in the displayed medicine list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_MEDICINE_SUCCESS = "Deleted Medicine: %1$s";

    public static final String MESSAGE_DELETE_MEDICINE_FAILURE = "Unable to delete medicine '%1$s'\n"
            + "Existing orders or allergies with given medicine must be deleted before medicine can be deleted. \n"
            + "Use '" + ListPeopleCommand.COMMAND_WORD + "' or '" + ListOrderCommand.COMMAND_WORD + "' commands to "
            + "find out affected orders or allergies";

    private final Index index;

    public DeleteMedicineCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Medicine> medicineList = model.getFilteredMedicineList();

        if (index.getZeroBased() >= medicineList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MEDICINE_DISPLAYED_INDEX);
        }

        assert index.getZeroBased() >= 0 : "Index should be positive";
        assert index.getZeroBased() < medicineList.size() : "Index should be within bounds of medicine list";

        Medicine medicine = medicineList.get(index.getZeroBased());
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        model.updateFilteredOrderList(Model.PREDICATE_SHOW_ALL_ORDERS);
        if (model.getFilteredPersonList().stream().anyMatch(x -> x.isAllergicTo(medicine))
            || model.getFilteredOrderList().stream().anyMatch(x -> x.getMedicines().contains(medicine))) {
            throw new CommandException(String.format(MESSAGE_DELETE_MEDICINE_FAILURE, Messages.format(medicine)));
        }

        model.deleteMedicine(medicine);

        assert !model.hasMedicine(medicine) : "Medicine should be deleted from model";

        return new CommandResult(String.format(MESSAGE_DELETE_MEDICINE_SUCCESS, Messages.format(medicine)));

    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteMedicineCommand)) {
            return false;
        }

        DeleteMedicineCommand otherDeleteCommand = (DeleteMedicineCommand) other;
        return index.equals(otherDeleteCommand.index);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", index)
                .toString();
    }
}
