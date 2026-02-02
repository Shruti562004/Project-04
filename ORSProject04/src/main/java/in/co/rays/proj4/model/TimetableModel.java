package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import in.co.rays.proj4.bean.CourseBean;
import in.co.rays.proj4.bean.StudentBean;
import in.co.rays.proj4.bean.SubjectBean;
import in.co.rays.proj4.bean.TimetableBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class TimetableModel {
	
	public Integer nextPk() throws DatabaseException {
		Connection conn=null;
		int pk=0;
		try {
			conn=JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_timetable");
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) {
			pk=rs.getInt(1);
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
	public long add(TimetableBean bean) throws ApplicationException {
		Connection conn=null;
		int pk=0;
		

		CourseModel courseModel = new CourseModel();
		CourseBean courseBean = courseModel.FindByPK(bean.getCourseId());
		bean.setCourseName(courseBean.getName());

		SubjectModel subjectModel = new SubjectModel();
		SubjectBean subjectBean = subjectModel.FindByPK(bean.getSubjectId());
		bean.setSubjectName(subjectBean.getName());
		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPk();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("INSERT st_timetable values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getSemester());
			
			pstmt.setString(3, bean.getDescription());

			pstmt.setDate(4, new java.sql.Date(bean.getExamDate().getTime()));

			pstmt.setString(5, bean.getExamTime());
			pstmt.setLong(6, bean.getCourseId());
			pstmt.setString(7, bean.getCourseName());
			pstmt.setLong(8, bean.getSubjectId());
			pstmt.setString(9, bean.getSubjectName());


		
			pstmt.setString(10, bean.getCreatedBy());
			pstmt.setString(11, bean.getModifiedBy());
			pstmt.setTimestamp(12, bean.getCreatedDatetime());
			pstmt.setTimestamp(13, bean.getModifiedDatetime());
			int i = pstmt.executeUpdate();
			System.out.println("record inserted" + i);
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			
			try {
				conn.rollback();
			} catch (Exception ex) {
		
				throw new ApplicationException("Exception : add rollback Exception" + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add timetable" );
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	
		return pk;

	}
	//delete
	
		public void delete(TimetableBean bean) throws ApplicationException {
			Connection conn=null;
			try {
				conn=JDBCDataSource.getConnection();
				conn.setAutoCommit(false);
				PreparedStatement pstmt=conn.prepareStatement("delete from st_timetable where id=?");
				pstmt.setLong(1, bean.getId());
				int i=pstmt.executeUpdate();
				System.out.println( i +" row affected");
				conn.commit();
				pstmt.close();
			}
			catch (Exception e) {
			
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
		public void update(TimetableBean bean) throws ApplicationException, DuplicateRecordException {
		
			Connection conn = null;


			CourseModel courseModel = new CourseModel();
			CourseBean courseBean = courseModel.FindByPK(bean.getCourseId());
			bean.setCourseName(courseBean.getName());

			SubjectModel subjectModel = new SubjectModel();
			SubjectBean subjectBean = subjectModel.FindByPK(bean.getSubjectId());
			bean.setSubjectName(subjectBean.getName());

			try {
				conn = JDBCDataSource.getConnection();
				conn.setAutoCommit(false);
				PreparedStatement pstmt = conn.prepareStatement(
						"update st_timetable set course_id=?,course_name=?,subject_id=?,subject_name=?,semester=?,exam_date=?,exam_time=?,description =? ,created_by=?,modified_by=?,created_datetime=?,modified_datetime=? where id=?");

				pstmt.setLong(1, bean.getCourseId());
				pstmt.setString(2, bean.getCourseName());
				pstmt.setLong(3, bean.getSubjectId());
				pstmt.setString(4, bean.getSubjectName());
				pstmt.setString(5, bean.getSemester());
				pstmt.setDate(6, new java.sql.Date(bean.getExamDate().getTime()));
				pstmt.setString(7, bean.getExamTime());
				pstmt.setString(8,bean.getDescription());
				pstmt.setString(9, bean.getCreatedBy());
				pstmt.setString(10, bean.getModifiedBy());
				pstmt.setTimestamp(11, bean.getCreatedDatetime());
				pstmt.setTimestamp(12, bean.getModifiedDatetime());
				pstmt.setLong(13, bean.getId());

				int i=pstmt.executeUpdate();
				
				System.out.println("timetable update");
				conn.commit();
				pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
				
				try {
					conn.rollback();

				} catch (Exception ex) {
					throw new ApplicationException("Exception : update rollback Exception" + ex.getMessage());
				}
				throw new ApplicationException("Exception in updating timetable");
			} finally {
				JDBCDataSource.closeConnection(conn);
			}
	
		}
//find by pk
		public TimetableBean findByPK(long pk) throws ApplicationException {
			
			StringBuffer sql = new StringBuffer("select * from st_timetable where id=?");
			TimetableBean bean = null;
			Connection conn = null;
			try {
				conn = JDBCDataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql.toString());
				pstmt.setLong(1, pk);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					bean = new TimetableBean();
					bean.setId(rs.getLong(1));
					bean.setSemester(rs.getString(2));
					bean.setDescription(rs.getString(3));
					bean.setExamDate(rs.getDate(4));
					bean.setExamTime(rs.getString(5));
					bean.setCourseId(rs.getLong(6));
					bean.setCourseName(rs.getString(7));
					bean.setSubjectId(rs.getLong(8));
					bean.setSubjectName(rs.getString(9));
				
				
					
					bean.setCreatedBy(rs.getString(10));
					bean.setModifiedBy(rs.getString(11));
					bean.setCreatedDatetime(rs.getTimestamp(12));
					bean.setModifiedDatetime(rs.getTimestamp(13));

				}
				rs.close();
			} catch (Exception e) {
				
				throw new ApplicationException("Exception : Exception in getting by pk");
			} finally {
				JDBCDataSource.closeConnection(conn);
			}
		
			return bean;
		}
		
//list
		public List list() throws Exception {
			return list(0, 0);
		}

		public List list(int pageNo, int pageSize) throws ApplicationException {

		    List list = new ArrayList();
		    StringBuffer sql = new StringBuffer("SELECT * FROM st_timetable");

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

		            TimetableBean bean = new TimetableBean();

		            bean.setId(rs.getLong(1));
		            bean.setSemester(rs.getString(2));
		            bean.setDescription(rs.getString(3));
		            bean.setExamDate(rs.getDate(4));
		            bean.setExamTime(rs.getString(5));
		            bean.setCourseId(rs.getLong(6));
		            bean.setCourseName(rs.getString(7));
		            bean.setSubjectId(rs.getLong(8));
		            bean.setSubjectName(rs.getString(9));
		            bean.setCreatedBy(rs.getString(10));
		            bean.setModifiedBy(rs.getString(11));
		            bean.setCreatedDatetime(rs.getTimestamp(12));
		            bean.setModifiedDatetime(rs.getTimestamp(13));

		            list.add(bean);
		        }

		        rs.close();

		    } catch (Exception e) {
		        throw new ApplicationException("Exception : Exception in getting timetable list" + e.getMessage());
		    } finally {
		        JDBCDataSource.closeConnection(conn);
		    }

		    return list;
		}

//
		public List search(TimetableBean bean) throws ApplicationException {
			return search(bean, 0, 0);
		}

		public List search(TimetableBean bean, int pageNo, int pageSize) throws ApplicationException {

		    StringBuffer sql = new StringBuffer("select * from st_timetable where 1=1");

		    if (bean != null) {

		        if (bean.getId() > 0) {
		            sql.append(" AND id = " + bean.getId());
		        }

		        if (bean.getCourseId() > 0) {
		            sql.append(" AND course_id = " + bean.getCourseId());
		        }

		        if (bean.getCourseName() != null && bean.getCourseName().trim().length() > 0) {
		            sql.append(" AND course_name LIKE '" + bean.getCourseName() + "%'");
		        }

		        if (bean.getSubjectId() > 0) {
		            sql.append(" AND subject_id = " + bean.getSubjectId());
		        }

		        if (bean.getSubjectName() != null && bean.getSubjectName().trim().length() > 0) {
		            sql.append(" AND subject_name LIKE '" + bean.getSubjectName() + "%'");
		        }

		        if (bean.getExamTime() != null && bean.getExamTime().trim().length() > 0) {
		            sql.append(" AND exam_time LIKE '" + bean.getExamTime() + "%'");
		        }
		    }

		    if (pageSize > 0) {
		        pageNo = (pageNo - 1) * pageSize;
		        sql.append(" LIMIT " + pageNo + "," + pageSize);
		    }

		    ArrayList list = new ArrayList();
		    Connection conn = null;

		    try {
		        conn = JDBCDataSource.getConnection();
		        PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		        
		        ResultSet rs = pstmt.executeQuery();

		        while (rs.next()) {
		          bean = new TimetableBean();
		            bean.setId(rs.getLong(1));
		            bean.setSemester(rs.getString(2));
		            bean.setDescription(rs.getString(3));
		            bean.setExamDate(rs.getDate(4));      
		            bean.setExamTime(rs.getString(5));
		            bean.setCourseId(rs.getLong(6));
		            bean.setCourseName(rs.getString(7));
		            bean.setSubjectId(rs.getLong(8));
		            bean.setSubjectName(rs.getString(9));
		        
		            
		            bean.setCreatedBy(rs.getString(10));
		            bean.setModifiedBy(rs.getString(11));
		            bean.setCreatedDatetime(rs.getTimestamp(12));
		            bean.setModifiedDatetime(rs.getTimestamp(13));

		            list.add(bean);
		        }
		        rs.close();

		    } catch (Exception e) {
		        throw new ApplicationException("Exception in Timetable search : " + e.getMessage());
		    } finally {
		        JDBCDataSource.closeConnection(conn);
		    }

		    return list;
		}
		
		//CSS**********************
		public TimetableBean checkBycss(long CourseId, long SubjectId, String semester) throws ApplicationException {
			Connection conn = null;
			TimetableBean bean = null;
			StringBuffer sql = new StringBuffer("select * from st_timetable where course_id=? and subject_id=? and semester=?");

			try {
				Connection con = JDBCDataSource.getConnection();
				PreparedStatement ps = con.prepareStatement(sql.toString());
				ps.setLong(1, CourseId);
				ps.setLong(2, SubjectId);
				ps.setString(3, semester);
				ResultSet rs = ps.executeQuery();

				while (rs.next()) {
					bean = new TimetableBean();
					  bean.setId(rs.getLong(1));
			            bean.setSemester(rs.getString(2));
			            bean.setDescription(rs.getString(3));
			            bean.setExamDate(rs.getDate(4));      
			            bean.setExamTime(rs.getString(5));
			            bean.setCourseId(rs.getLong(6));
			            bean.setCourseName(rs.getString(7));
			            bean.setSubjectId(rs.getLong(8));
			            bean.setSubjectName(rs.getString(9));
			        
			            
			            bean.setCreatedBy(rs.getString(10));
			            bean.setModifiedBy(rs.getString(11));
			            bean.setCreatedDatetime(rs.getTimestamp(12));
			            bean.setModifiedDatetime(rs.getTimestamp(13));
				}
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();

				throw new ApplicationException("Exception in list Method of timetable Model");
			} finally {
				JDBCDataSource.closeConnection(conn);
			}
		
			return bean;
		}

		// check TimeTable by cds
		 
		
		 
		public TimetableBean checkBycds(long CourseId, String Semester, Date ExamDate) throws ApplicationException {
			StringBuffer sql = new StringBuffer("select * from st_timetable where course_id=? and semester=? AND exam_date=?");

			Connection conn = null;
			TimetableBean bean = null;
			Date ExDate = new Date(ExamDate.getTime());

			try {
				Connection con = JDBCDataSource.getConnection();
				PreparedStatement ps = con.prepareStatement(sql.toString());
				ps.setLong(1, CourseId);
				ps.setString(2, Semester);
				ps.setDate(3, new java.sql.Date(ExamDate.getTime())); 

				ResultSet rs = ps.executeQuery();

				while (rs.next()) {
					bean = new TimetableBean();
					  bean.setId(rs.getLong(1));
			            bean.setSemester(rs.getString(2));
			            bean.setDescription(rs.getString(3));
			            bean.setExamDate(rs.getDate(4));      
			            bean.setExamTime(rs.getString(5));
			            bean.setCourseId(rs.getLong(6));
			            bean.setCourseName(rs.getString(7));
			            bean.setSubjectId(rs.getLong(8));
			            bean.setSubjectName(rs.getString(9));
			        
			            
			            bean.setCreatedBy(rs.getString(10));
			            bean.setModifiedBy(rs.getString(11));
			            bean.setCreatedDatetime(rs.getTimestamp(12));
			            bean.setModifiedDatetime(rs.getTimestamp(13));
				}
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			
				throw new ApplicationException("Exception in list Method of timetable Model");
			} finally {
				JDBCDataSource.closeConnection(conn);
			}
			
			return bean;

		}

		// check TimeTable by Semester
	
		 
		public  TimetableBean checkBysemester(long CourseId, long SubjectId, String semester,
				java.util.Date ExamDAte) {

			TimetableBean bean = null;

			Date ExDate = new Date(ExamDAte.getTime());

			StringBuffer sql = new StringBuffer("select * from st_timetable where course_id=?  and subject_id=? and semester=?");
			

			try {
				Connection con = JDBCDataSource.getConnection();
				PreparedStatement ps = con.prepareStatement(sql.toString());
				ps.setLong(1, CourseId);
				ps.setLong(2, SubjectId);
				ps.setString(3, semester);
				ResultSet rs = ps.executeQuery();

				while (rs.next()) {
					bean = new TimetableBean();
					  bean.setId(rs.getLong(1));
			            bean.setSemester(rs.getString(2));
			            bean.setDescription(rs.getString(3));
			            bean.setExamDate(rs.getDate(4));      
			            bean.setExamTime(rs.getString(5));
			            bean.setCourseId(rs.getLong(6));
			            bean.setCourseName(rs.getString(7));
			            bean.setSubjectId(rs.getLong(8));
			            bean.setSubjectName(rs.getString(9));
			        
			            
			            bean.setCreatedBy(rs.getString(10));
			            bean.setModifiedBy(rs.getString(11));
			            bean.setCreatedDatetime(rs.getTimestamp(12));
			            bean.setModifiedDatetime(rs.getTimestamp(13));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return bean;
		}

	
		 //check TimeTable by Course Name
		
		
		 public static TimetableBean checkByCourseName(long courseId, java.util.Date examDate) {

			    TimetableBean bean = null;

			    String sql = "select * from st_timetable where course_id=? and exam_date=?";

			    try {
			        Connection con = JDBCDataSource.getConnection();
			        PreparedStatement ps = con.prepareStatement(sql);

			        ps.setLong(1, courseId);

			        // convert java.util.Date to java.sql.Date
			        ps.setDate(2, new java.sql.Date(examDate.getTime()));

			        ResultSet rs = ps.executeQuery();

			        if (rs.next()) {
			            bean = new TimetableBean();
			            bean.setId(rs.getLong(1));
			            bean.setSemester(rs.getString(2));
			            bean.setDescription(rs.getString(3));
			            bean.setExamDate(rs.getDate(4));
			            bean.setExamTime(rs.getString(5));
			            bean.setCourseId(rs.getLong(6));
			            bean.setCourseName(rs.getString(7));
			            bean.setSubjectId(rs.getLong(8));
			            bean.setSubjectName(rs.getString(9));
			            bean.setCreatedBy(rs.getString(10));
			            bean.setModifiedBy(rs.getString(11));
			            bean.setCreatedDatetime(rs.getTimestamp(12));
			            bean.setModifiedDatetime(rs.getTimestamp(13));
			        }

			    } catch (Exception e) {
			        e.printStackTrace();
			    }

			    return bean;
			}



}
