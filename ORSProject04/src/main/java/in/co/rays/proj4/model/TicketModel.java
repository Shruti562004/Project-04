package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.TicketBean;
import in.co.rays.proj4.util.JDBCDataSource;

public class TicketModel {

	public Integer nextPk() {
		Connection conn=null;
		
		int pk=0;
		try {
			conn=JDBCDataSource.getConnection();
			PreparedStatement pstmt=conn.prepareStatement("select max(id) from st_ticket");
			ResultSet rs=pstmt.executeQuery();
			
			while(rs.next()) {
				pk=rs.getInt(1);
			}
		}
		
		catch(Exception e) {
			e.printStackTrace();
		}
		
		finally {
		JDBCDataSource.closeConnection(conn);
		}
		return pk+1;
	}
	
	public long add(TicketBean bean) throws SQLException{
		Connection conn=null;
		int pk=0;
		try {
			conn=JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			pk=nextPk();
			PreparedStatement pstmt=conn.prepareStatement("insert into st_ticket values(?,?,?,?,?)");
			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getCode());
			pstmt.setString(3, bean.getIssueTitle());
			pstmt.setString(4, bean.getAssignedAgent());
			pstmt.setString(5, bean.getTicketStatus());
			int i=pstmt.executeUpdate();
			System.out.println(i +"row affected");
			conn.commit();
			pstmt.close();
		}
		
		catch(Exception e) {
			conn.rollback();
			e.printStackTrace();
		}
		
		finally {
		JDBCDataSource.closeConnection(conn);
		}
		
