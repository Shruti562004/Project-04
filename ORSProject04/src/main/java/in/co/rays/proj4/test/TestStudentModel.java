package in.co.rays.proj4.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.StudentBean;
import in.co.rays.proj4.bean.UserBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.StudentModel;

public class TestStudentModel {

	public static void main(String[] args) throws ParseException, ApplicationException {
		
	testAdd();
	//	testDelete();
	// testUpdate();
		//testFindByPk();
		// list();
	 //testSearch();
		//testFindByEmailId();
	}
	
	
	//add*********************
	public static void testAdd() throws ParseException, ApplicationException {
	try {	
		StudentBean bean=new StudentBean();
		StudentModel model=new StudentModel();
		SimpleDateFormat sdf=new SimpleDateFormat("MM-dd-yy");
		bean.setFirstName("shruti");
		bean.setLastName("Rathore");
		bean.setDob(sdf.parse("05-06-2004"));
		bean.setGender("Female");
		bean.setMobileNo("6686843451");
		bean.setEmail("shruti@gmail.com");
		bean.setCollegeId(1);
		bean.setCollegeName("sage");
		bean.setCreatedBy("root");
		bean.setModifiedBy("root");
		bean.setCreatedDatetime(null);
		bean.setModifiedDatetime(null);
		
		long pk=model.add(bean);
		
	}
	catch(Exception e) {
		e.printStackTrace();
	}
	}
	
	//delete***************************
	public static void testDelete()  {
	try {	
	
StudentBean bean=new StudentBean();

StudentModel model=new StudentModel();

bean.setId(2);
model.delete(bean);
	}
	catch(ApplicationException e){
		e.printStackTrace();
	}
	}
	
//updating***************************
	
	public static void testUpdate() {
		try {
		
		StudentBean bean=new StudentBean();
		StudentModel model=new StudentModel();
		bean.setId(2);
		bean.setCollegeName("IPS");
		bean.setEmail("surbhi9@gmail.com");
	//	bean.setCollegeId(1);

		try {
			model.update(bean);
		} catch (DuplicateRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}	
		catch(ApplicationException e){
			e.printStackTrace();
		}
	}
	
	//find by pk****************************
	public static void testFindByPk() {
	    try {
	        StudentModel model = new StudentModel();
	        
	        // Find student by primary key
	        StudentBean bean = model.findByPk(1);

	        if (bean == null) {
	            System.out.println("Test Find By PK failed: No record found");
	       
	        }

	        // Print student details
	        System.out.println("First Name: " + bean.getFirstName());
	        System.out.println("Last Name: " + bean.getLastName());
	        System.out.println("DOB: " + bean.getDob());
	       
	        System.out.println("Gender: " + bean.getGender());
	        System.out.println("Mobile No: " + bean.getMobileNo());
	        System.out.println("Email: " + bean.getEmail());
	        System.out.println("College Name: " + bean.getCollegeName());
	        System.out.println("College ID: " + bean.getCollegeId());
	        System.out.println("Created By: " + bean.getCreatedBy());
	        System.out.println("Modified By: " + bean.getModifiedBy());
	        System.out.println("Created Datetime: " + bean.getCreatedDatetime());
	        System.out.println("Modified Datetime: " + bean.getModifiedDatetime());

	    } catch (ApplicationException e) {
	        e.printStackTrace();
	    }
	}
	//email**********************
	 public static void testFindByEmailId() {
	        try {
	            StudentBean bean = new StudentBean();
	            StudentModel model=new StudentModel();
	            bean = model.findByEmailId("shruti@gmail.com");
	            if (bean == null) {
	                System.out.println("Test Find By EmailId fail");
	            }
	            System.out.println("ID: " + bean.getId());
		        System.out.println("First Name: " + bean.getFirstName());
		        System.out.println("Last Name: " + bean.getLastName());
		        System.out.println("DOB: " + bean.getDob());
		       
		        System.out.println("Gender: " + bean.getGender());
		        System.out.println("Mobile No: " + bean.getMobileNo());
		        System.out.println("Email: " + bean.getEmail());
		        System.out.println("College Name: " + bean.getCollegeName());
		        System.out.println("College ID: " + bean.getCollegeId());
		        System.out.println("Created By: " + bean.getCreatedBy());
		        System.out.println("Modified By: " + bean.getModifiedBy());
		        System.out.println("Created Datetime: " + bean.getCreatedDatetime());
		        System.out.println("Modified Datetime: " + bean.getModifiedDatetime());
	        } catch (ApplicationException e) {
	            e.printStackTrace();
	        }
	    }

	//list****************************************************
	
	  public static void list() throws ApplicationException { 
		  try{ 
			  StudentBean  bean=new StudentBean();
	  
	  StudentModel model=new StudentModel();
	  List list=new ArrayList();
	  list=model.list(1, 1); 
	  if(list.size()==0) {
	 System.out.println("test list fail"); }
	 Iterator it=list.iterator();
	 while(it.hasNext()) {
		 bean=(StudentBean) it.next();
		 System.out.println(bean.getId());
	 System.out.println(bean.getFirstName());
	  System.out.println(bean.getLastName());
	  System.out.println(bean.getDob());
	  System.out.println(bean.getMobileNo());
	  System.out.println(bean.getEmail());
	  System.out.println(bean.getCollegeId());
	  System.out.println(bean.getCreatedBy());
	  System.out.println(bean.getModifiedBy());
	  System.out.println(bean.getCreatedDatetime());
	  System.out.println(bean.getModifiedDatetime());
	  } 
	 }
	  catch(ApplicationException e){ e.printStackTrace(); } }
	 
	//search**************************
	
	public static void testSearch(){
		 try{
			 StudentBean bean= new StudentBean();
			 StudentModel model=new StudentModel();

			 List list=new ArrayList();
		
			 bean.setFirstName("Shruti");
			// bean.setEmail("kmalviya30@gmail.com");
			// bean.setCollegeName("RML Maheshwari");
			 
			 list = model.search(bean,0,0);
			 if(list.size() < 0){
				 System.out.println("Test search fail");
			 }
			 Iterator it=list.iterator();
			 while(it.hasNext()){
				 bean=(StudentBean)it.next();
				 System.out.println(bean.getId());
				 System.out.println(bean.getFirstName());
				 System.out.println(bean.getLastName());
				 System.out.println(bean.getDob());
				 System.out.println(bean.getMobileNo());
				 System.out.println(bean.getEmail());
				 System.out.println(bean.getCollegeId()); 
				 
			 }
		 }catch(ApplicationException e){
			 e.printStackTrace();
		 }
	 }
}
