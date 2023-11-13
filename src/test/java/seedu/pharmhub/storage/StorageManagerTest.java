package seedu.pharmhub.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.pharmhub.testutil.TypicalPersons.getTypicalPharmHub;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.pharmhub.commons.core.GuiSettings;
import seedu.pharmhub.model.PharmHub;
import seedu.pharmhub.model.ReadOnlyPharmHub;
import seedu.pharmhub.model.UserPrefs;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonPharmHubStorage pharmHubStorage = new JsonPharmHubStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(pharmHubStorage, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void pharmHubReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonPharmHubStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonPharmHubStorageTest} class.
         */
        PharmHub original = getTypicalPharmHub();
        storageManager.savePharmHub(original);
        ReadOnlyPharmHub retrieved = storageManager.readPharmHub().get();
        assertEquals(original, new PharmHub(retrieved));
    }

    @Test
    public void getPharmHubFilePath() {
        assertNotNull(storageManager.getPharmHubFilePath());
    }

}
