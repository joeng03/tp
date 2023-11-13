package seedu.pharmhub.testutil;

import seedu.pharmhub.model.PharmHub;
import seedu.pharmhub.model.person.Person;

/**
 * A utility class to help with building PharmHub objects.
 * Example usage: <br>
 *     {@code PharmHub ab = new PharmHubBuilder().withPerson("John", "Doe").build();}
 */
public class PharmHubBuilder {

    private PharmHub pharmHub;

    public PharmHubBuilder() {
        pharmHub = new PharmHub();
    }

    public PharmHubBuilder(PharmHub pharmHub) {
        this.pharmHub = pharmHub;
    }

    /**
     * Adds a new {@code Person} to the {@code PharmHub} that we are building.
     */
    public PharmHubBuilder withPerson(Person person) {
        pharmHub.addPerson(person);
        return this;
    }

    public PharmHub build() {
        return pharmHub;
    }
}
