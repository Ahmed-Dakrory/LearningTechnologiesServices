package main.com.zc.service.courseCloBean;

public class cloResult {

	int cloNumber=0;
	int numberOfPersons=0;
	int[] eachGradeCloPersons=new int[5];
	double[] percentage=new double[5];
	
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





	public double[] getPercentage() {
		return percentage;
	}





	public void setPercentage(double[] percentage) {
		this.percentage = percentage;
	}





	public int getCloNumber() {
		return cloNumber;
	}

	public void setCloNumber(int cloNumber) {
		this.cloNumber = cloNumber;
	}

	
	
}
