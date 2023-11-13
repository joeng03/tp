package seedu.pharmhub.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.pharmhub.commons.exceptions.DataLoadingException;
import seedu.pharmhub.model.ReadOnlyPharmHub;

/**
 * Represents a storage for {@link seedu.pharmhub.model.PharmHub}.
 */
public interface PharmHubStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getPharmHubFilePath();

    /**
     * Returns PharmHub data as a {@link ReadOnlyPharmHub}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    Optional<ReadOnlyPharmHub> readPharmHub() throws DataLoadingException;

    /**
     * @see #getPharmHubFilePath()
     */
    Optional<ReadOnlyPharmHub> readPharmHub(Path filePath) throws DataLoadingException;

    /**
     * Saves the given {@link ReadOnlyPharmHub} to the storage.
     * @param pharmHub cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void savePharmHub(ReadOnlyPharmHub pharmHub) throws IOException;

    /**
     * @see #savePharmHub(ReadOnlyPharmHub)
     */
    void savePharmHub(ReadOnlyPharmHub pharmHub, Path filePath) throws IOException;

}
