package seedu.pharmhub.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.pharmhub.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.pharmhub.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.pharmhub.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.pharmhub.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.pharmhub.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.pharmhub.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.pharmhub.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.pharmhub.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.pharmhub.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.pharmhub.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.pharmhub.testutil.TypicalPersons.getTypicalPharmHub;

import org.junit.jupiter.api.Test;

import seedu.pharmhub.commons.core.index.Index;
import seedu.pharmhub.logic.Messages;
import seedu.pharmhub.logic.commands.EditPersonCommand.EditPersonDescriptor;
import seedu.pharmhub.model.Model;
import seedu.pharmhub.model.ModelManager;
import seedu.pharmhub.model.PharmHub;
import seedu.pharmhub.model.UserPrefs;
import seedu.pharmhub.model.person.Person;
import seedu.pharmhub.testutil.EditPersonDescriptorBuilder;
import seedu.pharmhub.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditPersonCommandTest {

    private Model model = new ModelManager(getTypicalPharmHub(), new UserPrefs());


    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Person editedPerson = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
        EditPersonCommand editCommand = new EditPersonCommand(INDEX_FIRST, descriptor, false);

        String expectedMessage =
                String.format(EditPersonCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new PharmHub(model.getPharmHub()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, editedPerson);

        assertCommandSuccess(editCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        Person lastPerson = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(lastPerson);
        Person editedPerson = personInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).withAllergies("Penicillin").build();

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).withAllergies("Penicillin").build();
        EditPersonCommand editCommand = new EditPersonCommand(indexLastPerson, descriptor, false);

        String expectedMessage =
                String.format(EditPersonCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new PharmHub(model.getPharmHub()), new UserPrefs());

        expectedModel.setPerson(lastPerson, editedPerson);
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, editedPerson);

        assertCommandSuccess(editCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditPersonCommand editCommand = new EditPersonCommand(INDEX_FIRST, new EditPersonDescriptor(), false);
        Person editedPerson = model.getFilteredPersonList().get(INDEX_FIRST.getZeroBased());

        String expectedMessage =
                String.format(EditPersonCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson));
        Model expectedModel = new ModelManager(new PharmHub(model.getPharmHub()), new UserPrefs());
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, editedPerson);
        assertCommandSuccess(editCommand, model, expectedCommandResult, expectedModel);

    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST);

        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST.getZeroBased());
        Person editedPerson = new PersonBuilder(personInFilteredList).withName(VALID_NAME_BOB).build();
        EditPersonCommand editCommand = new EditPersonCommand(INDEX_FIRST,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build(), false);

        String expectedMessage =
                String.format(EditPersonCommand.MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new PharmHub(model.getPharmHub()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, editedPerson);

        assertCommandSuccess(editCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST.getZeroBased());
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(firstPerson).build();
        EditPersonCommand editCommand = new EditPersonCommand(INDEX_SECOND, descriptor, false);

        assertCommandFailure(editCommand, model,
                String.format(EditPersonCommand.MESSAGE_DUPLICATE_PERSON, descriptor.getName().get()));
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST);

        // edit person in filtered list into a duplicate in PharmHub
        Person personInList = model.getPharmHub().getPersonList().get(INDEX_SECOND.getZeroBased());
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(personInList).build();
        EditPersonCommand editCommand = new EditPersonCommand(INDEX_FIRST, descriptor, false);

        assertCommandFailure(editCommand, model,
                String.format(EditPersonCommand.MESSAGE_DUPLICATE_PERSON, descriptor.getName().get()));
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditPersonCommand editCommand = new EditPersonCommand(outOfBoundIndex, descriptor, false);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of PharmHub
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST);
        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of PharmHub list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getPharmHub().getPersonList().size());

        EditPersonCommand editCommand = new EditPersonCommand(outOfBoundIndex,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build(), false);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditPersonCommand standardCommand = new EditPersonCommand(INDEX_FIRST, DESC_AMY, false);

        // same values -> returns true
        EditPersonDescriptor copyDescriptor = new EditPersonDescriptor(DESC_AMY);
        EditPersonCommand commandWithSameValues = new EditPersonCommand(INDEX_FIRST, copyDescriptor, false);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditPersonCommand(INDEX_SECOND, DESC_AMY, false)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditPersonCommand(INDEX_FIRST, DESC_BOB, false)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        EditPersonCommand editCommand = new EditPersonCommand(index, editPersonDescriptor, false);
        String expected = EditPersonCommand.class.getCanonicalName() + "{index=" + index + ", editPersonDescriptor="
                + editPersonDescriptor + "}";
        assertEquals(expected, editCommand.toString());
    }

}
