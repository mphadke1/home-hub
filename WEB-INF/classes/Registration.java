import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

@WebServlet("/Registration")

public class Registration extends HttpServlet {
	private String error_msg;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		displayRegistration(request, response, pw, false);
	}

	/*   Username,Password,Usertype information are Obtained from HttpServletRequest variable and validates whether
		 the User Credential Already Exists or else User Details are Added to the Users HashMap */

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String repassword = request.getParameter("repassword");
		String usertype = "customer";
		if(!utility.isLoggedin())
			usertype = request.getParameter("usertype");

		//if password and repassword does not match show error message

		if(!password.equals(repassword)) {
			error_msg = "Passwords doesn't match!";
		} else {
			HashMap<String, User> hm=new HashMap<String, User>();
			String TOMCAT_HOME = System.getProperty("catalina.home");

			//get the user details from file 

			try {
				hm = MySqlDataStoreUtilities.selectUser();
			} catch(Exception e) {
				
			}

			// if the user already exist show error that already exist

			if(hm.containsKey(username)) {
				error_msg = "Username already exist as " + usertype;
			} else {
				/*create a user object and store details into hashmap
				store the user hashmap into file  */

				User user = new User(username,password,usertype);
				hm.put(username, user);
				MySqlDataStoreUtilities.insertUser(username,password,repassword,usertype);
				
				HttpSession session = request.getSession(true);				
				session.setAttribute("login_msg", "Your "+usertype+" account has been created. Please login");
				if(!utility.isLoggedin()){
					response.sendRedirect("Login"); return;
				} else {
					response.sendRedirect("Account"); return;
				}
			}
		}

		//display the error message
		if(utility.isLoggedin()){
			HttpSession session = request.getSession(true);				
			session.setAttribute("login_msg", error_msg);
			response.sendRedirect("Account"); return;
		}
		displayRegistration(request, response, pw, true);
		
	}

	/*  displayRegistration function displays the Registration page of New User */
	
	protected void displayRegistration(
		HttpServletRequest request,
		HttpServletResponse response,
		PrintWriter pw,
		boolean error
	) throws ServletException, IOException {

		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		utility.printNavbar();

		String registerForm =
		  "<div class='container mt-3 bg-light p5'>"
    	+ "    <h3 class='p-2'>Register New User</h3>"
		+ "    <hr>";

		if(error) {
			registerForm +=
			  "    <div class='alert alert-danger'>"		
			+ error_msg
			+ "    </div>";
		}

		registerForm +=
    	  "    <div class='row pb-3'>"
    	+ "        <div class='col-4 offset-4'>"
    	+ "            <form method='POST' action='Registration'>"
    	+ "                <div class='form-group'>"
    	+ "                    <label for='username'>Username</label>"
    	+ "                    <input type='text' class='form-control' name='username' placeholder='Enter Username' required>"
    	+ "                </div>"
    	+ "                <div class='form-group'>"
    	+ "                    <label for='password'>Password</label>"
    	+ "                    <input type='password' class='form-control' name='password' placeholder='Enter Password' required>"
    	+ "                </div>"
    	+ "                <div class='form-group'>"
    	+ "                    <label for='password'>Confirm Password</label>"
    	+ "                    <input type='password' class='form-control' name='repassword' placeholder='Re-enter Password' required>"
    	+ "                </div>"
    	+ "                <div class='form-group'>"
    	+ "                    <label for='usertype'>User Type</label>"
    	+ "                    <select class='form-control custom-select' id='usertype' name='usertype'>"
    	+ "                        <option value='customer'>Customer</option>"
    	+ "                        <option value='storeManager'>Store Manager</option>"
    	+ "                        <option value='salesman'>Salesman</option>"
    	+ "                    </select>"
    	+ "                </div>"
    	+ "                <button id='register-button' type='submit' class='btn text-light float-right'>Register</button><br>"
    	+ "            </form>"
    	+ "        </div>"
    	+ "    </div>"
		+ "</div>";
		pw.print(registerForm);
		utility.printHtml("Footer.html");
	}
}
