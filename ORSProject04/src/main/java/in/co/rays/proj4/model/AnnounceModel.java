package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import in.co.rays.proj4.bean.AnnounceBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class AnnounceModel {

	// ================= NEXT PK =================
	public Integer nextPk() throws DatabaseException {
		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_announcement");
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw new DatabaseException("Exception in getting PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk+1;
	}

	// ================= ADD =================
	public long add(AnnounceBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;
		int pk = 0;

		AnnounceBean existbean = findByCode(bean.getCode());

		if (existbean != null) {
			throw new DuplicateRecordException("code already exists");
		}

		try {
			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn
					.prepareStatement("insert into st_announcement values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getCode());
			pstmt.setString(3, bean.getTitle());
			pstmt.setString(4, bean.getDescription());
			pstmt.setDate(5, new java.sql.Date(bean.getPublishDate().getTime()));
			pstmt.setString(6, bean.getStatus());
			pstmt.setString(7, bean.getCreatedBy());
			pstmt.setString(8, bean.getModifiedBy());
			pstmt.setTimestamp(9, bean.getCreatedDatetime());
			pstmt.setTimestamp(10, bean.getModifiedDatetime());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add User");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk;
	}

	// ================= DELETE =================
	public void delete(AnnounceBean bean) throws ApplicationException {

		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from st_announcement where id=?");

			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();

			conn.commit();
			pstmt.close();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in delete payment");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	// ================= UPDATE =================
	public void update(AnnounceBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		AnnounceBean existbean = findByCode(bean.getCode());
		if (existbean != null && existbean.getId() != bean.getId()) {
			throw new DuplicateRecordException("Transaction ID already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn
					.prepareStatement("update st_announcement set code=?,title=?, description=?, "
							+ "publish_date=?, status=?, created_by=?, modified_by=?, "
							+ "created_datetime=?, modified_datetime=? where id=?");

		
			pstmt.setString(1, bean.getCode());
			pstmt.setString(2, bean.getTitle());
			pstmt.setString(3, bean.getDescription());
			pstmt.setDate(4, new java.sql.Date(bean.getPublishDate().getTime()));
			pstmt.setString(5, bean.getStatus());
			pstmt.setString(6, bean.getCreatedBy());
			pstmt.setString(7, bean.getModifiedBy());
			pstmt.setTimestamp(8, bean.getCreatedDatetime());
			pstmt.setTimestamp(9, bean.getModifiedDatetime());
			pstmt.setLong(10, bean.getId());

			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Update rollback exception: " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating payment");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	// ================= FIND BY PK =================
	public AnnounceBean findByPK(long pk) throws ApplicationException {

		AnnounceBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_announcement where id=?");

			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new AnnounceBean();
				bean.setId(rs.getLong(1));
				bean.setCode(rs.getString(2));
				bean.setTitle(rs.getString(3));
				bean.setDescription(rs.getString(4));
				bean.setPublishDate(rs.getDate(5));
				bean.setStatus(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDatetime(rs.getTimestamp(9));
				bean.setModifiedDatetime(rs.getTimestamp(10));
			}
			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in getting Payment by PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	// ================= FIND BY TRANSACTION ID =================
	public AnnounceBean findByCode(String code) throws ApplicationException {

		AnnounceBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_announcement where code=?");

			pstmt.setString(1, code);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new AnnounceBean();
				bean.setId(rs.getLong(1));
				bean.setCode(rs.getString(2));
				bean.setTitle(rs.getString(3));
				bean.setDescription(rs.getString(4));
				bean.setPublishDate(rs.getDate(5));
				bean.setStatus(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDatetime(rs.getTimestamp(9));
				bean.setModifiedDatetime(rs.getTimestamp(10));
			}
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in getting Payment by Transaction ID");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	// ================= LIST =================
	public List list() throws ApplicationException {
		return list(0, 0);
	}

	public List list(int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from st_announcement");
		ArrayList list = new ArrayList();

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
				AnnounceBean bean = new AnnounceBean();
				bean.setId(rs.getLong(1));
				bean.setCode(rs.getString(2));
				bean.setTitle(rs.getString(3));
				bean.setDescription(rs.getString(4));
				bean.setPublishDate(rs.getDate(5));
				bean.setStatus(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDatetime(rs.getTimestamp(9));
				bean.setModifiedDatetime(rs.getTimestamp(10));
				list.add(bean);
			
			}
			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in getting Payment list");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;
	}

	// ================= SEARCH =================
	public List search(AnnounceBean bean, int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from st_announcement where 1=1");

		if (bean != null) {

			if (bean.getId() > 0) {
				sql.append(" and id=" + bean.getId());
			}

			if (bean.getCode() != null && bean.getCode().trim().length() > 0) {
				sql.append(" and code like '" + bean.getCode() + "%'");
			}

			if (bean.getTitle() != null && bean.getTitle().trim().length() > 0) {
				sql.append(" and title like '" + bean.getTitle() + "%'");
			}
			if (bean.getDescription()!= null && bean.getDescription().trim().length() > 0) {
				sql.append(" and description like '" + bean.getDescription() + "%'");
			}

			if (bean.getPublishDate() != null) {
				Date d = new java.sql.Date(bean.getPublishDate().getTime());
				sql.append(" and publish_date='" + d + "'");
			}

			if (bean.getStatus() != null && bean.getStatus().trim().length() > 0) {
				sql.append(" and payment_status like '" + bean.getStatus() + "%'");
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
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				 bean = new AnnounceBean();
				bean.setId(rs.getLong(1));
				bean.setCode(rs.getString(2));
				bean.setTitle(rs.getString(3));
				bean.setDescription(rs.getString(4));
				bean.setPublishDate(rs.getDate(5));
				bean.setStatus(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDatetime(rs.getTimestamp(9));
				bean.setModifiedDatetime(rs.getTimestamp(10));
				list.add(bean);
			}
			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;
	}

}