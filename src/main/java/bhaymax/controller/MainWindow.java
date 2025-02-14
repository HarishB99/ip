package bhaymax.controller;

import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.LinkedList;
import java.util.Objects;

import bhaymax.command.Command;
import bhaymax.exception.InvalidCommandFormatException;
import bhaymax.exception.InvalidFileFormatException;
import bhaymax.parser.Parser;
import bhaymax.storage.Storage;
import bhaymax.task.TaskList;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * A controller for the MainWindow
 */
public class MainWindow {
    // Specified in milliseconds
    private static final int PAUSE_DURATION_ON_EXIT = 650;

    private static final String IMAGE_PATH_BHAYMAX_USER = "/images/bhaymax_user.png";
    private static final String IMAGE_PATH_BHAYMAX_CHATBOT_NORMAL = "/images/bhaymax_chatbot_normal.png";
    private static final String IMAGE_PATH_BHAYMAX_CHATBOT_ANNOYED = "/images/bhaymax_chatbot_annoyed.png";
    private static final String IMAGE_PATH_BHAYMAX_CHATBOT_EXCITED = "/images/bhaymax_chatbot_excited.png";
    private static final String IMAGE_PATH_BHAYMAX_CHATBOT_HAPPY = "/images/bhaymax_chatbot_happy.png";
    private static final String IMAGE_PATH_BHAYMAX_CHATBOT_SAD = "/images/bhaymax_chatbot_sad.png";

    private static final String MESSAGE_WELCOME_FORMAT = "Hello! I'm %s, your personal chatbot and task list manager!";
    private static final String MESSAGE_GREETING = "Hello! How can I help you today?";
    private static final String MESSAGE_FAREWELL = "Bye. Hope to see you again soon!";
    private static final String MESSAGE_CHAT_BOX_CLEARED = "I have cleared the chat. Is there anything else "
            + "you want me to do?";

    private static final String ERROR_MESSAGE_PREFIX = "[-]";
    private static final String ERROR_MESSAGE_MAIN_FORMAT = ERROR_MESSAGE_PREFIX + " %s";
    private static final String ERROR_MESSAGE_SUB_FORMAT = ERROR_MESSAGE_PREFIX + "   %s";
    private static final String ERROR_MESSAGE_COMMAND_INVALID_SYNTAX = "Invalid command syntax provided:";
    private static final String ERROR_MESSAGE_COMMAND_TASK_NUMBER_ISNAN = "Task number should be numerical";
    private static final String ERROR_MESSAGE_FILE_IO_ERROR = "Unable to save task to file:";
    private static final String ERROR_MESSAGE_FILE_INVALID_FORMAT = "Format of task file is incorrect:";
    private static final String ERROR_MESSAGE_CHECK_FILE_AGAIN = "Please check your task file and try again";
    private static final String ERROR_MESSAGE_TASK_INVALID_FORMAT = "Task format is incorrect:";
    private static final String ERROR_MESSAGE_TASK_INVALID_DATE_FORMAT = "Wrong date/time format";
    private static final String ERROR_MESSAGE_UNKNOWN_ERROR = "An unknown error occurred:";
    private static final String ERROR_MESSAGE_TRY_AGAIN = "Try again.";
    private static final String ERROR_MESSAGE_TRY_AGAIN_LATER = "Please try again later";
    private static final String ERROR_MESSAGE_TRY_RESTARTING_APP = "Alternatively, you can try restarting the app";

    private static final String EMPTY_STRING = "";

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private final Image userImage = new Image(
            Objects.requireNonNull(this.getClass().getResourceAsStream(MainWindow.IMAGE_PATH_BHAYMAX_USER)));
    private final Image chatbotNormalImage = new Image(
            Objects.requireNonNull(this.getClass().getResourceAsStream(MainWindow.IMAGE_PATH_BHAYMAX_CHATBOT_NORMAL)));
    private final Image chatbotAnnoyedImage = new Image(
            Objects.requireNonNull(this.getClass().getResourceAsStream(MainWindow.IMAGE_PATH_BHAYMAX_CHATBOT_ANNOYED)));
    private final Image chatbotExcitedImage = new Image(
            Objects.requireNonNull(this.getClass().getResourceAsStream(MainWindow.IMAGE_PATH_BHAYMAX_CHATBOT_EXCITED)));
    private final Image chatbotHappyImage = new Image(
            Objects.requireNonNull(this.getClass().getResourceAsStream(MainWindow.IMAGE_PATH_BHAYMAX_CHATBOT_HAPPY)));
    private final Image chatbotSadImage = new Image(
            Objects.requireNonNull(this.getClass().getResourceAsStream(MainWindow.IMAGE_PATH_BHAYMAX_CHATBOT_SAD)));

