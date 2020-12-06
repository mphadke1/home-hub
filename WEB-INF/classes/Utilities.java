import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

@WebServlet("/Utilities")

/*
	Utilities class contains class variables of type HttpServletRequest, PrintWriter,String and HttpSession.

	Utilities class has a constructor with  HttpServletRequest, PrintWriter variables.

*/

public class Utilities extends HttpServlet{
	HttpServletRequest req;
	PrintWriter pw;
	String url;
	HttpSession session;
	public Utilities(HttpServletRequest req, PrintWriter pw) {
		this.req = req;
		this.pw = pw;
		this.url = this.getFullURL();
		this.session = req.getSession(true);
	}

	public String getUserType() {
		if(session.getAttribute("username") == null) {
			return "Guest";
		}

		return session.getAttribute("usertype").toString().toLowerCase();
	}

	// printNavbar function - Print the right navigation in header like username, cart, logout etc
	public void printNavbar() {

		String navbar =
		  "<nav id='navbar' class='sticky-top navbar navbar-expand-sm navbar-dark'>"
		+ "    <a class='navbar-brand' href='Home'>Home Hub</a>"
		+ "    <button class='navbar-toggler' type='button' data-toggle='collapse' data-target='#navbarSupportedContent'"
		+ "        aria-controls='navbarSupportedContent' aria-expanded='false' aria-label='Toggle navigation'>"
		+ "        <span class='navbar-toggler-icon'></span>"
		+ "    </button>"
		+ "    <div class='collapse navbar-nav navbar-collapse' id='navbarSupportedContent'>"
		+ "			<div class='nav-item dropdown'>"
		+ "                <a class='nav-link dropdown-toggle' href='#' id='navbarDropdown' role='button' data-toggle='dropdown'"
		+ "                    aria-haspopup='true' aria-expanded='false'>"
		+ "                    All Services"
		+ "                </a>"
		+ "                <div class='dropdown-menu' aria-labelledby='navbarDropdown'>"
		+ "                    <a class='dropdown-item' href='#'>Cleaning</a>"
		+ "                    <a class='dropdown-item' href='#'>TV and Electronics</a>"
		+ "                    <a class='dropdown-item' href='#'>Assembly</a>"
		+ "                    <a class='dropdown-item' href='#'>General Handyman</a>"
		+ "                    <a class='dropdown-item' href='#'>Plumbing</a>"
		+ "                    <a class='dropdown-item' href='#'>Electrical</a>"
		+ "                    <a class='dropdown-item' href='#'>Painting</a>"
		+ "                    <a class='dropdown-item' href='#'>Moving</a>"
		+ "                    <a class='dropdown-item' href='#'>Smart Home</a>"
		+ "                </div>"
		+ "            </div>";

		if(isLoggedin()) {
			switch(getUserType().toLowerCase()) {
				case "administrator":
					navbar +=
					  "        <div class='nav-item'>"
					+ "            <a class='nav-link' href='DataAnalytics'>Data Analytics</a>"
					+ "        </div>"
					+ "        <div class='nav-item'>"
					+ "            <a class='nav-link' href='HeatMap'>HeatMap</a>"
					+ "        </div>"
					+ "		   <div class='nav-item dropdown'>"
					+ "		   	   <a class='nav-link dropdown-toggle' href='#' id='navbarDropdownMenuLink' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'>"
					+ "		   	       Reports"
					+ "		   	   </a>"
					+ "		   	   <div class='dropdown-menu' aria-labelledby='navbarDropdownMenuLink'>"
					+ "		   	   <a class='dropdown-item' href='Inventory'>Inventory Report</a>"
					+ "		   	   <a class='dropdown-item' href='SalesReport'>Sales Report</a>"
					+ "            <a class='dropdown-item' href='WorkerReport'>Worker Report</a>"
					+ "		   	   </div>"
					+ "		   </div>"
					+ "		   <div class='nav-item dropdown'>"
					+ "		   	   <a class='nav-link dropdown-toggle' href='#' id='navbarDropdownMenuLink' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'>"
					+ "		   	       Additions"
					+ "		   	   </a>"
					+ "		   	   <div class='dropdown-menu' aria-labelledby='navbarDropdownMenuLink'>"
					+ "		   	   <a class='dropdown-item' href='AddCustomer'>Add Customer</a>"
					+ "		   	   <a class='dropdown-item' href='AddWorker'>Add Worker</a>"
					+ "		   	   </div>"
					+ "		   </div>"
					+ "        <div class='nav-item ml-auto'>"
					+ "            <a class='nav-link' href='CustomerOrders'>Customer Orders</a>"
					+ "        </div>"
					+ "        <div class='nav-item'>"
					+ "            <a class='nav-link' href='ProductCatalog'>Product Catalog</a>"
					+ "        </div>";
					break;
				case "worker":
					navbar +=
					  "        <div class='nav-item'>"
					+ "            <a class='nav-link' href='ApplyForLeave'>Apply For Leave</a>"
					+ "        </div>";
					break;
			}
			navbar +=
			  "        <div class='nav-item'>"
			+ "            <a class='nav-link' href='ViewOrder'>Work Orders</a>"
			+ "        </div>"
			+ "        <div class='nav-item text-light'>"
			+ "            Hello, " + session.getAttribute("username")
			+ "        </div>"
			+ "        <div class='nav-item'>"
			+ "            <a class='nav-link' href='Account'>Account</a>"
			+ "        </div>"
			+ "        <div class='nav-item'>"
			+ "            <a class='nav-link' href='Logout'>Logout</a>"
			+ "        </div>"
			+ "    </div>"
			+ "</nav>";

		} else {
			navbar +=
			  "        <div class='nav-item ml-auto'>"
			+ "            <a class='nav-link' href='Login'>Login</a>"
			+ "        </div>"
			+ "    </div>"
			+ "</nav>";
		}

		pw.print(navbar);
	}

