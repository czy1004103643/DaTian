package cn.edu.bjtu.vo;

// Generated 2015-2-24 14:26:59 by Hibernate Tools 3.4.0.CR1

import java.util.Date;

import org.springframework.stereotype.Component;

/**
 * Assessform generated by hbm2java
 */
@Component
public class Comment implements java.io.Serializable {

	private String id;
	private String clientId;
	private Date relDate;
	private String orderId;
	private String orderNum;//add by RussWest0 at 2015年6月25日,下午10:30:27 
	private String serviceAttitude;
	private String transportEfficiency;
	private String cargoSafety;
	private String totalMoney;
	private String comment;
	// add by RussWest0 at 2015年5月30日,上午9:42:38 
	private String linetransportId;
	private String citylineId;
	private String carId;
	private String warehouseId;
	
	private String carrierId;//add by haochendong 

	public Comment() {
	}
	
	

	public String getOrderNum() {
		return orderNum;
	}



	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}



	public String getCarrierId() {
		return carrierId;
	}

	public void setCarrierId(String carrierId) {
		this.carrierId = carrierId;
	}



	public Comment(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClientId() {
		return this.clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public Date getRelDate() {
		return this.relDate;
	}

	public void setRelDate(Date relDate) {
		this.relDate = relDate;
	}

	public String getOrderId() {
		return this.orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	

	public String getServiceAttitude() {
		return serviceAttitude;
	}



	public void setServiceAttitude(String serviceAttitude) {
		this.serviceAttitude = serviceAttitude;
	}



	public String getTransportEfficiency() {
		return transportEfficiency;
	}



	public void setTransportEfficiency(String transportEfficiency) {
		this.transportEfficiency = transportEfficiency;
	}



	public String getCargoSafety() {
		return cargoSafety;
	}



	public void setCargoSafety(String cargoSafety) {
		this.cargoSafety = cargoSafety;
	}



	public String getTotalMoney() {
		return totalMoney;
	}



	public void setTotalMoney(String totalMoney) {
		this.totalMoney = totalMoney;
	}



	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}



	public String getLinetransportId() {
		return linetransportId;
	}



	public void setLinetransportId(String linetransportId) {
		this.linetransportId = linetransportId;
	}



	public String getCitylineId() {
		return citylineId;
	}



	public void setCitylineId(String citylineId) {
		this.citylineId = citylineId;
	}



	public String getCarId() {
		return carId;
	}



	public void setCarId(String carId) {
		this.carId = carId;
	}



	public String getWarehouseId() {
		return warehouseId;
	}



	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}

	

	

}
