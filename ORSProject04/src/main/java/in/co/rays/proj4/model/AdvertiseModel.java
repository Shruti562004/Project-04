package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


import in.co.rays.proj4.bean.AdvertiseBean;


import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class AdvertiseModel {
	// nextPk**********************************************************

	public Integer nextPk() throws DatabaseException {

		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_advertisement");
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				pk = rs.getInt(1);

			}
			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new DatabaseException("Exception : Exception in getting PK");
		}

		finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk + 1;
	}

	// add********************************
	public long add(AdvertiseBean bean) throws ApplicationException, DuplicateRecordException {
		Connection conn = null;
		int pk = 0;

		AdvertiseBean existBean = findByName(bean.getName());

		if (existBean != null) {
			throw new DuplicateRecordException("advertisename already exists");
		}

		

		try {
			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("insert into st_advertisement values(?, ?, ?, ?)");
			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getName());
			pstmt.setDouble(3, bean.getBudget());
			pstmt.setDate(4, new java.sql.Date(bean.getStartDate().getTime()));
			
			int i = pstmt.executeUpdate();
			System.out.println(i + "row affected");
			conn.commit();
			pstmt.close();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in add Student");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk;
	}

//delete***************************************************

	public void delete(AdvertiseBean bean) throws ApplicationException {
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("delete from st_advertisement where id=?");
			pstmt.setLong(1, bean.getId());
			int i = pstmt.executeUpdate();
			System.out.println(i + " row affected");
			conn.commit();
			pstmt.close();
		} catch (Exception e) {

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

	// update*********************************************************

	public void update(AdvertiseBean bean) throws ApplicationException, DuplicateRecordException {
		Connection conn = null;


		AdvertiseBean existBean = findByName(bean.getName());

		if (existBean != null && existBean.getId() != bean.getId()) {
			throw new DuplicateRecordException(" is already exist");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(
					"update st_advertisement set name=? , budget=? , start_date=?  where id=?");
			pstmt.setString(1, bean.getName());
			pstmt.setDouble(2, bean.getBudget());
			pstmt.setDate(3, new java.sql.Date(bean.getStartDate().getTime()));
			pstmt.setLong(4, bean.getId());

			int i = pstmt.executeUpdate();
			System.out.println(i + "row affected");
			conn.commit();
			pstmt.close();
		}

		catch (Exception e) {

			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback  exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating Student");
		}

		finally {
			JDBCDataSource.closeConnection(conn);
		}

	}

	// findbypk********************************************************************

	public AdvertiseBean findByPk(long pk) throws ApplicationException {
		Connection conn = null;
		AdvertiseBean bean = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM st_advertisement WHERE id=?");
			pstmt.setLong(1, pk);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new AdvertiseBean();
				bean.setId(rs.getInt(1));
				bean.setName(rs.getString(2));
				bean.setBudget(rs.getDouble(3));
				bean.setStartDate(rs.getDate(4));
				
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in getting Student by PK: " + e.getMessage());
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	// find by email**************************************

	public AdvertiseBean findByName(String name) throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from st_advertisement where name=?");
		AdvertiseBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
			
				bean = new AdvertiseBean();
				bean.setId(rs.getInt(1));
				bean.setName(rs.getString(2));
				bean.setBudget(rs.getDouble(3));
				bean.setStartDate(rs.getDate(4));
				
			}
			rs.close();
		} catch (Exception e) {

			throw new ApplicationException("Exception : Exception in getting User by Email");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	// all data without pagination

	public List list() throws ApplicationException {
		return list(0, 0);
	}

	// all data with pagination

	public List list(int pageNo, int pageSize) throws ApplicationException {
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from st_advertisement");

		if (pageNo > 0) {

			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + " , " + pageSize);

		}
		Connection conn = null;
		AdvertiseBean bean = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new AdvertiseBean();
				bean.setId(rs.getInt(1));
				bean.setName(rs.getString(2));
				bean.setBudget(rs.getDouble(3));
				bean.setStartDate(rs.getDate(4));

				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {

			throw new ApplicationException("Exception : Exception in getting list of Student");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}

	// filter
	public List search(AdvertiseBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}

	public List<AdvertiseBean> search(AdvertiseBean bean, int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from st_advertisement where 1 = 1");

		if (bean != null) {
			if (bean.getId() > 0)
				sql.append(" and id = " + bean.getId());
		
			if (bean.getName() != null && bean.getName().length() > 0)
				sql.append(" and name like '" + bean.getName() + "%'");
			if (bean.getBudget() > 0)
				sql.append(" and budget = " + bean.getBudget());
			
			/*if (bean.getStartDate() != null && bean.getStartDate().getDate() > 0)
				sql.append(" and start_date = " + bean.getStartDate());
			*/
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + ", " + pageSize);
		}

		ArrayList list = new ArrayList();
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new AdvertiseBean();
				bean.setId(rs.getInt(1));
				bean.setName(rs.getString(2));
				bean.setBudget(rs.getDouble(3));
				bean.setStartDate(rs.getDate(4));

				list.add(bean);
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in search Student");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}
}