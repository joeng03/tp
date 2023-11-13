package seedu.pharmhub.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.pharmhub.commons.exceptions.IllegalValueException;
import seedu.pharmhub.model.PharmHub;
import seedu.pharmhub.model.ReadOnlyPharmHub;
import seedu.pharmhub.model.allergy.Allergy;
import seedu.pharmhub.model.medicine.Medicine;
import seedu.pharmhub.model.order.Order;
import seedu.pharmhub.model.person.Person;

/**
 * An Immutable PharmHub that is serializable to JSON format.
 */
@JsonRootName(value = "pharmhub")
class JsonSerializablePharmHub {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";
    public static final String MESSAGE_DUPLICATE_ORDER = "Orders list contains duplicate order(s).";
    public static final String MESSAGE_DUPLICATE_MEDICINE = "Medicines list contains duplicate medicine(s)";
    public static final String MESSAGE_INVALID_PERSON = "Order(s) belongs to person not in the Persons list";

    public static final String MESSAGE_INVALID_MEDICINE = "Data contains medicine(s) not in the medicine list";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();

    private final List<JsonAdaptedOrder> orders = new ArrayList<>();

    private final List<JsonAdaptedMedicine> medicineList = new ArrayList<>();


    /**
     * Constructs a {@code JsonSerializablePharmHub} with the given persons.
     */
    @JsonCreator
    public JsonSerializablePharmHub(@JsonProperty("persons") List<JsonAdaptedPerson> persons,
                                       @JsonProperty("orders") List<JsonAdaptedOrder> orders,
                                       @JsonProperty("medicineList") List<JsonAdaptedMedicine> medicineList) {
        this.persons.addAll(persons);
        this.orders.addAll(orders);
        this.medicineList.addAll(medicineList);
    }

    /**
     * Converts a given {@code ReadOnlyPharmHub} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializablePharmHub}.
     */
    public JsonSerializablePharmHub(ReadOnlyPharmHub source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
        orders.addAll(source.getOrderList().stream().map(JsonAdaptedOrder::new).collect(Collectors.toList()));
        medicineList.addAll(source.getMedicineList().stream().map(JsonAdaptedMedicine::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this PharmHub into the model's {@code PharmHub} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public PharmHub toModelType() throws IllegalValueException {
        PharmHub pharmHub = new PharmHub();

        for (JsonAdaptedMedicine jsonAdaptedMedicine : medicineList) {
            Medicine medicine = jsonAdaptedMedicine.toModelType();
            if (pharmHub.hasMedicine(medicine)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_MEDICINE);
            }
            pharmHub.addMedicine(medicine);
        }


        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (pharmHub.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }

            Set<Medicine> allergyMedicines = new HashSet<>(person.getAllergies().stream()
                    .map(Allergy::getAllergy).collect(Collectors.toList()));
            Set<Medicine> convertedMedicines = getMedicines(pharmHub, allergyMedicines);

            Set<Allergy> convertedAllergies = new HashSet<>(convertedMedicines.stream().map(Allergy::new)
                    .collect(Collectors.toList()));

            Person newPerson = new Person(person.getName(), person.getPhone(), person.getEmail(),
                    person.getAddress(), person.getTags(), convertedAllergies, new HashSet<>());


            pharmHub.addPerson(newPerson);
        }



        for (JsonAdaptedOrder jsonAdaptedOrder : orders) {
            Order order = jsonAdaptedOrder.toModelType();
            if (pharmHub.hasOrder(order)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ORDER);
            }
            Person person = pharmHub.getPersonList().stream().filter(order.getPerson()::isSamePerson)
                    .findFirst().orElseThrow(() -> new IllegalValueException(MESSAGE_INVALID_PERSON));
            Set<Medicine> convertedMedicines = getMedicines(pharmHub, order.getMedicines());
            Order toAdd = new Order(order.getOrderNumber(), person, convertedMedicines, order.getStatus());
            pharmHub.addOrder(toAdd);
        }


        return pharmHub;
    }

    /**
     * Converts the given set of medicines to another set of medicines with medicine short forms expanded to
     * the full name of the medicine as it is in the given PharmHub.
     * Throws Illegal Value exception if the given medicine name or its short form cannot be found in the given
     * PharmHub.
     */
    public static Set<Medicine> getMedicines(PharmHub ab, Set<Medicine> medicines) throws IllegalValueException {
        Set<Medicine> convertedMedicines = new HashSet<>();
        for (Medicine medicine : medicines) {
            Optional<Medicine> m = ab.getMedicine(medicine);
            if (m.isEmpty()) {
                throw new IllegalValueException(MESSAGE_INVALID_MEDICINE);
            }
            convertedMedicines.add(m.get());
        }
        return convertedMedicines;
    }

}
