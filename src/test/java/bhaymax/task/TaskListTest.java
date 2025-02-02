package bhaymax.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TaskListTest {
    @Test
    public void addTask_addToEmptyList_listSizeIsOne() {
        TaskList taskList = new TaskList();
        TaskStub taskStub = new TaskStub();
        assertEquals(1, taskList.addTask(taskStub));
    }
}
