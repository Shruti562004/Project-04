package in.co.rays.proj4.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.TaxBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.TaxModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "TaxCtl", urlPatterns = { "/ctl/TaxCtl" })
public class TaxCtl extends BaseCtl {

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("code"))) {
			request.setAttribute("code", PropertyReader.getValue("error.require", "code"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("code"))) {
			request.setAttribute("code", "Invalid code");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("type"))) {
			request.setAttribute("type", PropertyReader.getValue("error.require", "type"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("percentage"))) {
			request.setAttribute("percentage", PropertyReader.getValue("error.require", "percentage"));
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("status"))) {
			request.setAttribute("status", PropertyReader.getValue("error.require", "status"));
			pass = false;
		}
		return pass;
	}
	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		TaxBean bean = new TaxBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setCode(DataUtility.getString(request.getParameter("code")));
		bean.setType(DataUtility.getString(request.getParameter("type")));
		
		bean.setPercentage(DataUtility.getInt(request.getParameter("percentage")));
		bean.setStatus(DataUtility.getString(request.getParameter("status")));
		populateDTO(bean, request);

		return bean;
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	
		long id = DataUtility.getLong(request.getParameter("id"));

		TaxModel model = new TaxModel();

		if (id > 0) {
			try {
				TaxBean bean = model.findByPk(id);
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				   ServletUtility.handleException(e, request, response, getView());
				return;
			}
		}
		ServletUtility.forward(getView(), request, response);
	}
	

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

		String op = DataUtility.getString(request.getParameter("operation"));

		TaxModel model = new TaxModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			TaxBean bean = (TaxBean) populateBean(request);

			try {
				try {
					long pk = model.add(bean);
				} catch (ApplicationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Data is successfully saved", request);
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage(" already exists", request);
			} 
		}else if (OP_UPDATE.equalsIgnoreCase(op)) {

			TaxBean bean = (TaxBean) populateBean(request);

			try {
				if (id > 0) {
					model.update(bean);
				}
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Data is successfully updated", request);
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage(" already exists", request);
			} catch (Exception e) {
				e.printStackTrace();
				   
				return;
			} 

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.TAX_LIST_CTL, request, response);
			return;
		}
		
		 else if (OP_RESET.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.TAX_CTL, request, response);
				return;
			}
		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.TAX_VIEW;
	}
}