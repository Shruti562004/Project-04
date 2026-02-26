package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.FuelBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class FuelModel {

	// ================= NEXT PK =================
	public Integer nextPk() throws DatabaseException {

		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_fuel");
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				pk = rs.getInt(1);
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new DatabaseException("Exception in getting PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk + 1;
	}

	// ================= ADD =================
	public long add(FuelBean bean)
			throws ApplicationException, DatabaseException, DuplicateRecordException {

		Connection conn = null;

		FuelBean duplicate = findByCode(bean.getEntryCode());
		if (duplicate != null) {
			throw new DuplicateRecordException("Entry Code already exists");
		}

		int pk = 0;

		try {
			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn
					.prepareStatement("insert into st_fuel values(?,?,?,?,?)");

			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getEntryCode());
			pstmt.setString(3, bean.getVehicleNumber());
			pstmt.setDouble(4, bean.getFuelAmount());
			pstmt.setDate(5, new java.sql.Date(bean.getFuelDate().getTime()));

			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();

		} catch (Exception e) {

			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Add rollback exception " + ex.getMessage());
			}

			throw new ApplicationException("Exception in adding Fuel");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;
	}

	// ================= DELETE =================
	public void delete(FuelBean bean) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from st_fuel where id=?");
			pstmt.setInt(1, bean.getId());

			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();

		} catch (Exception e) {

			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Delete rollback exception " + ex.getMessage());
			}

			throw new ApplicationException("Exception in deleting Fuel");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	// ================= UPDATE =================
	public void update(FuelBean bean)
			throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		FuelBean exist = findByCode(bean.getEntryCode());
		if (exist != null && exist.getId() != bean.getId()) {
			throw new DuplicateRecordException("Entry Code already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"update st_fuel set entry_code=?, vehicle_number=?, fuel_amount=?, fuel_date=? where id=?");

			pstmt.setString(1, bean.getEntryCode());
			pstmt.setString(2, bean.getVehicleNumber());
			pstmt.setDouble(3, bean.getFuelAmount());
			pstmt.setDate(4, new java.sql.Date(bean.getFuelDate().getTime()));
			pstmt.setLong(5, bean.getId());

			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();

		} catch (Exception e) {

			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Update rollback exception " + ex.getMessage());
			}

			throw new ApplicationException("Exception in updating Fuel");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	// ================= FIND BY PK =================
	public FuelBean findByPK(long pk) throws ApplicationException {

		Connection conn = null;
		FuelBean bean = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn
					.prepareStatement("select * from st_fuel where id=?");

			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new FuelBean();
				bean.setId(rs.getInt(1));
				bean.setEntryCode(rs.getString(2));
				bean.setVehicleNumber(rs.getString(3));
				bean.setFuelAmount(rs.getDouble(4));
				bean.setFuelDate(rs.getDate(5));
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in getting Fuel by PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	// ================= FIND BY CODE =================
	public FuelBean findByCode(String code) throws ApplicationException {

		Connection conn = null;
		FuelBean bean = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn
					.prepareStatement("select * from st_fuel where entry_code=?");

			pstmt.setString(1, code);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new FuelBean();
				bean.setId(rs.getInt(1));
				bean.setEntryCode(rs.getString(2));
				bean.setVehicleNumber(rs.getString(3));
				bean.setFuelAmount(rs.getDouble(4));
				bean.setFuelDate(rs.getDate(5));
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in getting Fuel by Code");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	// ================= LIST =================
	public List<FuelBean> list(int pageNo, int pageSize) throws Exception {

		List<FuelBean> list = new ArrayList<>();
		StringBuffer sql = new StringBuffer("select * from st_fuel");

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			FuelBean bean = new FuelBean();
			bean.setId(rs.getInt(1));
			bean.setEntryCode(rs.getString(2));
			bean.setVehicleNumber(rs.getString(3));
			bean.setFuelAmount(rs.getDouble(4));
			bean.setFuelDate(rs.getDate(5));
			list.add(bean);
		}

		rs.close();
		pstmt.close();
		JDBCDataSource.closeConnection(conn);

		return list;
	}

	// ================= SEARCH =================
	public List<FuelBean> search(FuelBean bean, int pageNo, int pageSize)
			throws DatabaseException, ApplicationException {

		StringBuffer sql = new StringBuffer("select * from st_fuel where 1=1");

		if (bean != null) {

			if (bean.getId() > 0) {
				sql.append(" and id = " + bean.getId());
			}

			if (bean.getEntryCode() != null && bean.getEntryCode().length() > 0) {
				sql.append(" and entry_code like '" + bean.getEntryCode() + "%'");
			}

			if (bean.getVehicleNumber() != null && bean.getVehicleNumber().length() > 0) {
				sql.append(" and vehicle_number like '" + bean.getVehicleNumber() + "%'");
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		List<FuelBean> list = new ArrayList<>();
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				FuelBean b = new FuelBean();
				b.setId(rs.getInt(1));
				b.setEntryCode(rs.getString(2));
				b.setVehicleNumber(rs.getString(3));
				b.setFuelAmount(rs.getDouble(4));
				b.setFuelDate(rs.getDate(5));
				list.add(b);
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in search " + e.getMessage());
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}
}