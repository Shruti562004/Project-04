package in.co.rays.proj4.test;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.RoleBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.RoleModel;

public class TestRoleModel {

	public static RoleModel model = new RoleModel();

	public static void main(String[] args) throws SQLException, ApplicationException, DuplicateRecordException {
		//testAdd();
		//testDelete();
		//testUpdate();
		//testFindByPK();
		
	
		//testSearch();
	//testFindByName();
		//testList();
	}
	
	//add************************

	public static void testAdd() throws ApplicationException, DuplicateRecordException, SQLException {

		RoleBean bean = new RoleBean();
		bean.setName("faculty");
		bean.setDescription("faculty");
		bean.setCreatedBy("root");
		bean.setModifiedBy("root");
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));

		long pk= model.add(bean);
		RoleBean addedbean=model.findByPk(pk);
               if(addedbean==null){
		System.out.println("Test Add fail");
	}
	}
	
	//delete***************************************
	public static void testDelete() throws SQLException, ApplicationException {
		RoleBean bean=new RoleBean();
		bean.setId(6);
	model.delete(bean);
	
	System.out.println("record deleted");
	}
	
	//update**************************************
	public static void testUpdate() throws SQLException {
		try {
		RoleBean bean=new RoleBean();
 
		bean.setName("faculty");
		bean.setDescription("faculty");
		bean.setCreatedBy("root");
		bean.setModifiedBy("root");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		   
	    bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		bean.setId(5l);
		try {
			model.update(bean);
		} catch (SQLException | DuplicateRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("record update");
		}
		catch(ApplicationException e){
			e.printStackTrace();
		}
		
	}
	//findbyPk*****************************
	public static void testFindByPK() throws SQLException, ApplicationException {
		try{
			RoleBean bean=new RoleBean();
	
		bean=model.findByPk(1);
		if(bean==null) {
			System.out.println("Test Find By PK fill");
			
		}
		
		System.out.println(bean.getId());
		System.out.println(bean.getName());
		System.out.println(bean.getDescription());
		System.out.println(bean.getCreatedBy());
		System.out.println(bean.getModifiedBy());
		System.out.println(bean.getCreatedDatetime());
		System.out.println(bean.getModifiedDatetime());
		
	}
	catch(ApplicationException e){
		e.printStackTrace();
	}
}
	//name***********************************************
	public static void testFindByName(){
		try{
			RoleBean bean=new RoleBean();
			bean=model.findByName("admin");
			if(bean==null){
				System.out.println("Test Find By Name fill");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getDescription());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getCreatedDatetime());
			System.out.println(bean.getModifiedDatetime());
		}catch(ApplicationException e){
			e.printStackTrace();
		}
	}
	
	//filter data**************************
	public static void testSearch() {
		try {
			RoleBean bean=new RoleBean();
			List list=new ArrayList();
			bean.setName("admin");
			//bean.setName("shruti");
			list=model.search(bean,0,0);
			if(list.size()<0) {
				System.out.println("test search fill");
			}
			Iterator it=list.iterator();
			
			while(it.hasNext()) {
				bean=(RoleBean)it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getName());
				System.out.println(bean.getDescription());
				System.out.println(bean.getCreatedBy());
				System.out.println(bean.getModifiedBy());
				System.out.println(bean.getCreatedDatetime());
				System.out.println(bean.getModifiedDatetime());
				
			}
			
			}
		catch(ApplicationException e){
			e.printStackTrace();
		}
	}
	//list all data*/////////////////////
	public static void testList(){
		try{
			
			RoleBean bean=new RoleBean();
			List list=new ArrayList();
			list=model.list(1,10);
			if(list.size() >0){
				System.out.println("test List faill");
			}
			Iterator it=list.iterator();
			while(it.hasNext()){
				bean=(RoleBean)it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getName());
				System.out.println(bean.getDescription());
				System.out.println(bean.getCreatedBy());
				System.out.println(bean.getModifiedBy());
				System.out.println(bean.getCreatedDatetime());
				System.out.println(bean.getModifiedDatetime());
			}
		}catch(ApplicationException e){
			e.printStackTrace();
		}
	}

	
	
}

