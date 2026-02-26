package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.FeedBackBean;
import in.co.rays.proj4.util.JDBCDataSource;

public class FeedbackModel {
	
	public Integer nextPk() {
		Connection conn=null;
		int pk=0;
		try {
			conn=JDBCDataSource.getConnection();
			PreparedStatement pstmt=conn.prepareStatement("select max(id) from st_feedback");
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) {
				pk=rs.getInt(1);
			}
			
		pstmt.close();
			
		}
		
		catch(Exception e ){
			e.printStackTrace();
		}
		finally{
			JDBCDataSource.closeConnection(conn);
		}
		return pk+1;
		
	}
	
	
	public long add(FeedBackBean bean) throws SQLException {
		Connection conn=null;
		int pk=0;
		
		try {
			conn=JDBCDataSource.getConnection();
			pk=nextPk();
			PreparedStatement pstmt=conn.prepareStatement("insert into st_feedback values(?,?,?,?,?)");
			conn.setAutoCommit(false);
			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getCode());
			pstmt.setString(3, bean.getCandidateName());
			pstmt.setString(4, bean.getInterviewerName());
			pstmt.setString(5, bean.getFeedbackStatus());
			int i=pstmt.executeUpdate();
			System.out.println( i +"row affected");
			conn.commit();
			pstmt.close();
			
		}
		
		catch(Exception e ){
			conn.rollback();
			e.printStackTrace();
		}
		finally{
			JDBCDataSource.closeConnection(conn);
		}
		return pk;
		
	}
	
	public void delete(FeedBackBean bean) throws SQLException {
		Connection conn=null;
		try {
			conn=JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt=conn.prepareStatement("delete from st_feedback where id=?");
		
			pstmt.setLong(1, bean.getId());
			int i=pstmt.executeUpdate();
			System.out.println( i +"row affected");
			conn.commit();
			pstmt.close();
			
		}
		catch(Exception e ){
			conn.rollback();
			e.printStackTrace();
		}
		finally{
			JDBCDataSource.closeConnection(conn);
		}
	}
	
	public void update(FeedBackBean bean) throws SQLException {
		Connection conn=null;
		try {
			conn=JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt=conn.prepareStatement("update st_feedback  set code=?, candidate_name=?,interviewer_name=?,feedback_status=? where id=?");
			pstmt.setString(1, bean.getCode());
			pstmt.setString(2, bean.getCandidateName());
			pstmt.setString(3, bean.getInterviewerName());
			pstmt.setString(4, bean.getFeedbackStatus());
			pstmt.setLong(5, bean.getId());
			int i=pstmt.executeUpdate();
			System.out.println( i +"row affected");
			conn.commit();
			pstmt.close();
		
	}
		catch(Exception e ){
			conn.rollback();
			e.printStackTrace();
		}
		finally{
			JDBCDataSource.closeConnection(conn);
		}

}
	
	public FeedBackBean findByPk(int pk) throws SQLException {
		Connection conn=null;
		FeedBackBean bean=null;
		try {
			conn=JDBCDataSource.getConnection();
			
			PreparedStatement pstmt=conn.prepareStatement("select * from st_feedback where id=?");
			pstmt.setInt(1, pk);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) {
				bean=new FeedBackBean();
				bean.setId(rs.getLong(1));
				bean.setCode(rs.getString(2));
				bean.setCandidateName(rs.getString(3));
				bean.setInterviewerName(rs.getString(4));
				bean.setFeedbackStatus(rs.getString(5));
			}
		
	}
		
		catch(Exception e ){
			conn.rollback();
			e.printStackTrace();
		}
		finally{
			JDBCDataSource.closeConnection(conn);
		}
		
		return bean;
	}
	
	public FeedBackBean findByCode(String code) throws SQLException {
		Connection conn=null;
		FeedBackBean bean=null;
		try {
			conn=JDBCDataSource.getConnection();
			
			PreparedStatement pstmt=conn.prepareStatement("select * from st_feedback where code=?");
			pstmt.setString(1, code);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) {
				bean=new FeedBackBean();
				bean.setId(rs.getLong(1));
				bean.setCode(rs.getString(2));
				bean.setCandidateName(rs.getString(3));
				bean.setInterviewerName(rs.getString(4));
				bean.setFeedbackStatus(rs.getString(5));
			}
		
	}
		
		catch(Exception e ){
			conn.rollback();
			e.printStackTrace();
		}
		finally{
			JDBCDataSource.closeConnection(conn);
		}
		
		return bean;
	}
	

	
	public List list(int pageNo , int pageSize) throws SQLException {
		
		StringBuffer sql=new StringBuffer("select * from st_feedback");
		ArrayList list=new ArrayList();
		
	if(pageSize >0) {
		pageNo=(pageNo-1)*pageSize;
		
		sql.append(" limit " + pageNo + "," +pageSize);
	}
	
	Connection conn=null;
	FeedBackBean bean=null;
	try {
		conn=JDBCDataSource.getConnection();
		
		PreparedStatement pstmt=conn.prepareStatement(sql.toString());
		
		ResultSet rs=pstmt.executeQuery();
		while(rs.next()) {
			bean=new FeedBackBean();
			bean.setId(rs.getLong(1));
			bean.setCode(rs.getString(2));
			bean.setCandidateName(rs.getString(3));
			bean.setInterviewerName(rs.getString(4));
			bean.setFeedbackStatus(rs.getString(5));
			list.add(bean);
		}
	
}
	
	catch(Exception e ){
		conn.rollback();
		e.printStackTrace();
	}
	finally{
		JDBCDataSource.closeConnection(conn);
	}
	
	
	return list;
		
		
	}
	
	public List search(FeedBackBean bean,int pageNo,int pageSize) throws SQLException {
		
		StringBuffer sql=new StringBuffer("select * from st_feedback where 1=1");
		if(bean!=null) {
			if(bean.getId()>0) {
				sql.append(" and id  = " +bean.getId());
			}
			
			if(bean.getCode()!=null && bean.getCode().length()>0){
				sql.append(" and code like'" +bean.getCode() +"%'");
			}
			
			if(bean.getCandidateName()!=null && bean.getCandidateName().length()>0){
				sql.append(" and candidate_name like'" +bean.getCandidateName() +"%'");
			}
			if(bean.getInterviewerName()!=null && bean.getInterviewerName().length()>0){
				sql.append(" and interviewer_name like'" +bean.getInterviewerName() +"%'");
			}
			if(bean.getFeedbackStatus()!=null && bean.getFeedbackStatus().length()>0){
				sql.append(" and feedback_status like'" +bean.getFeedbackStatus() +"%'");
			}
		}
		ArrayList list=new ArrayList();
		
		if(pageSize >0) {
			pageNo=(pageNo-1)*pageSize;
			
			sql.append(" limit " + pageNo + "," +pageSize);
		}
		
		Connection conn=null;
	
		try {
			conn=JDBCDataSource.getConnection();
			
			PreparedStatement pstmt=conn.prepareStatement(sql.toString());
			
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) {
				bean=new FeedBackBean();
				bean.setId(rs.getLong(1));
				bean.setCode(rs.getString(2));
				bean.setCandidateName(rs.getString(3));
				bean.setInterviewerName(rs.getString(4));
				bean.setFeedbackStatus(rs.getString(5));
				list.add(bean);
			}
		}
			catch(Exception e ){
				conn.rollback();
				e.printStackTrace();
			}
			finally{
				JDBCDataSource.closeConnection(conn);
			}
			
			
			return list;
				
	}
}
