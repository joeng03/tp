package seedu.pharmhub.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.pharmhub.logic.parser.CliSyntax.PREFIX_MEDICINE_NAME;
import static seedu.pharmhub.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.pharmhub.commons.util.ToStringBuilder;
import seedu.pharmhub.logic.Messages;
import seedu.pharmhub.logic.commands.exceptions.CommandException;
import seedu.pharmhub.model.Model;
import seedu.pharmhub.model.medicine.Medicine;


/**
 * A class which represents the adding of a medicine.
 */
public class AddMedicineCommand extends Command {

    public static final String COMMAND_WORD = "addm";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds the given medicine \n"
            + "Parameters:  "
            + PREFIX_MEDICINE_NAME + "MEDICINE_NAME (Full name of Medicine) \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MEDICINE_NAME + "Panadol";

    public static final String MESSAGE_SUCCESS = "New medicine added: %1$s";
    public static final String MESSAGE_DUPLICATE_MEDICINE = "Medicine named '%1$s' already exists in PharmHub";

    private final Medicine toAdd;

    /**
     * Creates an AddMedicineCommand to add the specified {@code Medicine}
     */
    public AddMedicineCommand(Medicine medicine) {
        requireNonNull(medicine);
        toAdd = medicine;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.hasMedicine(toAdd)) {
            throw new CommandException(
                    String.format(MESSAGE_DUPLICATE_MEDICINE, toAdd.getMedicineName()));
        }

        model.addMedicine(toAdd);

        assert model.hasMedicine(toAdd) : "Medicine should be added to model";

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddMedicineCommand)) {
            return false;
        }

        AddMedicineCommand otherAddCommand = (AddMedicineCommand) other;
        return toAdd.equals(otherAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }


}
