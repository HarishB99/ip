package bhaymax.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.StringTokenizer;

import bhaymax.exception.InvalidFileFormatException;
import bhaymax.task.Task;
import bhaymax.task.TaskList;
import bhaymax.task.Todo;
import bhaymax.task.timesensitive.Deadline;
import bhaymax.task.timesensitive.Event;

public class Storage {
    public static final String DATA_DIRECTORY = "data";
    public static final String DATA_FILE = "tasks.txt";
    private final String filePath;

    public Storage() {
        this.filePath = String.format(
                "%s/%s", Storage.DATA_DIRECTORY, Storage.DATA_FILE);
    }

    public TaskList loadTasks()
            throws InvalidFileFormatException, DateTimeParseException {
        TaskList taskList = new TaskList();
        try {
            File file = new File(this.filePath);
            Scanner sc = new Scanner(file);
            int lineNumber = 1;
            while (sc.hasNextLine()) {
                String line = sc.nextLine().strip();
                StringTokenizer tokenizer = new StringTokenizer(line, "|");
                if (!tokenizer.hasMoreTokens()) {
                    // File exists but is empty. Should be acceptable.
                    return taskList;
                }
                String taskType = tokenizer.nextToken().strip();
                if (!tokenizer.hasMoreTokens()) {
                    throw new InvalidFileFormatException("Line " + lineNumber + ": Status of task is missing");
                }
                String taskStatus = tokenizer.nextToken().strip();
                boolean taskWasDone;
                try {
                    taskWasDone = Integer.parseInt(taskStatus) == 1;
                } catch (NumberFormatException e) {
                    throw new InvalidFileFormatException(
                            "Line " + lineNumber + ": Status of task is not a valid value");
                }
                if (!tokenizer.hasMoreTokens()) {
                    throw new InvalidFileFormatException("Line " + lineNumber + ": Task description is missing");
                }
                String taskDescription = tokenizer.nextToken().strip();
                Task task;
                switch (taskType) {
                case Todo.TYPE:
                    task = new Todo(taskDescription);
                    if (taskWasDone) {
                        task.markAsDone();
                    }
                    break;
                case Deadline.TYPE:
                    if (!tokenizer.hasMoreTokens()) {
                        throw new InvalidFileFormatException(
                                "Line " + lineNumber + ": Deadline Task doesn't have a deadline");
                    }
                    String deadline = tokenizer.nextToken().strip();
                    task = new Deadline(taskDescription, deadline);
                    if (taskWasDone) {
                        task.markAsDone();
                    }
                    break;
                case Event.TYPE:
                    if (!tokenizer.hasMoreTokens()) {
                        throw new InvalidFileFormatException(
                                "Line " + lineNumber + ": Event doesn't have a start date/time");
                    }
                    String start = tokenizer.nextToken().strip();
                    if (!tokenizer.hasMoreTokens()) {
                        throw new InvalidFileFormatException(
                                "Line " + lineNumber + ": Event doesn't have an end date/time");
                    }
                    String end = tokenizer.nextToken().strip();
                    task = new Event(taskDescription, start, end);
                    if (taskWasDone) {
                        task.markAsDone();
                    }
                    break;
                default:
                    throw new InvalidFileFormatException("Line " + lineNumber + ": Unrecognised task type");
                }
                taskList.addTask(task);
                lineNumber++;
            }
            sc.close();
            return taskList;
        } catch (FileNotFoundException e) {
            return taskList;
        }
    }

    public void saveTasks(TaskList taskList) throws IOException {
        File directory = new File(Storage.DATA_DIRECTORY);
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                throw new IOException(
                        "Unable to create directory '" + Storage.DATA_DIRECTORY + "'");
            }
        }
        File file = new File(this.filePath);
        FileOutputStream fileOutputStream = new FileOutputStream(file, false);
        PrintWriter writer = new PrintWriter(fileOutputStream);
        writer.write(taskList.serialise());
        writer.close();
        fileOutputStream.close();
    }
}
