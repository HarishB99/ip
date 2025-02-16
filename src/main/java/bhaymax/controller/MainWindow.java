package bhaymax.controller;

import java.time.format.DateTimeParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import bhaymax.command.Command;
import bhaymax.exception.UnknownException;
import bhaymax.exception.command.InvalidCommandFormatException;
import bhaymax.exception.command.InvalidDateFormatInCommandException;
import bhaymax.exception.file.FileWriteException;
import bhaymax.exception.file.InvalidFileFormatException;
import bhaymax.main.ImageFilePath;
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

    private static final String MESSAGE_WELCOME_FORMAT = "Hello! I'm %s, your personal chatbot and task list manager!";
    private static final String MESSAGE_GREETING = "Hello! How can I help you today?";
    private static final String MESSAGE_FAREWELL = "Bye. Hope to see you again soon!";
    private static final String MESSAGE_CHAT_BOX_CLEARED = "I have cleared the chat. Is there anything else "
            + "you want me to do?";

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private final Image userImage = new Image(
            Objects.requireNonNull(this.getClass().getResourceAsStream(ImageFilePath.USER.toString())));
    private final Image chatbotNormalImage = new Image(
            Objects.requireNonNull(this.getClass().getResourceAsStream(ImageFilePath.CHATBOT_NORMAL.toString())));
    private final Image chatbotAnnoyedImage = new Image(
            Objects.requireNonNull(this.getClass().getResourceAsStream(ImageFilePath.CHATBOT_ANNOYED.toString())));
    private final Image chatbotExcitedImage = new Image(
            Objects.requireNonNull(this.getClass().getResourceAsStream(ImageFilePath.CHATBOT_EXCITED.toString())));
    private final Image chatbotHappyImage = new Image(
            Objects.requireNonNull(this.getClass().getResourceAsStream(ImageFilePath.CHATBOT_HAPPY.toString())));
    private final Image chatbotSadImage = new Image(
            Objects.requireNonNull(this.getClass().getResourceAsStream(ImageFilePath.CHATBOT_SAD.toString())));

    private TaskList tasks;
    private Storage storage;

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
    public void showWelcomeDialogBox(String appName) {
        this.dialogContainer.getChildren().addAll(
                this.getNormalChatbotDialog(
                        String.format(MainWindow.MESSAGE_WELCOME_FORMAT, appName)),
                this.getExcitedChatbotDialog(MainWindow.MESSAGE_GREETING)
        );
    }

    /**
     * Shows a dialog box to greet back to the user
     */
    public void showGreetingDialogBox() {
        this.dialogContainer.getChildren().addAll(
                this.getExcitedChatbotDialog(MainWindow.MESSAGE_GREETING)
        );
    }

    /**
     * Shows a dialog box that is meant to be shown to the user before exiting the app
     */
    public void showFarewellDialogBox() {
        this.dialogContainer.getChildren().addAll(
                this.getHappyChatbotDialog(MainWindow.MESSAGE_FAREWELL));
    }

    /**
     * Shows a dialog box with a normal face alongside the given response
     *
     * @param response A message to be shown to the user
     *                 using a dialog box from the chatbot
     */
    public void showNormalResponse(String response) {
        this.dialogContainer.getChildren().addAll(this.getNormalChatbotDialog(response));
    }

    /**
     * Shows a dialog box with an excited face alongside the given response
     *
     * @param response A message to be shown to the user
     *                 using a dialog box from the chatbot
     */
    public void showExcitedResponse(String response) {
        this.dialogContainer.getChildren().addAll(this.getExcitedChatbotDialog(response));
    }

    /**
     * Shows a dialog box with a sad face alongside the given response
     *
     * @param response A message to be shown to the user
     *                 using a dialog box from the chatbot
     */
    public void showSadResponse(String response) {
        this.dialogContainer.getChildren().addAll(this.getSadChatbotDialog(response));
    }

    /**
     * Shows the appropriate response for when InvalidFileFormatException occurs
     *
     * @param exception an {@link InvalidFileFormatException} exception
     */
    public void showInvalidFileFormatDialogBox(InvalidFileFormatException exception) {
        this.displayErrorResponses(List.<String>of(exception.getMessage()), false);
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
                this.getNormalChatbotDialog(MainWindow.MESSAGE_CHAT_BOX_CLEARED));
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

    private DialogBox getNormalChatbotDialog(String input) {
        return DialogBox.getChatbotDialog(input, this.chatbotNormalImage);
    }

    private DialogBox getAnnoyedChatbotDialog(String input) {
        return DialogBox.getChatbotDialog(input, this.chatbotAnnoyedImage);
    }

    private DialogBox getExcitedChatbotDialog(String input) {
        return DialogBox.getChatbotDialog(input, this.chatbotExcitedImage);
    }

    private DialogBox getHappyChatbotDialog(String input) {
        return DialogBox.getChatbotDialog(input, this.chatbotHappyImage);
    }

    private DialogBox getSadChatbotDialog(String input) {
        return DialogBox.getChatbotDialog(input, this.chatbotSadImage);
    }

    /**
     * Shows an error message when {@code DateTimeParseException} occurs
     *
     * @param ignored An {@code DateTimeParseException} object
     */
    private LinkedList<String> getErrorResponses(DateTimeParseException ignored) {
        LinkedList<String> responses = new LinkedList<String>();
        responses.add(new InvalidDateFormatInCommandException().getMessage());
        return responses;
    }

    /**
     * Prints the given message as an error message. (meant to be used when a generic {@code Exception} occurs)
     *
     * @param message The error message to be printed
     */
    private LinkedList<String> getErrorResponses(String message) {
        LinkedList<String> responses = new LinkedList<String>();
        responses.add(new UnknownException(message).getMessage());
        return responses;
    }

    private void displayErrorResponses(List<String> responses, boolean hasFaultInApp) {
        String finalResponse = responses.stream()
                .reduce((previousResponse, nextResponse)
                        -> previousResponse + System.lineSeparator() + nextResponse)
                .orElse("");
        DialogBox dialogBox = hasFaultInApp
                ? this.getSadChatbotDialog(finalResponse)
                : this.getAnnoyedChatbotDialog(finalResponse);
        this.dialogContainer.getChildren().addAll(dialogBox);
    }

    private void closeApp() {
        this.disableInputs();
        PauseTransition pauseTransition = new PauseTransition(
                Duration.millis(MainWindow.PAUSE_DURATION_ON_EXIT));
        pauseTransition.setOnFinished(event -> Platform.exit());
        pauseTransition.play();
    }

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
        } catch (InvalidCommandFormatException | FileWriteException e) {
            this.displayErrorResponses(List.<String>of(e.getMessage()), false);
        } catch (DateTimeParseException e) {
            this.displayErrorResponses(this.getErrorResponses(e), false);
        } catch (Exception e) {
            this.displayErrorResponses(this.getErrorResponses(e.getMessage()), true);
        } finally {
            this.userInput.clear();
        }
    }
}
