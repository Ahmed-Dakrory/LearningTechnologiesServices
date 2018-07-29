/**
 * 
 */
package main.com.zc.services.domain.shared.enumurations;

/**
 * @author omnya
 *
 */
public enum DropTypesEnum {
	WP(0) {
		@Override
		public int getID() {
			return 0;
		}
		
		
	},
	WF(1) {
		@Override
		public int getID() {
			return 1;
		}
	
	},
	;
public abstract int getID();

private Integer value;

private DropTypesEnum(Integer valuee) {
	this.value = value;

}

public Integer getValue() {
	return value;
}
}
