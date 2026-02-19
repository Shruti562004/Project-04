package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.FollowupBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class FollowupModel {

	public Integer nextPk() {
		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_followup");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
		}

		catch (Exception e) {

			e.printStackTrace();
		}

		finally {
			JDBCDataSource.closeConnection(conn);

		}
		return pk + 1;
	}

	public long add(FollowupBean bean) throws ApplicationException, DuplicateRecordException, SQLException {
		int pk = 0;
		Connection conn = null;
		FollowupBean existbean = findByDoctor(bean.getDoctor());

		if (existbean != null) {
			throw new DuplicateRecordException("name already exists");
		}

		try {
			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn
					.prepareStatement("insert into st_followup values(?, ?, ?, ?, ?, ?, ?, ?, ?)");

			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getPatient());
			pstmt.setString(3, bean.getDoctor());
			pstmt.setDate(4, new java.sql.Date(bean.getDob().getTime()));
			pstmt.setString(5, bean.getFees());
			pstmt.setString(6, bean.getCreatedBy());
			pstmt.setString(7, bean.getModifiedBy());
			pstmt.setTimestamp(8, bean.getCreatedDatetime());
			pstmt.setTimestamp(9, bean.getModifiedDatetime());
			int i = pstmt.executeUpdate();

			System.out.println(i + "row affected");
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			try {
				conn.rollback();
			}

			catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add User");
		}

		finally {

			JDBCDataSource.closeConnection(conn);
		}

		return pk;
	}

	public void update(FollowupBean bean) throws SQLException {
		Connection conn = null;
	

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"update st_followup set patient=?, dooctor=? , dob=?,fees=?, created_by=?, modified_by=?, created_datetime=?, modified_datetime=? where id=?");

			pstmt.setString(1, bean.getPatient());
			pstmt.setString(2, bean.getDoctor());
			pstmt.setDate(3, new java.sql.Date(bean.getDob().getTime()));
			pstmt.setString(4, bean.getFees());
			pstmt.setString(5, bean.getCreatedBy());
			pstmt.setString(6, bean.getModifiedBy());
			pstmt.setTimestamp(7, bean.getCreatedDatetime());
			pstmt.setTimestamp(8, bean.getModifiedDatetime());
			pstmt.setLong(9, bean.getId());
			int i = pstmt.executeUpdate();
			conn.commit();

		}

		catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
		}

		finally {
			JDBCDataSource.closeConnection(conn);

		}

	}

	public FollowupBean findByPk(long pk) throws SQLException {
		Connection conn = null;
		FollowupBean bean = null;

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("select * from st_followup where id=?");
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new FollowupBean();
				bean.setId(rs.getLong(1));
				bean.setPatient(rs.getString(2));
				bean.setDoctor(rs.getString(3));
				bean.setDob(rs.getDate(4));
				bean.setFees(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
			}

		} catch (Exception e) {
			
			e.printStackTrace();
		}

		finally {
			JDBCDataSource.closeConnection(conn);

		}
		return bean;
	}

	public FollowupBean findByDoctor(String doctor) throws SQLException {
		Connection conn = null;
		FollowupBean bean = null;

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("select * from st_followup where doctor=?");

			pstmt.setString(1, doctor);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new FollowupBean();
				bean.setId(rs.getLong(1));
				bean.setPatient(rs.getString(2));
				bean.setDoctor(rs.getString(3));
				bean.setDob(rs.getDate(4));
				bean.setFees(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			JDBCDataSource.closeConnection(conn);

		}
		return bean;
	}

	public List list(int pageNo, int pageSize) throws SQLException {

		StringBuffer sql = new StringBuffer("select * from st_followup");
		ArrayList list = new ArrayList();
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" LIMIT " + pageNo + "," + pageSize);
		}

		Connection conn = null;
		FollowupBean bean = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new FollowupBean();
				bean.setId(rs.getLong(1));
				bean.setPatient(rs.getString(2));
				bean.setDoctor(rs.getString(3));
				bean.setDob(rs.getDate(4));
				bean.setFees(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
				list.add(bean);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			JDBCDataSource.closeConnection(conn);

		}

		return list;

	}

	// search()
	public List search(FollowupBean bean, int pageNo, int pageSize) throws SQLException {

		StringBuffer sql = new StringBuffer("select * from st_followup where 1=1");
		ArrayList list = new ArrayList();

		if (bean != null) {

			if (bean.getId() > 0) {
				sql.append(" AND id=" + bean.getId());
			}

			if (bean.getPatient() != null && bean.getPatient().trim().length() > 0) {
				sql.append(" AND patient LIKE '" + bean.getPatient() + "%'");
			}

			if (bean.getDoctor() != null && bean.getDoctor().trim().length() > 0) {
				sql.append(" AND doctor LIKE '" + bean.getDoctor() + "%'");
			}

			if (bean.getDob() != null) {
				sql.append(" and dob = '" + new java.sql.Date(bean.getDob().getTime()) + "'");

			}

			if (bean.getFees() != null && bean.getFees().trim().length() > 0) {
				sql.append(" AND fees LIKE '" + bean.getFees() + "%'");
			}

		}
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" LIMIT " + pageNo + "," + pageSize);
		}

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new FollowupBean();
				bean.setId(rs.getLong(1));
				bean.setPatient(rs.getString(2));
				bean.setDoctor(rs.getString(3));
				bean.setDob(rs.getDate(4));
				bean.setFees(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
				list.add(bean);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			JDBCDataSource.closeConnection(conn);

		}

		return list;

	}

}
