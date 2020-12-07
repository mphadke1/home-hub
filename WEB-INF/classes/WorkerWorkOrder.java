import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/WorkerWorkOrder")

public class WorkerWorkOrder extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		
		Utilities utility = new Utilities(request,pw);
		utility.printHtml("Header.html");
		utility.printNavbar();
		utility.printHtml("WorkerWorkOrder.html");
		utility.printHtml("Footer.html");
	}

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
		
		Utilities utility = new Utilities(request,pw);
		utility.printHtml("Header.html");
		utility.printNavbar();
        
        String body =
              "    <div class='container mt-2 py-3 bg-light'>"
            + "        <div class='alert alert-success' style='color:Green'>"
            + "            Your change has been Submitted"
            + "        </div>"
            + "    </div>";

        pw.print(body);
		utility.printHtml("Footer.html");
    }
    
}
