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

@WebServlet("/SalesReport")
public class SalesReport extends HttpServlet
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
				session.setAttribute("login_msg", "Please Login to view Sales Report.");
				response.sendRedirect("Login");
				return;
            }
            
            utility.printHtml("Header.html");
            utility.printNavbar();
            utility.printHtml("LeftNavigationBar.html");

            String body =
              "            <div class='col-12 col-sm-9'>"
            + "                <h1 class='mb-3'>Sales</h1>"
            + "                <hr>"
            + "                <h4 class='mb-3'>Total Products Sold and Sales Table</h4>"
            + "                <table class='gridDataTable display'>"
            + "                    <thead>"
            + "                        <tr>"
            + "                            <td>Sr No.</td>"
            + "                            <td>Product Name</td>"
            + "                            <td>Product Price</td>"
            + "                            <td>Number of Products Sold</td>"
            + "                            <td>Total Sales of Product (Price * No. of Products Sold)</td>"
            + "                        </tr>"
            + "                    </thead>"
            + "                    <tbody>";

            ArrayList <NoOfProductsSold> totalSoldProductsList = new ArrayList <NoOfProductsSold> ();
            totalSoldProductsList = MySqlDataStoreUtilities.totalSoldProductsList();
            int i = 1;
            for(NoOfProductsSold product : totalSoldProductsList)
            {
                body +=
                  "                        <tr>"
                + "                            <td>" + i + "</td>"
                + "                            <td>" + product.getProductName() + "</td>"
                + "                            <td>" + product.getProductPrice() + "</td>"
                + "                            <td>" + product.getNoOfProductsSold() + "</td>"
                + "                            <td>" + product.getProductTotalSales() + "</td>"
                + "                        </tr>";
                i++;
            }

            body +=
              "                    </tbody>"
            + "                </table>"
            + "                <hr>"
            + "                <h4 class='mb-3'>Total Prodcusts Sold and Sales Graph</h4>"
            + "                <div id='chartDivTotalProductSales' style='height: 150vh'></div>"
            + "                <hr class='mt-5'>"
            + "                <h4 class='mb-3'>Total Daily Sales Table</h4>"
            + "                <table class='gridDataTable display'>"
            + "                    <thead>"
            + "                        <tr>"
            + "                            <td>Sr No.</td>"
            + "                            <td>Date</td>"
            + "                            <td>Product and Price List</td>"
            + "                            <td>Total Sales</td>"
            + "                        </tr>"
            + "                    </thead>"
            + "                    <tbody>";

            ArrayList <TotalSalesDaily> totalSalesDailyOrdersList = new ArrayList <TotalSalesDaily> ();
            totalSalesDailyOrdersList = MySqlDataStoreUtilities.totalSalesDailyOrdersList();
            i = 1;
            for(TotalSalesDaily order : totalSalesDailyOrdersList)
            {
                body +=
                  "                        <tr>"
                + "                            <td>" + i + "</td>"
                + "                            <td>" + order.getOrderDate() + "</td>"
                + "                            <td>" + order.getProductsListDesciption() + "</td>"
                + "                            <td>" + order.getTotalDailySales() + "</td>"
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
            + "    <script type='text/javascript' src='salesReport.js'></script>";

            pw.print(body);

            utility.printHtml("Footer.html");
        }
        catch(Exception e)
        {

        }
    }
}