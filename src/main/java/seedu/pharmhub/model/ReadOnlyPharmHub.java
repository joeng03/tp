package seedu.pharmhub.model;

import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.pharmhub.model.medicine.Medicine;
import seedu.pharmhub.model.order.Order;
import seedu.pharmhub.model.person.Person;


/**
 * Unmodifiable view of an PharmHub
 */
public interface ReadOnlyPharmHub {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

    ObservableList<Order> getOrderList();

    ObservableList<Medicine> getMedicineList();

    Optional<Order> getOrder(String orderNumber);

    Optional<Medicine> getMedicine(Medicine medicine);
}
