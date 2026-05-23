package in.co.rays.proj4.test;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import in.co.rays.proj4.bean.CountryBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.CountryModel;

public class CountryTest {
public static void main(String[] args) throws SQLException, ApplicationException {
	 testAdd();
}


public static void testAdd() throws SQLException, ApplicationException {
	CountryBean bean=new CountryBean();
	CountryModel model=new CountryModel();
	
	bean.setCode("yyy");
	bean.setName("india");
	bean.setRegion("hindu");
	bean.setStatus("off");
	bean.setCreatedBy("admin");
	bean.setModifiedBy("admin");
	bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
	bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
	model.add(bean);
	
}
}
