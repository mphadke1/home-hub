import java.io.IOException;
import java.io.*;


/* 
	OrderPayment class contains class variables username,ordername,price,image,address,creditcardno.

	OrderPayment  class has a constructor with Arguments username,ordername,price,image,address,creditcardno
	  
	OrderPayment  class contains getters and setters for username,ordername,price,image,address,creditcardno
*/

public class OrderPayment implements Serializable{
	private int orderId;
	private String userName;
	private String orderName;
	private double orderPrice;
	private String userAddress;
	private String creditCardNo;
	private String orderDate;
	private String deliveryDate;
	private String maxOrderCancellationDate;
	private String pickupType;
	private String storeId;

	public OrderPayment(
		int orderId,
		String userName,
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

		this.orderId = orderId;
		this.storeId = storeId;
		this.userName = userName;
		this.orderDate = orderDate;
		this.orderName = orderName;
		this.pickupType = pickupType;
	 	this.orderPrice = orderPrice;
		this.userAddress = userAddress;
		this.creditCardNo = creditCardNo;
		this.deliveryDate = deliveryDate;
		this.maxOrderCancellationDate = maxOrderCancellationDate;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getMaxOrderCancellationDate() {
		return maxOrderCancellationDate;
	}

	public void setMaxOrderCancellationDate(String maxOrderCancellationDate) {
		this.maxOrderCancellationDate = maxOrderCancellationDate;
	}

	public String getPickupType() {
		return pickupType;
	}

	public void setPickupType(String pickupType) {
		this.pickupType = pickupType;
	}

	public String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	public String getCreditCardNo() {
		return creditCardNo;
	}

	public void setCreditCardNo(String creditCardNo) {
		this.creditCardNo = creditCardNo;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}


	public double getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(double orderPrice) {
		this.orderPrice = orderPrice;
	}
	

}
