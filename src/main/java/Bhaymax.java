public class Bhaymax {
    public static final String NAME = "Bhaymax";

    public static void printHorizontalLine() {
        System.out.println("____________________________________________________________");
    }

    public static void greetHello(String name) {
        System.out.println("Hello! I'm " + name);
        System.out.println("What can I do for you?");
    }

    public static void sayFarewell() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    public static void main(String[] args) {
        Bhaymax.printHorizontalLine();
        Bhaymax.greetHello(Bhaymax.NAME);
        Bhaymax.printHorizontalLine();
        Bhaymax.sayFarewell();
        Bhaymax.printHorizontalLine();
    }
}
