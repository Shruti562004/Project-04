package in.co.rays.proj4.bean;

import java.util.Date;

public class AnnounceBean extends BaseBean{
private String code;
private String title;
private String description;
private Date publishDate;
private String status;
public String getCode() {
	return code;
}
public void setCode(String code) {
	this.code = code;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
public Date getPublishDate() {
	return publishDate;
}
public void setPublishDate(Date publishDate) {
	this.publishDate = publishDate;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public String getKey() {
	// TODO Auto-generated method stub
	return id+"";
}
public String getValue() {
	// TODO Auto-generated method stub
	 return status;
}



}
