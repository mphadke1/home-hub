import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/VoiceAssistantList")

public class VoiceAssistantList extends HttpServlet {

	/* List Page Displays all the VoiceAssistant products and their Information */

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		String name = null;
		String CategoryName = request.getParameter("maker");
        
		HashMap<String, VoiceAssistant> allVoiceAssistants = new HashMap<String, VoiceAssistant>();
		HashMap<String, VoiceAssistant> hm = new HashMap<String, VoiceAssistant>();

		try
		{
			allVoiceAssistants = MySqlDataStoreUtilities.getVoiceAssistants();
		}
		catch(Exception e)
		{

		}

		if(CategoryName == null) {
			hm.putAll(allVoiceAssistants);
			name = "";
		} else {
            for(Map.Entry<String, VoiceAssistant> entry : allVoiceAssistants.entrySet()) {
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
		+ "            <h2 class='m-3'>" + name + "Voice Assistant Catalog</h2>"
		+ "            <div class='row row-cols-2'>";

		for(Map.Entry<String, VoiceAssistant> entry : hm.entrySet()) {
			VoiceAssistant product = entry.getValue();
			body +=
			  "                <div class='col-6 my-2'>"
			+ "                    <div class='card p-3'>"
			+ "                        <img id='card-img' src='images/" + product.getImage() + "' class='card-img-top'>"
			+ "                        <div class='card-body'>"
			+ "                            <h5 class='card-title'>" + product.getName() + "</h5>"
			+ "                            <p class='card-text'>$ " + product.getPrice() + "</p>"
			+ "							   <form class='float-right' method='POST' action='Cart'>"
			+ "                                <input type='hidden' name='name' value='" + entry.getKey() + "'>"
			+ "                                <input type='hidden' name='type' value='voiceAssistants'>"
			+ "                                <input type='hidden' name='maker' value='" + product.getRetailer() + "'>"
			+ "                                <input type='hidden' name='access' value=''>"
			+ "                                <button id='buy-button' type='submit' class='btn btn-sm text-light'>Buy Now</button>"
			+ "                            </form>"
			+ "							   <form method='POST' action='WriteReview'>"
			+ "                                <input type='hidden' name='name' value='" + product.getName() + "'>"
			+ "                                <input type='hidden' name='type' value='voiceAssistant'>"
			+ "                                <input type='hidden' name='maker' value='" + product.getRetailer() + "'>"
			+ "                                <input type='hidden' name='price' value='" + product.getPrice() + "'>"
			+ "                                <input type='hidden' name='access' value=''>"
			+ "                                <button id='buy-button' type='submit' class='btn btn-sm text-light'>Write Review</button>"
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