    private TaskList tasks;
    private Storage storage;
    private String appName;

    /**
     * Binds the scroll pane's height to the height of the dialog container it holds
     */
    @FXML
    public void initialize() {
        this.scrollPane.vvalueProperty().bind(this.dialogContainer.heightProperty());
    }

    /**
     * Sets the task list
     *
     * @param tasks a {@link TaskList} object
     */
    public void setTasks(TaskList tasks) {
        this.tasks = tasks;
    }

    /**
     * Sets the name of the app
     *
     * @param appName the name of the app
     */
    public void setAppName(String appName) {
        this.appName = appName;
    }

    /**
     * Sets the storage object
     *
     * @param storage the {@link Storage} object
     */
    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    /**
     * Shows a dialog box that is meant to be shown to the user at the start of the app
     */
    public void showWelcomeDialogBox() {
        this.dialogContainer.getChildren().addAll(
                this.getChatbotNormalDialog(
                        String.format(MainWindow.MESSAGE_WELCOME_FORMAT, this.appName)),
                this.getChatbotExcitedDialog(MainWindow.MESSAGE_GREETING)
        );
    }

    /**
     * Shows a dialog box to greet back to the user
     */
    public void showGreetingDialogBox() {
        this.dialogContainer.getChildren().addAll(
                this.getChatbotExcitedDialog(MainWindow.MESSAGE_GREETING)
        );
    }

    /**
     * Shows a dialog box that is meant to be shown to the user before exiting the app
     */
    public void showFarewellDialogBox() {
        this.dialogContainer.getChildren().addAll(
                this.getChatbotHappyDialog(MainWindow.MESSAGE_FAREWELL));
    }

    /**
     * Shows a dialog box with a normal face alongside the given response
     *
     * @param response A message to be shown to the user
     *                 using a dialog box from the chatbot
     */
    public void showResponse(String response) {
        this.dialogContainer.getChildren().addAll(this.getChatbotNormalDialog(response));
    }

    /**
     * Shows a dialog box with an excited face alongside the given response
     *
     * @param response A message to be shown to the user
     *                 using a dialog box from the chatbot
     */
    public void showExcitedResponse(String response) {
        this.dialogContainer.getChildren().addAll(this.getChatbotExcitedDialog(response));
    }

    /**
     * Shows a dialog box with a sad face alongside the given response
     *
     * @param response A message to be shown to the user
     *                 using a dialog box from the chatbot
     */
    public void showSadResponse(String response) {
        this.dialogContainer.getChildren().addAll(this.getChatbotSadDialog(response));
    }

    /**
     * Shows the appropriate response for when InvalidFileFormatException occurs
     *
     * @param exception an {@link InvalidFileFormatException} exception
     */
    public void showInvalidFileFormatDialogBox(InvalidFileFormatException exception) {
        this.displayErrorResponses(this.getErrorResponses(exception), false);
    }

    /**
     * Shows the appropriate response for when DateTimeParseException occurs
     *
     * @param exception an {@link DateTimeParseException} exception
     */
    public void showDateTimeParseExceptionDialogBox(DateTimeParseException exception) {
        this.displayErrorResponses(this.getErrorResponses(exception), false);
    }

    /**
     * Removes all previous messages from the chat area
     */
    public void clearChat(boolean shouldIndicateChatbotCleared) {
        this.dialogContainer.getChildren().clear();
        if (!shouldIndicateChatbotCleared) {
            return;
        }
        this.dialogContainer.getChildren().addAll(
                this.getChatbotNormalDialog(MainWindow.MESSAGE_CHAT_BOX_CLEARED));
    }

