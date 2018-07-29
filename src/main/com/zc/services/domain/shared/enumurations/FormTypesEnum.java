/**
 * 
 */
package main.com.zc.services.domain.shared.enumurations;


/**
 * @author heba
 * @since Mar 3, 2015
 * @lastUpdated  Mar 3, 2015 
 */
public enum FormTypesEnum {
	ACADEMICPETITION(0) {
		@Override
		public int getID() {
			return 0;
		}
		@Override
		public String getName() {
			return "Academic Petition";
		}
		
	},
	REPEATECOURSE(1) {
		@Override
		public int getID() {
			return 1;
		}
		@Override
		public String getName() {
			return "Repeate Course";
		}
	},CHANGEMAJOR(2) {
		@Override
		public int getID() {
			return 2;
		}
		@Override
		public String getName() {
			return "Change Major";
		}
		
	}
	,DROPADD(3) {
		@Override
		public int getID() {
			return 3;
		}
		@Override
		public String getName() {
			return "Drop/Add Petition";
		}
		
	}
	,OVERLOADREQUEST(4) {
		@Override
		public int getID() {
			return 4;
		}
		@Override
		public String getName() {
			return "OverLoad Request";
		}
	}
	,INCOMPLETEGRADE(5) {
		@Override
		public int getID() {
			return 5;
		}
		@Override
		public String getName() {
			return "IncompleteGrade";
		}
	}
	,JUNIORTAPROGRAM(6) {
		@Override
		public int getID() {
			return 6;
		}
		@Override
		public String getName() {
			return "Junior TA Program";
		}
	}
	,	CHANGECONCENTRATION(7) {
		@Override
		public int getID() {
			return 7;
		}
		@Override
		public String getName() {
			return "Change of concentration";
		}
	}
	,	FEEDBACK(8) {
		@Override
		public int getID() {
			return 8;
		}
		@Override
		public String getName() {
			return "General Feedback";
		}
	}
	;
public abstract int getID();
public abstract String getName();
private Integer value;
private FormTypesEnum(Integer value) {
	this.value = value;
}

public Integer getValue() {
	return value;
}
}
