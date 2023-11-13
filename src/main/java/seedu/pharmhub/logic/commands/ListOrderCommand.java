package seedu.pharmhub.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.pharmhub.model.Model.PREDICATE_SHOW_ALL_ORDERS;

import seedu.pharmhub.model.Model;

/**
 * Lists all orders in PharmHub to the user.
 */
public class ListOrderCommand extends Command {
    public static final String COMMAND_WORD = "listo";

    public static final String MESSAGE_SUCCESS = "Listed all orders";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredOrderList(PREDICATE_SHOW_ALL_ORDERS);
        return new CommandResult(
                MESSAGE_SUCCESS,
                CommandResult.ListPanelEffects.ORDER);
    }
}
