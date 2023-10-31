package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.medicine.Medicine;
import seedu.address.model.order.Order;
import seedu.address.model.person.Person;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";
    public static final String MESSAGE_DUPLICATE_ORDER = "Orders list contains duplicate order(s).";
    public static final String MESSAGE_DUPLICATE_MEDICINE = "Medicines list contains duplicate medicine(s)";
    public static final String MESSAGE_INVALID_PERSON = "Order(s) belongs to person not in the Persons list";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();

    private final List<JsonAdaptedOrder> orders = new ArrayList<>();

    private final List<JsonAdaptedMedicine> medicines = new ArrayList<>();


    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("persons") List<JsonAdaptedPerson> persons,
                                       @JsonProperty("orders") List<JsonAdaptedOrder> orders,
                                       @JsonProperty("medicineList") List<JsonAdaptedMedicine> medicines) {
        this.persons.addAll(persons);
        this.orders.addAll(orders);
        this.medicines.addAll(medicines);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
        orders.addAll(source.getOrderList().stream().map(JsonAdaptedOrder::new).collect(Collectors.toList()));
        medicines.addAll(source.getMedicineList().stream().map(JsonAdaptedMedicine::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (addressBook.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            addressBook.addPerson(person);
        }
        for (JsonAdaptedMedicine jsonAdaptedMedicine : medicines) {
            Medicine medicine = jsonAdaptedMedicine.toModelType();
            if (addressBook.hasMedicine(medicine)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_MEDICINE);
            }
            addressBook.addMedicine(medicine);
        }
        for (JsonAdaptedOrder jsonAdaptedOrder : orders) {
            Order order = jsonAdaptedOrder.toModelType();
            if (addressBook.hasOrder(order)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ORDER);
            }
            if (addressBook.getPersonList().stream().noneMatch(order.getPerson()::equals)) {
                throw new IllegalValueException(MESSAGE_INVALID_PERSON);
            }
            addressBook.addOrder(order);
        }
        return addressBook;
    }

}
