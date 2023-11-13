package seedu.pharmhub.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import seedu.pharmhub.model.medicine.Medicine;
import seedu.pharmhub.model.order.Order;
import seedu.pharmhub.model.order.OrderNumber;
import seedu.pharmhub.model.order.Status;

/**
 * A utility class containing a list of {@code Order} objects to be used in tests.
 */
public class TypicalOrders {
    public static final String ORDER_NUMBER_FIRST_ORDER = "1";

    public static final String ORDER_NUMBER_SECOND_ORDER = "1234";
    public static final Status STATUS_PENDING = new Status(Status.OrderStatus.PENDING);
    public static final Status STATUS_COMPLETED = new Status(Status.OrderStatus.COMPLETED);

    public static final Medicine PANADOL_MEDICINE = new Medicine("Panadol");
    public static final Medicine ASPIRIN_MEDICINE = new Medicine("Aspirin");
    public static final Medicine PARACETAMOL_MEDICINE = new Medicine("Paracetamol");
    public static final Medicine PENICILLIN_MEDICINE = new Medicine("Penicillin");
    public static final Medicine IBUPROFEN_MEDICINE = new Medicine("Ibuprofen");
    public static final Order CARL_PANADOL_ORDER = new Order(new OrderNumber("1"),
            TypicalPersons.CARL, new HashSet<>(List.of(PANADOL_MEDICINE)), STATUS_PENDING);

    public static final Order BENSON_PANADOL_ORDER = new Order(new OrderNumber("1234"),
            TypicalPersons.BENSON, new HashSet<>(List.of(PANADOL_MEDICINE)), STATUS_COMPLETED);

    public static List<Order> getTypicalOrders() {
        return new ArrayList<>(Arrays.asList(CARL_PANADOL_ORDER, BENSON_PANADOL_ORDER));
    }
}
