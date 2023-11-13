package seedu.pharmhub.model.order.exceptions;

import seedu.pharmhub.model.order.Status;

/**
 * Signals that the given status is invalid.
 */
public class InvalidStatusException extends RuntimeException {
    public InvalidStatusException() {
        super(Status.MESSAGE_CONSTRAINTS);
    }
}


