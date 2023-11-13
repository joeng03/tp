package seedu.pharmhub.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.pharmhub.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.pharmhub.logic.parser.CliSyntax.PREFIX_ALLERGY;
import static seedu.pharmhub.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.pharmhub.logic.parser.CliSyntax.PREFIX_MEDICINE_NAME;
import static seedu.pharmhub.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.pharmhub.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.pharmhub.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.pharmhub.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.pharmhub.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.pharmhub.commons.core.index.Index;
import seedu.pharmhub.logic.commands.exceptions.CommandException;
import seedu.pharmhub.model.Model;
import seedu.pharmhub.model.PharmHub;
import seedu.pharmhub.model.medicine.Medicine;
import seedu.pharmhub.model.order.Order;
import seedu.pharmhub.model.person.NameContainsKeywordsPredicate;
import seedu.pharmhub.model.person.Person;
import seedu.pharmhub.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_ALLERGY_ASPIRIN = "Aspirin";
    public static final String VALID_ALLERGY_PENICILLIN = "Penicillin";
    public static final String VALID_STATUS_PENDING = "PENDING";
    public static final String VALID_STATUS_COMPLETED = "COMPLETED";
    public static final String VALID_SHORT_STATUS_COMPLETED = "cp";
    public static final String VALID_MEDICINE_PANADOL = "panadol";
    public static final String VALID_MEDICINE_IBUPROFEN = "ibuprofen";
    public static final String VALID_SHORT_MEDICINE_PANADOL = "pan";
    public static final String VALID_SHORT_MEDICINE_IBUPROFEN = "ibu";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;
    public static final String ALLERGY_DESC_ASPIRIN = " " + PREFIX_ALLERGY + VALID_ALLERGY_ASPIRIN;
    public static final String ALLERGY_DESC_PENICILLIN = " " + PREFIX_ALLERGY + VALID_ALLERGY_PENICILLIN;
    public static final String STATUS_DESC_PENDING = " " + PREFIX_STATUS + VALID_STATUS_PENDING;
    public static final String MEDICINE_DESC_PANADOL = " " + PREFIX_MEDICINE_NAME + VALID_MEDICINE_PANADOL;
    public static final String MEDICINE_DESC_IBUPROFEN = " " + PREFIX_MEDICINE_NAME + VALID_MEDICINE_IBUPROFEN;
    public static final String SHORT_MEDICINE_DESC_PANADOL = " " + PREFIX_MEDICINE_NAME + VALID_SHORT_MEDICINE_PANADOL;
    public static final String SHORT_MEDICINE_DESC_IBUPROFEN =
            " " + PREFIX_MEDICINE_NAME + VALID_SHORT_MEDICINE_IBUPROFEN;
    // Invalid cases
    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_STATUS_DESC = " " + PREFIX_STATUS + "DONE"; // no such status
    public static final String INVALID_SHORT_STATUS_DESC = " " + PREFIX_STATUS + "de"; // no such short form medicine
    public static final String INVALID_MEDICINE_DESC = " " + PREFIX_MEDICINE_NAME + "*!*!*"; // no such medicine
    public static final String INVALID_MEDICINE = "*!*!*"; // no such medicine
    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditPersonCommand.EditPersonDescriptor DESC_AMY;
    public static final EditPersonCommand.EditPersonDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_FRIEND)
                .withAllergies(VALID_ALLERGY_ASPIRIN, VALID_ALLERGY_PENICILLIN)
                .build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the PharmHub, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        PharmHub expectedPharmHub = new PharmHub(actualModel.getPharmHub());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedPharmHub, actualModel.getPharmHub());
        assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s PharmHub.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the order at the given {@code targetIndex} in the
     * {@code model}'s PharmHub.
     */
    public static void showOrderAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredOrderList().size());

        Order order = model.getFilteredOrderList().get(targetIndex.getZeroBased());
        model.updateFilteredOrderList(x-> x.getOrderNumber() == order.getOrderNumber());

        assertEquals(1, model.getFilteredOrderList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the medicine at the given {@code targetIndex} in the
     * {@code model}'s PharmHub.
     */
    public static void showMedicineAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredMedicineList().size());

        Medicine medicine = model.getFilteredMedicineList().get(targetIndex.getZeroBased());
        model.updateFilteredMedicineList(x-> medicine.getMedicineName().equals(x.getMedicineName()));

        assertEquals(1, model.getFilteredMedicineList().size());
    }

}
