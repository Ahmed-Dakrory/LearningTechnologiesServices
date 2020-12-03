package main.com.zc.service.instructorSurveyAllBean;

import java.util.ArrayList;
import java.util.List;

import main.com.zc.service.instructor_all_survey_ans.instructor_all_survey_ans;
import main.com.zc.service.instructor_all_survey_ques.instructor_all_survey_ques;

public class cloThreshold {

			 static int Excellent = 4;
			 static int Verygood = 3;
			 static int good = 2;
			 static int Fair = 1;
			 static int NeedsImprovement = 0;
			 static int Threshold = 2;

			private List<cloResult> resultsPersonPercntageClo;

			private float[] cloThresholdPercentage ;
			private float[] cloThresholdPersons ;

			private List<instructor_all_survey_ans> listOfCourseAnswers;
			private instructor_all_survey_ques selectedInstructor;
			private List<instructor_all_survey_ques> listOfCourseQuestions;
			int numberofQuestions=0;
			public cloThreshold( List<instructor_all_survey_ans> listOfInstructorAnswers,
					instructor_all_survey_ques selectedInstructor,int numberofQuestions,List<instructor_all_survey_ques> listOfCourseQuestions) {
				super();
				this.numberofQuestions = numberofQuestions;
				cloThresholdPercentage =new float[numberofQuestions];
				cloThresholdPersons =new float[numberofQuestions];
				

				this.listOfCourseAnswers = listOfInstructorAnswers;
				this.listOfCourseQuestions = listOfCourseQuestions;
				this.selectedInstructor = selectedInstructor;
				
				GenerateThe_resulsPersonPercentageCLO();
				GenerateTheThresholdValues();
			}
			
			
			
			
			private void GenerateTheThresholdValues() {
				// TODO Auto-generated method stub
				for(int i=0;i<this.numberofQuestions;i++) {
					for(int j=4;j>=Threshold;j--) {
						if(resultsPersonPercntageClo!=null&&resultsPersonPercntageClo.size()>0) {
							cloThresholdPercentage[i]+=resultsPersonPercntageClo.get(i).percentage[j];
							cloThresholdPersons[i]+=resultsPersonPercntageClo.get(i).eachGradeCloPersons[j];
						}else {
							cloThresholdPercentage[i]+=0;
							cloThresholdPersons[i]+=0;
						}
						
					}
				}
			}




			private void GenerateThe_resulsPersonPercentageCLO() {
				resultsPersonPercntageClo=new ArrayList<cloResult>();
				
				
				
				if(listOfCourseAnswers!=null) {
					
					
				
				for(int i=0;i<this.numberofQuestions;i++) {

					resultsPersonPercntageClo.add(new cloResult(i+1));
				}
				
				for(int i=0;i<listOfCourseAnswers.size();i++) {
					if(listOfCourseAnswers.get(i).getAns()!=null) {
						Integer res =listOfCourseAnswers.get(i).getAns();
						cloResult clo = null;
						for(int j=0;j<this.getListOfCourseQuestions().size();j++) {

							if(this.getListOfCourseQuestions().get(j).getId().equals(listOfCourseAnswers.get(i).getQuesId().getId())) {

								
								
								clo=resultsPersonPercntageClo.get(j);
								clo.numberOfPersons=clo.numberOfPersons+1;
								int[] numberOfPersonForEachGrade=clo.getEachGradeCloPersons();
								
								numberOfPersonForEachGrade[res]=numberOfPersonForEachGrade[res]+1;
								clo.setEachGradeCloPersons(numberOfPersonForEachGrade);
								clo=resultsPersonPercntageClo.get(j);
								resultsPersonPercntageClo.set(j,clo);
								
								
							}
							
						}
						
					
						
						
					}
					
				}
				
				
				
				for(int i=0;i<resultsPersonPercntageClo.size();i++) {
					cloResult cR= resultsPersonPercntageClo.get(i);
					for(int j=0;j<resultsPersonPercntageClo.get(i).percentage.length;j++) {
						if(resultsPersonPercntageClo.get(i).getNumberOfPersons()!=0) {
							cR.percentage[j]= ((float)cR.getEachGradeCloPersons()[j])/((float) cR.getNumberOfPersons())*100;
							
						}
					}
					
					resultsPersonPercntageClo.set(i, cR);
				}

				
				
				}
			}




			
			
			
			public List<cloResult> getResultsPersonPercntageClo() {
				return resultsPersonPercntageClo;
			}




			public void setResultsPersonPercntageClo(List<cloResult> resultsPersonPercntageClo) {
				this.resultsPersonPercntageClo = resultsPersonPercntageClo;
			}




			public float[] getCloThresholdPercentage() {
				return cloThresholdPercentage;
			}




			public void setCloThresholdPercentage(float[] cloThresholdPercentage) {
				this.cloThresholdPercentage = cloThresholdPercentage;
			}




			public float[] getCloThresholdPersons() {
				return cloThresholdPersons;
			}




			public void setCloThresholdPersons(float[] cloThresholdPersons) {
				this.cloThresholdPersons = cloThresholdPersons;
			}




			public List<instructor_all_survey_ans> getListOfCourseAnswers() {
				return listOfCourseAnswers;
			}




			public void setListOfCourseAnswers(List<instructor_all_survey_ans> listOfCourseAnswers) {
				this.listOfCourseAnswers = listOfCourseAnswers;
			}




			public instructor_all_survey_ques getSelectedInstructor() {
				return selectedInstructor;
			}




			public void setSelectedInstructor(instructor_all_survey_ques selectedInstructor) {
				this.selectedInstructor = selectedInstructor;
			}




			public List<instructor_all_survey_ques> getListOfCourseQuestions() {
				return listOfCourseQuestions;
			}




			public void setListOfCourseQuestions(List<instructor_all_survey_ques> listOfCourseQuestions) {
				this.listOfCourseQuestions = listOfCourseQuestions;
			}




			public int getNumberofQuestions() {
				return numberofQuestions;
			}




			public void setNumberofQuestions(int numberofQuestions) {
				this.numberofQuestions = numberofQuestions;
			}




		
			
			
}
