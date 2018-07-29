/**
 * 
 */
package main.com.zc.services.domain.shared.enumurations;

/**
 * @author omnya
 *
 */
public enum FormsStatusEnum {


	Active(0,"Active") {
		@Override
		public int getID() {
			return 0;
		}
		
		public String getName() {
			return "Active";
		}
		
	},
	Inactive(1,"Inactive") {
		
		public int getID() {
			return 1;
		}
		
		public String getName() {
			return "Inactive";
		}
	},
	Second_Phase(2, "Phase 2") {
		
		public int getID() {
			return 2;
		}
		
		public String getName() {
			return "Phase 2";
		}
		
		
	},
	Third_Phase(3,"Phase 3") {
		@Override
		public int getID() {
			return 3;
		}
		
		public String getName() {
			return "Phase 3";
		}
	},
	
	
	;
public abstract int getID();

private Integer value;
private String name;
private FormsStatusEnum(Integer value, String name) {
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
