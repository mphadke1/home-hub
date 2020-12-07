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

            String body =
              "        <div class='container mt-3 bg-light'>"
            + "            <div class='col-12 col-sm-9'>"
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
            // switch(type) {
            //     case "transactions":
            //         dataList = MySqlDataStoreUtilities.getHeatMapDataForTransactions();
            //         jsonResponse = new Gson().toJson(dataList);
            //         break;
            //     case "pickups":
            //         dataList = MySqlDataStoreUtilities.getHeatMapDataForPickups();
            //         jsonResponse = new Gson().toJson(dataList);
            //         break;
            //     case "reviews":
            //         dataList = Utilities.getHeatMapDataForTotalReviews();
            //         jsonResponse = new Gson().toJson(dataList);
            //         break;
            //     case "likedProducts":
            //         dataList = Utilities.getHeatMapDataForLikedProducts();
            //         jsonResponse = new Gson().toJson(dataList);
            //         break;
            //     case "dislikedProducts":
            //         dataList = Utilities.getHeatMapDataForDislikedProducts();
            //         jsonResponse = new Gson().toJson(dataList);
            //         break;
            //     case "default":
            //         jsonResponse = new Gson().toJson(new ArrayList<>());
            //         break;
            // }

            Random rn = new Random();

            int weight = 0;
            weight = rn.nextInt(50);
            dataList.add(new HeatMapData(
                "Glenview",
                "1851 Tower Drive",
                "Glenview",
                "IL",
                60026,
                weight
            ));
            
            weight = rn.nextInt(50);            
            dataList.add(new HeatMapData(
                "Niles",
                "5681 West Touhy Avenue",
                "Niles",
                "IL",
                60714,
                weight
            ));
            weight = rn.nextInt(50);            
            dataList.add(new HeatMapData(
                "Evanston",
                "930 Church Street",
                "Evanston",
                "IL",
                60201,
                weight
            ));
            weight = rn.nextInt(50);            
            dataList.add(new HeatMapData(
                "Arlington Heights",
                "66 S. Arlington Heights Road",
                "Arlington Heights",
                "IL",
                60005,
                weight
            ));
            weight = rn.nextInt(50);            
            dataList.add(new HeatMapData(
                "Lincolnshire",
                "900 Milwaukee Avenue Suite A",
                "Lincolnshire",
                "IL",
                60069,
                weight
            ));
            weight = rn.nextInt(50);            
            dataList.add(new HeatMapData(
                "La Grange",
                "1 East Burlington Avenue",
                "LaGrange",
                "IL",
                60525,
                weight
            ));
            weight = rn.nextInt(50);            
            dataList.add(new HeatMapData(
                "Park Ridge",
                "510 W. Touhy",
                "Parkridge",
                "IL",
                60068,
                weight
            ));
            weight = rn.nextInt(50);            
            dataList.add(new HeatMapData(
                "Naperville",
                "207 South Washington",
                "Naperville",
                "IL",
                60540,
                weight
            ));
            weight = rn.nextInt(50);            
            dataList.add(new HeatMapData(
                "Geneva",
                "1310 Commons Drive",
                "Geneva",
                "IL",
                60134,
                weight
            ));
            weight = rn.nextInt(50);            
            dataList.add(new HeatMapData(
                "Bolingbrook",
                "137 N. Weber Road",
                "Bolingbrook",
                "IL",
                60440,
                weight
            ));
            weight = rn.nextInt(50);            
            dataList.add(new HeatMapData(
                "Chicago",
                "401 East 32nd Street",
                "Chicago",
                "IL",
                60440,
                weight * 10
            ));

            jsonResponse = new Gson().toJson(dataList);

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