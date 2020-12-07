import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/WriteReview")
	//once the user clicks writereview button from products page he will be directed
 	//to write review page where he can provide reqview for item rating reviewtext	
	
public class WriteReview extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {	
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
        Utilities utility= new Utilities(request, pw);
		review(request, response);
	}
	
    protected void review(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
                   
            response.setContentType("text/html");
            PrintWriter pw = response.getWriter();
            Utilities utility = new Utilities(request,pw);
            if(!utility.isLoggedin())
            {
                HttpSession session = request.getSession(true);				
                session.setAttribute("login_msg", "Please Login to Write a Review");
                response.sendRedirect("Login");
                return;
		    }
            String productname="";
            String producttype="";
            String productmaker="";
            String productprice="";


            utility.printHtml("Header.html");
            utility.printNavbar();
            // utility.printHtml("LeftNavigationBar.html");

            String body = 
              "    <div class='container mt-3 bg-light'>"
            + "        <div class='col-12 col-sm-9'>"
            + "            <h3 class='mx-3'>Write Review</h3>"
            + "            <hr>"
            + "            <form action='SubmitReview' method='POST'>"
            + "                <div class='form-row'>"
            + "                    <div class='form-group col-6'>"
            + "                        <label for='productname'>Worker Name</label>"
            + "                        <input class='form-control' type='text' name='productname' id='productname' value='Edgar Leeder' placeholder='Enter Worker Name' required readonly='true'>"
            + "                    </div>"
            + "                    <div class='form-group col-6'>"
            + "                        <label for='producttype'>Service Type</label>"
            + "                        <input class='form-control' type='text' name='producttype' id='producttype' value='Furniture Assembly' placeholder='Enter Service Type' required readonly='true'>"
            + "                    </div>"
            + "                </div>"
            + "                <div class='form-row'>"
            + "                    <div class='form-group col-6'>"
            + "                        <label for='productprice'>Service Price ($)</label>"
            + "                        <input class='form-control' step='any' type='number' name='productprice' id='productprice' value='70' placeholder='Enter Product Price' required readonly='true'>"
            + "                    </div>"
            + "                </div>"
            + "                <div class='form-row'>"
            + "                    <div class='form-group col-2'>"
            + "                        <label for='reviewrating'>Worker Rating</label>"
            + "                        <select class='form-control' name='reviewrating' id='reviewrating' required>"
            + "                            <option value='1'>1</option>"
            + "                            <option value='2'>2</option>"
            + "                            <option value='3'>3</option>"
            + "                            <option value='4'>4</option>"
            + "                            <option value='5'>5</option>"
            + "                        </select>"
            + "                    </div>"
            + "                </div>"
            + "                <div class='form-row'>"
            + "                    <div class='form-group col-6'>"
            + "                        <label for='zipcode'>Zip Code</label>"
            + "                        <input class='form-control' step='1' type='number' name='zipcode' id='zipcode' value='60616' placeholder='Enter Zip Code' required disabled>"
            + "                    </div>"
            + "                    <div class='form-group col-6'>"
            + "                        <label for='retailercity'>City</label>"
            + "                        <input class='form-control' type='text' name='retailercity' id='retailercity' value='Chicago' placeholder='Enter City' required disabled>"
            + "                    </div>"
            + "                </div>"
            + "                <div class='form-row'>"
            + "                    <div class='form-group col-3'>"
            + "                        <label for='reviewerAge'>Reviewer Age</label>"
            + "                        <input class='form-control' step='1' type='number' name='reviewerAge' id='reviewerAge' placeholder='Enter Age' required>"
            + "                    </div>"
            + "                    <div class='form-group col-3'>"
            + "                        <label for='reviewerGender'>Reviewer Gender</label>"
            + "                        <select class='form-control' name='reviewerGender' id='reviewerGender'required>"
            + "                            <option value='Male'>Male</option>"
            + "                            <option value='Female'>Female</option>"
            + "                            <option value='Other'>Other</option>"
            + "                        </select>"
            + "                    </div>"
            + "                    <div class='form-group col-6'>"
            + "                        <label for='reviewerOccupation'>Reviewer Occupation</label>"
            + "                        <input class='form-control' type='text' name='reviewerOccupation' id='reviewerOccupation' placeholder='Enter Occupation' required>"
            + "                    </div>"
            + "                </div>"
            + "                <div class='form-row'>"
            + "                    <div class='form-group col-4'>"
            + "                        <label for='reviewdate'>Review Date</label>"
            + "                        <input class='form-control' type='date' name='reviewdate' id='reviewdate' required>"
            + "                    </div>"
            + "                </div>"
            + "                <div class='form-group'>"
            + "                    <label for='reviewtext'>Review Text</label>"
            + "                    <textarea class='form-control' rows='4' name='reviewtext' id='reviewtext' required></textarea>"
            + "                </div>"
            + "                <input type='submit' id='buy-button' class='btn text-light' value='Submit Review'>"
            + "            </form>"
            + "        </div>"
            + "    </div>";
            // + "</div>";

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
        doPost(request, response);
    }
}
