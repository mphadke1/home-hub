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


		        // myCarousel = "myCarousel"+l;
					
				// sb.append("<div id='content'><div class='post'><h2 class='title meta'>");
				// sb.append("<a style='font-size: 24px;'>"+oi.getName()+" Accessories</a>");
				
				// sb.append("</h2>");

				// sb.append("<div class='container'>");
				// /* Carousels require the use of an id (in this case id="myCarousel") for carousel controls to function properly.
				// The .slide class adds a CSS transition and animation effect, which makes the items slide when showing a new item.
				// Omit this class if you do not want this effect. 
				// The data-ride="carousel" attribute tells Bootstrap to begin animating the carousel immediately when the page loads.
				// */

				// sb.append("<div class='carousel slide' id='"+myCarousel+"' data-ride='carousel'>");
				
				// /*The slides are specified in a <div> with class .carousel-inner.
				// The content of each slide is defined in a <div> with class .item. This can be text or images.
				// The .active class needs to be added to one of the slides. Otherwise, the carousel will not be visible.
				// */
				// sb.append("<div class='carousel-inner'>");
						
				// Console console1 = hm.get(oi.getName());
				// int k = 0; int size= hm.size();
			
				// for(Map.Entry<String, String> acc:console1.getAccessories().entrySet())
				// {
				
				// 	Accessory accessory= SaxParserDataStore.accessories.get(acc.getValue());
				// 	if (k==0 )
				// 	{
						
				// 		sb.append("<div class='item active'><div class='col-md-6' style = 'background-color: #58acfa;border :1px solid #cfd1d3'>");
				// 	}
				// 	else
				// 	{
				// 		sb.append("<div class='item'><div class='col-md-6' style = 'background-color: #58acfa ;border :1px solid #cfd1d3' >");
				// 	}
				// 	sb.append("<div id='shop_item'>");
				// 	sb.append("<h3>"+accessory.getName()+"</h3>");
				// 	sb.append("<strong>"+accessory.getPrice()+"$</strong><ul>");
				// 	sb.append("<li id='item'><img src='images/accessories/"+accessory.getImage()+"' alt='' /></li>");
				// 	sb.append("<li><form method='post' action='Cart'>" +
				// 			"<input type='hidden' name='name' value='"+acc.getValue()+"'>"+
				// 			"<input type='hidden' name='type' value='accessories'>"+
				// 			"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
				// 			"<input type='hidden' name='access' value='"+oi.getName()+"'>"+
				// 			"<input type='submit' class='btnbuy' value='Buy Now'></form></li>");
				// 	sb.append("<li><form method='POST' action='WriteReview'>"+"<input type='hidden' name='name' value='"+acc+"'>"+
				// 			"<input type='hidden' name='type' value='accessories'>"+
				// 			"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
				// 			"<input type='hidden' name='access' value='"+oi.getName()+"'>"+
				// 			"<input type='submit' value='WriteReview' class='btnreview'></form></li>");
				// 	sb.append("<li><form method='post' action='ViewReview'>"+"<input type='hidden' name='name' value='"+acc+"'>"+
				// 			"<input type='hidden' name='type' value='accessories'>"+
				// 			"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
				// 			"<input type='hidden' name='access' value='"+oi.getName()+"'>"+
				// 			"<input type='submit' value='ViewReview' class='btnreview'></form></li>");

				// 	sb.append("</ul></div></div>");
				// 	sb.append("</div>");
				
				// 	k++;
			
				// }
			
				// sb.append("</div>");
				// /*		The "Left and right controls" part:
				// 	This code adds "left" and "right" buttons that allows the user to go back and forth between the slides manually.
				// The data-slide attribute accepts the keywords "prev" or "next", which alters the slide position relative to its current position.
				// */
				// sb.append("<a class='left carousel-control' href='#"+myCarousel+"' data-slide='prev' style = 'width : 10% ;background-color:#D7e4ef; opacity :1'>"+
				// 		"<span class='glyphicon glyphicon-chevron-left' style = 'color :red'></span>"+
				// 		"<span class='sr-only'>Previous</span>"+
				// 		"</a>");
				// sb.append("<a class='right carousel-control' href='#"+myCarousel+"' data-slide='next' style = 'width : 10% ;background-color:#D7e4ef; opacity :1'>"+
				// 		"<span class='glyphicon glyphicon-chevron-right' style = 'color :red'></span>"+

				// 			"<span class='sr-only'>Next</span>"+
				// 			"</a>");

			
				// sb.append("</div>");
			
				// sb.append("</div></div>");
				// sb.append("</div>");
				// l++;
			
			}
		}
		// return sb.toString();
		return body;
	}
}
	