package in.co.rays.proj4.bean;

import java.util.Date;

public class LeaveBean {
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getLeaveCode() {
		return leaveCode;
	}
	public void setLeaveCode(String leaveCode) {
		this.leaveCode = leaveCode;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getLeaveStatus() {
		return leaveStatus;
	}
	public void setLeaveStatus(String leaveStatus) {
		this.leaveStatus = leaveStatus;
	}
	private long id;
	private String leaveCode;
	private String employeeName;
	private Date startDate;
	private Date endDate;
	private String leaveStatus;

	
	

}
