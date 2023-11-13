package seedu.pharmhub.logic.parser;

import static seedu.pharmhub.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.pharmhub.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.pharmhub.commons.core.LogsCenter;
import seedu.pharmhub.logic.commands.AddMedicineCommand;
import seedu.pharmhub.logic.commands.AddOrderCommand;
import seedu.pharmhub.logic.commands.AddPersonCommand;
import seedu.pharmhub.logic.commands.ClearCommand;
import seedu.pharmhub.logic.commands.Command;
import seedu.pharmhub.logic.commands.DeleteMedicineCommand;
import seedu.pharmhub.logic.commands.DeleteOrderCommand;
import seedu.pharmhub.logic.commands.DeletePersonCommand;
import seedu.pharmhub.logic.commands.EditPersonCommand;
import seedu.pharmhub.logic.commands.ExitCommand;
import seedu.pharmhub.logic.commands.FindMedicineCommand;
import seedu.pharmhub.logic.commands.FindOrderCommand;
import seedu.pharmhub.logic.commands.FindPersonCommand;
import seedu.pharmhub.logic.commands.HelpCommand;
import seedu.pharmhub.logic.commands.ListMedicineCommand;
import seedu.pharmhub.logic.commands.ListOrderCommand;
import seedu.pharmhub.logic.commands.ListPeopleCommand;
import seedu.pharmhub.logic.commands.RedoCommand;
import seedu.pharmhub.logic.commands.ShortFormCommand;
import seedu.pharmhub.logic.commands.UndoCommand;
import seedu.pharmhub.logic.commands.UpdateStatusCommand;
import seedu.pharmhub.logic.commands.ViewOrderCommand;
import seedu.pharmhub.logic.commands.ViewPersonCommand;
import seedu.pharmhub.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class PharmHubParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(PharmHubParser.class);

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        // Note to developers: Change the log level in config.json to enable lower level (i.e., FINE, FINER and lower)
        // log messages such as the one below.
        // Lower level log messages are used sparingly to minimize noise in the code.
        logger.fine("Command word: " + commandWord + "; Arguments: " + arguments);

        switch (commandWord.toLowerCase()) {

        case AddPersonCommand.COMMAND_WORD:
            return new AddPersonCommandParser().parse(arguments);

        case EditPersonCommand.COMMAND_WORD:
            return new EditPersonCommandParser().parse(arguments);

        case DeletePersonCommand.COMMAND_WORD:
            return new DeletePersonCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindMedicineCommand.COMMAND_WORD:
            return new FindMedicineCommandParser().parse(arguments);

        case FindPersonCommand.COMMAND_WORD:
            return new FindPersonCommandParser().parse(arguments);

        case FindOrderCommand.COMMAND_WORD:
            return new FindOrderCommandParser().parse(arguments);

        case ListPeopleCommand.COMMAND_WORD:
            return new ListPeopleCommand();

        case ListOrderCommand.COMMAND_WORD:
            return new ListOrderCommand();

        case ListMedicineCommand.COMMAND_WORD:
            return new ListMedicineCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case AddOrderCommand.COMMAND_WORD:
            return new AddOrderCommandParser().parse(arguments);

        case ViewOrderCommand.COMMAND_WORD:
            return new ViewOrderCommandParser().parse(arguments);

        case DeleteOrderCommand.COMMAND_WORD:
            return new DeleteOrderCommandParser().parse(arguments);

        case ViewPersonCommand.COMMAND_WORD:
            return new ViewPersonCommandParser().parse(arguments);

        case UpdateStatusCommand.COMMAND_WORD:
            return new UpdateStatusCommandParser().parse(arguments);

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        case AddMedicineCommand.COMMAND_WORD:
            return new AddMedicineCommandParser().parse(arguments);

        case ShortFormCommand.COMMAND_WORD:
            return new ShortFormCommandParser().parse(arguments);

        case DeleteMedicineCommand.COMMAND_WORD:
            return new DeleteMedicineCommandParser().parse(arguments);

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
