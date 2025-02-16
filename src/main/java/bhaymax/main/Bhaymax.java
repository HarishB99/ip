package bhaymax.main;

import java.io.IOException;
import java.util.Objects;

import bhaymax.controller.MainWindow;
import bhaymax.exception.file.InvalidFileFormatException;
import bhaymax.storage.Storage;
import bhaymax.task.TaskList;
import bhaymax.util.Pair;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Loads the main window for the chatbot
 */
public class Bhaymax extends Application {
    public static final String APP_NAME = "Bhaymax";

    private static final int PREFERRED_WIDTH = 420;
    private static final int PREFERRED_HEIGHT = 640;

    private static final String ERROR_MESSAGE_RECTIFY_FILE_ERRORS =
            "Please close this app, rectify the errors in file, and try again.";

    private final Image appIcon = new Image(
            Objects.requireNonNull(this.getClass().getResourceAsStream(ImageFilePath.CHATBOT_NORMAL.toString())));

    private Pair<MainWindow, AnchorPane> loadMainWindowAndAnchorPane() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    Bhaymax.class.getResource(FxmlFilePath.MAIN_WINDOW.toString()));
            AnchorPane anchorPane = fxmlLoader.load();
            MainWindow mainWindowController = fxmlLoader.<MainWindow>getController();
            return new Pair<MainWindow, AnchorPane>(mainWindowController, anchorPane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void prepareStage(Stage stage) {
        stage.setMinHeight(Bhaymax.PREFERRED_HEIGHT);
        stage.setMinWidth(Bhaymax.PREFERRED_WIDTH);
        stage.setMaxHeight(Bhaymax.PREFERRED_HEIGHT);
        stage.setMaxWidth(Bhaymax.PREFERRED_WIDTH);

        stage.getIcons().add(this.appIcon);
        stage.setTitle(Bhaymax.APP_NAME);
    }

    @Override
    public void start(Stage stage) {
        Pair<MainWindow, AnchorPane> controllerAndPane = this.loadMainWindowAndAnchorPane();
        MainWindow mainWindowController = controllerAndPane.first();
        AnchorPane anchorPane = controllerAndPane.second();

        try {
            Storage storage = new Storage();
            TaskList tasks = storage.loadTasks();
            mainWindowController.setTasks(tasks);
            mainWindowController.setStorage(storage);
            mainWindowController.showWelcomeDialogBox(Bhaymax.APP_NAME);
        } catch (InvalidFileFormatException e) {
            mainWindowController.disableInputs();
            mainWindowController.showInvalidFileFormatDialogBox(e);
            mainWindowController.showSadResponse(Bhaymax.ERROR_MESSAGE_RECTIFY_FILE_ERRORS);
        }

        this.prepareStage(stage);
        stage.setScene(new Scene(anchorPane));
        stage.show();
    }
}
