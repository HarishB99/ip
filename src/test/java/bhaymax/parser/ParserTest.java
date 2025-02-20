package bhaymax.parser;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import bhaymax.command.ClearCommand;
import bhaymax.command.DeleteCommand;
import bhaymax.command.ExitCommand;
import bhaymax.command.HelloCommand;
import bhaymax.command.ListCommand;
import bhaymax.command.MarkCommand;
import bhaymax.command.SearchCommand;
import bhaymax.command.TodoCommand;
import bhaymax.command.UnmarkCommand;
import bhaymax.exception.command.EmptyCommandException;
import bhaymax.exception.command.InvalidCommandFormatException;
import bhaymax.exception.command.MissingSearchTermException;
import bhaymax.exception.command.MissingTodoDescriptionException;
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
        "List"
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
        " search mid-term exams "
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

    @ParameterizedTest
    @ValueSource(strings = {
        "todo stuff",
        "Todo play games",
        "todo revise for cs2103t",
        " todo complete iP ",
    })
    public void parse_validTodoCommandProvided_returnsSearchCommand(String testInput) {
        try {
            assertInstanceOf(TodoCommand.class, Parser.parse(testInput, ParserTest.MOCK_TASK_LIST));
        } catch (InvalidCommandFormatException e) {
            fail();
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "todo",
        "Todo",
        "Todo ",
        "toDo   ",
        " todo   ",
    })
    public void parse_todoCommandMissingTaskDescription_throwsMissingTodoDescriptionException(String testInput) {
        assertThrows(MissingTodoDescriptionException.class, () -> Parser.parse(
                testInput, ParserTest.MOCK_TASK_LIST));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "delete 1",
        " dElete 2",
        "delete 3 ",
        " Delete 5",
        "Delete 4",
    })
    public void parse_validDeleteCommandProvided_returnsDeleteCommand(String testInput) {
        try {
            assertInstanceOf(DeleteCommand.class, Parser.parse(testInput, ParserTest.MOCK_TASK_LIST));
        } catch (InvalidCommandFormatException e) {
            fail();
        }
    }

    public void parse_deleteCommandMissingTaskNumber_throwsMissingTaskNumberException() {

    }

    public void parse_deleteCommandTaskNumberIsNotANumber_throwsTaskIndexIsNotANumberException() {

    }

    public void parse_deleteCommandOutOfBoundsIndexProvided_throwsTaskIndexOutOfBoundsExceptions() {

    }

    @ParameterizedTest
    @ValueSource(strings = {
        "mark 1",
        " mArk 2",
        "mark 3 ",
        " Mark 5",
        "Mark 4",
    })
    public void parse_validMarkCommandProvided_returnsMarkCommand(String testInput) {
        try {
            assertInstanceOf(MarkCommand.class, Parser.parse(testInput, ParserTest.MOCK_TASK_LIST));
        } catch (InvalidCommandFormatException e) {
            fail();
        }
    }

    public void parse_markCommandMissingTaskNumber_throwsMissingTaskNumberException() {

    }

    public void parse_markCommandTaskNumberIsNotANumber_throwsTaskIndexIsNotANumberException() {

    }

    public void parse_markCommandOutOfBoundsIndexProvided_throwsTaskIndexOutOfBoundsExceptions() {

    }

    @ParameterizedTest
    @ValueSource(strings = {
        "unmark 1",
        " UnmArk 2",
        "Unmark 3 ",
        " unMark 5",
        "UNMark 4",
    })
    public void parse_validUnmarkCommandProvided_returnsUnmarkCommand(String testInput) {
        try {
            assertInstanceOf(UnmarkCommand.class, Parser.parse(testInput, ParserTest.MOCK_TASK_LIST));
        } catch (InvalidCommandFormatException e) {
            fail();
        }
    }

    public void parse_unmarkCommandMissingTaskNumber_throwsMissingTaskNumberException() {

    }

    public void parse_unmarkCommandTaskNumberIsNotANumber_throwsTaskIndexIsNotANumberException() {

    }

    public void parse_unmarkCommandOutOfBoundsIndexProvided_throwsTaskIndexOutOfBoundsExceptions() {

    }
}
