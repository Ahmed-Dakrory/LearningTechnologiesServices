package main.com.zc.service.courseCloBean;

import java.util.ArrayList;
import java.util.List;

import main.com.zc.service.clo_survey_ans.clo_survey_ans;
import main.com.zc.service.courseClo.course_clo;

public class cloThreshold {

			 static int Excellent = 4;
			 static int Verygood = 3;
			 static int good = 2;
			 static int Fair = 1;
			 static int NeedsImprovement = 0;
			 static int Threshold = 2;

			private List<cloResult> resultsPersonPercntageClo;

			private float[] cloThresholdPercentage =new float[20];
			private float[] cloThresholdPersons =new float[20];

			private List<clo_survey_ans> listOfCourseAnswers;
			private course_clo selectedCourse;
			public cloThreshold( List<clo_survey_ans> listOfCourseAnswers,
					course_clo selectedCourse) {
				super();
				this.listOfCourseAnswers = listOfCourseAnswers;
				this.selectedCourse = selectedCourse;
				
				GenerateThe_resulsPersonPercentageCLO();
				GenerateTheThresholdValues();
			}
			
			
			
			
			private void GenerateTheThresholdValues() {
				// TODO Auto-generated method stub
				for(int i=0;i<20;i++) {
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
					
					
				
				for(int i=0;i<20;i++) {

					resultsPersonPercntageClo.add(new cloResult(i+1));
				}
				
				for(int i=0;i<listOfCourseAnswers.size();i++) {
					if(listOfCourseAnswers.get(i).getClo1()!=null) {
						Integer res =listOfCourseAnswers.get(i).getClo1();
						res=res-1;
						cloResult clo=resultsPersonPercntageClo.get(0);
						clo.numberOfPersons=clo.numberOfPersons+1;
						int[] numberOfPersonForEachGrade=clo.getEachGradeCloPersons();
						
						numberOfPersonForEachGrade[res]=numberOfPersonForEachGrade[res]+1;
						clo.setEachGradeCloPersons(numberOfPersonForEachGrade);
						resultsPersonPercntageClo.set(0,clo);
						
					}
					
					if(listOfCourseAnswers.get(i).getClo2()!=null) {
						Integer res =listOfCourseAnswers.get(i).getClo2();
						res=res-1;
						cloResult clo=resultsPersonPercntageClo.get(1);
						clo.numberOfPersons=clo.numberOfPersons+1;
						int[] numberOfPersonForEachGrade=clo.getEachGradeCloPersons();
						
						numberOfPersonForEachGrade[res]=numberOfPersonForEachGrade[res]+1;
						clo.setEachGradeCloPersons(numberOfPersonForEachGrade);
						resultsPersonPercntageClo.set(1,clo);
					}
					
					if(listOfCourseAnswers.get(i).getClo3()!=null) {
						Integer res =listOfCourseAnswers.get(i).getClo3();
						res=res-1;
						cloResult clo=resultsPersonPercntageClo.get(2);
						clo.numberOfPersons=clo.numberOfPersons+1;
						int[] numberOfPersonForEachGrade=clo.getEachGradeCloPersons();
						
						numberOfPersonForEachGrade[res]=numberOfPersonForEachGrade[res]+1;
						clo.setEachGradeCloPersons(numberOfPersonForEachGrade);
						resultsPersonPercntageClo.set(2,clo);
					}
					
					if(listOfCourseAnswers.get(i).getClo4()!=null) {
						Integer res =listOfCourseAnswers.get(i).getClo4();
						res=res-1;
						cloResult clo=resultsPersonPercntageClo.get(3);
						clo.numberOfPersons=clo.numberOfPersons+1;
						int[] numberOfPersonForEachGrade=clo.getEachGradeCloPersons();
						
						numberOfPersonForEachGrade[res]=numberOfPersonForEachGrade[res]+1;
						clo.setEachGradeCloPersons(numberOfPersonForEachGrade);
						resultsPersonPercntageClo.set(3,clo);
					}
					
					if(listOfCourseAnswers.get(i).getClo5()!=null) {
						Integer res =listOfCourseAnswers.get(i).getClo5();
						res=res-1;
						cloResult clo=resultsPersonPercntageClo.get(4);
						clo.numberOfPersons=clo.numberOfPersons+1;
						int[] numberOfPersonForEachGrade=clo.getEachGradeCloPersons();
						
						numberOfPersonForEachGrade[res]=numberOfPersonForEachGrade[res]+1;
						clo.setEachGradeCloPersons(numberOfPersonForEachGrade);
						resultsPersonPercntageClo.set(4,clo);
					}
					
					if(listOfCourseAnswers.get(i).getClo6()!=null) {
						Integer res =listOfCourseAnswers.get(i).getClo6();
						res=res-1;
						cloResult clo=resultsPersonPercntageClo.get(5);
						clo.numberOfPersons=clo.numberOfPersons+1;
						int[] numberOfPersonForEachGrade=clo.getEachGradeCloPersons();
						
						numberOfPersonForEachGrade[res]=numberOfPersonForEachGrade[res]+1;
						clo.setEachGradeCloPersons(numberOfPersonForEachGrade);
						resultsPersonPercntageClo.set(5,clo);
					}
					
					if(listOfCourseAnswers.get(i).getClo7()!=null) {
						Integer res =listOfCourseAnswers.get(i).getClo7();
						res=res-1;
						cloResult clo=resultsPersonPercntageClo.get(6);
						clo.numberOfPersons=clo.numberOfPersons+1;
						int[] numberOfPersonForEachGrade=clo.getEachGradeCloPersons();
						
						numberOfPersonForEachGrade[res]=numberOfPersonForEachGrade[res]+1;
						clo.setEachGradeCloPersons(numberOfPersonForEachGrade);
						resultsPersonPercntageClo.set(6,clo);
					}
					
					if(listOfCourseAnswers.get(i).getClo8()!=null) {
						Integer res =listOfCourseAnswers.get(i).getClo8();
						res=res-1;
						cloResult clo=resultsPersonPercntageClo.get(7);
						clo.numberOfPersons=clo.numberOfPersons+1;
						int[] numberOfPersonForEachGrade=clo.getEachGradeCloPersons();
						
						numberOfPersonForEachGrade[res]=numberOfPersonForEachGrade[res]+1;
						clo.setEachGradeCloPersons(numberOfPersonForEachGrade);
						resultsPersonPercntageClo.set(7,clo);
					}
					
					if(listOfCourseAnswers.get(i).getClo9()!=null) {
						Integer res =listOfCourseAnswers.get(i).getClo9();
						res=res-1;
						cloResult clo=resultsPersonPercntageClo.get(8);
						clo.numberOfPersons=clo.numberOfPersons+1;
						int[] numberOfPersonForEachGrade=clo.getEachGradeCloPersons();
						
						numberOfPersonForEachGrade[res]=numberOfPersonForEachGrade[res]+1;
						clo.setEachGradeCloPersons(numberOfPersonForEachGrade);
						resultsPersonPercntageClo.set(8,clo);
					}
					
					if(listOfCourseAnswers.get(i).getClo10()!=null) {
						Integer res =listOfCourseAnswers.get(i).getClo10();
						res=res-1;
						cloResult clo=resultsPersonPercntageClo.get(9);
						clo.numberOfPersons=clo.numberOfPersons+1;
						int[] numberOfPersonForEachGrade=clo.getEachGradeCloPersons();
						
						numberOfPersonForEachGrade[res]=numberOfPersonForEachGrade[res]+1;
						clo.setEachGradeCloPersons(numberOfPersonForEachGrade);
						resultsPersonPercntageClo.set(9,clo);
					}
					
					if(listOfCourseAnswers.get(i).getClo11()!=null) {
						Integer res =listOfCourseAnswers.get(i).getClo11();
						res=res-1;
						cloResult clo=resultsPersonPercntageClo.get(10);
						clo.numberOfPersons=clo.numberOfPersons+1;
						int[] numberOfPersonForEachGrade=clo.getEachGradeCloPersons();
						
						numberOfPersonForEachGrade[res]=numberOfPersonForEachGrade[res]+1;
						clo.setEachGradeCloPersons(numberOfPersonForEachGrade);
						resultsPersonPercntageClo.set(10,clo);
					}
					
					if(listOfCourseAnswers.get(i).getClo12()!=null) {
						Integer res =listOfCourseAnswers.get(i).getClo12();
						res=res-1;
						cloResult clo=resultsPersonPercntageClo.get(11);
						clo.numberOfPersons=clo.numberOfPersons+1;
						int[] numberOfPersonForEachGrade=clo.getEachGradeCloPersons();
						
						numberOfPersonForEachGrade[res]=numberOfPersonForEachGrade[res]+1;
						clo.setEachGradeCloPersons(numberOfPersonForEachGrade);
						resultsPersonPercntageClo.set(11,clo);
					}
					
					if(listOfCourseAnswers.get(i).getClo13()!=null) {
						Integer res =listOfCourseAnswers.get(i).getClo13();
						res=res-1;
						cloResult clo=resultsPersonPercntageClo.get(12);
						clo.numberOfPersons=clo.numberOfPersons+1;
						int[] numberOfPersonForEachGrade=clo.getEachGradeCloPersons();
						
						numberOfPersonForEachGrade[res]=numberOfPersonForEachGrade[res]+1;
						clo.setEachGradeCloPersons(numberOfPersonForEachGrade);
						resultsPersonPercntageClo.set(12,clo);
					}
					
					if(listOfCourseAnswers.get(i).getClo14()!=null) {
						Integer res =listOfCourseAnswers.get(i).getClo14();
						res=res-1;
						cloResult clo=resultsPersonPercntageClo.get(13);
						clo.numberOfPersons=clo.numberOfPersons+1;
						int[] numberOfPersonForEachGrade=clo.getEachGradeCloPersons();
						
						numberOfPersonForEachGrade[res]=numberOfPersonForEachGrade[res]+1;
						clo.setEachGradeCloPersons(numberOfPersonForEachGrade);
						resultsPersonPercntageClo.set(13,clo);
					}
					
					if(listOfCourseAnswers.get(i).getClo15()!=null) {
						Integer res =listOfCourseAnswers.get(i).getClo15();
						res=res-1;
						cloResult clo=resultsPersonPercntageClo.get(14);
						clo.numberOfPersons=clo.numberOfPersons+1;
						int[] numberOfPersonForEachGrade=clo.getEachGradeCloPersons();
						
						numberOfPersonForEachGrade[res]=numberOfPersonForEachGrade[res]+1;
						clo.setEachGradeCloPersons(numberOfPersonForEachGrade);
						resultsPersonPercntageClo.set(14,clo);
					}
					
					if(listOfCourseAnswers.get(i).getClo16()!=null) {
						Integer res =listOfCourseAnswers.get(i).getClo16();
						res=res-1;
						cloResult clo=resultsPersonPercntageClo.get(15);
						clo.numberOfPersons=clo.numberOfPersons+1;
						int[] numberOfPersonForEachGrade=clo.getEachGradeCloPersons();
						
						numberOfPersonForEachGrade[res]=numberOfPersonForEachGrade[res]+1;
						clo.setEachGradeCloPersons(numberOfPersonForEachGrade);
						resultsPersonPercntageClo.set(15,clo);
					}
					
					if(listOfCourseAnswers.get(i).getClo17()!=null) {
						Integer res =listOfCourseAnswers.get(i).getClo17();
						res=res-1;
						cloResult clo=resultsPersonPercntageClo.get(16);
						clo.numberOfPersons=clo.numberOfPersons+1;
						int[] numberOfPersonForEachGrade=clo.getEachGradeCloPersons();
						
						numberOfPersonForEachGrade[res]=numberOfPersonForEachGrade[res]+1;
						clo.setEachGradeCloPersons(numberOfPersonForEachGrade);
						resultsPersonPercntageClo.set(16,clo);
					}
					
					if(listOfCourseAnswers.get(i).getClo18()!=null) {
						Integer res =listOfCourseAnswers.get(i).getClo18();
						res=res-1;
						cloResult clo=resultsPersonPercntageClo.get(17);
						clo.numberOfPersons=clo.numberOfPersons+1;
						int[] numberOfPersonForEachGrade=clo.getEachGradeCloPersons();
						
						numberOfPersonForEachGrade[res]=numberOfPersonForEachGrade[res]+1;
						clo.setEachGradeCloPersons(numberOfPersonForEachGrade);
						resultsPersonPercntageClo.set(17,clo);
					}
					
					if(listOfCourseAnswers.get(i).getClo19()!=null) {
						Integer res =listOfCourseAnswers.get(i).getClo19();
						res=res-1;
						cloResult clo=resultsPersonPercntageClo.get(18);
						clo.numberOfPersons=clo.numberOfPersons+1;
						int[] numberOfPersonForEachGrade=clo.getEachGradeCloPersons();
						
						numberOfPersonForEachGrade[res]=numberOfPersonForEachGrade[res]+1;
						clo.setEachGradeCloPersons(numberOfPersonForEachGrade);
						resultsPersonPercntageClo.set(18,clo);
					}
					
					
					if(listOfCourseAnswers.get(i).getClo20()!=null) {
						Integer res =listOfCourseAnswers.get(i).getClo20();
						res=res-1;
						cloResult clo=resultsPersonPercntageClo.get(19);
						clo.numberOfPersons=clo.numberOfPersons+1;
						int[] numberOfPersonForEachGrade=clo.getEachGradeCloPersons();
						
						numberOfPersonForEachGrade[res]=numberOfPersonForEachGrade[res]+1;
						clo.setEachGradeCloPersons(numberOfPersonForEachGrade);
						resultsPersonPercntageClo.set(19,clo);
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




			public List<clo_survey_ans> getListOfCourseAnswers() {
				return listOfCourseAnswers;
			}




			public void setListOfCourseAnswers(List<clo_survey_ans> listOfCourseAnswers) {
				this.listOfCourseAnswers = listOfCourseAnswers;
			}




			public course_clo getSelectedCourse() {
				return selectedCourse;
			}




			public void setSelectedCourse(course_clo selectedCourse) {
				this.selectedCourse = selectedCourse;
			}
			
			
		
			
			
}
