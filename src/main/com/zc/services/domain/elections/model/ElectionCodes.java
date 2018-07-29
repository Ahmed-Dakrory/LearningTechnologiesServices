/**
 * 
 */
package main.com.zc.services.domain.elections.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 * @author omnya
 *
 */
@NamedQueries({
	@NamedQuery(
	name = "ElectionCodes.getByFileNo",
	query = "from ElectionCodes c where c.fileNo = :id"
	),
	
	 @NamedQuery(name="ElectionCodes.getAll",
    query="SELECT c FROM ElectionCodes c"
    )
	,
	 @NamedQuery(name="ElectionCodes.getById",
   query="from ElectionCodes c where c.id = :id"
   )
	,
	
   @NamedQuery(name="ElectionCodes.getByElectionCode",
   query="from ElectionCodes c where c.electionCode = :id"
   )
})
@Entity
@Table(name = "election_codes")
public class ElectionCodes {
	
	
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "FILE_NO")
	private String fileNo;
	
	@Column(name = "ELECTION_CODE")
	private String electionCode;
	
	
	@Column(name = "CODE_RECIEVING")
	private Boolean  codeRecevingStatus;


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getFileNo() {
		return fileNo;
	}


	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
	}


	public String getElectionCode() {
		return electionCode;
	}


	public void setElectionCode(String electionCode) {
		this.electionCode = electionCode;
	}


	public Boolean getCodeRecevingStatus() {
		return codeRecevingStatus;
	}


	public void setCodeRecevingStatus(Boolean codeRecevingStatus) {
		this.codeRecevingStatus = codeRecevingStatus;
	}
	
	

}
