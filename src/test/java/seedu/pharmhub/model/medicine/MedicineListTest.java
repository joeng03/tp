package seedu.pharmhub.model.medicine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.pharmhub.testutil.Assert.assertThrows;
import static seedu.pharmhub.testutil.TypicalOrders.ASPIRIN_MEDICINE;
import static seedu.pharmhub.testutil.TypicalOrders.IBUPROFEN_MEDICINE;
import static seedu.pharmhub.testutil.TypicalOrders.PANADOL_MEDICINE;
import static seedu.pharmhub.testutil.TypicalOrders.PENICILLIN_MEDICINE;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.pharmhub.model.medicine.exceptions.DuplicateMedicineException;
import seedu.pharmhub.model.medicine.exceptions.MedicineNotFoundException;


class MedicineListTest {
    private List<Medicine> listOfMedicines = null;

    private MedicineList medicineList;

    @BeforeEach
    public void init() {
        listOfMedicines = List.of(ASPIRIN_MEDICINE, IBUPROFEN_MEDICINE, PENICILLIN_MEDICINE);
        medicineList = new MedicineList();
        for (Medicine medicine : listOfMedicines) {
            medicineList.add(medicine);
        }
    }

    @Test
    public void setMedicines_withMedicineList_successfullySetMedicines() {
        MedicineList newMedicineList = new MedicineList();
        newMedicineList.setMedicines(medicineList);
        assertEquals(medicineList, newMedicineList);
    }

    @Test
    void setMedicines_withListOfMedicines_successfullySetMedicines() {
        MedicineList newMedicineList = new MedicineList();
        newMedicineList.setMedicines(listOfMedicines);
        assertEquals(medicineList, newMedicineList);
    }

    @Test
    public void equals() {
        assertTrue(medicineList.equals(medicineList));

        assertFalse(medicineList.equals(PENICILLIN_MEDICINE));

        assertFalse(medicineList.equals(null));

        MedicineList anotherDifferentMedicineList = new MedicineList();
        anotherDifferentMedicineList.add(IBUPROFEN_MEDICINE);

        assertFalse(medicineList.equals(anotherDifferentMedicineList));

        MedicineList anotherSameMedicineList = new MedicineList();
        for (Medicine medicine : listOfMedicines) {
            anotherSameMedicineList.add(medicine);
        }
        assertTrue(medicineList.equals(anotherSameMedicineList));
    }

    @Test
    public void add_duplicateMedicine_throwsDuplicateMedicineException() {

        assertThrows(DuplicateMedicineException.class, () -> medicineList.add(ASPIRIN_MEDICINE));
    }

    @Test
    public void remove_medicineInList_successfullyRemovesMedicine() {
        medicineList.remove(ASPIRIN_MEDICINE);
        assertFalse(medicineList.contains(ASPIRIN_MEDICINE));
    }

    @Test
    public void remove_medicineNotInList_throwsMedicineNotFoundException() {
        assertThrows(MedicineNotFoundException.class, () -> medicineList.remove(PANADOL_MEDICINE));
    }

    @Test
    public void contains_medicineInList_returnsTrue() {
        assertTrue(medicineList.contains(ASPIRIN_MEDICINE));
    }

    @Test
    public void contains_medicineNotInList_returnsFalse() {
        assertFalse(medicineList.contains(PANADOL_MEDICINE));
    }

    @Test
    public void contains_nullMedicine_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> medicineList.contains(null));
    }

    @Test
    public void getMedicine_medicineInList_returnsMedicine() {
        Optional<Medicine> medicine = medicineList.getMedicine(IBUPROFEN_MEDICINE);
        assertEquals(medicine.get(), IBUPROFEN_MEDICINE);
    }

    @Test
    public void getMedicine_medicineNotInList_returnsEmptyOptional() {
        Optional<Medicine> medicine = medicineList.getMedicine(new Medicine("PANADOL"));
        assertEquals(medicine, Optional.empty());
    }

    @Test
    public void setMedicine_medicineInList_successfullySetMedicine() {
        medicineList.setMedicine(ASPIRIN_MEDICINE, PANADOL_MEDICINE);
        assertTrue(medicineList.contains(PANADOL_MEDICINE));
        assertFalse(medicineList.contains(ASPIRIN_MEDICINE));
    }

    @Test
    public void setMedicine_medicineNotInList_throwsMedicineNotFoundException() {
        assertThrows(MedicineNotFoundException.class, () -> medicineList.setMedicine(PANADOL_MEDICINE,
                ASPIRIN_MEDICINE));
    }

    @Test
    public void toStringMethod() {
        assertEquals(medicineList.toString(), listOfMedicines.toString());
    }
}
