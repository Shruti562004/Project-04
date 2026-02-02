package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.SubjectBean;
import in.co.rays.proj4.bean.TimetableBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.SubjectModel;
import in.co.rays.proj4.model.TimetableModel;

public class TestTimetableModel {
	
	public static void main(String[] args) throws Exception {
		testAdd();
		//testDelete();
	//	testUpdate();
		//testList();
		//testSearch();
		//testfindBypk();
		 //testCheckByCss();
		// testCheckByCds();
		//testcheckBysemester();
		//testcheckBycourseName();
	}
//add
	public static void testAdd() {
		try {
			TimetableBean bean = new TimetableBean();
			TimetableModel model= new TimetableModel();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			bean.setId(2);
			bean.setSemester("4");
			bean.setDescription("IT");
			bean.setExamDate(sdf.parse("10/01/2026"));
			bean.setExamTime("10 am to 1 pm");
			bean.setCourseId(2);
			bean.setCourseName("c");
			bean.setSubjectId(2);
			bean.setSubjectName("science");
			
			bean.setCreatedBy("admin");
			bean.setModifiedBy("admin");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

			long pk = model.add(bean);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//delete

	
			public  static void testDelete() {
				try {
					TimetableBean bean= new TimetableBean();
					TimetableModel model=new TimetableModel();
					bean.setId(2);
					model.delete(bean);
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			
}

//update
public static void testUpdate() throws ParseException, DuplicateRecordException {
	try {
		TimetableBean bean = new TimetableBean();
		TimetableModel model=new TimetableModel();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		bean.setId(3);
		bean.setSemester("4");
		bean.setDescription("bca");
		bean.setExamDate(sdf.parse("12/09/2025"));
		bean.setExamTime(" 11 am to 1 pm");
		bean.setCourseId(2);
		bean.setCourseName("btech");
		bean.setSubjectId(2);
		bean.setSubjectName("java");
		
		
		
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		model.update(bean);;
	}catch(ApplicationException e) {
		e.printStackTrace();
	}
}
//findby pk
public static void testfindBypk() {
	try {
		TimetableBean bean = new TimetableBean();
		TimetableModel model=new TimetableModel();
		bean=model.findByPK(1);
        System.out.println(bean.getId());
System.out.println(bean.getSemester());
System.out.println(bean.getDescription());
System.out.println( bean.getExamDate());
System.out.println( bean.getExamTime());
          System.out.println(bean.getCourseId());
          System.out.println(  bean.getCourseName());
          System.out.println( bean.getSubjectId());
          System.out.println(bean.getSubjectName());
         
          
     
          System.out.println( bean.getCreatedBy());
          System.out.println(bean.getModifiedBy());
          System.out.println(bean.getCreatedDatetime());
          System.out.println(bean.getModifiedDatetime());
		
		
	}catch(ApplicationException e) {
		e.printStackTrace();
	}
}

//list
public static void testList() {
    try {

        TimetableModel model = new TimetableModel();
        List list = model.list(1, 3);  
        Iterator it = list.iterator();

        while (it.hasNext()) {
            TimetableBean bean = (TimetableBean) it.next();
            System.out.println(bean.getId());
System.out.println(bean.getSemester());
System.out.println(bean.getDescription());
System.out.println( bean.getExamDate());
System.out.println( bean.getExamTime());
            System.out.println(bean.getCourseId());
            System.out.println(  bean.getCourseName());
            System.out.println( bean.getSubjectId());
            System.out.println(bean.getSubjectName());
           
            
       
            System.out.println( bean.getCreatedBy());
            System.out.println(bean.getModifiedBy());
            System.out.println(bean.getCreatedDatetime());
            System.out.println(bean.getModifiedDatetime());
            System.out.println("----------------------------");
        }

    } catch (ApplicationException e) {
        e.printStackTrace();
    }
}

//search

public static void testSearch() {
    try {
        TimetableBean bean = new TimetableBean();
        TimetableModel model = new TimetableModel();

        bean.setSubjectName("java");

        List list = model.search(bean, 0, 0); 

        if (list.size() == 0) {
            System.out.println("No record found");
            return;
        }

        Iterator it = list.iterator();
        while (it.hasNext()) {
            bean = (TimetableBean) it.next();
System.out.println("ID " +bean.getId());
            System.out.println("Semester : " + bean.getSemester());
            System.out.println("Description : " + bean.getDescription());
            System.out.println("Exam Date : " + bean.getExamDate());
            System.out.println("Exam Time : " + bean.getExamTime());
            System.out.println("Course ID : " + bean.getCourseId());
            System.out.println("Course Name : " + bean.getCourseName());
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

//check by css
public static void testCheckByCss() throws ApplicationException {
try {
	TimetableModel model=new TimetableModel();

	TimetableBean bean=new TimetableBean();
	bean=model.checkBycss(2, 2, "4");
	
    System.out.println(bean.getId());
System.out.println(bean.getSemester());
System.out.println(bean.getDescription());
System.out.println( bean.getExamDate());
System.out.println( bean.getExamTime());
      System.out.println(bean.getCourseId());
      System.out.println(  bean.getCourseName());
      System.out.println( bean.getSubjectId());
      System.out.println(bean.getSubjectName());
     
      
 
      System.out.println( bean.getCreatedBy());
      System.out.println(bean.getModifiedBy());
      System.out.println(bean.getCreatedDatetime());
      System.out.println(bean.getModifiedDatetime());
	
}
catch(ApplicationException e) {
	e.printStackTrace();
}
	
}
//check by Cds
public static void testCheckByCds() throws ApplicationException {
 try {
     TimetableModel model = new TimetableModel();

     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
     Date examDate = sdf.parse("2025-09-12");   

     TimetableBean bean = model.checkBycds(2, "4", examDate);

     System.out.println(bean.getId());
     System.out.println(bean.getSemester());
     System.out.println(bean.getDescription());
     System.out.println(bean.getExamDate());
     System.out.println(bean.getExamTime());
     System.out.println(bean.getCourseId());
     System.out.println(bean.getCourseName());
     System.out.println(bean.getSubjectId());
     System.out.println(bean.getSubjectName());
     System.out.println(bean.getCreatedBy());
     System.out.println(bean.getModifiedBy());
     System.out.println(bean.getCreatedDatetime());
     System.out.println(bean.getModifiedDatetime());

 } catch (Exception e) {
     e.printStackTrace();
 }
}
//check by semester
public static void testcheckBysemester() throws ApplicationException, ParseException {
try {
	TimetableModel model=new TimetableModel();

	TimetableBean bean=new TimetableBean();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date examDate1 = sdf.parse("2025-09-12"); 
	bean=model.checkBysemester(2, 2, "4", examDate1);
	
  System.out.println(bean.getId());
System.out.println(bean.getSemester());
System.out.println(bean.getDescription());
System.out.println( bean.getExamDate());
System.out.println( bean.getExamTime());
    System.out.println(bean.getCourseId());
    System.out.println(  bean.getCourseName());
    System.out.println( bean.getSubjectId());
    System.out.println(bean.getSubjectName());
   
    

    System.out.println( bean.getCreatedBy());
    System.out.println(bean.getModifiedBy());
    System.out.println(bean.getCreatedDatetime());
    System.out.println(bean.getModifiedDatetime());
	
}
catch(Exception e) {
	e.printStackTrace();
}
}

//check by coursename
public static void testcheckBycourseName() throws ApplicationException, ParseException {
try {
	TimetableModel model=new TimetableModel();

	TimetableBean bean=new TimetableBean();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
  Date examDate1 = sdf.parse("2025-09-12"); 
	bean=model.checkByCourseName(2, examDate1);
	
System.out.println(bean.getId());
System.out.println(bean.getSemester());
System.out.println(bean.getDescription());
System.out.println( bean.getExamDate());
System.out.println( bean.getExamTime());
  System.out.println(bean.getCourseId());
  System.out.println(  bean.getCourseName());
  System.out.println( bean.getSubjectId());
  System.out.println(bean.getSubjectName());
 
  

  System.out.println( bean.getCreatedBy());
  System.out.println(bean.getModifiedBy());
  System.out.println(bean.getCreatedDatetime());
  System.out.println(bean.getModifiedDatetime());
	
}
catch(Exception e) {
	e.printStackTrace();
}
}
}

