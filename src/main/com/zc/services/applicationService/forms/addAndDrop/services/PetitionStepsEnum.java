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
			return "Dean of strategic enrollment has taken action";
		}
		
	}
	,
	ADMISSION_PROCESSING(3) {
			@Override
			public int getID() {
				return 3;
			}
			@Override
			public String getName() {
				return "Waiting Action From Registrar";
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
		,
		DEAN_OF_ACADIMICS(12) {
			@Override
			public int getID() {
				return 12;
			}
			@Override
			public String getName() {
				return "Dean of Academics has taken action";
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
			
		},ACCREDITION_ENG(12) {
			@Override
			public int getID() {
				return 12;
			}
			@Override
			public String getName() {
				return "Accredition Engineering";
			}
			
		},ACCREDITION_Science(13) {
			@Override
			public int getID() {
				return 13;
			}
			@Override
			public String getName() {
				return "Accredition Science";
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
