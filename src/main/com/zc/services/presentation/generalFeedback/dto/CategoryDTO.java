/**
 * 
 */
package main.com.zc.services.presentation.generalFeedback.dto;

/**
 * @author Omnya Alaa
 *
 */
public class CategoryDTO {

	private int id;
	private String categoryName;
	public CategoryDTO(int id, String categoryName) {
		super();
		this.id = id;
		this.categoryName = categoryName;
	}
	public CategoryDTO(String categoryName) {
		super();
		this.categoryName = categoryName;
	}
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
	
	
	
}
