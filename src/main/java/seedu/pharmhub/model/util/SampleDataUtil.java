package seedu.pharmhub.model.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.pharmhub.model.PharmHub;
import seedu.pharmhub.model.ReadOnlyPharmHub;
import seedu.pharmhub.model.allergy.Allergy;
import seedu.pharmhub.model.medicine.Medicine;
import seedu.pharmhub.model.order.Order;
import seedu.pharmhub.model.person.Address;
import seedu.pharmhub.model.person.Email;
import seedu.pharmhub.model.person.Name;
import seedu.pharmhub.model.person.Person;
import seedu.pharmhub.model.person.Phone;
import seedu.pharmhub.model.tag.Tag;

/**
 * Contains utility methods for populating {@code PharmHub} with sample data.
 */
public class SampleDataUtil {

    public static final HashSet<Order> EMPTY_ORDERS = new HashSet<>();
    public static Person[] getSamplePersons() {
        return new Person[]{ new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                        new Address("Blk 30 Geylang Street 29, #06-40"),
                        getTagSet("Elderly"),
                        getAllergySet("Paracetamol", "Penicillin"), EMPTY_ORDERS
                ), new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                        new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                        getTagSet("Elderly", "Diabetes"), getAllergySet("Paracetamol", "Penicillin"),
                EMPTY_ORDERS
                ), new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                        new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                        getTagSet("Hypertension"),
                        getAllergySet("Paracetamol"), EMPTY_ORDERS
                ), new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                        new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                        getTagSet("Elderly"),
                        getAllergySet("Penicillin"), EMPTY_ORDERS
                ), new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                        new Address("Blk 47 Tampines Street 20, #17-35"),
                        getTagSet(),
                        getAllergySet("Paracetamol", "Penicillin"), EMPTY_ORDERS
                ), new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                        new Address("Blk 45 Aljunied Street 85, #11-31"),
                        getTagSet("Diabetes"),
                        getAllergySet("Paracetamol"), EMPTY_ORDERS
                )
        };
    }

    public static ReadOnlyPharmHub getSamplePharmHub() {
        PharmHub sampleAb = new PharmHub();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        sampleAb.addMedicine(new Medicine("Paracetamol", "par"));
        sampleAb.addMedicine(new Medicine("Panadol", "pan"));
        sampleAb.addMedicine(new Medicine("Penicillin", "pen"));
        sampleAb.addMedicine(new Medicine("Ibuprofen", "ibu"));
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns an allergy set containing the list of strings given.
     */
    public static Set<Allergy> getAllergySet(String... strings) {
        return Arrays.stream(strings)
                .map(x -> new Allergy(new Medicine(x)))
                .collect(Collectors.toSet());
    }


}
