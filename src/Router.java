import java.util.Arrays;

/**
 * Created by rcanty on 12/16/15.
 */
public class Router {
    private String command;
    public String[] options;

    private static TodoList todoList = new TodoList();

    public Router( String[] args ) {
        if ( args.length > 0) {
            this.command = args[0];
            this.options = (args.length > 1) ? Arrays.copyOfRange(args, 1, args.length) : new String[0];
        }
    }
    public void execute() {
        try {
            switch (command) {
                case "list":
                    list();
                    break;
                case "add":
                    add();
                    break;
                case "check":
                    check();
                    break;
            }
            todoList.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void check() {
        try {
            todoList.check( options[0] );
            todoList.reload();
            list();
        } catch( Exception e ) {
            System.out.println("Could not find Todo with id: " + options[0]);
        }
    }

    private void add() {
        String joined = String.join(" ", options);
        Todo todo = todoList.add( joined );
        System.out.println( todo.show() );
    }

    private void list() {
        for (Todo todo : todoList) {
            System.out.println( todo.show() );
        }
    }

}
