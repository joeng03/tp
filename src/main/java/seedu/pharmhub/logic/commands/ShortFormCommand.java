package seedu.pharmhub.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.pharmhub.logic.parser.CliSyntax.PREFIX_DELETE_SHORT_FORM;
import static seedu.pharmhub.logic.parser.CliSyntax.PREFIX_MEDICINE_NAME;
import static seedu.pharmhub.model.Model.PREDICATE_SHOW_ALL_MEDICINES;

import java.util.List;

import seedu.pharmhub.commons.core.index.Index;
import seedu.pharmhub.commons.util.ToStringBuilder;
import seedu.pharmhub.logic.Messages;
import seedu.pharmhub.logic.commands.exceptions.CommandException;
import seedu.pharmhub.model.Model;
import seedu.pharmhub.model.medicine.Medicine;


/**
 * A class which represents the adding and deleting of a short form to a medicine.
 */
public class ShortFormCommand extends Command {
    public static final String COMMAND_WORD = "sfm";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets the short form for the Medicine identified "
            + "by the index number used in the displayed medicine list.\n"
            + "Add the tag '" + PREFIX_DELETE_SHORT_FORM + "' to delete the current short form for the medicine\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_MEDICINE_NAME + "SHORT_FORM] "
            + "[" + PREFIX_DELETE_SHORT_FORM + "]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_MEDICINE_NAME + "pan";

    public static final String MESSAGE_ADD_SHORT_FORM_SUCCESS = "Added Short Form: %1$s";

    public static final String MESSAGE_DELETE_SHORT_FORM_SUCCESS = "Deleted Short Form: %1$s";
    public static final String MESSAGE_DUPLICATE_SHORT_FORM =
            "This short form/medicine already exists in the PharmHub.";

    private final Index index;

    private final Medicine medicine;

    /**
     * Creates an ShortFormCommand
     */
    public ShortFormCommand(Index index, Medicine medicine) {
        requireNonNull(index);
        requireNonNull(medicine);
        this.index = index;
        this.medicine = medicine;
    }

    /**
     * Creates an ShortForm Command
     */
    public ShortFormCommand(Index index) {
        requireNonNull(index);
        this.index = index;
        medicine = null;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Medicine> lastShownList = model.getFilteredMedicineList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MEDICINE_DISPLAYED_INDEX);
        }

        if (medicine == null) {
            Medicine medicineToAddShortForm = lastShownList.get(index.getZeroBased());
            Medicine newMedicine = new Medicine(medicineToAddShortForm.getMedicineName(), "");
            model.setMedicine(medicineToAddShortForm, newMedicine);
            model.updateFilteredMedicineList(PREDICATE_SHOW_ALL_MEDICINES);
            return new CommandResult(String.format(MESSAGE_DELETE_SHORT_FORM_SUCCESS, Messages.format(newMedicine)));
        }

        if (model.hasMedicine(medicine)) {
            throw new CommandException(MESSAGE_DUPLICATE_SHORT_FORM);
        }

        Medicine medicineToAddShortForm = lastShownList.get(index.getZeroBased());
        Medicine newMedicine = new Medicine(medicineToAddShortForm.getMedicineName(), medicine.getMedicineName());

        model.setMedicine(medicineToAddShortForm, newMedicine);
        model.updateFilteredMedicineList(PREDICATE_SHOW_ALL_MEDICINES);
        return new CommandResult(String.format(MESSAGE_ADD_SHORT_FORM_SUCCESS, Messages.format(newMedicine)));

    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ShortFormCommand)) {
            return false;
        }

        ShortFormCommand otherCommand = (ShortFormCommand) other;
        return index.equals(otherCommand.index)
                && medicine.equals(otherCommand.medicine);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("medicine", medicine)
                .toString();
    }


}
