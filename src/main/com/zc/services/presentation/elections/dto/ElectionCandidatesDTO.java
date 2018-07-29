/**
 * 
 */
package main.com.zc.services.presentation.elections.dto;

/**
 * @author zc
 *
 */
public class ElectionCandidatesDTO {
	private int id;
	private String name;
	private Integer fileNo;
	private String img;
	private boolean selected;
	
	public ElectionCandidatesDTO(int id, String name, Integer fileNo,
			String img, boolean selected) {
		super();
		this.id = id;
		this.name = name;
		this.fileNo = fileNo;
		this.img = img;
		this.selected = selected;
	}
	
	public ElectionCandidatesDTO(int id, String name, Integer fileNo, String img) {
		super();
		this.id = id;
		this.name = name;
		this.fileNo = fileNo;
		this.img = img;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getFileNo() {
		return fileNo;
	}
	public void setFileNo(Integer fileNo) {
		this.fileNo = fileNo;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
}
