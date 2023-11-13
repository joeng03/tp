package seedu.pharmhub.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.pharmhub.commons.core.LogsCenter;
import seedu.pharmhub.commons.exceptions.DataLoadingException;
import seedu.pharmhub.model.ReadOnlyPharmHub;
import seedu.pharmhub.model.ReadOnlyUserPrefs;
import seedu.pharmhub.model.UserPrefs;

/**
 * Manages storage of PharmHub data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private PharmHubStorage pharmHubStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code PharmHubStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(PharmHubStorage pharmHubStorage, UserPrefsStorage userPrefsStorage) {
        this.pharmHubStorage = pharmHubStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ PharmHub methods ==============================

    @Override
    public Path getPharmHubFilePath() {
        return pharmHubStorage.getPharmHubFilePath();
    }

    @Override
    public Optional<ReadOnlyPharmHub> readPharmHub() throws DataLoadingException {
        return readPharmHub(pharmHubStorage.getPharmHubFilePath());
    }

    @Override
    public Optional<ReadOnlyPharmHub> readPharmHub(Path filePath) throws DataLoadingException {
        logger.fine("Attempting to read data from file: " + filePath);
        return pharmHubStorage.readPharmHub(filePath);
    }

    @Override
    public void savePharmHub(ReadOnlyPharmHub pharmHub) throws IOException {
        savePharmHub(pharmHub, pharmHubStorage.getPharmHubFilePath());
    }

    @Override
    public void savePharmHub(ReadOnlyPharmHub pharmHub, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        pharmHubStorage.savePharmHub(pharmHub, filePath);
    }

}
