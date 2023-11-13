package seedu.pharmhub.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.pharmhub.commons.util.StringUtil;
import seedu.pharmhub.logic.parser.Prefix;
import seedu.pharmhub.model.medicine.Medicine;
import seedu.pharmhub.model.order.Order;
import seedu.pharmhub.model.person.Person;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";

    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";

    public static final String MESSAGE_INVALID_ORDER_DISPLAYED_INDEX = "The order index provided is invalid";

    public static final String MESSAGE_INVALID_MEDICINE_DISPLAYED_INDEX = "The medicine index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";

    public static final String MESSAGE_MEDICINES_LISTED_OVERVIEW = "%1$d medicines listed!";
    public static final String MESSAGE_ORDERS_LISTED_OVERVIEW = "%1$d orders listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";

    public static final String MESSAGE_ALLERGIC_TO_MEDICINE = "Warning: This patient is allergic to this medicine! Add"
            + " 'ia/' (ignore allergy) to the end of the command to add this medicine to the order anyway.";

    public static final String MESSAGE_NO_ORDER_WITH_GIVEN_ORDER_NUMBER =
            "There is no order with %1$s as order number.";

    public static final String MESSAGE_INVALID_MEDICINE = "Medicine '%1$s' does not exist. Use 'addm' to add a new"
            + " medicine to PharmHub";

    public static final String MESSAGE_NO_VALID_PREVIOUS_STATE = "No previous state to revert to";


    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String format(Person person) {
        return person.getName().toString();
    }

    /**
     * Formats the {@code order} for display to the user.
     */
    public static String format(Order order) {
        return order.getOrderNumber().toString();
    }

    /**
     * Formats the {@code medicine} for display to the user.
     */
    public static String format(Medicine medicine) {
        return medicine.getMedicineName().toString();
    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String formatOrder(Order order) {
        final StringBuilder builder = new StringBuilder();
        builder.append(order.getOrderNumber())
                .append("; Person Name: ")
                .append(order.getPerson().getName())
                .append("; Medicines: ")
                .append(StringUtil.setToStr(order.getMedicines()))
                .append("; Status: ")
                .append(order.getStatus());
        return builder.toString();
    }
    /**
     * Formats the {@code person} for display to the user.
     */
    public static String formatStatus(Order order) {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Order Number: " + order.getOrderNumber())
                .append("; Updated to Status: ")
                .append(order.getStatus());
        return builder.toString();
    }

}
