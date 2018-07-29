/**
 * 
 */
package main.com.zc.services.domain.shared.enumurations;

/**
 * @author Omnya Alaa
 *
 */
public enum BookStatusEnum {
	FREE(0,"Free") {
		@Override
		public int getID() {
			return 0;
		}
		
		public String getName() {
			return "Free";
		}
		
	},
	HELD(1,"Held") {
		
		public int getID() {
			return 1;
		}
		
		public String getName() {
			return "Held";
		}
	},
	
	
	;
public abstract int getID();

private Integer value;
private String name;
private BookStatusEnum(Integer value, String name) {
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
