package main.com.zc.services.domain.shared.enumurations;




/**
 * @author omnya
 *
 */
public enum PetitionActionTypeEnum {

	Approved(0,"Approved") {
		@Override
		public int getID() {
			return 0;
		}
		
		public String getName() {
			return "Approved";
		}
		
	},
	Refused(1,"Refused") {
		
		public int getID() {
			return 1;
		}
		
		public String getName() {
			return "Refused";
		}
	},
	DEAN_APPROVED(2, "Approved") {
		
		public int getID() {
			return 2;
		}
		
		public String getName() {
			return "Approved";
		}
		
		
	},
	DEAN_REFUSED(3,"Refused") {
		@Override
		public int getID() {
			return 3;
		}
		
		public String getName() {
			return "Refused";
		}
	},
	Mark_As_Done_Approving(4, "The request is approved by registrar") {
		@Override
		public int getID() {
			return 4;
		}
		
		public String getName() {
			return "The request is finalized";
		}
		
		
	},
	Mark_As_Done_Refusing(7, "The request is finalized by registrar") {
		@Override
		public int getID() {
			return 7;
		}
		
		public String getName() {
			return "The request is finalized";
		}
		
		
	},
	Admission_Approved(5,"Approved") {
		@Override
		public int getID() {
			return 5;
		}
		
		public String getName() {
			return "Approved";
		}
		
	},
	Admission_Refused(6, "Refused") {
		@Override
		public int getID() {
			return 6;
		}
		public String getName() {
			return "Refused";
		}
	},PROVOST_APPROVED(7, "Approved") {
		
		public int getID() {
			return 7;
		}
		
		public String getName() {
			return "Approved";
		}
		
		
	},
	PROVOST_REFUSED(8,"Refused") {
		@Override
		public int getID() {
			return 8;
		}
		
		public String getName() {
			return "Refused";
		}
	},
	CHECKED_AND_SENT(8,"Checked and sent") {
		@Override
		public int getID() {
			return 8;
		}
		
		public String getName() {
			return "The form is checked by handler and sent to major head";
		}
	},
	CHECKED_AND_REFUSED(9,"Checked and refused") {
		@Override
		public int getID() {
			return 9;
		}
		
		public String getName() {
			return "The form is checked by handler and refused";
		}
	},
	UNDER_PROCESSING(9,"Under processing") {
		@Override
		public int getID() {
			return 9;
		}
		
		public String getName() {
			return "The form is checked by Major head and under processing";
		}
	},
	CLOSED_AND_APPROVED(10,"Closed and approved") {
		@Override
		public int getID() {
			return 10;
		}
		
		public String getName() {
			return "The form is closed & approved by Major head";
		}
	},
	CLOSED_AND_REFUSED(11,"Closed and refused") {
		@Override
		public int getID() {
			return 11;
		}
		
		public String getName() {
			return "The form is closed & refused by Major head";
		}
	},
	REVERT(12,"Revert") {
		@Override
		public int getID() {
			return 12;
		}
		
		public String getName() {
			return "The form reverted";
		}
	},PROCEED(13,"Proceed") {
		@Override
		public int getID() {
			return 13;
		}
		
		public String getName() {
			return "The form Auditied by registrar and will be proceed";
		}
	},
	DECLINE(14,"Decline") {
		@Override
		public int getID() {
			return 14;
		}
		
		public String getName() {
			return "The form Auditied by registrar and will be not to proceed";
		}
	}
	;
public abstract int getID();

private Integer value;
private String name;
private PetitionActionTypeEnum(Integer value, String name) {
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



