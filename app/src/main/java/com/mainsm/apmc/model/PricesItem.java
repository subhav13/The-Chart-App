package com.mainsm.apmc.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PricesItem implements Serializable {

	@SerializedName("cities")
	private String cities;

	@SerializedName("month")
	private String month;

	@SerializedName("price")
	private String price;

	@SerializedName("created")
	private long created;

	@SerializedName("___class")
	private String class_;

	@SerializedName("ownerId")
	private Object ownerId;

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

	public String getPrice(){
		return price;
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

	public Object getUpdated(){
		return updated;
	}

	public String getObjectId(){
		return objectId;
	}

	@Override
 	public String toString(){
		return 
			"PricesItem{" + 
			"cities = '" + cities + '\'' + 
			",month = '" + month + '\'' + 
			",price = '" + price + '\'' + 
			",created = '" + created + '\'' + 
			",___class = '" + class_ + '\'' +
			",ownerId = '" + ownerId + '\'' + 
			",updated = '" + updated + '\'' + 
			",objectId = '" + objectId + '\'' + 
			"}";
		}
}