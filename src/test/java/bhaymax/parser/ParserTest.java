package bhaymax.parser;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import bhaymax.command.ClearCommand;
import bhaymax.command.DeadlineCommand;
import bhaymax.command.DeleteCommand;
import bhaymax.command.EventCommand;
import bhaymax.command.ExitCommand;
import bhaymax.command.FilterCommand;
import bhaymax.command.HelloCommand;
import bhaymax.command.ListCommand;
import bhaymax.command.MarkCommand;
import bhaymax.command.SearchCommand;
import bhaymax.command.TodoCommand;
import bhaymax.command.UnmarkCommand;
import bhaymax.exception.command.EmptyCommandException;
import bhaymax.exception.command.InvalidCommandFormatException;
import bhaymax.exception.command.InvalidDateTimeFormatInCommandException;
import bhaymax.exception.command.MissingSearchTermException;
import bhaymax.exception.command.MissingTaskNumberForDeleteException;
import bhaymax.exception.command.MissingTaskNumberForMarkException;
import bhaymax.exception.command.MissingTaskNumberForUnmarkException;
import bhaymax.exception.command.MissingTodoDescriptionException;
import bhaymax.exception.command.TaskIndexIsNotANumberException;
import bhaymax.exception.command.TaskIndexOutOfBoundsException;
import bhaymax.exception.command.UnrecognisedCommandException;
import bhaymax.exception.command.deadline.MissingDeadlineDescriptionException;
import bhaymax.exception.command.deadline.MissingDeadlineDueByDateException;
import bhaymax.exception.command.event.InvalidTimeRangeForEventException;
import bhaymax.exception.command.event.MissingEventDescriptionException;
import bhaymax.exception.command.event.MissingEventEndDateException;
import bhaymax.exception.command.event.MissingEventStartDateException;
import bhaymax.exception.command.filter.InvalidFilterOptionException;
import bhaymax.exception.command.filter.MissingFilterDateException;
import bhaymax.exception.command.filter.MissingFilterOptionException;
import bhaymax.exception.file.InvalidDateFormatInFileException;
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

    // TODO: Add test case for deadline, event, filter all jumbled up
    @ParameterizedTest
    @ValueSource(strings = {
        "ads",
        "by E",
        "B y E",
        "exi t",
        "H i",
        "H ello",
        "cLe ar",
        "0 unmark",
        "1 mark",
        "2 delete",
        "organise study table todo",
        "gui search"
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
        " search   "
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
        " todo complete iP "
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
        " todo   "
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
        "Delete 4"
    })
    public void parse_validDeleteCommandProvided_returnsDeleteCommand(String testInput) {
        TaskList taskList = ParserTest.MOCK_TASK_LIST;
        for (int i = 0; i < 5; i++) {
            when(taskList.isValidIndex(i)).thenReturn(true);
        }
        try {
            assertInstanceOf(DeleteCommand.class, Parser.parse(testInput, taskList));
        } catch (InvalidCommandFormatException e) {
            fail();
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "delete ",
        " dElete",
        "delete",
        " Delete",
        "Delete"
    })
    public void parse_deleteCommandMissingTaskNumber_throwsMissingTaskNumberException(String testInput) {
        assertThrows(MissingTaskNumberForDeleteException.class, ()
                -> Parser.parse(testInput, ParserTest.MOCK_TASK_LIST));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "delete -",
        " dElete a",
        "delete final exams",
        " Delete stuff",
        "Delete 0.1",
        "delete 1/3"
    })
    public void parse_deleteCommandTaskNumberIsNotANumber_throwsTaskIndexIsNotANumberException(String testInput) {
        assertThrows(TaskIndexIsNotANumberException.class, () -> Parser.parse(testInput, ParserTest.MOCK_TASK_LIST));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "delete 1",
        " dElete 2",
        "delete 3 ",
        " Delete 5",
        "Delete 4"
    })
    public void parse_deleteCommandOutOfBoundsIndexProvided_throwsTaskIndexOutOfBoundsException(String testInput) {
        TaskList taskList = ParserTest.MOCK_TASK_LIST;
        for (int i = 0; i < 5; i++) {
            when(taskList.isValidIndex(i)).thenReturn(false);
        }
        assertThrows(TaskIndexOutOfBoundsException.class, () -> Parser.parse(testInput, taskList));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "mark 1",
        " mArk 2",
        "mark 3 ",
        " Mark 5",
        "Mark 4"
    })
    public void parse_validMarkCommandProvided_returnsMarkCommand(String testInput) {
        TaskList taskList = ParserTest.MOCK_TASK_LIST;
        for (int i = 0; i < 5; i++) {
            when(taskList.isValidIndex(i)).thenReturn(true);
        }
        try {
            assertInstanceOf(MarkCommand.class, Parser.parse(testInput, taskList));
        } catch (InvalidCommandFormatException e) {
            fail();
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "mark ",
        " mArk ",
        "mark",
        " Mark",
        "Mark"
    })
    public void parse_markCommandMissingTaskNumber_throwsMissingTaskNumberException(String testInput) {
        assertThrows(MissingTaskNumberForMarkException.class, ()
                -> Parser.parse(testInput, ParserTest.MOCK_TASK_LIST));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "mark -",
        " mArk a",
        "mark complete homework ",
        " Mark exercise",
        "Mark 0.1",
        "mark 1/3"
    })
    public void parse_markCommandTaskNumberIsNotANumber_throwsTaskIndexIsNotANumberException(String testInput) {
        assertThrows(TaskIndexIsNotANumberException.class, () -> Parser.parse(testInput, ParserTest.MOCK_TASK_LIST));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "mark 1",
        " mArk 2",
        "mark 3 ",
        " Mark 5",
        "Mark 4"
    })
    public void parse_markCommandOutOfBoundsIndexProvided_throwsTaskIndexOutOfBoundsException(String testInput) {
        TaskList taskList = ParserTest.MOCK_TASK_LIST;
        for (int i = 0; i < 5; i++) {
            when(taskList.isValidIndex(i)).thenReturn(false);
        }
        assertThrows(TaskIndexOutOfBoundsException.class, () -> Parser.parse(testInput, taskList));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "unmark 1",
        " UnmArk 2",
        "Unmark 3 ",
        " unMark 5",
        "UNMark 4"
    })
    public void parse_validUnmarkCommandProvided_returnsUnmarkCommand(String testInput) {
        TaskList taskList = ParserTest.MOCK_TASK_LIST;
        for (int i = 0; i < 5; i++) {
            when(taskList.isValidIndex(i)).thenReturn(true);
        }
        try {
            assertInstanceOf(UnmarkCommand.class, Parser.parse(testInput, taskList));
        } catch (InvalidCommandFormatException e) {
            fail();
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "unmark ",
        " UnmArk",
        "Unmark  ",
        " unMark ",
        "UNMark"
    })
    public void parse_unmarkCommandMissingTaskNumber_throwsMissingTaskNumberException(String testInput) {
        assertThrows(MissingTaskNumberForUnmarkException.class, ()
                -> Parser.parse(testInput, ParserTest.MOCK_TASK_LIST));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "unmark -",
        " unmArk a",
        "unmark complete homework ",
        " unMark exercise",
        "unmark 0.1",
        "unmark 1/3"
    })
    public void parse_unmarkCommandTaskNumberIsNotANumber_throwsTaskIndexIsNotANumberException(String testInput) {
        assertThrows(TaskIndexIsNotANumberException.class, () -> Parser.parse(testInput, ParserTest.MOCK_TASK_LIST));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "unmark 1",
        " UnmArk 2",
        "Unmark 3 ",
        " unMark 5",
        "UNMark 4"
    })
    public void parse_unmarkCommandOutOfBoundsIndexProvided_throwsTaskIndexOutOfBoundsException(String testInput) {
        TaskList taskList = ParserTest.MOCK_TASK_LIST;
        for (int i = 0; i < 5; i++) {
            when(taskList.isValidIndex(i)).thenReturn(false);
        }
        assertThrows(TaskIndexOutOfBoundsException.class, () -> Parser.parse(testInput, taskList));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "deadline finish watching lectures /by 02/03/2025 12:00",
        "deadline submit report /by 01/03/2025 23:59",
        "deadline prepare for mid-terms /by 02/03/2025 23:59",
        "deadline Revise for finals /by 28/04/2025 23:59",
        "deadline buy video game on sale /by 28/02/2025 23:59"
    })
    public void parse_validDeadlineCommandProvided_returnsDeadlineCommand(String testInput) {
        try {
            assertInstanceOf(DeadlineCommand.class, Parser.parse(testInput, ParserTest.MOCK_TASK_LIST));
        } catch (InvalidCommandFormatException e) {
            fail();
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "deadline ",
        "Deadline /by 05/03/2025 23:00",
        " deadline  /by 28/02/2025 19:00  ",
        "deadline",
        "deadline /by",
        "deadline /by 28/02/2025 19:00 submit report"
    })
    public void parse_deadlineCommandMissingDescription_throwsMissingDeadlineDescriptionException(String testInput) {
        assertThrows(MissingDeadlineDescriptionException.class, ()
                -> Parser.parse(testInput, ParserTest.MOCK_TASK_LIST));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "deadline important stuff",
        " deadline important stuff 05/03/2025 23:00 ",
        " deadline important stuff by 05/03/2025 23:00 ",
    })
    public void parse_deadlineCommandMissingDueByFlag_throwsMissingDeadlineDueByDateException(String testInput) {
        assertThrows(MissingDeadlineDueByDateException.class, ()
                -> Parser.parse(testInput, ParserTest.MOCK_TASK_LIST));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "deadline important stuff /by",
        " deadline some other stuff /by ",
    })
    public void parse_deadlineCommandMissingDueByDate_throwsMissingDeadlineDueByDateException(String testInput) {
        assertThrows(MissingDeadlineDueByDateException.class, ()
                -> Parser.parse(testInput, ParserTest.MOCK_TASK_LIST));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "deadline finish watching lectures /by 02-03-2025 12:00",
        "Deadline high-priority task /by 31/03/2025 5:00",
        "Deadline high-priority task /by 31/03/2025 05:00am",
        "Deadline high-priority task /by 31/03/2025 05:00pm",
        "Deadline high-priority task /by 31/03/2025 05:00Am",
        "Deadline high-priority task /by 31/03/2025 05:00Pm",
        "Deadline high-priority task /by 31/03/2025 05:00AM",
        "Deadline high-priority task /by 31/03/2025 05:00PM",
        "Deadline high-priority task /by 31/03/2025 05:00 am",
        "Deadline high-priority task /by 31/03/2025 05:00 pm",
        "Deadline high-priority task /by 31/03/2025 05:00 Am",
        "Deadline high-priority task /by 31/03/2025 05:00 Pm",
        "Deadline high-priority task /by 31/03/2025 05:00 AM",
        "Deadline high-priority task /by 31/03/2025 05:00 PM",
        "deadline submit report /by Tue 3pm",
        "deadline prepare for mid-terms /by Tue",
        "deadline buy video game on sale /by 28/02/2025 23:59 /by 29/03/2025 12:00",
    })
    public void parse_deadlineCommandInvalidDateFormat_throwsInvalidDateTimeFormatInCommandException(String testInput) {
        assertThrows(InvalidDateTimeFormatInCommandException.class, ()
                -> Parser.parse(testInput, ParserTest.MOCK_TASK_LIST));
    }

    public void parse_validEventCommandProvided_returnsEventCommand(String testInput) {
        // TODO: Add parameterised test case annotation
        try {
            assertInstanceOf(EventCommand.class, Parser.parse(testInput, ParserTest.MOCK_TASK_LIST));
        } catch (InvalidCommandFormatException e) {
            fail();
        }
    }

    public void parse_eventCommandMissingDescription_throwsMissingEventDescriptionException(String testInput) {
        assertThrows(MissingEventDescriptionException.class, ()
                -> Parser.parse(testInput, ParserTest.MOCK_TASK_LIST));
    }

    public void parse_eventCommandMissingFromFlag_throwsMissingEventStartDateException(String testInput) {
        assertThrows(MissingEventStartDateException.class, ()
                -> Parser.parse(testInput, ParserTest.MOCK_TASK_LIST));
    }

    public void parse_eventCommandMissingStartDate_throwsMissingEventStartDateException(String testInput) {
        assertThrows(MissingEventStartDateException.class, ()
                -> Parser.parse(testInput, ParserTest.MOCK_TASK_LIST));
    }

    public void parse_eventCommandMissingToFlag_throwsMissingEventEndDateException(String testInput) {
        assertThrows(MissingEventEndDateException.class, ()
                -> Parser.parse(testInput, ParserTest.MOCK_TASK_LIST));
    }

    public void parse_eventCommandMissingEndDate_throwsMissingEventEndDateException(String testInput) {
        assertThrows(MissingEventEndDateException.class, ()
                -> Parser.parse(testInput, ParserTest.MOCK_TASK_LIST));
    }

    public void parse_eventCommandInvalidStartAndEndDate_throwsInvalidDateFormatInCommandException(String testInput) {
        assertThrows(InvalidDateFormatInFileException.class, ()
                -> Parser.parse(testInput, ParserTest.MOCK_TASK_LIST));
    }

    public void parse_eventCommandInvalidDateRange_throwsInvalidTimeRangeForEventException(String testInput) {
        assertThrows(InvalidTimeRangeForEventException.class, ()
                -> Parser.parse(testInput, ParserTest.MOCK_TASK_LIST));
    }

    public void parse_validFilterCommandProvided_returnsFilterCommand(String testInput) {
        try {
            assertInstanceOf(FilterCommand.class, Parser.parse(testInput, ParserTest.MOCK_TASK_LIST));
        } catch (InvalidCommandFormatException e) {
            fail();
        }
    }

    public void parse_filterCommandMissingOptionFlag_throwsMissingFilterOptionException(String testInput) {
        assertThrows(MissingFilterOptionException.class, ()
                -> Parser.parse(testInput, ParserTest.MOCK_TASK_LIST));
    }

    public void parse_filterCommandMissingFilterDate_throwsMissingFilterDateException(String testInput) {
        assertThrows(MissingFilterDateException.class, ()
                -> Parser.parse(testInput, ParserTest.MOCK_TASK_LIST));
    }

    public void parse_filterCommandInvalidFilterOptionFlag_throwsInvalidFilterOptionException(String testInput) {
        assertThrows(InvalidFilterOptionException.class, ()
                -> Parser.parse(testInput, ParserTest.MOCK_TASK_LIST));
    }

    public void parse_filterCommandInvalidDateFormat_throwsInvalidDateFormatInCommandException(String testInput) {
        assertThrows(InvalidDateFormatInFileException.class, ()
                -> Parser.parse(testInput, ParserTest.MOCK_TASK_LIST));
    }
}
