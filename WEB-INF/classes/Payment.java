import java.util.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Payment")

public class Payment extends HttpServlet {
	
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		

		Utilities utility = new Utilities(request, pw);
		if(!utility.isLoggedin())
		{
			HttpSession session = request.getSession(true);				
			session.setAttribute("login_msg", "Please Login to Pay");
			response.sendRedirect("Login");
			return;
		}
		// get the payment details like credit card no address processed from cart servlet	

		String userAddress = request.getParameter("userAddress");
		String creditCardNo = request.getParameter("creditCardNo");
		String pickupType = request.getParameter("pickupType");
		String storeId = request.getParameter("storeId");

		Store store = null;
		if(pickupType == "Home Delivery") {
			storeId = "0";
		} else {
			store = MySqlDataStoreUtilities.getStoreById(Integer.parseInt(storeId));
		}

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String orderDate = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, 14);
		String deliveryDate = sdf.format(cal.getTime());
		cal.setTime(new Date());
		cal.add(Calendar.DATE, 9);
		String maxOrderCancellationDate = sdf.format(cal.getTime());

        String body =
              "            <div class='col-12 col-sm-9'>"
            + "                <h2 class='mx-3'>Order Details</h2>"
            + "                <hr>";

		if(!userAddress.trim().isEmpty() && !creditCardNo.trim().isEmpty()) {
			
            int orderId = utility.getOrderPaymentSize() + 1;

			//iterate through each order

			for (OrderItem oi : utility.getCustomerOrders()) {
				//set the parameter for each column and execute the prepared statement
				utility.storePayment(
                    orderId,
                    oi.getName(),
                    oi.getPrice(),
                    userAddress,
                    creditCardNo,
                    orderDate,
                    deliveryDate,
                    maxOrderCancellationDate,
                    pickupType,
					storeId
                );
			}

			//remove the order details from cart after processing
			
			OrdersHashMap.orders.remove(utility.username());	
			utility.printHtml("Header.html");
            utility.printNavbar();
			utility.printHtml("LeftNavigationBar.html");

            body +=
              "                <div class='m-3 alert alert-info'>"
            + "                    <p>Your order has been stored.</p>"
            + "                    <p class='mb-0'>Your Order Number is <span class='font-weight-bold'>" + orderId + "</span></p>"
            + "                    <p class='mb-0'>Your Order Date is <span class='font-weight-bold'>" + orderDate + "</span></p>"
            + "                    <p class='mb-0'>Your Delivery Date is <span class='font-weight-bold'>" + deliveryDate + "</span></p>"
            + "                    <p class='mb-0'>Your Pickup Type is <span class='font-weight-bold'>" + pickupType + "</span></p>";
			if(pickupType.equals("Store Pickup") && store != null) {
				body += 
				  "                    <p class='mb-0'><span class='font-weight-bold'>Store Details:</span></p>"
				+ "                    <p class='mb-0'>Street: <span class='font-weight-bold'>" + store.getStreet() + "</span></p>"
				+ "                    <p class='mb-0'>City: <span class='font-weight-bold'>" + store.getCity() + "</span></p>"
				+ "                    <p class='mb-0'>State: <span class='font-weight-bold'>" + store.getState() + "</span></p>"
				+ "                    <p class='mb-0'>Zipcode: <span class='font-weight-bold'>" + store.getZipcode() + "</span></p>";
			}
			body +=
              "                    <p class='mb-0'>You can cancel your order before <span class='font-weight-bold'>" + maxOrderCancellationDate + "</span></p>"
            + "                </div>";

			// pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
			// pw.print("<a style='font-size: 24px;'>Order</a>");
			// pw.print("</h2><div class='entry'>");
		
			// pw.print("<h2>Your Order");
			// pw.print("&nbsp&nbsp");  
			// pw.print("is stored ");
			// pw.print("<br>Your Order No is "+(orderId));
			// pw.print("</h2></div></div></div>");		
			// utility.printHtml("Footer.html");
		} else {

            body +=
              "                <div class='m-3 alert alert-error'>"
            + "                    Please enter valid address and credit card number"
            + "                </div>";


			// utility.printHtml("Header.html");
			// utility.printHtml("LeftNavigationBar.html");
			// pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
			// pw.print("<a style='font-size: 24px;'>Order</a>");
			// pw.print("</h2><div class='entry'>");
		
			// pw.print("<h4 style='color:red'>Please enter valid address and creditcard number</h4>");
			// pw.print("</h2></div></div></div>");		
			// utility.printHtml("Footer.html");
		}

        body +=
          "            </div>"
        + "        </div>"
        + "    </div>";

        pw.print(body);
        utility.printHtml("Footer.html");
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		
		
	}
}
