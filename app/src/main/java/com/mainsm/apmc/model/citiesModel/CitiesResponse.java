package com.mainsm.apmc.model.citiesModel;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;
import com.mainsm.apmc.model.PricesItem;

public class CitiesResponse implements Serializable {

	@SerializedName("created")
	private long created;

	@SerializedName("name")
	private String name;

	@SerializedName("___class")
	private String class_;

	@SerializedName("thisMonthPrices")
	private List<com.mainsm.apmc.model.citiesModel.ThisMonthPricesItem> thisMonthPrices;

	@SerializedName("ownerId")
	private Object ownerId;

	@SerializedName("sixMonthsPrice")
	private List<com.mainsm.apmc.model.citiesModel.SixMonthsPriceItem> sixMonthsPrice;

	@SerializedName("prices")
	private List<com.mainsm.apmc.model.PricesItem> prices;

	@SerializedName("updated")
	private Object updated;

	@SerializedName("objectId")
	private String objectId;

	public long getCreated(){
		return created;
	}

	public String getName(){
		return name;
	}

	public String getclass(){
		return class_;
	}

	public List<ThisMonthPricesItem> getThisMonthPrices(){
		return thisMonthPrices;
	}

	public Object getOwnerId(){
		return ownerId;
	}

	public List<SixMonthsPriceItem> getSixMonthsPrice(){
		return sixMonthsPrice;
	}

	public List<PricesItem> getPrices(){
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
				"CitiesItem{" +
						"created = '" + created + '\'' +
						",name = '" + name + '\'' +
						",___class = '" + class_ + '\'' +
						",thisMonthPrices = '" + thisMonthPrices + '\'' +
						",ownerId = '" + ownerId + '\'' +
						",sixMonthsPrice = '" + sixMonthsPrice + '\'' +
						",prices = '" + prices + '\'' +
						",updated = '" + updated + '\'' +
						",objectId = '" + objectId + '\'' +
						"}";
	}
}