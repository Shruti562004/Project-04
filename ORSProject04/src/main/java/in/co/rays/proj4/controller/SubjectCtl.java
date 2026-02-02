package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.CourseModel;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet("/SubjectCtl")
public class SubjectCtl extends BaseCtl {


	@Override
	protected void preload(HttpServletRequest request) throws Exception {
		CourseModel courseModel = new CourseModel();
		try {
			List courseList = courseModel.list();
			request.setAttribute("courseList", courseList);
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
	  @Override
	  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  		ServletUtility.forward(getView(), request, response);
	  }
	    
	    
	    @Override
	  	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  		ServletUtility.forward(getView(), request, response);
	  	}
	    
	@Override
	protected String getView() {
	return ORSView.SUBJECT_VIEW;
	}

}
