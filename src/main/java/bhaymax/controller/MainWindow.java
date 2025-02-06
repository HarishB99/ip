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

    /**
     * Shows an error message when
     * {@code InvalidCommandException} occurs
     *
     * @param exception An {@code InvalidCommandException} object
     */
    private LinkedList<DialogBox> getErrorDialogBoxes(InvalidCommandException exception) {
        LinkedList<DialogBox> dialogBoxes = new LinkedList<DialogBox>();
        dialogBoxes.add(this.getChatbotErrorDialog("[-] Invalid command syntax provided:"));
        dialogBoxes.add(this.getChatbotErrorDialog("[-]   " + exception.getMessage()));
        dialogBoxes.add(this.getChatbotErrorDialog("[-] Try again."));
        return dialogBoxes;
    }

    /**
     * Shows an error message when
     * {@code InvalidCommandFormatException} occurs
     *
     * @param exception An {@code InvalidCommandFormatException} object
     */
    private LinkedList<DialogBox> getErrorDialogBoxes(InvalidCommandFormatException exception) {
        LinkedList<DialogBox> dialogBoxes = new LinkedList<DialogBox>();
        dialogBoxes.add(this.getChatbotErrorDialog("[-] Invalid command syntax provided:"));
        dialogBoxes.add(this.getChatbotErrorDialog("[-]   " + exception.getMessage()));
        dialogBoxes.add(this.getChatbotErrorDialog("[-] Try again."));
        return dialogBoxes;
    }

    /**
     * Shows an error message when
     * {@code InvalidFileFormatException} occurs
     *
     * @param exception An {@code InvalidFileFormatException} object
     */
    private LinkedList<DialogBox> getErrorDialogBoxes(InvalidFileFormatException exception) {
        LinkedList<DialogBox> dialogBoxes = new LinkedList<DialogBox>();
        dialogBoxes.add(this.getChatbotErrorDialog("[-] Format of task file is incorrect:"));
        dialogBoxes.add(this.getChatbotErrorDialog("[-]   " + exception.getMessage()));
        dialogBoxes.add(this.getChatbotErrorDialog("[-] Please check your task file and try again"));
        return dialogBoxes;
    }

    /**
     * Shows an error message when
     * {@code NumberFormatException} occurs
     *
     * @param ignored An {@code NumberFormatException} object
     */
    private LinkedList<DialogBox> getErrorDialogBoxes(NumberFormatException ignored) {
        LinkedList<DialogBox> dialogBoxes = new LinkedList<DialogBox>();
        dialogBoxes.add(this.getChatbotErrorDialog("[-] Invalid command syntax provided:"));
        dialogBoxes.add(this.getChatbotErrorDialog("[-]   Task number should be numerical"));
        dialogBoxes.add(this.getChatbotErrorDialog("[-] Try again."));
        return dialogBoxes;
    }

    /**
     * Shows an error message when
     * {@code IOException} occurs
     *
     * @param exception An {@code IOException} object
     */
    private LinkedList<DialogBox> getErrorDialogBoxes(IOException exception) {
        LinkedList<DialogBox> dialogBoxes = new LinkedList<DialogBox>();
        dialogBoxes.add(this.getChatbotErrorDialog("[-] Unable to save task to file:"));
        dialogBoxes.add(this.getChatbotErrorDialog("[-]   " + exception.getMessage()));
        return dialogBoxes;
    }

    /**
     * Shows an error message when
     * {@code DateTimeParseException} occurs
     *
     * @param ignored An {@code DateTimeParseException} object
     */
    private LinkedList<DialogBox> getErrorDialogBoxes(DateTimeParseException ignored) {
        LinkedList<DialogBox> dialogBoxes = new LinkedList<DialogBox>();
        dialogBoxes.add(this.getChatbotErrorDialog("[-] Task format is incorrect:"));
        dialogBoxes.add(this.getChatbotErrorDialog("[-]   Wrong date/time format"));
        dialogBoxes.add(this.getChatbotErrorDialog("[-] Try again."));
        return dialogBoxes;
    }

    /**
     * Prints the given message as an error message.
     * (meant to be used when a generic {@code Exception} occurs)
     *
     * @param message The error message to be printed
     */
    private LinkedList<DialogBox> getErrorDialogBoxes(String message) {
        LinkedList<DialogBox> dialogBoxes = new LinkedList<DialogBox>();
        dialogBoxes.add(this.getChatbotErrorDialog("[-] An unknown error occurred:"));
        dialogBoxes.add(this.getChatbotErrorDialog("[-]   " + message));
        dialogBoxes.add(this.getChatbotErrorDialog("[-] Please try again later"));
        dialogBoxes.add(this.getChatbotErrorDialog("[-] Alternatively, you can try restarting the app"));
        return dialogBoxes;
    }

    /**
     * Shows a dialog box that is meant to be
     * shown to the user at the start of the
     * app
     */
    public void showWelcomeDialogBox() {
        this.dialogContainer.getChildren().addAll(
                this.getChatbotDialog("Hello! I'm " + this.appName
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
                this.getChatbotDialog("Bye. Hope to see you again soon!"));
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

    /**
     * Creates two dialog boxes, one echoing user input and
     * the other containing Duke's reply and then appends
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
            this.displayDialogs(this.getErrorDialogBoxes(e));
        } catch (InvalidCommandException e) {
            this.displayDialogs(this.getErrorDialogBoxes(e));
        } catch (DateTimeParseException e) {
            this.displayDialogs(this.getErrorDialogBoxes(e));
        } catch (NumberFormatException e) {
            this.displayDialogs(this.getErrorDialogBoxes(e));
        } catch (IOException e) {
            this.displayDialogs(this.getErrorDialogBoxes(e));
        } catch (Exception e) {
            this.displayDialogs(this.getErrorDialogBoxes(e.getMessage()));
        } finally {
            this.userInput.clear();
        }
    }
}
