package seedu.pharmhub;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import seedu.pharmhub.commons.core.Config;
import seedu.pharmhub.commons.core.LogsCenter;
import seedu.pharmhub.commons.core.Version;
import seedu.pharmhub.commons.exceptions.DataLoadingException;
import seedu.pharmhub.commons.util.ConfigUtil;
import seedu.pharmhub.commons.util.StringUtil;
import seedu.pharmhub.logic.Logic;
import seedu.pharmhub.logic.LogicManager;
import seedu.pharmhub.model.Model;
import seedu.pharmhub.model.ModelManager;
import seedu.pharmhub.model.PharmHub;
import seedu.pharmhub.model.ReadOnlyPharmHub;
import seedu.pharmhub.model.ReadOnlyUserPrefs;
import seedu.pharmhub.model.UserPrefs;
import seedu.pharmhub.model.util.SampleDataUtil;
import seedu.pharmhub.storage.JsonPharmHubStorage;
import seedu.pharmhub.storage.JsonUserPrefsStorage;
import seedu.pharmhub.storage.PharmHubStorage;
import seedu.pharmhub.storage.Storage;
import seedu.pharmhub.storage.StorageManager;
import seedu.pharmhub.storage.UserPrefsStorage;
import seedu.pharmhub.ui.Ui;
import seedu.pharmhub.ui.UiManager;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(0, 2, 2, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing PharmHub ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());
        initLogging(config);

        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        UserPrefs userPrefs = initPrefs(userPrefsStorage);
        PharmHubStorage pharmHubStorage = new JsonPharmHubStorage(userPrefs.getPharmHubFilePath());
        storage = new StorageManager(pharmHubStorage, userPrefsStorage);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model, storage);

        ui = new UiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s PharmHub and {@code userPrefs}. <br>
     * The data from the sample PharmHub will be used instead if {@code storage}'s PharmHub is not found,
     * or an empty PharmHub will be used instead if errors occur when reading {@code storage}'s PharmHub.
     */
    private Model initModelManager(Storage storage, ReadOnlyUserPrefs userPrefs) {
        logger.info("Using data file : " + storage.getPharmHubFilePath());

        Optional<ReadOnlyPharmHub> pharmHubOptional;
        ReadOnlyPharmHub initialData;
        try {
            pharmHubOptional = storage.readPharmHub();
            if (!pharmHubOptional.isPresent()) {
                logger.info("Creating a new data file " + storage.getPharmHubFilePath()
                        + " populated with a sample PharmHub.");
            }
            initialData = pharmHubOptional.orElseGet(SampleDataUtil::getSamplePharmHub);
        } catch (DataLoadingException e) {
            logger.warning("Data file at " + storage.getPharmHubFilePath() + " could not be loaded."
                    + " Will be starting with an empty PharmHub.");
            initialData = new PharmHub();
        }

        return new ModelManager(initialData, userPrefs);
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    /**
     * Returns a {@code Config} using the file at {@code configFilePath}. <br>
     * The default file path {@code Config#DEFAULT_CONFIG_FILE} will be used instead
     * if {@code configFilePath} is null.
     */
    protected Config initConfig(Path configFilePath) {
        Config initializedConfig;
        Path configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if (configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            if (!configOptional.isPresent()) {
                logger.info("Creating new config file " + configFilePathUsed);
            }
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataLoadingException e) {
            logger.warning("Config file at " + configFilePathUsed + " could not be loaded."
                    + " Using default config properties.");
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    /**
     * Returns a {@code UserPrefs} using the file at {@code storage}'s user prefs file path,
     * or a new {@code UserPrefs} with default configuration if errors occur when
     * reading from the file.
     */
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        Path prefsFilePath = storage.getUserPrefsFilePath();
        logger.info("Using preference file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            if (!prefsOptional.isPresent()) {
                logger.info("Creating new preference file " + prefsFilePath);
            }
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataLoadingException e) {
            logger.warning("Preference file at " + prefsFilePath + " could not be loaded."
                    + " Using default preferences.");
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting PharmHub " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping PharmHub ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
