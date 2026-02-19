package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.FollowupBean;
import in.co.rays.proj4.model.FollowupModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet("/FollowupCtl")
public class FollowupCtl extends BaseCtl {
	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("patient"))) {

			request.setAttribute("patient", PropertyReader.getValue("error.require", "patient"));
			pass = false;

		} else if (!DataValidator.isName(request.getParameter("patient"))) {

			request.setAttribute("patient", "patient ");
			pass = false;

		}

		if (DataValidator.isNull(request.getParameter("doctor"))) {
			request.setAttribute("doctor", PropertyReader.getValue("error.require", "doctor"));
			pass = false;

		} else if (!DataValidator.isName(request.getParameter("doctor"))) {
			request.setAttribute("doctor", "Doctor name is invalid");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("dob"))) {
			request.setAttribute("dob", PropertyReader.getValue("error.require", " invalid Date of Birth"));
			pass = false;

		} else if (!DataValidator.isDate(request.getParameter("dob"))) {
			request.setAttribute("error.date", "Date of Birth");
			pass = false;

		}

		if (DataValidator.isNull(request.getParameter("fees"))) {
			request.setAttribute("fees", PropertyReader.getValue("error.require", "product"));
			pass = false;

		} else if (!DataValidator.isLong(request.getParameter("fees"))) {
			request.setAttribute("error.require", "fees");
			pass = false;

		}

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		FollowupBean bean = new FollowupBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setPatient(DataUtility.getString(request.getParameter("patient")));
		bean.setDoctor(DataUtility.getString(request.getParameter("doctor")));
		bean.setDob(DataUtility.getDate(request.getParameter("dob")));

		bean.setFees(DataUtility.getString(request.getParameter("fees")));

		populateDTO(bean, request);
		return bean;

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ServletUtility.forward(getView(), request, response);

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = DataUtility.getString(request.getParameter("operation"));
		System.out.println(op + "fllwup");
		FollowupModel model = new FollowupModel();

		if (OP_SAVE.equalsIgnoreCase(op)) {
			FollowupBean bean = (FollowupBean) populateBean(request);
			try {
				long pk = model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Record Added Successfully", request);

			} catch (Exception e) {
				e.printStackTrace();
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Database error", request);
			}
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(getView(), request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);

	}

	@Override
	protected String getView() {
		return ORSView.FOLLOWUP_VIEW;

	}

}
