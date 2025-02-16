package bhaymax.main;

/**
 * Provides a list of paths to available image files
 */
public enum ImageFilePath {
    USER("/images/bhaymax_user.png"),
    CHATBOT_NORMAL("/images/bhaymax_chatbot_normal.png"),
    CHATBOT_ANNOYED("/images/bhaymax_chatbot_annoyed.png"),
    CHATBOT_EXCITED("/images/bhaymax_chatbot_excited.png"),
    CHATBOT_HAPPY("/images/bhaymax_chatbot_happy.png"),
    CHATBOT_SAD("/images/bhaymax_chatbot_sad.png");

    private final String filePath;

    ImageFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return this.filePath;
    }
}
