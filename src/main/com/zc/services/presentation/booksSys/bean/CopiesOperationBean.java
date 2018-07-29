/**
 * 
 */
package main.com.zc.services.presentation.booksSys.bean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import main.com.zc.services.domain.shared.enumurations.BookActionEnum;
import main.com.zc.services.domain.shared.enumurations.BookStatusEnum;
import main.com.zc.services.presentation.booksSys.dto.BookCopiesDTO;
import main.com.zc.services.presentation.booksSys.dto.BookDTO;
import main.com.zc.services.presentation.booksSys.dto.BookInstructorDTO;
import main.com.zc.services.presentation.booksSys.dto.BookStudentDTO;
import main.com.zc.services.presentation.booksSys.facade.IBookCopiesFacade;
import main.com.zc.services.presentation.booksSys.facade.IBookReservationFacade;
import main.com.zc.services.presentation.booksSys.facade.IBooksFacade;
import main.com.zc.services.presentation.users.dto.InstructorDTO;
import main.com.zc.services.presentation.users.dto.StudentDTO;
import main.com.zc.services.presentation.users.facade.IGetLoggedInInstructorData;
import main.com.zc.services.presentation.users.facade.IGetLoggedInStudentDataFacade;
import main.com.zc.services.presentation.users.facade.IStudentFacade;
import main.com.zc.shared.JavaScriptMessagesHandler;
import main.com.zc.shared.presentation.dto.PersonDataDTO;

/**
 * @author omnya
 *
 */
@SessionScoped
@ManagedBean(name="CopiesOperationBean")
public class CopiesOperationBean {

	
	
	@ManagedProperty("#{IBookReservationFacade}")
	private IBookReservationFacade bookReservationFacade;
	
	@ManagedProperty("#{GetLoggedInStudentDataFacadeImpl}")
	private IGetLoggedInStudentDataFacade studentDataFacade;
	
	@ManagedProperty("#{GetLoggedInInstructorDataImpl}")
	private IGetLoggedInInstructorData getInsDataFacade;
	
	
	@ManagedProperty("#{IStudentFacade}")
	private	IStudentFacade studentFacade;
	
	@ManagedProperty("#{IBooksFacade}")
	IBooksFacade booksFacade;
	
	@ManagedProperty("#{IBookCopiesFacade}")
	IBookCopiesFacade booksCopiesFacade;
	
