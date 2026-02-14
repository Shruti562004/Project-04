
package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.RoleBean;
import in.co.rays.proj4.bean.UserBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.RoleModel;
import in.co.rays.proj4.model.UserModel;

public class TestUserModel {

	public static UserModel model = new UserModel();

	public static void main(String[] args) throws Exception {

		 testAdd();
		 //authenticate();

		// testDelete();
		// testUpdate();
		// testFindByPK();
		// testFindByLogin();
		// testList();
		//testSearch();
	}
//add*************************
	public static void testAdd() {
		try {
			UserBean bean = new UserBean();

			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
			// bean.setId(2);
			bean.setFirstName("varun");
			bean.setLastName("Dhawan");
			bean.setLogin("varun@gmail.com");
			bean.setPassword("varun@1234");
			bean.setDob(sdf.parse("05-08-1997"));
			bean.setRoleId(1L);
			bean.setGender("male");
			bean.setMobileNo("7899661301");
			bean.setCreatedBy("root");
			bean.setModifiedBy("root");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			   
		    bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

			UserModel model = new UserModel();

			long pk = model.add(bean);
			/*
			 * UserBean addedbean = model.findByPk(pk); System.out.println("Test add succ");
			 * 
			 * if (addedbean == null) { System.out.println("Test add fail"); }
			 */
			System.out.println("record insert");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
//authemticate*******************************************
	public static void authenticate() {
		try {
			UserBean bean = new UserBean();
			UserModel model = new UserModel();
			
			bean = model.authenticate("Shruti30@gmail.com","Shr@123");
			if (bean != null) {
				System.out.println("Successfully login");
			} else {
				System.out.println("Invaliad login Id & password");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	// delete*******************

	public static void testDelete() throws ApplicationException {

		UserBean bean = new UserBean();
		UserModel model = new UserModel();
		bean.setId(1);
		model.delete(bean);
		System.out.println("record deleted");

	}

	// update****************************
	public static void testUpdate() throws ParseException, DuplicateRecordException {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
			UserModel model = new UserModel();
			UserBean bean = new UserBean();

			bean.setId(1);
			bean.setFirstName("Chinu");
			bean.setLastName("Patel");
			bean.setLogin("chinu@gmail.com");
			bean.setPassword("chinu@123");
			bean.setDob(sdf.parse("06-02-2000"));
			bean.setMobileNo("7879706591");
			bean.setRoleId(123);
			bean.setGender("Female");
			bean.setCreatedBy("Student");
			bean.setModifiedBy("Student");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			   
		    bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			model.update(bean);
			System.out.println("record update");
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}
	// find by pk************************

	public static void testFindByPK() {
		try {
			UserModel model = new UserModel();
			UserBean bean = new UserBean();
		
			bean = model.findByPk(1);
			if (bean == null) {
				System.out.println("Test Find By PK fill");

			}

			System.out.println(bean.getId());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getLogin());
			System.out.println(bean.getPassword());
			System.out.println(bean.getDob());
			System.out.println(bean.getMobileNo());
			System.out.println(bean.getRoleId());
			System.out.println(bean.getGender());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getCreatedDatetime());
			System.out.println(bean.getModifiedDatetime());
			
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	
	//login************************************
	public static void testFindByLogin() {
		try {
			UserModel model = new UserModel();
			UserBean bean = new UserBean();
			bean = model.findByLogin("Shruti30@gmail.com");
			if (bean == null) {
				System.out.println("Test Find by login fill");
			}

			System.out.println(bean.getId());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getLogin());
			System.out.println(bean.getPassword());
			System.out.println(bean.getDob());
			System.out.println(bean.getMobileNo());
			System.out.println(bean.getRoleId());
			System.out.println(bean.getGender());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getCreatedDatetime());
			System.out.println(bean.getModifiedDatetime());
		}

		catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
	
	//all data***************************************

	public static void testList() {
		try {
			UserBean bean = new UserBean();
			UserModel model = new UserModel();
			List list = new ArrayList();
			list = model.list(1, 10);
			if (list.size() > 0) {
				System.out.println("test List faill");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (UserBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getFirstName());
				System.out.println(bean.getLastName());
				System.out.println(bean.getLogin());
				System.out.println(bean.getPassword());
				System.out.println(bean.getDob());
				System.out.println(bean.getMobileNo());
				System.out.println(bean.getRoleId());
				System.out.println(bean.getGender());
				System.out.println(bean.getCreatedBy());
				System.out.println(bean.getModifiedBy());
				System.out.println(bean.getCreatedDatetime());
				System.out.println(bean.getModifiedDatetime());
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	// search by filter
	private static void testSearch() {
		try {
			UserBean bean = new UserBean();
			UserModel model = new UserModel();
			List list = new ArrayList();
			bean.setFirstName("V");

			// bean.setId(8L);
			list = model.search(bean, 0, 0);
			if (list.size() < 0) {
				System.out.println("Test search fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (UserBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getFirstName());
				System.out.println(bean.getLastName());
				System.out.println(bean.getLogin());
				System.out.println(bean.getPassword());
				System.out.println(bean.getDob());
				System.out.println(bean.getMobileNo());
				System.out.println(bean.getRoleId());
				System.out.println(bean.getGender());
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
