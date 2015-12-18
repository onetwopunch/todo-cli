import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by rcanty on 12/16/15.
 */

public class Todo {
    public String description;
    public Boolean is_complete;
    public Date due_date;
    public Integer id;

    public Todo(Integer id, String description, Boolean is_complete, Date due_date) {
        this.id = id;
        this.description = description;
        this.is_complete = is_complete;
        this.due_date = due_date;
    }
    public Todo(String description, Boolean is_complete, Date due_date) {
        this.description = description;
        this.is_complete = is_complete;
        this.due_date = due_date;
    }
    public Todo(String description) {
        this.description = description;
        this.is_complete = false;

        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, 1);
        dt = c.getTime();

        this.due_date = dt;
    }
    public static Date stringToDate( String str ) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse(str);
    }
    public String formattedDate() throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format( due_date );
    }
    public String show() {
        StringBuilder sb = new StringBuilder();
        String check = ( is_complete ) ? "[x] " : "[ ] ";
        sb.append(id.toString() + ") ");
        sb.append(check);
        sb.append(description);
        sb.append(" ( Due: " + due_date.toString() + " )");
        return sb.toString();
    }

    public String inspect() {
        StringBuilder sb = new StringBuilder();
        String NEW_LINE = System.getProperty("line.separator");
        sb.append(this.getClass().getName() + ": instance { " + NEW_LINE );
        sb.append(" description: " + description + "," +  NEW_LINE );
        sb.append(" is_complete: "  + is_complete.toString() + "," +  NEW_LINE );
        sb.append(" due_date: " + due_date.toString() + "," + NEW_LINE + "}");
        return sb.toString();
    }
}
