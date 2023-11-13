package seedu.pharmhub.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.pharmhub.commons.core.GuiSettings;
import seedu.pharmhub.logic.commands.CommandResult;
import seedu.pharmhub.logic.commands.exceptions.CommandException;
import seedu.pharmhub.logic.parser.exceptions.ParseException;
import seedu.pharmhub.model.ReadOnlyPharmHub;
import seedu.pharmhub.model.medicine.Medicine;
import seedu.pharmhub.model.order.Order;
import seedu.pharmhub.model.person.Person;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the PharmHub.
     *
     * @see seedu.pharmhub.model.Model#getPharmHub()
     */
    ReadOnlyPharmHub getPharmHub();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Person> getFilteredPersonList();

    /** Returns an unmodifiable view of the filtered list of orders */
    ObservableList<Order> getFilteredOrderList();

    /** Returns an unmodifiable view of the filtered list of medicines */
    ObservableList<Medicine> getFilteredMedicineList();

    /**
     * Returns the user prefs' PharmHub file path.
     */
    Path getPharmHubFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
