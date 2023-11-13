package seedu.pharmhub.storage;

import static seedu.pharmhub.storage.JsonAdaptedOrder.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.pharmhub.testutil.Assert.assertThrows;
import static seedu.pharmhub.testutil.TypicalOrders.PANADOL_MEDICINE;
import static seedu.pharmhub.testutil.TypicalPersons.BENSON;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.pharmhub.commons.exceptions.IllegalValueException;
import seedu.pharmhub.model.order.OrderNumber;
import seedu.pharmhub.model.person.Person;


public class JsonAdaptedOrderTest {

    private static final String INVALID_ORDERNUMBER_NONINTEGER = "a234";
    private static final String INVALID_ORDERNUMBER_NEGATIVE = "-1234";

    private static final String VALID_ORDERNUMBER = "1234";

    private static final JsonAdaptedPerson VALID_PERSON = new JsonAdaptedPerson(BENSON);
    private static final JsonAdaptedStatus VALID_STATUS = new JsonAdaptedStatus("PENDING");

    private static final JsonAdaptedMedicine VALID_MEDICINE = new JsonAdaptedMedicine(PANADOL_MEDICINE);

    @Test
    public void toModelType_nonIntegerOrderNumber_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(INVALID_ORDERNUMBER_NONINTEGER,
                VALID_PERSON, List.of(VALID_MEDICINE), VALID_STATUS);
        String expectedMessage = OrderNumber.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_negativeIntegerOrderNumber_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(INVALID_ORDERNUMBER_NEGATIVE,
                VALID_PERSON, List.of(VALID_MEDICINE), VALID_STATUS);
        String expectedMessage = OrderNumber.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_nullOrderNumber_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(null,
                VALID_PERSON, List.of(VALID_MEDICINE), VALID_STATUS);

        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, OrderNumber.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_nullPerson_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(VALID_ORDERNUMBER,
                null, List.of(VALID_MEDICINE), VALID_STATUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Person.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_nullMedicineName_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(VALID_ORDERNUMBER,
                VALID_PERSON, null, VALID_STATUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "medicines");

        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }
}
