package seedu.pharmhub.model;

import java.util.ArrayDeque;

import seedu.pharmhub.model.medicine.Medicine;
import seedu.pharmhub.model.order.Order;
import seedu.pharmhub.model.person.Person;

/**
 * Wraps the {@code PharmHub} with an PharmHub that supports versioning
 */
public class VersionedPharmHub extends PharmHub {
    private final ArrayDeque<PharmHub> undoHistory;
    private final ArrayDeque<PharmHub> redoHistory;

    VersionedPharmHub() {
        super();
        undoHistory = new ArrayDeque<>();
        redoHistory = new ArrayDeque<>();
    }

    /**
     * Creates a VersionedPharmHub using the models in {@code toBeCopied} and the {@code modelManager}
     */
    public VersionedPharmHub(ReadOnlyPharmHub toBeCopied) {
        super(toBeCopied);
        undoHistory = new ArrayDeque<>();
        redoHistory = new ArrayDeque<>();
    }

    private void saveHistory() {
        if (undoHistory.size() > 30) {
            undoHistory.pollLast();
        }
        undoHistory.addFirst(new PharmHub(this));
    }

    private void clearRedoHistory() {
        redoHistory.clear();
    }

    /**
     * Returns true if the VersionedPharmHub can be reverted to the previous state
     */
    public boolean canUndo() {
        return !undoHistory.isEmpty();
    }

    /**
     * Reverts the VersionedPharmHub to the previous state
     */
    public void undo() {
        assert(!undoHistory.isEmpty());

        PharmHub prev = undoHistory.pollFirst();
        redoHistory.addFirst(new PharmHub(this));
        super.resetData(prev);
    }

    /**
     * Returns true if the VersionedPharmHub can be reverted to the next state (before the previous undo)
     */
    public boolean canRedo() {
        return !redoHistory.isEmpty();
    }

    /**
     * Reverts the VersionedPharmHub to the next state (before the previous undo)
     */
    public void redo() {
        assert(!redoHistory.isEmpty());

        PharmHub next = redoHistory.pollFirst();
        undoHistory.addFirst(new PharmHub(this));
        super.resetData(next);
    }

    //=========== Undoable functions =============================================================

    @Override
    public void clear() {
        saveHistory();
        super.clear();
        clearRedoHistory();
    }
    @Override
    public void addPerson(Person p) {
        saveHistory();
        super.addPerson(p);
        clearRedoHistory();
    }
    @Override
    public void setPerson(Person target, Person editedPerson) {
        saveHistory();
        super.setPerson(target, editedPerson);
        clearRedoHistory();
    }
    @Override
    public void removePerson(Person key) {
        saveHistory();
        super.removePerson(key);
        clearRedoHistory();
    }
    @Override
    public void addOrder(Order o) {
        saveHistory();
        super.addOrder(o);
        clearRedoHistory();
    }
    @Override
    public void removeOrder(Order key) {
        saveHistory();
        super.removeOrder(key);
        clearRedoHistory();
    }
    @Override
    public void setOrder(Order target, Order editedOrder) {
        saveHistory();
        super.setOrder(target, editedOrder);
        clearRedoHistory();
    }
    @Override
    public void addMedicine(Medicine m) {
        saveHistory();
        super.addMedicine(m);
        clearRedoHistory();
    }
    @Override
    public void removeMedicine(Medicine m) {
        saveHistory();
        super.removeMedicine(m);
        clearRedoHistory();
    }
    @Override
    public void setMedicine(Medicine target, Medicine editedMedicine) {
        saveHistory();
        super.setMedicine(target, editedMedicine);
        clearRedoHistory();
    }
}
