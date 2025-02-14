package bhaymax.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Random;

import org.junit.jupiter.api.Test;

import bhaymax.util.Pair;

public class TaskListTest {
    @Test
    public void isValidIndex_listIsEmpty_returnsFalse() {
        TaskList taskList = new TaskList();
        for (int i = -5; i < 10; i++) {
            assertFalse(taskList.isValidIndex(i), "List is empty. Therefore index " + i + " is invalid.");
        }
    }

    @Test
    public void isValidIndex_indexBelowZero_returnsFalse() {
        TaskList taskList = new TaskList();
        assertEquals(1, taskList.addTask(new TaskStub()));
        for (int i = -20; i < 0; i++) {
            assertFalse(taskList.isValidIndex(i), "Negative index " + i + " is invalid (cannot be negative).");
        }
    }

    @Test
    public void isValidIndex_listIsNotEmptyAndIndexIsWithinBounds_returnsTrue() {
        TaskList taskList = new TaskList();
        assertEquals(1, taskList.addTask(new TaskStub()));
        assertTrue(taskList.isValidIndex(0), "Index is within bounds");
        assertFalse(taskList.isValidIndex(1), "Index is not within bounds");
        assertEquals(2, taskList.addTask(new TaskStub()));
        assertEquals(3, taskList.addTask(new TaskStub()));
        assertTrue(taskList.isValidIndex(0), "Index is within bounds");
        assertTrue(taskList.isValidIndex(1), "Index is within bounds");
        assertTrue(taskList.isValidIndex(2), "Index is within bounds");
        assertFalse(taskList.isValidIndex(3), "Index is not within bounds");

        TaskList taskListWithRandomSize = new TaskList();
        int taskListSize = new Random().nextInt(1, 101);
        for (int i = 0; i < taskListSize; i++) {
            assertEquals(i + 1, taskListWithRandomSize.addTask(new TaskStub()));
        }

        for (int i = 0; i <= taskListSize; i++) {
            if (i == taskListSize) {
                assertFalse(taskListWithRandomSize.isValidIndex(i));
            } else {
                assertTrue(taskListWithRandomSize.isValidIndex(i));
            }
        }
    }

    @Test
    public void addTask_addToEmptyList_returnsListSizeOfOne() {
        TaskList taskList = new TaskList();
        TaskStub taskStub = new TaskStub();
        assertEquals(1, taskList.addTask(taskStub), "Adding a task to an empty list");
    }

    @Test
    public void addTask_addToNonEmptyList_returnsListSizeIncrementedByOne() {
        int initialListSize = new Random()
                .nextInt(1, 101);
        TaskList taskList = new TaskList();
        for (int i = 0; i < initialListSize; i++) {
            assertEquals(i + 1, taskList.addTask(new TaskStub()));
        }
        Task taskToBeAdded = new TaskStub();
        assertEquals(
                initialListSize + 1,
                taskList.addTask(taskToBeAdded));
    }

    @Test
    public void removeTask_removeFromEmptyList_throwsIndexOutOfBoundsException() {
        TaskList taskList = new TaskList();
        assertThrows(IndexOutOfBoundsException.class, () -> taskList.removeTask(0));
    }

    @Test
    public void removeTask_removeNonExistentIndexFromNonEmptyList_throwsIndexOutOfBoundsException() {
        TaskList taskList = new TaskList();
        assertEquals(1, taskList.addTask(new TaskStub()));
        assertThrows(IndexOutOfBoundsException.class, () -> taskList.removeTask(1));
    }

    @Test
    public void removeTask_removeExistingIndexFromNonEmptyList_returnsRemovedTask() {
        TaskList taskList = new TaskList();
        TaskStub taskStub = new TaskStub();
        assertEquals(1, taskList.addTask(taskStub));
        Pair<Task, Integer> pair = taskList.removeTask(0);
        Task removedTask = pair.first();
        Integer newTaskListSize = pair.second();
        assertEquals(taskStub, removedTask);
        assertEquals(0, newTaskListSize);
    }
}
