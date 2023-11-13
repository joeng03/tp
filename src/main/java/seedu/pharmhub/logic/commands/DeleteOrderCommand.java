package seedu.pharmhub.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.pharmhub.commons.core.index.Index;
import seedu.pharmhub.commons.util.ToStringBuilder;
import seedu.pharmhub.logic.Messages;
import seedu.pharmhub.logic.commands.exceptions.CommandException;
import seedu.pharmhub.model.Model;
import seedu.pharmhub.model.order.Order;


/**
 * Deletes an order identified using it's displayed index from the PharmHub.
 */
public class DeleteOrderCommand extends Command {
    public static final String COMMAND_WORD = "deleteo";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the order identified by the index number used in the displayed order list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_ORDER_SUCCESS = "Deleted Order: #%1$s";

    private final Index index;

    public DeleteOrderCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Order> orderList = model.getFilteredOrderList();


        if (index.getZeroBased() >= orderList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
        }

        assert index.getZeroBased() >= 0 : "Index should be positive";
        assert index.getZeroBased() < orderList.size() : "Index should be within bounds of order list";

        Order order = orderList.get(index.getZeroBased());
        model.deleteOrder(order);

        assert !model.hasOrder(order) : "Order should be deleted from model";

        return new CommandResult(String.format(MESSAGE_DELETE_ORDER_SUCCESS, Messages.format(order)), order);

    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteOrderCommand)) {
            return false;
        }

        DeleteOrderCommand otherDeleteOrderCommand = (DeleteOrderCommand) other;

        return index.equals(otherDeleteOrderCommand.index);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", index)
                .toString();
    }


}
