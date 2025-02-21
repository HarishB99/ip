package bhaymax.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.Scanner;

import bhaymax.exception.TaskAlreadyExistsException;
import bhaymax.exception.file.DuplicateTaskInFileException;
import bhaymax.exception.file.FileWriteException;
import bhaymax.exception.file.InvalidDateFormatInFileException;
import bhaymax.exception.file.InvalidFileFormatException;
import bhaymax.exception.file.UnrecognisedTaskTypeException;
import bhaymax.task.Task;
import bhaymax.task.TaskList;
import bhaymax.task.Todo;
import bhaymax.task.timesensitive.Deadline;
import bhaymax.task.timesensitive.Event;

/**
 * Provides methods to load (read) and save (write) tasks from/to file respectively
 */
public class Storage {
    public static final String DATA_DIRECTORY = "data";
    public static final String DATA_FILE = "tasks.txt";
    public static final String FILE_PATH_FORMAT = "%s/%s";

    public static final int TASK_TYPE_INDEX_START = 0;
    public static final int TASK_TYPE_INDEX_END = 1;

    private final String filePath;

    /**
     * Sets up the path to the tasks file to be the default path,
     * which is a concatenation of {@link Storage#DATA_DIRECTORY}
     * and {@link Storage#DATA_FILE}
     */
    public Storage() {
        this(String.format(
                Storage.FILE_PATH_FORMAT, Storage.DATA_DIRECTORY, Storage.DATA_FILE));
    }

    /**
     * Sets up the path to the tasks file
     *
     * @param filePath path to the tasks file
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    private Optional<Task> parseLineFromFile(int lineNumber, String line)
            throws InvalidFileFormatException {
        String trimmedLine = line.trim();
        if (trimmedLine.isEmpty()) {
            return Optional.<Task>empty();
        }

        String taskType = trimmedLine.substring(
                Storage.TASK_TYPE_INDEX_START, Storage.TASK_TYPE_INDEX_END);

        return switch (taskType) {
        case Todo.TYPE -> Optional.<Task>of(Todo.deSerialise(lineNumber, trimmedLine));
        case Deadline.TYPE -> Optional.<Task>of(Deadline.deSerialise(lineNumber, trimmedLine));
        case Event.TYPE -> Optional.<Task>of(Event.deSerialise(lineNumber, trimmedLine));
        default -> throw new UnrecognisedTaskTypeException(lineNumber);
        };
    }

    /**
     * Loads tasks from the tasks file and returns a {@link TaskList} object containing said loaded tasks
     *
     * @return a {@link TaskList} object containing the loaded tasks
     * @throws InvalidFileFormatException If the data in the file is not of valid format
     * @throws DateTimeParseException If the dates provided in the file are not of recognised format
     * @see bhaymax.parser.Parser#DATETIME_INPUT_FORMAT
     */
    public TaskList loadTasks()
            throws InvalidFileFormatException {
        TaskList taskList = new TaskList();
        int lineNumber = 1;
        try {
            File file = new File(this.filePath);
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                parseLineFromFile(lineNumber, line)
                        .ifPresent(taskList::addTask);
                lineNumber++;
            }
            sc.close();
            return taskList;
        } catch (DateTimeParseException e) {
            throw new InvalidDateFormatInFileException(lineNumber);
        } catch (TaskAlreadyExistsException e) {
            throw new DuplicateTaskInFileException(lineNumber);
        } catch (FileNotFoundException e) {
            return taskList;
        }
    }

    /**
     * Saves tasks to the tasks file
     *
     * @param taskList The {@link TaskList} object containing the list of tasks to be saved
     * @throws FileWriteException If any error occurs when saving the tasks to the file
     */
    public void saveTasks(TaskList taskList) throws FileWriteException {
        try {
            boolean ignored = new File(Storage.DATA_DIRECTORY).mkdirs();
            File file = new File(this.filePath);
            FileOutputStream fileOutputStream = new FileOutputStream(file, false);
            PrintWriter writer = new PrintWriter(fileOutputStream);
            writer.write(taskList.serialise());
            writer.close();
            fileOutputStream.close();
        } catch (IOException e) {
            throw new FileWriteException(e);
        }
    }
}
