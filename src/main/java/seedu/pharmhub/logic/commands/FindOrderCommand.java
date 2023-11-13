package seedu.pharmhub.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.pharmhub.logic.parser.CliSyntax.PREFIX_MEDICINE_NAME;
import static seedu.pharmhub.logic.parser.CliSyntax.PREFIX_STATUS;

import java.util.Set;
import java.util.function.Predicate;

import seedu.pharmhub.commons.util.ToStringBuilder;
import seedu.pharmhub.logic.Messages;
import seedu.pharmhub.logic.commands.exceptions.CommandException;
import seedu.pharmhub.model.Model;
import seedu.pharmhub.model.medicine.Medicine;
import seedu.pharmhub.model.order.Order;
import seedu.pharmhub.model.order.Status;

/**
 * Finds and lists all persons in PharmHub whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindOrderCommand extends Command {

    public static final String COMMAND_WORD = "findo";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds all orders with statuses matching the given status (if any) AND containing "
            + "any of the given medicines (if any) and displays them as a list with index numbers.\n"
            + "Parameters: " + "[" + PREFIX_STATUS + "STATUS]"
            + "[" + PREFIX_MEDICINE_NAME + "MEDICINE_NAME]...\n"
            + "Example: " + COMMAND_WORD + " s/preparing m/panadol";

    private final Status statusToFind;
    private final Set<Medicine> medicineToFind;

    /**
     * Constructor method for the find order class.
     * @param statusToFind The status to find.
     * @param medicineToFind The Medicine to find.
     */
    public FindOrderCommand(Status statusToFind, Set<Medicine> medicineToFind) {
        this.statusToFind = statusToFind;
        this.medicineToFind = medicineToFind;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (statusToFind == null && medicineToFind == null) {
            return new CommandResult(FindOrderCommand.MESSAGE_USAGE);
        }

        Predicate<Order> statusMatches = order -> statusToFind == null
                || order.getStatus().getStatus() == statusToFind.getStatus();
        Predicate<Order> medicineMatches = order -> medicineToFind == null
                || order.getMedicines().stream()
                .anyMatch(medicine -> medicineToFind.stream()
                        .anyMatch(checkMedicine -> checkMedicine.isSameMedicine(medicine)));

        Predicate<Order> combined = statusMatches.and(medicineMatches);

        model.updateFilteredOrderList(combined);
        return new CommandResult(
                String.format(Messages.MESSAGE_ORDERS_LISTED_OVERVIEW, model.getFilteredOrderList().size()),
                CommandResult.ListPanelEffects.ORDER);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindOrderCommand)) {
            return false;
        }

        FindOrderCommand otherFindCommand = (FindOrderCommand) other;
        return statusToFind.equals(otherFindCommand.statusToFind)
                && medicineToFind.equals(otherFindCommand.medicineToFind);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("status", statusToFind)
                .add("medicine", medicineToFind)
                .toString();
    }
}
