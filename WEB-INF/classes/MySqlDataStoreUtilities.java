import java.sql.*;
import java.util.*;

public class MySqlDataStoreUtilities
{
    static Connection conn = null;

    public static void getConnection()
    {

        try
        {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignment2","root","root");
        }
        catch(Exception e)
        {

        }
    }

    public static void startup() {
        try {
            getConnection();
            String query =
              "create table if not exists Registration("
            + "    username varchar(50) primary key,"
            + "    password varchar(50),"
            + "    repassword varchar(50),"
            + "    usertype varchar(50)"
            + ");";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.executeUpdate();

            query =
              "create table if not exists stores("
            + "    store_id int primary key AUTO_INCREMENT,"
            + "    name varchar(50),"
            + "    street varchar(50),"
            + "    city varchar(50),"
            + "    state varchar(50),"
            + "    zipcode int"
            + ");";
            pst = conn.prepareStatement(query);
            pst.executeUpdate();

            query =
              "create table if not exists customerOrders("
            + "    OrderId int primary key,"
            + "    UserName varchar(50),"
            + "    OrderName varchar(50),"
            + "    OrderPrice double,"
            + "    userAddress varchar(50),"
            + "    creditCardNo varchar(50),"
            + "    orderDate varchar(50),"
            + "    deliveryDate varchar(50),"
            + "    maxOrderCancellationDate varchar(50),"
            + "    pickupType varchar(50),"
            + "    store_id int,"
            + "    foreign key (store_id) references stores(store_id)"
            + ");";
            pst = conn.prepareStatement(query);
            pst.executeUpdate();

            query =
              "create table if not exists Productdetails("
            + "    ProductType varchar(50),"
            + "    Id int primary key AUTO_INCREMENT,"
            + "    productName varchar(50),"
            + "    productPrice double,"
            + "    productImage varchar(50),"
            + "    productManufacturer varchar(50),"
            + "    productCondition varchar(50),"
            + "    productDiscount double,"
            + "    manufacturerRebate double,"
            + "    numberOfAvailableProducts int,"
            + "    numberOfItemsSold int"
            + ");";
            pst = conn.prepareStatement(query);
            pst.executeUpdate();

            query = "select count(*) from Productdetails";
            pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            rs.next();
            int count = rs.getInt("count(*)");
            int prodCount = 0;
            if(count == 0) {
                for(Map.Entry<String, TV> entry : SaxParserDataStore.tvs.entrySet()) {
                    prodCount++;
                    TV tv = entry.getValue();
                    addproducts(
                        "tv",
                        tv.getName(),
                        tv.getPrice(),
                        tv.getImage(),
                        tv.getRetailer(),
                        tv.getCondition(),
                        tv.getDiscount(),
                        0,
                        20,
                        10 + prodCount,
                        ""
                    );
                }
                for(Map.Entry<String, SoundSystem> entry : SaxParserDataStore.soundSystems.entrySet()) {
                    prodCount++;
                    SoundSystem soundSystem = entry.getValue();
                    addproducts(
                        "soundSystem",
                        soundSystem.getName(),
                        soundSystem.getPrice(),
                        soundSystem.getImage(),
                        soundSystem.getRetailer(),
                        soundSystem.getCondition(),
                        soundSystem.getDiscount(),
                        10,
                        40,
                        20,
                        ""
                    );
                }
                for(Map.Entry<String, Phone> entry : SaxParserDataStore.phones.entrySet()) {
                    prodCount++;
                    Phone phone = entry.getValue();
                    addproducts(
                        "phone",
                        phone.getName(),
                        phone.getPrice(),
                        phone.getImage(),
                        phone.getRetailer(),
                        phone.getCondition(),
                        phone.getDiscount(),
                        prodCount,
                        100,
                        70 - prodCount,
                        ""
                    );
                }
                for(Map.Entry<String, Laptop> entry : SaxParserDataStore.laptops.entrySet()) {
                    prodCount++;
                    Laptop laptop = entry.getValue();
                    addproducts(
                        "laptop",
                        laptop.getName(),
                        laptop.getPrice(),
                        laptop.getImage(),
                        laptop.getRetailer(),
                        laptop.getCondition(),
                        laptop.getDiscount(),
                        20 + prodCount,
                        30,
                        10,
                        ""
                    );
                }
                for(Map.Entry<String, VoiceAssistant> entry : SaxParserDataStore.voiceAssistants.entrySet()) {
                    prodCount++;
                    VoiceAssistant voiceAssistant = entry.getValue();
                    addproducts(
                        "voiceAssistant",
                        voiceAssistant.getName(),
                        voiceAssistant.getPrice(),
                        voiceAssistant.getImage(),
                        voiceAssistant.getRetailer(),
                        voiceAssistant.getCondition(),
                        voiceAssistant.getDiscount(),
                        0,
                        70,
                        10 + prodCount,
                        ""
                    );
                }
                for(Map.Entry<String, Wearable> entry : SaxParserDataStore.wearables.entrySet()) {
                    prodCount++;
                    Wearable wearable = entry.getValue();
                    addproducts(
                        "wearable",
                        wearable.getName(),
                        wearable.getPrice(),
                        wearable.getImage(),
                        wearable.getRetailer(),
                        wearable.getCondition(),
                        wearable.getDiscount(),
                        0,
                        200,
                        100 + prodCount,
                        ""
                    );
                }
            }

        } catch(Exception e) {
            System.out.println("Error creating initial structure:");
            System.out.println(e.getMessage());
        }
    }

