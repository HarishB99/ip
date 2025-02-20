package bhaymax.parser;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;

import bhaymax.command.ClearCommand;
import bhaymax.command.ExitCommand;
import bhaymax.command.HelloCommand;
import bhaymax.exception.command.InvalidCommandFormatException;
import bhaymax.exception.command.UnrecognisedCommandException;
import bhaymax.task.TaskList;

public class ParserTest {
    public static final TaskList MOCK_TASK_LIST = mock(TaskList.class);

    @Test
    public void parse_emptyCommandString_throwsInvalidCommandFormatException() {
        assertThrows(InvalidCommandFormatException.class, () -> Parser.parse(
                "", ParserTest.MOCK_TASK_LIST));
    }

    @Test
    public void parse_unrecognisedCommand_throwsInvalidCommandFormatException() {
        assertThrows(UnrecognisedCommandException.class, () -> Parser.parse(
                "ads", ParserTest.MOCK_TASK_LIST));
    }

    @Test
    public void parse_byeCommand_returnsExitCommand() {
        try {
            assertInstanceOf(ExitCommand.class, Parser.parse("bye", ParserTest.MOCK_TASK_LIST));
            assertInstanceOf(ExitCommand.class, Parser.parse(" bye", ParserTest.MOCK_TASK_LIST));
            assertInstanceOf(ExitCommand.class, Parser.parse("bye ", ParserTest.MOCK_TASK_LIST));
            assertInstanceOf(ExitCommand.class, Parser.parse("  bye ", ParserTest.MOCK_TASK_LIST));
            assertInstanceOf(ExitCommand.class, Parser.parse("   bye     ", ParserTest.MOCK_TASK_LIST));
            assertInstanceOf(ExitCommand.class, Parser.parse("   bYe     ", ParserTest.MOCK_TASK_LIST));
            assertInstanceOf(ExitCommand.class, Parser.parse("   byE     ", ParserTest.MOCK_TASK_LIST));
            assertInstanceOf(ExitCommand.class, Parser.parse("   Bye     ", ParserTest.MOCK_TASK_LIST));
            assertInstanceOf(ExitCommand.class, Parser.parse("   BYE     ", ParserTest.MOCK_TASK_LIST));
            assertInstanceOf(ExitCommand.class, Parser.parse("BYE", ParserTest.MOCK_TASK_LIST));
            assertInstanceOf(ExitCommand.class, Parser.parse("Bye", ParserTest.MOCK_TASK_LIST));
            assertInstanceOf(ExitCommand.class, Parser.parse("bYe", ParserTest.MOCK_TASK_LIST));
            assertInstanceOf(ExitCommand.class, Parser.parse("byE", ParserTest.MOCK_TASK_LIST));
            assertThrows(UnrecognisedCommandException.class, () -> Parser.parse(
                    "by E", ParserTest.MOCK_TASK_LIST));
        } catch (InvalidCommandFormatException e) {
            fail();
        }
    }

    @Test
    public void parse_exitCommand_returnsExitCommand() {
        try {
            assertInstanceOf(ExitCommand.class, Parser.parse("exit", ParserTest.MOCK_TASK_LIST));
            assertInstanceOf(ExitCommand.class, Parser.parse(" exit", ParserTest.MOCK_TASK_LIST));
            assertInstanceOf(ExitCommand.class, Parser.parse("exit ", ParserTest.MOCK_TASK_LIST));
            assertInstanceOf(ExitCommand.class, Parser.parse("  exit ", ParserTest.MOCK_TASK_LIST));
            assertInstanceOf(ExitCommand.class, Parser.parse("         exIT      ", ParserTest.MOCK_TASK_LIST));
            assertInstanceOf(ExitCommand.class, Parser.parse("         exiT      ", ParserTest.MOCK_TASK_LIST));
            assertInstanceOf(ExitCommand.class, Parser.parse("         eXit      ", ParserTest.MOCK_TASK_LIST));
            assertInstanceOf(ExitCommand.class, Parser.parse("         exIt      ", ParserTest.MOCK_TASK_LIST));
            assertInstanceOf(ExitCommand.class, Parser.parse("         Exit      ", ParserTest.MOCK_TASK_LIST));
            assertInstanceOf(ExitCommand.class, Parser.parse("         EXIT      ", ParserTest.MOCK_TASK_LIST));
            assertInstanceOf(ExitCommand.class, Parser.parse("EXIT", ParserTest.MOCK_TASK_LIST));
            assertInstanceOf(ExitCommand.class, Parser.parse("EXIt", ParserTest.MOCK_TASK_LIST));
            assertInstanceOf(ExitCommand.class, Parser.parse("ExIt", ParserTest.MOCK_TASK_LIST));
            assertInstanceOf(ExitCommand.class, Parser.parse("Exit", ParserTest.MOCK_TASK_LIST));
            assertThrows(UnrecognisedCommandException.class, () -> Parser.parse(
                    "exi t", ParserTest.MOCK_TASK_LIST));
        } catch (InvalidCommandFormatException e) {
            fail();
        }
    }

