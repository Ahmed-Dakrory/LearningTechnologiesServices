/**
 * 
 */
package main.com.zc.services.domain.shared.enumurations;

/**
 * @author momen
 *
 */
public enum AcceptRefuseFormEnum {

	Accept(0) {
		@Override
		public int getID() {
			return 0;
		}
		@Override
		public String getName() {
			return "Accept";
		}
		
	},
	Refuse(1) {
		@Override
		public int getID() {
			return 1;
		}
		@Override
		public String getName() {
			return "Refuse";
		}
	},
	Not(2) {
		@Override
		public int getID() {
			return 2;
		}
		@Override
		public String getName() {
			return "Not";
		}
		
		
	}
	;
public abstract int getID();
public abstract String getName();
private Integer value;

private AcceptRefuseFormEnum(Integer value) {
	this.value = value;
}

public Integer getValue() {
	return value;
}


}