	/*  printhtml Function gets the html file name as function Argument  */

	public void printHtml(String file) {
		String result = HtmlToString(file);
		pw.print(result);
	}


	/*  getFullURL Function - Reconstructs the URL user request  */

	public String getFullURL() {
		String scheme = req.getScheme();
		String serverName = req.getServerName();
		int serverPort = req.getServerPort();
		String contextPath = req.getContextPath();
		StringBuffer url = new StringBuffer();
		url.append(scheme).append("://").append(serverName);

		if ((serverPort != 80) && (serverPort != 443)) {
			url.append(":").append(serverPort);
		}
		url.append(contextPath);
		url.append("/");
		return url.toString();
	}

	/*  HtmlToString - Gets the Html file and Converts into String and returns the String.*/
	public String HtmlToString(String file) {
		String result = null;
		try {
			String webPage = url + file;
			URL url = new URL(webPage);
			URLConnection urlConnection = url.openConnection();
			InputStream is = urlConnection.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);

			int numCharsRead;
			char[] charArray = new char[1024];
			StringBuffer sb = new StringBuffer();
			while ((numCharsRead = isr.read(charArray)) > 0) {
				sb.append(charArray, 0, numCharsRead);
			}
			result = sb.toString();
		}
		catch (Exception e) {
		}
		return result;
	}

	/*  logout Function removes the username , usertype attributes from the session variable*/

	public void logout(){
		session.removeAttribute("username");
		session.removeAttribute("usertype");
	}

	/*  logout Function checks whether the user is loggedIn or Not*/

	public boolean isLoggedin(){
		if (session.getAttribute("username")==null)
			return false;
		return true;
	}

	/*  username Function returns the username from the session variable.*/

	public String username(){
		if (session.getAttribute("username")!=null)
			return session.getAttribute("username").toString();
		return null;
	}

	/*  usertype Function returns the usertype from the session variable.*/
	public String usertype(){
		if (session.getAttribute("usertype")!=null)
			return session.getAttribute("usertype").toString();
		return null;
	}

	/*  getUser Function checks the user is a customer or retailer or manager and returns the user class variable.*/
	public User getUser(){
		String usertype = usertype();
		HashMap<String, User> hm = new HashMap<String, User>();
		String TOMCAT_HOME = System.getProperty("catalina.home");
			try
			{
				hm = MySqlDataStoreUtilities.selectUser();
			}
			catch(Exception e)
			{
			}
		User user = hm.get(username());
		return user;
	}

	/*  getCustomerOrders Function gets  the Orders for the user*/
	public ArrayList<OrderItem> getCustomerOrders(){
		ArrayList<OrderItem> order = new ArrayList<OrderItem>();
		if(OrdersHashMap.orders.containsKey(username()))
			order= OrdersHashMap.orders.get(username());
		return order;
	}

	/*  getOrdersPaymentSize Function gets  the size of OrderPayment */
	public int getOrderPaymentSize(){
		HashMap<Integer, ArrayList<OrderPayment>> orderPayments = new HashMap<Integer, ArrayList<OrderPayment>>();
		String TOMCAT_HOME = System.getProperty("catalina.home");
		try {
			orderPayments = MySqlDataStoreUtilities.selectOrder();
		}
		catch(Exception e) {

		}
		int size=0;
		for(Map.Entry<Integer, ArrayList<OrderPayment>> entry : orderPayments.entrySet()){
			System.out.println(entry);
			size++;
		}
		return size;
	}

	/*  CartCount Function gets  the size of User Orders*/
	public int getCartCount(){
		if(isLoggedin())
			return getCustomerOrders().size();
		return 0;
	}

	/* StoreProduct Function stores the Purchased product in Orders HashMap according to the User Names.*/

	public void storeProduct(String name, String type, String maker, String acc){
		if(!OrdersHashMap.orders.containsKey(username())){
			ArrayList<OrderItem> arr = new ArrayList<OrderItem>();
			OrdersHashMap.orders.put(username(), arr);
		}
		ArrayList<OrderItem> orderItems = OrdersHashMap.orders.get(username());
		OrderItem orderitem;
		if(type.toLowerCase().equals("accessories") || type.toLowerCase().equals("accessory")) {
			Accessory accessory = SaxParserDataStore.accessories.get(name);
			orderitem = new OrderItem(accessory.getName(), accessory.getPrice(), accessory.getImage(), accessory.getRetailer());
			orderItems.add(orderitem);
			return;
		}
		if(type.toLowerCase().equals("tvs") || type.toLowerCase().equals("tv")) {
			TV tv;
			tv = MySqlDataStoreUtilities.getTVs().get(name);
			orderitem = new OrderItem(tv.getName(), tv.getPrice(), tv.getImage(), tv.getRetailer());
			orderItems.add(orderitem);
			return;
		}
		if(type.toLowerCase().equals("soundsystems") || type.toLowerCase().equals("soundsystem")) {
			SoundSystem soundSystem;
			soundSystem = MySqlDataStoreUtilities.getSoundSystems().get(name);
			orderitem = new OrderItem(soundSystem.getName(), soundSystem.getPrice(), soundSystem.getImage(), soundSystem.getRetailer());
			orderItems.add(orderitem);
			return;
		}
		if(type.toLowerCase().equals("phones") || type.toLowerCase().equals("phone")) {
			Phone phone;
			phone = MySqlDataStoreUtilities.getPhones().get(name);
			orderitem = new OrderItem(phone.getName(), phone.getPrice(), phone.getImage(), phone.getRetailer());
			orderItems.add(orderitem);
			return;
		}
		if(type.toLowerCase().equals("laptops") || type.toLowerCase().equals("laptop")) {
			Laptop laptop;
			System.out.println(MySqlDataStoreUtilities.getLaptops());
			laptop = MySqlDataStoreUtilities.getLaptops().get(name);
			orderitem = new OrderItem(laptop.getName(), laptop.getPrice(), laptop.getImage(), laptop.getRetailer());
			orderItems.add(orderitem);
			return;
		}
		if(type.toLowerCase().equals("voiceAssistants") || type.toLowerCase().equals("voiceAssistant")) {
			VoiceAssistant voiceAssistant;
			voiceAssistant = MySqlDataStoreUtilities.getVoiceAssistants().get(name);
			orderitem = new OrderItem(voiceAssistant.getName(), voiceAssistant.getPrice(), voiceAssistant.getImage(), voiceAssistant.getRetailer());
			orderItems.add(orderitem);
			return;
		}
		if(type.toLowerCase().equals("wearables") || type.toLowerCase().equals("wearable")) {
			Wearable wearable;
			wearable = MySqlDataStoreUtilities.getWearables().get(name);
			orderitem = new OrderItem(wearable.getName(), wearable.getPrice(), wearable.getImage(), wearable.getRetailer());
			orderItems.add(orderitem);
			return;
		}
	}

	// store the payment details for orders
	public void storePayment(
			int orderId,
			String orderName,
			double orderPrice,
			String userAddress,
			String creditCardNo,
			String orderDate,
			String deliveryDate,
			String maxOrderCancellationDate,
			String pickupType,
			String storeId
		) {

		HashMap<Integer, ArrayList<OrderPayment>> orderPayments= new HashMap<Integer, ArrayList<OrderPayment>>();
		String TOMCAT_HOME = System.getProperty("catalina.home");
			// get the payment details file
		try {
			orderPayments = MySqlDataStoreUtilities.selectOrder();
		}
		catch(Exception e) {

		}
		if(orderPayments == null) {
			orderPayments = new HashMap<Integer, ArrayList<OrderPayment>>();
		}
		// if there exist order id already add it into same list for order id or create a new record with order id

		if(!orderPayments.containsKey(orderId)){
			ArrayList<OrderPayment> arr = new ArrayList<OrderPayment>();
			orderPayments.put(orderId, arr);
		}
		ArrayList<OrderPayment> listOrderPayment = orderPayments.get(orderId);
		OrderPayment orderpayment = new OrderPayment(
			orderId,
			username(),
			orderName,
			orderPrice,
			userAddress,
			creditCardNo,
			orderDate,
			deliveryDate,
			maxOrderCancellationDate,
			pickupType,
			storeId
		);
		listOrderPayment.add(orderpayment);

		// add order details into file

		try {
			MySqlDataStoreUtilities.insertOrder(
				orderId,
				username(),
				orderName,
				orderPrice,
				userAddress,
				creditCardNo,
				orderDate,
				deliveryDate,
				maxOrderCancellationDate,
				pickupType,
				storeId
			);
		}
		catch(Exception e) {
			System.out.println("inside exception file not written properly");
		}
	}

	public String storeReview(String productname,String producttype,String productmaker,String reviewrating,String reviewdate,String  reviewtext,String reatilerpin,String price,String city, String reviewerAge, String reviewerGender, String reviewerOccupation)
	{
		String message = MongoDBDataStoreUtilities.insertReview(
			productname,
			username(),
			producttype,
			productmaker,
			reviewrating,
			reviewdate,
			reviewtext,
			reatilerpin,
			price,
			city,
			reviewerAge,
			reviewerGender,
			reviewerOccupation
		);
		if(!message.equals("Successfull")) {
			return "UnSuccessfull";
		} else {
			HashMap<String, ArrayList<Review>> reviews= new HashMap<String, ArrayList<Review>>();
			try {
				reviews = MongoDBDataStoreUtilities.selectReview();
			} catch(Exception e) { }

			if(reviews == null) {
				reviews = new HashMap<String, ArrayList<Review>>();
			}
			// if there exist product review already add it into same list for productname or create a new record with product name

			if(!reviews.containsKey(productname)) {
				ArrayList<Review> arr = new ArrayList<Review>();
				reviews.put(productname, arr);
			}
			ArrayList<Review> listReview = reviews.get(productname);
			Review review = new Review(productname,username(),producttype,productmaker,reviewrating,reviewdate,reviewtext,reatilerpin,price,city,reviewerAge,reviewerGender,reviewerOccupation);
			listReview.add(review);

			// add Reviews into database

			return "Successfull";
		}
	}

	/* getSoundSystems Functions returns the Hashmap with all consoles in the store.*/

	public HashMap<String, SoundSystem> getSoundSystems(){
			HashMap<String, SoundSystem> hm = new HashMap<String, SoundSystem>();
			hm.putAll(SaxParserDataStore.soundSystems);
			return hm;
	}

	/* getPhones Functions returns the Hashmap with all consoles in the store.*/

	public HashMap<String, Phone> getPhones(){
			HashMap<String, Phone> hm = new HashMap<String, Phone>();
			hm.putAll(SaxParserDataStore.phones);
			return hm;
	}

	/* getLaptops Functions returns the Hashmap with all consoles in the store.*/

	public HashMap<String, Laptop> getLaptops(){
			HashMap<String, Laptop> hm = new HashMap<String, Laptop>();
			hm.putAll(SaxParserDataStore.laptops);
			return hm;
	}

	/* getVoiceAssistants Functions returns the Hashmap with all consoles in the store.*/

	public HashMap<String, VoiceAssistant> getVoiceAssistants(){
			HashMap<String, VoiceAssistant> hm = new HashMap<String, VoiceAssistant>();
			hm.putAll(SaxParserDataStore.voiceAssistants);
			return hm;
	}

	/* getWearables Functions returns the Hashmap with all consoles in the store.*/

	public HashMap<String, Wearable> getWearables(){
			HashMap<String, Wearable> hm = new HashMap<String, Wearable>();
			hm.putAll(SaxParserDataStore.wearables);
			return hm;
	}

	/* getTVs Functions returns the Hashmap with all consoles in the store.*/

	public HashMap<String, TV> getTVs(){
			HashMap<String, TV> hm = new HashMap<String, TV>();
			hm.putAll(SaxParserDataStore.tvs);
			return hm;
	}

	/* getProducts Functions returns the Arraylist of TVs in the store.*/

	public ArrayList<String> getProductsTVs(){
		ArrayList<String> ar = new ArrayList<String>();
		for(Map.Entry<String, TV> entry : getTVs().entrySet()){
			ar.add(entry.getValue().getName());
		}
		return ar;
	}

	/* getProductsSoundSystems Functions returns the Arraylist of SoundSystems in the store.*/

	public ArrayList<String> getProductsSoundSystems(){
		ArrayList<String> ar = new ArrayList<String>();
		for(Map.Entry<String, SoundSystem> entry : getSoundSystems().entrySet()){
			ar.add(entry.getValue().getName());
		}
		return ar;
	}

	/* getProductsPhones Functions returns the Arraylist of Phones in the store.*/

	public ArrayList<String> getProductsPhones(){
		ArrayList<String> ar = new ArrayList<String>();
		for(Map.Entry<String, Phone> entry : getPhones().entrySet()){
			ar.add(entry.getValue().getName());
		}
		return ar;
	}

	/* getProductsLaptops Functions returns the Arraylist of Laptop in the store.*/

	public ArrayList<String> getProductsLaptops(){
		ArrayList<String> ar = new ArrayList<String>();
		for(Map.Entry<String, Laptop> entry : getLaptops().entrySet()){
			ar.add(entry.getValue().getName());
		}
		return ar;
	}

	/* getProductsVoiceAssistants Functions returns the Arraylist of VoiceAssistant in the store.*/

	public ArrayList<String> getProductsVoiceAssistants(){
		ArrayList<String> ar = new ArrayList<String>();
		for(Map.Entry<String, VoiceAssistant> entry : getVoiceAssistants().entrySet()){
			ar.add(entry.getValue().getName());
		}
		return ar;
	}

	/* getProductsWearables Functions returns the Arraylist of Wearables in the store.*/

	public ArrayList<String> getProductsWearables(){
		ArrayList<String> ar = new ArrayList<String>();
		for(Map.Entry<String, Wearable> entry : getWearables().entrySet()){
			ar.add(entry.getValue().getName());
		}
		return ar;
	}

	public static ArrayList<HeatMapData> getHeatMapDataForTotalReviews() {
		ArrayList<HeatMapData> dataList = new ArrayList<HeatMapData>();
		ArrayList<Store> storeList = new ArrayList<Store>();
		storeList = MySqlDataStoreUtilities.getStoresList();
		for (Store store : storeList) {
			long count = MongoDBDataStoreUtilities.getNumberOfReviewsByZipcode(store.getZipcode());
			HeatMapData data = new HeatMapData(store, (int) count);
			dataList.add(data);
		}

		return dataList;
	}

	public static ArrayList<HeatMapData> getHeatMapDataForLikedProducts() {
		ArrayList<HeatMapData> dataList = new ArrayList<HeatMapData>();
		ArrayList<Store> storeList = new ArrayList<Store>();
		storeList = MySqlDataStoreUtilities.getStoresList();
		for (Store store : storeList) {
			long count = MongoDBDataStoreUtilities.getNumberOfLikedReviewsByZipcode(store.getZipcode());
			HeatMapData data = new HeatMapData(store, (int) count);
			dataList.add(data);
		}

		return dataList;
	}
	public static ArrayList<HeatMapData> getHeatMapDataForDislikedProducts() {
		ArrayList<HeatMapData> dataList = new ArrayList<HeatMapData>();
		ArrayList<Store> storeList = new ArrayList<Store>();
		storeList = MySqlDataStoreUtilities.getStoresList();
		for (Store store : storeList) {
			long count = MongoDBDataStoreUtilities.getNumberOfDislikedReviewsByZipcode(store.getZipcode());
			HeatMapData data = new HeatMapData(store, (int) count);
			dataList.add(data);
		}

		return dataList;
	}

}
