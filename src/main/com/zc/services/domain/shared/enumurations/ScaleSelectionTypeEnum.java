/**
 * 
 */
package main.com.zc.services.domain.shared.enumurations;

import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory.Default;

/**
 * @author Omnya Alaa
 *
 */
public enum ScaleSelectionTypeEnum {
	MAIN(0) {
		@Override
		public int getID() {
			return 0;
		}
		@Override
		public String getName() {
			return "main";
		}
		
	},
	STRENGTH(1) {
		@Override
		public int getID() {
			return 1;
		}
		@Override
		public String getName() {
			return "strength";
		}
	},
	CONCERN(2) {
		@Override
		public int getID() {
			return 2;
		}
		@Override
		public String getName() {
			return "concern";
		}
		
		
	},
	NA(3) {
		@Override
		public int getID() {
			return 3;
		}
		@Override
		public String getName() {
			return "NA";
		}}
	;
public abstract int getID();
public abstract String getName();
private Integer value;

 ScaleSelectionTypeEnum(Integer value) {
	this.value = value;
}

 public static ScaleSelectionTypeEnum getByid(Integer id){
	switch (id) {
	case 0:
		return ScaleSelectionTypeEnum.MAIN;
	case 1:
		return ScaleSelectionTypeEnum.STRENGTH;
	
	case 2:
	return ScaleSelectionTypeEnum.CONCERN;
	case 3:
		return ScaleSelectionTypeEnum.NA;
	
 	default:
	  return ScaleSelectionTypeEnum.MAIN;
	  }
 }
public Integer getValue() {
	return value;
}

}
