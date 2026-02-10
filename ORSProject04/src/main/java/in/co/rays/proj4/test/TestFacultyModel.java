package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.FacultyBean;
import in.co.rays.proj4.bean.StudentBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.FacultyModel;
import in.co.rays.proj4.model.StudentModel;

public class TestFacultyModel {
	
	public static void main(String[] args) throws DuplicateRecordException {
		
	
testAdd();
//testDelete();
//testUpdate();
//testList();
	//testSearch();
		//testFindByPk();
		//testFindByEmailId();
}

public static void testAdd() throws DuplicateRecordException {
	
	try {
		FacultyBean bean = new FacultyBean();
		FacultyModel model= new FacultyModel();
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
		//bean.setId(1);
	
		bean.setFirstName("Amir");
		bean.setLastName("Khan");
		bean.setGender("male");
		bean.setEmail("amir@gmail.com");
		bean.setMobileNo("677654329");
		bean.setCollegeId(1);
		bean.setCollegeName("sage");
		bean.setCourseId(1);
		bean.setCourseName("java");
		bean.setDob(sdf.parse("6/09/1999"));
		bean.setSubjectId(1);
		bean.setSubjectName("maths");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		
		long pk=model.add(bean);
		System.out.println("success");
	}
	catch(Exception e) {
		e.printStackTrace();
	}
}

//delete
public static void testDelete()  {
try {	

FacultyBean bean=new FacultyBean();
FacultyModel model=new FacultyModel();
bean.setId(2);
model.delete(bean);
}
catch(ApplicationException e){
	e.printStackTrace();
}
}
//update
public static void testUpdate() {
    try {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        FacultyBean bean = new FacultyBean();
        FacultyModel model = new FacultyModel();

        bean.setId(3);  
        bean.setFirstName("akash");
        bean.setLastName("Khan");
        bean.setDob(sdf.parse("06/09/1999"));
        bean.setGender("male");
        bean.setMobileNo("677654329");
        bean.setEmail("amir@gmail.com");
       
        bean.setCollegeId(3);
        bean.setCollegeName("IPS");
        bean.setCourseId(4);
        bean.setCourseName("m.com");
        
        bean.setSubjectId(5);
        bean.setSubjectName("maths");
        bean.setCreatedBy("admin");
        bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		   
	    bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

        model.update(bean);
        System.out.println("Faculty updated successfully");

    } catch (Exception e) {
        e.printStackTrace();
    }
}
//find by pk
public static void testFindByPk() {
	try {
		FacultyBean bean=new FacultyBean();
		FacultyModel model=new FacultyModel();
		
		bean=model.findByPK(1);
		if(bean==null) {
			System.out.println("test findbypk fail");
		}
	    System.out.println(bean.getId());
        System.out.println(bean.getFirstName());
        System.out.println(bean.getLastName());
        System.out.println(bean.getDob());
        System.out.println(bean.getGender());
        System.out.println(bean.getEmail());
        System.out.println(bean.getMobileNo());
        System.out.println(bean.getCollegeId());
        System.out.println(bean.getCollegeName());
        System.out.println(bean.getCourseId());
        System.out.println(bean.getCourseName());

        System.out.println(bean.getSubjectId());
        System.out.println(bean.getSubjectName());
        System.out.println(bean.getCreatedBy());
        System.out.println(bean.getModifiedBy());
        System.out.println(bean.getCreatedDatetime());
        System.out.println(bean.getModifiedDatetime());
	}catch(ApplicationException e) {
		e.printStackTrace();
	}
}
///email///////****************

public static void testFindByEmailId() {
    try {
        FacultyBean bean = new FacultyBean();
        FacultyModel model=new FacultyModel();
        bean = model.findByEmailId("sahil@gmail.com");
        if (bean == null) {
            System.out.println("Test Find By EmailId fail");
        }
        System.out.println(bean.getId());
        System.out.println(bean.getFirstName());
        System.out.println(bean.getLastName());
        System.out.println(bean.getDob());
        System.out.println(bean.getGender());
        System.out.println(bean.getEmail());
        System.out.println(bean.getMobileNo());
        System.out.println(bean.getCollegeId());
        System.out.println(bean.getCollegeName());
        System.out.println(bean.getCourseId());
        System.out.println(bean.getCourseName());

        System.out.println(bean.getSubjectId());
        System.out.println(bean.getSubjectName());
        System.out.println(bean.getCreatedBy());
        System.out.println(bean.getModifiedBy());
        System.out.println(bean.getCreatedDatetime());
        System.out.println(bean.getModifiedDatetime());
    } catch (ApplicationException e) {
        e.printStackTrace();
    }
}


//list
public static void testList() {
    try {
        FacultyModel model = new FacultyModel();

        List list = model.list(0, 0);

        if (list.size() == 0) {
            System.out.println("No records found");
            return;
        }

        Iterator<FacultyBean> it = list.iterator();
        while (it.hasNext()) {
            FacultyBean bean = it.next();

            System.out.println(bean.getId());
            System.out.println(bean.getFirstName());
            System.out.println(bean.getLastName());
            System.out.println(bean.getDob());
            System.out.println(bean.getGender());
            System.out.println(bean.getEmail());
            System.out.println(bean.getMobileNo());
            System.out.println(bean.getCollegeId());
            System.out.println(bean.getCollegeName());
            System.out.println(bean.getCourseId());
            System.out.println(bean.getCourseName());

            System.out.println(bean.getSubjectId());
            System.out.println(bean.getSubjectName());
            System.out.println(bean.getCreatedBy());
            System.out.println(bean.getModifiedBy());
            System.out.println(bean.getCreatedDatetime());
            System.out.println(bean.getModifiedDatetime());
            System.out.println("----------------------------");
        }

    } catch (ApplicationException e) {
        e.printStackTrace();
    }
}

public static void testSearch() {
    try {
        FacultyBean bean = new FacultyBean();
        FacultyModel model = new FacultyModel();

        bean.setFirstName("a");

        List list = model.search(bean);

        Iterator it = list.iterator();
        while (it.hasNext()) {
          bean = (FacultyBean) it.next();

            System.out.println("ID : " + bean.getId());
            System.out.println("First Name : " + bean.getFirstName());
            System.out.println("Last Name : " + bean.getLastName());
            System.out.println("Gender : " + bean.getGender());
            System.out.println("Email : " + bean.getEmail());
            System.out.println("Mobile : " + bean.getMobileNo());
            System.out.println("College ID : " + bean.getCollegeId());
            System.out.println("College Name : " + bean.getCollegeName());
            System.out.println("Course ID : " + bean.getCourseId());
            System.out.println("Course Name : " + bean.getCourseName());
            System.out.println("DOB : " + bean.getDob());
            System.out.println("Subject ID : " + bean.getSubjectId());
            System.out.println("Subject Name : " + bean.getSubjectName());
            System.out.println("Created By : " + bean.getCreatedBy());
            System.out.println("Modified By : " + bean.getModifiedBy());
            System.out.println("Created Date : " + bean.getCreatedDatetime());
            System.out.println("Modified Date : " + bean.getModifiedDatetime());
            System.out.println("--------------------------------");
        }

    } catch (ApplicationException e) {
        e.printStackTrace();
    }
}


}