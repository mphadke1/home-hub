import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.HashMap;
import java.io.PrintWriter;
import java.io.IOException;
import com.google.gson.Gson;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/FurnitureAssemblyQuoteSuccess")
public class FurnitureAssemblyQuoteSuccess extends HttpServlet
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
        //displayInventory(request, response);
        Utilities utility = new Utilities(request, pw);

        try {
            utility.printHtml("Header.html");
            utility.printNavbar();

            String small, medium, large;
            small = request.getParameter("small-furniture");
            medium = request.getParameter("medium-furniture");
            large = request.getParameter("large-furniture");
            String oldDate = request.getParameter("datetime");
            SimpleDateFormat ipdf = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
            Date inputDate = ipdf.parse(oldDate);
            SimpleDateFormat opdf = new SimpleDateFormat("MMM dd, yyyy @ hh:mm aa");
            String date = opdf.format(inputDate);


            String body =
              "    <div class='container mt-3 bg-light'>"
            + "        <div class='row row-cols-2 pb-3'>"
            + "            <div class='col-8 mt-3'>"
            + "                <h3>Set up your Service</h3>"
            + "                <form action='WorkerList'>"
            + "                    <div class='form-row'>"
            + "                        <div class='form-group col-3'>"
            + "                            <label for='coupon'>Coupon Code (Optional)</label>"
            + "                            <input type='text' class='form-control' placeholder='Coupon Code' value='NEWUSER'>"
            + "                            <div class='text-success'>Coupon Applied</div>"
            + "                        </div>"
            + "                    </div>"
            + "                    <input type='hidden' name='small-furniture' value='" + small + "'/>"
            + "                    <input type='hidden' name='medium-furniture' value='" + medium + "'/>"
            + "                    <input type='hidden' name='large-furniture' value='" + large + "'/>"
            + "                    <input type='hidden' name='datetime' value='" + oldDate + "'/>"
            + "                    <button id='buy-button' class='btn text-light'>Validate</button>"
            + "                    <hr>"
            + "                    <h3>Select Worker</h3>"
            + "                    <div class='form-row'>"
            + "                        <div class='form-group col-6'>"
            + "                            <select id='worker-select' class='form-control'>"
            + "                                <option value='automatic'>Automatic</option>"
            + "                                <option value='manual'>Manual</option>"
            + "                            </select>"
            + "                        </div>"
            + "                    </div>"
            + "                    <button type='submit' id='buy-button' class='btn text-light select-worker d-none'>Select Worker</button>"
            + "                    <hr>"
            + "                    <h4>Service Address</h4>"
            + "                    <div class='form-row'>"
            + "                        <div class='form-group col-6'>"
            + "                            <label for='first-name'>First Name</label>"
            + "                            <input type='text' class='form-control' placeholder='Enter First Name'>"
            + "                        </div>"
            + "                        <div class='form-group col-6'>"
            + "                            <label for='last-name'>Last Name</label>"
            + "                            <input type='text' class='form-control' placeholder='Enter Last Name'>"
            + "                        </div>"
            + "                    </div>"
            + "                    <div class='form-row'>"
            + "                        <div class='form-group col-6'>"
            + "                            <label for='street'>Street Address</label>"
            + "                            <input type='text' class='form-control' placeholder='Enter Street Address'>"
            + "                        </div>"
            + "                        <div class='form-group col-6'>"
            + "                            <label for='apt'>Apt # (Optional)</label>"
            + "                            <input type='text' class='form-control' placeholder='Enter Apt # (Optional)'>"
            + "                        </div>"
            + "                    </div>"
            + "                    <div class='form-row'>"
            + "                        <div class='form-group col-6'>"
            + "                            <label for='city'>City</label>"
            + "                            <input type='text' class='form-control' placeholder='Enter City'>"
            + "                        </div>"
            + "                        <div class='form-group col-6'>"
            + "                            <label for='state'>State</label>"
            + "                            <input type='text' class='form-control' placeholder='Enter State'>"
            + "                        </div>"
            + "                    </div>"
            + "                    <div class='form-row'>"
            + "                        <div class='form-group col-6'>"
            + "                            <label for='phone'>Phone Number</label>"
            + "                            <input type='number' class='form-control' placeholder='Enter Phone Number'>"
            + "                        </div>"
            + "                    </div>"
            + "                    <hr>"
            + "                    <h4>Payment Information</h4>"
            + "                    <div class='form-row'>"
            + "                        <div class='form-group col-6'>"
            + "                            <label for='credit'>Credit Card Number</label>"
            + "                            <input type='number' class='form-control' placeholder='Enter Credit Card Number'>"
            + "                        </div>"
            + "                    </div>"
            + "                    <div class='form-row'>"
            + "                        <div class='form-group col-6'>"
            + "                            <label for='phone'>Expiry Date</label>"
            + "                            <input type='number' class='form-control' placeholder='Enter Expiry Date (MM/YY)'>"
            + "                        </div>"
            + "                        <div class='form-group col-6'>"
            + "                            <label for='phone'>Security Code</label>"
            + "                            <input type='number' class='form-control' placeholder='CVC'>"
            + "                        </div>"
            + "                    </div>"
            + "                    <button class='btn btn-block btn-primary text-light' id='buy-button'>Complete Booking</button>"
            + "                    <div class='clearfix'>"
            + "                    </div>"
            + "                </form>"
            + "            </div>"
            + "            <div class='col-4 mt-4'>"
            + "                <div class='bg-white p-3 sticky-top' style='top: 80px'>"
            + "                    <p class='font-weight-bold mb-0'>Furniture Assembly</p>"
            + "                    <br>"
            + "                    <span>"
            + "                        " + date + "<br>"
            +                           small + " Small Furniture<br>"
            +                           medium + " Medium Furniture<br>"
            +                           large + " Large Furniture"
            + "                    </span>"
            + "                    <table class='table mt-3'>"
            + "                        <tr>"
            + "                            <td>Base Price</td>"
            + "                            <td class='text-right'>$100.00</td>"
            + "                        </tr>"
            + "                        <tr class='table-success'>"
            + "                            <td>Coupon</td>"
            + "                            <td id='coupon' class='text-right'>- $30.00</td>"
            + "                        </tr>"
            + "                        <tr class='font-weight-bold'>"
            + "                            <td>Total</td>"
            + "                            <td class='text-right'>$70.00</td>"
            + "                        </tr>"
            + "                    </table>"
            + "                </div>"
            + "            </div>"
            + "        </div>"
            + "    </div>"
            + "    <script src='furnitureAssemblyQuote.js'></script>";


            pw.print(body);

            utility.printHtml("Footer.html");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}