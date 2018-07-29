/**
 * 
 */
package main.com.zc.services.domain.shared.enumurations;

/**
 * @author omnya
 *
 */
public enum QuestionsCategory {
	Rate(0,"Other Questions Rate") {
		@Override
		public int getID() {
			return 0;
		}
		public String getName() {
			return "Other Questions Rate";
		}
	},
	Faculty_Eval(1,"Faculty Evaluation") {
		@Override
		public int getID() {
			return 1;
		}
		public String getName() {
			return "Faculty Evaluation";
		}
		
	},
	Course_Eval_course(2,"Course") {
		@Override
		public int getID() {
			return 2;
		}
		public String getName() {
			return "Course";
		}
	},
	Course_Eval_assign(3,"Online Assignments") {
		@Override
		public int getID() {
			return 3;
		}
		public String getName() {
			return "Online Assignments";
		}
	},
	Teach_Asis(4,"Teaching Assistant Evaluation") {
		@Override
		public int getID() {
			return 4;
		}
		public String getName() {
			return "Teaching Assistant Evaluation";
		}
	},
	Lab_Eval_Fac(5,"Faculty") {
		@Override
		public int getID() {
			return 5;
		}
		public String getName() {
			return "Faculty";
		}
	},
	Lab_Eval_Exp(6,"Experiments") {
		@Override
		public int getID() {
			return 6;
		}
		public String getName() {
			return "Experiments";
		}
	},
	Other_Ques_Text(7,"Other Questions Text") {
		@Override
		public int getID() {
			return 7;
		}
		public String getName() {
			return "Other Questions Text";
		}
	},
	Course_Eval(8,"Course Evaluation") {
		@Override
		public int getID() {
			return 8;
		}
		public String getName() {
			return "Course Evaluation";
		}
	},
	Student_Eval(9,"Self Evaluation") {
		@Override
		public int getID() {
			return 9;
		}
		public String getName() {
			return "Self Evaluation";
		}
	},
	Instructor_Asis(10,"Instructor Evaluation") {
		@Override
		public int getID() {
			return 10;
		}
		public String getName() {
			return "Instructor Evaluation";
		}
	},
	Languag_Of_Instruction(11,"Language of instruction") {
		@Override
		public int getID() {
			return 11;
		}
		public String getName() {
			return "Language of instruction";
		}
	},
	Teaching_Asis(12,"Teaching Assistant Evaluation") {
		@Override
		public int getID() {
			return 12;
		}
		public String getName() {
			return "Teaching Assistant Evaluation";
		}
	},
	Lab_Asis(13,"Lab Evaluation") {
		@Override
		public int getID() {
			return 13;
		}
		public String getName() {
			return "Lab Evaluation";
		}
	},
	Other_Comments(14,"Other Comments") {
		@Override
		public int getID() {
			return 14;
		}
		public String getName() {
			return "Lab Evaluation";
		}
	},
	Ins_Eval_TA(15,"Professors evaluation to Tas") {
		@Override
		public int getID() {
			return 15;
		}
		public String getName() {
			return "Professors evaluation to Tas";
		}
	},
	TA_Eval_TA(16,"Peer evalution to Tas") {
		@Override
		public int getID() {
			return 16;
		}
		public String getName() {
			return "Peer evalution to Tas";
		}
	},
	LECTURE_OBJECTIVES_FEEDBACK(17,"Lecture Objectives Feedback") {
		@Override
		public int getID() {
			return 17;
		}
		public String getName() {
			return "Lecture Objectives Feedback";
		}
	},
	MIDTERM_EVALUATION_INSTRUCTOR(319,"Instructor Evaluation") {
		@Override
		public int getID() {
			return 319;
		}
		public String getName() {
			return "Instructor Evaluation";
		}
	},
	MIDTERM_EVALUATION_TA(321,"Teaching Assistant Evaluation") {
		@Override
		public int getID() {
			return 321;
		}
		public String getName() {
			return "Teaching Assistant Evaluation";
		}
	},
	MIDTERM_EVALUATION_LAB(322,"Lab Evaluation") {
		@Override
		public int getID() {
			return 322;
		}
		public String getName() {
			return "Lab Evaluation";
		}
	},
	MIDTERM_EVALUATION_OTHER(324,"Others") {
		@Override
		public int getID() {
			return 324;
		}
		public String getName() {
			return "Others";
		}
	}
	
	;
public abstract int getID();

private Integer value;
private String name;
private QuestionsCategory(Integer value,String name) {
	this.value = value;
	this.name=name;

}

public Integer getValue() {
	return value;
}

public String getName() {
	return name;
}

}
