package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import in.co.rays.proj4.bean.EventBean;
import in.co.rays.proj4.bean.FacultyBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class EventModel {

    // ================= nextPK =================
    public Integer nextPk() throws DatabaseException {

        Connection conn = null;
        int pk = 0;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_event");
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

        return pk + 1;
    }

    // ================= ADD =================
    public long add(EventBean bean) throws ApplicationException, DuplicateRecordException {

        int pk = 0;
        Connection conn = null;

        EventBean existbean = findByCode(bean.getEventCode());
        if (existbean != null) {
            throw new DuplicateRecordException("Event code already exists");
        }

        try {
            pk = nextPk();
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "insert into st_event values(?,?,?,?,?,?,?,?,?)");

            pstmt.setInt(1, pk);
            pstmt.setString(2, bean.getEventCode());
            pstmt.setString(3, bean.getEventName());
            pstmt.setDate(4, new java.sql.Date(bean.getEventDate().getTime()));
            pstmt.setString(5, bean.getEventLocation());
            pstmt.setString(6, bean.getCreatedBy());
            pstmt.setString(7, bean.getModifiedBy());
            pstmt.setTimestamp(8, bean.getCreatedDatetime());
            pstmt.setTimestamp(9, bean.getModifiedDatetime());

            pstmt.executeUpdate();
            conn.commit();

            pstmt.close();

        } catch (Exception e) {

            try {
                if (conn != null) conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Add rollback exception " + ex.getMessage());
            }

            throw new ApplicationException("Exception in add Event");

        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return pk;
    }

    // ================= UPDATE =================
    public void update(EventBean bean) throws ApplicationException, DuplicateRecordException {

        Connection conn = null;

        EventBean existbean = findByCode(bean.getEventCode());
        if (existbean != null && existbean.getId() != bean.getId()) {
            throw new DuplicateRecordException("Event code already exists");
        }

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "update st_event set event_code=?, event_name=?, event_date=?, "
                            + "event_location=?, created_by=?, modified_by=?, "
                            + "created_datetime=?, modified_datetime=? where id=?");

            pstmt.setString(1, bean.getEventCode());
            pstmt.setString(2, bean.getEventName());
            pstmt.setDate(3, new java.sql.Date(bean.getEventDate().getTime()));
            pstmt.setString(4, bean.getEventLocation());
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
                if (conn != null) conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Update rollback exception " + ex.getMessage());
            }

            throw new ApplicationException("Exception in updating Event");

        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    // ================= DELETE =================
    public void delete(EventBean bean) throws ApplicationException {

        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt =
                    conn.prepareStatement("delete from st_event where id=?");

            pstmt.setLong(1, bean.getId());
            pstmt.executeUpdate();

            conn.commit();
            pstmt.close();

        } catch (Exception e) {

            try {
                if (conn != null) conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Delete rollback exception " + ex.getMessage());
            }

            throw new ApplicationException("Exception in delete Event");

        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    // ================= FIND BY PK =================
    public EventBean findByPk(long pk) throws ApplicationException {

        EventBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt =
                    conn.prepareStatement("select * from st_event where id=?");

            pstmt.setLong(1, pk);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                bean = new EventBean();
                bean.setId(rs.getLong(1));
                bean.setEventCode(rs.getString(2));
                bean.setEventName(rs.getString(3));
                bean.setEventDate(rs.getDate(4));
                bean.setEventLocation(rs.getString(5));
                bean.setCreatedBy(rs.getString(6));
                bean.setModifiedBy(rs.getString(7));
                bean.setCreatedDatetime(rs.getTimestamp(8));
                bean.setModifiedDatetime(rs.getTimestamp(9));
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in findByPk");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return bean;
    }

    // ================= FIND BY CODE (FIXED) =================
    public EventBean findByCode(String code) throws ApplicationException {

        EventBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();

            PreparedStatement pstmt =
                    conn.prepareStatement("select * from st_event where event_code=?");

            pstmt.setString(1, code);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                bean = new EventBean();
                bean.setId(rs.getLong(1));
                bean.setEventCode(rs.getString(2));
                bean.setEventName(rs.getString(3));
                bean.setEventDate(rs.getDate(4));
                bean.setEventLocation(rs.getString(5));
                bean.setCreatedBy(rs.getString(6));
                bean.setModifiedBy(rs.getString(7));
                bean.setCreatedDatetime(rs.getTimestamp(8));
                bean.setModifiedDatetime(rs.getTimestamp(9));
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in findByCode");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return bean;
    }

    // ================= SEARCH (FIXED) =================
    public List search(EventBean bean, int pageNo, int pageSize)
            throws ApplicationException {

        StringBuffer sql = new StringBuffer("select * from st_event where 1=1");

        if (bean != null) {

            if (bean.getEventCode() != null && bean.getEventCode().length() > 0) {
                sql.append(" and event_code like '" + bean.getEventCode() + "%'");
            }

            if (bean.getEventName() != null && bean.getEventName().length() > 0) {
                sql.append(" and event_name like '" + bean.getEventName() + "%'");
            }
        	if (bean.getEventDate() != null && bean.getEventDate().getTime() > 0) {
				Date d = new Date(bean.getEventDate().getTime());
				sql.append(" and dob = '" + d + "'");
			}
            if (bean.getEventLocation() != null && bean.getEventLocation().length() > 0) {
                sql.append(" and event_location like '" + bean.getEventLocation() + "%'");
            }
        }

        if (pageSize > 0) {
            pageNo = (pageNo - 1) * pageSize;
            sql.append(" limit " + pageNo + ", " + pageSize);
        }

        List list = new ArrayList();
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                EventBean b = new EventBean();
                b.setId(rs.getLong(1));
                b.setEventCode(rs.getString(2));
                b.setEventName(rs.getString(3));
                b.setEventDate(rs.getDate(4));
                b.setEventLocation(rs.getString(5));
                b.setCreatedBy(rs.getString(6));
                b.setModifiedBy(rs.getString(7));
                b.setCreatedDatetime(rs.getTimestamp(8));
                b.setModifiedDatetime(rs.getTimestamp(9));

                list.add(b);
            }

            rs.close();
            pstmt.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in search Event");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return list;
    }

    public List list(int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer("select * from st_event");
		List list = new ArrayList();

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" LIMIT " + pageNo + "," + pageSize);
		}

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(
					"select * FROM st_event");
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				EventBean b = new EventBean();

				 b.setId(rs.getLong(1));
	                b.setEventCode(rs.getString(2));
	                b.setEventName(rs.getString(3));
	                b.setEventDate(rs.getDate(4));
	                b.setEventLocation(rs.getString(5));
	                b.setCreatedBy(rs.getString(6));
	                b.setModifiedBy(rs.getString(7));
	                b.setCreatedDatetime(rs.getTimestamp(8));
	                b.setModifiedDatetime(rs.getTimestamp(9));

				list.add(b);
			}
			rs.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in getting faculty list");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}

}
