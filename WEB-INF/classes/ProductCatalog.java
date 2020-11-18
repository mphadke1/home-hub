import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

import java.sql.*;

@WebServlet("/ProductCatalog")

public class ProductCatalog extends HttpServlet {

    private String success_msg = "";
    private String error_msg = "";
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
        Utilities utility = new Utilities(request, pw);
        
        String productId = request.getParameter("productId");
        String productType = request.getParameter("productType");

        Boolean success = false;
        switch(productType.toLowerCase()) {
            case "tv":
                TV ret = SaxParserDataStore.tvs.remove(productId);
                if(ret != null) {
                    success = true;
                }
                break;
        }
        if(success) {
            success_msg = "Product deleted successfully";
        } else {
            error_msg = "There was an error deleting the product";
        }

        displayProductCatalog(request, response, success, !success);
	}
	
    private void displayProductCatalog(HttpServletRequest request, HttpServletResponse response, Boolean success, Boolean error)
        throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        Utilities utility = new Utilities(request, pw);

        if(!utility.isLoggedin()) {
			HttpSession session = request.getSession(true);				
			session.setAttribute("login_msg", "Please Login to view product catalog");
			response.sendRedirect("Login");
			return;
		}

        utility.printHtml("Header.html");
        utility.printNavbar();

        String body =
          "    <div class='container mt-3 bg-light'>"
        + "        <div class='row row-cols-2 pb-3'>"
        + "            <div class='col-12'>"
        + "                <h2 class='m-3'>Product Catalog</h2>"
        + "                <hr>";

        if(success) {
            body +=
              "                <div class='alert alert-info'>" + success_msg + "</div>";
        }
        if(error) {
            body +=
              "                <div class='alert alert-danger'>" + error_msg + "</div>";
        }

        body +=
          "                <a href='AddProduct' id='buy-button' class='mb-3 btn text-light'><span class='fa fa-plus'></span> Add Product</a>"
        + "                <div>"
        + "                    <table class='table'>"
        + "                        <thead>"
        + "                            <tr>"
        + "                                <th>Product Name</th>"
        + "                                <th>Product Price</th>"
        + "                                <th>Image</th>"
        + "                                <th>Manufacturer</th>"
        + "                                <th>Condition</th>"
        + "                                <th>Discount</th>"
        + "                                <th>Action</th>"
        + "                            </tr>"
        + "                        </thead>"
        + "                        <tbody>";
        

        for(Map.Entry<String, TV> entry : MySqlDataStoreUtilities.getTVs().entrySet()) {
            TV tv = entry.getValue();

            body +=
              "                            <tr>"
            + "                                <td>" + tv.getName() + "</td>"
            + "                                <td>$ " + tv.getPrice() + "</td>"
            + "                                <td><a href='images/" + tv.getImage() + "'>Link</a></td>"
            + "                                <td>" + tv.getRetailer() + "</td>"
            + "                                <td>" + tv.getCondition() + "</td>"
            + "                                <td>" + tv.getDiscount() + "</td>"
            + "                                <td>"
            + "                                    <form action='ProductCatalog' method='POST'>"
            + "                                        <input type='hidden' name='productId' value='" + tv.getId() + "'>"
            + "                                        <input type='hidden' name='productType' value='tv'>"
            + "                                        <button id='buy-button' class='btn btn-sm text-light'>Delete Product</button>"
            + "                                    </form>"
            + "                                </td>"
            + "                            </tr>";
        }

        for(Map.Entry<String, SoundSystem> entry : MySqlDataStoreUtilities.getSoundSystems().entrySet()) {
            SoundSystem soundSystem = entry.getValue();

            body +=
              "                            <tr>"
            + "                                <td>" + soundSystem.getName() + "</td>"
            + "                                <td>$ " + soundSystem.getPrice() + "</td>"
            + "                                <td><a href='images/" + soundSystem.getImage() + "'>Link</a></td>"
            + "                                <td>" + soundSystem.getRetailer() + "</td>"
            + "                                <td>" + soundSystem.getCondition() + "</td>"
            + "                                <td>" + soundSystem.getDiscount() + "</td>"
            + "                                <td>"
            + "                                    <form action='ProductCatalog' method='POST'>"
            + "                                        <input type='hidden' name='productId' value='" + soundSystem.getId() + "'>"
            + "                                        <input type='hidden' name='productType' value='soundSystem'>"
            + "                                        <button id='buy-button' class='btn btn-sm text-light'>Delete Product</button>"
            + "                                    </form>"
            + "                                </td>"
            + "                            </tr>";
        }

        for(Map.Entry<String, Phone> entry : MySqlDataStoreUtilities.getPhones().entrySet()) {
            Phone phone = entry.getValue();

            body +=
              "                            <tr>"
            + "                                <td>" + phone.getName() + "</td>"
            + "                                <td>$ " + phone.getPrice() + "</td>"
            + "                                <td><a href='images/" + phone.getImage() + "'>Link</a></td>"
            + "                                <td>" + phone.getRetailer() + "</td>"
            + "                                <td>" + phone.getCondition() + "</td>"
            + "                                <td>" + phone.getDiscount() + "</td>"
            + "                                <td>"
            + "                                    <form action='ProductCatalog' method='POST'>"
            + "                                        <input type='hidden' name='productId' value='" + phone.getId() + "'>"
            + "                                        <input type='hidden' name='productType' value='phone'>"
            + "                                        <button id='buy-button' class='btn btn-sm text-light'>Delete Product</button>"
            + "                                    </form>"
            + "                                </td>"
            + "                            </tr>";
        }

        for(Map.Entry<String, Laptop> entry : MySqlDataStoreUtilities.getLaptops().entrySet()) {
            Laptop laptop = entry.getValue();

            body +=
              "                            <tr>"
            + "                                <td>" + laptop.getName() + "</td>"
            + "                                <td>$ " + laptop.getPrice() + "</td>"
            + "                                <td><a href='images/" + laptop.getImage() + "'>Link</a></td>"
            + "                                <td>" + laptop.getRetailer() + "</td>"
            + "                                <td>" + laptop.getCondition() + "</td>"
            + "                                <td>" + laptop.getDiscount() + "</td>"
            + "                                <td>"
            + "                                    <form action='ProductCatalog' method='POST'>"
            + "                                        <input type='hidden' name='productId' value='" + laptop.getId() + "'>"
            + "                                        <input type='hidden' name='productType' value='laptop'>"
            + "                                        <button id='buy-button' class='btn btn-sm text-light'>Delete Product</button>"
            + "                                    </form>"
            + "                                </td>"
            + "                            </tr>";
        }

        for(Map.Entry<String, VoiceAssistant> entry : MySqlDataStoreUtilities.getVoiceAssistants().entrySet()) {
            VoiceAssistant voiceAssistant = entry.getValue();

            body +=
              "                            <tr>"
            + "                                <td>" + voiceAssistant.getName() + "</td>"
            + "                                <td>$ " + voiceAssistant.getPrice() + "</td>"
            + "                                <td><a href='images/" + voiceAssistant.getImage() + "'>Link</a></td>"
            + "                                <td>" + voiceAssistant.getRetailer() + "</td>"
            + "                                <td>" + voiceAssistant.getCondition() + "</td>"
            + "                                <td>" + voiceAssistant.getDiscount() + "</td>"
            + "                                <td>"
            + "                                    <form action='ProductCatalog' method='POST'>"
            + "                                        <input type='hidden' name='productId' value='" + voiceAssistant.getId() + "'>"
            + "                                        <input type='hidden' name='productType' value='voiceAssistant'>"
            + "                                        <button id='buy-button' class='btn btn-sm text-light'>Delete Product</button>"
            + "                                    </form>"
            + "                                </td>"
            + "                            </tr>";
        }

        for(Map.Entry<String, Wearable> entry : MySqlDataStoreUtilities.getWearables().entrySet()) {
            Wearable wearable = entry.getValue();

            body +=
              "                            <tr>"
            + "                                <td>" + wearable.getName() + "</td>"
            + "                                <td>$ " + wearable.getPrice() + "</td>"
            + "                                <td><a href='images/" + wearable.getImage() + "'>Link</a></td>"
            + "                                <td>" + wearable.getRetailer() + "</td>"
            + "                                <td>" + wearable.getCondition() + "</td>"
            + "                                <td>" + wearable.getDiscount() + "</td>"
            + "                                <td>"
            + "                                    <form action='ProductCatalog' method='POST'>"
            + "                                        <input type='hidden' name='productId' value='" + wearable.getId() + "'>"
            + "                                        <input type='hidden' name='productType' value='wearable'>"
            + "                                        <button id='buy-button' class='btn btn-sm text-light'>Delete Product</button>"
            + "                                    </form>"
            + "                                </td>"
            + "                            </tr>";
        }

        body +=
          "                        </tbody>"
        + "                    </table>"
        + "                </div>"
        + "            </div>"
        + "        </div>"
        + "    </div>";

        pw.print(body);
        utility.printHtml("Footer.html");
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
        
        displayProductCatalog(request, response, false, false);
    }
}
