package bhaymax.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import bhaymax.exception.InvalidCommandFormatException;

public class ParserTest {
    @Test
    public void parse_emptyCommandString_exceptionThrown() {
        assertThrows(InvalidCommandFormatException.class, () -> {
            Parser.parse("", new TaskListStub());
        });
    }
}
