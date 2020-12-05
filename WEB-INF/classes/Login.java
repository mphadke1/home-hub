import java.io.*;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Login")

public class Login extends HttpServlet {

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		/* User Information(username,password,usertype) is obtained from HttpServletRequest,
		Based on the Type of user(customer,retailer,manager) respective hashmap is called and the username and 
		password are validated and added to session variable and display Login Function is called */

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String usertype = request.getParameter("usertype");
		HashMap<String, User> hm = new HashMap<String, User>();
		String TOMCAT_HOME = System.getProperty("catalina.home");
		try {
			hm = MySqlDataStoreUtilities.selectUser();
		} catch(Exception e) {
				
		}
		User user = hm.get(username);
		if(user != null) {
			String user_password = user.getPassword();
			String user_type = user.getUsertype();
			if (password.equals(user_password) && usertype.equals(user_type)) {
				HttpSession session = request.getSession(true);
				session.setAttribute("username", user.getName());
				session.setAttribute("usertype", user.getUsertype());
				response.sendRedirect("Home");
				return;
			}
		}
		displayLogin(request, response, pw, true);
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		displayLogin(request, response, pw, false);
	}


	/*  Login Screen is Displayed, Registered User specifies credentials and logins into the Game Speed Application. */
	protected void displayLogin(HttpServletRequest request,
			HttpServletResponse response, PrintWriter pw, boolean error)
			throws ServletException, IOException {

		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		utility.printNavbar();

		String loginForm = 
		  "		<div class='container mt-3 bg-light p5'>"
		+ "        <h3 class='p-2'>Login</h3>"
		+ "        <hr>";

		// Check for error
		if(error) {
			loginForm +=
				"        <div class='alert alert-danger'>"
			+ "Invalid Username, Password or User Type"
			+ "        </div>";
		}
		HttpSession session = request.getSession(true);
		if(session.getAttribute("login_msg") != null) {
			loginForm +=
				"        <div class='alert alert-danger'>"
			+ session.getAttribute("login_msg")
			+ "        </div>";
		}

		loginForm +=
		  "        <div class='row pb-3'>"
		+ "            <div class='col-4 offset-4'>"
		+ "                <form method='POST' action='Login'>"
		+ "                    <div class='form-group'>"
		+ "                        <label for='username'>Username</label>"
		+ "                        <input type='text' class='form-control' name='username' placeholder='Enter Username' required>"
		+ "                    </div>"
		+ "                    <div class='form-group'>"
		+ "                        <label for='password'>Password</label>"
		+ "                        <input type='password' class='form-control' name='password' placeholder='Enter Password' required>"
		+ "                    </div>"
		+ "                    <div class='form-group'>"
		+ "                        <label for='usertype'>User Type</label>"
		+ "                        <select class='form-control custom-select' id='usertype' name='usertype'>"
		+ "                            <option value='customer'>Customer</option>"
		+ "                            <option value='administrator'>Administrator</option>"
		+ "                            <option value='worker'>Worker</option>"
		+ "                        </select>"
		+ "                    </div>"
		+ "                    <div class='text-right'>"
		+ "                        <button id='login-button' type='submit' class='btn text-light'>Login</button><br>"
		+ "                        <a class='small' href='Registration'>New User? Register Here</a>"
		+ "                    </div>"
		+ "                </form>"
		+ "            </div>"
		+ "        </div>"
		+ "    </div>";

		pw.print(loginForm);
		utility.printHtml("Footer.html");
	}

}
