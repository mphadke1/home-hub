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

@WebServlet("/Inventory")
public class Inventory extends HttpServlet
{
	public static ArrayList <NoOfAvailableProducts> fakeAvailableProducts = new ArrayList <NoOfAvailableProducts>(); 
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
        //displayInventory(request, response);
        Utilities utility = new Utilities(request, pw);

        try
        {
            if(!utility.isLoggedin())
			{
				HttpSession session = request.getSession(true);
				session.setAttribute("login_msg", "Please Login to view Inventory.");
				response.sendRedirect("Login");
				return;
            }

			utility.printHtml("Header.html");
            utility.printNavbar();

            String body =
              "        <div class='container mt-3 bg-light'>"
            + "            <div class='col-12 col-sm-9'>"
            + "                <h1 class='mb-3'>Service Reports</h1>"
            + "                <hr>"
            + "                <h4 class='mb-3'>Available Services Table</h4>"
            + "                <table class='gridDataTable display'>"
            + "                    <thead>"
            + "                        <tr>"
            + "                            <td>Sr No.</td>"
            + "                            <td>Service Name</td>"
            // + "                            <td>Product Price</td>"
            + "                            <td>Number of Orders</td>"
            + "                        </tr>"
            + "                    </thead>"
            + "                    <tbody>";
            ArrayList <NoOfAvailableProducts> availableProductsList = new ArrayList <NoOfAvailableProducts> ();
            // availableProductsList = MySqlDataStoreUtilities.availableProductsList();
            availableProductsList = getFakeAvailableProducts();
            int i = 1;
            for(NoOfAvailableProducts product : availableProductsList) {
                body +=
                  "                        <tr>"
                + "                            <td>" + i + "</td>"
                + "                            <td>" + product.getProductName() + "</td>"
                // + "                            <td>" + product.getProductPrice() + "</td>"
                + "                            <td>" + product.getNumberOfAvailableProducts() + "</td>"
                + "                        </tr>";
                i++;
            }

            body +=
              "                    </tbody>"
            + "                </table>"
            + "                <hr>"
            + "                <h4 class='mb-3'>Available Services Graph</h4>"
            + "                <div id='chartDivNumberOfAvailableProducts' style='height: 150vh'></div>"
            + "                <hr class='mt-5'>"
            + "                <h4 class='mb-3'>Service Sales</h4>"
            + "                <table class='gridDataTable display'>"
            + "                    <thead>"
            + "                        <tr>"
            + "                            <td>Sr No.</td>"
            + "                            <td>Service Name</td>"
            + "                            <td>Service Price</td>"
            + "                            <td>Number of Orders</td>"
            + "                            <td>Total Sale</td>"
            + "                        </tr>"
            + "                    </thead>"
            + "                    <tbody>";
            ArrayList <NoOfAvailableProducts> currentOnSaleProductsList = new ArrayList <NoOfAvailableProducts> ();
            // currentOnSaleProductsList = MySqlDataStoreUtilities.currentOnSaleProductsList();
            currentOnSaleProductsList = getFakeAvailableProducts();
            i = 1;
            for(NoOfAvailableProducts product : currentOnSaleProductsList) {
                body +=
                  "                        <tr>"
                + "                            <td>" + i + "</td>"
                + "                            <td>" + product.getProductName() + "</td>"
                + "                            <td>" + product.getProductPrice() + "</td>"
                + "                            <td>" + product.getNumberOfAvailableProducts() + "</td>"
                + "                            <td>" + Integer.parseInt(product.getProductPrice()) * Integer.parseInt(product.getNumberOfAvailableProducts()) + "</td>"
                + "                        </tr>";
                i++;
            }
            body +=
              "                    </tbody>"
            + "                </table>"
            + "            </div>"
            + "        </div>"
            // + "    </div>"
            + "    <script type='text/javascript' src=\"https://www.gstatic.com/charts/loader.js\"></script>"
            + "    <script type='text/javascript' src='inventory.js'></script>";

            pw.print(body);

            utility.printHtml("Footer.html");
        }
        catch(Exception e)
        {

        }
    }

	public static ArrayList <NoOfAvailableProducts> getFakeAvailableProducts() {

		if(fakeAvailableProducts.isEmpty()) {
			String[] servicesArray = {
				"Home Cleaning",
				"Handyman Services",
				"Hanging Pictures & Shelves",
				"Office Cleaning",
				"TV Mounting",
				"Home Theater AV Setup",
				"Bed Assembly",
				"Office Furniture Assembly",
				"Faucet Replacement",
				"Toilet Trouble",
				"Plumbing Services",
				"Outlet Installation",
				"Light Fixtures",
				"Electrical Service",
				"Interior Painting",
				"Bedroom Painting",
				"Accent Wall Painting",
				"Moving Help",
				"Smart Home Hub Setup",
				"Furniture Assembly",
				"Smart Security Cam Installation",
				"Smart Device Installation"
			};

			for(int i = 0; i < servicesArray.length; i++) {
				Random rn = new Random();
				fakeAvailableProducts.add(new NoOfAvailableProducts(
					servicesArray[i],
					String.valueOf(rn.nextInt(100) + 1),
					String.valueOf((rn.nextInt(50) + 1)*10)));
			}
		}

		return fakeAvailableProducts;
	}
}