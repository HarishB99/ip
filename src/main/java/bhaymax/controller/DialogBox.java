package bhaymax.controller;

import java.io.IOException;

import bhaymax.main.FxmlFilePath;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Controller for DialogBox
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    MainWindow.class.getResource(FxmlFilePath.DIALOG_BOX.toString()));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.dialog.setText(text);
        this.dialog.getStyleClass().add(DialogBoxStyleClass.USER.getCssClassName());
        this.displayPicture.setImage(img);
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text is on the right
     */
    private void flip(DialogBoxStyleClass dialogBoxStyleClass) {
        ObservableList<Node> nodes = FXCollections.observableArrayList(this.getChildren());
        FXCollections.reverse(nodes);
        this.getChildren().setAll(nodes);
        // this.dialog.getStyleClass().clear();
        this.dialog.getStyleClass().add(dialogBoxStyleClass.getCssClassName());
        this.setAlignment(Pos.TOP_LEFT);
    }

    public static DialogBox getUserDialogBox(String text, Image img) {
        return new DialogBox(text, img);
    }

    public static DialogBox getChatbotDialogBox(String text, Image img, DialogBoxStyleClass dialogBoxStyleClass) {
        DialogBox db = new DialogBox(text, img);
        // db.getStyleClass().clear();
        // db.getStyleClass().add(dialogBoxStyleClass.getCssClassName());
        db.flip(dialogBoxStyleClass);
        return db;
    }
}
