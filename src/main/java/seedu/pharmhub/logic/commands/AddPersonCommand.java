package seedu.pharmhub.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.pharmhub.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.pharmhub.logic.parser.CliSyntax.PREFIX_ALLERGY;
import static seedu.pharmhub.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.pharmhub.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.pharmhub.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.pharmhub.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.pharmhub.commons.util.ToStringBuilder;
import seedu.pharmhub.logic.Messages;
import seedu.pharmhub.logic.commands.exceptions.CommandException;
import seedu.pharmhub.model.Model;
import seedu.pharmhub.model.allergy.Allergy;
import seedu.pharmhub.model.medicine.Medicine;
import seedu.pharmhub.model.person.Person;



/**
 * Adds a person to the PharmHub.
 */
public class AddPersonCommand extends Command {

    public static final String COMMAND_WORD = "addp";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to PharmHub. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + "[" + PREFIX_TAG + "TAG]... "
            + "[" + PREFIX_ALLERGY + "ALLERGY]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney";

    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "Person with name '%1$s' already exists in PharmHub";

    private final Person toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddPersonCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            throw new CommandException(
                    String.format(MESSAGE_DUPLICATE_PERSON, toAdd.getName()));
        }

        Set<Medicine> allergyMedicines = toAdd.getAllergies().stream()
                .map(Allergy::getAllergy).collect(Collectors.toSet());
        Set<Medicine> convertedMedicines = CommandUtil.getModelMedicine(model, allergyMedicines);

        Set<Allergy> convertedAllergies = convertedMedicines.stream().map(Allergy::new).collect(Collectors.toSet());

        Person newPerson = new Person(toAdd.getName(), toAdd.getPhone(), toAdd.getEmail(), toAdd.getAddress(),
                                    toAdd.getTags(), convertedAllergies, new HashSet<>());

        model.addPerson(newPerson);

        assert model.hasPerson(newPerson) : "Person should be added to model";

        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(newPerson)), newPerson);

    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddPersonCommand)) {
            return false;
        }

        AddPersonCommand otherAddCommand = (AddPersonCommand) other;
        return toAdd.equals(otherAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
