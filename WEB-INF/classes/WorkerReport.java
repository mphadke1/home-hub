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

@WebServlet("/WorkerReport")
public class WorkerReport extends HttpServlet
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
				session.setAttribute("login_msg", "Please Login to view Worker Report.");
				response.sendRedirect("Login");
				return;
            }
            
            utility.printHtml("Header.html");
            utility.printNavbar();
            utility.printHtml("LeftNavigationBar.html");

            String body =
              "            <div class='col-12 col-sm-9'>"
            + "                <h1 class='mb-3'>Worker Data</h1>"
            + "                <hr>"
            + "                <h4 class='mb-3'>Workers with Highest Rating</h4>"
            + "                <table class='gridDataTable display'>"
            + "                    <thead>"
            + "                        <tr>"
            + "                            <td>Sr No.</td>"
            + "                            <td>Worker Name</td>"
            + "                            <td>Worker Age</td>"
            + "                            <td>Rating</td>"
            + "                        </tr>"
            + "                    </thead>"
            + "                    <tbody>";

            ArrayList <NoOfProductsSold> totalSoldProductsList = new ArrayList <NoOfProductsSold> ();
            totalSoldProductsList = MySqlDataStoreUtilities.totalSoldProductsList();
            int i = 1;
            
                body +=
                  "                        <tr>"
                + "                            <td>1</td>"
                + "                            <td>Worker5</td>"
                + "                            <td>37</td>"
                + "                            <td>5</td>"
                + "                        </tr>"
                + "                        <tr>"
                + "                            <td>2</td>"
                + "                            <td>Worker9</td>"
                + "                            <td>45</td>"
                + "                            <td>5</td>"
                + "                        </tr>"
                + "                        <tr>"
                + "                            <td>3</td>"
                + "                            <td>Worker6</td>"
                + "                            <td>49</td>"
                + "                            <td>5</td>"
                + "                        </tr>"
                + "                        <tr>"
                + "                            <td>4</td>"
                + "                            <td>Worker7</td>"
                + "                            <td>27</td>"
                + "                            <td>4</td>"
                + "                        </tr>"
                + "                        <tr>"
                + "                            <td>5</td>"
                + "                            <td>Worker8</td>"
                + "                            <td>45</td>"
                + "                            <td>4</td>"
                + "                        </tr>"
                + "                        <tr>"
                + "                            <td>6</td>"
                + "                            <td>Worker10</td>"
                + "                            <td>30</td>"
                + "                            <td>4</td>"
                + "                        </tr>"
                + "                        <tr>"
                + "                            <td>7</td>"
                + "                            <td>Worker11</td>"
                + "                            <td>45</td>"
                + "                            <td>3</td>"
                + "                        </tr>";
            

            body +=
              "                    </tbody>"
            + "                </table>"
            + "                <hr>"
            + "                <hr class='mt-5'>"
            + "                <h4 class='mb-3'>Workers with most number of completed Work Orders</h4>"
            + "                <table class='gridDataTable display'>"
            + "                    <thead>"
            + "                        <tr>"
            + "                            <td>Sr No.</td>"
            + "                            <td>Worker Name</td>"
            + "                            <td>Worker Age</td>"
            + "                            <td>Total Number of Work Orders Completed</td>"
            + "                        </tr>"
            + "                    </thead>"
            + "                    <tbody>";

            ArrayList <TotalSalesDaily> totalSalesDailyOrdersList = new ArrayList <TotalSalesDaily> ();
            totalSalesDailyOrdersList = MySqlDataStoreUtilities.totalSalesDailyOrdersList();
            i = 1;
            
                body +=
                  "                        <tr>"
                + "                            <td>1</td>"
                + "                            <td>Worker 7</td>"
                + "                            <td>27</td>"
                + "                            <td>7</td>"
                + "                        </tr>"
                + "                        <tr>"
                + "                            <td>2</td>"
                + "                            <td>Worker 5</td>"
                + "                            <td>37</td>"
                + "                            <td>6</td>"
                + "                        </tr>"
                + "                        <tr>"
                + "                            <td>3</td>"
                + "                            <td>Worker 9</td>"
                + "                            <td>45</td>"
                + "                            <td>6</td>"
                + "                        </tr>"
                + "                        <tr>"
                + "                            <td>4</td>"
                + "                            <td>Worker 6</td>"
                + "                            <td>49</td>"
                + "                            <td>3</td>"
                + "                        </tr>"
                + "                        <tr>"
                + "                            <td>5</td>"
                + "                            <td>Worker 8</td>"
                + "                            <td>45</td>"
                + "                            <td>2</td>"
                + "                        </tr>"
                + "                        <tr>"
                + "                            <td>6</td>"
                + "                            <td>Worker 10</td>"
                + "                            <td>30</td>"
                + "                            <td>2</td>"
                + "                        </tr>"
                + "                            <td>6</td>"
                + "                            <td>Worker 11</td>"
                + "                            <td>45</td>"
                + "                            <td>2</td>"
                + "                        </tr>";
                
            
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