package seedu.pharmhub.model.order;

import static seedu.pharmhub.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.pharmhub.commons.util.ToStringBuilder;
import seedu.pharmhub.model.InfoObject;
import seedu.pharmhub.model.medicine.Medicine;
import seedu.pharmhub.model.person.Person;



/**
 * Represents an Order in the PharmHub.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Order implements InfoObject {
    private final OrderNumber orderNumber;
    private final Person person;
    private final Set<Medicine> medicines = new HashSet<>();

    private final Status orderStatus;
    /**
     * Every field must be present and not null.
     */
    public Order(OrderNumber orderNumber, Person person, Set<Medicine> medicines, Status orderStatus) {
        requireAllNonNull(orderNumber, person, medicines);
        this.orderNumber = orderNumber;
        this.person = person;
        this.medicines.addAll(medicines);
        this.orderStatus = orderStatus;
    }

    public OrderNumber getOrderNumber() {
        return orderNumber;
    }

    public Person getPerson() {
        return person;
    }

    public Set<Medicine> getMedicines() {
        return Collections.unmodifiableSet(medicines);
    }

    public Status getStatus() {
        return orderStatus;
    }

    public void addOrderToPerson() {
        person.addOrder(this);
    }

    /**
     * Returns true if both orders have the same order number.
     * This defines a weaker notion of equality between two orders.
     */
    public boolean isSameOrder(Order otherOrder) {
        if (otherOrder == this) {
            return true;
        }

        return otherOrder != null
                && otherOrder.getOrderNumber().equals(getOrderNumber());
    }

    /**
     * Returns true if both orders have the same identity and data fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Order)) {
            return false;
        }

        Order otherOrder = (Order) other;
        return orderNumber.equals(otherOrder.orderNumber)
                && medicines.equals(otherOrder.medicines)
                && person.equals(otherOrder.person)
                && orderStatus.equals(otherOrder.orderStatus);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(orderNumber, person, medicines, orderStatus);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("orderNumber", orderNumber)
                .add("person", person)
                .add("medicines", medicines)
                .add("status", orderStatus)
                .toString();
    }
}
