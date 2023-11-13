package seedu.pharmhub.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.pharmhub.commons.exceptions.IllegalValueException;
import seedu.pharmhub.model.medicine.Medicine;

/**
 * Jackson-friendly version of {@link Medicine}.
 */
public class JsonAdaptedMedicine {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Medicine's %s field is missing!";

    private final String medicineName;

    private final String shortForm;

    /**
     * Constructs a {@code JsonAdaptedMedicine} with the given medicine details.
     */
    @JsonCreator
    public JsonAdaptedMedicine(@JsonProperty("medicineName") String medicineName,
                            @JsonProperty("shortForm") String shortForm) {
        this.medicineName = medicineName;
        this.shortForm = shortForm;
    }

    /**
     * Converts a given {@code Medicine} into this class for Jackson use.
     */
    public JsonAdaptedMedicine(Medicine medicine) {
        this.medicineName = medicine.getMedicineName();
        this.shortForm = medicine.getShortForm();
    }

    /**
     * Converts this Jackson-friendly adapted medicine object into the model's {@code Medicine} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted medcine.
     */
    public Medicine toModelType() throws IllegalValueException {

        if (medicineName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "medicineName"));
        }

        if (shortForm == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "shortForm"));
        }


        if (!Medicine.isValidMedicineName(medicineName)) {
            throw new IllegalValueException(Medicine.MESSAGE_MEDICINE_NAME_CONSTRAINTS);
        }

        if (!Medicine.isValidShortForm(shortForm)) {
            throw new IllegalValueException(Medicine.MESSAGE_SHORT_FORM_CONSTRAINTS);
        }

        return new Medicine(medicineName, shortForm);
    }

}
