package seedu.pharmhub.logic.parser;

import static seedu.pharmhub.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.pharmhub.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.pharmhub.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.pharmhub.logic.commands.ViewOrderCommand;


class ViewOrderCommandParserTest {
    private ViewOrderCommandParser parser = new ViewOrderCommandParser();

    @Test
    public void parse_validArgs_returnsViewOrderCommand() {
        assertParseSuccess(parser, "1", new ViewOrderCommand("1"));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewOrderCommand.MESSAGE_USAGE));
    }

}
