package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.TaxBean;

import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class TaxModel {

	public Integer nextPk() throws DatabaseException {

		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_tax");

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				pk = rs.getInt(1);

			}

			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw new DatabaseException("Exception : Exception in getting PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk + 1;
	}



	public long add(TaxBean bean) throws ApplicationException, DuplicateRecordException {
		Connection conn = null;
		int pk = 0;
		
		TaxBean existBean =findByCode(bean.getCode());
		if(existBean!=null) {
			throw new DuplicateRecordException("code already exist");
		}
		try {
			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("insert into st_tax values (?,?,?,?,?,?,?,?,?)");
			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getCode());
			pstmt.setString(3, bean.getType());
	          pstmt.setInt(4,bean.getPercentage());
	          pstmt.setString(5, bean.getStatus());
			pstmt.setString(6, bean.getCreatedBy());
			pstmt.setString(7, bean.getModifiedBy());
			pstmt.setTimestamp(8, bean.getCreatedDatetime());
			pstmt.setTimestamp(9, bean.getModifiedDatetime());
			int i = pstmt.executeUpdate();
			if (i == 0) {
				throw new ApplicationException("Insert failed");
			}
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add User");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk;
	}

	public void update(TaxBean bean) throws ApplicationException, DuplicateRecordException {
		Connection conn = null;
		
		TaxBean existBean=findByCode(bean.getCode());
		if(existBean!=null&&existBean.getId()!=bean.getId()) {
			throw new DuplicateRecordException("already exist");
		}
		
		
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(
					"update st_tax set code=?,type=?,percentage=?,status=?, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");
		
			pstmt.setString(1, bean.getCode());
			pstmt.setString(2, bean.getType());
	          pstmt.setInt(3,bean.getPercentage());
	          pstmt.setString(4,bean.getStatus());
	        
			pstmt.setString(5, bean.getCreatedBy());
			pstmt.setString(6, bean.getModifiedBy());
			pstmt.setTimestamp(7, bean.getCreatedDatetime());
			pstmt.setTimestamp(8, bean.getModifiedDatetime());
			pstmt.setLong(9, bean.getId());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add User");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

	}


	public void delete(TaxBean bean) throws ApplicationException {
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("delete from st_tax where id =?");
			pstmt.setLong(1, bean.getId());

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
			throw new ApplicationException("Exception : Exception in add User");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public TaxBean findByPk(long pk) throws ApplicationException {
		Connection conn = null;
		TaxBean bean = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_tax where id=?");
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new TaxBean();
				bean.setId(rs.getLong(1));
				bean.setCode(rs.getString(2));
				bean.setType(rs.getString(3));
				bean.setPercentage(rs.getInt(4));
				bean.setStatus(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
			}
			pstmt.close();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add User");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	public TaxBean findByCode(String code) throws ApplicationException {
		Connection conn = null;
		TaxBean bean = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_tax where code=?");
			pstmt.setString(1, code);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new TaxBean();
				bean.setId(rs.getLong(1));
				bean.setCode(rs.getString(2));
				bean.setType(rs.getString(3));
				bean.setPercentage(rs.getInt(4));
				bean.setStatus(rs.getString(5));
			

				pstmt.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	public List<TaxBean> list(int pageNo, int pageSize) {
		Connection conn = null;
		TaxBean bean = null;
		ArrayList<TaxBean> list = new ArrayList<TaxBean>();
		StringBuffer sql = new StringBuffer("select * from st_tax");

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit" + pageNo + "," + pageSize);
		}

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new TaxBean();

				bean.setId(rs.getLong(1));
				bean.setCode(rs.getString(2));
				bean.setType(rs.getString(3));
				bean.setPercentage(rs.getInt(4));
				bean.setStatus(rs.getString(5));
			
				list.add(bean);
			}
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;
	}

	public List search(TaxBean bean,int pageNo,int pageSize) throws ApplicationException {
		Connection conn=null;
		
		ArrayList list=new ArrayList();
		StringBuffer sql=new StringBuffer("select * from st_tax where 1=1");
		
		if(bean!=null) {
			if(bean.getId()>0) {
				sql.append(" and id = " +bean.getId());
			}
			
			if(bean.getCode()!=null && bean.getCode().length()>0) {
				sql.append(" and code like '" +bean.getCode() +"%'");
			}
			if(bean.getPercentage()>0) {
				sql.append(" and percentage = " +bean.getPercentage());
			}
			
			
			
		}
		
		if(pageSize>0) {
			pageNo=(pageNo-1)*pageSize;
			sql.append(" limit "+pageNo +"," +pageSize);
		}
		try {
		conn=JDBCDataSource.getConnection();
		PreparedStatement pstmt=conn.prepareStatement(sql.toString());
		ResultSet rs=pstmt.executeQuery();
		
		while (rs.next()) {
			bean = new TaxBean();

			bean.setId(rs.getLong(1));
			bean.setCode(rs.getString(2));
			bean.setType(rs.getString(3));
			bean.setPercentage(rs.getInt(4));
			bean.setStatus(rs.getString(5));
			list.add(bean);
		}
		pstmt.close();
	} 
	catch (Exception e) {
		e.printStackTrace();
	}

	finally {
		JDBCDataSource.closeConnection(conn);
	}
	return list;
}
	}
