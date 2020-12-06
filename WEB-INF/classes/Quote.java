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

@WebServlet("/Quote")
public class Quote extends HttpServlet
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
            String date = request.getParameter("datetime");
            SimpleDateFormat ipdf = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
            Date inputDate = ipdf.parse(date);
            SimpleDateFormat opdf = new SimpleDateFormat("MMM dd, yyyy @ hh:mm aa");
            date = opdf.format(inputDate);


            String body =
              "    <div class='container mt-3 bg-light'>"
            + "        <div class='row row-cols-2 pb-3'>"
            + "            <div class='col-8 mt-3'>"
            + "                <h3>Set up your Service</h3>"
            + "                <form action=''>"
            + "                    <div class='form-row'>"
            + "                        <div class='form-group col-6'>"
            + "                            <label for='frequency'>Choose your Frequency</label>"
            + "                            <select name='frequency' id='frequency' class='form-control'>"
            + "                                <option value='Monthly'>Monthly</option>"
            + "                                <option value='Biweekly' selected='selected'>Biweekly</option>"
            + "                                <option value='Weekly'>Weekly</option>"
            + "                            </select>"
            + "                        </div>"
            + "                        <div class='form-group col-6'>"
            + "                            <label for='frequency'>Choose your Plan</label>"
            + "                            <select name='plan' id='plan' class='form-control'>"
            + "                                <option value='3 Months'>3 Months</option>"
            + "                                <option value='6 Months' selected='selected'>6 Months</option>"
            + "                                <option value='12 Months'>12 Months</option>"
            + "                            </select>"
            + "                        </div>"
            + "                    </div>"
            + "                    <hr>"
            + "                    <div class='form-row'>"
            + "                        <div class='form-group col-3'>"
            + "                            <label for='coupon'>Coupon Code (Optional)</label>"
            + "                            <input type='text' class='form-control' placeholder='Coupon Code' value='new-user'>"
            + "                        </div>"
            + "                    </div>"
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
            + "                    <p class='font-weight-bold mb-0'>Biweekly Cleaning Plan</p>"
            + "                    Minimum 6 months"
            + "                    <br><br>"
            + "                    <span>"
            + "                        " + date + "<br>"
            +                           small + " Small Furniture<br>"
            +                           medium + " Medium Furniture<br>"
            +                           large + " Large Furniture"
            + "                    </span>"
            + "                    <table class='table mt-3'>"
            + "                        <tr>"
            + "                            <td>Per Cleaning</td>"
            + "                            <td class='text-right'>$75.00</td>"
            + "                        </tr>"
            + "                        <tr class='table-success d-none'>"
            + "                            <td>Coupon</td>"
            + "                            <td class='text-right'>- $22.50</td>"
            + "                        </tr>"
            + "                        <tr class='font-weight-bold'>"
            + "                            <td>Total</td>"
            + "                            <td class='text-right'>$55.50</td>"
            + "                        </tr>"
            + "                    </table>"
            + "                </div>"
            + "            </div>"
            + "        </div>"
            + "    </div>";


            pw.print(body);

            utility.printHtml("Footer.html");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}