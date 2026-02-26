package in.co.rays.proj4.controller;

import java.io.IOException;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.AnnounceBean;
import in.co.rays.proj4.bean.BaseBean;


import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;

import in.co.rays.proj4.model.AnnounceModel;

import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet("/AnnounceCtl")
public class AnnounceCtl extends BaseCtl {

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

		if (DataValidator.isNull(request.getParameter("title"))) {
			request.setAttribute("title", PropertyReader.getValue("error.require", "title"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("title"))) {
			request.setAttribute("title", "Invalid title");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("description"))) {
			request.setAttribute("description", PropertyReader.getValue("error.require", "description"));
			pass = false;
		}
		else if (!DataValidator.isName(request.getParameter("description"))) {
			request.setAttribute("description", PropertyReader.getValue("description", " invalid description"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("publishDate"))) {
			request.setAttribute("publishDate", PropertyReader.getValue("error.require", "publishDate"));
			pass = false;
		} else if (!DataValidator.isDate(request.getParameter("publishDate"))) {
			request.setAttribute("publishDate", " invalid publishDate ");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("status"))) {
			request.setAttribute("status", PropertyReader.getValue("error.require", "status"));
			pass = false;
		}

		else if (!DataValidator.isName(request.getParameter("status"))) {
			request.setAttribute("status", PropertyReader.getValue("status", " invalid status"));
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		AnnounceBean bean = new AnnounceBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setCode(DataUtility.getString(request.getParameter("code")));
		bean.setTitle(DataUtility.getString(request.getParameter("title")));
		bean.setDescription(DataUtility.getString(request.getParameter("description")));
		bean.setPublishDate(DataUtility.getDate(request.getParameter("publishDate")));
		bean.setStatus(DataUtility.getString(request.getParameter("status")));

		populateDTO(bean, request);

		return bean;
	}

	@Override
	
	protected void preload(HttpServletRequest request) {

	    AnnounceModel model = new AnnounceModel();

	    try {
	        List announceList = model.list();   // ⭐ correct name
	        request.setAttribute("announceList", announceList); // ⭐ MUST
	    } catch (ApplicationException e) {
	        e.printStackTrace();
	    }
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		long id = DataUtility.getLong(request.getParameter("id"));

		AnnounceModel model = new AnnounceModel();

		if (id > 0) {
			try {
				AnnounceBean bean = model.findByPK(id);
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = DataUtility.getString(request.getParameter("operation"));
		System.out.println("Operation = " + op);

		AnnounceModel model = new AnnounceModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {

			AnnounceBean bean = (AnnounceBean) populateBean(request);
			System.out.println(" id" + request.getParameter("id"));
			try {
				long pk = model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage(" added successfully", request);

			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("code already exists", request);

			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_UPDATE.equalsIgnoreCase(op)) {

			AnnounceBean bean = (AnnounceBean) populateBean(request);

			try {
				if (id > 0) {
					model.update(bean);
				}
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage(" updated successfully", request);

			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("code already exists", request);

			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.ANNOUNCE_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.ANNOUNCE_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {

		return ORSView.ANNOUNCE_VIEW;
	}

}
