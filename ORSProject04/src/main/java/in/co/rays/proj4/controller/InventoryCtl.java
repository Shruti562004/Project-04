package in.co.rays.proj4.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.InventoryBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.InventoryModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet("/InventoryCtl")
public class InventoryCtl extends BaseCtl {

    @Override
    protected boolean validate(HttpServletRequest request) {

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("supplierName"))) {
            request.setAttribute("supplierName",
                    PropertyReader.getValue("error.require", "Supplier Name"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("dob"))) {
            request.setAttribute("dob",
                    PropertyReader.getValue("error.require", "Date of Birth"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("quantity"))) {
            request.setAttribute("quantity",
                    PropertyReader.getValue("error.require", "Quantity"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("product"))) {
            request.setAttribute("product",
                    PropertyReader.getValue("error.require", "Product"));
            pass = false;
        }

        return pass;
    }

    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        InventoryBean bean = new InventoryBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setSupplierName(DataUtility.getString(request.getParameter("supplierName")));
        bean.setDob(DataUtility.getDate(request.getParameter("dob")));
        bean.setQuantity(DataUtility.getLong(request.getParameter("quantity")));
        bean.setProduct(DataUtility.getString(request.getParameter("product")));

        populateDTO(bean, request);

        return bean;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        long id = DataUtility.getLong(request.getParameter("id"));
        InventoryModel model = new InventoryModel();

        if (id > 0) {
            InventoryBean bean = model.findByPk(id);
			ServletUtility.setBean(bean, request);
        }

        ServletUtility.forward(getView(), request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String op = DataUtility.getString(request.getParameter("operation"));
        InventoryModel model = new InventoryModel();
        long id = DataUtility.getLong(request.getParameter("id"));

        if (OP_SAVE.equalsIgnoreCase(op)) {

            InventoryBean bean = (InventoryBean) populateBean(request);

            try {
                try {
					long pk = model.add(bean);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Inventory added successfully", request);

            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Record already exists", request);

            }

        } else if (OP_UPDATE.equalsIgnoreCase(op)) {

            InventoryBean bean = (InventoryBean) populateBean(request);

            try {
                if (id > 0) {
                    model.update(bean);
                }
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage("Inventory updated successfully", request);

            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Record already exists", request);

            } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

        
        } else if (OP_RESET.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.INVENTORY_CTL, request, response);
            return;
        }

        ServletUtility.forward(getView(), request, response);
    }

    @Override
    protected String getView() {
        return ORSView.INVENTORY_VIEW;
    }
}
