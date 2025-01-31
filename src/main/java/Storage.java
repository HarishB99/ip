import java.io.*;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
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
        // TODO: Make directory if it doesn't exist
        File file = new File(this.filePath);
        FileOutputStream fileOutputStream = new FileOutputStream(file, false);
        PrintWriter writer = new PrintWriter(fileOutputStream);
        writer.write(taskList.serialise());
        writer.close();
        fileOutputStream.close();
    }
}
