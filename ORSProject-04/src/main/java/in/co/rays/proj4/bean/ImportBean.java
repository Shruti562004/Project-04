package in.co.rays.proj4.bean;

public class ImportBean  extends BaseBean{
	private String code;
          private String name;
		@Override
		public String getKey() {
			return null;
		}
		@Override
		public String getValue() {
		
			return null;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}

}
