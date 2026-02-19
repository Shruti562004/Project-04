package in.co.rays.proj4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.EventBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.CourseModel;
import in.co.rays.proj4.model.EventModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet("/EventListCtl")
public class EventListCtl extends BaseCtl {

	
	@Override
	protected void preload(HttpServletRequest request) throws Exception {

		EventModel  model = new EventModel();

		try {
			List eventList = model.list(0, 0);
			request.setAttribute("eventList", eventList);
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        EventBean bean = new EventBean();

        bean.setEventCode(DataUtility.getString(request.getParameter("eventCode")));
        bean.setEventName(DataUtility.getString(request.getParameter("eventName")));
        bean.setEventDate(DataUtility.getDate(request.getParameter("eventDate")));
        bean.setEventLocation(DataUtility.getString(request.getParameter("eventLocation")));
     


        return bean;
    }


    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        int pageNo = 1;
        int pageSize = DataUtility.getInt(
                PropertyReader.getValue("page.size"));

        EventBean bean = (EventBean) populateBean(request);
        EventModel model = new EventModel();
        try {

            /* ✅ MAIN LIST */
            List list = model.search(bean, pageNo, pageSize);
            List nextList = model.search(bean, pageNo + 1, pageSize);

            request.setAttribute("nextListSize", nextList.size());
            ServletUtility.setList(list, request);

            /* 🔥 ADD THESE (MISSING — CAUSING ERROR) */
            ServletUtility.setPageNo(pageNo, request);
            ServletUtility.setPageSize(pageSize, request);
            ServletUtility.setBean(bean, request);

            /* ✅ DROPDOWN */
            List<EventBean> eventDropdownList = model.list(0, 0);
            request.setAttribute("eventDropdownList", eventDropdownList);

            ServletUtility.forward(getView(), request, response);

        } catch (ApplicationException e) {
            ServletUtility.handleException(e, request, response);
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<EventBean> list = null;
        List<EventBean> next = null;

        int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
        int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

        pageNo = (pageNo == 0) ? 1 : pageNo;
        pageSize = (pageSize == 0)
                ? DataUtility.getInt(PropertyReader.getValue("page.size"))
                : pageSize;

        EventBean bean = (EventBean) populateBean(request);
        EventModel model = new EventModel();

        String op = DataUtility.getString(request.getParameter("operation"));
        String[] ids = request.getParameterValues("ids");
        System.out.println("in do get");
        try {

            if (OP_SEARCH.equalsIgnoreCase(op)) {
                pageNo = 1;

            } else if (OP_NEXT.equalsIgnoreCase(op)) {
                pageNo++;

            } else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
                pageNo--;

            } else if (OP_NEW.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.EVENT_CTL, request, response);
                return;

            } else if (OP_DELETE.equalsIgnoreCase(op)) {

                pageNo = 1;

                if (ids != null && ids.length > 0) {
                    EventBean deleteBean = new EventBean();

                    for (String id : ids) {
                        deleteBean.setId(DataUtility.getLong(id));
                        model.delete(deleteBean);
                    }

                    ServletUtility.setSuccessMessage("Event deleted successfully", request);

                } else {
                    ServletUtility.setErrorMessage("Select at least one record", request);
                }

            } else if (OP_RESET.equalsIgnoreCase(op) || OP_BACK.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.EVENT_LIST_CTL, request, response);
                return;
            }

            list = model.search(bean, pageNo, pageSize);
            next = model.search(bean, pageNo + 1, pageSize);

            if (list == null || list.isEmpty()) {
                ServletUtility.setErrorMessage("No record found", request);
            }

            ServletUtility.setList(list, request);
            ServletUtility.setPageNo(pageNo, request);
            ServletUtility.setPageSize(pageSize, request);
            ServletUtility.setBean(bean, request);
            request.setAttribute("nextListSize", next.size());
            /* 🔥 MUST ADD THIS */
            List<EventBean> eventDropdownList = model.list(0, 0);
            request.setAttribute("eventDropdownList", eventDropdownList);


            ServletUtility.forward(getView(), request, response);

        } catch (ApplicationException e) {
            e.printStackTrace();
            ServletUtility.handleException(e, request, response);
        }
    }

    @Override
    protected String getView() {
        return ORSView.EVENT_LIST_VIEW;
    }
}
