package seedu.pharmhub.logic.parser;

import static seedu.pharmhub.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.pharmhub.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.pharmhub.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.pharmhub.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.Test;

import seedu.pharmhub.logic.commands.DeleteMedicineCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteMedicineCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteOrderCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
class DeleteMedicineCommandParserTest {

    private DeleteMedicineCommandParser parser = new DeleteMedicineCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new DeleteMedicineCommand(INDEX_FIRST));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteMedicineCommand.MESSAGE_USAGE));
    }
}