    public static void deleteOrder(int orderId,String orderName)
    {
        try
        {
            getConnection();
            String deleteOrderQuery ="Delete from customerorders where OrderId=? and orderName=?";
            PreparedStatement pst = conn.prepareStatement(deleteOrderQuery);
            pst.setInt(1,orderId);
            pst.setString(2,orderName);
            pst.executeUpdate();
        }
        catch(Exception e)
        {

        }
    }

    public static void insertOrder(int orderId,String userName,String orderName,double orderPrice,String userAddress,String creditCardNo, String orderDate, String deliveryDate, String maxOrderCancellationDate, String pickupType, String store_id)
    {
        try
        {

            getConnection();
            String insertIntoCustomerOrderQuery = "INSERT INTO customerOrders(OrderId,UserName,OrderName,OrderPrice,userAddress,creditCardNo,orderDate,deliveryDate,maxOrderCancellationDate,pickupType,store_id)"
            + "VALUES (?,?,?,?,?,?,?,?,?,?,?);";

            PreparedStatement pst = conn.prepareStatement(insertIntoCustomerOrderQuery);
            //set the parameter for each column and execute the prepared statement
            pst.setInt(1,orderId);
            pst.setString(2,userName);
            pst.setString(3,orderName);
            pst.setDouble(4,orderPrice);
            pst.setString(5,userAddress);
            pst.setString(6,creditCardNo);
            pst.setString(7,orderDate);
            pst.setString(8,deliveryDate);
            pst.setString(9,maxOrderCancellationDate);
            pst.setString(10,pickupType);
            if(store_id.equals("0")) {
                pst.setString(11, "NULL");
            } else {
                pst.setInt(11, Integer.parseInt(store_id));
            }
            pst.execute();
        }
        catch(Exception e)
        {

        }
    }

