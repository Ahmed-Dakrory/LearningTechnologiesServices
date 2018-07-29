/**
 * 
 */
package main.com.zc.services.presentation.survey.courseFeedback.dto;

/**
 * @author Omnya Alaa
 *
 */
public class RatingResultsDTO {
	  private int stronglyDisagree=0;
	  private  int disagree=0;
	  private int neutral=0;
	  private int stronglyAgree=0;
	  private int agree=0;
	  private int notAnswred=0;
	  
	public RatingResultsDTO() {
		super();
	}
	
	public RatingResultsDTO(int stronglyDisagree, int disagree, int neutral,
			int stronglyAgree, int agree) {
		super();
		this.stronglyDisagree = stronglyDisagree;
		this.disagree = disagree;
		this.neutral = neutral;
		this.stronglyAgree = stronglyAgree;
		this.agree = agree;
	}

	public RatingResultsDTO(int stronglyDisagree, int disagree, int neutral,
			int stronglyAgree, int agree, int notAnswred) {
		super();
		this.stronglyDisagree = stronglyDisagree;
		this.disagree = disagree;
		this.neutral = neutral;
		this.stronglyAgree = stronglyAgree;
		this.agree = agree;
		this.notAnswred = notAnswred;
	}

	public int getStronglyDisagree() {
		return stronglyDisagree;
	}
	public void setStronglyDisagree(int stronglyDisagree) {
		this.stronglyDisagree = stronglyDisagree;
	}
	public int getDisagree() {
		return disagree;
	}
	public void setDisagree(int disagree) {
		this.disagree = disagree;
	}
	public int getNeutral() {
		return neutral;
	}
	public void setNeutral(int neutral) {
		this.neutral = neutral;
	}
	public int getStronglyAgree() {
		return stronglyAgree;
	}
	public void setStronglyAgree(int stronglyAgree) {
		this.stronglyAgree = stronglyAgree;
	}
	public int getAgree() {
		return agree;
	}
	public void setAgree(int agree) {
		this.agree = agree;
	}

	public int getNotAnswred() {
		return notAnswred;
	}

	public void setNotAnswred(int notAnswred) {
		this.notAnswred = notAnswred;
	}
	  
}
