package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import in.co.rays.proj4.bean.LeaveBean;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class LeaveModel {

	
	public Integer nextPk() {
		Connection conn=null;
		int pk=0;
		
		
		try {
			
			conn=JDBCDataSource.getConnection();
			PreparedStatement pstmt=conn.prepareStatement("select max(id) from st_leave");
			
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
	
	//add
	
	
	public long add(LeaveBean bean) throws SQLException, DuplicateRecordException {
		
		
		Connection conn=null;
		LeaveBean existbean=findByCode(bean.getLeaveCode());
		if(existbean!=null) {
			throw new DuplicateRecordException("code already exist");
		}
		
		int pk=0;
		try {
			conn=JDBCDataSource.getConnection();
			pk=nextPk();
			conn.setAutoCommit(false);
			
			PreparedStatement pstmt=conn.prepareStatement("insert into st_leave values(?,?,?,?,?,?)");
			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getLeaveCode());
			pstmt.setString(3, bean.getEmployeeName());
			pstmt.setDate(4, new java.sql.Date(bean.getStartDate().getTime()));
			pstmt.setDate(5, new java.sql.Date(bean.getEndDate().getTime()));

			pstmt.setString(6, bean.getLeaveStatus());
			int i=pstmt.executeUpdate();
			System.out.println( i + "row affected");
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
	
	public void delete(LeaveBean bean) throws SQLException {
		Connection conn=null;
		try {
			conn=JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt=conn.prepareStatement("delete from st_leave where id=?");
			pstmt.setLong(1, bean.getId());
			int i=pstmt.executeUpdate();
			System.out.println( i + "row affected");
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
	
	//update
	
	public void update(LeaveBean bean) throws SQLException, DuplicateRecordException {
		Connection conn=null;
		LeaveBean existbean=findByCode(bean.getLeaveCode());
		if(existbean!=null && existbean.getId()!=bean.getId()) {
			throw new DuplicateRecordException("code already exist");
		}
		try {
			conn=JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt=conn.prepareStatement("update st_leave set leaveCode=?,employeeName=?,startDate=?,endDate=?,leaveStatus=? where id=?");
		
			pstmt.setString(1, bean.getLeaveCode());
			pstmt.setString(2, bean.getEmployeeName());
			pstmt.setDate(3, new java.sql.Date(bean.getStartDate().getTime()));
			pstmt.setDate(4, new java.sql.Date(bean.getEndDate().getTime()));

			pstmt.setString(5, bean.getLeaveStatus());
			pstmt.setLong(6, bean.getId());

			int i=pstmt.executeUpdate();
			System.out.println( i + "row affected");
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
	
	//find by pk
	
	
	public LeaveBean findByPk(long pk) {

	    Connection conn = null;
	    LeaveBean bean = null;

	    try {
	        conn = JDBCDataSource.getConnection();

	        PreparedStatement pstmt = conn.prepareStatement(
	                "select * from st_leave where id=?");

	        pstmt.setLong(1, pk); 

	        ResultSet rs = pstmt.executeQuery();

	        while (rs.next()) {
	            bean = new LeaveBean();
	            bean.setId(rs.getLong(1));
	            bean.setLeaveCode(rs.getString(2));
	            bean.setEmployeeName(rs.getString(3));
	            bean.setStartDate(rs.getDate(4));
	            bean.setEndDate(rs.getDate(5));
	            bean.setLeaveStatus(rs.getString(6));
	        }

	        rs.close();        
	        pstmt.close();

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        JDBCDataSource.closeConnection(conn);
	    }

	    return bean;
	}
	
	public LeaveBean findByCode(String code) {
		Connection conn=null;
		LeaveBean bean=null;
		try {
			conn=JDBCDataSource.getConnection();
			PreparedStatement pstmt=conn.prepareStatement("select * from st_leave where leaveCode=?");
			pstmt.setString(1, code);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) {
				bean=new LeaveBean();
				bean.setId(rs.getLong(1));
				bean.setLeaveCode(rs.getString(2));
				bean.setEmployeeName(rs.getString(3));
				bean.setStartDate(rs.getDate(4));
				bean.setEndDate(rs.getDate(5));
				bean.setLeaveStatus(rs.getString(6));
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
	
	//list
	

	public List list(int pageNo,int pageSize) {
		ArrayList list=new ArrayList();
		StringBuffer sql=new StringBuffer("select * from st_leave");
		
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + " ," + pageSize);
			
		}
		
		Connection conn=null;
		LeaveBean bean=null;
		try {
			conn=JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) {
				bean=new LeaveBean();
				bean.setId(rs.getLong(1));
				bean.setLeaveCode(rs.getString(2));
				bean.setEmployeeName(rs.getString(3));
				bean.setStartDate(rs.getDate(4));
				bean.setEndDate(rs.getDate(5));
				bean.setLeaveStatus(rs.getString(6));
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
	
	//search
	
	public List search(LeaveBean bean ,int pageNo,int pageSize) {
	


StringBuffer sql=new StringBuffer("select * from st_leave where 1=1"); 
if(bean!=null) {
    if (bean.getId() > 0) {
        sql.append(" and id = " + bean.getId());
    }

    if (bean.getLeaveCode() != null && bean.getLeaveCode().length() > 0) {
        sql.append(" and code like '" + bean.getLeaveCode() + "%'");
    }

    if (bean.getEmployeeName() != null && bean.getEmployeeName().length() > 0) {
        sql.append(" and name like '" + bean.getEmployeeName() + "%'");
    }
    if (bean.getStartDate() != null) {
    	sql.append(" and startDate = '" + new java.sql.Date(bean.getStartDate().getTime()) + "'");

    }
    if (bean.getEndDate() != null) {
    	sql.append(" and endDate = '" + new java.sql.Date(bean.getEndDate().getTime()) + "'");

    }

    if (bean.getLeaveStatus() != null && bean.getLeaveStatus().length() > 0) {
        sql.append(" and product like '" + bean.getLeaveStatus() + "%'");
    }
	}
	
	
	

if (pageSize > 0) {
	pageNo = (pageNo - 1) * pageSize;
	sql.append(" limit " + pageNo + "," + pageSize);
	
}
ArrayList list=new ArrayList();
		
		Connection conn=null;
		try {
			conn=JDBCDataSource.getConnection();
			PreparedStatement pstmt=conn.prepareStatement(sql.toString());
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) {
				bean=new LeaveBean();
				bean.setId(rs.getLong(1));
				bean.setLeaveCode(rs.getString(2));
				bean.setEmployeeName(rs.getString(3));
				bean.setStartDate(rs.getDate(4));
				bean.setEndDate(rs.getDate(5));
				bean.setLeaveStatus(rs.getString(6));
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
