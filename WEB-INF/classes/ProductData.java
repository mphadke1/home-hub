	
import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;


@WebServlet("/ProductData")
public class ProductData extends HttpServlet {
	
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException 
    {
		try {
            PrintWriter pw = response.getWriter();
            Utilities utility = new Utilities(request,pw);
			String searchId = request.getParameter("searchId");

            utility.printHtml("Header.html");
            utility.printHtml("LeftNavigationBar.html");
            
			Product product = MySqlDataStoreUtilities.getProductById(searchId);

			String body = 
			  "			<div class='col-12 col-sm-9'>"
			+ "                <h2 class='m-3'>Search Results</h2>"
			+ "                <div class='row row-cols-2'>"
			+ "                    <div class='col-6 my-2'>"
			+ "                        <div class='card p-3'>"
			+ "                            <img id='card-img' src='images/" + product.getImage() + "' class='card-img-top'>"
			+ "                            <div class='card-body'>"
			+ "                                <h5 class='card-title'>" + product.getName() + "</h5>"
			+ "                                <p class='card-text'>$ " + product.getPrice() + "</p>"
			+ "					    		   <form class='float-right' method='POST' action='Cart'>"
			+ "                                    <input type='hidden' name='name' value='" + product.getName() + "'>"
			+ "                                    <input type='hidden' name='type' value='tvs'>"
			+ "                                    <input type='hidden' name='maker' value='" + product.getRetailer() + "'>"
			+ "                                    <input type='hidden' name='access' value=''>"
			+ "                                    <button id='buy-button' type='submit' class='btn btn-sm text-light'>Buy Now</button>"
			+ "                                </form>"
			+ "					    		   <form method='POST' action='WriteReview'>"
			+ "                                    <input type='hidden' name='name' value='" + product.getName() + "'>"
			+ "                                    <input type='hidden' name='type' value='tv'>"
			+ "                                    <input type='hidden' name='maker' value='" + product.getRetailer() + "'>"
			+ "                                    <input type='hidden' name='price' value='" + product.getPrice() + "'>"
			+ "                                    <input type='hidden' name='access' value=''>"
			+ "                                    <button id='buy-button' type='submit' class='btn btn-sm text-light'>Write Review</button>"
			+ "                                </form>"
			+ "                            </div>"
			+ "                        </div>"
			+ "                    </div>"
			+ "                </div>"
			+ "            </div>"
			+ "        </div>"
			+ "    </div>";

			pw.print(body);
            utility.printHtml("Footer.html");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void destroy()	{
      // do nothing.
	}
	

}