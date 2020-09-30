import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.*;

@WebServlet("/CheckOut")

//once the user clicks buy now button page is redirected to checkout page where user has to give checkout information

public class CheckOut extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
	        Utilities Utility = new Utilities(request, pw);
		storeOrders(request, response);
	}
	
	protected void storeOrders(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
	    
        try {
            response.setContentType("text/html");
            PrintWriter pw = response.getWriter();
            Utilities utility = new Utilities(request,pw);
            if(!utility.isLoggedin()) {
                HttpSession session = request.getSession(true);				
                session.setAttribute("login_msg", "Please Login to add items to cart");
                response.sendRedirect("Login");
                return;
            }
            HttpSession session = request.getSession(); 

            //get the order product details	on clicking submit the form will be passed to submitorder page	
            
            String userName = session.getAttribute("username").toString();
            String orderTotal = request.getParameter("orderTotal");
            utility.printHtml("Header.html");
            utility.printNavbar();
            utility.printHtml("LeftNavigationBar.html");

            String body =
              "            <div class='col-12 col-sm-9'>"
            + "                <h2 class='my-3'>Order Details</h2>"
            + "                <hr>"
            + "                <div class='row mt-4'>"
            + "                    <div class='col-6'>"
            + "                        <table class='table table-bordered table-sm'>"
            + "                            <tr>"
            + "                                <td>Customer Name :</td>"
            + "                                <td>" + userName + "</td>"
            + "                            </tr>";
            for (OrderItem oi : utility.getCustomerOrders()) {
                body +=
                  "                            <tr>"
                + "                                <td>Product Purchased</td>"
                + "                                <td>" + oi.getName() + "</td>"
                + "                            </tr>"
                + "                            <tr>"
                + "                                <td>Product Price</td>"
                + "                                <td>$ " + oi.getPrice() + "</td>"
                + "                            </tr>";
            }
            body +=
              "                            <tr class='table-info font-weight-bold'>"
            + "                                <td>Total Order Cost</td>"
            + "                                <td>$ " + orderTotal + "</td>"
            + "                            </tr>"
            + "                        </table>"
            + "                    </div>"
            + "                </div>"
            + "                <div class='row mt-4'>"
            + "                    <div class='col-5'>"
            + "                        <form action='Payment' method='POST'>"
            + "                            <div class='form-group'>"
            + "                                <label for='creditCardNo'>Credit / Account Number</label>"
            + "                                <input class='form-control' type='text' name='creditCardNo' id='creditCardNo' placeholder='Enter Credit / Account Number' required>"
            + "                            </div>"
            + "                            <div class='form-group'>"
            + "                                <label for='userAddress'>Customer Address</label>"
            + "                                <input class='form-control' type='text' name='userAddress' id='userAddress' placeholder='Enter Address' required>"
            + "                            </div>"
            + "                            <div class='form-group'>"
            + "                                <label for='pickupType'>Pickup Type</label>"
            + "                                <select class='form-control custom-select' id='pickupType' name='pickupType'>"
            + "                                    <option value='Store Pickup'>Store Pickup</option>"
            + "                                    <option value='Home Delivery'>Home Delivery</option>"
            + "                                </select>"
            + "                            </div>"
            + "                            <button id='checkout-button' type='submit'"
            + "                                class='btn btn-lg text-light'>Place Order</button>"
            + "                        </form>"
            + "                    </div>"
            + "                </div>"
            + "            </div>"
            + "        </div>"
            + "    </div>";

            pw.print(body);
            utility.printHtml("Footer.html");



            // pw.print("<form name ='CheckOut' action='Payment' method='post'>");
            // pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
            // pw.print("<a style='font-size: 24px;'>Order</a>");
            // pw.print("</h2><div class='entry'>");
            // pw.print("<table  class='gridtable'><tr><td>Customer Name:</td><td>");
            // pw.print(userName);
            // pw.print("</td></tr>");
            // // for each order iterate and display the order name price
            // for (OrderItem oi : utility.getCustomerOrders()) {
            //     pw.print("<tr><td> Product Purchased:</td><td>");
            //     pw.print(oi.getName()+"</td></tr><tr><td>");
            //     pw.print("<input type='hidden' name='orderPrice' value='"+oi.getPrice()+"'>");
            //     pw.print("<input type='hidden' name='orderName' value='"+oi.getName()+"'>");
            //     pw.print("Product Price:</td><td>"+ oi.getPrice());
            //     pw.print("</td></tr>");
            // }
            // pw.print("<tr><td>");
            // pw.print("Total Order Cost</td><td>"+orderTotal);
            // pw.print("<input type='hidden' name='orderTotal' value='"+orderTotal+"'>");
            // pw.print("</td></tr></table><table><tr></tr><tr></tr>");	
            // pw.print("<tr><td>");
            // pw.print("Credit/accountNo</td>");
            // pw.print("<td><input type='text' name='creditCardNo'>");
            // pw.print("</td></tr>");
            // pw.print("<tr><td>");
            // pw.print("Customer Address</td>");
            // pw.print("<td><input type='text' name='userAddress'>");
            // pw.print("</td></tr>");
            // pw.print("<tr><td colspan='2'>");
            // pw.print("<input type='submit' name='submit' class='btnbuy'>");
            // pw.print("</td></tr></table></form>");
            // pw.print("</div></div></div>");		
            // utility.printHtml("Footer.html");
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
    }
}
