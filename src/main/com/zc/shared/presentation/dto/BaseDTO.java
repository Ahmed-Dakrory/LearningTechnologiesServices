/**
 * 
 */
package main.com.zc.shared.presentation.dto;

/**
 * @author omnya
 *
 */
public class BaseDTO {

	private int id;
	private String name;
	private Integer fileNo;
	private String img;
	
	public BaseDTO() {
		super();
	}
	
	public BaseDTO(int id) {
		super();
		this.id = id;
	}

	public BaseDTO(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public BaseDTO(int id, String name, Integer fileNo) {
		super();
		this.id = id;
		this.name = name;
		this.fileNo = fileNo;
	}
	
	public BaseDTO(int id, String name, Integer fileNo, String img) {
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
	
	
}
