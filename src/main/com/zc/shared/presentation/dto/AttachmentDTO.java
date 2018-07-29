/**
 * 
 */
package main.com.zc.shared.presentation.dto;

/**
 * @author momen
 *
 */
public class AttachmentDTO {

	private Integer id;
	private String name;
	private byte[] contents;
	
	public AttachmentDTO(String name, byte[] contents) {
		super();
		this.name = name;
		this.contents = contents;
	}

	public AttachmentDTO(int id, String name, byte[] contents) {
		super();
		this.id = id;
		this.name = name;
		this.contents = contents;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public byte[] getContents() {
		return contents;
	}
	public void setFileNo(byte[] contents) {
		this.contents = contents;
	}	
}
