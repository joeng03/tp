package seedu.pharmhub.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.pharmhub.commons.exceptions.IllegalValueException;
import seedu.pharmhub.model.allergy.Allergy;

/**
 * Jackson-friendly version of {@link Allergy}.
 */
class JsonAdaptedAllergy {

    private final JsonAdaptedMedicine allergy;

    /**
     * Constructs a {@code JsonAdaptedAllergy} with the given {@code allergyName}.
     */
    @JsonCreator
    public JsonAdaptedAllergy(@JsonProperty("allergy") JsonAdaptedMedicine allergy) {
        this.allergy = allergy;
    }

    /**
     * Converts a given {@code Allergy} into this class for Jackson use.
     */
    public JsonAdaptedAllergy(Allergy source) {
        allergy = new JsonAdaptedMedicine(source.allergy);
    }


    /**
     * Converts this Jackson-friendly adapted allergy object into the model's {@code Allergy} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted allergy.
     */
    public Allergy toModelType() throws IllegalValueException {
        return new Allergy(allergy.toModelType());
    }

}
