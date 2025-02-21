# Bhaymax User Guide

// Update the title above to match the actual product name

// Product screenshot goes here

// Product intro goes here

## Adding deadlines

// Describe the action and its outcome.

// Give examples of usage

Example: `keyword (optional arguments)`

// A description of the expected outcome goes here

```
expected output
```

## Feature ABC

// Feature details

## Clear the chat area: `clear`

Clears the chat (box) area.

Format: `clear`

## Exiting the program: `bye` or `exit`

Exits the program.

Format: `bye` ___OR___ `exit`

## Editing the data file

The data file is first created when the first task is added to
the program. It is saved at `[JAR file
location]/data/tasks.txt`. Tasks are stored in the following
format:

`TASK_TYPE | TASK_COMPLETION_STATUS | TASK_DESCRIPTION |
[DUE_BY_DATE / START_DATE] | [END_DATE]`

- `TASK_TYPE`: The type of task. It can be one of three
  values:
  - `T` - Represents a to-do item
  - `D` - Represents a deadline item
  - `E` - Represents an event
- `TASK_COMPLETION_STATUS`:
