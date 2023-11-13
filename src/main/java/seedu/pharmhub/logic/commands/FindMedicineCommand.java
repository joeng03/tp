package seedu.pharmhub.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Stream;

import seedu.pharmhub.commons.util.ToStringBuilder;
import seedu.pharmhub.logic.Messages;
import seedu.pharmhub.model.Model;
import seedu.pharmhub.model.medicine.Medicine;



/**
 * Finds and lists all medicines in PharmHub whose name contains the given keyword as a substring.
 * Keyword matching is case-insensitive.
 */
public class FindMedicineCommand extends Command {

    public static final String COMMAND_WORD = "findm";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all medicines whose names contain "
            + "the given substring (case insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]... \n"
            + "Example: " + COMMAND_WORD + " ol";

    private final String[] keyWords;

    public FindMedicineCommand(String... keyWords) {
        this.keyWords = keyWords;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        Predicate<Medicine> predicate = m -> Stream.of(keyWords).anyMatch(x ->
                m.getMedicineName().toLowerCase().contains(x.toLowerCase())
                || m.getShortForm().toLowerCase().contains(x.toLowerCase()));
        model.updateFilteredMedicineList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_MEDICINES_LISTED_OVERVIEW, model.getFilteredMedicineList().size()),
                CommandResult.ListPanelEffects.MEDICINE);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindMedicineCommand)) {
            return false;
        }

        FindMedicineCommand otherFindCommand = (FindMedicineCommand) other;
        return Arrays.equals(keyWords, otherFindCommand.keyWords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("keyWords", keyWords)
                .toString();
    }
}