    public static HashMap<Integer, ArrayList<OrderPayment>> selectOrder()
    {

        HashMap<Integer, ArrayList<OrderPayment>> orderPayments=new HashMap<Integer, ArrayList<OrderPayment>>();

        try
        {

            getConnection();
            //select the table
            String selectOrderQuery ="select * from customerorders";
            PreparedStatement pst = conn.prepareStatement(selectOrderQuery);
            ResultSet rs = pst.executeQuery();
            ArrayList<OrderPayment> orderList=new ArrayList<OrderPayment>();
            while(rs.next())
            {
                if(!orderPayments.containsKey(rs.getInt("OrderId")))
                {
                    ArrayList<OrderPayment> arr = new ArrayList<OrderPayment>();
                    orderPayments.put(rs.getInt("orderId"), arr);
                }
                ArrayList<OrderPayment> listOrderPayment = orderPayments.get(rs.getInt("OrderId"));
                System.out.println("data is"+rs.getInt("OrderId")+orderPayments.get(rs.getInt("OrderId")));

                //add to orderpayment hashmap
                OrderPayment order= new OrderPayment(
                    rs.getInt("OrderId"),
                    rs.getString("userName"),
                    rs.getString("orderName"),
                    rs.getDouble("orderPrice"),
                    rs.getString("userAddress"),
                    rs.getString("creditCardNo"),
                    rs.getString("orderDate"),
                    rs.getString("deliveryDate"),
                    rs.getString("maxOrderCancellationDate"),
                    rs.getString("pickupType"),
                    Integer.toString(rs.getInt("store_id"))
                );
                listOrderPayment.add(order);

            }


        }
        catch(Exception e)
        {

        }
        return orderPayments;
    }


