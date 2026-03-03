
package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.EmiBean;
import in.co.rays.proj4.bean.RoleBean;
import in.co.rays.proj4.bean.UserBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.EmiModel;
import in.co.rays.proj4.model.RoleModel;
import in.co.rays.proj4.model.UserModel;

public class EmiTest {

	public static EmiModel model = new EmiModel();

	public static void main(String[] args) throws Exception {

		//testAdd();
		

		 //testDelete();
	// testUpdate();
		// testFindByPK();
		// testFindByLogin();
		// testList();
		testSearch();
	}
//add*************************
	public static void testAdd() {
		try {
			EmiBean bean = new EmiBean();

			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
			// bean.setId(2);
			bean.setAmount(454);
			bean.setDueDate(sdf.parse("06-07-2006"));
			bean.setStatus("pending");
			
			bean.setCreatedBy("root");
			bean.setModifiedBy("root");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			   
		    bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

			

			long pk = model.add(bean);
			
			System.out.println("record insert");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	// delete*******************

	public static void testDelete() throws ApplicationException {

		EmiBean bean = new EmiBean();
	
		bean.setId(1);
		model.delete(bean);
		System.out.println("record deleted");

	}

	// update****************************
	public static void testUpdate() throws ParseException, DuplicateRecordException {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		
			EmiBean bean = new EmiBean();

		
			bean.setAmount(78);
			bean.setDueDate(sdf.parse("06-07-2006"));
bean.setStatus("sucess");
			
			bean.setCreatedBy("root");
			bean.setModifiedBy("root");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			   
		    bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			bean.setId(1);
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
	EmiBean bean = new EmiBean();
			EmiModel model = new EmiModel();
			List list = new ArrayList();
			bean.setStatus("p");

			
			list = model.search(bean, 0, 0);
			if (list.size() < 0) {
				System.out.println("Test search fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (EmiBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getAmount());
				System.out.println(bean.getDueDate());
				System.out.println(bean.getStatus());
				
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
