package seedu.pharmhub.model;

import java.nio.file.Path;

import seedu.pharmhub.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getPharmHubFilePath();

}
