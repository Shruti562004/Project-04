package in.co.rays.proj4.test;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.TicketBean;
import in.co.rays.proj4.model.TicketModel;

public class TestTicketModel {
	
	public static void main(String[] args) throws SQLException {
		//testAdd();
		//testDelete();
//testUpdate();
		//testPk();
		//testCode();
		 //testList();
		testSearch();
		
		
	}

	
	public static void testAdd() throws SQLException {
		TicketBean bean=new TicketBean();
		TicketModel model=new TicketModel();
		
		bean.setCode("SR");
		bean.setIssueTitle("Problem");
		bean.setAssignedAgent("Chinu");
	bean.setTicketStatus("Pending");
	long pk=model.add(bean);
	
		
		
	}
	
	public static void testDelete() throws SQLException {
		TicketBean bean=new TicketBean();
		TicketModel model=new TicketModel();
		
		bean.setId(4);
		model.delete(bean);
	}
	
	public static void testUpdate() throws SQLException {
		TicketBean bean=new TicketBean();
		TicketModel model=new TicketModel();
		bean.setCode("SP");
		bean.setIssueTitle("Solve");
		bean.setAssignedAgent("Sandeep");
		bean.setTicketStatus("Save");
		bean.setId(2);
		
		model.update(bean);
		
		
	}
	
	public static void testPk() throws SQLException {
		TicketBean bean=new TicketBean();
		TicketModel model=new TicketModel();
		
		 bean= model.findByPk(1);
		 System.out.println(bean.getId());
		 System.out.println(bean.getCode());
		 System.out.println(bean.getIssueTitle());
		 System.out.println(bean.getAssignedAgent());
		 System.out.println(bean.getTicketStatus());
	}
	
	public static void testCode() throws SQLException {
		TicketBean bean=new TicketBean();
		TicketModel model=new TicketModel();
		
		 bean= model.findByCode("SP");
		 System.out.println(bean.getId());
		 System.out.println(bean.getCode());
		 System.out.println(bean.getIssueTitle());
		 System.out.println(bean.getAssignedAgent());
		 System.out.println(bean.getTicketStatus());
	}
	
	
	public static void testList() {
		TicketBean bean=new TicketBean();
		TicketModel model=new TicketModel();
		
		List list=model.list(0, 0);
		
		Iterator<TicketBean> it=list.iterator();
		while(it.hasNext()) {
			bean =it.next();
			 System.out.println(bean.getId());
			 System.out.println(bean.getCode());
			 System.out.println(bean.getIssueTitle());
			 System.out.println(bean.getAssignedAgent());
			 System.out.println(bean.getTicketStatus());
		}
		
	}
	
	
	public static void testSearch() {

	    TicketBean bean = new TicketBean();
	    TicketModel model = new TicketModel();

	    bean.setCode("S");

	    List<TicketBean> list = model.search(bean, 0, 0);

	    Iterator<TicketBean> it = list.iterator();

	    while (it.hasNext()) {
	        TicketBean b = it.next();

	        System.out.println(b.getId());
	        System.out.println(b.getCode());
	        System.out.println(b.getIssueTitle());
	        System.out.println(b.getAssignedAgent());
	        System.out.println(b.getTicketStatus());
	    }
	}
}



