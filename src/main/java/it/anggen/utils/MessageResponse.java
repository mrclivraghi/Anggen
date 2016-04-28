package it.anggen.utils;

public class MessageResponse {
	private String message;
	
	private Boolean authenticated=false;
	
	public MessageResponse(String msg, Boolean authenticated)
	{
		this.setMessage(msg);
		this.setAuthenticated(authenticated);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Boolean getAuthenticated() {
		return authenticated;
	}

	public void setAuthenticated(Boolean authenticated) {
		this.authenticated = authenticated;
	}
}
