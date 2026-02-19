package in.co.rays.proj4.bean;

import java.util.Date;

public class FollowupBean  extends BaseBean{
private String patient;
private String Doctor;
private Date dob;
private String fees;


public String getPatient() {
	return patient;
}
public void setPatient(String patient) {
	this.patient = patient;
}
public String getDoctor() {
	return Doctor;
}
public void setDoctor(String doctor) {
	Doctor = doctor;
}
public Date getDob() {
	return dob;
}
public void setDob(Date dob) {
	this.dob = dob;
}
public String getFees() {
	return fees;
}
public void setFees(String fees) {
	this.fees = fees;
}
@Override
public String getKey() {
	// TODO Auto-generated method stub
	return null;
}
@Override
public String getValue() {
	// TODO Auto-generated method stub
	return null;
}




	
}