    @Test
    public void parse_hiCommand_returnsHelloCommand() {
        try {
            assertInstanceOf(HelloCommand.class, Parser.parse("hi", ParserTest.MOCK_TASK_LIST));
            assertInstanceOf(HelloCommand.class, Parser.parse("Hi", ParserTest.MOCK_TASK_LIST));
            assertInstanceOf(HelloCommand.class, Parser.parse("hI", ParserTest.MOCK_TASK_LIST));
            assertInstanceOf(HelloCommand.class, Parser.parse("HI", ParserTest.MOCK_TASK_LIST));
            assertInstanceOf(HelloCommand.class, Parser.parse(" hi", ParserTest.MOCK_TASK_LIST));
            assertInstanceOf(HelloCommand.class, Parser.parse("   Hi", ParserTest.MOCK_TASK_LIST));
            assertInstanceOf(HelloCommand.class, Parser.parse("hI ", ParserTest.MOCK_TASK_LIST));
            assertInstanceOf(HelloCommand.class, Parser.parse("HI   ", ParserTest.MOCK_TASK_LIST));
            assertInstanceOf(HelloCommand.class, Parser.parse("  hI   ", ParserTest.MOCK_TASK_LIST));
            assertThrows(UnrecognisedCommandException.class, () -> Parser.parse(
                    "H i", ParserTest.MOCK_TASK_LIST));
        } catch (InvalidCommandFormatException e) {
            fail();
        }
    }

    @Test
    public void parse_helloCommand_returnsHelloCommand() {
        try {
            assertInstanceOf(HelloCommand.class, Parser.parse("hello", ParserTest.MOCK_TASK_LIST));
            assertInstanceOf(HelloCommand.class, Parser.parse("HELLO", ParserTest.MOCK_TASK_LIST));
            assertInstanceOf(HelloCommand.class, Parser.parse("helLo", ParserTest.MOCK_TASK_LIST));
            assertInstanceOf(HelloCommand.class, Parser.parse("Hello", ParserTest.MOCK_TASK_LIST));
            assertInstanceOf(HelloCommand.class, Parser.parse("HEllo", ParserTest.MOCK_TASK_LIST));
            assertInstanceOf(HelloCommand.class, Parser.parse(" hello", ParserTest.MOCK_TASK_LIST));
            assertInstanceOf(HelloCommand.class, Parser.parse("HELLO ", ParserTest.MOCK_TASK_LIST));
            assertInstanceOf(HelloCommand.class, Parser.parse("helLo  ", ParserTest.MOCK_TASK_LIST));
            assertInstanceOf(HelloCommand.class, Parser.parse("   Hello  ", ParserTest.MOCK_TASK_LIST));
            assertThrows(UnrecognisedCommandException.class, () -> Parser.parse(
                    "H ello", ParserTest.MOCK_TASK_LIST));
        } catch (InvalidCommandFormatException e) {
            fail();
        }
    }

    @Test
    public void parse_clearCommand_returnsClearCommand() {
        try {
            assertInstanceOf(ClearCommand.class, Parser.parse("clear", ParserTest.MOCK_TASK_LIST));
            assertInstanceOf(ClearCommand.class, Parser.parse("Clear", ParserTest.MOCK_TASK_LIST));
            assertInstanceOf(ClearCommand.class, Parser.parse(" cLear", ParserTest.MOCK_TASK_LIST));
            assertInstanceOf(ClearCommand.class, Parser.parse("clEAr ", ParserTest.MOCK_TASK_LIST));
            assertInstanceOf(ClearCommand.class, Parser.parse("CLEAR", ParserTest.MOCK_TASK_LIST));
            assertThrows(UnrecognisedCommandException.class, () -> Parser.parse(
                    "cLe ar", ParserTest.MOCK_TASK_LIST));
        } catch (InvalidCommandFormatException e) {
            fail();
        }
    }
}
