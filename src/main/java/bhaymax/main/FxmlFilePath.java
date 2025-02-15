package bhaymax.main;

/**
 * Provides a list of paths to available FXML files
 */
public enum FxmlFilePath {
    MAIN_WINDOW("/view/MainWindow.fxml"),
    DIALOG_BOX("/view/DialogBox.fxml");

    private final String filePath;

    FxmlFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return this.filePath;
    }
}
