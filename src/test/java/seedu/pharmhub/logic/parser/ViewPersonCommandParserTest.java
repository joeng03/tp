package seedu.pharmhub.logic.parser;

import static seedu.pharmhub.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.pharmhub.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.pharmhub.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.pharmhub.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.Test;

import seedu.pharmhub.logic.commands.ViewPersonCommand;


class ViewPersonCommandParserTest {
    private ViewPersonCommandParser parser = new ViewPersonCommandParser();

    @Test
    public void parse_validArgs_returnsViewPersonCommand() {
        assertParseSuccess(
                parser,
                String.valueOf(INDEX_FIRST.getOneBased()),
                new ViewPersonCommand(INDEX_FIRST));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewPersonCommand.MESSAGE_USAGE));
    }

}
