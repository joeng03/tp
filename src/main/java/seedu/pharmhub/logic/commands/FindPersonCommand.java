package seedu.pharmhub.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.pharmhub.commons.util.ToStringBuilder;
import seedu.pharmhub.logic.Messages;
import seedu.pharmhub.model.Model;
import seedu.pharmhub.model.allergy.Allergy;
import seedu.pharmhub.model.person.Email;
import seedu.pharmhub.model.person.NameContainsKeywordsPredicate;
import seedu.pharmhub.model.person.Person;
import seedu.pharmhub.model.person.Phone;
import seedu.pharmhub.model.tag.Tag;

/**
 * Finds and lists all persons in PharmHub whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindPersonCommand extends Command {

    public static final String COMMAND_WORD = "findp";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: findp [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [t/TAG] [no/ALLERGY]\n"
            + "At least one of the parameters must be specified.\n"
            + "Example: " + COMMAND_WORD + " n/alex Bernice Charlotte p/123456 no/Paracetamol Penicillin";

    private final List<String> nameKeywords;

    private final Predicate<Person> nameContainsKeywordsPredicate;
    private final Phone phoneToFind;
    private final Email emailToFind;
    private final Set<Tag> tagsToFind;
    private final Set<Allergy> allergiesToFind;

    /**
     * Constructor method for the find person class.
     * @param nameKeywords The name keywords of the person.
     * @param phoneToFind The phone to find.
     * @param emailToFind The email to find.
     * @param tagsToFind The tags to find.
     * @param allergiesToFind The allergies to find.
     */
    public FindPersonCommand(List<String> nameKeywords,
                             Phone phoneToFind, Email emailToFind, Set<Tag> tagsToFind, Set<Allergy> allergiesToFind) {
        this.nameKeywords = nameKeywords;
        this.nameContainsKeywordsPredicate =
                nameKeywords == null
                        ? person -> true
                        : new NameContainsKeywordsPredicate(nameKeywords);

        this.phoneToFind = phoneToFind;
        this.emailToFind = emailToFind;
        this.tagsToFind = tagsToFind;
        this.allergiesToFind = allergiesToFind;
    }

    public FindPersonCommand(
            List<String> nameKeywords
    ) {
        this(nameKeywords, null, null, null, null);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        Predicate<Person> phoneMatches = person -> phoneToFind == null
                || person.getPhone().equals(phoneToFind);

        Predicate<Person> emailMatches = person -> emailToFind == null
                || person.getEmail().equals(emailToFind);

        Predicate<Person> tagMatches = person -> tagsToFind == null
                || person.getTags().stream()
                .anyMatch(tag -> tagsToFind.stream()
                        .anyMatch(checkTag -> checkTag.equals(tag)));

        Predicate<Person> allergyMatches = person -> allergiesToFind == null
                || person.getAllergies().stream()
                .anyMatch(allergy -> allergiesToFind.stream()
                        .anyMatch(checkAllergy-> checkAllergy.isSameAllergy(allergy)));

        Predicate<Person> combined =
                nameContainsKeywordsPredicate
                        .and(phoneMatches)
                        .and(emailMatches)
                        .and(tagMatches)
                        .and(allergyMatches);

        model.updateFilteredPersonList(combined);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()),
                CommandResult.ListPanelEffects.PERSON);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindPersonCommand)) {
            return false;
        }

        FindPersonCommand otherFindCommand = (FindPersonCommand) other;
        return nameContainsKeywordsPredicate.equals(otherFindCommand.nameContainsKeywordsPredicate)
                && ((phoneToFind == null && otherFindCommand.phoneToFind == null)
                || phoneToFind.equals(otherFindCommand.phoneToFind))
                && ((emailToFind == null && otherFindCommand.emailToFind == null)
                || emailToFind.equals(otherFindCommand.emailToFind))
                && ((tagsToFind == null && otherFindCommand.tagsToFind == null)
                || tagsToFind.equals(otherFindCommand.tagsToFind))
                && ((allergiesToFind == null && otherFindCommand.allergiesToFind == null)
                || allergiesToFind.equals(otherFindCommand.allergiesToFind));
    }

    @Override
    public String toString() {
        ToStringBuilder toStringBuilder = new ToStringBuilder(this);

        if (nameKeywords != null) {
            toStringBuilder.add("nameKeywords", nameKeywords);
        }

        if (phoneToFind != null) {
            toStringBuilder.add("phone", phoneToFind);
        }
        if (emailToFind != null) {
            toStringBuilder.add("email", emailToFind);
        }
        if (tagsToFind != null) {
            toStringBuilder.add("tags", tagsToFind);
        }
        if (allergiesToFind != null) {
            toStringBuilder.add("allergies", allergiesToFind);
        }

        return toStringBuilder.toString();
    }
}
