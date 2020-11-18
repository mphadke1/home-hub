import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.io.*;
import java.sql.*;

@WebServlet("/CustomerOrders")

public class CustomerOrders extends HttpServlet {
	private String error_msg;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		displayCustomerOrders(request, response);
	}

	/* Display Account Details of the Customer (Name and Usertype) */

	protected void displayCustomerOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		try {
			response.setContentType("text/html");
			if(!utility.isLoggedin()) {
				HttpSession session = request.getSession(true);				
				session.setAttribute("login_msg", "Please Login add customers");
				response.sendRedirect("Login");
				return;
			}

			HttpSession session = request.getSession(); 	
			utility.printHtml("Header.html");
			utility.printNavbar();
			utility.printHtml("LeftNavigationBar.html");

			User user = utility.getUser();
			String body =
			  "            <div class='col-12 col-sm-9'>"
			+ "                <h2 class='m-3'>Customer Orders</h2>"
			+ "                <hr>"
			+ "                <p>Username: <span class='font-weight-bold'>" + user.getName() + "</span></p>"
			+ "                <p>User Type: <span class='font-weight-bold'>" + user.getUsertype() + "</span></p>";

			HashMap<Integer, ArrayList<OrderPayment>> orderPayments = new HashMap<Integer, ArrayList<OrderPayment>>();
			String TOMCAT_HOME = System.getProperty("catalina.home");
			try {
				// FileInputStream fileInputStream = new FileInputStream(new File(TOMCAT_HOME+"\\webapps\\assignment1\\PaymentDetails.txt"));
				// ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);	      
				// orderPayments = (HashMap)objectInputStream.readObject();
				orderPayments=MySqlDataStoreUtilities.selectOrder();
			}
			catch(Exception e) {

			}
			int size = 0;
			for(Map.Entry<Integer, ArrayList<OrderPayment>> entry : orderPayments.entrySet()) {
				for(OrderPayment od:entry.getValue())	
				// if(od.getUserName().equals(user.getName()))
				size++;
			}

			if(size > 0) {

                body +=
                  "                <div>"
                + "                    <table class='table'>"
                + "                        <thead>"
                + "                            <tr>"
                + "                                <th>Order ID</th>"
                + "                                <th>Product Ordered</th>"
                + "                                <th>Product Price</th>"
                + "                                <th>Pickup Type</th>"
                + "                                <th>Action</th>"
                + "                            </tr>"
                + "                        </thead>"
                + "                        <tbody>";

                for(Map.Entry<Integer, ArrayList<OrderPayment>> entry : orderPayments.entrySet()) {
                    for(OrderPayment oi:entry.getValue()) {

						body +=
							"                            <tr>"
						+ "                                <td>" + oi.getOrderId() + "</td>"
						+ "                                <td>" + oi.getOrderName() + "</td>"
						+ "                                <td>$ " + oi.getOrderPrice() + "</td>"
						+ "                                <td>" + oi.getPickupType() + "</td>"
						+ "                                <td>"
						+ "                                    <form action='ViewOrder' method='GET'>"
						+ "                                        <input type='hidden' name='orderName' value='" + oi.getOrderName() + "'>"
						+ "                                        <input type='hidden' name='orderId' value='" + oi.getOrderId() + "'>"
						+ "                                        <input type='hidden' name='Order' value='CancelOrder'>"
						+ "                                        <button id='buy-button' class='btn btn-sm text-light'>Cancel Order</button>"
						+ "                                    </form>"
						+ "                                </td>"
						+ "                            </tr>";
                    }
                }
                
                body +=
                  "                        </tbody>"
                + "                    </table>"
                + "                </div>";
            }

            body +=
              "            </div>"
            + "        </div>"
            + "    </div>";
            
            pw.print(body);
			utility.printHtml("Footer.html");	        	
		} catch(Exception e) {

		}		
	}
}
