package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.CollegeModel;
import in.co.rays.proj4.model.CourseModel;
import in.co.rays.proj4.model.SubjectModel;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet("/FacultyCtl")
public class FacultyCtl extends BaseCtl {

	protected void preload(HttpServletRequest request) {

		CollegeModel collegeModel = new CollegeModel();
		SubjectModel subjectModel = new SubjectModel();
		CourseModel courseModel = new CourseModel();

		try {
			List collegeList = collegeModel.list();
			request.setAttribute("collegeList", collegeList);

			List subjectList;
			try {
				subjectList = subjectModel.list();
				request.setAttribute("subjectList", subjectList);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			List courseList;
			try {
				courseList = courseModel.list();
				request.setAttribute("courseList", courseList);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.FACULTY_VIEW;
	}

}
