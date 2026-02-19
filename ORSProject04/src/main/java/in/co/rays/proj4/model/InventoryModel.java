package in.co.rays.proj4.model;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import in.co.rays.proj4.bean.InventoryBean;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class InventoryModel {
	//pk generate
	
	public Integer nextPk() throws SQLException {
		
		Connection conn=null;
		int pk=0;
		
		try {
		conn=JDBCDataSource.getConnection();
		PreparedStatement pstmt=conn.prepareStatement("select max(id) from st_inventory");
		
		ResultSet rs=pstmt.executeQuery();
		
		
	while(rs.next()) {
		pk=rs.getInt(1);
		
		
	}
	rs.close();
		pstmt.close();
	
		
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
	
	
	public Integer add(InventoryBean bean) throws SQLException, DuplicateRecordException {
		Connection conn=null;
		InventoryBean existbean =findByName(bean.getSupplierName());
		if(existbean!=null) {
			throw new DuplicateRecordException("name already exist");
		}
		int pk=nextPk();
		
		try {
			conn=JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt=conn.prepareStatement("insert into st_inventory values(?,?,?,?,?,?,?,?,?)");
		pstmt.setInt(1, pk);
	pstmt.setString(2, bean.getSupplierName());
		pstmt.setDate(3, new java.sql.Date(bean.getDob().getTime()));
		pstmt.setLong(4, bean.getQuantity());
		pstmt.setString(5, bean.getProduct());
		pstmt.setString(6, bean.getCreatedBy());
		pstmt.setString(7, bean.getModifiedBy());
		pstmt.setTimestamp(8, bean.getCreatedDatetime());
		pstmt.setTimestamp(9, bean.getModifiedDatetime());
			
		int i = pstmt.executeUpdate();
		  conn.commit();
	
			
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
	
	//delete
	
	public void delete(InventoryBean bean) throws SQLException {
		Connection conn=null;
		
		try {
			
		conn=JDBCDataSource.getConnection();
		conn.setAutoCommit(false);
		PreparedStatement pstmt=conn.prepareStatement("delete from st_inventory where id=?");
		pstmt.setLong(1, bean.getId());
		int i = pstmt.executeUpdate();
		  conn.commit();
	
		
	 	
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
	public void update(InventoryBean bean) throws SQLException, DuplicateRecordException {
		Connection conn=null;
		InventoryBean existbean =findByName(bean.getSupplierName());
		if(existbean!=null && existbean.getId()!=bean.getId()){
			throw new DuplicateRecordException("name already exist");
		}
		
		
		try {
			conn=JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt=conn.prepareStatement("update st_inventory set supplier_name=?, dob=? , quantity=?,product=?, created_by=?, modified_by=?, created_datetime=?, modified_datetime=? where id=?");
		pstmt.setString(1, bean.getSupplierName());
		pstmt.setDate(2, new java.sql.Date(bean.getDob().getTime()));
		pstmt.setLong(3, bean.getQuantity());
		pstmt.setString(4, bean.getProduct());
		pstmt.setString(5, bean.getCreatedBy());
		pstmt.setString(6, bean.getModifiedBy());
		pstmt.setTimestamp(7, bean.getCreatedDatetime());
		pstmt.setTimestamp(8, bean.getModifiedDatetime());
		pstmt.setLong(9, bean.getId());
		int i = pstmt.executeUpdate();
		  conn.commit();
		
		
		
		
		
		}
		catch(Exception e) {
			conn.rollback();
			e.printStackTrace();
		}
		
		finally {
			JDBCDataSource.closeConnection(conn);
	
		}
	}
	
	//searchbypk
	
	public InventoryBean findByPk(long pk) {
		
		Connection conn=null;
		InventoryBean bean=null;
		
		try {
			conn=JDBCDataSource.getConnection();
			PreparedStatement pstmt=conn.prepareStatement("select * from st_inventory where id=?");
			pstmt.setLong(1, pk);
			
			ResultSet rs=pstmt.executeQuery();
			
			while(rs.next()) {
			bean=new InventoryBean();
			bean.setId(rs.getLong(1));
			bean.setSupplierName(rs.getString(2));
			bean.setDob(rs.getDate(3));
			bean.setQuantity(rs.getLong(4));
			bean.setProduct(rs.getString(5));
			bean.setCreatedBy(rs.getString(6));
			bean.setModifiedBy(rs.getString(7));
			bean.setCreatedDatetime(rs.getTimestamp(8));
			bean.setModifiedDatetime(rs.getTimestamp(9));
			
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
	
	
	//name
	public InventoryBean findByName(String supplierName) {
		Connection conn=null;
		InventoryBean bean=null;
		
		try {
			conn=JDBCDataSource.getConnection();
			PreparedStatement pstmt=conn.prepareStatement("select * from st_inventory where supplier_name=?");
			pstmt.setString(1, supplierName);
			
			ResultSet rs=pstmt.executeQuery();
			
			while(rs.next()) {
			bean=new InventoryBean();
			bean.setId(rs.getLong(1));
			bean.setSupplierName(rs.getString(2));
			bean.setDob(rs.getDate(3));
			bean.setQuantity(rs.getLong(4));
			bean.setProduct(rs.getString(5));
			bean.setCreatedBy(rs.getString(6));
			bean.setModifiedBy(rs.getString(7));
			bean.setCreatedDatetime(rs.getTimestamp(8));
			bean.setModifiedDatetime(rs.getTimestamp(9));
			
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
		StringBuffer sql=new StringBuffer("select * from st_inventory");
		
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + " ," + pageSize);
			
		}
		
		Connection conn=null;
		InventoryBean bean=null;
		try {
			conn=JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) {
				bean=new InventoryBean();
				bean.setId(rs.getLong(1));
				bean.setSupplierName(rs.getString(2));
				bean.setDob(rs.getDate(3));
				bean.setQuantity(rs.getLong(4));
				bean.setProduct(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
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
	
	public List search(InventoryBean bean ,int pageNo,int pageSize) {
	


StringBuffer sql=new StringBuffer("select * from st_inventory where 1=1"); 
if(bean!=null) {
    if (bean.getId() > 0) {
        sql.append(" and id = " + bean.getId());
    }

    if (bean.getSupplierName() != null && bean.getSupplierName().length() > 0) {
        sql.append(" and supplier_name like '" + bean.getSupplierName() + "%'");
    }

    if (bean.getDob() != null) {
    	sql.append(" and dob = '" + new java.sql.Date(bean.getDob().getTime()) + "'");

    }

    if (bean.getQuantity() > 0) {
        sql.append(" and quantity = " + bean.getQuantity());
    }

    if (bean.getProduct() != null && bean.getProduct().length() > 0) {
        sql.append(" and product like '" + bean.getProduct() + "%'");
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
				bean=new InventoryBean();
				bean.setId(rs.getLong(1));
				bean.setSupplierName(rs.getString(2));
				bean.setDob(rs.getDate(3));
				bean.setQuantity(rs.getLong(4));
				bean.setProduct(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
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
	


