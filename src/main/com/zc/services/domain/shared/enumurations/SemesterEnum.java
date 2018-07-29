/**
 * 
 */
package main.com.zc.services.domain.shared.enumurations;

/**
 * @author omnya
 *
 */
public enum SemesterEnum {

	Fall(0) {
		@Override
		public int getID() {
			return 0;
		}
		@Override
		public String getName() {
			return "Fall";
		}
		
	},
	Spring(1) {
		@Override
		public int getID() {
			return 1;
		}
		@Override
		public String getName() {
			return "Spring";
		}
	},Summer(2) {
		@Override
		public int getID() {
			return 2;
		}
		@Override
		public String getName() {
			return "Summer";
		}
		
		
	}
	,Winter(3) {
		@Override
		public int getID() {
			return 3;
		}
		@Override
		public String getName() {
			return "Winter";
		}
		
		
	}
	;
public abstract int getID();
public abstract String getName();
private Integer value;

private SemesterEnum(Integer value) {
	this.value = value;
}

public Integer getValue() {
	return value;
}


}
