package bhaymax.controller;

/**
 * Provides enumeration values for class names available to style a dialog box
 */
public enum DialogBoxStyleClass {
    USER("label-user"),
    CHATBOT_NORMAL("label-chatbot-normal"),
    CHATBOT_SAD("label-chatbot-sad"),
    CHATBOT_ANNOYED("label-chatbot-annoyed");

    private final String cssClassName;

    DialogBoxStyleClass(String cssClassName) {
        this.cssClassName = cssClassName;
    }

    public String getCssClassName() {
        return this.cssClassName;
    }
}
