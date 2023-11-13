package seedu.pharmhub.model.medicine;

import static java.util.Objects.requireNonNull;
import static seedu.pharmhub.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.pharmhub.model.medicine.exceptions.DuplicateMedicineException;
import seedu.pharmhub.model.medicine.exceptions.MedicineNotFoundException;


/**
 * A list of medicines that enforces uniqueness between its elements and does not allow nulls.
 * A medicine is considered unique by comparing using {@code Medicine#isSameMedicine(Medicine)}. As such, adding of
 * medicines uses Medicine#isSameMedicine(Medicine) for equality so as to ensure that the medicine being added or
 * updated is unique in terms of identity in the MedicineList.
 *
 * Supports a minimal set of list operations.
 *
 * @see Medicine#isSameMedicine(Medicine)
 */
public class MedicineList {
    private final ObservableList<Medicine> internalList = FXCollections.observableArrayList();
    private final ObservableList<Medicine> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent medicine as the given argument.
     */
    public boolean contains(Medicine toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameMedicine);
    }

    /**
     * Adds a medicine to the list.
     */
    public void add(Medicine toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateMedicineException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent medicine from the list.
     * The medicine must exist in the list.
     */
    public void remove(Medicine toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new MedicineNotFoundException();
        }
    }

    public void setMedicines(MedicineList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code medicines}.
     */
    public void setMedicines(List<Medicine> medicines) {
        requireAllNonNull(medicines);
        internalList.setAll(medicines);
    }


    /**
     * Retrieves the medicine with the same identity as given medicine specified by Medicine#isSameMedicine(Medicine)
     */
    public Optional<Medicine> getMedicine(Medicine medicine) {
        Stream<Medicine> filtered = internalList.stream().filter(m ->
                medicine.isSameMedicine(m));
        return filtered.findFirst();
    }


    /**
     * Replaces the medicine {@code target} in the list with {@code editedMedicine}.
     * {@code target} must exist in the list.
     * The medicine identity of {@code editedMedicine} must not be the same as another existing medicine in the list.
     */
    public void setMedicine(Medicine target, Medicine editedMedicine) {
        requireAllNonNull(target, editedMedicine);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new MedicineNotFoundException();
        }

        if (!target.isSameMedicine(editedMedicine) && contains(editedMedicine)) {
            throw new DuplicateMedicineException();
        }

        internalList.set(index, editedMedicine);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Medicine> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }


        // instanceof handles nulls
        if (!(other instanceof MedicineList)) {
            return false;
        }

        MedicineList medicineList = (MedicineList) other;
        return internalList.equals(medicineList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

}
