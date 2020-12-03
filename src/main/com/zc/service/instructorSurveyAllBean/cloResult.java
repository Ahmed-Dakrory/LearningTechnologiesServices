package main.com.zc.service.instructorSurveyAllBean;

public class cloResult {

	public int cloNumber=0;
	public int numberOfPersons=0;
	public int[] eachGradeCloPersons=new int[5];
	public float[] percentage=new float[5];
	
	public cloResult(int cloNumber) {
		// TODO Auto-generated constructor stub
		this.cloNumber=cloNumber;
	}

	
	


	public int[] getEachGradeCloPersons() {
		return eachGradeCloPersons;
	}





	public void setEachGradeCloPersons(int[] eachGradeCloPersons) {
		this.eachGradeCloPersons = eachGradeCloPersons;
	}





	public int getNumberOfPersons() {
		return numberOfPersons;
	}





	public void setNumberOfPersons(int numberOfPersons) {
		this.numberOfPersons = numberOfPersons;
	}





	public float[] getPercentage() {
		return percentage;
	}





	public void setPercentage(float[] percentage) {
		this.percentage = percentage;
	}





	public int getCloNumber() {
		return cloNumber;
	}

	public void setCloNumber(int cloNumber) {
		this.cloNumber = cloNumber;
	}

	
	
}
