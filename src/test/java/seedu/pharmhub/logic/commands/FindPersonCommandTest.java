package seedu.pharmhub.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.pharmhub.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.pharmhub.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.pharmhub.testutil.TypicalPersons.BENSON;
import static seedu.pharmhub.testutil.TypicalPersons.CARL;
import static seedu.pharmhub.testutil.TypicalPersons.ELLE;
import static seedu.pharmhub.testutil.TypicalPersons.FIONA;
import static seedu.pharmhub.testutil.TypicalPersons.getTypicalPharmHub;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.pharmhub.model.Model;
import seedu.pharmhub.model.ModelManager;
import seedu.pharmhub.model.UserPrefs;
import seedu.pharmhub.model.allergy.Allergy;
import seedu.pharmhub.model.medicine.Medicine;
import seedu.pharmhub.model.person.Email;
import seedu.pharmhub.model.person.NameContainsKeywordsPredicate;
import seedu.pharmhub.model.person.Person;
import seedu.pharmhub.model.person.Phone;
import seedu.pharmhub.model.tag.Tag;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindPersonCommandTest {
    private Model model = new ModelManager(getTypicalPharmHub(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalPharmHub(), new UserPrefs());

    @Test
    public void equals() {
        FindPersonCommand findFirstCommand = new FindPersonCommand(prepareNameKeywords("first"));
        FindPersonCommand findSecondCommand = new FindPersonCommand(prepareNameKeywords("second"));

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindPersonCommand findFirstCommandCopy = new FindPersonCommand(prepareNameKeywords("first"));
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        List<String> nameKeywords = prepareNameKeywords(" ");
        FindPersonCommand command = new FindPersonCommand(nameKeywords);
        expectedModel.updateFilteredPersonList(
                preparePredicate(nameKeywords)
        );
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, CommandResult.ListPanelEffects.PERSON);
        assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        List<String> nameKeywords = prepareNameKeywords("Kurz Elle Kunz");
        FindPersonCommand command = new FindPersonCommand(nameKeywords);
        expectedModel.updateFilteredPersonList(preparePredicate(nameKeywords));
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, CommandResult.ListPanelEffects.PERSON);
        assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywordAttributes_singlePersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        List<String> nameKeywords = prepareNameKeywords("Alice Benson");
        Set<Tag> tagsToFind = new HashSet<>(
                Arrays.asList(
                        new Tag("friends")
                ));
        Set<Allergy> allergiesToFind = new HashSet<>(
                Arrays.asList(
                        new Allergy(new Medicine("Penicillin"))
                ));

        FindPersonCommand command = new FindPersonCommand(nameKeywords, null, null, tagsToFind, allergiesToFind);
        expectedModel.updateFilteredPersonList(preparePredicate(nameKeywords));
        expectedModel.updateFilteredPersonList(person -> person.getTags().stream()
                .anyMatch(tag -> tagsToFind.stream()
                        .anyMatch(checkTag -> checkTag.equals(tag)))
        );
        expectedModel.updateFilteredPersonList(person ->
                person.getAllergies().stream()
                        .anyMatch(allergy -> allergiesToFind.stream()
                                .anyMatch(checkAllergy -> checkAllergy.equals(allergy)))
        );
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, CommandResult.ListPanelEffects.PERSON);
        assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
        assertEquals(Arrays.asList(BENSON), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleNonKeywordAttributes_singlePersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        List<String> nameKeywords = null;
        Phone phoneToFind = new Phone("95352563");
        Email emailToFind = new Email("heinz@example.com");
        FindPersonCommand command = new FindPersonCommand(nameKeywords, phoneToFind, emailToFind, null, null);
        expectedModel.updateFilteredPersonList(preparePredicate(nameKeywords));
        expectedModel.updateFilteredPersonList(person -> person.getPhone().equals(phoneToFind));
        expectedModel.updateFilteredPersonList(person -> person.getEmail().equals(emailToFind));
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, CommandResult.ListPanelEffects.PERSON);
        assertCommandSuccess(command, model, expectedCommandResult, expectedModel);
        assertEquals(Arrays.asList(CARL), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethodKeywordPredicate() {
        List<String> nameKeywords = prepareNameKeywords("keyword");

        FindPersonCommand findCommand = new FindPersonCommand(nameKeywords);
        String expected = FindPersonCommand.class.getCanonicalName() + "{nameKeywords=" + nameKeywords + "}";
        assertEquals(expected, findCommand.toString());
    }

    @Test
    public void toStringMethodMultipleAttributes() {
        List<String> nameKeywords = prepareNameKeywords("keyword");
        Email email = new Email("test@gmail.com");
        Set<Tag> tags = new HashSet<>(
                Arrays.asList(
                        new Tag("friends")
                ));
        Set<Allergy> allergies = new HashSet<>(
                Arrays.asList(
                        new Allergy(new Medicine("Penicillin"))
                ));

        FindPersonCommand findCommand = new FindPersonCommand(nameKeywords, null, email, tags, allergies);
        String expected =
                FindPersonCommand.class.getCanonicalName() + "{nameKeywords=" + nameKeywords + ", email=" + email
                        + ", tags=" + tags + ", allergies=" + allergies + "}";
        assertEquals(expected, findCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code List<String>}.
     */
    private List<String> prepareNameKeywords(String userInput) {
        return Arrays.asList(userInput.split("\\s+"));
    }

    /**
     * Parses {@code List<String>} into a {@code NameContainsKeywordsPredicate}.
     */

    private Predicate<Person> preparePredicate(List<String> nameKeywords) {
        return nameKeywords == null
                ? person -> true
                : new NameContainsKeywordsPredicate(nameKeywords);
    }
}
