package seedu.pharmhub.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.pharmhub.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.pharmhub.commons.exceptions.IllegalValueException;
import seedu.pharmhub.commons.util.JsonUtil;
import seedu.pharmhub.model.PharmHub;
import seedu.pharmhub.testutil.TypicalPersons;

public class JsonSerializablePharmHubTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializablePharmHubTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsPharmHub.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonPharmHub.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonPharmHub.json");

    private static final Path DUPLICATE_ORDER_FILE = TEST_DATA_FOLDER.resolve("duplicateOrderPharmHub.json");

    private static final Path INVALID_ORDERPERSON_FILE = TEST_DATA_FOLDER.resolve("invalidOrderPersonPharmHub.json");

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializablePharmHub dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializablePharmHub.class).get();
        PharmHub pharmHubFromFile = dataFromFile.toModelType();
        PharmHub typicalPersonsPharmHub = TypicalPersons.getTypicalPharmHub();
        assertEquals(pharmHubFromFile, typicalPersonsPharmHub);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializablePharmHub dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializablePharmHub.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializablePharmHub dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializablePharmHub.class).get();
        assertThrows(IllegalValueException.class, JsonSerializablePharmHub.MESSAGE_DUPLICATE_PERSON,
                dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateOrders_throwsIllegalValueException() throws Exception {
        JsonSerializablePharmHub dataFromFile = JsonUtil.readJsonFile(DUPLICATE_ORDER_FILE,
                JsonSerializablePharmHub.class).get();
        assertThrows(IllegalValueException.class, JsonSerializablePharmHub.MESSAGE_DUPLICATE_ORDER,
                dataFromFile::toModelType);
    }

    @Test
    public void toModelType_invalidOrder_throwsIllegalValueException() throws Exception {
        JsonSerializablePharmHub dataFromFile = JsonUtil.readJsonFile(INVALID_ORDERPERSON_FILE,
                JsonSerializablePharmHub.class).get();
        assertThrows(IllegalValueException.class, JsonSerializablePharmHub.MESSAGE_INVALID_PERSON,
                dataFromFile::toModelType);
    }

}
