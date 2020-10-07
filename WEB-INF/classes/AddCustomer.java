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

@WebServlet("/AddCustomer")

public class AddCustomer extends HttpServlet {
    private String error_msg;
    private String success_msg;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		displayAddCustomer(request, response, pw, false, false); //errorMsg, successMsg
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

		//if password and repassword does not match show error message
		if(!password.equals(repassword)) {
            error_msg = "Passwords doesn't match!";
            displayAddCustomer(request, response, pw, true, false);
		} else {
			HashMap<String, User> hm=new HashMap<String, User>();
			String TOMCAT_HOME = System.getProperty("catalina.home");

			//get the user details from file 

			try {
                FileInputStream fileInputStream = new FileInputStream(new File(TOMCAT_HOME+"\\webapps\\Assignment_1\\UserDetails.txt"));
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);	      
                hm= (HashMap)objectInputStream.readObject();
			} catch(Exception e) {
				
			}

			// if the user already exist show error that already exist

            if(hm.containsKey(username)) {
                error_msg = "Username already exist as " + usertype;
                displayAddCustomer(request, response, pw, true, false);
            } else {
				/*create a user object and store details into hashmap
				store the user hashmap into file  */

				User user = new User(username,password,usertype);
				hm.put(username, user);
			    FileOutputStream fileOutputStream = new FileOutputStream(TOMCAT_HOME+"\\webapps\\home-hub\\UserDetails.txt");
        		ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
           	 	objectOutputStream.writeObject(hm);
				objectOutputStream.flush();
				objectOutputStream.close();       
                fileOutputStream.close();
                
                success_msg = "Your " + usertype + " account has been created.";
                displayAddCustomer(request, response, pw, false, true);
			}
        }
	}

	/*  displayAddCustomer function displays the Registration page of New User */

    protected void displayAddCustomer(
		HttpServletRequest request,
		HttpServletResponse response,
		PrintWriter pw,
		boolean error,
        boolean success
	) throws ServletException, IOException {

		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		utility.printNavbar();

		String registerForm =
		  "<div class='container mt-3 bg-light p5'>"
    	+ "    <h3 class='p-2'>Add Customer</h3>"
		+ "    <hr>";

		if(error) {
			registerForm +=
			  "    <div class='alert alert-danger'>"		
			+ error_msg
			+ "    </div>";
		}
		if(success) {
			registerForm +=
			  "    <div class='alert alert-info'>"		
			+ success_msg
			+ "    </div>";
		}

		registerForm +=
    	  "    <div class='row pb-3'>"
    	+ "        <div class='col-4 offset-4'>"
    	+ "            <form method='POST' action='AddCustomer'>"
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
    	+ "                <button id='register-button' type='submit' class='btn text-light float-right'>Add Customer</button><br>"
    	+ "            </form>"
    	+ "        </div>"
    	+ "    </div>"
		+ "</div>";
		pw.print(registerForm);
		utility.printHtml("Footer.html");
	}
}
