/**
 * 
 */
package main.com.zc.services.domain.feedback.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 * @author Omnya Alaa
 *
 */
@NamedQueries({
	

	 @NamedQuery(name="FeedbackCategory.getAll",
     query="SELECT f FROM FeedbackCategory f"
     )
	,
	 @NamedQuery(name="FeedbackCategory.getById",
    query="from FeedbackCategory f where f.id = :id"
    )
})
@Entity
@Table(name = "feedback_category")
public class FeedbackCategory {

	
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private int id;
	
	
	@Column(name="CATEGORY")
	private String categoryName;
	
	
	@Column(name = "CATEGORY_HEAD_ID")
	private Integer categoryHeadID;
/*	
	@Column(name="CATEGORY_HEAD_EMAIL")	
	private String categoryHeadEmail	;

	@Column(name="CATEGORY_HEAD_NAME")	
	private String categoryHeadName	;*/



	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getCategoryName() {
		return categoryName;
	}


	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}


	public Integer getCategoryHeadID() {
		return categoryHeadID;
	}


	public void setCategoryHeadID(Integer categoryHeadID) {
		this.categoryHeadID = categoryHeadID;
	}



	
}
