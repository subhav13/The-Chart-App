package com.mainsm.apmc.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class APMCResponse{

	@SerializedName("cities")
	private List<CitiesItem> cities;

	@SerializedName("created")
	private long created;

	@SerializedName("name")
	private String name;

	@SerializedName("___class")
	private String class_;

	@SerializedName("ownerId")
	private Object ownerId;

	@SerializedName("updated")
	private long updated;

	@SerializedName("objectId")
	private String objectId;

	public List<CitiesItem> getCities(){
		return cities;
	}

	public long getCreated(){
		return created;
	}

	public String getName(){
		return name;
	}

	public String getclass(){
		return class_;
	}

	public Object getOwnerId(){
		return ownerId;
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
			"APMCResponse{" + 
			"cities = '" + cities + '\'' + 
			",created = '" + created + '\'' + 
			",name = '" + name + '\'' + 
			",___class = '" + class_ + '\'' +
			",ownerId = '" + ownerId + '\'' + 
			",updated = '" + updated + '\'' + 
			",objectId = '" + objectId + '\'' + 
			"}";
		}
}