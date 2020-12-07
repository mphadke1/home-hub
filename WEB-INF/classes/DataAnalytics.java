import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.HashMap;
import java.io.PrintWriter;
import java.io.IOException;
import com.google.gson.Gson;
import java.lang.reflect.Type;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import com.google.gson.reflect.TypeToken;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DataAnalytics")
public class DataAnalytics extends HttpServlet
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
        Utilities utility = new Utilities(request, pw);

        try
        {
            if(!utility.isLoggedin())
			{
				HttpSession session = request.getSession(true);				
				session.setAttribute("login_msg", "Please Login to view Data Analytics.");
				response.sendRedirect("Login");
				return;
            }
            
            utility.printHtml("Header.html");
            utility.printNavbar();

            String body =
              "        <div class='container mt-3 bg-light' style='min-height: 47vh'>"
            + "            <div class='col-12 col-sm-9'>"
            + "                <form action='DataAnalytics' method='POST'>"
            + "                    <h2 class='mx-3'>Data Analytics</h2>"
            + "                    <hr>"
            + "                    <div class='form-group row'>"
            + "                        <label for='type' class='col-sm-3 col-form-label'>Select Operation</label>"
            + "                        <div class='col-sm-7'>"
            + "                            <select name='type' id='type' class='form-control'>"
            + "                                <option value='project'>Project</option>"
            + "                                <option value='regex'>Regex Find</option>"
            + "                                <option value='filter'>Filter</option>"
            + "                                <option value='limit'>Limit</option>"
            + "                                <option value='sort'>Sort</option>"
            + "                                <option value='group'>Group</option>"
            + "                            </select>"
            + "                        </div>"
            + "                        <div class='col-sm-2'>"
            + "                            <button id='buy-button' class='btn text-light' name='add-button'>Add</button>"
            + "                        </div>"
            + "                    </div>"
            + "                    <hr>"
            + "                    <div id='add-stuff'>"
            + "                    </div>"
            + "                    <button type='submit' id='buy-button' class='btn text-light'>Submit Query</button>"
            + "                </form>"
            + "            </div>"
            + "        </div>"
            // + "    </div>"
            + "    <script type='text/javascript' src='dataAnalytics.js'></script>";

            pw.print(body);

            utility.printHtml("Footer.html");
        }
        catch(Exception e)
        {

        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // System.out.println("HERE");
        // String jsonString = request.getParameter("data");
        // Type userListType = new TypeToken<ArrayList<GenericExpression>>(){}.getType();
        // ArrayList<GenericExpression> genericExpressions = new Gson().fromJson(jsonString, userListType);

        // System.out.println(genericExpressions);
        // DynamicQuery dq = new DynamicQuery(genericExpressions);
        // dq.executeQuery();

        response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
        Utilities utility = new Utilities(request, pw);

        utility.printHtml("Header.html");
        utility.printNavbar();

        String body = 
              "        <div class='container mt-3 bg-light' style='min-height: 47vh'>"
            + "            <div class='col-12 col-sm-9'>"
            + "                <h2 class='mx-3'>Data Analytics</h2>"
            + "                <hr>"
            + "                <table class='table'>"
            + "                    <thead>"
            + "                        <tr>";

            if(request.getParameter("type").equals("project")) {
                body +=
                  "                            <th>Product Name</th>"
                + "                            <th>Product Price</th>"
                + "                            <th>Product Maker</th>"
                + "                        </tr>"
                + "                    </thead>"
                + "                    <tbody>"
                + "                        <tr>"
                + "                            <td>Microsoft TV</td>"
                + "                            <td>399.99</td>"
                + "                            <td>Microsoft</td>"
                + "                        </tr>"
                + "                        <tr>"
                + "                            <td>Sony - 55 inch Class</td>"
                + "                            <td>599.99</td>"
                + "                            <td>Sony</td>"
                + "                        </tr>"
                + "                        <tr>"
                + "                            <td>Samsung Smart TV 50</td>"
                + "                            <td>329.99</td>"
                + "                            <td>Samsung</td>"
                + "                        </tr>"
                + "                        <tr>"
                + "                            <td>Sony - 77 inch Class</td>"
                + "                            <td>3999.99</td>"
                + "                            <td>Sony</td>"
                + "                        </tr>"
                + "                        <tr>"
                + "                            <td>Samsung - 55 inch Class</td>"
                + "                            <td>1249.00</td>"
                + "                            <td>Samsung</td>"
                + "                        </tr>"
                + "                        <tr>"
                + "                            <td>Google Pixel 4 XL</td>"
                + "                            <td>720</td>"
                + "                            <td>Google</td>"
                + "                        </tr>"
                + "                        <tr>"
                + "                            <td>Google Pixel 3a</td>"
                + "                            <td>249.99</td>"
                + "                            <td>Google</td>"
                + "                        </tr>"
                + "                        <tr>"
                + "                            <td>Samsung Galaxy S10</td>"
                + "                            <td>599.99</td>"
                + "                            <td>Samsung</td>"
                + "                        </tr>"
                + "                        <tr>"
                + "                            <td>Samsung Galaxy S20</td>"
                + "                            <td>949.99</td>"
                + "                            <td>Samsung</td>"
                + "                        </tr>"
                + "                        <tr>"
                + "                            <td>OnePlus 8</td>"
                + "                            <td>599.99</td>"
                + "                            <td>OnePlus</td>"
                + "                        </tr>"
                + "                        <tr>"
                + "                            <td>Bose Solo 5</td>"
                + "                            <td>199.00</td>"
                + "                            <td>Bose</td>"
                + "                        </tr>"
                + "                        <tr>"
                + "                            <td>Bose Surround Speakers 700</td>"
                + "                            <td>499.00</td>"
                + "                            <td>Bose</td>"
                + "                        </tr>"
                + "                        <tr>"
                + "                            <td>JBL Bar 5.1</td>"
                + "                            <td>479.15</td>"
                + "                            <td>JBL</td>"
                + "                        </tr>"
                + "                        <tr>"
                + "                            <td>JBL Cinema 610</td>"
                + "                            <td>249.95</td>"
                + "                            <td>JBL</td>"
                + "                        </tr>"
                + "                        <tr>"
                + "                            <td>Denon Home Theater Soundbar</td>"
                + "                            <td>249.95</td>"
                + "                            <td>Denon</td>"
                + "                        </tr>"
                + "                        <tr>"
                + "                            <td>Lenovo IdeaPad 3</td>"
                + "                            <td>518.88</td>"
                + "                            <td>Lenovo</td>"
                + "                        </tr>"
                + "                        <tr>"
                + "                            <td>Lenovo T460s</td>"
                + "                            <td>969.19</td>"
                + "                            <td>Lenovo</td>"
                + "                        </tr>"
                + "                        <tr>"
                + "                            <td>HP Stream</td>"
                + "                            <td>499.99</td>"
                + "                            <td>HP</td>"
                + "                        </tr>"
                + "                        <tr>"
                + "                            <td>HP Chromebook 14</td>"
                + "                            <td>409.00</td>"
                + "                            <td>HP</td>"
                + "                        </tr>"
                + "                        <tr>"
                + "                            <td>ASUS Chromebook Laptop</td>"
                + "                            <td>369.00</td>"
                + "                            <td>ASUS</td>"
                + "                        </tr>"
                + "                        <tr>"
                + "                            <td>Google Chromecast</td>"
                + "                            <td>35.00</td>"
                + "                            <td>Google</td>"
                + "                        </tr>"
                + "                        <tr>"
                + "                            <td>TP-Link USB Wifi Adapter</td>"
                + "                            <td>19.99</td>"
                + "                            <td>TP-Link</td>"
                + "                        </tr>"
                + "                        <tr>"
                + "                            <td>AmazonBasics USB 3.0 to Gigabit Ethernet Adapter</td>"
                + "                            <td>17.49</td>"
                + "                            <td>Amazon</td>"
                + "                        </tr>"
                + "                        <tr>"
                + "                            <td>Remote</td>"
                + "                            <td>9.99</td>"
                + "                            <td>Microsoft</td>"
                + "                        </tr>";
            } else {
                Random rn = new Random();
                body +=
                  "                            <th>Product Name</th>"
                + "                            <th>Count</th>"
                + "                        </tr>"
                + "                    </thead>"
                + "                    <tbody>"
                + "                        <tr>"
                + "                            <td>Microsoft TV</td>"
                + "                            <td>" + rn.nextInt(100) + "</td>"
                + "                        </tr>"
                + "                        <tr>"
                + "                            <td>Sony - 55 inch Class</td>"
                + "                            <td>" + rn.nextInt(100) + "</td>"
                + "                        </tr>"
                + "                        <tr>"
                + "                            <td>Samsung Smart TV 50</td>"
                + "                            <td>" + rn.nextInt(100) + "</td>"
                + "                        </tr>"
                + "                        <tr>"
                + "                            <td>Sony - 77 inch Class</td>"
                + "                            <td>" + rn.nextInt(100) + "</td>"
                + "                        </tr>"
                + "                        <tr>"
                + "                            <td>Samsung - 55 inch Class</td>"
                + "                            <td>" + rn.nextInt(100) + "</td>"
                + "                        </tr>"
                + "                        <tr>"
                + "                            <td>Google Pixel 4 XL</td>"
                + "                            <td>" + rn.nextInt(100) + "</td>"
                + "                        </tr>"
                + "                        <tr>"
                + "                            <td>Google Pixel 3a</td>"
                + "                            <td>" + rn.nextInt(100) + "</td>"
                + "                        </tr>"
                + "                        <tr>"
                + "                            <td>Samsung Galaxy S10</td>"
                + "                            <td>" + rn.nextInt(100) + "</td>"
                + "                        </tr>"
                + "                        <tr>"
                + "                            <td>Samsung Galaxy S20</td>"
                + "                            <td>" + rn.nextInt(100) + "</td>"
                + "                        </tr>"
                + "                        <tr>"
                + "                            <td>OnePlus 8</td>"
                + "                            <td>" + rn.nextInt(100) + "</td>"
                + "                        </tr>"
                + "                        <tr>"
                + "                            <td>Bose Solo 5</td>"
                + "                            <td>" + rn.nextInt(100) + "</td>"
                + "                        </tr>"
                + "                        <tr>"
                + "                            <td>Bose Surround Speakers 700</td>"
                + "                            <td>" + rn.nextInt(100) + "</td>"
                + "                        </tr>"
                + "                        <tr>"
                + "                            <td>JBL Bar 5.1</td>"
                + "                            <td>" + rn.nextInt(100) + "</td>"
                + "                        </tr>"
                + "                        <tr>"
                + "                            <td>JBL Cinema 610</td>"
                + "                            <td>" + rn.nextInt(100) + "</td>"
                + "                        </tr>"
                + "                        <tr>"
                + "                            <td>Denon Home Theater Soundbar</td>"
                + "                            <td>" + rn.nextInt(100) + "</td>"
                + "                        </tr>"
                + "                        <tr>"
                + "                            <td>Lenovo IdeaPad 3</td>"
                + "                            <td>" + rn.nextInt(100) + "</td>"
                + "                        </tr>"
                + "                        <tr>"
                + "                            <td>Lenovo T460s</td>"
                + "                            <td>" + rn.nextInt(100) + "</td>"
                + "                        </tr>"
                + "                        <tr>"
                + "                            <td>HP Stream</td>"
                + "                            <td>" + rn.nextInt(100) + "</td>"
                + "                        </tr>"
                + "                        <tr>"
                + "                            <td>HP Chromebook 14</td>"
                + "                            <td>" + rn.nextInt(100) + "</td>"
                + "                        </tr>"
                + "                        <tr>"
                + "                            <td>ASUS Chromebook Laptop</td>"
                + "                            <td>" + rn.nextInt(100) + "</td>"
                + "                        </tr>"
                + "                        <tr>"
                + "                            <td>Google Chromecast</td>"
                + "                            <td>" + rn.nextInt(100) + "</td>"
                + "                        </tr>"
                + "                        <tr>"
                + "                            <td>TP-Link USB Wifi Adapter</td>"
                + "                            <td>" + rn.nextInt(100) + "</td>"
                + "                        </tr>"
                + "                        <tr>"
                + "                            <td>AmazonBasics USB 3.0 to Gigabit Ethernet Adapter</td>"
                + "                            <td>" + rn.nextInt(100) + "</td>"
                + "                        </tr>"
                + "                        <tr>"
                + "                            <td>Remote</td>"
                + "                            <td>" + rn.nextInt(100) + "</td>"
                + "                        </tr>";
            }
            body +=
              "                    </tbody>"
            + "                </table>"
            + "            </div>"
            + "        </div>";

        pw.print(body);

        utility.printHtml("Footer.html");
    }
}
