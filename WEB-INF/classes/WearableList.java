import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/WearableList")

public class WearableList extends HttpServlet {

	/* List Page Displays all the Wearable products and their Information */

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		String name = null;
		String CategoryName = request.getParameter("maker");
        
		HashMap<String, Wearable> hm = new HashMap<String, Wearable>();

		if(CategoryName == null) {
			hm.putAll(SaxParserDataStore.wearables);
			name = "";
		} else {
            for(Map.Entry<String, Wearable> entry : SaxParserDataStore.wearables.entrySet()) {
                if(entry.getValue().getRetailer().equalsIgnoreCase(CategoryName.toLowerCase())) {
                    hm.put(entry.getValue().getId(), entry.getValue());   
                }
            }
            name = capitalize(CategoryName) + " ";
        }
		
		/* Header, Left Navigation Bar are Printed.

		 List of product is displayed

		and then Footer is Printed*/

		Utilities utility = new Utilities(request,pw);
		utility.printHtml("Header.html");
		utility.printNavbar();
		utility.printHtml("LeftNavigationBar.html");

		String body = 
		  "	  <div class='col-12 col-sm-9'>"
		+ "            <h2 class='m-3'>" + name + "Wearable Catalog</h2>"
		+ "            <div class='row row-cols-2'>";

		for(Map.Entry<String, Wearable> entry : hm.entrySet()) {
			Wearable product = entry.getValue();
			body +=
			  "                <div class='col-6 my-2'>"
			+ "                    <div class='card p-3'>"
			+ "                        <img id='card-img' src='images/" + product.getImage() + "' class='card-img-top'>"
			+ "                        <div class='card-body'>"
			+ "                            <h5 class='card-title'>" + product.getName() + "</h5>"
			+ "                            <p class='card-text'>$ " + product.getPrice() + "</p>"
			+ "							   <form class='float-right' method='POST' action='Cart'>"
			+ "                                <input type='hidden' name='name' value='" + entry.getKey() + "'>"
			+ "                                <input type='hidden' name='type' value='wearables'>"
			+ "                                <input type='hidden' name='maker' value='" + CategoryName + "'>"
			+ "                                <input type='hidden' name='access' value=''>"
			+ "                                <button id='buy-button' type='submit' class='btn btn-sm text-light'>Buy Now</button>"
			+ "                            </form>"
			+ "                        </div>"
			+ "                    </div>"
			+ "                </div>";
		}
		
		body +=
		  "            </div>"
		+ "        </div>"
		+ "    </div>"
		+ "</div>";

		pw.print(body);
		utility.printHtml("Footer.html");
	}

    String capitalize(String str) {
        str = str.toLowerCase();
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
