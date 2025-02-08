package bhaymax.controller;

import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.LinkedList;
import java.util.Objects;

import bhaymax.command.Command;
import bhaymax.exception.InvalidCommandException;
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
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;
    private final Image userImage = new Image(
            Objects.requireNonNull(
                    this.getClass().getResourceAsStream(
                            "/images/bhaymax_user.png")));
    private final Image chatbotNormalImage = new Image(
            Objects.requireNonNull(
                    this.getClass().getResourceAsStream(
                            "/images/bhaymax_chatbot_normal.png")));
    private final Image chatbotErrorImage = new Image(
            Objects.requireNonNull(
                    this.getClass().getResourceAsStream(
                            "/images/bhaymax_chatbot_error.png")));
    private final Image chatbotWelcomeImage = new Image(
            Objects.requireNonNull(
                    this.getClass().getResourceAsStream(
                            "/images/bhaymax_chatbot_welcome.png")));
    private final Image chatbotFarewellImage = new Image(
            Objects.requireNonNull(
                    this.getClass().getResourceAsStream(
                            "/images/bhaymax_chatbot_farewell.png")));
    private TaskList tasks;
    private Storage storage;
    private String appName;

    /**
     * Binds the scroll pane's vertical
     * value to the height of the dialog container
     * it holds
     */
    @FXML
    public void initialize() {
        this.scrollPane.vvalueProperty().bind(
                this.dialogContainer.heightProperty());
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

    private void displayDialogs(LinkedList<DialogBox> dialogBoxes) {
        this.dialogContainer.getChildren().addAll(dialogBoxes);
    }

    private void displayErrorResponses(LinkedList<String> responses) {
        String finalResponse = responses.stream()
                .reduce((previousResponse, nextResponse)
                        -> previousResponse + System.lineSeparator() + nextResponse)
                .orElse("");
        this.dialogContainer.getChildren().addAll(this.getChatbotErrorDialog(finalResponse));
    }

    /**
     * Shows an error message when
     * {@code InvalidCommandException} occurs
     *
     * @param exception An {@code InvalidCommandException} object
     */
    private LinkedList<String> getErrorResponses(InvalidCommandException exception) {
        LinkedList<String> responses = new LinkedList<String>();
        responses.add("[-] Invalid command syntax provided:");
        responses.add("[-]   " + exception.getMessage());
        responses.add("[-] Try again.");
        return responses;
    }

    /**
     * Shows an error message when
     * {@code InvalidCommandFormatException} occurs
     *
     * @param exception An {@code InvalidCommandFormatException} object
     */
    private LinkedList<String> getErrorResponses(InvalidCommandFormatException exception) {
        LinkedList<String> responses = new LinkedList<String>();
        responses.add("[-] Invalid command syntax provided:");
        responses.add("[-]   " + exception.getMessage());
        responses.add("[-] Try again.");
        return responses;
    }

    /**
     * Shows an error message when
     * {@code InvalidFileFormatException} occurs
     *
     * @param exception An {@code InvalidFileFormatException} object
     */
    private LinkedList<String> getErrorResponses(InvalidFileFormatException exception) {
        LinkedList<String> responses = new LinkedList<String>();
        responses.add("[-] Format of task file is incorrect:");
        responses.add("[-]   " + exception.getMessage());
        responses.add("[-] Please check your task file and try again");
        return responses;
    }

    /**
     * Shows an error message when
     * {@code NumberFormatException} occurs
     *
     * @param ignored An {@code NumberFormatException} object
     */
    private LinkedList<String> getErrorResponses(NumberFormatException ignored) {
        LinkedList<String> responses = new LinkedList<String>();
        responses.add("[-] Invalid command syntax provided:");
        responses.add("[-]   Task number should be numerical");
        responses.add("[-] Try again.");
        return responses;
    }

    /**
     * Shows an error message when
     * {@code IOException} occurs
     *
     * @param exception An {@code IOException} object
     */
    private LinkedList<String> getErrorResponses(IOException exception) {
        LinkedList<String> responses = new LinkedList<String>();
        responses.add("[-] Unable to save task to file:");
        responses.add("[-]   " + exception.getMessage());
        return responses;
    }

    /**
     * Shows an error message when
     * {@code DateTimeParseException} occurs
     *
     * @param ignored An {@code DateTimeParseException} object
     */
    private LinkedList<String> getErrorResponses(DateTimeParseException ignored) {
        LinkedList<String> responses = new LinkedList<String>();
        responses.add("[-] Task format is incorrect:");
        responses.add("[-]   Wrong date/time format");
        responses.add("[-] Try again.");
        return responses;
    }

    /**
     * Prints the given message as an error message.
     * (meant to be used when a generic {@code Exception} occurs)
     *
     * @param message The error message to be printed
     */
    private LinkedList<String> getErrorResponses(String message) {
        LinkedList<String> responses = new LinkedList<String>();
        responses.add("[-] An unknown error occurred:");
        responses.add("[-]   " + message);
        responses.add("[-] Please try again later");
        responses.add("[-] Alternatively, you can try restarting the app");
        return responses;
    }

    /**
     * Shows a dialog box that is meant to be
     * shown to the user at the start of the
     * app
     */
    public void showWelcomeDialogBox() {
        this.dialogContainer.getChildren().addAll(
                this.getChatbotWelcomeDialog("Hello! I'm " + this.appName
                        + ", your personal chatbot and task list manager!"),
                this.getChatbotDialog("What can I do for you?")
        );
    }

    /**
     * Shows a dialog box that is meant to be
     * shown to the user before exiting the app
     */
    public void showFarewellDialogBox() {
        this.dialogContainer.getChildren().addAll(
                this.getChatbotFarewellDialog("Bye. Hope to see you again soon!"));
    }

    /**
     * Shows a dialog box with the given response
     *
     * @param response A message to be shown to the user
     *                 using a dialog box from the chatbot
     */
    public void showResponse(String response) {
        this.dialogContainer.getChildren().addAll(this.getChatbotDialog(response));
    }

    private DialogBox getUserDialog(String input) {
        return DialogBox.getUserDialog(input, this.userImage);
    }

    private DialogBox getChatbotDialog(String input) {
        return DialogBox.getChatbotDialog(input, this.chatbotNormalImage);
    }

    private DialogBox getChatbotErrorDialog(String input) {
        return DialogBox.getChatbotDialog(input, this.chatbotErrorImage);
    }

    private DialogBox getChatbotWelcomeDialog(String input) {
        return DialogBox.getChatbotDialog(input, this.chatbotWelcomeImage);
    }

    private DialogBox getChatbotFarewellDialog(String input) {
        return DialogBox.getChatbotDialog(input, this.chatbotFarewellImage);
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
                PauseTransition pauseTransition = new PauseTransition(Duration.millis(650));
                pauseTransition.setOnFinished(event -> {
                    Platform.exit();
                });
                pauseTransition.play();
            }
        } catch (InvalidCommandFormatException e) {
            this.displayErrorResponses(this.getErrorResponses(e));
        } catch (InvalidCommandException e) {
            this.displayErrorResponses(this.getErrorResponses(e));
        } catch (DateTimeParseException e) {
            this.displayErrorResponses(this.getErrorResponses(e));
        } catch (NumberFormatException e) {
            this.displayErrorResponses(this.getErrorResponses(e));
        } catch (IOException e) {
            this.displayErrorResponses(this.getErrorResponses(e));
        } catch (Exception e) {
            this.displayErrorResponses(this.getErrorResponses(e.getMessage()));
        } finally {
            this.userInput.clear();
        }
    }
}
