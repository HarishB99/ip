package bhaymax.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskListTest {
    @Test
    public void addTask_addToEmptyList_listSizeIsOne() {
        TaskList taskList = new TaskList();
        TaskStub taskStub = new TaskStub();
        assertEquals(1, taskList.addTask(taskStub));
    }
}
