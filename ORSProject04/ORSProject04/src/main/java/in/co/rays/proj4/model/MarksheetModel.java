package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.MarksheetBean;
import in.co.rays.proj4.bean.StudentBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class MarksheetModel {
	//nextPK*******************************************************************
	public Integer nextPk(){
		
		Connection conn=null;
		int pk=0;
		
		try {
			conn=JDBCDataSource.getConnection();
			PreparedStatement pstmt=conn.prepareStatement("select max(id) from st_marksheet");
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) {
				pk=rs.getInt(1);
				
			}
			
			rs.close();
			pstmt.close();
		}
		catch(Exception e) {
			
		}
		
		finally {
			JDBCDataSource.closeConnection(conn);
			
		
		}
		
		return pk+1;
	}

	//add///////////////////////////
	
	public long add(MarksheetBean bean) throws ApplicationException, DuplicateRecordException {
	int pk=nextPk();
	Connection conn=null;


	StudentModel studentModel = new StudentModel();
	StudentBean studentbean = studentModel.findByPk(bean.getStudentId());
	bean.setName(studentbean.getFirstName() + " " + studentbean.getLastName());

	MarksheetBean duplicateMarksheet = findByRollNo(bean.getRollNo());

	if (duplicateMarksheet != null) {
		throw new DuplicateRecordException("Roll Number already exists");
	}

	try {
		conn=JDBCDataSource.getConnection();
		conn.setAutoCommit(false);
		PreparedStatement pstmt= conn.prepareStatement("insert into st_marksheet values(?,?,?,?,?,?,?,?,?,?,?)");
		pstmt.setInt(1, pk);
		pstmt.setString(2, bean.getRollNo());
		pstmt.setLong(3,bean.getStudentId());
		pstmt.setString(4, bean.getName() );
		pstmt.setInt(5, bean.getPhysics());
		pstmt.setInt(6, bean.getChemistry());
		pstmt.setInt(7, bean.getMaths());
		pstmt.setString(8, bean.getCreatedBy());
		pstmt.setString(9, bean.getModifiedBy());
		pstmt.setTimestamp(10, bean.getCreatedDatetime());
		pstmt.setTimestamp(11, bean.getModifiedDatetime());
		int i=pstmt.executeUpdate();
		
		System.out.println(i +"row affected");
		conn.commit();
		pstmt.close();
		
	}
	catch (Exception e) {
	
		try {
			conn.rollback();
		} catch (Exception ex) {
			throw new ApplicationException("add rollback exception " + ex.getMessage());
		}
		throw new ApplicationException("Exception in add marksheet");
	} finally {
		JDBCDataSource.closeConnection(conn);
	}
	
	return pk;
		
		
	}
	//delete******************************************
	public void delete(MarksheetBean bean) throws ApplicationException {
		Connection conn=null;
		try {
			
			conn=JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt=conn.prepareStatement("delete from st_marksheet where id=?");
			pstmt.setLong(1, bean.getId());
			int i=pstmt.executeUpdate();
			System.out.println(i + "row affected");
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
	//update************************************
	
	public void update(MarksheetBean bean) throws ApplicationException, DuplicateRecordException {
		Connection conn=null;
		MarksheetBean beanExist = findByRollNo(bean.getRollNo());

		if (beanExist != null && beanExist.getId() != bean.getId()) {
			throw new DuplicateRecordException("Roll No is already exist");
		}

		StudentModel studentModel = new StudentModel();
		StudentBean studentbean = studentModel.findByPk(bean.getStudentId());
		bean.setName(studentbean.getFirstName() + " " + studentbean.getLastName());
		try{
			conn=JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt= conn.prepareStatement("update st_marksheet set roll_no =? , student_id=? , name=? , physics=? , chemistry=? , maths=? , created_by=? , modified_by=? ,create_datetime=? , modified_datetime=? where id =?");
			pstmt.setString(1, bean.getRollNo());
			pstmt.setLong(2, bean.getStudentId());
			pstmt.setString(3, bean.getName());
			pstmt.setInt(4, bean.getPhysics());
			pstmt.setInt(5, bean.getChemistry());
			pstmt.setInt(6, bean.getMaths());
			pstmt.setString(7, bean.getCreatedBy());
			pstmt.setString(8, bean.getModifiedBy());
			pstmt.setTimestamp(9, bean.getCreatedDatetime());
			pstmt.setTimestamp(10, bean.getModifiedDatetime());
			pstmt.setLong(11, bean.getId());
			int i=pstmt.executeUpdate();
			System.out.println(i + "row affected");
			conn.commit();
			pstmt.close();
			
			
		}
		catch (Exception e) {
		
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Update rollback exception" + ex.getMessage());

			}
			e.printStackTrace();
			throw new ApplicationException("Exception id updating Marksheet");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		
	}
	//find by pk***********************************************************
	
	public MarksheetBean findByPK(Long pk) throws ApplicationException {
		

		

		MarksheetBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_marksheet where id=?");
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new MarksheetBean();
				bean.setId(rs.getLong(1));
				bean.setRollNo(rs.getString(2));
				bean.setStudentId(1);
				bean.setName(rs.getString(4));
				bean.setPhysics(rs.getInt(5));
				bean.setChemistry(rs.getInt(6));
				bean.setMaths(rs.getInt(7));
				bean.setCreatedBy(rs.getString(8));
				bean.setModifiedBy(rs.getString(9));
				bean.setCreatedDatetime(rs.getTimestamp(10));
				bean.setModifiedDatetime(rs.getTimestamp(11));
			}
			rs.close();
		} catch (Exception e) {
		
			throw new ApplicationException("Exception in getting marksheet by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}
	
	//rollno********************************

	public MarksheetBean findByRollNo(String rollNo) throws ApplicationException {
		

		
		MarksheetBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select  * from  st_marksheet where roll_no=?");
			pstmt.setString(1, rollNo);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new MarksheetBean();
				bean.setId(rs.getLong(1));
				bean.setRollNo(rs.getString(2));
				bean.setStudentId(rs.getLong(3));;
				bean.setName(rs.getString(4));
				
				bean.setPhysics(rs.getInt(5));
				bean.setChemistry(rs.getInt(6));
				bean.setMaths(rs.getInt(7));
				bean.setCreatedBy(rs.getString(8));
				bean.setModifiedBy(rs.getString(9));
				bean.setCreatedDatetime(rs.getTimestamp(10));
				bean.setModifiedDatetime(rs.getTimestamp(11));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			
			throw new ApplicationException("Exception in getting marksheet by roll no");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		
		return bean;
	}
//list******************** all data
	public List list(int pageNo, int pageSize) throws ApplicationException {


		List list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from st_marksheet");
		// if page size is greater than zero then apply pagination
		if (pageSize > 0) { 
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				MarksheetBean bean = new MarksheetBean();
				bean.setId(rs.getLong(1));
				bean.setRollNo(rs.getString(2));
				bean.setStudentId(rs.getLong(3));
				bean.setName(rs.getString(4));
				bean.setPhysics(rs.getInt(5));
				bean.setChemistry(rs.getInt(6));
				bean.setMaths(rs.getInt(7));
				bean.setCreatedBy(rs.getString(8));
				bean.setModifiedBy(rs.getString(9));
				bean.setCreatedDatetime(rs.getTimestamp(10));
				bean.setModifiedDatetime(rs.getTimestamp(11));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			
			throw new ApplicationException("Exception in getting list of Marksheet");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		
		return list;

	}
	
	//filter all data*******************************************************************
public List search(MarksheetBean bean, int pageNo, int pageSize) throws ApplicationException {



		StringBuffer sql = new StringBuffer("select * from st_marksheet where 1=1");

		if (bean != null) {
			
			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}
			if (bean.getRollNo() != null && bean.getRollNo().length() > 0) {
				sql.append(" AND roll_no like '" + bean.getRollNo() + "%'");
			}
			if (bean.getName() != null && bean.getName().length() > 0) {
				
				sql.append(" AND name like '" + bean.getName() + "%'");
			}
			if (bean.getPhysics() != null && bean.getPhysics() > 0) {
				sql.append(" AND physics = " + bean.getPhysics());
			}
			if (bean.getChemistry() != null && bean.getChemistry() > 0) {
				sql.append(" AND chemistry = " + bean.getChemistry());
			}
			if (bean.getMaths() != null && bean.getMaths() > 0) {
				sql.append(" AND maths = '" + bean.getMaths());
			}

		}

		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + ", " + pageSize);
			// sql.append(" limit " + pageNo + "," + pageSize);
		}

		
		
		ArrayList list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new MarksheetBean();
				bean.setId(rs.getLong(1));
				bean.setRollNo(rs.getString(2));
				bean.setStudentId(rs.getLong(3));
				bean.setName(rs.getString(4));
				bean.setPhysics(rs.getInt(5));
				bean.setChemistry(rs.getInt(6));
				bean.setMaths(rs.getInt(7));
				bean.setCreatedBy(rs.getString(8));
				bean.setModifiedBy(rs.getString(9));
				bean.setCreatedDatetime(rs.getTimestamp(10));
				bean.setModifiedDatetime(rs.getTimestamp(11));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
		
			throw new ApplicationException("Update rollback exception " + e.getMessage());
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}
//////meritlist
public List getMeritList(int pageNo, int pageSize) throws ApplicationException {
	

	ArrayList list = new ArrayList();
	StringBuffer sql = new StringBuffer("select id, roll_no, name, physics, chemistry, maths, (physics+chemistry+maths) as total from st_marksheet where physics >= 33 and chemistry >= 33 and maths >= 33 order by total desc");
		

	if (pageSize > 0) {
		pageNo = (pageNo - 1) * pageSize;
		sql.append(" limit " + pageNo + "," + pageSize);
	}
	Connection conn = null;
	try {
		conn = JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql.toString());

		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {

			MarksheetBean bean = new MarksheetBean();
			bean.setId(rs.getLong(1));
			bean.setRollNo(rs.getString(2));
			bean.setStudentId(rs.getLong(3));
			bean.setName(rs.getString(4));
			bean.setPhysics(rs.getInt(5));
			bean.setChemistry(rs.getInt(6));
			bean.setMaths(rs.getInt(7));
			bean.setCreatedBy(rs.getString(8));
			bean.setModifiedBy(rs.getString(9));
			bean.setCreatedDatetime(rs.getTimestamp(10));
			bean.setModifiedDatetime(rs.getTimestamp(11));
			list.add(bean);

		}
		rs.close();
	} catch (Exception e) {
		e.printStackTrace();
	
		throw new ApplicationException("Exception is getting meritList of Marksheet");
	} finally {
		JDBCDataSource.closeConnection(conn);
	}
	
	return list;
}
}