    /**
     * Disables the text field and button in the UI
     */
    public void disableInputs() {
        this.userInput.setDisable(true);
        this.sendButton.setDisable(true);
    }

    private DialogBox getUserDialog(String input) {
        return DialogBox.getUserDialog(input, this.userImage);
    }

    private DialogBox getChatbotNormalDialog(String input) {
        return DialogBox.getChatbotDialog(input, this.chatbotNormalImage);
    }

    private DialogBox getChatbotAnnoyedDialog(String input) {
        return DialogBox.getChatbotDialog(input, this.chatbotAnnoyedImage);
    }

    private DialogBox getChatbotExcitedDialog(String input) {
        return DialogBox.getChatbotDialog(input, this.chatbotExcitedImage);
    }

    private DialogBox getChatbotHappyDialog(String input) {
        return DialogBox.getChatbotDialog(input, this.chatbotHappyImage);
    }

    private DialogBox getChatbotSadDialog(String input) {
        return DialogBox.getChatbotDialog(input, this.chatbotSadImage);
    }

    /**
     * Shows an error message when {@code InvalidCommandFormatException} occurs
     *
     * @param exception An {@code InvalidCommandFormatException} object
     */
    private LinkedList<String> getErrorResponses(InvalidCommandFormatException exception) {
        LinkedList<String> responses = new LinkedList<String>();
        responses.add(
                String.format(
                        MainWindow.ERROR_MESSAGE_MAIN_FORMAT,
                        MainWindow.ERROR_MESSAGE_COMMAND_INVALID_SYNTAX));
        responses.add(
                String.format(
                        MainWindow.ERROR_MESSAGE_SUB_FORMAT,
                        exception.getMessage()));
        responses.add(
                String.format(
                        MainWindow.ERROR_MESSAGE_MAIN_FORMAT,
                        MainWindow.ERROR_MESSAGE_TRY_AGAIN));
        return responses;
    }

    /**
     * Shows an error message when {@code InvalidFileFormatException} occurs
     *
     * @param exception An {@code InvalidFileFormatException} object
     */
    private LinkedList<String> getErrorResponses(InvalidFileFormatException exception) {
        LinkedList<String> responses = new LinkedList<String>();
        responses.add(
                String.format(
                        MainWindow.ERROR_MESSAGE_MAIN_FORMAT,
                        MainWindow.ERROR_MESSAGE_FILE_INVALID_FORMAT));
        responses.add(
                String.format(
                        MainWindow.ERROR_MESSAGE_SUB_FORMAT,
                        exception.getMessage()));
        responses.add(
                String.format(
                        MainWindow.ERROR_MESSAGE_MAIN_FORMAT,
                        MainWindow.ERROR_MESSAGE_CHECK_FILE_AGAIN));
        return responses;
    }

    /**
     * Shows an error message when {@code NumberFormatException} occurs
     *
     * @param ignored An {@code NumberFormatException} object
     */
    private LinkedList<String> getErrorResponses(NumberFormatException ignored) {
        LinkedList<String> responses = new LinkedList<String>();
        responses.add(
                String.format(
                        MainWindow.ERROR_MESSAGE_MAIN_FORMAT,
                        MainWindow.ERROR_MESSAGE_COMMAND_INVALID_SYNTAX));
        responses.add(
                String.format(
                        MainWindow.ERROR_MESSAGE_SUB_FORMAT,
                        MainWindow.ERROR_MESSAGE_COMMAND_TASK_NUMBER_ISNAN));
        responses.add(
                String.format(
                        MainWindow.ERROR_MESSAGE_MAIN_FORMAT,
                        MainWindow.ERROR_MESSAGE_TRY_AGAIN));
        return responses;
    }

    /**
     * Shows an error message when {@code IOException} occurs
     *
     * @param exception An {@code IOException} object
     */
    private LinkedList<String> getErrorResponses(IOException exception) {
        LinkedList<String> responses = new LinkedList<String>();
        responses.add(
                String.format(
                        MainWindow.ERROR_MESSAGE_MAIN_FORMAT,
                        MainWindow.ERROR_MESSAGE_FILE_IO_ERROR));
        responses.add(
                String.format(
                        MainWindow.ERROR_MESSAGE_SUB_FORMAT,
                        exception.getMessage()));
        return responses;
    }

