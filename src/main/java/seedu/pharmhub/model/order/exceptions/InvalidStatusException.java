package seedu.pharmhub.model.order.exceptions;

import seedu.pharmhub.model.order.Status;

/**
 * Signals that the operation is unable to find the specified order.
 */
public class InvalidStatusException extends RuntimeException {
    public InvalidStatusException() {
        super(Status.MESSAGE_CONSTRAINTS);
    }
}


