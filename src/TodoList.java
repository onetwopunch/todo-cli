import java.io.File;
import java.util.ArrayList;
import java.sql.*;
import java.util.Iterator;

/**
 * Created by rcanty on 12/16/15.
 */
// Using a decorator pattern instead of inheriting from ArrayList
public class TodoList implements Iterable<Todo>{
    private ArrayList<Todo> todoList = new ArrayList<Todo>();
    private String dbfile = "todolist.db";
    private Connection cxn = null;

    public TodoList() {
        try {
            cxn = connect();
            System.out.println("Creating database if it doesn't exist...");
            Statement stmt = cxn.createStatement();
            stmt.executeUpdate( sqlCreateDatabase() );
            stmt.close();
            loadTodoList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void close() throws SQLException {
        cxn.close();
    }
    public void reload() {
        try {
            loadTodoList();
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }
    @Override
    public Iterator<Todo> iterator() {
        return todoList.iterator();
    }
    public Todo add( String description ) {
        Statement stmt = null;
        try {
            stmt = cxn.createStatement();
            Todo todo = new Todo( description );
            String formatted_date = todo.formattedDate();
            int complete_int = todo.is_complete ? 1 : 0;

            String sql = "insert into todos (description, is_complete, due_date ) values ( '%s', %d, '%s')";

            stmt.executeUpdate( String.format(sql, description, complete_int, formatted_date));
            stmt.close();
            return todo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Connection connect() {
        Connection cxn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            cxn = DriverManager.getConnection("jdbc:sqlite:" + dbfile);
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        System.out.println("Opened database successfully");
        return cxn;
    }
    private String sqlCreateDatabase() {
        StringBuilder sb = new StringBuilder();
        sb.append("create table if not exists todos ");
        sb.append("( id integer primary key, ");
        sb.append("description text not null, ");
        sb.append("due_date datetime not null, ");
        sb.append("is_complete integer not null );");
        return sb.toString();
    }
    private void loadTodoList() throws SQLException {
        Statement stmt = cxn.createStatement();
        ResultSet rs = stmt.executeQuery("select * from todos;");
        while( rs.next() ) {
            String description = rs.getString("description");
            Boolean is_complete = rs.getBoolean("is_complete");
            Date due_date = rs.getDate("due_date");
            Integer id = rs.getInt("id");
            Todo todo = new Todo(id, description, is_complete, due_date);
            todoList.add( todo );
        }
        rs.close();
        stmt.close();
    }

    public void check(String s) throws SQLException {
        Statement stmt = cxn.createStatement();
        String sql = "update todos set is_complete = 1 where ID=%s;";
        stmt.executeUpdate( String.format(sql, s) );
        stmt.close();
    }
}
