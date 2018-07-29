/**
 * 
 */
package main.com.zc.services.applicationService.forms.addAndDrop.services;

/**
 * @author omnya
 *
 */
public enum PetitionStepsEnum {
	UNDER_REVIEW(0) {
		@Override
		public int getID() {
			return 0;
		}
		@Override
		public String getName() {
			return "Under Review";
		}
		
	},
	INSTRUCTOR(1) {
		@Override
		public int getID() {
			return 1;
		}
		@Override
		public String getName() {
			return "Instructor has taken action";
		}
}
	,
	DEAN(2) {
		@Override
		public int getID() {
			return 2;
		}
		@Override
		public String getName() {
			return "Dean has taken action";
		}
		
	}
	,
	ADMISSION_HEAD(3) {
			@Override
			public int getID() {
				return 3;
			}
			@Override
			public String getName() {
				return "Admission Head has taken action";
			}
			
		}
	,
	ADMISSION_DEPT(4) {
			@Override
			public int getID() {
				return 4;
			}
			@Override
			public String getName() {
				return "Admission Dept has taken action";
			}
			
		}
	,
	PROVOST(5) {
			@Override
			public int getID() {
				return 5;
			}
			@Override
			public String getName() {
				return "Provost has taken action";
			}
			
		}
	,
	INSTRUCTOR_OF_COURSE(6) {
			@Override
			public int getID() {
				return 6;
			}
			@Override
			public String getName() {
				return "Instructor of the course has take action";
			}
			
		},
		PA(7) {
			@Override
			public int getID() {
				return 7;
			}
			@Override
			public String getName() {
				return "Program advisor has take action";
			}
			
		},
		
		CHECKED(8) {
			@Override
			public int getID() {
				return 8;
			}
			@Override
			public String getName() {
				return "Checked by feedback handler";
			}
			
		},
		UNDER_PROCESSING(9) {
			@Override
			public int getID() {
				return 9;
			}
			@Override
			public String getName() {
				return "Under processing";
			}
			
		},
		CLOSED(10) {
			@Override
			public int getID() {
				return 10;
			}
			@Override
			public String getName() {
				return "Closed";
			}
			}
			
	,AUDITING(11) {
		@Override
		public int getID() {
			return 11;
		}
		@Override
		public String getName() {
			return "Auditing";
		}
		
	}
	;
public abstract int getID();
public abstract String getName();
private Integer value;
private PetitionStepsEnum(Integer value) {
	this.value = value;
}

public Integer getValue() {
	return value;
}
}
