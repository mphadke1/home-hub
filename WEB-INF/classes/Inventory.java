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
            utility.printHtml("LeftNavigationBar.html");

            String body =
              "            <div class='col-12 col-sm-9'>"
            + "                <h1 class='mb-3'>Inventory</h1>"
            + "                <hr>"
            + "                <h4 class='mb-3'>Available Products Table</h4>"
            + "                <table class='gridDataTable display'>"
            + "                    <thead>"
            + "                        <tr>"
            + "                            <td>Sr No.</td>"
            + "                            <td>Product Name</td>"
            + "                            <td>Product Price</td>"
            + "                            <td>Number of Products (Available in Store)</td>"
            + "                        </tr>"
            + "                    </thead>"
            + "                    <tbody>";
            ArrayList <NoOfAvailableProducts> availableProductsList = new ArrayList <NoOfAvailableProducts> ();
            availableProductsList = MySqlDataStoreUtilities.availableProductsList();
            int i = 1;
            for(NoOfAvailableProducts product : availableProductsList) {
                body +=
                  "                        <tr>"
                + "                            <td>" + i + "</td>"
                + "                            <td>" + product.getProductName() + "</td>"
                + "                            <td>" + product.getProductPrice() + "</td>"
                + "                            <td>" + product.getNumberOfAvailableProducts() + "</td>"
                + "                        </tr>";
                i++;
            }

            body +=
              "                    </tbody>"
            + "                </table>"
            + "                <hr>"
            + "                <h4 class='mb-3'>Available Products Graph</h4>"
            + "                <div id='chartDivNumberOfAvailableProducts' style='height: 150vh'></div>"
            + "                <hr class='mt-5'>"
            + "                <h4 class='mb-3'>Products Currently on Sale</h4>"
            + "                <table class='gridDataTable display'>"
            + "                    <thead>"
            + "                        <tr>"
            + "                            <td>Sr No.</td>"
            + "                            <td>Product Name</td>"
            + "                            <td>Product Price</td>"
            + "                            <td>Product Discount</td>"
            + "                            <td>Number of Products (Available in Store)</td>"
            + "                        </tr>"
            + "                    </thead>"
            + "                    <tbody>";
            ArrayList <NoOfAvailableProducts> currentOnSaleProductsList = new ArrayList <NoOfAvailableProducts> ();
            currentOnSaleProductsList = MySqlDataStoreUtilities.currentOnSaleProductsList();
            i = 1;
            for(NoOfAvailableProducts product : currentOnSaleProductsList) {
                body +=
                  "                        <tr>"
                + "                            <td>" + i + "</td>"
                + "                            <td>" + product.getProductName() + "</td>"
                + "                            <td>" + product.getProductPrice() + "</td>"
                + "                            <td>" + product.getProductDiscount() + "</td>"
                + "                            <td>" + product.getNumberOfAvailableProducts() + "</td>"
                + "                        </tr>";
                i++;
            }
            body +=
              "                    </tbody>"
            + "                </table>"
            + "                <hr>"
            + "                <h4 class='mb-3'>Products with Manufacturer Rebates</h4>"
            + "                <table class='gridDataTable display'>"
            + "                    <thead>"
            + "                        <tr>"
            + "                            <td>Sr No.</td>"
            + "                            <td>Product Name</td>"
            + "                            <td>Product Price</td>"
            + "                            <td>Product Discount</td>"
            + "                            <td>Manufacturer Rebate</td>"
            + "                        </tr>"
            + "                    </thead>"
            + "                    <tbody>";
            ArrayList <NoOfAvailableProducts> manufacturerRebateProductsList = new ArrayList <NoOfAvailableProducts> ();
            manufacturerRebateProductsList = MySqlDataStoreUtilities.manufacturerRebateProductsList();
            i = 1;
            for(NoOfAvailableProducts product : manufacturerRebateProductsList)
            {
                body +=
                  "                        <tr>"
                + "                            <td>" + i + "</td>"
                + "                            <td>" + product.getProductName() + "</td>"
                + "                            <td>" + product.getProductPrice() + "</td>"
                + "                            <td>" + product.getProductDiscount() + "</td>"
                + "                            <td>" + product.getManufacturerRebate() + "</td>"
                + "                        </tr>";
                i++;
            }
            body +=
              "                    </tbody>"
            + "                </table>"
            + "            </div>"
            + "        </div>"
            + "    </div>"
            + "    <script type='text/javascript' src=\"https://www.gstatic.com/charts/loader.js\"></script>"
            + "    <script type='text/javascript' src='inventory.js'></script>";

            pw.print(body);

            utility.printHtml("Footer.html");
        }
        catch(Exception e)
        {

        }
    }
}