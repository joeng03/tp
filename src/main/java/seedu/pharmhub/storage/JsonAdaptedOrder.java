package seedu.pharmhub.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.pharmhub.commons.exceptions.IllegalValueException;
import seedu.pharmhub.model.medicine.Medicine;
import seedu.pharmhub.model.order.Order;
import seedu.pharmhub.model.order.OrderNumber;
import seedu.pharmhub.model.order.Status;
import seedu.pharmhub.model.person.Person;


/**
 * Jackson-friendly version of {@link Order}.
 */
public class JsonAdaptedOrder {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Order's %s field is missing!";

    private final String orderNumber;

    private final JsonAdaptedPerson person;

    private final List<JsonAdaptedMedicine> medicines = new ArrayList<>();

    private final JsonAdaptedStatus orderStatus;
    /**
     * Constructs a {@code JsonAdaptedOrder} with the given order details.
     */
    @JsonCreator
    public JsonAdaptedOrder(@JsonProperty("orderNumber") String orderNumber,
                            @JsonProperty("person") JsonAdaptedPerson person,
                            @JsonProperty("medicines") List<JsonAdaptedMedicine> medicines,
                            @JsonProperty("status") JsonAdaptedStatus orderStatus) {
        this.orderNumber = orderNumber;
        this.person = person;
        if (medicines != null) {
            this.medicines.addAll(medicines);
        }
        this.orderStatus = orderStatus;
    }

    /**
     * Converts a given {@code Order} into this class for Jackson use.
     */
    public JsonAdaptedOrder(Order order) {
        this.orderNumber = order.getOrderNumber().value;
        this.person = new JsonAdaptedPerson(order.getPerson());
        medicines.addAll(order.getMedicines().stream().map(JsonAdaptedMedicine::new).collect(Collectors.toList()));
        this.orderStatus = new JsonAdaptedStatus(order.getStatus());
    }


    /**
     * Converts this Jackson-friendly adapted order object into the model's {@code Order} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted order.
     */
    public Order toModelType() throws IllegalValueException {

        final List<Medicine> orderMedicines = new ArrayList<>();

        for (JsonAdaptedMedicine medicine : medicines) {
            orderMedicines.add(medicine.toModelType());
        }

        if (person == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Person.class.getSimpleName()));
        }
        if (orderStatus == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Status.class.getSimpleName()));
        }
        final Person p = person.toModelType();
        final Status s = orderStatus.toModelType();


        if (orderNumber == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    OrderNumber.class.getSimpleName()));
        }

        if (!OrderNumber.isValidOrderNumber(orderNumber)) {
            throw new IllegalValueException(OrderNumber.MESSAGE_CONSTRAINTS);
        }

        if (orderMedicines.size() < 1) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "medicines"));
        }

        final Set<Medicine> modelMedicines = new HashSet<>(orderMedicines);

        return new Order(new OrderNumber(orderNumber), p, modelMedicines, s);
    }

}
