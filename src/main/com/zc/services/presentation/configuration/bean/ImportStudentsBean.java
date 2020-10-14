/**
 * 
 */
package main.com.zc.services.presentation.configuration.bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import main.com.zc.services.presentation.configuration.dto.StudentCourseDTO;
import main.com.zc.services.presentation.configuration.facade.IStudentCourseFacade;
import main.com.zc.services.presentation.users.dto.StudentDTO;
import main.com.zc.shared.JavaScriptMessagesHandler;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 * @author omnya
 *
 */
@SessionScoped
@ManagedBean(name="ImportStudentsBean")
public class ImportStudentsBean {

	private UploadedFile file;
	private String statusMessage;
	private List<StudentDTO> students;
	private StudentDTO selectedStudentsLst;
	private Integer originalSize;
	private Integer newStudentsSize;
	private boolean renderResultText;
//	  private StreamedContent downloadFile;
	@ManagedProperty("#{IStudentCourseFacade}")
	private IStudentCourseFacade facade; 

	List<StudentDTO> newStudents;
	List<StudentDTO> oldStudents;
	
	
	@PostConstruct
	public void init()
	{
		students=new ArrayList<StudentDTO>();
			}

	
	public String parseFile()
	{
		 InputStream inputStream = null;
		 try {
			inputStream=file.getInputstream();
			List<StudentDTO> list=facade.praseStudentFile(inputStream);

			System.out.println("SizeNow : "+list.size());
			//System.out.println("Ahmed Dakrory1: List: "+String.valueOf(list.size()));
			//list.remove(list.get(list.size()-1));
		    students=list;
		    Map<Integer, StudentDTO> map = new LinkedHashMap();
		    for (StudentDTO ays : list) {
		      map.put(ays.getFacultyId(), ays);
		    }
		    list.clear();
		    list.addAll(map.values());

			//System.out.println("Ahmed Dakrory2: List: "+String.valueOf(list.size()));
		    students=list;
			System.out.println("Size : "+students.size());
			// see if students existed in database or not 
			newStudents=facade.getNewStudents(students);
			oldStudents =facade.getOldStudents(students);
			
			System.out.println("Ahmed Dakrory2: List: "+String.valueOf(newStudents.size()));
			newStudentsSize=newStudents.size();
			originalSize=students.size();
			//students=newStudents;
			renderResultText=true;
			// get file original size 
			// get the new students to save them
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return "";
	}
	
	 public void upload(FileUploadEvent event) {  
		    // Do what you want with the file      
		    setFile(event.getFile());
		    //parseFile();
		    setStatusMessage(file.getFileName());

		    try {
			} catch (Exception e) {
			}
		}  
	 public void saveStudents(){
		 boolean add=true;
		 for(int i=0;i<newStudents.size();i++)
		 {

			 add&=facade.addStudent(newStudents.get(i));
		 }
		 
		 for(int i=0;i<oldStudents.size();i++)
		 {

			 add&=facade.addStudent(oldStudents.get(i));
		 }
		 if(add)
		 {
			 JavaScriptMessagesHandler.RegisterNotificationMessage(null, students.size()+" student(s) has(have) been saved Successfully"); 
			 students =new ArrayList<>();
			 newStudents =new ArrayList<>();
			 oldStudents =new ArrayList<>();
		 }
	 }

	 public void downloadFile()
	 {
		 try {
		 FacesContext fc = FacesContext.getCurrentInstance();
		   
		    File file = new File("/templates/students.xlsx");
		    InputStream fis = new FileInputStream(file);
		     byte[] buf = new byte[1024];
		     int offset = 0;
		     int numRead = 0;
		     while ((offset < buf.length) && ((numRead = fis.read(buf, offset, buf.length - offset)) >= 0))
		     {
		       offset += numRead;
		     }
		     fis.close();
		     HttpServletResponse response =
		        (HttpServletResponse) FacesContext.getCurrentInstance()
		            .getExternalContext().getResponse();
		    
		    response.setContentType("application/octet-stream");
		    response.setHeader("Content-Disposition", "attachment;filename=instructions.txt");
		    response.getOutputStream().write(buf);
		    response.getOutputStream().flush();
		    response.getOutputStream().close();
		    FacesContext.getCurrentInstance().responseComplete();}
		 catch(Exception ex)
		 {
			 ex.printStackTrace();
		 }

		  
	 }
	public UploadedFile getFile() {
		return file;
	}

	 
	 
	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public String getAttachmentFileName()
	{
		if(file == null)
			return "None";
		else
			return file.getFileName();
	}

	
	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public List<StudentDTO> getStudents() {
		return students;
	}

	public void setStudents(List<StudentDTO> students) {
		this.students = students;
	}


	public IStudentCourseFacade getFacade() {
		return facade;
	}


	public void setFacade(IStudentCourseFacade facade) {
		this.facade = facade;
	}


	public StudentDTO getSelectedStudentsLst() {
		return selectedStudentsLst;
	}


	public void setSelectedStudentsLst(StudentDTO selectedStudentsLst) {
		this.selectedStudentsLst = selectedStudentsLst;
	}


	public Integer getOriginalSize() {
		return originalSize;
	}


	public void setOriginalSize(Integer originalSize) {
		this.originalSize = originalSize;
	}


	public Integer getNewStudentsSize() {
		return newStudentsSize;
	}


	public void setNewStudentsSize(Integer newStudentsSize) {
		this.newStudentsSize = newStudentsSize;
	}


	public boolean isRenderResultText() {
		return renderResultText;
	}


	public void setRenderResultText(boolean renderResultText) {
		this.renderResultText = renderResultText;
	}


	public List<StudentDTO> getNewStudents() {
		return newStudents;
	}


	public void setNewStudents(List<StudentDTO> newStudents) {
		this.newStudents = newStudents;
	}


	public List<StudentDTO> getOldStudents() {
		return oldStudents;
	}


	public void setOldStudents(List<StudentDTO> oldStudents) {
		this.oldStudents = oldStudents;
	}



	
}
