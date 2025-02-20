package bhaymax.parser;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import bhaymax.command.ClearCommand;
import bhaymax.command.ExitCommand;
import bhaymax.command.HelloCommand;
import bhaymax.command.ListCommand;
import bhaymax.command.SearchCommand;
import bhaymax.exception.command.EmptyCommandException;
import bhaymax.exception.command.InvalidCommandFormatException;
import bhaymax.exception.command.MissingSearchTermException;
import bhaymax.exception.command.UnrecognisedCommandException;
import bhaymax.task.TaskList;

public class ParserTest {
    public static final TaskList MOCK_TASK_LIST = mock(TaskList.class);

    @ParameterizedTest
    @ValueSource(strings = {
        "",
        " ",
        "    ",
        "           ",
    })
    public void parse_emptyCommandStringProvided_throwsEmptyCommandException(String testInput) {
        assertThrows(EmptyCommandException.class, () -> Parser.parse(
                testInput, ParserTest.MOCK_TASK_LIST));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "ads",
        "by E",
        "B y E",
        "exi t",
        "H i",
        "H ello",
        "cLe ar"
    })
    public void parse_unrecognisedCommandProvided_throwsUnrecognisedCommandException(String testInput) {
        assertThrows(UnrecognisedCommandException.class, () -> Parser.parse(
                testInput, ParserTest.MOCK_TASK_LIST));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "bye",
        " bye",
        "bye ",
        "  bye ",
        "   bye     ",
        "   bYe     ",
        "   byE     ",
        "   Bye     ",
        "   BYE     ",
        "BYE",
        "Bye",
        "bYe",
        "byE"
    })
    public void parse_validByeCommandProvided_returnsExitCommand(String testInput) {
        try {
            assertInstanceOf(ExitCommand.class, Parser.parse(testInput, ParserTest.MOCK_TASK_LIST));
        } catch (InvalidCommandFormatException e) {
            fail();
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "exit",
        " exit",
        "exit ",
        "  exit ",
        "         exIT      ",
        "         exiT      ",
        "         eXit      ",
        "         exIt      ",
        "         Exit      ",
        "         EXIT      ",
        "EXIT",
        "EXIt",
        "ExIt",
        "Exit"
    })
    public void parse_validExitCommandProvided_returnsExitCommand(String testInput) {
        try {
            assertInstanceOf(ExitCommand.class, Parser.parse(testInput, ParserTest.MOCK_TASK_LIST));
        } catch (InvalidCommandFormatException e) {
            fail();
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "hi",
        "Hi",
        "hI",
        "HI",
        " hi",
        "   Hi",
        "hI ",
        "HI   ",
        "  hI   "
    })
    public void parse_validHiCommandProvided_returnsHelloCommand(String testInput) {
        try {
            assertInstanceOf(HelloCommand.class, Parser.parse(testInput, ParserTest.MOCK_TASK_LIST));
        } catch (InvalidCommandFormatException e) {
            fail();
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "hello",
        "HELLO",
        "helLo",
        "Hello",
        "HEllo",
        " hello",
        "HELLO ",
        "helLo  ",
        "   Hello  "
    })
    public void parse_validHelloCommandProvided_returnsHelloCommand(String testInput) {
        try {
            assertInstanceOf(HelloCommand.class, Parser.parse(testInput, ParserTest.MOCK_TASK_LIST));
        } catch (InvalidCommandFormatException e) {
            fail();
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "clear",
        "Clear",
        " cLear",
        "clEAr ",
        "CLEAR"
    })
    public void parse_validClearCommandProvided_returnsClearCommand(String testInput) {
        try {
            assertInstanceOf(ClearCommand.class, Parser.parse(testInput, ParserTest.MOCK_TASK_LIST));
        } catch (InvalidCommandFormatException e) {
            fail();
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "list",
        " list",
        "list ",
        "  list ",
        "         liST      ",
        "         lisT      ",
        "         lIst      ",
        "         liSt      ",
        "         List      ",
        "         LIST      ",
        "LIST",
        "LISt",
        "LiSt",
        "List",
    })
    public void parse_validListCommandProvided_returnsListCommand(String testInput) {
        try {
            assertInstanceOf(ListCommand.class, Parser.parse(testInput, ParserTest.MOCK_TASK_LIST));
        } catch (InvalidCommandFormatException e) {
            fail();
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "search todo",
        "Search event",
        "seaRch deadLine",
        " search eveNt ",
    })
    public void parse_validSearchCommandProvided_returnsSearchCommand(String testInput) {
        try {
            assertInstanceOf(SearchCommand.class, Parser.parse(testInput, ParserTest.MOCK_TASK_LIST));
        } catch (InvalidCommandFormatException e) {
            fail();
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "search",
        "Search",
        "Search ",
        "seaRch   ",
        " search   ",
    })
    public void parse_searchCommandMissingSearchTerm_throwsMissingSearchTermException(String testInput) {
        assertThrows(MissingSearchTermException.class, () -> Parser.parse(
                testInput, ParserTest.MOCK_TASK_LIST));
    }

}
