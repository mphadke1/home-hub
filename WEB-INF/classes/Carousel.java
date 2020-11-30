/* This code is used to implement carousel feature in Website. Carousels are used to implement slide show feature. This  code is used to display 
all the accessories related to a particular product. This java code is getting called from cart.java. The product which is added to cart, all the 
accessories realated to product will get displayed in the carousel.*/
  

import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
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
			
			
public class Carousel{
			
	public String carouselfeature(Utilities utility) {
				
						
		HashMap<String, TV> hm = new HashMap<String, TV>();
		// StringBuilder sb = new StringBuilder();
		String myCarousel = null;
			
		String name = null;
		String CategoryName = null;
		if(CategoryName == null){
			hm.putAll(SaxParserDataStore.tvs);
			name = "";
		}
        String body = "";
		int l = 0;
		for (OrderItem oi : utility.getCustomerOrders()) {

			if (hm.containsKey(oi.getName())) {	

                myCarousel = "myCarousel" + l;

                body =
                  "                <h4>Accessories</h4>"
                + "                <div class='row'>"
                + "                    <div class='col-12'>"
                + "                        <div id='" + myCarousel + "' class='carousel slide w-100' data-ride='carousel'>"
                + "                            <div class='carousel-inner'>";

                TV tv1 = hm.get(oi.getName());
				int k = 0;
                int size = hm.size();

				for(Map.Entry<String, String> acc : tv1.getAccessories().entrySet()) {

                    Accessory accessory = SaxParserDataStore.accessories.get(acc.getValue());

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
                    + "                                                <img id='card-img' src='images/" + accessory.getImage() + "' class='card-img-top'>"
                    + "                                                <div class='card-body px-2'>"
                    + "                                                    <h5 class='card-title'>" + accessory.getName() + "</h5>"
                    + "                                                    <p class='card-text'>$" + accessory.getPrice() + "</p>"
                    + "                                                    <form class='float-right' method='POST' action='Cart'>"
                    + "                                                        <button type='submit' id='buy-button' class='btn btn-sm text-light float-right'>Buy Now</button>"
                    + "                                                        <input type='hidden' name='name' value='" + acc.getValue() + "'>"
                    + "                                                        <input type='hidden' name='type' value='accessories'>"
                    + "                                                        <input type='hidden' name='maker' value='" + accessory.getRetailer() + "'>"
                    + "                                                        <input type='hidden' name='access' value='" + oi.getName() + "'>"
                    + "                                                    </form>"
                    + "							   						   <form method='POST' action='WriteReview'>"
					+ "                            						       <input type='hidden' name='name' value='" + accessory.getName() + "'>"
					+ "                            						       <input type='hidden' name='type' value='accessory'>"
					+ "                            						       <input type='hidden' name='maker' value='" + accessory.getRetailer() + "'>"
					+ "						                                   <input type='hidden' name='price' value='" + accessory.getPrice() + "'>"
					+ "                            						       <input type='hidden' name='access' value='" + oi.getName() + "'>"
					+ "                            						       <button id='buy-button' type='submit' class='btn btn-sm text-light'>Write Review</button>"
					+ "                            						   </form>"
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

                l++;
			}
		}
		return body;
	}
}
	