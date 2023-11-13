package seedu.pharmhub.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.pharmhub.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.pharmhub.model.Model.PREDICATE_SHOW_ALL_ORDERS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.pharmhub.commons.core.index.Index;
import seedu.pharmhub.commons.util.CollectionUtil;
import seedu.pharmhub.commons.util.ToStringBuilder;
import seedu.pharmhub.logic.Messages;
import seedu.pharmhub.logic.commands.exceptions.CommandException;
import seedu.pharmhub.model.Model;
import seedu.pharmhub.model.medicine.Medicine;
import seedu.pharmhub.model.order.Order;
import seedu.pharmhub.model.order.OrderNumber;
import seedu.pharmhub.model.order.Status;
import seedu.pharmhub.model.person.Person;


/**
 * Edits the details of a status of an existing order in PharmHub.
 */
public class UpdateStatusCommand extends Command {

    public static final String COMMAND_WORD = "updates";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the Status of the order identified "
            + "by the order number used in the displayed order list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_STATUS + "STATUS\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_STATUS + "completed";

    public static final String MESSAGE_EDIT_ORDER_STATUS_SUCCESS = "Successfully updated status of Order #%1$s";
    public static final String MESSAGE_NOT_EDITED = "Status to edit to must be provided.";
    public static final String MESSAGE_DUPLICATE_ORDER = "Operation would result in duplicate order";
    public static final String MESSAGE_WRONG_CHRONOLOGICAL_ORDER_STATUS =
            "Status can only be updated in a chronological order "
                    + Status.OrderStatus.PENDING + " (PD) -> "
                    + Status.OrderStatus.PREPARING + " (PR) -> "
                    + Status.OrderStatus.COMPLETED + " (CP) -> "
                    + Status.OrderStatus.CANCELLED + " (CC)";

    private final Index index;
    private final EditOrderDescriptor editOrderDescriptor;

    /**
     * @param index of the order in the filtered order list to edit
     * @param editOrderDescriptor details to edit the order with
     */
    public UpdateStatusCommand(Index index, EditOrderDescriptor editOrderDescriptor) {
        requireNonNull(index);
        requireNonNull(editOrderDescriptor);

        this.index = index;
        this.editOrderDescriptor = new EditOrderDescriptor(editOrderDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Order> lastShownList = model.getFilteredOrderList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
        }

        assert index.getZeroBased() >= 0 : "Index should be positive";
        assert index.getZeroBased() < lastShownList.size() : "Index should be within bounds of order list";

        Order orderToEdit = lastShownList.get(index.getZeroBased());
        Order editedOrder = createEditedOrder(orderToEdit, editOrderDescriptor);

        if (!Status.isValidChronologicalStatus(orderToEdit.getStatus().getStatus(),
                editedOrder.getStatus().getStatus())) {
            throw new CommandException(MESSAGE_WRONG_CHRONOLOGICAL_ORDER_STATUS);
        }

        if (!orderToEdit.isSameOrder(editedOrder) && model.hasOrder(editedOrder)) {
            throw new CommandException(MESSAGE_DUPLICATE_ORDER);
        }

        model.setOrder(orderToEdit, editedOrder);
        model.updateFilteredOrderList(PREDICATE_SHOW_ALL_ORDERS);
        return new CommandResult(
                String.format(MESSAGE_EDIT_ORDER_STATUS_SUCCESS, Messages.format(editedOrder)), editedOrder);
    }

    /**
     * Creates and returns a {@code Order} with the details of {@code orderToEdit}
     * edited with {@code editOrderDescriptor}.
     */
    private static Order createEditedOrder(Order orderToEdit, EditOrderDescriptor editOrderDescriptor) {
        assert orderToEdit != null;

        OrderNumber updatedOrderNumber = orderToEdit.getOrderNumber();
        Person updatedPerson = editOrderDescriptor.getPerson().orElse(orderToEdit.getPerson());
        Set<Medicine> updatedMedicineName = editOrderDescriptor.getMedicines().orElse(orderToEdit.getMedicines());
        Status updatedStatus = editOrderDescriptor.getStatus().orElse(orderToEdit.getStatus());

        return new Order(updatedOrderNumber, updatedPerson, updatedMedicineName, updatedStatus);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UpdateStatusCommand)) {
            return false;
        }

        UpdateStatusCommand otherEditCommand = (UpdateStatusCommand) other;
        return index.equals(otherEditCommand.index)
                && editOrderDescriptor.equals(otherEditCommand.editOrderDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editOrderDescriptor", editOrderDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the order with. Each non-empty field value will replace the
     * corresponding field value of the order.
     */
    public static class EditOrderDescriptor {
        private OrderNumber orderNumber;
        private Person person;
        private Status status;
        private Set<Medicine> medicines;

        public EditOrderDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code medicines} is used internally.
         */
        public EditOrderDescriptor(EditOrderDescriptor toCopy) {
            setOrderNumber(toCopy.orderNumber);
            setPerson(toCopy.person);
            setMedicines(toCopy.medicines);
            setStatus(toCopy.status);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(orderNumber, person, medicines, status);
        }

        public void setOrderNumber(OrderNumber orderNumber) {
            this.orderNumber = orderNumber;
        }

        public Optional<OrderNumber> getOrderNumber() {
            return Optional.ofNullable(orderNumber);
        }

        public void setPerson(Person person) {
            this.person = person;
        }

        public Optional<Person> getPerson() {
            return Optional.ofNullable(person);
        }

        /**
         * Sets {@code medicines} to this object's {@code medicines}.
         * A defensive copy of {@code medicines} is used internally.
         */
        public void setMedicines(Set<Medicine> medicines) {
            this.medicines = (medicines != null) ? new HashSet<>(medicines) : null;
        }

        /**
         * Returns an unmodifiable medicines set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code medicines} is null.
         */
        public Optional<Set<Medicine>> getMedicines() {
            return (medicines != null) ? Optional.of(Collections.unmodifiableSet(medicines)) : Optional.empty();
        }

        public void setStatus(Status orderStatus) {
            this.status = orderStatus;
        }

        public Optional<Status> getStatus() {
            return Optional.ofNullable(status);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditOrderDescriptor)) {
                return false;
            }

            EditOrderDescriptor otherEditOrderDescriptor = (EditOrderDescriptor) other;
            return Objects.equals(orderNumber, otherEditOrderDescriptor.orderNumber)
                    && Objects.equals(person, otherEditOrderDescriptor.person)
                    && Objects.equals(medicines, otherEditOrderDescriptor.medicines)
                    && Objects.equals(status, otherEditOrderDescriptor.status);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("orderNumber", orderNumber)
                    .add("person", person)
                    .add("medicines", medicines)
                    .add("status", status)
                    .toString();
        }
    }
}
