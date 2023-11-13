package seedu.pharmhub.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.pharmhub.commons.core.LogsCenter;
import seedu.pharmhub.commons.exceptions.DataLoadingException;
import seedu.pharmhub.commons.exceptions.IllegalValueException;
import seedu.pharmhub.commons.util.FileUtil;
import seedu.pharmhub.commons.util.JsonUtil;
import seedu.pharmhub.model.ReadOnlyPharmHub;

/**
 * A class to access PharmHub data stored as a json file on the hard disk.
 */
public class JsonPharmHubStorage implements PharmHubStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonPharmHubStorage.class);

    private Path filePath;

    public JsonPharmHubStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getPharmHubFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyPharmHub> readPharmHub() throws DataLoadingException {
        return readPharmHub(filePath);
    }

    /**
     * Similar to {@link #readPharmHub()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataLoadingException if loading the data from storage failed.
     */
    public Optional<ReadOnlyPharmHub> readPharmHub(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);

        Optional<JsonSerializablePharmHub> jsonPharmHub = JsonUtil.readJsonFile(
                filePath, JsonSerializablePharmHub.class);
        if (!jsonPharmHub.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonPharmHub.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataLoadingException(ive);
        }
    }

    @Override
    public void savePharmHub(ReadOnlyPharmHub pharmHub) throws IOException {
        savePharmHub(pharmHub, filePath);
    }

    /**
     * Similar to {@link #savePharmHub(ReadOnlyPharmHub)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void savePharmHub(ReadOnlyPharmHub pharmHub, Path filePath) throws IOException {
        requireNonNull(pharmHub);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializablePharmHub(pharmHub), filePath);
    }

}
