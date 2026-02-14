package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.CourseBean;
import in.co.rays.proj4.bean.SubjectBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class SubjectModel {

	public Integer nextPk() throws DatabaseException {
		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_subject");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}

			rs.close();
			pstmt.close();
		}

		catch (Exception e) {
			throw new DatabaseException("Exception : Exception in getting PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk + 1;
	}

//add
	public long add(SubjectBean bean) throws ApplicationException, DatabaseException, DuplicateRecordException {

		Connection conn = null;
		int pk = 0;

		CourseModel courseModel = new CourseModel();
		CourseBean courseBean = courseModel.FindByPK(bean.getCourseId());
		bean.setCourseName(courseBean.getName());

		SubjectBean duplicateSubject = findByName(bean.getName());
		if (duplicateSubject != null) {
			throw new DuplicateRecordException("Subject Name already exists");
		}
		try {
			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("insert into st_subject values(?,?,?,?,?,?,?,?,?)");
			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getName());
			pstmt.setLong(3, bean.getCourseId());

			pstmt.setString(4, bean.getCourseName());
			pstmt.setString(5, bean.getDescription());
			pstmt.setString(6, bean.getCreatedBy());
			pstmt.setString(7, bean.getModifiedBy());
			pstmt.setTimestamp(8, bean.getCreatedDatetime());
			pstmt.setTimestamp(9, bean.getCreatedDatetime());
			int i = pstmt.executeUpdate();
			System.out.println(i + "row affected");
			conn.commit();
			pstmt.close();

		} catch (Exception e) {

			try {
				conn.rollback();
			} catch (Exception ex) {

				throw new ApplicationException("Excetion : add rollback Exception " + ex.getMessage());
			}

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk;

	}

	// delete
	public void delete(SubjectBean bean) throws ApplicationException {
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("delete from st_subject where id=?");
			pstmt.setLong(1, bean.getId());
			int i = pstmt.executeUpdate();
			System.out.println(i + "row affected");
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

//update
	public void update(SubjectBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		SubjectBean beanexist = findByName(bean.getName());
		if (beanexist != null && beanexist.getId() != bean.getId()) {
			throw new DuplicateRecordException("subject is already exist");

		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(
					"update st_subject set name=? ,  course_id=?, course_name=?, description=?, created_by=?, modified_by=?, created_datetime=?, modified_datetime=? where id=?");

			pstmt.setString(1, bean.getName());

			pstmt.setLong(2, bean.getCourseId());
			pstmt.setString(3, bean.getCourseName());
			pstmt.setString(4, bean.getDescription());
			pstmt.setString(5, bean.getCreatedBy());
			pstmt.setString(6, bean.getModifiedBy());
			pstmt.setTimestamp(7, bean.getCreatedDatetime());
			pstmt.setTimestamp(8, bean.getModifiedDatetime());
			pstmt.setLong(9, bean.getId());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();

			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : update rollback Exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception :Exception in update subject");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

	}

	// pk****************************

	public SubjectBean FindByPK(long pk) throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from st_subject where id=?");
		Connection conn = null;
		SubjectBean bean = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new SubjectBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
			
				bean.setCourseId(rs.getLong(3));
				bean.setCourseName(rs.getString(4));
				bean.setDescription(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
			}
			rs.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in getting subject by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);

		}
		return bean;
	}

	// name***************
	public SubjectBean findByName(String name) throws ApplicationException {

		SubjectBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_subject where name=?");
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new SubjectBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
			
				bean.setCourseId(rs.getLong(3));
				bean.setCourseName(rs.getString(4));
				bean.setDescription(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
			}
			rs.close();
		} catch (Exception e) {

			throw new ApplicationException("Exception in getting subject by name");
		} finally {
			JDBCDataSource.closeConnection(conn);

		}
		return bean;

	}

	// list**************************************
	public List list() throws Exception {
		System.out.println("subject list: " + list(0, 0).size());
		return list(0, 0);
	}

	public List<SubjectBean> list(int pageNo, int pageSize) throws ApplicationException {

		List list = new ArrayList();
		StringBuffer sql = new StringBuffer("SELECT * FROM st_subject");

		// Pagination
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
				SubjectBean bean = new SubjectBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
			
				bean.setCourseId(rs.getLong(3));
				bean.setCourseName(rs.getString(4));
				bean.setDescription(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));

				list.add(bean);
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in getting list of subjects: " + e.getMessage());
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		System.out.println("subject list size:====== " + list.size());
		return list;
	}

	// search****************
	public List search(SubjectBean bean) throws DatabaseException, ApplicationException {
		return search(bean, 0, 0);
	}

	public List search(SubjectBean bean, int pageNo, int pageSize) throws ApplicationException {

		List list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from st_subject where 1=1");

		if (bean != null) {

			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}

			if (bean.getName() != null && bean.getName().trim().length() > 0) {
				sql.append(" AND name LIKE '" + bean.getName() + "%'");
			}

			if (bean.getDescription() != null && bean.getDescription().trim().length() > 0) {
				sql.append(" AND description LIKE '" + bean.getDescription() + "%'");
			}

			if (bean.getCourseName() != null && bean.getCourseName().trim().length() > 0) {
				sql.append(" AND course_name LIKE '" + bean.getCourseName() + "%'");
			}

		}

		// Pagination
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" LIMIT " + pageNo + "," + pageSize);
		}

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			System.out.println("Search SQL: " + sql); // optional debug
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new SubjectBean(); // new reference
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));

				bean.setCourseId(rs.getLong(3));
				bean.setCourseName(rs.getString(4));
				bean.setDescription(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
				list.add(bean);
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			throw new ApplicationException("Exception in Subject search: " + e.getMessage());
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}

}
