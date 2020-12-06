import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/WorkerList")

public class WorkerList extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		
		Utilities utility = new Utilities(request,pw);
		utility.printHtml("Header.html");
		utility.printNavbar();

        String small, medium, large;
        small = request.getParameter("small-furniture");
        medium = request.getParameter("medium-furniture");
        large = request.getParameter("large-furniture");
        String oldDate = request.getParameter("datetime");

        String body =
          "<form action='FurnitureAssemblyQuoteWorkerSelect'>"
        + "<input type='hidden' name='small-furniture' value='" + small + "'/>"
        + "<input type='hidden' name='medium-furniture' value='" + medium + "'/>"
        + "<input type='hidden' name='large-furniture' value='" + large + "'/>"
        + "<input type='hidden' name='datetime' value='" + oldDate + "'/>";

        pw.print(body);
		utility.printHtml("WorkerList.html");
        body = "</form>";
        pw.print(body);
		utility.printHtml("Footer.html");
	}

}
