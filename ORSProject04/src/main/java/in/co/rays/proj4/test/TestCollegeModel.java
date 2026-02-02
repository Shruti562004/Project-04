package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.CollegeBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.CollegeModel;



public class TestCollegeModel {
public static void main(String[] args) {
	//testAdd();
	//testDelete();
	//testUpdate();
	//testFindByName();
	//testFindByPk();
	//testSearch();
	//list();
}

//add***********************************************
public static void testAdd() {
	try {
		CollegeBean bean = new CollegeBean();
		// bean.setId(2L);
		bean.setName("Medicap");
		bean.setAddress("Indore");
		bean.setState("mp");
		bean.setCity("Khargone");
		bean.setPhoneNo("767856545465");
		bean.setCreatedBy("Admin");
		bean.setModifiedBy("Admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		
		CollegeModel model = new CollegeModel();
		try {
			long pk = model.add(bean);
		} catch (DuplicateRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Test Add succ");
		
	} catch (ApplicationException e) {
		e.printStackTrace();
	}
}


//delete******************************

public static void testDelete() {

	CollegeBean bean = new CollegeBean();
	bean.setId(2);

	CollegeModel model = new CollegeModel();

	try {
		model.delete(bean);
		System.out.println("College deleted Successfully");
	} catch (ApplicationException e) {
		e.printStackTrace();
	}
}
//update---************************************************
public static void testUpdate() {

	CollegeBean bean = new CollegeBean();
	bean.setId(1);
	bean.setName("Sage University");
	bean.setAddress("noida");
	bean.setState("Uttar Pradesh");
	bean.setCity("noida");
	bean.setPhoneNo("786659789");
	bean.setCreatedBy("admin");
	bean.setModifiedBy("admin");
	bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
	bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

	CollegeModel model = new CollegeModel();
	try {
		model.update(bean);
		System.out.println("College updated successfully");
	} catch (Exception e) {

		e.printStackTrace();
	}
}
//name************************************

public static void testFindByName() {

	CollegeModel model = new CollegeModel();
	try {
		CollegeBean bean = model.findByName("Medicap");

		if (bean == null) {
			System.out.println("Test Find by name fail");

		}
		System.out.println(bean.getName());
		System.out.println(bean.getAddress());
		System.out.println(bean.getState());
		System.out.println(bean.getCity());
		System.out.println(bean.getPhoneNo());
		System.out.println(bean.getCreatedBy());
		System.out.println(bean.getModifiedBy());
		System.out.println(bean.getCreatedDatetime());
		System.out.println(bean.getModifiedDatetime());

	} catch (ApplicationException e) {

		e.printStackTrace();
	}
}
//pk*******************************************
public static void testFindByPk() {

	CollegeModel model = new CollegeModel();
	try {
		CollegeBean bean = model.findByPk(1);

		if (bean == null) {
			System.out.println("Test Find by pk fail");

		}
		System.out.println(bean.getName());
		System.out.println(bean.getAddress());
		System.out.println(bean.getState());
		System.out.println(bean.getCity());
		System.out.println(bean.getPhoneNo());
		System.out.println(bean.getCreatedBy());
		System.out.println(bean.getModifiedBy());
		System.out.println(bean.getCreatedDatetime());
		System.out.println(bean.getModifiedDatetime());

	} catch (ApplicationException e) {

		e.printStackTrace();
	}
}

//filter**********************
public static void testSearch() {
	try {
		CollegeModel model = new CollegeModel();
		CollegeBean bean = new CollegeBean();
		List list = new ArrayList();
		bean.setCity("noida");
		list = model.search(bean, 0, 0);
		if (list.size() < 0) {
			System.out.println("Test Search fail");
		}
		Iterator it = list.iterator();
		while (it.hasNext()) {
			bean = (CollegeBean) it.next();
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getPhoneNo());
			System.out.println(bean.getState());
		}
	} catch (ApplicationException e) {
		e.printStackTrace();
	}
}

//all data***************************

private static void list() {
	try {
		CollegeBean bean = new CollegeBean();
		List list = new ArrayList();
		CollegeModel  model = new CollegeModel();
		list = model.list(1,1);
		if (list.size() < 0) {
			System.out.println("Test list fail");
		}
		Iterator it = list.iterator();
		while (it.hasNext()) {
			bean = (CollegeBean) it.next();
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getAddress());
			System.out.println(bean.getState());
			System.out.println(bean.getCity());
			System.out.println(bean.getPhoneNo());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getCreatedDatetime());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getModifiedDatetime());
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	
	
}
}
