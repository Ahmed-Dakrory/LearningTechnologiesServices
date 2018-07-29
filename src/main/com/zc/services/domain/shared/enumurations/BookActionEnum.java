/**
 * 
 */
package main.com.zc.services.domain.shared.enumurations;

/**
 * @author omnya
 *
 */
public enum BookActionEnum {

	RESERVE(0,"Reserve") {
		@Override
		public int getID() {
			return 0;
		}
		
		public String getName() {
			return "Reserve";
		}
		
	},
	RETURN(1,"Return") {
		
		public int getID() {
			return 1;
		}
		
		public String getName() {
			return "Return";
		}
	},
	
	
	;
public abstract int getID();

private Integer value;
private String name;
private BookActionEnum(Integer value, String name) {
	this.value = value;
	this.name = name;
}

public Integer getValue() {
	return value;
}

public String getName() {
	return name;
}

}
