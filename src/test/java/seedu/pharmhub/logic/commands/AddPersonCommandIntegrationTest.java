package seedu.pharmhub.logic.commands;

import static seedu.pharmhub.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.pharmhub.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.pharmhub.testutil.TypicalPersons.getTypicalPharmHub;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.pharmhub.logic.Messages;
import seedu.pharmhub.model.Model;
import seedu.pharmhub.model.ModelManager;
import seedu.pharmhub.model.UserPrefs;
import seedu.pharmhub.model.person.Person;
import seedu.pharmhub.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddPersonCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalPharmHub(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() {
        Person validPerson = new PersonBuilder().build();

        Model expectedModel = new ModelManager(model.getPharmHub(), new UserPrefs());
        expectedModel.addPerson(validPerson);

        CommandResult expectedCommandResult = new CommandResult(
                String.format(AddPersonCommand.MESSAGE_SUCCESS, Messages.format(validPerson)),
                validPerson);

        assertCommandSuccess(new AddPersonCommand(validPerson), model,
                expectedCommandResult,
                expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person personInList = model.getPharmHub().getPersonList().get(0);
        assertCommandFailure(new AddPersonCommand(personInList), model,
                String.format(AddPersonCommand.MESSAGE_DUPLICATE_PERSON, personInList.getName()));
    }

}
