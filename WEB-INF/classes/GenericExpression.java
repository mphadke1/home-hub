import java.util.*;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/GenericExpression")

public class GenericExpression extends HttpServlet{
	public String type;
    public String field;
    public String pattern;
    public String operator;
    public String value;
    public String number;
    public String maxField;
}
