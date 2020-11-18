import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/AddProduct")

public class AddProduct extends HttpServlet {

    Boolean error = false;
    Boolean info = false;
    String error_msg, info_msg;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        displayAddProduct(request, response);
    }

    private void displayAddProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

        Utilities utility = new Utilities(request,pw);
		utility.printHtml("Header.html");
		utility.printNavbar();
		utility.printHtml("LeftNavigationBar.html");

        String body = 
          "            <div class='col-12 col-sm-9'>";

        if(error) {
            body += 
            "                <div class='mx-3 my-3 alert alert-danger'>" + error_msg + "</div>";
        }
        if(info) {
            body += 
            "                <div class='mx-3 my-3 alert alert-info'>" + info_msg + "</div>";
        }
        body +=
          "                <h2 class='mx-3'>Add Product</h2>"
        + "                <hr>"
        + "                <div class='m-3'>"
        + "                    <form action='AddProduct' method='post'>"
        + "                        <div class='form-group'>"
        + "                            <label for='productType'><strong>Select Product Type</strong></label>"
        + "                            <select name='producttype' id='productType' class='form-control' required>"
        + "                                <option value='tv'>TV</option>"
        + "                                <option value='soundSystem'>Sound System</option>"
        + "                                <option value='phone'>Phone</option>"
        + "                                <option value='laptop'>Laptop</option>"
        + "                                <option value='voiceAssistant'>Voice Assistant</option>"
        + "                                <option value='wearable'>Wearable</option>"
        + "                            </select>"
        + "                        </div>"
        + "                        <div class='form-group'>"
        + "                            <label for='productName'><strong>Product Name</strong></label>"
        + "                            <input type='text' name='productName' id='productName' class='form-control' placeholder='Enter Product Name' required>"
        + "                        </div>"
        + "                        <div class='form-group'>"
        + "                            <label for='productPrice'><strong>Product Price ($)</strong></label>"
        + "                            <input type='number' step='any' name='productPrice' id='productPrice' class='form-control' placeholder='Enter Product Price' required>"
        + "                        </div>"
        + "                        <div class='form-group'>"
        + "                            <label for='productImage'><strong>Select Image</strong></label>"
        + "                            <input type='file' accept='image/*' name='productImage' id='productImage' class='form-control-file' required>"
        + "                        </div>"
        + "                        <div class='form-group'>"
        + "                            <label for='productManufacturer'><strong>Product Manufacturer</strong></label>"
        + "                            <input type='text' name='productManufacturer' id='productManufacturer' class='form-control' placeholder='Enter Product Manufacturer' required>"
        + "                        </div>"
        + "                        <div class='form-group'>"
        + "                            <label for='productCondition'><strong>Product Condition</strong></label>"
        + "                            <input type='text' name='productCondition' id='productCondition' class='form-control' placeholder='Enter Product Condition' required>"
        + "                        </div>"
        + "                        <div class='form-group'>"
        + "                            <label for='productDiscount'><strong>Product Discount (%)</strong></label>"
        + "                            <input type='number' step='any' name='productDiscount' id='productDiscount' class='form-control' placeholder='Enter Product Discount' required>"
        + "                        </div>"
        + "                        <button type='submit' id='buy-button' class='btn text-light'>Add Product</button>"
        + "                    </form>"
        + "                </div>"
        + "            </div>"
        + "        </div>"
        + "    </div>";

        pw.print(body);
        utility.printHtml("Footer.html");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, NumberFormatException {
    
        String msg = MySqlDataStoreUtilities.addproducts(
            request.getParameter("productType"),
            request.getParameter("productName"),
            Double.parseDouble(request.getParameter("productPrice")),
            "newtv.webp", // TODO: handle file upload
            request.getParameter("productManufacturer"),
            request.getParameter("productCondition"),
            Double.parseDouble(request.getParameter("productDiscount")),
            0,
            0,
            0,
            ""
        );

        if(msg.contains("Error")) {
            this.error = true;
            this.error_msg = msg;
        } else {
            this.info = true;
            this.info_msg = msg;
        }

        displayAddProduct(request, response);
    
    }
}