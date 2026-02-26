package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;



import in.co.rays.proj4.bean.LeaveBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;

import in.co.rays.proj4.model.LeaveModel;

public class TestLeave {
	
	public static void main(String[] args) throws Exception {
		//testAdd();
	//testDelete();
		//testUpdate() ;
		//testlist();
	testsearch() ;
//testFindByPk();
//testFindByName();
	}

	
	//add*****************************
	public static void testAdd() {
		
		try {
			LeaveBean bean=new LeaveBean();
			LeaveModel model=new LeaveModel();
		SimpleDateFormat sdf=new SimpleDateFormat("MM-dd-yyyy");
			bean.setLeaveCode("chinu");
			bean.setEmployeeName("Shruti");
			bean.setStartDate(sdf.parse("06-05-2004"));	
			bean.setEndDate(sdf.parse("10-06-2004"));	
			bean.setLeaveStatus("null");
			
			
			long pk=model.add(bean);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		}
	//delete
	
	public  static void testDelete() {
		try {
			LeaveBean  bean= new LeaveBean();
			LeaveModel model=new LeaveModel();
			bean.setId(2);
			model.delete(bean);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	//update
	
	public static void testUpdate() {
		try {
			LeaveBean bean=new LeaveBean();
			LeaveModel model=new LeaveModel();
			SimpleDateFormat sdf=new SimpleDateFormat("MM-dd-yyyy");
			bean.setLeaveCode("ttytyyhu");
			bean.setEmployeeName("Varun");
			bean.setStartDate(sdf.parse("06-05-2004"));	
			bean.setEndDate(sdf.parse("10-06-2004"));	
			bean.setLeaveStatus("Hwelloo");
			bean.setId(3);
			
			model.update(bean);
		
		
		
			 
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	//find by pk*****************************
	public static void testFindByPk() {

	    LeaveModel model = new LeaveModel();
	    LeaveBean bean = model.findByPk(1);

	    if (bean == null) {
	        System.out.println("test findByPk fail");
	    } else {
	        System.out.println(bean.getId());
	        System.out.println(bean.getLeaveCode());
	        System.out.println(bean.getEmployeeName());
	        System.out.println(bean.getStartDate());
	        System.out.println(bean.getEndDate());
	        System.out.println(bean.getLeaveStatus());
	    }
	}
	//name**********************************************
	
	public static void testFindByName() {
		LeaveBean bean=new LeaveBean();
		LeaveModel model= new  LeaveModel();
		bean=model.findByCode("ttytyyhu");
		if(bean==null) {
			System.out.println("test findBy Name fail");
		}
		
		else {
		    System.out.println(bean.getId());
		    System.out.println(bean.getLeaveCode());
		    System.out.println(bean.getEmployeeName());
		    System.out.println(bean.getStartDate());
		    System.out.println(bean.getEndDate());
		    System.out.println(bean.getLeaveStatus());
		}
	}
	//list/*****************************
	 public static void testlist() throws Exception { LeaveBean bean = new LeaveBean();
	 LeaveModel model=new LeaveModel();
	  List list = new ArrayList();
	  list =model.list(0,0);
  if(list.size() < 0) { 
	  System.out.println("test list fail");
	  } 
  Iterator it=list.iterator();
  while(it.hasNext()) {
	  bean=(LeaveBean) it.next();
	  System.out.println(bean.getId());
	    System.out.println(bean.getLeaveCode());
	    System.out.println(bean.getEmployeeName());
	    System.out.println(bean.getStartDate());
	    System.out.println(bean.getEndDate());
	    System.out.println(bean.getLeaveStatus());

  
  } 
	  }
	 
		public static void testsearch() throws DatabaseException {
			LeaveBean bean = new LeaveBean();
							LeaveModel model=new LeaveModel();
							List list = new ArrayList();
							list=model.search(bean, 0, 0);
							bean.setEmployeeName("S");
							
							Iterator it=list.iterator();
							while(it.hasNext()) {
								bean = (LeaveBean) it.next();
								 System.out.println(bean.getId());
								    System.out.println(bean.getLeaveCode());
								    System.out.println(bean.getEmployeeName());
								    System.out.println(bean.getStartDate());
								    System.out.println(bean.getEndDate());
								    System.out.println(bean.getLeaveStatus());
			
							  
							}
		}
	}