		return pk;
	}
	
	public void delete(TicketBean bean) throws SQLException {
		Connection conn=null;
		try {
			conn=JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt=conn.prepareStatement("delete from st_ticket where id=?");
			pstmt.setLong(1, bean.getId());
			int i=pstmt.executeUpdate();
			System.out.println(i +"row affected");
			conn.commit();
			pstmt.close();
		}
		catch(Exception e) {
			conn.rollback();
			e.printStackTrace();
		}
		
		finally {
		JDBCDataSource.closeConnection(conn);
		}
	}
	
	public void update(TicketBean bean) throws SQLException {
		Connection conn=null;
		try {
			conn=JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt=conn.prepareStatement("update st_ticket set code=?,issue_title=?,assigned_agent=?,ticket_status=? where id=?");
			
			pstmt.setString(1, bean.getCode());
			pstmt.setString(2, bean.getIssueTitle());
			pstmt.setString(3, bean.getAssignedAgent());
			pstmt.setString(4, bean.getTicketStatus());
			pstmt.setLong(5, bean.getId());
			int i=pstmt.executeUpdate();
			System.out.println(i +"row affected");
			conn.commit();
			pstmt.close();
		}
		catch(Exception e) {
			conn.rollback();
			e.printStackTrace();
		}
		
		finally {
		JDBCDataSource.closeConnection(conn);
		}
		
	}
	
	public TicketBean findByPk(int pk) throws SQLException {
		Connection conn=null;
		TicketBean bean=null;
		try {
		conn=JDBCDataSource.getConnection();
		PreparedStatement pstmt=conn.prepareStatement("select * from st_ticket where id=?");
		pstmt.setLong(1, pk);
		ResultSet rs=pstmt.executeQuery();
		
		while(rs.next()) {
			bean=new TicketBean();
			bean.setId(rs.getLong(1));
			bean.setCode(rs.getString(2));
			bean.setIssueTitle(rs.getString(3));
			bean.setAssignedAgent(rs.getString(4));
			bean.setTicketStatus(rs.getString(5));
		}
		
		
	}
		
		catch(Exception e) {
		
			e.printStackTrace();
		}
		
		finally {
		JDBCDataSource.closeConnection(conn);
		}
		
		
		return bean;
	}

	
	public TicketBean findByCode(String code) throws SQLException {
		Connection conn=null;
		TicketBean bean=null;
		try {
		conn=JDBCDataSource.getConnection();
		PreparedStatement pstmt=conn.prepareStatement("select * from st_ticket where code=?");
		pstmt.setString(1, code);
		ResultSet rs=pstmt.executeQuery();
		
		while(rs.next()) {
			bean=new TicketBean();
			bean.setId(rs.getLong(1));
			bean.setCode(rs.getString(2));
			bean.setIssueTitle(rs.getString(3));
			bean.setAssignedAgent(rs.getString(4));
			bean.setTicketStatus(rs.getString(5));
		}
		
		
	}
		
		catch(Exception e) {
	
			e.printStackTrace();
		}
		
		finally {
		JDBCDataSource.closeConnection(conn);
		}
		
		
		return bean;
	}
	
	public List list(int pageNo, int pageSize) {
		StringBuffer sql=new StringBuffer("select * from st_ticket");
		ArrayList list=new ArrayList();
		if(pageSize>0) {
		pageNo=(pageNo-1)*pageSize;
		sql.append(" limit" +pageNo +"," +pageSize);
		
		
		}
		
		Connection conn=null;
		TicketBean bean=null;
		try {
		conn=JDBCDataSource.getConnection();
		PreparedStatement pstmt=conn.prepareStatement(sql.toString());
	
		ResultSet rs=pstmt.executeQuery();
		while(rs.next()) {
			bean=new TicketBean();
			bean.setId(rs.getLong(1));
			bean.setCode(rs.getString(2));
			bean.setIssueTitle(rs.getString(3));
			bean.setAssignedAgent(rs.getString(4));
			bean.setTicketStatus(rs.getString(5));
			list.add(bean);
		}
		
		
	}
		
		catch(Exception e) {
	
			e.printStackTrace();
		}
		
		finally {
		JDBCDataSource.closeConnection(conn);
		}
		
		return list;
	}
	
	
	public List search(TicketBean bean,int pageNo,int pageSize) {
		StringBuffer sql=new StringBuffer("select * from st_ticket where 1=1");
		if (bean != null) {
		if(bean.getId()>0) {
			sql.append(" and id =" +bean.getId());
		}
		if(bean.getCode()!=null && bean.getCode().length()>0) {
			sql.append(" and CODE like '" +bean.getCode() +"%'" );
		}
		if(bean.getIssueTitle()!=null && bean.getIssueTitle().length()>0) {
			sql.append(" and ISSUETITLE like '" +bean.getIssueTitle() +"%'" );
		}
		if(bean.getAssignedAgent()!=null && bean.getAssignedAgent().length()>0) {
			sql.append(" and ASSIGNEDAGENT like '" +bean.getAssignedAgent() +"%'" );
		}
		if(bean.getTicketStatus()!=null && bean.getTicketStatus().length()>0) {
			sql.append(" and TICKETSTATUS like '" +bean.getTicketStatus() +"%'" );
		}
		}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		ArrayList list=new ArrayList();
		if(pageSize>0) {
		pageNo=(pageNo-1)*pageSize;
		sql.append(" limit " +pageNo + "," + pageSize);
		
		
		}
		
		Connection conn=null;
		
		try {
		conn=JDBCDataSource.getConnection();
		PreparedStatement pstmt=conn.prepareStatement(sql.toString());
		System.out.println("SQL = " + sql.toString());
		ResultSet rs=pstmt.executeQuery();
		while(rs.next()) {
			bean=new TicketBean();
			bean.setId(rs.getLong(1));
			bean.setCode(rs.getString(2));
			bean.setIssueTitle(rs.getString(3));
			bean.setAssignedAgent(rs.getString(4));
			bean.setTicketStatus(rs.getString(5));
			list.add(bean);
		}
		
		
	}
		
		catch(Exception e) {
	
			e.printStackTrace();
		}
		
		finally {
		JDBCDataSource.closeConnection(conn);
		}
		
		return list;
	}
	
	}
