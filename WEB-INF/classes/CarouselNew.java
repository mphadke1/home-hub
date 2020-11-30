/* This code is used to implement carousel feature in Website. Carousels are used to implement slide show feature. This  code is used to display 
all the accessories related to a particular product. This java code is getting called from cart.java. The product which is added to cart, all the 
accessories realated to product will get displayed in the carousel.*/
  

import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;	

			
			
public class CarouselNew{
			
	public String  carouselfeature(Utilities utility){
				
		ProductRecommenderUtility prodRecUtility = new ProductRecommenderUtility();
		
		HashMap<String, Console> hm = new HashMap<String, Console>();
		String myCarousel = null;
			
		String name = "";
		HashMap<String,String> prodRecmMap = new HashMap<String,String>();
		prodRecmMap = prodRecUtility.readOutputFile();

		String body = "";

		for(String user: prodRecmMap.keySet()) {
			if(user.equals(utility.username())) {
				String products = prodRecmMap.get(user);
				products = products.replace("[","");
				products = products.replace("]","");
				products = products.replaceAll("\"\"\"\"", "\"");
				products = products.replaceAll("\"\"", "");
				products = products.substring(1);
				products = products.substring(0, products.length() - 1);
				ArrayList<String> productsList = new ArrayList<String>(Arrays.asList(products.split(",")));

				myCarousel = "myCarousel";

                body =
                  "                <h4>Recommended</h4>"
                + "                <div class='row'>"
                + "                    <div class='col-12'>"
                + "                        <div id='" + myCarousel + "' class='carousel slide w-100' data-ride='carousel'>"
                + "                            <div class='carousel-inner'>";


				int k = 0;
				for(String prod : productsList) {
					prod = prod.replaceAll("'", "");
					Product prodObj = new Product();
					prodObj = MySqlDataStoreUtilities.getProductsByNameOrType(prod.trim()).get(0);
					
					if(k % 2 == 0) {
                        if(k == 0) {
                            body +=
                            "                                <div class='carousel-item active'>";
                        } else {
                            body +=
                            "                                <div class='carousel-item'>";
                        }
                        body +=
                        "                                    <div class='row px-5 mx-5 pt-3 k-" + k + "'>";
                    }

                    body +=
                      "                                        <div class='col-6 k-" + k + "'>"
                    + "                                            <div class='card px-4 pt-2'>"
                    + "                                                <img id='card-img' src='images/" + prodObj.getImage() + "' class='card-img-top'>"
                    + "                                                <div class='card-body px-2'>"
                    + "                                                    <h5 class='card-title'>" + prodObj.getName() + "</h5>"
                    + "                                                    <p class='card-text'>$" + prodObj.getPrice() + "</p>"
                    + "                                                    <form class='float-right' method='POST' action='Cart'>"
                    + "                                                        <button type='submit' id='buy-button' class='btn btn-sm text-light float-right'>Buy Now</button>"
                    + "                                                        <input type='hidden' name='name' value='" + prodObj.getName() + "'>"
                    + "                                                        <input type='hidden' name='type' value='" + prodObj.getType() + "'>"
                    + "                                                        <input type='hidden' name='maker' value='" + prodObj.getRetailer() + "'>"
                    + "                                                        <input type='hidden' name='access' value=''>"
                    + "                                                    </form>"
                    + "                                                </div>"
                    + "                                            </div>"
                    + "                                        </div>";

                    if(k % 2 == 1) {
                        body +=
                          "                                    </div>"
                        + "                                </div>";
                    }

                    k++;
                }

                if(k % 2 == 1) {
                    body +=
                        "                                    </div>"
                    + "                                </div>";
                }

                body +=
                  "                            </div>"
                + "                            <a class='carousel-control-prev' href='#" + myCarousel + "' role='button' data-slide='prev'>"
                + "                                <!-- <span class='carousel-control-prev-icon' aria-hidden='true'></span> -->"
                + "                                <span id='carousel-controls' class='fa fa-2x fa-angle-left' aria-hidden='true'></span>"
                + "                            </a>"
                + "                            <a class='carousel-control-next' href='#" + myCarousel + "' role='button' data-slide='next'>"
                + "                                <!-- <span class='carousel-control-next-icon' aria-hidden='true'></span> -->"
                + "                                <span id='carousel-controls' class='fa fa-2x fa-angle-right' aria-hidden='true'></span>"
                + "                            </a>"
                + "                        </div>"
                + "                    </div>"
                + "                </div>";
			}
		}
		return body;
	}
}
	