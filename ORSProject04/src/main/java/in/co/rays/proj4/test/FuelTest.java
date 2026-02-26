package in.co.rays.proj4.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.FuelBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.FuelModel;

public class FuelTest {

	public static void main(String[] args) throws Exception {
		//testAdd();
		 //testDelete();
		// testUpdate();
		// testFindByCode();
		// testFindByPk();
		testSearch();
		// list();
	}

	// ================= ADD =================
	public static void testAdd() throws DatabaseException, ParseException {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			FuelBean bean = new FuelBean();
			bean.setEntryCode("SAAA");
			bean.setVehicleNumber("342");
			bean.setFuelAmount(80.8888);
			bean.setFuelDate(sdf.parse("2026-07-06"));

			FuelModel model = new FuelModel();

			long pk = model.add(bean);
			System.out.println("Test Add Success, PK = " + pk);

		} catch (DuplicateRecordException e) {
			System.out.println("Record already exists");
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	// ================= DELETE =================
	public static void testDelete() {

		FuelBean bean = new FuelBean();
		bean.setId(2);

		FuelModel model = new FuelModel();

		try {
			model.delete(bean);
			System.out.println("Fuel deleted successfully");
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	// ================= UPDATE =================
	public static void testUpdate() throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		FuelBean bean = new FuelBean();
		bean.setId(1);
		bean.setEntryCode("AS");
		bean.setVehicleNumber("342");
		bean.setFuelAmount(80.8888);
		bean.setFuelDate(sdf.parse("2026-07-06"));

		FuelModel model = new FuelModel();

		try {
			model.update(bean);
			System.out.println("Fuel updated successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ================= FIND BY CODE =================
	public static void testFindByCode() {

		FuelModel model = new FuelModel();

		try {
			FuelBean bean = model.findByCode("AS");

			if (bean == null) {
				System.out.println("Test Find by Code Fail");
				return;
			}

			System.out.println(bean.getId());
			System.out.println(bean.getEntryCode());
			System.out.println(bean.getVehicleNumber());
			System.out.println(bean.getFuelAmount());
			System.out.println(bean.getFuelDate());

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	// ================= FIND BY PK =================
	public static void testFindByPk() {

		FuelModel model = new FuelModel();

		try {
			FuelBean bean = model.findByPK(1);

			if (bean == null) {
				System.out.println("Test Find by PK Fail");
				return;
			}

			System.out.println(bean.getId());
			System.out.println(bean.getEntryCode());
			System.out.println(bean.getVehicleNumber());
			System.out.println(bean.getFuelAmount());
			System.out.println(bean.getFuelDate());

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	// ================= SEARCH =================
	public static void testSearch() throws DatabaseException {

		try {
			FuelModel model = new FuelModel();
			FuelBean bean = new FuelBean();

			bean.setEntryCode("SAA");

			List<FuelBean> list = model.search(bean, 0, 0);

			if (list.size() == 0) {
				System.out.println("Test Search Fail");
				return;
			}

			Iterator<FuelBean> it = list.iterator();
			while (it.hasNext()) {
				FuelBean b = it.next();
				System.out.println(b.getId());
				System.out.println(b.getEntryCode());
				System.out.println(b.getVehicleNumber());
				System.out.println(b.getFuelAmount());
				System.out.println(b.getFuelDate());
			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	// ================= LIST =================
	private static void list() {

		try {
			FuelModel model = new FuelModel();

			List<FuelBean> list = model.list(1, 10);

			if (list.size() == 0) {
				System.out.println("Test List Fail");
				return;
			}

			Iterator<FuelBean> it = list.iterator();
			while (it.hasNext()) {
				FuelBean bean = it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getEntryCode());
				System.out.println(bean.getVehicleNumber());
				System.out.println(bean.getFuelAmount());
				System.out.println(bean.getFuelDate());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}