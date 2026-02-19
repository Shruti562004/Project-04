package in.co.rays.proj4.bean;

import java.util.Date;

public class EventBean extends BaseBean {
	
	private String eventCode;
	private String eventName;
	private Date eventDate;
	public String eventLocation;
	
	public String getEventCode() {
		return eventCode;
	}
	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public Date getEventDate() {
		return eventDate;
	}
	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}
	public String getEventLocation() {
		return eventLocation;
	}
	public void setEventLocation(String eventLocation) {
		this.eventLocation = eventLocation;
	}
	public String getKey() {
		return id + "";
	}

	@Override
	public String getValue() {
		return eventName;
	}

}
