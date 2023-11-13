package seedu.pharmhub.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.pharmhub.logic.Messages.format;

import java.util.Optional;

import seedu.pharmhub.commons.util.ToStringBuilder;
import seedu.pharmhub.logic.commands.exceptions.CommandException;
import seedu.pharmhub.model.Model;
import seedu.pharmhub.model.order.Order;


/**
 * Finds and displays the order with the specified order number.
 */
public class ViewOrderCommand extends Command {

    public static final String COMMAND_WORD = "viewo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays the order identified by the given "
            + "order number\n"
            + "Parameters: ORDER_NUMBER\n"
            + "Example: " + COMMAND_WORD + " 12345";

    public static final String MESSAGE_ORDER_NOT_FOUND = "No order found with order number #%1$s";

    public static final String MESSAGE_VIEW_ORDER_SUCCESS = "Displayed Order: #%1$s";

    private final String orderNumber;

    public ViewOrderCommand(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Optional<Order> orderOptional = model.getOrder(orderNumber);
        if (orderOptional.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_ORDER_NOT_FOUND, orderNumber));
        } else {
            return new CommandResult(
                    String.format(MESSAGE_VIEW_ORDER_SUCCESS, format(orderOptional.get())),
                    orderOptional.get());
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ViewOrderCommand)) {
            return false;
        }

        ViewOrderCommand otherViewOrderCommand = (ViewOrderCommand) other;
        return orderNumber.equals(otherViewOrderCommand.orderNumber);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("orderNumber", orderNumber)
                .toString();
    }
}
