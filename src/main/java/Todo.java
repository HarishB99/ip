public class Todo extends Task {
    public static final String TYPE = "T";

    public Todo(String description) {
        super(Todo.TYPE, description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
