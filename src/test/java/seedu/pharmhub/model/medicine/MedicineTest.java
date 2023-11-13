package seedu.pharmhub.model.medicine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.pharmhub.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class MedicineTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Medicine(null));
    }

    @Test
    public void constructor_invalidMedicineName_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Medicine(""));
    }

    @Test
    public void hasShortForm_initializedWithValidShortForm_returnsTrue() {
        Medicine medicine = new Medicine("Panadol", "Pan");
        assertEquals(medicine.hasShortForm(), true);
    }
    @Test
    public void hasShortForm_notInitializedWithShortForm_returnsFalse() {
        Medicine medicine = new Medicine("Panadol");
        assertEquals(medicine.hasShortForm(), false);
    }
    @Test
    public void getShortForm_initializedWithShortForm_returnsShortForm() {
        Medicine medicine = new Medicine("Panadol", "Pan");
        assertEquals(medicine.getShortForm(), "Pan");
    }

    @Test
    public void getShortForm_notInitializedWithShortForm_returnsBlankString() {
        Medicine medicine = new Medicine("Panadol");
        assertEquals(medicine.getShortForm(), "");
    }

    @Test
    public void isValidMedicineName() {
        // null medicine name
        assertThrows(NullPointerException.class, () -> Medicine.isValidMedicineName(null));
    }

    @Test
    public void equals_sameMedicineName_returnsTrue() {
        Medicine medicine = new Medicine("Aspirin");
        Medicine medicineCopy = new Medicine("Aspirin");
        assertEquals(medicine, medicineCopy);
    }

    @Test
    public void equals_differentMedicineName_returnsFalse() {
        Medicine medicine = new Medicine("Aspirin");
        Medicine medicineCopy = new Medicine("Panadol");
        assertNotEquals(medicine, medicineCopy);
    }

    @Test
    public void isSameMedicine_sameMedicineObject_returnsTrue() {
        Medicine medicine = new Medicine("Aspirin");
        assertEquals(medicine, medicine);
    }

    @Test
    public void isSameMedicine_medicineIsNull_returnsFalse() {
        Medicine medicine = new Medicine("Aspirin");
        assertNotEquals(medicine, null);
    }
    @Test
    public void isSameMedicine_sameMedicineName_returnsTrue() {
        Medicine medicine = new Medicine("Aspirin");
        Medicine medicineCopy = new Medicine("Aspirin");
        assertEquals(medicine, medicineCopy);
    }

    @Test
    public void isSameMedicine_differentMedicineName_returnsFalse() {
        Medicine medicine = new Medicine("Aspirin");
        Medicine medicineCopy = new Medicine("Panadol");
        assertNotEquals(medicine, medicineCopy);
    }
    @Test
    public void isValidShortForm_returnsTrue() {
        assertEquals(Medicine.isValidShortForm("Pan"), true);
        assertEquals(Medicine.isValidShortForm("Asp"), true);
        assertEquals(Medicine.isValidShortForm(""), true);
    }
}
