package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.MarksheetBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.MarksheetModel;
import in.co.rays.proj4.model.StudentModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet("/MarksheetCtl")
public class MarksheetCtl  extends BaseCtl{
	
	
	@Override
	protected void preload(HttpServletRequest request) {
		StudentModel studentModel = new StudentModel();
		try {
			List studentList = studentModel.list();
			request.setAttribute("studentList", studentList);
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		MarksheetBean bean = new MarksheetBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setRollNo(DataUtility.getString(request.getParameter("rollNo")));
		bean.setName(DataUtility.getString(request.getParameter("name")));

		if (request.getParameter("physics") != null && request.getParameter("physics").length() != 0) {
			bean.setPhysics(DataUtility.getInt(request.getParameter("physics")));
		}
		if (request.getParameter("chemistry") != null && request.getParameter("chemistry").length() != 0) {
			bean.setChemistry(DataUtility.getInt(request.getParameter("chemistry")));
		}
		if (request.getParameter("maths") != null && request.getParameter("maths").length() != 0) {
			bean.setMaths(DataUtility.getInt(request.getParameter("maths")));
		}

		bean.setStudentId(DataUtility.getLong(request.getParameter("studentId")));

		populateDTO(bean, request);

		return bean;
	}
	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("studentId"))) {
			request.setAttribute("studentId", PropertyReader.getValue("error.require", "Student Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("rollNo"))) {
			request.setAttribute("rollNo", PropertyReader.getValue("error.require", "Roll Number"));
			pass = false;
		} else if (!DataValidator.isRollNo(request.getParameter("rollNo"))) {
			request.setAttribute("rollNo", "Roll No is invalid");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("physics"))) {
			request.setAttribute("physics", PropertyReader.getValue("error.require", "Marks"));
			pass = false;
		} else if (DataValidator.isNotNull(request.getParameter("physics"))
				&& !DataValidator.isInteger(request.getParameter("physics"))) {
			request.setAttribute("physics", PropertyReader.getValue("error.integer", "Marks"));
			pass = false;
		} else if (DataUtility.getInt(request.getParameter("physics")) > 100
				|| DataUtility.getInt(request.getParameter("physics")) < 0) {
			request.setAttribute("physics", "Marks should be in 0 to 100");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("chemistry"))) {
			request.setAttribute("chemistry", PropertyReader.getValue("error.require", "Marks"));
			pass = false;
		} else if (DataValidator.isNotNull(request.getParameter("chemistry"))
				&& !DataValidator.isInteger(request.getParameter("chemistry"))) {
			request.setAttribute("chemistry", PropertyReader.getValue("error.integer", "Marks"));
			pass = false;
		} else if (DataUtility.getInt(request.getParameter("chemistry")) > 100
				|| DataUtility.getInt(request.getParameter("chemistry")) < 0) {
			request.setAttribute("chemistry", "Marks should be in 0 to 100");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("maths"))) {
			request.setAttribute("maths", PropertyReader.getValue("error.require", "Marks"));
			pass = false;
		} else if (DataValidator.isNotNull(request.getParameter("maths"))
				&& !DataValidator.isInteger(request.getParameter("maths"))) {
			request.setAttribute("maths", PropertyReader.getValue("error.integer", "Marks"));
			pass = false;
		} else if (DataUtility.getInt(request.getParameter("maths")) > 100
				|| DataUtility.getInt(request.getParameter("maths")) < 0) {
			request.setAttribute("maths", "Marks should be in 0 to 100");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("studentId"))) {
			request.setAttribute("studentId", PropertyReader.getValue("error.require", "Student Name"));
			pass = false;
		}

		return pass;
	}

  @Override
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletUtility.forward(getView(), request, response);
}
  
  
 @Override
		protected void doPost(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {

			List list = null;
			List next = null;

			int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
			int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

			pageNo = (pageNo == 0) ? 1 : pageNo;
			pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

			MarksheetBean bean = (MarksheetBean) populateBean(request);
			MarksheetModel model = new MarksheetModel();

			String op = DataUtility.getString(request.getParameter("operation"));
			String[] ids = request.getParameterValues("ids");

			try {

				if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op) || "Previous".equalsIgnoreCase(op)) {

					if (OP_SEARCH.equalsIgnoreCase(op)) {
						pageNo = 1;
					} else if (OP_NEXT.equalsIgnoreCase(op)) {
						pageNo++;
					} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
						pageNo--;
					}

				} else if (OP_NEW.equalsIgnoreCase(op)) {
					ServletUtility.redirect(ORSView.MARKSHEET_CTL, request, response);
					return;

				} else if (OP_DELETE.equalsIgnoreCase(op)) {
					pageNo = 1;
					if (ids != null && ids.length > 0) {
						MarksheetBean deletebean = new MarksheetBean();
						for (String id : ids) {
							deletebean.setId(DataUtility.getInt(id));
							model.delete(deletebean);
							ServletUtility.setSuccessMessage("Marksheet is deleted successfully", request);
						}
					} else {
						ServletUtility.setErrorMessage("Select at least one record", request);
					}

				} else if (OP_RESET.equalsIgnoreCase(op)) {
					ServletUtility.redirect(ORSView.MARKSHEET_LIST_CTL, request, response);
					return;

				} else if (OP_BACK.equalsIgnoreCase(op)) {
					ServletUtility.redirect(ORSView.MARKSHEET_LIST_CTL, request, response);
					return;
				}

				list = model.search(bean, pageNo, pageSize);
				next = model.search(bean, pageNo + 1, pageSize);

				if (list == null || list.size() == 0) {
					ServletUtility.setErrorMessage("No record found ", request);
				}

				ServletUtility.setList(list, request);
				ServletUtility.setPageNo(pageNo, request);
				ServletUtility.setPageSize(pageSize, request);
				ServletUtility.setBean(bean, request);
				request.setAttribute("nextListSize", next.size());

				ServletUtility.forward(getView(), request, response);
			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
	}
	@Override
	protected String getView() {
		
		return ORSView.MARKSHEET_VIEW ;
	}


}
