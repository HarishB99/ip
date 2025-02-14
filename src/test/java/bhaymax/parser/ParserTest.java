package bhaymax.parser;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import bhaymax.command.ClearCommand;
import bhaymax.command.ExitCommand;
import bhaymax.command.HelloCommand;
import bhaymax.exception.InvalidCommandException;
import bhaymax.exception.InvalidCommandFormatException;
import bhaymax.task.TaskList;

public class ParserTest {
    @Test
    public void parse_emptyCommandString_throwsInvalidCommandFormatException() {
        assertThrows(InvalidCommandFormatException.class, () -> Parser.parse(
                "", new TaskListStub()));
    }

    @Test
    public void parse_unrecognisedCommand_throwsInvalidCommandFormatException() {
        assertThrows(InvalidCommandException.class, () -> Parser.parse(
                "ads", new TaskListStub()));
    }

    @Test
    public void parse_byeCommand_returnsExitCommand() {
        try {
            assertInstanceOf(ExitCommand.class, Parser.parse("bye", new TaskListStub()));
            assertInstanceOf(ExitCommand.class, Parser.parse(" bye", new TaskListStub()));
            assertInstanceOf(ExitCommand.class, Parser.parse("bye ", new TaskListStub()));
            assertInstanceOf(ExitCommand.class, Parser.parse("  bye ", new TaskListStub()));
            assertInstanceOf(ExitCommand.class, Parser.parse("   bye     ", new TaskListStub()));
            assertInstanceOf(ExitCommand.class, Parser.parse("   bYe     ", new TaskListStub()));
            assertInstanceOf(ExitCommand.class, Parser.parse("   byE     ", new TaskListStub()));
            assertInstanceOf(ExitCommand.class, Parser.parse("   Bye     ", new TaskListStub()));
            assertInstanceOf(ExitCommand.class, Parser.parse("   BYE     ", new TaskListStub()));
            assertInstanceOf(ExitCommand.class, Parser.parse("BYE", new TaskListStub()));
            assertInstanceOf(ExitCommand.class, Parser.parse("Bye", new TaskListStub()));
            assertInstanceOf(ExitCommand.class, Parser.parse("bYe", new TaskListStub()));
            assertInstanceOf(ExitCommand.class, Parser.parse("byE", new TaskListStub()));
            assertThrows(InvalidCommandException.class, () -> Parser.parse(
                    "by E", new TaskListStub()));
        } catch (InvalidCommandFormatException | InvalidCommandException e) {
            fail();
        }
    }

    @Test
    public void parse_exitCommand_returnsExitCommand() {
        try {
            assertInstanceOf(ExitCommand.class, Parser.parse("exit", new TaskListStub()));
            assertInstanceOf(ExitCommand.class, Parser.parse(" exit", new TaskListStub()));
            assertInstanceOf(ExitCommand.class, Parser.parse("exit ", new TaskListStub()));
            assertInstanceOf(ExitCommand.class, Parser.parse("  exit ", new TaskListStub()));
            assertInstanceOf(ExitCommand.class, Parser.parse("         exIT      ", new TaskListStub()));
            assertInstanceOf(ExitCommand.class, Parser.parse("         exiT      ", new TaskListStub()));
            assertInstanceOf(ExitCommand.class, Parser.parse("         eXit      ", new TaskListStub()));
            assertInstanceOf(ExitCommand.class, Parser.parse("         exIt      ", new TaskListStub()));
            assertInstanceOf(ExitCommand.class, Parser.parse("         Exit      ", new TaskListStub()));
            assertInstanceOf(ExitCommand.class, Parser.parse("         EXIT      ", new TaskListStub()));
            assertInstanceOf(ExitCommand.class, Parser.parse("EXIT", new TaskListStub()));
            assertInstanceOf(ExitCommand.class, Parser.parse("EXIt", new TaskListStub()));
            assertInstanceOf(ExitCommand.class, Parser.parse("ExIt", new TaskListStub()));
            assertInstanceOf(ExitCommand.class, Parser.parse("Exit", new TaskListStub()));
            assertThrows(InvalidCommandException.class, () -> Parser.parse(
                    "exi t", new TaskList()));
        } catch (InvalidCommandFormatException | InvalidCommandException e) {
            fail();
        }
    }

    @Test
    public void parse_hiCommand_returnsHelloCommand() {
        try {
            assertInstanceOf(HelloCommand.class, Parser.parse("hi", new TaskListStub()));
            assertInstanceOf(HelloCommand.class, Parser.parse("Hi", new TaskListStub()));
            assertInstanceOf(HelloCommand.class, Parser.parse("hI", new TaskListStub()));
            assertInstanceOf(HelloCommand.class, Parser.parse("HI", new TaskListStub()));
            assertInstanceOf(HelloCommand.class, Parser.parse(" hi", new TaskListStub()));
            assertInstanceOf(HelloCommand.class, Parser.parse("   Hi", new TaskListStub()));
            assertInstanceOf(HelloCommand.class, Parser.parse("hI ", new TaskListStub()));
            assertInstanceOf(HelloCommand.class, Parser.parse("HI   ", new TaskListStub()));
            assertInstanceOf(HelloCommand.class, Parser.parse("  hI   ", new TaskListStub()));
            assertThrows(InvalidCommandException.class, () -> Parser.parse(
                    "H i", new TaskList()));
        } catch (InvalidCommandFormatException | InvalidCommandException e) {
            fail();
        }
    }

    @Test
    public void parse_helloCommand_returnsHelloCommand() {
        try {
            assertInstanceOf(HelloCommand.class, Parser.parse("hello", new TaskListStub()));
            assertInstanceOf(HelloCommand.class, Parser.parse("HELLO", new TaskListStub()));
            assertInstanceOf(HelloCommand.class, Parser.parse("helLo", new TaskListStub()));
            assertInstanceOf(HelloCommand.class, Parser.parse("Hello", new TaskListStub()));
            assertInstanceOf(HelloCommand.class, Parser.parse("HEllo", new TaskListStub()));
            assertInstanceOf(HelloCommand.class, Parser.parse(" hello", new TaskListStub()));
            assertInstanceOf(HelloCommand.class, Parser.parse("HELLO ", new TaskListStub()));
            assertInstanceOf(HelloCommand.class, Parser.parse("helLo  ", new TaskListStub()));
            assertInstanceOf(HelloCommand.class, Parser.parse("   Hello  ", new TaskListStub()));
            assertThrows(InvalidCommandException.class, () -> Parser.parse(
                    "H ello", new TaskList()));
        } catch (InvalidCommandFormatException | InvalidCommandException e) {
            fail();
        }
    }

    @Test
    public void parse_clearCommand_returnsClearCommand() {
        try {
            assertInstanceOf(ClearCommand.class, Parser.parse("clear", new TaskListStub()));
            assertInstanceOf(ClearCommand.class, Parser.parse("Clear", new TaskListStub()));
            assertInstanceOf(ClearCommand.class, Parser.parse(" cLear", new TaskListStub()));
            assertInstanceOf(ClearCommand.class, Parser.parse("clEAr ", new TaskListStub()));
            assertInstanceOf(ClearCommand.class, Parser.parse("CLEAR", new TaskListStub()));
            assertThrows(InvalidCommandException.class, () -> Parser.parse(
                    "cLe ar", new TaskListStub()));
        } catch (InvalidCommandFormatException | InvalidCommandException e) {
            fail();
        }
    }
}