    public static void insertUser(String username,String password,String repassword,String usertype)
    {
        try
        {

            getConnection();
            String insertIntoCustomerRegisterQuery = "INSERT INTO Registration(username,password,repassword,usertype) "
            + "VALUES (?,?,?,?);";

            PreparedStatement pst = conn.prepareStatement(insertIntoCustomerRegisterQuery);
            pst.setString(1,username);
            pst.setString(2,password);
            pst.setString(3,repassword);
            pst.setString(4,usertype);
            pst.execute();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static HashMap<String,User> selectUser()
    {
        HashMap<String,User> hm=new HashMap<String,User>();
        try
        {
            getConnection();
            Statement stmt=conn.createStatement();
            String selectCustomerQuery="select * from  Registration";
            ResultSet rs = stmt.executeQuery(selectCustomerQuery);
            while(rs.next())
            {	User user = new User(
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("usertype")
                );
                    hm.put(rs.getString("username"), user);
            }
        }
        catch(Exception e)
        {
            System.out.println("ERROR");
            System.out.println(e.getMessage());
        }
        return hm;
    }

    public static HashMap<String,Wearable> getWearables()
    {
        HashMap<String,Wearable> hm=new HashMap<String,Wearable>();
        try
        {
            getConnection();

            String selectWearable="select * from  Productdetails where ProductType=?";
            PreparedStatement pst = conn.prepareStatement(selectWearable);
            pst.setString(1,"wearable");
            ResultSet rs = pst.executeQuery();

            while(rs.next())
            {
                Wearable wp = new Wearable(
                    rs.getString("productName"),
                    rs.getDouble("productPrice"),
                    rs.getString("productImage"),
                    rs.getString("productManufacturer"),
                    rs.getString("productCondition"),
                    rs.getDouble("productDiscount")
                );
                hm.put(rs.getString("productName"), wp);
                wp.setId(rs.getString("Id"));
            }
        }
        catch(Exception e)
        {

        }
        return hm;
    }

    public static HashMap<String,TV> getTVs()
    {
        HashMap<String,TV> hm=new HashMap<String,TV>();
        try
        {
            getConnection();

            String selectTV="select * from  Productdetails where ProductType=?";
            PreparedStatement pst = conn.prepareStatement(selectTV);
            pst.setString(1,"tv");
            ResultSet rs = pst.executeQuery();

            while(rs.next())
            {
                TV tv = new TV(
                    rs.getString("productName"),
                    rs.getDouble("productPrice"),
                    rs.getString("productImage"),
                    rs.getString("productManufacturer"),
                    rs.getString("productCondition"),
                    rs.getDouble("productDiscount")
                );
                hm.put(rs.getString("productName"), tv);
                tv.setId(rs.getString("Id"));
            }
        }
        catch(Exception e)
        {

        }
        return hm;
    }

    public static HashMap<String,Laptop> getLaptops()
    {
        HashMap<String,Laptop> hm=new HashMap<String,Laptop>();
        try
        {
            getConnection();

            String selectLaptop="select * from  Productdetails where ProductType=?";
            PreparedStatement pst = conn.prepareStatement(selectLaptop);
            pst.setString(1,"laptop");
            ResultSet rs = pst.executeQuery();

            while(rs.next())
            {
                Laptop lp = new Laptop(
                    rs.getString("productName"),
                    rs.getDouble("productPrice"),
                    rs.getString("productImage"),
                    rs.getString("productManufacturer"),
                    rs.getString("productCondition"),
                    rs.getDouble("productDiscount")
                );
                hm.put(rs.getString("productName"), lp);
                lp.setId(rs.getString("Id"));
            }
        }
        catch(Exception e)
        {

        }
        return hm;
    }

    public static HashMap<String,Phone> getPhones()
    {
        HashMap<String,Phone> hm=new HashMap<String,Phone>();
        try
        {
            getConnection();

            String selectPhone="select * from  Productdetails where ProductType=?";
            PreparedStatement pst = conn.prepareStatement(selectPhone);
            pst.setString(1,"phone");
            ResultSet rs = pst.executeQuery();

            while(rs.next())
            {
                Phone ph = new Phone(
                    rs.getString("productName"),
                    rs.getDouble("productPrice"),
                    rs.getString("productImage"),
                    rs.getString("productManufacturer"),
                    rs.getString("productCondition"),
                    rs.getDouble("productDiscount")
                );
                hm.put(rs.getString("productName"), ph);
                ph.setId(rs.getString("Id"));
            }
        }
        catch(Exception e)
        {

        }
        return hm;
    }

    public static HashMap<String,SoundSystem> getSoundSystems()
    {
        HashMap<String,SoundSystem> hm=new HashMap<String,SoundSystem>();
        try
        {
            getConnection();

            String selectSoundSystem="select * from  Productdetails where ProductType=?";
            PreparedStatement pst = conn.prepareStatement(selectSoundSystem);
            pst.setString(1,"soundSystem");
            ResultSet rs = pst.executeQuery();

            while(rs.next())
            {
                SoundSystem ss = new SoundSystem(
                    rs.getString("productName"),
                    rs.getDouble("productPrice"),
                    rs.getString("productImage"),
                    rs.getString("productManufacturer"),
                    rs.getString("productCondition"),
                    rs.getDouble("productDiscount")
                );
                hm.put(rs.getString("productName"), ss);
                ss.setId(rs.getString("Id"));
            }
        }
        catch(Exception e)
        {

        }
        return hm;
    }

    public static HashMap<String,VoiceAssistant> getVoiceAssistants()
    {
        HashMap<String,VoiceAssistant> hm=new HashMap<String,VoiceAssistant>();
        try
        {
            getConnection();

            String selectVoiceAssistant="select * from  Productdetails where ProductType=?";
            PreparedStatement pst = conn.prepareStatement(selectVoiceAssistant);
            pst.setString(1,"voiceAssistant");
            ResultSet rs = pst.executeQuery();

            while(rs.next())
            {
                VoiceAssistant va = new VoiceAssistant(
                    rs.getString("productName"),
                    rs.getDouble("productPrice"),
                    rs.getString("productImage"),
                    rs.getString("productManufacturer"),
                    rs.getString("productCondition"),
                    rs.getDouble("productDiscount")
                );
                hm.put(rs.getString("productName"), va);
                va.setId(rs.getString("Id"));
            }
        }
        catch(Exception e)
        {

        }
        return hm;
    }

    public static String addproducts(String producttype,String productName,double productPrice,String productImage,String productManufacturer,String productCondition,double productDiscount,double manufacturerRebate,int numberOfAvailableProducts,int numberOfProductsSold,String prod)
    {
        String msg = "Product is added successfully";
        try
        {
            getConnection();
            String addProductQurey = "INSERT INTO Productdetails(ProductType,productName,productPrice,productImage,productManufacturer,productCondition,productDiscount,manufacturerRebate,numberOfAvailableProducts,numberOfProductsSold)" +
            "VALUES (?,?,?,?,?,?,?,?,?,?);";

            PreparedStatement pst = conn.prepareStatement(addProductQurey);
            pst.setString(1,producttype);
            pst.setString(2,productName);
            pst.setDouble(3,productPrice);
            pst.setString(4,productImage);
            pst.setString(5,productManufacturer);
            pst.setString(6,productCondition);
            pst.setDouble(7,productDiscount);
            pst.setDouble(8,manufacturerRebate);
            pst.setDouble(9,numberOfAvailableProducts);
            pst.setDouble(10,numberOfProductsSold);

            pst.executeUpdate();
        }
        catch(Exception e)
        {
            msg = "Error while adding the product";
            e.printStackTrace();

        }
        return msg;
    }
    public static String updateproducts(String producttype,String productId,String productName,double productPrice,String productImage,String productManufacturer,String productCondition,double productDiscount)
    {
        String msg = "Product is updated successfully";
        try
        {
            getConnection();
            String updateProductQurey = "UPDATE Productdetails SET productName=?,productPrice=?,productImage=?,productManufacturer=?,productCondition=?,productDiscount=? where Id =?;" ;
            PreparedStatement pst = conn.prepareStatement(updateProductQurey);

            pst.setString(1,productName);
            pst.setDouble(2,productPrice);
            pst.setString(3,productImage);
            pst.setString(4,productManufacturer);
            pst.setString(5,productCondition);
            pst.setDouble(6,productDiscount);
            pst.setString(7,productId);
            pst.executeUpdate();
        }
        catch(Exception e)
        {
            msg = "Product cannot be updated";
            e.printStackTrace();
        }
        return msg;
    }

    public static String deleteproducts(String productId)
    {
        String msg = "Product is deleted successfully";
        try
        {
            getConnection();
            String deleteproductsQuery ="Delete from Productdetails where Id=?";
            PreparedStatement pst = conn.prepareStatement(deleteproductsQuery);
            pst.setString(1,productId);
            pst.executeUpdate();
        }
        catch(Exception e)
        {
            msg = "Proudct cannot be deleted";
        }
        return msg;
    }

    public static HashMap<String, Store> getStores() {
        HashMap<String, Store> hm = new HashMap<String, Store>();
        try {
            getConnection();
            String getStoresQuery = "SELECT * from stores;";
            PreparedStatement pst = conn.prepareStatement(getStoresQuery);
            ResultSet rs = pst.executeQuery();

            while(rs.next()) {
                Store store = new Store(
                    rs.getInt("store_id"),
                    rs.getString("name"),
                    rs.getString("street"),
                    rs.getString("city"),
                    rs.getString("state"),
                    rs.getInt("zipcode")
                );
                hm.put(Integer.toString(rs.getInt("store_id")), store);
            }
        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return hm;
    }

    public static Store getStoreById(int store_id) {
        Store store = null;
        try {
            getConnection();
            String getStoresQuery = "SELECT * from stores where store_id = ?;";
            PreparedStatement pst = conn.prepareStatement(getStoresQuery);
            pst.setInt(1, store_id);
            ResultSet rs = pst.executeQuery();

            rs.next();
            store = new Store(
                rs.getInt("store_id"),
                rs.getString("name"),
                rs.getString("street"),
                rs.getString("city"),
                rs.getString("state"),
                rs.getInt("zipcode")
            );
        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return store;
    }

    public static ArrayList<NoOfAvailableProducts> availableProductsList() {
        ArrayList<NoOfAvailableProducts> availableProductsList = new ArrayList<NoOfAvailableProducts>();

        try {
            getConnection();
            
            String availableProductsListQuery="SELECT productName, productPrice, productDiscount, numberOfAvailableProducts, manufacturerRebate FROM productdetails;";
            PreparedStatement pst = conn.prepareStatement(availableProductsListQuery);
            ResultSet rs = pst.executeQuery();
        
            while(rs.next())
            {	
                NoOfAvailableProducts product = new NoOfAvailableProducts(rs.getString("productName"),rs.getString("productPrice"),rs.getString("productPrice"),rs.getString("numberOfAvailableProducts"),rs.getString("manufacturerRebate"));
                availableProductsList.add(product);
            }
        }
        catch(Exception e) {
            System.out.println("availableProductsList(): "+e);
        }

        return availableProductsList;
    }

    public static ArrayList<NoOfAvailableProducts> currentOnSaleProductsList()
    {
        ArrayList<NoOfAvailableProducts> currentOnSaleProductsList = new ArrayList<NoOfAvailableProducts>();
        
        try {
            getConnection();
            
            String currentOnSaleProductsListQuery="SELECT productName, productPrice, productDiscount, numberOfAvailableProducts, manufacturerRebate FROM productdetails WHERE productDiscount > 0;";
            PreparedStatement pst = conn.prepareStatement(currentOnSaleProductsListQuery);
            ResultSet rs = pst.executeQuery();
        
            while(rs.next()) {	
                NoOfAvailableProducts product = new NoOfAvailableProducts(rs.getString("productName"),rs.getString("productPrice"),rs.getString("productDiscount") ,rs.getString("numberOfAvailableProducts"),rs.getString("manufacturerRebate"));
                currentOnSaleProductsList.add(product);
            }
        }
        catch(Exception e) {
            System.out.println("currentOnSaleProductsList(): "+e);
        }

        return currentOnSaleProductsList;
    }

    public static ArrayList<NoOfAvailableProducts> manufacturerRebateProductsList() {
        ArrayList<NoOfAvailableProducts> manufacturerRebateProductsList = new ArrayList<NoOfAvailableProducts>();

        try {
            getConnection();

            String manufacturerRebateProductsListQuery="SELECT productName, productPrice, productDiscount,numberOfAvailableProducts, manufacturerRebate FROM productdetails WHERE manufacturerRebate > 0;";
            PreparedStatement pst = conn.prepareStatement(manufacturerRebateProductsListQuery);
            ResultSet rs = pst.executeQuery();

            while(rs.next()) {
                NoOfAvailableProducts product = new NoOfAvailableProducts(rs.getString("productName"),rs.getString("productPrice"),rs.getString("productDiscount") ,rs.getString("numberOfAvailableProducts") ,rs.getString("manufacturerRebate"));
                manufacturerRebateProductsList.add(product);
            }
        }
        catch(Exception e) {
            System.out.println("manufacturerRebateProductsList(): "+e);
        }

        return manufacturerRebateProductsList;
    }

    public static ArrayList<NoOfProductsSold> totalSoldProductsList()
    {
        ArrayList<NoOfProductsSold> totalSoldProductsList = new ArrayList<NoOfProductsSold>();

        try 
        {
            getConnection();
            
            String totalSoldProductsListQuery="SELECT productName, productPrice, numberOfItemsSold, (productPrice * numberOfItemsSold) AS productTotalSales FROM productdetails;";
            PreparedStatement pst = conn.prepareStatement(totalSoldProductsListQuery);
            ResultSet rs = pst.executeQuery();
        
            while(rs.next())
            {	
                NoOfProductsSold product = new NoOfProductsSold(rs.getString("productName"),rs.getString("productPrice"),rs.getString("numberOfItemsSold") ,rs.getString("productTotalSales"));
                totalSoldProductsList.add(product);
            }
        }
        catch(Exception e)
        {
            System.out.println("totalSoldProductsList(): "+e);
        }

        return totalSoldProductsList;
    }

    public static ArrayList<TotalSalesDaily> totalSalesDailyOrdersList()
    {
        ArrayList<TotalSalesDaily> totalSalesDailyOrdersList = new ArrayList<TotalSalesDaily>();

        try 
        {
            getConnection();
            
            String totalSalesDailyOrdersListQuery="SELECT orderDate, SUM(orderPrice) AS totalDailySales, GROUP_CONCAT(CONCAT(orderName, ' (', orderPrice, ')')) AS productsListDesciption FROM customerorders GROUP BY orderDate;";
            PreparedStatement pst = conn.prepareStatement(totalSalesDailyOrdersListQuery);
            ResultSet rs = pst.executeQuery();
        
            while(rs.next())
            {	
                TotalSalesDaily order = new TotalSalesDaily(rs.getString("orderDate"),rs.getString("totalDailySales"), rs.getString("productsListDesciption"));
                totalSalesDailyOrdersList.add(order);
            }
        }
        catch(Exception e)
        {
            System.out.println("totalSalesDailyOrdersList(): "+e);
        }

        return totalSalesDailyOrdersList;
    }

    public static ArrayList<Product> getProductsByNameOrType(String filterString) {
        ArrayList<Product> productList = new ArrayList<Product>();

        try 
        {
            getConnection();
            
            String productQuery = "SELECT * FROM productDetails where productName LIKE ? OR ProductType LIKE ? OR productManufacturer LIKE ? ORDER BY productName;";
            PreparedStatement pst = conn.prepareStatement(productQuery);
            pst.setString(1, "%" + filterString + "%");
            pst.setString(2, "%" + filterString + "%");
            pst.setString(3, "%" + filterString + "%");
            ResultSet rs = pst.executeQuery();
        
            while(rs.next()) {	
                Product p = new Product(rs.getString("Id"),rs.getString("productName"),rs.getDouble("productPrice"),rs.getString("productImage"),rs.getString("productManufacturer"),rs.getString("productCondition"),rs.getString("ProductType"),rs.getDouble("productDiscount"));
                productList.add(p);
			}
        }
        catch(Exception e)
        {
            System.out.println("getProductsByName(): " + e.getMessage());
        }

        return productList;
    }

    public static Product getProductById(String id) {
        Product p = null;
        try 
        {
            getConnection();
            
            String productQuery = "SELECT * FROM productDetails where Id = ?;";
            PreparedStatement pst = conn.prepareStatement(productQuery);
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();
        
            rs.next();
            p = new Product(
                rs.getString("Id"),
                rs.getString("productName"),
                rs.getDouble("productPrice"),
                rs.getString("productImage"),
                rs.getString("productManufacturer"),
                rs.getString("productCondition"),
                rs.getString("ProductType"),
                rs.getDouble("productDiscount")
            );
        } catch(Exception e) {
            System.out.println("getProductsById(): " + e.getMessage());
        }
        return p;
    }

    public static HashMap<String, Product> getProductsAsHashMap() {
		HashMap<String,Product> hm = new HashMap<String,Product>();
		try {
			getConnection();
			Statement stmt=conn.createStatement();
			String selectCustomerQuery="select * from  productdetails";
			ResultSet rs = stmt.executeQuery(selectCustomerQuery);
			while(rs.next()) {	
                Product p = new Product(
                    rs.getString("Id"),
                    rs.getString("productName"),
                    rs.getDouble("productPrice"),
                    rs.getString("productImage"),
                    rs.getString("productManufacturer"),
                    rs.getString("productCondition"),
                    rs.getString("ProductType"),
                    rs.getDouble("productDiscount")
                );
				hm.put(rs.getString("productName"), p);
			}
		} catch(Exception e) {
            e.printStackTrace();	
		}
		return hm;			
	}
}