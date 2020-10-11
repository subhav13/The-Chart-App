package com.mainsm.apmc.model.citiesModel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ThisMonthPricesItem implements Serializable {

	@SerializedName("cities")
	private String cities;

	@SerializedName("price")
	private String price;

	@SerializedName("created")
	private long created;

	@SerializedName("___class")
	private String class_;

	@SerializedName("ownerId")
	private Object ownerId;

	@SerializedName("day")
	private String day;

	@SerializedName("updated")
	private long updated;

	@SerializedName("objectId")
	private String objectId;

	public String getCities(){
		return cities;
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

	public String getDay(){
		return day;
	}

	public long getUpdated(){
		return updated;
	}

	public String getObjectId(){
		return objectId;
	}

	@Override
 	public String toString(){
		return 
			"ThisMonthPricesItem{" + 
			"cities = '" + cities + '\'' + 
			",price = '" + price + '\'' + 
			",created = '" + created + '\'' + 
			",___class = '" + class_ + '\'' +
			",ownerId = '" + ownerId + '\'' + 
			",day = '" + day + '\'' + 
			",updated = '" + updated + '\'' + 
			",objectId = '" + objectId + '\'' + 
			"}";
		}
}