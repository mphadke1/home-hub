import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/SubmitReview")

public class SubmitReview extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {	
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        Utilities utility= new Utilities(request, pw);
        storeReview(request, response);
    }
    
    protected void storeReview(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            response.setContentType("text/html");
            PrintWriter pw = response.getWriter();
            Utilities utility = new Utilities(request,pw);
            if(!utility.isLoggedin())
            {
                HttpSession session = request.getSession(true);				
                session.setAttribute("login_msg", "Please Login to add items to cart");
                response.sendRedirect("Login");
                return;
            }
            String productname=request.getParameter("productname");		
            String producttype=request.getParameter("producttype");
            String productprice=request.getParameter("productprice");
            System.out.println("PRICE:");
            System.out.println(productprice);
            String productmaker=request.getParameter("productmaker");
            String reviewrating=request.getParameter("reviewrating");
            String reviewdate=request.getParameter("reviewdate");
            String reviewtext=request.getParameter("reviewtext");
            String retailerpin=request.getParameter("zipcode");
            String retailercity = request.getParameter("retailercity");
            String reviewerAge = request.getParameter("reviewerAge");
            String reviewerGender = request.getParameter("reviewerGender");
            String reviewerOccupation = request.getParameter("reviewerOccupation");
            String message = utility.storeReview(
                productname,
                producttype,
                productmaker,
                reviewrating,
                reviewdate,
                reviewtext,
                retailerpin,
                productprice,
                retailercity,
                reviewerAge,
                reviewerGender,
                reviewerOccupation
            );

            utility.printHtml("Header.html");
            utility.printNavbar();
            utility.printHtml("LeftNavigationBar.html");

            String body = 
              "            <div class='col-12 col-sm-9'>";
            
            if(message.equals("Successfull")) {
                body +=
                  "                <div class='m-3 alert alert-info'>"
                + "                    Review for &nbsp" + productname + " Stored"
                + "                </div>";
            }
            else {
                body +=
                  "                <div class='m-3 alert alert-danger'>"
                + "                    Some error occured"
                + "                </div>";
            }

            body +=
              "            </div>"
            + "        </div>"
            + "    </div>";

            pw.print(body);
            utility.printHtml("Footer.html");
        }
        catch(Exception e)
		{
            System.out.println(e.getMessage());
		}  				
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {	
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();	
    }
}