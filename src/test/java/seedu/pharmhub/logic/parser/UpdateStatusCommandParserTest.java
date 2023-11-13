package seedu.pharmhub.logic.parser;

import static seedu.pharmhub.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.pharmhub.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.pharmhub.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.pharmhub.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.pharmhub.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.pharmhub.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.Test;

import seedu.pharmhub.logic.commands.UpdateStatusCommand;
import seedu.pharmhub.model.order.Status;

public class UpdateStatusCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_STATUS;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateStatusCommand.MESSAGE_USAGE);
    private static final String VALID_STATUS = " s/PENDING";

    private UpdateStatusCommandParser parser = new UpdateStatusCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validInputWithStatus_returnsUpdateStatusCommand() {
        UpdateStatusCommand.EditOrderDescriptor descriptor = new UpdateStatusCommand.EditOrderDescriptor();
        descriptor.setStatus(new Status(Status.OrderStatus.PENDING));
        UpdateStatusCommand expectedCommand = new UpdateStatusCommand(INDEX_FIRST, descriptor);
        assertParseSuccess(parser, "1 " + PREFIX_STATUS + "PENDING", expectedCommand);
    }
    @Test
    public void parse_validInputWithShortForm_returnsUpdateStatusCommand() {
        UpdateStatusCommand.EditOrderDescriptor descriptor = new UpdateStatusCommand.EditOrderDescriptor();
        descriptor.setStatus(new Status(Status.OrderStatus.PENDING));
        UpdateStatusCommand expectedCommand = new UpdateStatusCommand(INDEX_FIRST, descriptor);
        assertParseSuccess(parser, "1 " + PREFIX_STATUS + "pd", expectedCommand);
    }
    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + VALID_STATUS, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + VALID_STATUS, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }
    @Test
    public void parse_invalidshortform_failure() {
        // invalid status lower case
        assertParseFailure(parser, "1 s/ww", Status.MESSAGE_CONSTRAINTS);

        // invalid status upper case
        assertParseFailure(parser, "1 s/WO", Status.MESSAGE_CONSTRAINTS);
    }
    @Test
    public void parse_missingStatusPrefix_throwsParseException() {
        // Missing PREFIX_STATUS
        String userInput = "updates 1 done";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateStatusCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        // Invalid index
        String userInput = "updates a " + PREFIX_STATUS + "PENDING";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateStatusCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidStatus_throwsParseException() {
        // Invalid status
        String userInput = "updates 1 " + PREFIX_STATUS + "INVALID_STATUS";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateStatusCommand.MESSAGE_USAGE));
    }
}
