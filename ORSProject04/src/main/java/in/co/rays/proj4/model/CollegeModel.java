package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.CollegeBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class CollegeModel {
//nextPk***************************************
	 public Integer nextPK() throws DatabaseException {


	        Connection conn = null;
	        int pk = 0;

	        try {
	            conn = JDBCDataSource.getConnection();
	            PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_college");
	            ResultSet rs = pstmt.executeQuery();

	            while (rs.next()) {
	                pk = rs.getInt(1);
	            }

	            rs.close();
	            pstmt.close();


	        } catch (Exception e) {
	            throw new DatabaseException("Exception: Exception in getting PK");
	        } finally {
	            JDBCDataSource.closeConnection(conn);
	        }

	        return pk + 1;
	    }


	// add******************************************

	public long add(CollegeBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;
		int pk = 0;
		
		CollegeBean duplicateCollegeName = findByName(bean.getName());

		if (duplicateCollegeName != null) {
			throw new DuplicateRecordException("Course Name alredy exists");

		}

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn
					.prepareStatement("insert into st_college values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getName());
			pstmt.setString(3, bean.getAddress());
			pstmt.setString(4, bean.getState());
			pstmt.setString(5, bean.getCity());
			pstmt.setString(6, bean.getPhoneNo());
			pstmt.setString(7, bean.getCreatedBy());
			pstmt.setString(8, bean.getModifiedBy());
			pstmt.setTimestamp(9, bean.getCreatedDatetime());
			pstmt.setTimestamp(10, bean.getModifiedDatetime());

			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add College");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;
	}

	//delete************************************************************************
	 public void delete(CollegeBean bean) throws ApplicationException {

	      

	        Connection conn = null;

	        try {
	            conn = JDBCDataSource.getConnection();
	            conn.setAutoCommit(false);

	            PreparedStatement pstmt = conn.prepareStatement(
	                    "delete from st_college where id = ?");

	            pstmt.setLong(1, bean.getId());
	            pstmt.executeUpdate();

	            conn.commit();
	            pstmt.close();



	        } catch (Exception e) {
	            try {
	                conn.rollback();
	            } catch (Exception ex) {
	            
	                throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
	            }
	        
	            throw new ApplicationException("Exception : Exception in delete College");
	        } finally {
	            JDBCDataSource.closeConnection(conn);
	        }
	    }
	 //update****************************************************
	    public void update(CollegeBean bean) throws ApplicationException, DuplicateRecordException {

	        

	        Connection conn = null;

	    	CollegeBean duplicateCollegeName = findByName(bean.getName());

			// Check if updated College already exist
			if (duplicateCollegeName != null && duplicateCollegeName.getId() != bean.getId()) {

				throw new DuplicateRecordException("Course is already exist");
			}


	        try {
	            conn = JDBCDataSource.getConnection();
	            conn.setAutoCommit(false);

	            PreparedStatement pstmt = conn.prepareStatement("update st_college set name = ?, address = ?, state = ?, city = ?, phone_no = ?,created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");

	            pstmt.setString(1, bean.getName());
	            pstmt.setString(2, bean.getAddress());
	            pstmt.setString(3, bean.getState());
	            pstmt.setString(4, bean.getCity());
	            pstmt.setString(5, bean.getPhoneNo());
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
	            
	                throw new ApplicationException("Exception : Update rollback exception " + ex.getMessage());
	            }
	        
	            throw new ApplicationException("Exception in updating College");
	        } finally {
	            JDBCDataSource.closeConnection(conn);
	        }
	    }
	    
	    //name********************************************
	    
	    public CollegeBean findByName(String name) throws ApplicationException {

	     

	        CollegeBean bean = null;
	        Connection conn = null;

	        try {
	            conn = JDBCDataSource.getConnection();
	            PreparedStatement pstmt = conn.prepareStatement("select * from st_college where name = ?");
	            pstmt.setString(1, name);

	            ResultSet rs = pstmt.executeQuery();

	            while (rs.next()) {
	                bean = new CollegeBean();
	                bean.setId(rs.getLong(1));
	                bean.setName(rs.getString(2));
	                bean.setAddress(rs.getString(3));
	                bean.setState(rs.getString(4));
	                bean.setCity(rs.getString(5));
	                bean.setPhoneNo(rs.getString(6));
	                bean.setCreatedBy(rs.getString(7));
	                bean.setModifiedBy(rs.getString(8));
	                bean.setCreatedDatetime(rs.getTimestamp(9));
	                bean.setModifiedDatetime(rs.getTimestamp(10));
	            }

	            rs.close();
	            pstmt.close();

	        } catch (Exception e) {
	       
	            throw new ApplicationException("Exception : Exception in getting College by name");
	        } finally {
	            JDBCDataSource.closeConnection(conn);
	        }

	        return bean;
	    }
	    //PK***************************************
	    public CollegeBean findByPk(long pk) throws ApplicationException {



	 
	        CollegeBean bean = null;
	        Connection conn = null;

	        try {
	            conn = JDBCDataSource.getConnection();
	            PreparedStatement pstmt = conn.prepareStatement("select * from st_college where id = ?");
	            pstmt.setLong(1, pk);

	            ResultSet rs = pstmt.executeQuery();

	            while (rs.next()) {
	                bean = new CollegeBean();
	                bean.setId(rs.getLong(1));
	                bean.setName(rs.getString(2));
	                bean.setAddress(rs.getString(3));
	                bean.setState(rs.getString(4));
	                bean.setCity(rs.getString(5));
	                bean.setPhoneNo(rs.getString(6));
	                bean.setCreatedBy(rs.getString(7));
	                bean.setModifiedBy(rs.getString(8));
	                bean.setCreatedDatetime(rs.getTimestamp(9));
	                bean.setModifiedDatetime(rs.getTimestamp(10));
	            }

	            rs.close();
	            pstmt.close();

	        } catch (Exception e) {
	          
	            throw new ApplicationException("Exception : Exception in getting College by PK");
	        } finally {
	            JDBCDataSource.closeConnection(conn);
	        }

	        return bean;
	    }
	    
	    //search  by filter
	    public List  search() throws ApplicationException {
	        return search(null, 0, 0);
	    }

	    
	    public List  search(CollegeBean bean, int pageNo, int pageSize)
	            throws ApplicationException {

	 

	        StringBuffer sql = new StringBuffer("select * from st_college where 1 = 1");

	        if (bean != null) {

	            if (bean.getId() > 0)
	                sql.append(" and id = " + bean.getId());

	            if (bean.getName() != null && bean.getName().length() > 0)
	                sql.append(" and name like '" + bean.getName() + "%'");

	            if (bean.getAddress() != null && bean.getAddress().length() > 0)
	                sql.append(" and address like '" + bean.getAddress() + "%'");

	            if (bean.getState() != null && bean.getState().length() > 0)
	                sql.append(" and state like '" + bean.getState() + "%'");

	            if (bean.getCity() != null && bean.getCity().length() > 0)
	                sql.append(" and city like '" + bean.getCity() + "%'");

	            if (bean.getPhoneNo() != null && bean.getPhoneNo().length() > 0)
	                sql.append(" and phone_no = " + bean.getPhoneNo());
	        }

	        if (pageSize > 0) {
	            pageNo = (pageNo - 1) * pageSize;
	            sql.append(" limit " + pageNo + ", " + pageSize);
	        }

	        List<CollegeBean> list = new ArrayList<CollegeBean>();
	        Connection conn = null;

	        try {
	            conn = JDBCDataSource.getConnection();
	            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
	            ResultSet rs = pstmt.executeQuery();

	            while (rs.next()) {
	                bean = new CollegeBean();
	                bean.setId(rs.getLong(1));
	                bean.setName(rs.getString(2));
	                bean.setAddress(rs.getString(3));
	                bean.setState(rs.getString(4));
	                bean.setCity(rs.getString(5));
	                bean.setPhoneNo(rs.getString(6));
	                bean.setCreatedBy(rs.getString(7));
	                bean.setModifiedBy(rs.getString(8));
	                bean.setCreatedDatetime(rs.getTimestamp(9));
	                bean.setModifiedDatetime(rs.getTimestamp(10));
	                list.add(bean);
	            }

	            rs.close();
	            pstmt.close();

	        } catch (Exception e) {
	           
	            throw new ApplicationException("Exception : Exception in search College");
	        } finally {
	            JDBCDataSource.closeConnection(conn);
	        }

	        return list;
	    }
	    
	    
	    //all data
	    public List list() throws ApplicationException {
			return list(0, 0);
		}

		public List list(int pageNo, int pageSize) throws ApplicationException {
		
			ArrayList list = new ArrayList();
			StringBuffer sql = new StringBuffer("select * from st_college");
			
			if (pageSize > 0) {
				
				pageNo = (pageNo - 1) * pageSize;
				sql.append(" limit " + pageNo + "," + pageSize);
			}

			Connection conn = null;
			CollegeBean bean = null;

			try {
				conn = JDBCDataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql.toString());
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					bean = new CollegeBean();
					bean.setId(rs.getLong(1));
					bean.setName(rs.getString(2));
					bean.setAddress(rs.getString(3));
					bean.setState(rs.getString(4));
					bean.setCity(rs.getString(5));
					bean.setPhoneNo(rs.getString(6));
					bean.setCreatedBy(rs.getString(7));
					bean.setModifiedBy(rs.getString(8));
					bean.setCreatedDatetime(rs.getTimestamp(9));
					bean.setModifiedDatetime(rs.getTimestamp(10));
					list.add(bean);
				}
				rs.close();
			} catch (Exception e) {
			
				throw new ApplicationException("Exception : Exception in getting list of users");
			} finally {
				JDBCDataSource.closeConnection(conn);
			}

	
			return list;

		}
	}
