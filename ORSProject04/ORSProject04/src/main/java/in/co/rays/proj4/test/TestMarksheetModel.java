package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.CollegeBean;
import in.co.rays.proj4.bean.FacultyBean;
import in.co.rays.proj4.bean.MarksheetBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.CollegeModel;
import in.co.rays.proj4.model.FacultyModel;
import in.co.rays.proj4.model.MarksheetModel;

public class TestMarksheetModel {

	
	public static void main(String[] args) throws ApplicationException {
		
		//testAdd();
		//testDelete();
		//testUpdate();
		//testList();
		//testSearch();
		//testFindByPK();
		//testFindByRollNo();
		testMeritList();
	}
	
	//add
	
	public static void testAdd() throws ApplicationException {
	try {	MarksheetBean bean=new MarksheetBean();
		MarksheetModel model=new MarksheetModel();
		
		bean.setRollNo("r2");
		bean.setStudentId(2);
		bean.setName("Sandeep");
		bean.setPhysics(67);
		bean.setChemistry(40);
		bean.setMaths(13);
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
	bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
	bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		
		long pk=model.add(bean);
		//verify
	}
	catch (Exception e) {
		e.printStackTrace();
	}
		
		
	}
	//delete
	public static void testDelete() {
		try {
	MarksheetBean	 bean=new MarksheetBean();
	MarksheetModel model=new MarksheetModel();
	bean.setId(4);
	model.delete(bean);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//update
	public static void testUpdate() {
		try {

			MarksheetModel model = new MarksheetModel();
		MarksheetBean bean = new MarksheetBean();
		
		bean.setId(3);
		bean.setRollNo("r1");
		bean.setStudentId(2);
		bean.setName("Varun");
		bean.setPhysics(67);
		bean.setChemistry(40);
		bean.setMaths(13);

		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		
			model.update(bean);
			System.out.println(" updated successfully");
		} 
		catch (Exception e) {

			e.printStackTrace();
		}
	}
	//find by pk
	public static void testFindByPK() {
		try {
			MarksheetBean bean = new MarksheetBean();
			MarksheetModel model=new MarksheetModel();
			
			bean = model.findByPK(1l);
			if (bean == null) {
				System.out.println("Find By pk fail");
			}
			 System.out.println(bean.getId());
	       
	            System.out.println(bean.getRollNo());
	            System.out.println(bean.getStudentId());
	            System.out.println(bean.getName());

	        
	            System.out.println(bean.getPhysics());
	            System.out.println(bean.getChemistry());
	            System.out.println(bean.getMaths());
	           
	            System.out.println(bean.getCreatedBy());
	            System.out.println(bean.getModifiedBy());
	            System.out.println(bean.getCreatedDatetime());
	            System.out.println(bean.getModifiedDatetime());

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
//roll no***************************************
	public static void testFindByRollNo() {
		try {
			MarksheetBean bean = new MarksheetBean();
			MarksheetModel model=new MarksheetModel();
			
			bean = model.findByRollNo("r2");
			if (bean == null) {
				System.out.println("Find By roll no fail");
			}
			 System.out.println(bean.getId());
	       
	            System.out.println(bean.getRollNo());
	            System.out.println(bean.getStudentId());
	            System.out.println(bean.getName());

	        
	            System.out.println(bean.getPhysics());
	            System.out.println(bean.getChemistry());
	            System.out.println(bean.getMaths());
	           
	            System.out.println(bean.getCreatedBy());
	            System.out.println(bean.getModifiedBy());
	            System.out.println(bean.getCreatedDatetime());
	            System.out.println(bean.getModifiedDatetime());

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
	//list*********************************
	
	public static void testList() {
		try {
			MarksheetBean bean = new MarksheetBean();
			MarksheetModel model=new MarksheetModel();
			List list = new ArrayList();
			list = model.list(1, 6);
			if (list.size() < 0) {
				System.out.println("Test List fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (MarksheetBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getRollNo());
				System.out.println(bean.getName());
				System.out.println(bean.getPhysics());
				System.out.println(bean.getChemistry());
				System.out.println(bean.getMaths());
				System.out.println(bean.getCreatedBy());
				System.out.println(bean.getModifiedBy());
				System.out.println(bean.getCreatedDatetime());
				System.out.println(bean.getModifiedDatetime());
			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
	
	public static void testSearch() {
		try {
			MarksheetBean bean = new MarksheetBean();
			MarksheetModel model=new MarksheetModel();
			List list = new ArrayList();
			bean.setName("s");
			//bean.setId(9L);
			list = model.search(bean, 1, 10);
			if (list.size() < 0) {
				System.out.println("Test search fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (MarksheetBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getRollNo());
				System.out.println(bean.getName());
				System.out.println(bean.getPhysics());
				System.out.println(bean.getChemistry());
				System.out.println(bean.getMaths());
				System.out.println(bean.getCreatedBy());
				System.out.println(bean.getModifiedBy());
				System.out.println(bean.getCreatedDatetime());
				System.out.println(bean.getModifiedDatetime());
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
//meritlist
	public static void testMeritList() {
		try {
			MarksheetBean bean = new MarksheetBean();
			MarksheetModel model=new MarksheetModel();
			List list = new ArrayList();
			list = model.list(1, 5);
			if (list.size() > 0) {
				System.out.println("Test List fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (MarksheetBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getRollNo());
				System.out.println(bean.getName());
				System.out.println(bean.getPhysics());
				System.out.println(bean.getChemistry());
				System.out.println(bean.getMaths());
				System.out.println(bean.getCreatedBy());
				System.out.println(bean.getModifiedBy());
				System.out.println(bean.getCreatedDatetime());
				System.out.println(bean.getModifiedDatetime());
			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	
}
