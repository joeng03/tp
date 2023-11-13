package seedu.pharmhub.logic;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.pharmhub.commons.core.GuiSettings;
import seedu.pharmhub.commons.core.LogsCenter;
import seedu.pharmhub.logic.commands.Command;
import seedu.pharmhub.logic.commands.CommandResult;
import seedu.pharmhub.logic.commands.exceptions.CommandException;
import seedu.pharmhub.logic.parser.PharmHubParser;
import seedu.pharmhub.logic.parser.exceptions.ParseException;
import seedu.pharmhub.model.Model;
import seedu.pharmhub.model.ReadOnlyPharmHub;
import seedu.pharmhub.model.medicine.Medicine;
import seedu.pharmhub.model.order.Order;
import seedu.pharmhub.model.person.Person;
import seedu.pharmhub.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_FORMAT = "Could not save data due to the following error: %s";

    public static final String FILE_OPS_PERMISSION_ERROR_FORMAT =
            "Could not save data to file %s due to insufficient permissions to write to the file or the folder.";

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final PharmHubParser pharmHubParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        pharmHubParser = new PharmHubParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = pharmHubParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.savePharmHub(model.getPharmHub());
        } catch (AccessDeniedException e) {
            throw new CommandException(String.format(FILE_OPS_PERMISSION_ERROR_FORMAT, e.getMessage()), e);
        } catch (IOException ioe) {
            throw new CommandException(String.format(FILE_OPS_ERROR_FORMAT, ioe.getMessage()), ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyPharmHub getPharmHub() {
        return model.getPharmHub();
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public ObservableList<Order> getFilteredOrderList() {
        return model.getFilteredOrderList();
    }

    @Override
    public ObservableList<Medicine> getFilteredMedicineList() {
        return model.getFilteredMedicineList();
    }

    @Override
    public Path getPharmHubFilePath() {
        return model.getPharmHubFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}
