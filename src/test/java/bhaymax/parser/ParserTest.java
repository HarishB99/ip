package bhaymax.parser;

import bhaymax.exception.InvalidCommandFormatException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParserTest {
    @Test
    public void parse_emptyCommandString_exceptionThrown() {
        assertThrows(InvalidCommandFormatException.class, () -> {
            Parser.parse("", new TaskListStub());
        });
    }
}
