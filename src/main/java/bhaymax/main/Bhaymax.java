package bhaymax.main;

import java.io.IOException;

import bhaymax.controller.MainWindow;
import bhaymax.exception.file.InvalidFileFormatException;
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

    private static final int PREFERRED_WIDTH = 420;
    private static final int PREFERRED_HEIGHT = 640;
    // Specified in milliseconds
    private static final int PAUSE_DURATION_FOR_TERMINATION = 5000;
    private static final String ERROR_MESSAGE_TERMINATING_APP = "I will terminate in 5 seconds";

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
        AnchorPane anchorPane;
        MainWindow mainWindowController;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    Bhaymax.class.getResource(FxmlFilePath.MAIN_WINDOW.toString()));
            anchorPane = fxmlLoader.load();
            mainWindowController = fxmlLoader.<MainWindow>getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        boolean canOpenFile = false;
        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);

        // Set up properties for MainWindow controller
        mainWindowController.setStorage(this.storage);

        PauseTransition pauseTransition = new PauseTransition(
                Duration.millis(Bhaymax.PAUSE_DURATION_FOR_TERMINATION));
        pauseTransition.setOnFinished(event -> Platform.exit());
        try {
            this.tasks = this.storage.loadTasks();
            canOpenFile = true;
        } catch (InvalidFileFormatException e) {
            mainWindowController.disableInputs();
            mainWindowController.clearChat(false);
            mainWindowController.showInvalidFileFormatDialogBox(e);
            mainWindowController.showSadResponse(Bhaymax.ERROR_MESSAGE_TERMINATING_APP);
            pauseTransition.play();
        }

        if (canOpenFile) {
            mainWindowController.showWelcomeDialogBox(Bhaymax.APP_NAME);
            mainWindowController.setTasks(this.tasks);
        }

        stage.setMinHeight(Bhaymax.PREFERRED_HEIGHT);
        stage.setMinWidth(Bhaymax.PREFERRED_WIDTH);
        stage.setMaxHeight(Bhaymax.PREFERRED_HEIGHT);
        stage.setMaxWidth(Bhaymax.PREFERRED_WIDTH);

        stage.show();
    }
}
