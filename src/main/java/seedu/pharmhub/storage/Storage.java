package seedu.pharmhub.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.pharmhub.commons.exceptions.DataLoadingException;
import seedu.pharmhub.model.ReadOnlyPharmHub;
import seedu.pharmhub.model.ReadOnlyUserPrefs;
import seedu.pharmhub.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends PharmHubStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataLoadingException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getPharmHubFilePath();

    @Override
    Optional<ReadOnlyPharmHub> readPharmHub() throws DataLoadingException;

    @Override
    void savePharmHub(ReadOnlyPharmHub pharmHub) throws IOException;

}
