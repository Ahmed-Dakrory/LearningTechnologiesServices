/**
 * 
 */
package main.com.zc.services.domain.courseEval.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import main.com.zc.services.domain.shared.enumurations.ScaleSelectionTypeEnum;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 * @author Omnya Alaa
 *
 */
@NamedQueries({

	@NamedQuery(name = "ScaleSelections.getAll", query = "SELECT s FROM ScaleSelections s "),
	@NamedQuery(name = "ScaleSelections.getById", query = "from ScaleSelections s where s.id = :id"),
	@NamedQuery(name = "ScaleSelections.getByScaleTypeId", query = "from ScaleSelections s where s.scaleType.id = :id"),
	@NamedQuery(name = "ScaleSelections.getByScaleTypeIDAndSelType", query = "from ScaleSelections s where s.scaleType.id = :id and s.type = :type"),
	
	}
 )

@Entity
@Table(name = "scale_selection")
public class ScaleSelections {

	
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;

	@Column(name="NAME")
	private String name;
	
	
	@Column(name="TYPE")
	private ScaleSelectionTypeEnum type;
	
	@ManyToOne
	@JoinColumn(name = "SELECT_TYPE_ID")
	private ScaleType scaleType;

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

	public ScaleSelectionTypeEnum getType() {
		return type;
	}

	public void setType(ScaleSelectionTypeEnum type) {
		this.type = type;
	}

	public ScaleType getScaleType() {
		return scaleType;
	}

	public void setScaleType(ScaleType scaleType) {
		this.scaleType = scaleType;
	}

	
	
}
