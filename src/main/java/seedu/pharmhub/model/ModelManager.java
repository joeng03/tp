package seedu.pharmhub.model;

import static java.util.Objects.requireNonNull;
import static seedu.pharmhub.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.pharmhub.commons.core.GuiSettings;
import seedu.pharmhub.commons.core.LogsCenter;
import seedu.pharmhub.model.medicine.Medicine;
import seedu.pharmhub.model.order.Order;
import seedu.pharmhub.model.person.Person;

/**
 * Represents the in-memory model of the PharmHub data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedPharmHub pharmHub;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Order> filteredOrders;

    private final FilteredList<Medicine> filteredMedicines;

    /**
     * Initializes a ModelManager with the given PharmHub and userPrefs.
     */
    public ModelManager(ReadOnlyPharmHub pharmHub, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(pharmHub, userPrefs);

        logger.fine("Initializing with PharmHub: " + pharmHub + " and user prefs " + userPrefs);

        this.pharmHub = new VersionedPharmHub(pharmHub);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.pharmHub.getPersonList());
        filteredOrders = new FilteredList<>(this.pharmHub.getOrderList());
        filteredMedicines = new FilteredList<>(this.pharmHub.getMedicineList());
    }

    public ModelManager() {
        this(new VersionedPharmHub(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getPharmHubFilePath() {
        return userPrefs.getPharmHubFilePath();
    }

    @Override
    public void setPharmHubFilePath(Path pharmHubFilePath) {
        requireNonNull(pharmHubFilePath);
        userPrefs.setPharmHubFilePath(pharmHubFilePath);
    }

    //=========== PharmHub ================================================================================

    @Override
    public void setPharmHub(ReadOnlyPharmHub pharmHub) {
        this.pharmHub.resetData(pharmHub);
    }

    @Override
    public ReadOnlyPharmHub getPharmHub() {
        return pharmHub;
    }

    @Override
    public void clearPharmHub() {
        pharmHub.clear();
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return pharmHub.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        pharmHub.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        pharmHub.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        pharmHub.setPerson(target, editedPerson);
    }
    @Override
    public void addOrder(Order order) {
        requireNonNull(order);
        pharmHub.addOrder(order);
    }

    @Override
    public void deleteOrder(Order target) {
        pharmHub.removeOrder(target);
    }

    @Override
    public Optional<Order> getOrder(String orderNumber) {
        return pharmHub.getOrder(orderNumber);
    }
    @Override
    public boolean hasOrder(Order order) {
        requireNonNull(order);
        return pharmHub.hasOrder(order);
    }

    @Override
    public void setOrder(Order target, Order editedOrder) {
        requireAllNonNull(target, editedOrder);

        pharmHub.setOrder(target, editedOrder);
    }

    @Override
    public void addMedicine(Medicine medicine) {
        requireNonNull(medicine);
        pharmHub.addMedicine(medicine);
    }

    @Override
    public void deleteMedicine(Medicine medicine) {
        pharmHub.removeMedicine(medicine);
    }

    @Override
    public Optional<Medicine> getMedicine(Medicine medicine) {
        return pharmHub.getMedicine(medicine);
    }
    @Override
    public boolean hasMedicine(Medicine medicine) {
        requireNonNull(medicine);
        return pharmHub.hasMedicine(medicine);
    }

    @Override
    public void setMedicine(Medicine target, Medicine editedMedicine) {
        requireAllNonNull(target, editedMedicine);

        pharmHub.setMedicine(target, editedMedicine);
    }

    //=========== Undo/Redo Methods =============================================================
    @Override
    public boolean canUndo() {
        return pharmHub.canUndo();
    }

    @Override
    public void undo() {
        pharmHub.undo();
    }

    @Override
    public boolean canRedo() {
        return pharmHub.canRedo();
    }

    @Override
    public void redo() {
        pharmHub.redo();
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedPharmHub}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }


    //=========== Filtered Order List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Order} backed by the internal list of
     * {@code versionedPharmHub}
     */
    @Override
    public ObservableList<Order> getFilteredOrderList() {
        return filteredOrders;
    }

    @Override
    public void updateFilteredOrderList(Predicate<Order> predicate) {
        requireNonNull(predicate);
        filteredOrders.setPredicate(predicate);
    }

    //=========== Filtered Medicine List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Medicine} backed by the internal list of
     * {@code versionedPharmHub}
     */
    @Override
    public ObservableList<Medicine> getFilteredMedicineList() {
        return filteredMedicines;
    }

    @Override
    public void updateFilteredMedicineList(Predicate<Medicine> predicate) {
        requireNonNull(predicate);
        filteredMedicines.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return pharmHub.equals(otherModelManager.pharmHub)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons)
                && filteredOrders.equals(otherModelManager.filteredOrders);
    }

}