    /**
     * Shows an error message when {@code DateTimeParseException} occurs
     *
     * @param ignored An {@code DateTimeParseException} object
     */
    private LinkedList<String> getErrorResponses(DateTimeParseException ignored) {
        LinkedList<String> responses = new LinkedList<String>();
        responses.add(
                String.format(
                        MainWindow.ERROR_MESSAGE_MAIN_FORMAT,
                        MainWindow.ERROR_MESSAGE_TASK_INVALID_FORMAT));
        responses.add(
                String.format(
                        MainWindow.ERROR_MESSAGE_SUB_FORMAT,
                        MainWindow.ERROR_MESSAGE_TASK_INVALID_DATE_FORMAT));
        responses.add(
                String.format(
                        MainWindow.ERROR_MESSAGE_MAIN_FORMAT,
                        MainWindow.ERROR_MESSAGE_TRY_AGAIN));
        return responses;
    }

    /**
     * Prints the given message as an error message. (meant to be used when a generic {@code Exception} occurs)
     *
     * @param message The error message to be printed
     */
    private LinkedList<String> getErrorResponses(String message) {
        LinkedList<String> responses = new LinkedList<String>();
        responses.add(
                String.format(
                        MainWindow.ERROR_MESSAGE_MAIN_FORMAT,
                        MainWindow.ERROR_MESSAGE_UNKNOWN_ERROR));
        responses.add(
                String.format(
                        MainWindow.ERROR_MESSAGE_SUB_FORMAT,
                        message));
        responses.add(
                String.format(
                        MainWindow.ERROR_MESSAGE_MAIN_FORMAT,
                        MainWindow.ERROR_MESSAGE_TRY_AGAIN_LATER));
        responses.add(
                String.format(
                        MainWindow.ERROR_MESSAGE_MAIN_FORMAT,
                        MainWindow.ERROR_MESSAGE_TRY_RESTARTING_APP));
        return responses;
    }

    private void displayErrorResponses(LinkedList<String> responses, boolean hasFaultInApp) {
        String finalResponse = responses.stream()
                .reduce((previousResponse, nextResponse)
                        -> previousResponse + System.lineSeparator() + nextResponse)
                .orElse(MainWindow.EMPTY_STRING);
        DialogBox dialogBox = hasFaultInApp
                ? this.getChatbotSadDialog(finalResponse)
                : this.getChatbotAnnoyedDialog(finalResponse);
        this.dialogContainer.getChildren().addAll(dialogBox);
    }

    private void closeApp() {
        this.disableInputs();
        PauseTransition pauseTransition = new PauseTransition(
                Duration.millis(MainWindow.PAUSE_DURATION_ON_EXIT));
        pauseTransition.setOnFinished(event -> Platform.exit());
        pauseTransition.play();
    }

    /**
     * Creates two dialog boxes, one echoing user input and
     * the other containing Bhaymax's reply and then appends
     * them to the dialog container. Clears the user input
     * after processing
     */
    @FXML
    private void handleUserInput() {
        String input = this.userInput.getText();
        this.dialogContainer.getChildren().addAll(this.getUserDialog(input));
        try {
            Command command = Parser.parse(input, this.tasks);
            command.execute(this.tasks, this, this.storage);
            if (command.isExit()) {
                this.closeApp();
            }
        } catch (InvalidCommandFormatException e) {
            this.displayErrorResponses(this.getErrorResponses(e), false);
        } catch (DateTimeParseException e) {
            this.displayErrorResponses(this.getErrorResponses(e), false);
        } catch (NumberFormatException e) {
            this.displayErrorResponses(this.getErrorResponses(e), false);
        } catch (IOException e) {
            this.displayErrorResponses(this.getErrorResponses(e), false);
        } catch (Exception e) {
            this.displayErrorResponses(this.getErrorResponses(e.getMessage()), true);
        } finally {
            this.userInput.clear();
        }
    }
}
