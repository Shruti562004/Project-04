package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


import in.co.rays.proj4.bean.CourseBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.CourseModel;

public class TestCourseModel {
	
	public static void main(String[] args) throws Exception {
		//testAdd();
	//testDelete();
		//testUpdate() ;
		//testlist();
	//testsearch() ;
//testFindByPk();
testFindByName();
	}

	
	//add*****************************
	public static void testAdd() {
		
		try {
			CourseBean bean=new CourseBean();
			CourseModel model=new CourseModel();
		
			bean.setName("BTech");
			bean.setDuration("2 Year");
			bean.setDescription("Course");	
			bean.setCreatedBy("student");
			bean.setModifiedBy("student");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			   
		    bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			
			
			long pk=model.add(bean);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		}
	//delete
	
	public  static void testDelete() {
		try {
			CourseBean bean= new CourseBean();
			CourseModel model=new CourseModel();
			bean.setId(3);
			model.delete(bean);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	//update
	
	public static void testUpdate() {
		try {
			CourseBean bean=new CourseBean();
			CourseModel model=new CourseModel();
			bean.setId(4);
			bean.setName("Btech");
			bean.setDuration("3 Year");
			bean.setDescription("Science");
			
			bean.setCreatedBy("admin");
			bean.setModifiedBy("admin");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			   
		    bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			model.update(bean);
		
		
		
			 
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	//find by pk*****************************
	public static void testFindByPk() {
		try {
			CourseBean bean=new CourseBean();
			CourseModel model=new CourseModel();
			
			bean=model.FindByPK(1);
			if(bean==null) {
				System.out.println("test findbypk fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getDuration());
			System.out.println(bean.getDescription());
			
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getCreatedDatetime());
			System.out.println(bean.getModifiedDatetime());
		}catch(ApplicationException e) {
			e.printStackTrace();
		}
	}
	//name**********************************************
	
	public static void testFindByName() {
		try {
			CourseBean bean=new CourseBean();
			CourseModel model= new  CourseModel();
			bean=model.findByName("BSC");
			if(bean==null) {
				System.out.println("test findBy Name fail");
			}
		
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getDuration());
			System.out.println(bean.getDescription());
		
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getCreatedDatetime());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getModifiedDatetime());
			
		}catch(ApplicationException e) {
			e.printStackTrace();
		}
	}
	//list/*****************************
	 public static void testlist() throws Exception { try { 
		  CourseBean bean = new CourseBean();
		  CourseModel model=new CourseModel();
		  List list = new ArrayList();
		  list =model.list(0,0);
	  if(list.size() < 0) { 
		  System.out.println("test list fail");
		  } 
	  Iterator it=list.iterator();
	  while(it.hasNext()) {
		  bean=(CourseBean) it.next();
	  System.out.println(bean.getName());
	  System.out.println(bean.getDescription());
	  System.out.println(bean.getDuration());
	  System.out.println(bean.getCreatedBy());
	  System.out.println(bean.getModifiedBy());
	  System.out.println(bean.getCreatedDatetime());
	  System.out.println(bean.getModifiedDatetime());
	  
	  }
	  
	  }catch(ApplicationException e) {
		  e.printStackTrace(); 
		  } 
	  }
	 
		public static void testsearch() throws DatabaseException {
			try {
				CourseBean bean = new CourseBean();
				CourseModel model=new CourseModel();
				List list = new ArrayList();
				list=model.search(bean);
				bean.setName("b");
				
				Iterator it=list.iterator();
				while(it.hasNext()) {
					bean = (CourseBean) it.next();
					System.out.println(bean.getId());
					System.out.println(bean.getName());
					System.out.println(bean.getDescription());
					System.out.println(bean.getDuration());
					  System.out.println(bean.getCreatedBy());
					  System.out.println(bean.getModifiedBy());
					  System.out.println(bean.getCreatedDatetime());
					  System.out.println(bean.getModifiedDatetime());
					
				}
			}catch(ApplicationException e) {
				e.printStackTrace();
			}
		}
	}