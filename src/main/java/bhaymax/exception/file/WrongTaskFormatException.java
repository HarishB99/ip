package bhaymax.exception.file;

import java.util.Arrays;

import bhaymax.task.Task;

/**
 * {@inheritDoc}
 */
public class WrongTaskFormatException extends TaskDeSerialisationException {
    public static final String MESSAGE_TEMPLATE = "%s in file should be of format "
            + "'%s " + Task.DELIMITER + " {0, 1} " + Task.DELIMITER + " {description}%s'";
    public static final String ADDITIONAL_DETAILS_TEMPLATE = " " + Task.DELIMITER + " %s";

    /**
     * Constructor for {@code WrongTaskFormatException}
     *
     * @param lineNumber the line number at which the exception occurred
     * @param taskName the name of the task being read
     * @param taskType the type of the task being read
     * @param additionalDetails (optional) additional details the task requires,
     *                          apart from name, type and description
     */
    public WrongTaskFormatException(int lineNumber, String taskName, String taskType, String ...additionalDetails) {
        super(lineNumber, String.format(
                WrongTaskFormatException.MESSAGE_TEMPLATE,
                taskName,
                taskType,
                Arrays.stream(additionalDetails)
                        .map(detail ->
                                String.format(WrongTaskFormatException.ADDITIONAL_DETAILS_TEMPLATE, detail))
                        .reduce((previousDetail, nextDetail) -> previousDetail + nextDetail)
                        .orElse("")
        ));
    }
}
