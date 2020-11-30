import java.io.IOException;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DealMatchesUtilities")

public class DealMatchesUtilities extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		HashMap<String,Product> selectedproducts = new HashMap<String,Product>();
		
		String body =
			  "			<div class='col-12 col-sm-9'>"
			+ "                <div class='jumbotron mt-3'>"
			+ "                    <h1>Welcome to Best Deal</h1>"
			+ "                    <p class='lead'>The world trusts us to deliver SPEEDY service for all fans</p>"
			+ "                    <hr class='my-4'>"
			+ "                    <p>We beat our competitors in all aspects. Price-Match Guaranteed</p>"
			+ "                </div>"
			+ "                <div class='card'>"
			+ "                    <div class='card-body text-center pb-0'>"
			+ "                    <h5 class='card-title text-left'>Deals</h5>";

		try {
			String line=null;
			String TOMCAT_HOME = System.getProperty("catalina.home");

			HashMap<String, Product> productmap = MySqlDataStoreUtilities.getProductsAsHashMap();
			
			for(Map.Entry<String, Product> entry : productmap.entrySet()) {
				if(!selectedproducts.containsKey(entry.getKey().toLowerCase())) {
					BufferedReader reader = new BufferedReader(
						new FileReader (
							new File(TOMCAT_HOME+"\\webapps\\assignment5\\DealMatches.txt")
						)
					);
					line = reader.readLine();

					if(line == null) {			
						body += "                        <div class='mb-3'>No Deals Found</div>";
						break;
					} else {
						do {	
							if(line.toLowerCase().contains(entry.getKey().toLowerCase())) {
								body += "                        <div class='alert alert-info text-left'>" + line + "</div>";
								selectedproducts.put(entry.getKey(),entry.getValue());
								break;
							}	
						} while((line = reader.readLine()) != null);
					}
				}
			}
		} catch(Exception e) {
			body += "                        No Deals Found";
		}	
			
		body +=
			  "                    </div>"
			+ "                </div>"
			+ "                <h2 class='mt-2'>Deal Matches</h2>";

		if(selectedproducts.size()==0) {
			body +=
				  "                <div class='card'>"
				+ "                    <div class='card-body text-center'>No Deals Found</div>"
				+ "                </div>";
		} else {
			body += "                <div class='row row-cols-2'>";
			for(Map.Entry<String, Product> entry : selectedproducts.entrySet()) {
				Product product = entry.getValue();
				System.out.println(product.getName());
				body +=
				  "                <div class='col-6 my-2'>"
				+ "                    <div class='card p-3'>"
				+ "                        <img id='card-img' src='images/" + product.getImage() + "' class='card-img-top'>"
				+ "                        <div class='card-body'>"
				+ "                            <h5 class='card-title'>" + product.getName() + "</h5>"
				+ "                            <p class='card-text'>$ " + product.getPrice() + "</p>"
				+ "							   <form class='float-right' method='POST' action='Cart'>"
				+ "                                <input type='hidden' name='name' value='" + product.getName() + "'>"
				+ "                                <input type='hidden' name='type' value='" + product.getType() + "'>"
				+ "                                <input type='hidden' name='maker' value='" + product.getRetailer() + "'>"
				+ "                                <input type='hidden' name='access' value=''>"
				+ "                                <button id='buy-button' type='submit' class='btn btn-sm text-light'>Buy Now</button>"
				+ "                            </form>"
				+ "							   <form method='POST' action='WriteReview'>"
				+ "                                <input type='hidden' name='name' value='" + product.getName() + "'>"
				+ "                                <input type='hidden' name='type' value='" + product.getType() + "'>"
				+ "                                <input type='hidden' name='maker' value='" + product.getRetailer() + "'>"
				+ "                                <input type='hidden' name='price' value='" + product.getPrice() + "'>"
				+ "                                <input type='hidden' name='access' value=''>"
				+ "                                <button id='buy-button' type='submit' class='btn btn-sm text-light'>Write Review</button>"
				+ "                            </form>"
				+ "                        </div>"
				+ "                    </div>"
				+ "                </div>";
			}
			body += "                </div>";
		}

		body +=
			  "            </div>"
			+ "        </div>"
			+ "    </div>";

		pw.print(body);
		
	}
}