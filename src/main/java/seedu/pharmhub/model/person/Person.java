package seedu.pharmhub.model.person;

import static seedu.pharmhub.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.pharmhub.commons.util.ToStringBuilder;
import seedu.pharmhub.model.InfoObject;
import seedu.pharmhub.model.allergy.Allergy;
import seedu.pharmhub.model.medicine.Medicine;
import seedu.pharmhub.model.order.Order;
import seedu.pharmhub.model.tag.Tag;

/**
 * Represents a Person in the PharmHub.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person implements InfoObject {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();
    private final Set<Allergy> allergies = new HashSet<>();
    private final Set<Order> orders = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags, Set<Allergy> allergies,
                  Set<Order> orders) {
        requireAllNonNull(name, phone, email, address, tags, allergies);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.allergies.addAll(allergies);
        this.orders.addAll(orders);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns an immutable allergy set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Allergy> getAllergies() {
        return Collections.unmodifiableSet(allergies);
    }

    /**
     * Returns an immutable order set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Order> getOrders() {
        return Collections.unmodifiableSet(orders);
    }

    public void setOrders(Set<Order> newOrders) {
        orders.clear();
        orders.addAll(newOrders);
    }

    public void clearOrders() {
        orders.clear();
    }

    /**
     * Adds an order to the person.
     * @param o The order to be added.
     */
    public void addOrder(Order o) {
        this.orders.add(o);
    }

    /**
     * Removes an order from the person.
     * @param o The order to be removed.
     */
    public void removeOrder(Order o) {
        this.orders.remove(o);
    }

    /**
     * Replaces an order in the person.
     * @param oldOrder The order to be replaced.
     * @param newOrder The new order.
     */
    public void replaceOrder(Order oldOrder, Order newOrder) {
        this.orders.remove(oldOrder);
        this.orders.add(newOrder);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if the person is allergic to the medicine.
     */
    public boolean isAllergicTo(Medicine medicine) {
        return allergies.stream().anyMatch(allergy -> allergy.getAllergy().isSameMedicine(medicine));
    }

    /**
     * Returns true if the person is allergic to any of the given medicines.
     */
    public boolean isAllergicToAny(Set<Medicine> medicines) {
        return medicines.stream().anyMatch(medicine -> isAllergicTo(medicine));
    }

    public boolean hasOrderConflicts() {
        return orders.stream().anyMatch(o -> isAllergicToAny(o.getMedicines()));
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && tags.equals(otherPerson.tags)
                && allergies.equals(otherPerson.allergies);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags, allergies);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("tags", tags)
                .add("allergies", allergies)
                .toString();
    }

}