	private Integer selectedAssignee;
	private Integer selectedInsID;
	private Integer selectedTaID;
	private List<InstructorDTO> insLst;
	private List<InstructorDTO> taLst;
	private String studentID;
	private String barCode;
	private InstructorDTO selectedIns;
	private InstructorDTO selectedTa;
	private StudentDTO selectedStudent;
	private List<BookStudentDTO> studentBooks;
	private List<BookInstructorDTO> insBooks;
	private List<BookInstructorDTO> taBooks;
	private BookStudentDTO selectedStudentBooks;
	private BookInstructorDTO selectedInsBooks;
	private BookInstructorDTO selectedTABooks;
	@PostConstruct
	public void init()
	{
		 fillInsLst();
	}
	public void fillInsLst(){
		insLst=new ArrayList<InstructorDTO>();
		taLst=new ArrayList<InstructorDTO>();
		insLst=bookReservationFacade.getAllInstructors();
		taLst=bookReservationFacade.getAllTAs();
	}
	public void getPersonData(){
		if(selectedAssignee==1) // student
		{
			selectedStudent=new StudentDTO();
			studentBooks=new ArrayList<BookStudentDTO>();
			try{
			Integer id=Integer.parseInt(studentID);
			PersonDataDTO person=studentDataFacade.getPersonByFileNo(id);
			StudentDTO student=studentFacade.getById(person.getId());
			if(student!=null){
				selectedStudent=student;
				studentBooks=bookReservationFacade.getByStudentID(selectedStudent.getId());
			   
			}
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
		}
		else if(selectedAssignee==2) // Instructor
		{
			selectedIns=new InstructorDTO();
			insBooks=new ArrayList<BookInstructorDTO>();
			try{
			
			InstructorDTO tempIns =getInsDataFacade.getInsByPersonID(selectedInsID);
			
			if(tempIns!=null){
				selectedIns=tempIns;
				insBooks=bookReservationFacade.getByInsID(selectedIns.getId());
			   
			}
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
		}
		else if(selectedAssignee==3) // TA 
		{
			selectedTa=new InstructorDTO();
			taBooks=new ArrayList<BookInstructorDTO>();
			try{
			
			InstructorDTO tempIns =getInsDataFacade.getInsByPersonID(selectedTaID);
			
			if(tempIns!=null){
				selectedTa=tempIns;
				taBooks=bookReservationFacade.getByInsID(selectedTa.getId());
			   
			}
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}
	public void ReserveBook(){
		try{
		BookCopiesDTO checkCopy=new BookCopiesDTO();
		checkCopy=booksCopiesFacade.getByBarCode(getBarCode());
		if(checkCopy!=null){
			if(checkCopy.getId()!=null)
			{
		if(!checkCopy.getStatus().equals(BookStatusEnum.HELD)){
		if(selectedAssignee==1) // student
		{
				BookStudentDTO relation=new BookStudentDTO();
				relation.setDate(Calendar.getInstance());
				relation.setBarCode(getBarCode());
				relation.setStudent(getSelectedStudent());
				relation.setAction(BookActionEnum.RESERVE);
				relation=bookReservationFacade.add(relation);
			
				if(relation!=null)
				{
					 BookCopiesDTO copy=new BookCopiesDTO();
					    copy= booksCopiesFacade.getByBarCode(getBarCode());
					    BookDTO book =copy.getBook();
					    book.setRemaingCopies(copy.getBook().getRemaingCopies()-1);;
					    booksFacade.update(book);
					    copy.setStatus(BookStatusEnum.HELD);
					    booksCopiesFacade.update(copy);
						JavaScriptMessagesHandler.RegisterErrorMessage(null, "Book is reserved Successfully");
					
				}setBarCode(null);
				studentBooks=bookReservationFacade.getByStudentID(selectedStudent.getId());}
				
		
		
		
		else if(selectedAssignee==2) // ins
		{
				if(checkCopy!=null)
			{
				if(checkCopy.getId()!=null)
				{

		
				
		
			
			BookInstructorDTO relation=new BookInstructorDTO();
			relation.setDate(Calendar.getInstance());
			relation.setBarCode(getBarCode());
			relation.setInstructor(getSelectedIns());
			relation.setAction(BookActionEnum.RESERVE);
			relation=bookReservationFacade.add(relation);
		
			if(relation!=null)
			{
				 BookCopiesDTO copy=new BookCopiesDTO();
				    copy= booksCopiesFacade.getByBarCode(getBarCode());
				    BookDTO book =copy.getBook();
				    book.setRemaingCopies(copy.getBook().getRemaingCopies()-1);;
				    booksFacade.update(book);
				    copy.setStatus(BookStatusEnum.HELD);
				    booksCopiesFacade.update(copy);
					JavaScriptMessagesHandler.RegisterErrorMessage(null, "Book is reserved Successfully");
			
					
			}
		
			setBarCode(null);	
			}
		 insBooks=bookReservationFacade.getByInsID(selectedIns.getId());
		}
				
		}
		else 	if(selectedAssignee==3) // TA
		{
				if(checkCopy!=null)
			{
				if(checkCopy.getId()!=null)
				{
	
				
		
			BookInstructorDTO relation=new BookInstructorDTO();
			relation.setDate(Calendar.getInstance());
			relation.setBarCode(getBarCode());
			relation.setInstructor(getSelectedTa());
			relation.setAction(BookActionEnum.RESERVE);
			relation=bookReservationFacade.add(relation);
		
			if(relation!=null)
			{
				 BookCopiesDTO copy=new BookCopiesDTO();
				    copy= booksCopiesFacade.getByBarCode(getBarCode());
				    BookDTO book =copy.getBook();
				    book.setRemaingCopies(copy.getBook().getRemaingCopies()-1);;
				    booksFacade.update(book);
				    copy.setStatus(BookStatusEnum.HELD);
				    booksCopiesFacade.update(copy);
					JavaScriptMessagesHandler.RegisterErrorMessage(null, "Book is reserved Successfully");
			}
		
			setBarCode(null);	}
		 taBooks=bookReservationFacade.getByInsID(selectedTa.getId());
		}
		}
		
		}
		else JavaScriptMessagesHandler.RegisterErrorMessage(null, "Busy Book");
		}
		else {
			 JavaScriptMessagesHandler.RegisterErrorMessage(null, "No such recorded book in Database");
		}
		}
		else JavaScriptMessagesHandler.RegisterErrorMessage(null, "No such recorded book in Database");
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			JavaScriptMessagesHandler.RegisterErrorMessage(null, "Unexpected Error please contact LT");
		}
		
	
	}
	public IBookReservationFacade getBookReservationFacade() {
		return bookReservationFacade;
	}
	public void setBookReservationFacade(
			IBookReservationFacade bookReservationFacade) {
		this.bookReservationFacade = bookReservationFacade;
	}
	public Integer getSelectedAssignee() {
		return selectedAssignee;
	}
	public void setSelectedAssignee(Integer selectedAssignee) {
		this.selectedAssignee = selectedAssignee;
	}

	public Integer getSelectedInsID() {
		return selectedInsID;
	}

	public void setSelectedInsID(Integer selectedInsID) {
		this.selectedInsID = selectedInsID;
	}

	public Integer getSelectedTaID() {
		return selectedTaID;
	}

	public void setSelectedTaID(Integer selectedTaID) {
		this.selectedTaID = selectedTaID;
	}

	public List<InstructorDTO> getInsLst() {
		return insLst;
	}

	public void setInsLst(List<InstructorDTO> insLst) {
		this.insLst = insLst;
	}

	public List<InstructorDTO> getTaLst() {
		return taLst;
	}

	public void setTaLst(List<InstructorDTO> taLst) {
		this.taLst = taLst;
	}
	public String getStudentID() {
		return studentID;
	}
	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}
	public String getBarCode() {
		return barCode;
	}
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	public InstructorDTO getSelectedIns() {
		return selectedIns;
	}
	public void setSelectedIns(InstructorDTO selectedIns) {
		this.selectedIns = selectedIns;
	}
	public InstructorDTO getSelectedTa() {
		return selectedTa;
	}
	public void setSelectedTa(InstructorDTO selectedTa) {
		this.selectedTa = selectedTa;
	}
	public StudentDTO getSelectedStudent() {
		return selectedStudent;
	}
	public void setSelectedStudent(StudentDTO selectedStudent) {
		this.selectedStudent = selectedStudent;
	}
	public IGetLoggedInStudentDataFacade getStudentDataFacade() {
		return studentDataFacade;
	}
	public void setStudentDataFacade(IGetLoggedInStudentDataFacade studentDataFacade) {
		this.studentDataFacade = studentDataFacade;
	}
	public IStudentFacade getStudentFacade() {
		return studentFacade;
	}
	public void setStudentFacade(IStudentFacade studentFacade) {
		this.studentFacade = studentFacade;
	}
	public List<BookStudentDTO> getStudentBooks() {
		return studentBooks;
	}
	public void setStudentBooks(List<BookStudentDTO> studentBooks) {
		this.studentBooks = studentBooks;
	}
	public BookStudentDTO getSelectedStudentBooks() {
		return selectedStudentBooks;
	}
	public void setSelectedStudentBooks(BookStudentDTO selectedStudentBooks) {
		this.selectedStudentBooks = selectedStudentBooks;
	}
	public IBooksFacade getBooksFacade() {
		return booksFacade;
	}
	public void setBooksFacade(IBooksFacade booksFacade) {
		this.booksFacade = booksFacade;
	}
	public IBookCopiesFacade getBooksCopiesFacade() {
		return booksCopiesFacade;
	}
	public void setBooksCopiesFacade(IBookCopiesFacade booksCopiesFacade) {
		this.booksCopiesFacade = booksCopiesFacade;
	}
	public List<BookInstructorDTO> getInsBooks() {
		return insBooks;
	}
	public void setInsBooks(List<BookInstructorDTO> insBooks) {
		this.insBooks = insBooks;
	}
	public List<BookInstructorDTO> getTaBooks() {
		return taBooks;
	}
	public void setTaBooks(List<BookInstructorDTO> taBooks) {
		this.taBooks = taBooks;
	}
	public IGetLoggedInInstructorData getGetInsDataFacade() {
		return getInsDataFacade;
	}
	public void setGetInsDataFacade(IGetLoggedInInstructorData getInsDataFacade) {
		this.getInsDataFacade = getInsDataFacade;
	}
	public BookInstructorDTO getSelectedInsBooks() {
		return selectedInsBooks;
	}
	public void setSelectedInsBooks(BookInstructorDTO selectedInsBooks) {
		this.selectedInsBooks = selectedInsBooks;
	}
	public BookInstructorDTO getSelectedTABooks() {
		return selectedTABooks;
	}
	public void setSelectedTABooks(BookInstructorDTO selectedTABooks) {
		this.selectedTABooks = selectedTABooks;
	}
	
}
