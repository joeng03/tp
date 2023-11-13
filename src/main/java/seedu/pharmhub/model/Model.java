package seedu.pharmhub.model;

import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.pharmhub.commons.core.GuiSettings;
import seedu.pharmhub.model.medicine.Medicine;
import seedu.pharmhub.model.order.Order;
import seedu.pharmhub.model.person.Person;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Order> PREDICATE_SHOW_ALL_ORDERS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Medicine> PREDICATE_SHOW_ALL_MEDICINES = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' PharmHub file path.
     */
    Path getPharmHubFilePath();

    /**
     * Sets the user prefs' PharmHub file path.
     */
    void setPharmHubFilePath(Path pharmHubFilePath);

    /**
     * Replaces PharmHub data with the data in {@code PharmHub}.
     */
    void setPharmHub(ReadOnlyPharmHub pharmHub);

    /**
     * Clears the PharmHub data
     */
    void clearPharmHub();

    /** Returns the PharmHub */
    ReadOnlyPharmHub getPharmHub();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the PharmHub.
     */
    boolean hasPerson(Person person);


    /**
     * Returns true if an order with the same identity as {@code order} exists in the PharmHub.
     */
    boolean hasOrder(Order order);


    /**
     * Returns true if a medicine with the same identity as {@code medicine} exists in the PharmHub.
     */
    boolean hasMedicine(Medicine medicine);


    /**
     * Deletes the given person and the person's orders.
     * The person must exist in the PharmHub.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the PharmHub.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the PharmHub.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the PharmHub.
     */
    void setPerson(Person target, Person editedPerson);

    /**
     * Adds the given order.
     */
    void addOrder(Order order);

    /**
     * Deletes the given order.
     * The order must exist in the PharmHub.
     */
    void deleteOrder(Order target);

    /**
     * Gets the order with the specified orderNumber.
     * @param orderNumber
     * @return Order with the specified orderNumber.
     */
    Optional<Order> getOrder(String orderNumber);
    /**
     * Replaces the given order {@code target} with {@code editedOrder}.
     * {@code target} must exist in the PharmHub.
     * The Order identity of {@code editedOrder} must not be the same as another existing Order in the PharmHub.
     */
    void setOrder(Order target, Order editedOrder);

    /**
     * Adds the given medicine.
     */
    void addMedicine(Medicine medicine);

    /**
     * Deletes the given medicine.
     * The medicine must exist in the PharmHub.
     */
    void deleteMedicine(Medicine medicine);

    /**
     * Gets the medicine that has the identity as the given oven.
     * @param medicine
     * @return medicine with the same identity.
     */
    Optional<Medicine> getMedicine(Medicine medicine);

    /**
     * Replaces the given medicine {@code target} with {@code editedMedicine}.
     * {@code target} must exist in the PharmHub.
     * The Medicine identity of {@code editedMedicine} must not be the same as another existing Medicine in the
     * PharmHub.
     */
    void setMedicine(Medicine target, Medicine editedMedicine);

    boolean canUndo();

    void undo();

    boolean canRedo();

    void redo();

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /** Returns an unmodifiable view of the filtered order list */
    ObservableList<Order> getFilteredOrderList();

    /**
     * Updates the filter of the filtered order list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredOrderList(Predicate<Order> predicate);

    /** Returns an unmodifiable view of the filtered medicine list */
    ObservableList<Medicine> getFilteredMedicineList();

    /**
     * Updates the filter of the filtered medicine list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredMedicineList(Predicate<Medicine> predicate);


}
