package seedu.pharmhub.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.pharmhub.commons.util.ToStringBuilder;
import seedu.pharmhub.model.InfoObject;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    /**
     * Specifies the effects that commands will have on the list panel.
     */
    public enum ListPanelEffects {
        NO_EFFECT,
        PERSON,
        ORDER,
        MEDICINE
    }

    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean exit;

    /** Effects on listPanelView */
    private final ListPanelEffects listPanelEffects;

    /** Info object to be displayed */
    private final InfoObject infoObject;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit,
                         ListPanelEffects listPanelEffects, InfoObject infoObject) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.listPanelEffects = listPanelEffects;
        this.infoObject = infoObject;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, ListPanelEffects.NO_EFFECT, null);
    }

    public CommandResult(String feedbackToUser, ListPanelEffects listPanelEffects) {
        this(feedbackToUser, false, false, listPanelEffects, null);
    }

    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        this(feedbackToUser, showHelp, exit, ListPanelEffects.NO_EFFECT, null);
    }

    public CommandResult(String feedbackToUser, InfoObject infoObject) {
        this(feedbackToUser, false, false, ListPanelEffects.NO_EFFECT, infoObject);
    }

    public ListPanelEffects getListPanelEffects() {
        return listPanelEffects;
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public InfoObject getInfoObject() {
        return infoObject;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    public boolean hasInfoObject() {
        return infoObject != null;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;

        if (infoObject == null) {
            if (otherCommandResult.infoObject != null) {
                return false;
            }
        } else {
            if (!infoObject.equals(otherCommandResult.infoObject)) {
                return false;
            }
        }

        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && showHelp == otherCommandResult.showHelp
                && exit == otherCommandResult.exit
                && listPanelEffects == otherCommandResult.listPanelEffects;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit, listPanelEffects, infoObject);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("feedbackToUser", feedbackToUser)
                .add("showHelp", showHelp)
                .add("exit", exit)
                .add("listPanelEffects", listPanelEffects)
                .add("infoObject", infoObject)
                .toString();
    }

}
