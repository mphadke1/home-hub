import java.io.*;
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

@WebServlet("/ApplyForLeave")

public class ApplyForLeave extends HttpServlet {
	
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		Utilities utility = new Utilities(request, pw);
		//check if the user is logged in
		if(!utility.isLoggedin()){
			HttpSession session = request.getSession(true);				
			session.setAttribute("login_msg", "Please Login to View your Orders");
			response.sendRedirect("Login");
			return;
		}
		String username = utility.username();
		utility.printHtml("Header.html");
        utility.printNavbar();
		utility.printHtml("LeftNavigationBar.html");

        String body =
          "        <div class='col-12 col-sm-9'>"
        + "            <h2 class='m-3'>Apply For Leave</h2>"
        + "            <hr>";

		
	
		if(request.getParameter("Order") == null) {

            body +=
              "            <div class='m-3'>"
            + "                <form action='ViewOrder' method='GET'>"
			+ "                <b>Reason For Leave</b>"
			+ "                <select status='selectType' class='input'><option value='Medical Leave' >Medical Leave</option><option value='Personal Leave' selected>Personal leave</option><option value='Family Issue Leave' >Family Issue Leave</option><option value='Other Reason' >Other Reason</option>"
            + "                    <input type='hidden' name='Order' value='ViewOrder'>"
            + "                    <button id='buy-button' class='btn text-light' type='submit'>Submit</button>"
            + "                </form>"
            + "            </div>";

			// pw.print("<table align='center'><tr><td>Enter OrderNo &nbsp&nbsp<input name='orderId' type='text'></td>");
			// pw.print("<td><input type='submit' name='Order' value='ViewOrder' class='btnbuy'></td></tr></table>");
		}

		//hashmap gets all the order details from file 

		HashMap<Integer, ArrayList<OrderPayment>> orderPayments = new HashMap<Integer, ArrayList<OrderPayment>>();
		String TOMCAT_HOME = System.getProperty("catalina.home");

