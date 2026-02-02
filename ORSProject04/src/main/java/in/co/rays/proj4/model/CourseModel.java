package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.CourseBean;
import in.co.rays.proj4.bean.MarksheetBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class CourseModel {
	//nextpk**************************************
	public Integer nextPk() throws DatabaseException {
		Connection conn=null;
		int pk=0;
		try {
			conn=JDBCDataSource.getConnection();
			PreparedStatement pstmt=conn.prepareStatement("select max(id) from st_course");
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) {
				pk=rs.getInt(1);
			}
			rs.close();
			pstmt.close();;
		}
		
		
	 catch (Exception e) {
		throw new DatabaseException("Exception : Exception in getting PK");
	} finally {
		JDBCDataSource.closeConnection(conn);
	}
	return pk + 1;
}
	//add*************************************************************************************************
	
	public long add(CourseBean bean) throws ApplicationException, DatabaseException, DuplicateRecordException {
		
		Connection conn=null;
		CourseBean duplicatecourseName = findByName(bean.getName());
		 if(duplicatecourseName != null) {
			 throw new DuplicateRecordException("course already exist"); }
	
		int pk=0;
		
		try {
		 pk=nextPk();
			conn=JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt=conn.prepareStatement("insert into st_course values(?,?,?,?,?,?,?,?)");
			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getName());
			pstmt.setString(3, bean.getDuration());
			pstmt.setString(4, bean.getDescription());
		
			pstmt.setString(5, bean.getCreatedBy());
			pstmt.setString(6, bean.getModifiedBy());
			pstmt.setTimestamp(7, bean.getCreatedDatetime());
			pstmt.setTimestamp(8, bean.getCreatedDatetime());
			int i=pstmt.executeUpdate();
			System.out.println(i +"row affected");
			conn.commit();
			
			pstmt.close();
			
			
		}
		
		catch (Exception e) {
			
			try {
				conn.rollback();
			} catch (Exception ex) {
			
		 throw new ApplicationException("Excetion : add rollback Exception " +ex.getMessage());
			}
		 throw new ApplicationException("Exception : Exception in add course" );
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	
		return pk;

		
		
		
		
	}
	
	//delete*************************************************************************************
	public void delete(CourseBean bean) throws ApplicationException {
		Connection conn=null;
		try{
			conn=JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt=conn.prepareStatement("delete from st_course where id=?");
			pstmt.setLong(1, bean.getId());
			int i=pstmt.executeUpdate();
		System.out.println(i +"row affected");
			conn.commit();
			pstmt.close();
		}
		catch (Exception e) {
			
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : delete rollback exception  " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete Student");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

	}
//update************************************************************************
	public void update(CourseBean bean) throws ApplicationException, DuplicateRecordException {
	
		Connection conn = null;
		CourseBean beanExist = findByName(bean.getName());
		if (beanExist != null && beanExist.getId() != bean.getId()) {
			throw new DuplicateRecordException("Course is alredy Exist");
		}
		
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("update st_course set name=?, description=?, duration=?, created_by=?, modified_by=?, created_datetime=?, modified_datetime=? where id=?");


			pstmt.setString(1, bean.getName());
			pstmt.setString(2, bean.getDescription());
			pstmt.setString(3, bean.getDuration());
			pstmt.setString(4, bean.getCreatedBy());
			pstmt.setString(5, bean.getModifiedBy());
			pstmt.setTimestamp(6, bean.getCreatedDatetime());
			pstmt.setTimestamp(7, bean.getModifiedDatetime());
			pstmt.setLong(8, bean.getId());
		int i=	pstmt.executeUpdate();
			System.out.println( i + "row affected");
			conn.commit();
			pstmt.close();

		} catch (Exception e) {
			e.printStackTrace();
			
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : update rollback Exception " + ex.getMessage());
			}
		 throw new ApplicationException("Exception in updatingcourse" );
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	
	}
	//find by pk*********************************************************
	public CourseBean FindByPK(long pk) throws ApplicationException {
		
		StringBuffer sql = new StringBuffer("select * from st_course where id=?");
		Connection conn = null;
		CourseBean bean = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new CourseBean();
				bean.setId(1);
				bean.setName(rs.getString(2));
				bean.setDuration(rs.getString(3));
				bean.setDescription(rs.getString(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setModifiedDatetime(rs.getTimestamp(8));
			}
			rs.close();

		} catch (Exception e) {
			
			throw new ApplicationException("Exception in getting course by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		
		}
		return bean;
	}
	//name*****************************************************
	public CourseBean findByName(String name) throws ApplicationException {

	
		CourseBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_course where name=?");
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new CourseBean();
				bean.setId(1);
				bean.setName(rs.getString(2));
				bean.setDuration(rs.getString(3));
				bean.setDescription(rs.getString(4));
			
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setModifiedDatetime(rs.getTimestamp(8));

			}
			rs.close();
		} catch (Exception e) {
		
		 throw new ApplicationException("Exception in getting course by name");
		} finally {
			JDBCDataSource.closeConnection(conn);
			
		}
		return bean;

	}
	//list********************************
	public List list() throws Exception {
		return list(0, 0);
	}

	public List list(int pageNo, int pageSize) throws Exception {

		

		List list = new ArrayList();

		StringBuffer sql = new StringBuffer("select * from st_course");

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + " ," + pageSize);
		}

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			ResultSet rs = pstmt.executeQuery();
			CourseBean bean;
			while (rs.next()) {
				bean = new CourseBean();

				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDuration(rs.getString(3));
				bean.setDescription(rs.getString(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setModifiedDatetime(rs.getTimestamp(8));

				list.add(bean);
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			
			throw new ApplicationException("Exception : Exception in getting lidt " + e.getMessage());

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;
	}
	
	//filter*****************************************
	public List search(CourseBean bean) throws DatabaseException, ApplicationException {
		return search(bean, 0, 0);
	}

	public List search(CourseBean bean, int pageNo, int pageSize) throws DatabaseException, ApplicationException {
	
		StringBuffer sql = new StringBuffer("Select * from st_course where 1=1");
		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}
			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" AND Name like '" + bean.getName() + "%'");
			}
			if (bean.getDescription() != null && bean.getDescription().length() > 0) {
				sql.append(" AND Description like '" + bean.getDescription() + "%'");
			}
			if (bean.getDuration() != null && bean.getDuration().length() > 0) {
				sql.append(" AND Duration like '" + bean.getDuration() + "%'");
			}
		}
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		ArrayList list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			System.out.println(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new CourseBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setDuration(rs.getString(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setModifiedDatetime(rs.getTimestamp(8));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			
			throw new ApplicationException("Exception in the search" + e.getMessage());
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		
		return list;
	}
}
