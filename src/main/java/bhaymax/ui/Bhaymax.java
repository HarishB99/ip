package bhaymax.ui;

import java.io.IOException;

import bhaymax.controller.MainWindow;
import bhaymax.storage.Storage;
import bhaymax.task.TaskList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Main class for JavaFX version of
 * the Chatbot app
 */
public class Bhaymax extends Application {
    public static final String APP_NAME = "Bhaymax";

    private final Storage storage;
    private final TaskList tasks;

    /**
     * Sets up the UI and loads the task
     * from a file, if it exists
     */
    public Bhaymax() {
        this.storage = new Storage();
        try {
            this.tasks = this.storage.loadTasks();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    Bhaymax.class.getResource(
                            "/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);

            // Set up properties for MainWindow controller
            MainWindow mainWindowController = fxmlLoader.<MainWindow>getController();
            mainWindowController.setAppName(Bhaymax.APP_NAME);
            mainWindowController.setTasks(this.tasks);
            mainWindowController.setStorage(this.storage);
            mainWindowController.showWelcomeDialogBox();

            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
