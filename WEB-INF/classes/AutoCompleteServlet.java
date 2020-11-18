import java.io.*;
import java.util.*;
import java.text.*;
import java.io.IOException;
import com.google.gson.Gson;
import javax.servlet.http.*;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/autocomplete")

public class AutoCompleteServlet extends HttpServlet {

    private ServletContext context;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.context = config.getServletContext();
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
	{
        System.out.println("HERE");
		try {
            String searchId = request.getParameter("searchId");

			if(searchId != null && !searchId.equals("")) {
                searchId = searchId.trim().toLowerCase();

                ArrayList<Product> productList = MySqlDataStoreUtilities.getProductsByNameOrType(searchId);
                String jsonResponse = new Gson().toJson(productList);

                response.setContentType("application/JSON");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(jsonResponse);
			} else {
				context.getRequestDispatcher("/Error").forward(request, response);
            }
		} catch(Exception e) {
            e.printStackTrace();
		}
    }
}