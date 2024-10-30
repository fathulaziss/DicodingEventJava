package com.example.dicodingeventjava.data.response;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class EventResponse{

	@SerializedName("listEvents")
	private List<ListEventsItem> listEvents;

	@SerializedName("error")
	private boolean error;

	@SerializedName("message")
	private String message;

	public void setListEvents(List<ListEventsItem> listEvents){
		this.listEvents = listEvents;
	}

	public List<ListEventsItem> getListEvents(){
		return listEvents;
	}

	public void setError(boolean error){
		this.error = error;
	}

	public boolean isError(){
		return error;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	@Override
 	public String toString(){
		return 
			"EventResponse{" + 
			"listEvents = '" + listEvents + '\'' + 
			",error = '" + error + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}