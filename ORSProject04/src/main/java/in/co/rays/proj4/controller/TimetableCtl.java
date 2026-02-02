package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.CourseModel;
import in.co.rays.proj4.model.SubjectModel;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet("/TimetableCtl")
public class TimetableCtl extends BaseCtl {

	@Override
	protected void preload(HttpServletRequest request) throws ApplicationException {

		SubjectModel subjectModel = new SubjectModel();
		CourseModel courseModel = new CourseModel();
		System.out.println("Timetable preload called");

		List subjectList = new ArrayList();
		try {
			subjectList = subjectModel.list();
			request.setAttribute("subjectList", subjectList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		List courseList = new ArrayList();
		try {
			courseList = courseModel.list();
			request.setAttribute("courseList", courseList);

		} catch (Exception e) {
			// TODO Auto-generated catch block
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

		return ORSView.TIMETABLE_VIEW;
	}

}