		try {
			FileInputStream fileInputStream = new FileInputStream(new File(TOMCAT_HOME+"\\webapps\\home-hub\\PaymentDetails.txt"));
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);	      
			orderPayments = (HashMap)objectInputStream.readObject();
		}
		catch(Exception e){

		}
		

		/*if order button is clicked that is user provided a order number to view order 
		order details will be fetched and displayed in  a table 
		Also user will get an button to cancel the order */

		if(request.getParameter("Order") != null && request.getParameter("Order").equals("ViewOrder")) {
			if (request.getParameter("orderId") != null && request.getParameter("orderId") != "" ) {
				int orderId = Integer.parseInt(request.getParameter("orderId"));
				pw.print("<input type='hidden' name='orderId' value='"+orderId+"'>");
				//get the order details from file
				try {
					// FileInputStream fileInputStream = new FileInputStream(new File(TOMCAT_HOME+"\\webapps\\assignment1\\PaymentDetails.txt"));
					// ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);	      
					// orderPayments = (HashMap)objectInputStream.readObject();
				
					orderPayments=MySqlDataStoreUtilities.selectOrder();
				}
				catch(Exception e) {
			
				}

				int size = 0;
			

				/*get the order size and check if there exist an order with given order number 
				if there is no order present give a message no order stored with this id */

				if(orderPayments.get(orderId) != null) {
                    for(OrderPayment od:orderPayments.get(orderId))	
                    if(od.getUserName().equals(username))
                    size = orderPayments.get(orderId).size();
				}
				// display the orders if there exist order with order id
				if(size > 0) {

                    body +=
                      "                <div>"
                    + "                    <table class='table'>"
                    + "                        <thead>"
                    + "                            <tr>"
                    + "                                <th>Order ID</th>"
                    + "                                <th>Username</th>"
                    + "                                <th>Product Ordered</th>"
                    + "                                <th>Product Price</th>"
                    + "                                <th>Pickup Type</th>"
                    + "                                <th>Action</th>"
                    + "                            </tr>"
                    + "                        </thead>"
                    + "                        <tbody>";

					for (OrderPayment oi : orderPayments.get(orderId)) {
                        body +=
                          "                            <tr>"
                        + "                                <td>" + oi.getOrderId() + "</td>"
                        + "                                <td>" + oi.getUserName() + "</td>"
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

                    body +=
                      "                        </tbody>"
                    + "                    </table>"
                    + "                </div>";

					// pw.print("<table  class='gridtable'>");
					// pw.print("<tr><td></td>");
					// pw.print("<td>OrderId:</td>");
					// pw.print("<td>UserName:</td>");
					// pw.print("<td>productOrdered:</td>");
					// pw.print("<td>productPrice:</td></tr>");
					// for (OrderPayment oi : orderPayments.get(orderId)) {
					// 	pw.print("<tr>");			
					// 	pw.print("<td><input type='radio' name='orderName' value='"+oi.getOrderName()+"'></td>");			
					// 	pw.print("<td>"+oi.getOrderId()+".</td><td>"+oi.getUserName()+"</td><td>"+oi.getOrderName()+"</td><td>Price: "+oi.getOrderPrice()+"</td>");
					// 	pw.print("<td><input type='submit' name='Order' value='CancelOrder' class='btnbuy'></td>");
					// 	pw.print("</tr>");
					
					// }
					// pw.print("</table>");
				}
				else {
                    body +=
                      "                    <div class='alert alert-danger'>"
                    + "                        You have not placed any order with this order ID"
                    + "                    </div>";
					// pw.print("<h4 style='color:red'>You have not placed any order with this order id</h4>");
				}
			} else {
                body +=
                      "                    <div class='alert alert-danger'>"
                    + "                        Please enter valid order number"
                    + "                    </div>";
				// pw.print("<h4 style='color:red'>Please enter the valid order number</h4>");	
			}
		}
		//if the user presses cancel order from order details shown then process to cancel the order
		if(request.getParameter("Order") != null && request.getParameter("Order").equals("CancelOrder")) {
			if(request.getParameter("orderName") != null) {
				String orderName = request.getParameter("orderName");
				int orderId = 0;
				orderId = Integer.parseInt(request.getParameter("orderId"));
				ArrayList<OrderPayment> listOrderPayment =new ArrayList<OrderPayment>();
				//get the order from file
				try {
					// FileInputStream fileInputStream = new FileInputStream(new File(TOMCAT_HOME+"\\webapps\\assignment1\\PaymentDetails.txt"));
					// ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);	      
					// orderPayments = (HashMap)objectInputStream.readObject();
				
					orderPayments=MySqlDataStoreUtilities.selectOrder();
				} catch(Exception e) {
			
				}
				//get the exact order with same ordername and add it into cancel list to remove it later
				for (OrderPayment oi : orderPayments.get(orderId)) {
					String maxDate = oi.getMaxOrderCancellationDate();
					SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

					try {
						Date formattedMaxDate = new SimpleDateFormat("MM/dd/yyyy").parse(maxDate);

						String currentDate = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
						Date formattedCurrentDate = new SimpleDateFormat("MM/dd/yyyy").parse(currentDate);

						if(formattedMaxDate.compareTo(formattedCurrentDate) > 0)
						{
							MySqlDataStoreUtilities.deleteOrder(orderId,orderName);
							listOrderPayment.add(oi);
							body +=
							  "                         <div class='alert alert-info'>"
							+ "                             Your order has been cancelled"
							+ "                         </div>";
						} else {
							body +=
							  "                         <div class='alert alert-danger'>"
							+ "                             Less than 5 days left for delivery. You cannot cancel order now."
							+ "                         </div>";
						}
					} catch(Exception e) {

					}
                }
				//remove all the orders from hashmap that exist in cancel list
				orderPayments.get(orderId).removeAll(listOrderPayment);
				if(orderPayments.get(orderId).size() == 0) {
                    orderPayments.remove(orderId);
                }
				//save the updated hashmap with removed order to the file	
				// try {	
				// 	FileOutputStream fileOutputStream = new FileOutputStream(new File(TOMCAT_HOME+"\\webapps\\assignment1\\PaymentDetails.txt"));
				// 	ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
				// 	objectOutputStream.writeObject(orderPayments);
				// 	objectOutputStream.flush();
				// 	objectOutputStream.close();       
				// 	fileOutputStream.close();
				// } catch(Exception e) {
				
				// }	
			} else {
                body +=
                  "                <div class='alert alert-danger'>"
                + "                    Please select any product"
                + "                </div>";
				pw.print("<h4 style='color:red'>Please select any product</h4>");
			}
		}

        body +=
          "                </div>"
        + "            </div>"
        + "        </div>";

        pw.print(body);

		// pw.print("</form></div></div></div>");
		//utility.printHtml("Footer.html");
	}

}


