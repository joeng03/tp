package seedu.pharmhub.logic.commands;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.pharmhub.logic.Messages;
import seedu.pharmhub.logic.commands.exceptions.CommandException;
import seedu.pharmhub.model.Model;
import seedu.pharmhub.model.medicine.Medicine;

/**
 * A class which contains utility methods for commands.
 */
public class CommandUtil {

    /**
     * Converts the given set of medicines to another set of medicines with medicine short forms expanded to
     * the full name of the medicine as it is in the given model.
     * Throws command exception if the given medicine name or its short form cannot be found in the given model.
     */
    public static Set<Medicine> getModelMedicine(Model model, Set<Medicine> medicines) throws CommandException {
        Set<Medicine> convertedMedicines = new HashSet<>();
        for (Medicine medicine : medicines) {
            Optional<Medicine> m = model.getMedicine(medicine);
            if (m.isEmpty()) {
                throw new CommandException(
                        String.format(Messages.MESSAGE_INVALID_MEDICINE, medicine.getMedicineName()));
            }
            convertedMedicines.add(m.get());
        }
        return convertedMedicines;
    }
}
