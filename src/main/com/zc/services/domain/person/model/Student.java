package main.com.zc.services.domain.person.model;


import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import main.com.zc.services.domain.data.model.Data;
import main.com.zc.services.domain.time.model.Time;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;


@NamedQueries({
	@NamedQuery(
	name = "Student.getPersonByID",
	query = "from Student p where p.id = :id"
	),
	@NamedQuery(
	name = "Student.getPersonByFileNo",
	query = "from Student p where p.fileNo = :fileNo"
	),
	 @NamedQuery(name="Student.getAll",
     query="SELECT p FROM Student p"
     )
	,
	 @NamedQuery(name="Student.getByMail",
    query="from Student p where LOWER(p.data.mail) LIKE :mail"
    )
	,
	 @NamedQuery(name="Student.getByLevel",
   query="from Student s where s.level =  :level"
   )
})

@Entity
@Table(name = "student")
@Inheritance(strategy= InheritanceType.JOINED)
public class Student {
	
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer  id;

	@Column(name = "FILE_NO")
	private int fileNo;

	@OneToOne
	 @JoinColumn(name="DATA_ID")
	 private Data data;  
	   
	
	@OneToMany(mappedBy="person")
  private List<Time> timeList;
	
	/*@OneToMany(mappedBy="person")//necessary
	  private List<CourseFeedback> courseFeedbackList;
	*/
/*	@OneToMany(mappedBy="person")//not necessary
	  private List<Feedback> feedbackList;
	*/
	@Column(name="LEVEL")
	private Integer level;

	public List<Time> getTimeList() {
		return timeList;
	}

	public void setTimeList(List<Time> timeList) {
		this.timeList = timeList;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public Student()
	{}

	public Student(int fileNo ,Data data)
	{
		this.fileNo=fileNo;
		this.data=data;
		//this.dataID=dataNo;
	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getFileNo() {
		return fileNo;
	}

	public void setFileNo(int fileNo) {
		this.fileNo = fileNo;
	}

//	public List<CourseFeedback> getCourseFeedbackList() {
//		return courseFeedbackList;
//	}
//
//	public void setCourseFeedbackList(List<CourseFeedback> courseFeedbackList) {
//		this.courseFeedbackList = courseFeedbackList;
//	}
/*
	public List<Feedback> getFeedbackList() {
		return feedbackList;
	}

	public void setFeedbackList(List<Feedback> feedbackList) {
		this.feedbackList = feedbackList;
	}
*/
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	
}
