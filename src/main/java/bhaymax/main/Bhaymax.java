package bhaymax.main;

import java.io.IOException;
import java.time.format.DateTimeParseException;

import bhaymax.controller.MainWindow;
import bhaymax.exception.InvalidFileFormatException;
import bhaymax.storage.Storage;
import bhaymax.task.TaskList;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Main class for JavaFX version of the Chatbot app
 */
public class Bhaymax extends Application {
    public static final String APP_NAME = "Bhaymax";

    private final Storage storage;
    private TaskList tasks;

    /**
     * Sets up the UI and loads the task
     * from a file, if it exists
     */
    public Bhaymax() {
        this.storage = new Storage();
    }

    @Override
    public void start(Stage stage) {
        try {
            boolean canOpenFile = false;
            FXMLLoader fxmlLoader = new FXMLLoader(
                    Bhaymax.class.getResource(
                            "/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);

            // Set up properties for MainWindow controller
            MainWindow mainWindowController = fxmlLoader.<MainWindow>getController();
            mainWindowController.setAppName(Bhaymax.APP_NAME);
            mainWindowController.setStorage(this.storage);

            PauseTransition pauseTransition = new PauseTransition(Duration.millis(5000));
            pauseTransition.setOnFinished(event -> Platform.exit());
            try {
                this.tasks = this.storage.loadTasks();
                canOpenFile = true;
            } catch (InvalidFileFormatException e) {
                mainWindowController.disableInputs();
                mainWindowController.clearChat(false);
                mainWindowController.showInvalidFileFormatDialogBox(e);
                mainWindowController.showSadResponse("I will terminate in 5 seconds");
                pauseTransition.play();
            } catch (DateTimeParseException e) {
                mainWindowController.disableInputs();
                mainWindowController.clearChat(false);
                mainWindowController.showDateTimeParseExceptionDialogBox(e);
                mainWindowController.showSadResponse("I will terminate in 5 seconds");
                pauseTransition.play();
            }

            if (canOpenFile) {
                mainWindowController.showWelcomeDialogBox();
                mainWindowController.setTasks(this.tasks);
            }

            stage.setMinHeight(640);
            stage.setMinWidth(400);
            stage.setMaxHeight(640);
            stage.setMaxWidth(400);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
