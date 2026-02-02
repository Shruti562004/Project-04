package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.CourseBean;
import in.co.rays.proj4.bean.SubjectBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.CourseModel;
import in.co.rays.proj4.model.SubjectModel;

public class TestSubjectModel {
	public static void main(String[] args) throws Exception {
		testAdd();
		//testDelete();
		//testUpdate();
		//testList();
		//testSearch();
		//testFindByPk();
		// testFindByName();
	}
//add**********************
	
	public static void testAdd() {
	try {
		SubjectBean bean = new SubjectBean();
		SubjectModel model=new SubjectModel();
		bean.setName("commerce");
		bean.setCourseId(2);
		bean.setCourseName("c");
		bean.setDescription("programing");
		
		
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

		long pk = model.add(bean);

	} catch (Exception e) {
		e.printStackTrace();
	}
}
//delete******************************************

	
		public  static void testDelete() {
			try {
				SubjectBean bean= new SubjectBean();
				SubjectModel model=new SubjectModel();
				bean.setId(3);
				model.delete(bean);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
	
		
		
		}
		
		//update/***********************************
		public static void testUpdate() {
			try {
				SubjectBean bean = new SubjectBean();
				SubjectModel model=new SubjectModel();
				bean.setId(1);
			bean.setName("java");
			bean.setCourseId(2);
			bean.setCourseName("python");
				bean.setDescription("commerce");
				bean.setCreatedBy("admin");
				bean.setModifiedBy("admin");
				bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
				bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
				bean.setId(2);
				model.update(bean);
				System.out.println("update succ");

			

			} catch (ApplicationException e) {
				e.printStackTrace();
			} catch (DuplicateRecordException e) {
				e.printStackTrace();
			}
		}
		//pk*********************************
		public static void testFindByPk() {
			try {
				SubjectBean bean = new SubjectBean();
				SubjectModel model=new   SubjectModel();
				
				bean = model.FindByPK(1L);
				if (bean == null) {
					System.out.println("test findbypk fail");
				}
				System.out.println(bean.getId());
				System.out.println(bean.getName());
				System.out.println(bean.getDescription());
				System.out.println(bean.getCourseId());
				System.out.println(bean.getCourseName());
				System.out.println(bean.getCreatedBy());
				System.out.println(bean.getModifiedBy());
				System.out.println(bean.getCreatedDatetime());
				System.out.println(bean.getModifiedDatetime());
			} catch (ApplicationException e) {
				e.printStackTrace();
			}
		}
		//name************************
		public static void testFindByName() {
			try {
				SubjectBean bean = new SubjectBean();
				SubjectModel model=new SubjectModel();
				bean = model.findByName("java");
				if (bean == null) {
					System.out.println("test findBy Name fail");
				}

				System.out.println(bean.getId());
				System.out.println(bean.getName());
				System.out.println(bean.getDescription());
				System.out.println(bean.getCourseId());
				System.out.println(bean.getCourseName());
				System.out.println(bean.getCreatedBy());
				System.out.println(bean.getModifiedBy());
				System.out.println(bean.getCreatedDatetime());
				System.out.println(bean.getModifiedDatetime());

			} catch (ApplicationException e) {
				e.printStackTrace();
			}
		}
		//list********************
		public static void testList() throws Exception {
		    try {
		        SubjectModel model = new SubjectModel();
		        List<SubjectBean> list = model.list(1, 10); // page 1, 10 records

		        if (list.size() == 0) {
		            System.out.println("No records found");
		            return;
		        }

		        Iterator<SubjectBean> it = list.iterator();
		        while (it.hasNext()) {
		            SubjectBean bean = it.next();
		            System.out.println("ID: " + bean.getId());
		            System.out.println("Name: " + bean.getName());
		            System.out.println("Description: " + bean.getDescription());
		            System.out.println("Course ID: " + bean.getCourseId());
		            System.out.println("Course Name: " + bean.getCourseName());
		            System.out.println("Created By: " + bean.getCreatedBy());
		            System.out.println("Modified By: " + bean.getModifiedBy());
		            System.out.println("Created Date: " + bean.getCreatedDatetime());
		            System.out.println("Modified Date: " + bean.getModifiedDatetime());
		            System.out.println("----------------------------");
		        }

		    } catch (ApplicationException e) {
		        e.printStackTrace();
		    }
		}

		public static void testSearch() throws DatabaseException {
		    try {
		        SubjectBean bean = new SubjectBean();
		        SubjectModel model = new SubjectModel();

		        // Search criteria
		        bean.setName("j");
		        // bean.setId(2L);
		        // bean.setCourseId(2L);

		        // Execute search
		        List  list = model.search(bean, 0, 0);

		        if (list.isEmpty()) {
		            System.out.println("No records found");
		            return;
		        }

		        Iterator<SubjectBean> it = list.iterator();
		        while (it.hasNext()) {
		             bean= it.next(); // new reference
		            System.out.println(" ID "+bean.getId());
		            System.out.println("Name: " + bean.getName());
		 
		            System.out.println("Course ID: " + bean.getCourseId());
		            System.out.println("Course Name: " + bean.getCourseName());
		            System.out.println("Description: " + bean.getDescription());
		            System.out.println("Created By: " + bean.getCreatedBy());
		            System.out.println("Modified By: " + bean.getModifiedBy());
		            System.out.println("Created Date: " + bean.getCreatedDatetime());
		            System.out.println("Modified Date: " + bean.getModifiedDatetime());
		            System.out.println("----------------------------");
		        }

		    } catch (ApplicationException e) {
		        e.printStackTrace();
		    }
		}


}

