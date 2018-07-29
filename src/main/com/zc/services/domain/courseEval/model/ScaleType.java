/**
 * 
 */
package main.com.zc.services.domain.courseEval.model;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 * @author Omnya Alaa
 *
 */@NamedQueries({

		@NamedQuery(name = "ScaleType.getAll", query = "SELECT s FROM ScaleType s "),
		@NamedQuery(name = "ScaleType.getById", query = "from ScaleType s where s.id = :id"),
		@NamedQuery(name = "ScaleType.getScaleByName", query = "from ScaleType s where s.name = :name")
		}
	 )

@Entity
@Table(name = "scale_type")
public class ScaleType {

		@Id
		@GeneratedValue
		@Column(name = "ID")
		private Integer id;
		
		@Column(name="NAME")
		private String name;

		@OneToMany(mappedBy="scaleType",fetch=FetchType.EAGER,cascade=javax.persistence.CascadeType.ALL)
		private List<ScaleSelections> selections;
		
		
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

		public List<ScaleSelections> getSelections() {
			return selections;
		}

		public void setSelections(List<ScaleSelections> selections) {
			this.selections = selections;
		}
		
}
