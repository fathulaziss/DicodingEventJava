package com.example.dicodingeventjava.data.response;

public class DetailEventResponse{
	private boolean error;
	private String message;
	private Event event;

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

	public void setEvent(Event event){
		this.event = event;
	}

	public Event getEvent(){
		return event;
	}

	@Override
 	public String toString(){
		return 
			"DetailEventResponse{" + 
			"error = '" + error + '\'' + 
			",message = '" + message + '\'' + 
			",event = '" + event + '\'' + 
			"}";
		}
}
