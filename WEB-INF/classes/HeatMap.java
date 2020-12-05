import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.HashMap;
import java.io.PrintWriter;
import java.io.IOException;
import com.google.gson.Gson;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/HeatMap")
public class HeatMap extends HttpServlet
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
				session.setAttribute("login_msg", "Please Login to view Heat Map.");
				response.sendRedirect("Login");
				return;
            }
            
            utility.printHtml("Header.html");
            utility.printNavbar();
            utility.printHtml("LeftNavigationBar.html");

            String body =
              "            <div class='col-12 col-sm-9'>"
            + "                <h2 class='mx-3'>Heat Map</h2>"
            + "                <hr>"
            + "                <form>"
            + "                    <div class='form-group row'>"
            + "                        <label for='type' class='col-sm-3 col-form-label'>Select HeatMap Type</label>"
            + "                        <div class='col-sm-7'>"
            + "                            <select name='type' id='type' class='form-control'>"
            + "                                <option value='reviews'>Number of Reviews</option>"
            + "                                <option value='transactions'>Number of Transactions</option>"
            + "                                <option value='likedProducts'>Liked Products (Rating >= 3)</option>"
            + "                                <option value='dislikedProducts'>Disliked Products (Rating < 3)</option>"
            + "                                <option value='pickups'>Number of In-Store Pickups</option>"
            + "                            </select>"
            + "                        </div>"
            + "                        <div class='col-sm-2'>"
            + "                            <button id='buy-button' class='btn text-light'>Submit</button>"
            + "                        </div>"
            + "                    </div>"
            + "                </form>"
            + "                <hr>"
            + "                <div id='map' style='height: 80vh'></div>"
            + "            </div>"
            + "        </div>"
            + "    </div>"
            + "    <script defer src=\"https://maps.googleapis.com/maps/api/js?key=AIzaSyCW5bEaU5FDZwF2OvZIGzRwICVbbpCQRPE&callback=initMap&libraries=visualization&callback=initMap\"></script>"
            + "    <script type='text/javascript' src='heatMap.js'></script>";

            pw.print(body);

            utility.printHtml("Footer.html");
        }
        catch(Exception e)
        {

        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

        HashMap hm = new HashMap();
        hm.putAll(request.getParameterMap());
        System.out.println(hm);

        try {
            String type = request.getParameter("type");

            System.out.println("Type = ");
            System.out.println(type);

            String jsonResponse = "";
            ArrayList<HeatMapData> dataList = new ArrayList <HeatMapData>();
            switch(type) {
                case "transactions":
                    dataList = MySqlDataStoreUtilities.getHeatMapDataForTransactions();
                    jsonResponse = new Gson().toJson(dataList);
                    break;
                case "pickups":
                    dataList = MySqlDataStoreUtilities.getHeatMapDataForPickups();
                    jsonResponse = new Gson().toJson(dataList);
                    break;
                case "reviews":
                    dataList = Utilities.getHeatMapDataForTotalReviews();
                    jsonResponse = new Gson().toJson(dataList);
                    break;
                case "likedProducts":
                    dataList = Utilities.getHeatMapDataForLikedProducts();
                    jsonResponse = new Gson().toJson(dataList);
                    break;
                case "dislikedProducts":
                    dataList = Utilities.getHeatMapDataForDislikedProducts();
                    jsonResponse = new Gson().toJson(dataList);
                    break;
                case "default":
                    jsonResponse = new Gson().toJson(new ArrayList<>());
                    break;
            }

            response.setContentType("application/JSON");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonResponse);
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
}