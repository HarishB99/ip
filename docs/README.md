# Bhaymax User Guide

// Product screenshot goes here

// Product intro goes here

## Greeting the chatbot (Optional)

Greets the chatbot. (And the chatbot greets you back!)

Format: `hello` __or__ `hi`

## Adding To-Do Items: `todo`

// Describe the action and its outcome.

// Give examples of usage

Example: `keyword (optional arguments)`

// A description of the expected outcome goes here

```
expected output
```

## Feature ABC

// Feature details

## Adding Deadline Items: `deadline`

## Adding Events: `event`

## Listing all tasks and events: `list`

## Filtering list of tasks and events by date: `filter`

## Search for tasks with description matching keywords: `search`

## Marking a task as completed: `mark`

## Marking a task as _incomplete_: `unmark`

## Deleting a task: `delete`

## Clear the chat area: `clear`

Clears the chat (box) area.

Format: `clear`

## Exiting the program: `bye` or `exit`

Exits the program.

Format: `bye` __or__ `exit`

## Editing the data file

The data file is automatically created when a task is added to the program.
It is saved at `[JAR file location]/data/tasks.txt`.
Tasks are stored in the following format:

`TASK_TYPE | TASK_COMPLETION_STATUS | TASK_DESCRIPTION |
[DUE_BY_DATE or START_DATE] | [END_DATE]`

- `TASK_TYPE`: The type of task. It can take one of three
  values:
  - `T` - Represents a to-do item
  - `D` - Represents a deadline item
  - `E` - Represents an event
- `TASK_COMPLETION_STATUS`: Indicates whether the task has been marked as completed. It can take one of two values:
  - `1` - Indicates the task has been marked as completed
  - `0` - Indicates the task has been marked as incomplete
- `TASK_DESCRIPTION`: A brief description about the task.
- `DUE_BY_DATE` (Required for deadline items): The date and time the deadline item is due by. Specified in the format `dd/MM/yyyy HH:mm`.
  - E.g., You need to submit a report on March 2nd, 2025, at 11:59 pm. You will input this deadline as follows:
    - `deadline submit report /by 02/03/2025 23:59`
    - Note that the time is in _24-hour format_.
- `START_DATE` (Required by events): The date and time at which the event starts.
- `END_DATE` (Required by events): The date and time at which the event ends.
