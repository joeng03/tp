package seedu.pharmhub.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.pharmhub.testutil.Assert.assertThrows;
import static seedu.pharmhub.testutil.TypicalPersons.ALICE;
import static seedu.pharmhub.testutil.TypicalPersons.HOON;
import static seedu.pharmhub.testutil.TypicalPersons.IDA;
import static seedu.pharmhub.testutil.TypicalPersons.getTypicalPharmHub;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.pharmhub.commons.exceptions.DataLoadingException;
import seedu.pharmhub.model.PharmHub;
import seedu.pharmhub.model.ReadOnlyPharmHub;

public class JsonPharmHubStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonPharmHubStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readPharmHub_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readPharmHub(null));
    }

    private java.util.Optional<ReadOnlyPharmHub> readPharmHub(String filePath) throws Exception {
        return new JsonPharmHubStorage(Paths.get(filePath)).readPharmHub(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readPharmHub("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> readPharmHub("notJsonFormatPharmHub.json"));
    }

    @Test
    public void readPharmHub_invalidPersonPharmHub_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readPharmHub("invalidPersonPharmHub.json"));
    }

    @Test
    public void readPharmHub_invalidAndValidPersonPharmHub_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readPharmHub("invalidAndValidPersonPharmHub.json"));
    }

    @Test
    public void readAndSavePharmHub_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempPharmHub.json");
        PharmHub original = getTypicalPharmHub();
        JsonPharmHubStorage jsonPharmHubStorage = new JsonPharmHubStorage(filePath);

        // Save in new file and read back
        jsonPharmHubStorage.savePharmHub(original, filePath);
        ReadOnlyPharmHub readBack = jsonPharmHubStorage.readPharmHub(filePath).get();
        assertEquals(original, new PharmHub(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPerson(HOON);
        original.removePerson(ALICE);
        jsonPharmHubStorage.savePharmHub(original, filePath);
        readBack = jsonPharmHubStorage.readPharmHub(filePath).get();
        assertEquals(original, new PharmHub(readBack));

        // Save and read without specifying file path
        original.addPerson(IDA);
        jsonPharmHubStorage.savePharmHub(original); // file path not specified
        readBack = jsonPharmHubStorage.readPharmHub().get(); // file path not specified
        assertEquals(original, new PharmHub(readBack));

    }

    @Test
    public void savePharmHub_nullPharmHub_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> savePharmHub(null, "SomeFile.json"));
    }

    /**
     * Saves {@code PharmHub} at the specified {@code filePath}.
     */
    private void savePharmHub(ReadOnlyPharmHub pharmHub, String filePath) {
        try {
            new JsonPharmHubStorage(Paths.get(filePath))
                    .savePharmHub(pharmHub, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void savePharmHub_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> savePharmHub(new PharmHub(), null));
    }
}
