import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/AddProduct")

public class AddProduct extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

        Utilities utility = new Utilities(request,pw);
		utility.printHtml("Header.html");
		utility.printNavbar();
		utility.printHtml("LeftNavigationBar.html");

        String body = 
          "            <div class='col-12 col-sm-9'>"
        + "                <h2 class='mx-3'>Add Product</h2>"
        + "                <hr>"
        + "                <div class='m-3'>"
        + "                    <form action='post'>"
        + "                        <div class='form-group'>"
        + "                            <label for='product_name'><strong>Product Name</strong></label>"
        + "                            <input type='text' name='product_name' id='product_name' class='form-control' placeholder='Enter Product Name' required>"
        + "                        </div>"
        + "                        <div class='form-group'>"
        + "                            <label for='product_price'><strong>Product Price ($)</strong></label>"
        + "                            <input type='number' name='product_price' id='product_price' class='form-control' placeholder='Enter Product Price' required>"
        + "                        </div>"
        + "                        <div class='form-group'>"
        + "                            <label for='product_image'><strong>Select Image</strong></label>"
        + "                            <input type='file' name='product_image' id='product_image' class='form-control-file' required>"
        + "                        </div>"
        + "                        <div class='form-group'>"
        + "                            <label for='product_accessories'><strong>Select Accessories</strong></label>"
        + "                            <select multiple name='product_accessories' id='product_accessories' class='form-control'>"
        + "                                <option value='option1'>Option 1</option>"
        + "                                <option value='option2'>Option 2</option>"
        + "                                <option value='option3'>Option 3</option>"
        + "                                <option value='option4'>Option 4</option>"
        + "                            </select>"
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

}