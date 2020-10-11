package com.mainsm.apmc.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SixMonthsPriceItem implements Serializable {

	@SerializedName("cities")
	private String cities;

	@SerializedName("month")
	private String month;

	@SerializedName("created")
	private long created;

	@SerializedName("___class")
	private String class_;

	@SerializedName("ownerId")
	private Object ownerId;

	@SerializedName("prices")
	private String prices;

	@SerializedName("updated")
	private Object updated;

	@SerializedName("objectId")
	private String objectId;

	public String getCities(){
		return cities;
	}

	public String getMonth(){
		return month;
	}

	public long getCreated(){
		return created;
	}

	public String getclass(){
		return class_;
	}

	public Object getOwnerId(){
		return ownerId;
	}

	public String getPrices(){
		return prices;
	}

	public Object getUpdated(){
		return updated;
	}

	public String getObjectId(){
		return objectId;
	}

	@Override
 	public String toString(){
		return 
			"SixMonthsPriceItem{" + 
			"cities = '" + cities + '\'' + 
			",month = '" + month + '\'' + 
			",created = '" + created + '\'' + 
			",___class = '" + class_ + '\'' +
			",ownerId = '" + ownerId + '\'' + 
			",prices = '" + prices + '\'' + 
			",updated = '" + updated + '\'' + 
			",objectId = '" + objectId + '\'' + 
			"}";
		}
}