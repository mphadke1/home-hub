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
        //displayInventory(request, response);
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
            utility.printHtml("LeftNavigationBar.html");

            String body =
              "            <div class='col-12 col-sm-9'>"
            + "                <h2 class='mx-3'>Data Analytics</h2>"
            + "                <hr>"
            + "                <div class='form-group row'>"
            + "                    <label for='type' class='col-sm-3 col-form-label'>Select Operation</label>"
            + "                    <div class='col-sm-7'>"
            + "                        <select name='type' id='type' class='form-control'>"
            + "                            <option value='project'>Project</option>"
            + "                            <option value='regex'>Regex Find</option>"
            + "                            <option value='filter'>Filter</option>"
            + "                            <option value='limit'>Limit</option>"
            + "                            <option value='sort'>Sort</option>"
            + "                            <option value='group'>Group</option>"
            + "                        </select>"
            + "                    </div>"
            + "                    <div class='col-sm-2'>"
            + "                        <button id='buy-button' class='btn text-light' name='add-button'>Add</button>"
            + "                    </div>"
            + "                </div>"
            + "                <hr>"
            + "                <form>"
            + "                </form>"
            + "                <button type='submit' id='buy-button' class='btn text-light'>Submit Query</button>"
            + "            </div>"
            + "        </div>"
            + "    </div>"
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
        System.out.println("HERE");
        String jsonString = request.getParameter("data");
        Type userListType = new TypeToken<ArrayList<GenericExpression>>(){}.getType();
        ArrayList<GenericExpression> genericExpressions = new Gson().fromJson(jsonString, userListType);

        System.out.println(genericExpressions);
        DynamicQuery dq = new DynamicQuery(genericExpressions);
        dq.executeQuery();
        

        // try {
        //     String jsonResponse = "";

        //     response.setContentType("application/JSON");
        //     response.setCharacterEncoding("UTF-8");
        //     response.getWriter().write(jsonResponse);
        // }
        // catch (Exception ex) {
        //     System.out.println(ex.getMessage());
        //     ex.printStackTrace();
        // }
    }
}
