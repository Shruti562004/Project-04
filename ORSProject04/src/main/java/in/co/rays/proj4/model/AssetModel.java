package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import in.co.rays.proj4.bean.AssetBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class AssetModel {

	// ================= NEXT PK =================
	public Integer nextPk() throws DatabaseException {
		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_asset");
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
		return ++pk;
	}

	// ================= ADD =================
	public long add(AssetBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;
		int pk = 0;

		AssetBean existbean = findByCode(bean.getAssetCode());

		if (existbean != null) {
			throw new DuplicateRecordException("Login Id already exists");
		}

		try {
			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn
					.prepareStatement("insert into st_asset values(?, ?, ?, ?, ?,?)");

			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getAssetCode());
			pstmt.setString(3, bean.getAssetName());
			pstmt.setString(4, bean.getAssignedTo());
			pstmt.setDate(5, new java.sql.Date(bean.getIssueDate().getTime()));
			pstmt.setString(6, bean.getAssetStatus());
			
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
	public void delete(AssetBean bean) throws ApplicationException {

		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from st_asset where id=?");

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
	public void update(AssetBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		AssetBean existbean = findByCode(bean.getAssetCode());
		if (existbean != null && existbean.getId() != bean.getId()) {
			throw new DuplicateRecordException("Transaction ID already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn
					.prepareStatement("update st_asset set asset_code=?,asset_name=?,assigned_to=?,issue_date=? where id=?");

			pstmt.setString(1, bean.getAssetCode());
			pstmt.setString(2, bean.getAssetName());
			pstmt.setString(3, bean.getAssignedTo());
			pstmt.setDate(4, new java.sql.Date(bean.getIssueDate().getTime()));
			pstmt.setString(5, bean.getAssetStatus());
			pstmt.setLong(6, bean.getId());

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
	public AssetBean findByPK(long pk) throws ApplicationException {

		AssetBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_asset where id=?");

			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new AssetBean();
				bean.setId(rs.getLong(1));
				bean.setAssetCode(rs.getString(2));
				bean.setAssetName(rs.getString(3));
				bean.setAssignedTo(rs.getString(4));
				bean.setIssueDate(rs.getDate(5));
				bean.setAssetStatus(rs.getString(6));
				
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
	public AssetBean findByCode(String code) throws ApplicationException {

		AssetBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_asset where asset_code=?");

			pstmt.setString(1, code);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new AssetBean();
				bean.setId(rs.getLong(1));
				bean.setAssetCode(rs.getString(2));
				bean.setAssetName(rs.getString(3));
				bean.setAssignedTo(rs.getString(4));
				bean.setIssueDate(rs.getDate(5));
				bean.setAssetStatus(rs.getString(6));
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

		StringBuffer sql = new StringBuffer("select * from st_asset");
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
				AssetBean	bean = new AssetBean();
				bean.setId(rs.getLong(1));
				bean.setAssetCode(rs.getString(2));
				bean.setAssetName(rs.getString(3));
				bean.setAssignedTo(rs.getString(4));
				bean.setIssueDate(rs.getDate(5));
				bean.setAssetStatus(rs.getString(6));
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
	public List search(AssetBean bean, int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from st_asset where 1=1");

		if (bean != null) {

			if (bean.getId() > 0) {
				sql.append(" and id=" + bean.getId());
			}

			if (bean.getAssetCode() != null && bean.getAssetCode().trim().length() > 0) {
				sql.append(" and asset_code like '" + bean.getAssetCode() + "%'");
			}

			if (bean.getAssetName() != null && bean.getAssetName().trim().length() > 0) {
				sql.append(" and asset_name like '" + bean.getAssetName() + "%'");
			}
			if (bean.getAssignedTo() != null && bean.getAssignedTo().trim().length() > 0) {
				sql.append(" and assigned_to like '" + bean.getAssignedTo() + "%'");
			}

			
			if (bean.getIssueDate() != null) {
				Date d = new java.sql.Date(bean.getIssueDate().getTime());
				sql.append(" and issue_date='" + d + "'");
			}

			if (bean.getAssetStatus() != null && bean.getAssetStatus().trim().length() > 0) {
				sql.append(" and asset_status like '" + bean.getAssetStatus() + "%'");
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
					bean = new AssetBean();
				bean.setId(rs.getLong(1));
				bean.setAssetCode(rs.getString(2));
				bean.setAssetName(rs.getString(3));
				bean.setAssignedTo(rs.getString(4));
				bean.setIssueDate(rs.getDate(5));
				bean.setAssetStatus(rs.getString(6));
				
				list.add(bean);
			}
			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in search Payment");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;
	}

}