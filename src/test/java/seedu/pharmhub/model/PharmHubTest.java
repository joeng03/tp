package seedu.pharmhub.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.pharmhub.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.pharmhub.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.pharmhub.testutil.Assert.assertThrows;
import static seedu.pharmhub.testutil.TypicalOrders.PANADOL_MEDICINE;
import static seedu.pharmhub.testutil.TypicalPersons.ALICE;
import static seedu.pharmhub.testutil.TypicalPersons.getTypicalPharmHub;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.pharmhub.model.medicine.Medicine;
import seedu.pharmhub.model.order.Order;
import seedu.pharmhub.model.order.OrderNumber;
import seedu.pharmhub.model.order.Status;
import seedu.pharmhub.model.person.Person;
import seedu.pharmhub.model.person.exceptions.DuplicatePersonException;
import seedu.pharmhub.testutil.PersonBuilder;

public class PharmHubTest {

    private final PharmHub pharmHub = new PharmHub();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), pharmHub.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> pharmHub.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyPharmHub_replacesData() {
        PharmHub newData = getTypicalPharmHub();
        pharmHub.resetData(newData);
        assertEquals(newData, pharmHub);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        Order order = new Order(
                new OrderNumber("12"), ALICE, new HashSet<>(List.of(PANADOL_MEDICINE)),
                new Status(Status.OrderStatus.PENDING));
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        List<Order> newOrders = List.of(order);
        List<Medicine> newMedicines = List.of(PANADOL_MEDICINE);
        PharmHubStub newData = new PharmHubStub(newPersons, newOrders, newMedicines);

        assertThrows(DuplicatePersonException.class, () -> pharmHub.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> pharmHub.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInPharmHub_returnsFalse() {
        assertFalse(pharmHub.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInPharmHub_returnsTrue() {
        pharmHub.addPerson(ALICE);
        assertTrue(pharmHub.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInPharmHub_returnsTrue() {
        pharmHub.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(pharmHub.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> pharmHub.getPersonList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = PharmHub.class.getCanonicalName() + "{persons=" + pharmHub.getPersonList() + ", "
                + "orders=" + pharmHub.getOrderList() + "}";
        assertEquals(expected, pharmHub.toString());
    }

    /**
     * A stub ReadOnlyPharmHub whose persons list can violate interface constraints.
     */
    private static class PharmHubStub implements ReadOnlyPharmHub {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();

        private final ObservableList<Order> orders = FXCollections.observableArrayList();

        private final ObservableList<Medicine> medicines = FXCollections.observableArrayList();

        PharmHubStub(Collection<Person> persons, Collection<Order> orders, Collection<Medicine> medicines) {
            this.persons.setAll(persons);
            this.orders.setAll(orders);
            this.medicines.setAll(medicines);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        @Override
        public ObservableList<Order> getOrderList() {
            return orders;
        }

        @Override
        public ObservableList<Medicine> getMedicineList() {
            return medicines;
        }

        @Override
        public Optional<Order> getOrder(String orderNumber) {
            return orders.stream().filter(order ->
                    order.getOrderNumber().toString().equals(orderNumber)).findFirst();
        }

        @Override
        public Optional<Medicine> getMedicine(Medicine medicine) {
            return medicines.stream().filter(m -> medicine.isSameMedicine(m)).findFirst();
        }
    }

}
