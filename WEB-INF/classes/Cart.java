import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/Cart")

public class Cart extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();


		/* From the HttpServletRequest variable name,type,maker and acessories information are obtained.*/

		Utilities utility = new Utilities(request, pw);
		String name = request.getParameter("name");
		String type = request.getParameter("type");
		String maker = request.getParameter("maker");
		String access = request.getParameter("access");

		/* StoreProduct Function stores the Purchased product in Orders HashMap.*/	
		
		utility.storeProduct(name, type, maker, access);
		displayCart(request, response);
	}
	

/* displayCart Function shows the products that users has bought, these products will be displayed with Total Amount.*/

	protected void displayCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request,pw);
		Carousel carousel = new Carousel();
		if(!utility.isLoggedin()){
			HttpSession session = request.getSession(true);				
			session.setAttribute("login_msg", "Please Login to add items to cart");
			response.sendRedirect("Login");
			return;
		}
		
		utility.printHtml("Header.html");
		utility.printNavbar();
		utility.printHtml("LeftNavigationBar.html");

		String body =
		  "		<div class='col-12 col-sm-9'>"
		+ "                <h2 class='m-3'>Cart</h2>";

		if(utility.getCartCount() <= 0) {
			body +=
			  "                <div class='alert alert-warning my-3'>Your cart is empty</div>";
		} else {
			body +=
			  "                <form class='w-100' method='POST' action='CheckOut'>"
			+ "                    <div class='row'>"
			+ "                        <div class='col-12'>"
			+ "                            <table class='table'>"
			+ "                                <thead>"
			+ "                                    <th scope='col'>#</th>"
			+ "                                    <th scope='col'>Name</th>"
			+ "                                    <th scope='col'>Price</th>"
			+ "                                    <th scope='col'>Action</th>"
			+ "                                </thead>";
			int i = 1;
			double totalPrice = 0;
			for(OrderItem oi : utility.getCustomerOrders()) {
				body +=
				  "                                <tr>"
				+ "                                    <td>" + i + "</td>"
				+ "                                    <td>" + oi.getName() + "</td>"
				+ "                                    <td>$ " + oi.getPrice() + "</td>"
				+ "                                    <td><button class='btn text-light' id='buy-button'>Remove</button></td>"
				+ "                                    <input type='hidden' name='orderName' value='" + oi.getName() + "'>"
				+ "                                    <input type='hidden' name='orderPrice' value='" + oi.getPrice() + "'>"
				+ "                                </tr>";
				totalPrice += oi.getPrice();
				i++;
			}

			String totalString = String.format("%.2f", totalPrice);
			body +=
			  "                                <tr class='table-info'>"
			+ "                                    <td></td>"
			+ "                                    <th>Total</td>"
			+ "                                    <th>$ " + totalString + "</td>"
			+ "                                    <td></td>"
			+ "                                </tr>"
			+ "                            </table>"
			+ "                        </div>"
			+ "                    </div>"
			+ "                    <div class='row'>"
			+ "                        <div class='col-12'>"
			+ "                                <button id='checkout-button' type='submit' class='btn btn-lg text-light float-right'>Checkout</button>"
			+ "                        </div>"
			+ "                    </div>"
			+ "                    <input type='hidden' name='orderTotal' value='" + totalString + "'>"
			+ "                </form>";

		}

		body += carousel.carouselfeature(utility);

		body +=
		  "            </div>"
		+ "        </div>"
		+ "    </div>";

		pw.print(body);

		utility.printHtml("Footer.html");









		// pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		// pw.print("<a style='font-size: 24px;'>Cart("+utility.CartCount()+")</a>");
		// pw.print("</h2><div class='entry'>");
		// pw.print("<form name ='Cart' action='CheckOut' method='post'>");
		// if(utility.CartCount()>0)
		// {
		// 	pw.print("<table  class='gridtable'>");
		// 	int i = 1;
		// 	double total = 0;
		// 	for (OrderItem oi : utility.getCustomerOrders()) 
		// 	{
		// 		pw.print("<tr>");
		// 		pw.print("<td>"+i+".</td><td>"+oi.getName()+"</td><td>: "+oi.getPrice()+"</td>");
		// 		pw.print("<input type='hidden' name='orderName' value='"+oi.getName()+"'>");
		// 		pw.print("<input type='hidden' name='orderPrice' value='"+oi.getPrice()+"'>");
		// 		pw.print("</tr>");
		// 		total = total +oi.getPrice();
		// 		i++;
		// 	}
		// 	pw.print("<input type='hidden' name='orderTotal' value='"+total+"'>");	
		// 	pw.print("<tr><th></th><th>Total</th><th>"+total+"</th>");
		// 	pw.print("<tr><td></td><td></td><td><input type='submit' name='CheckOut' value='CheckOut' class='btnbuy' /></td>");
		// 	pw.print("</table></form>");
		// 	/* This code is calling Carousel.java code to implement carousel feature*/
		// 	
		// }
		// else
		// {
		// 	pw.print("<h4 style='color:red'>Your Cart is empty</h4>");
		// }
		// pw.print("</div></div></div>");		
		// utility.printHtml("Footer.html");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		
		displayCart(request, response);
	}
}
