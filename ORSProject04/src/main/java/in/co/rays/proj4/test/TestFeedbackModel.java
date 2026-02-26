package in.co.rays.proj4.test;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.FeedBackBean;
import in.co.rays.proj4.model.FeedbackModel;

public class TestFeedbackModel {
	
	public static void main(String[] args) throws SQLException {
		
		// testAdd();
		//testDelete();
		//testUpdate();
	//testCode();
		 //testPk();
		// testList();
		testSearch();
		
	}
	
	public static void testAdd() throws SQLException {
		
		FeedbackModel model=new FeedbackModel();
		FeedBackBean bean=new FeedBackBean();
		
		bean.setCode("srs");
		bean.setCandidateName("shruti");
		bean.setInterviewerName("rishi");
		bean.setFeedbackStatus("pending");
	  long pk= model.add(bean);
	}

	
	public static void testDelete() throws SQLException {
		FeedbackModel model=new FeedbackModel();
		FeedBackBean bean=new FeedBackBean();
		bean.setId(2);
		model.delete(bean);
		
	}
	
	public static void testUpdate() throws SQLException {
		FeedbackModel model=new FeedbackModel();
		FeedBackBean bean=new FeedBackBean();
		
		bean.setCode("rrr");
		bean.setCandidateName("Darsh");
		bean.setInterviewerName("Surbhi");
		bean.setFeedbackStatus("pending");
		bean.setId(3);
		model.update(bean);
		
	}
	
	public static void testPk() throws SQLException {
		FeedbackModel model=new FeedbackModel();
		FeedBackBean bean=new FeedBackBean();
		bean=model.findByPk(1);
		System.out.println(bean.getId());
		System.out.println(bean.getCode());
		System.out.println(bean.getCandidateName());
		System.out.println(bean.getInterviewerName());
		System.out.println(bean.getFeedbackStatus());
	}
	
	
	public static void testCode() throws SQLException {
		FeedbackModel model=new FeedbackModel();
		FeedBackBean bean=new FeedBackBean();
		bean=model.findByCode("srs");
		System.out.println(bean.getId());
		System.out.println(bean.getCode());
		System.out.println(bean.getCandidateName());
		System.out.println(bean.getInterviewerName());
		System.out.println(bean.getFeedbackStatus());
	}
	
	public static void testList() throws SQLException {
		FeedbackModel model=new FeedbackModel();
		FeedBackBean bean=new FeedBackBean();
		
		List list=model.list(0, 0);
		
		Iterator it=list.iterator();
		while(it.hasNext()){
			 bean= (FeedBackBean)it.next();
			 System.out.println(bean.getId());
				System.out.println(bean.getCode());
				System.out.println(bean.getCandidateName());
				System.out.println(bean.getInterviewerName());
				System.out.println(bean.getFeedbackStatus()); 
		}
	}
	
	public static void testSearch() throws SQLException {
		FeedbackModel model=new FeedbackModel();
		FeedBackBean bean=new FeedBackBean();
		
		List list=model.search(bean, 0, 0);
		bean.setCode("s");
		
		Iterator it=list.iterator();
		while(it.hasNext()){
			 bean= (FeedBackBean)it.next();
			 System.out.println(bean.getId());
				System.out.println(bean.getCode());
				System.out.println(bean.getCandidateName());
				System.out.println(bean.getInterviewerName());
				System.out.println(bean.getFeedbackStatus()); 
		}
	}
}
