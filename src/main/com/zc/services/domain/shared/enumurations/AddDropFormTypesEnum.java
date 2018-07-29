/**
 * 
 */
package main.com.zc.services.domain.shared.enumurations;

/**
 * @author omnya
 *
 */
public enum AddDropFormTypesEnum {
	DROP(0) {
		@Override
		public int getID() {
			return 0;
		}
		@Override
		public String getName() {
			return "Drop";
		}
		
	},
	ADD(1) {
		@Override
		public int getID() {
			return 1;
		}
		@Override
		public String getName() {
			return "Add";
		}
	},DROPANDADD(2) {
		@Override
		public int getID() {
			return 2;
		}
		@Override
		public String getName() {
			return "Drop And Add";
		}
		
	}
	;
public abstract int getID();
public abstract String getName();
private Integer value;
private AddDropFormTypesEnum(Integer value) {
	this.value = value;
}

public Integer getValue() {
	return value;
}
}
