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
import javax.faces.bean.ViewScoped;

import main.com.zc.services.domain.booksSys.model.BookStudent;
import main.com.zc.services.domain.shared.enumurations.BookActionEnum;
import main.com.zc.services.domain.shared.enumurations.BookStatusEnum;
import main.com.zc.services.presentation.booksSys.dto.BookCopiesDTO;
import main.com.zc.services.presentation.booksSys.dto.BookDTO;
import main.com.zc.services.presentation.booksSys.dto.BookInstructorDTO;
import main.com.zc.services.presentation.booksSys.dto.BookStudentDTO;
import main.com.zc.services.presentation.booksSys.facade.IBookCopiesFacade;
import main.com.zc.services.presentation.booksSys.facade.IBookReservationFacade;
import main.com.zc.services.presentation.booksSys.facade.IBooksFacade;
import main.com.zc.services.presentation.users.facade.IGetLoggedInInstructorData;
import main.com.zc.services.presentation.users.facade.IGetLoggedInStudentDataFacade;
import main.com.zc.services.presentation.users.facade.IStudentFacade;
import main.com.zc.shared.JavaScriptMessagesHandler;

/**
 * @author Omnya Alaa
 *
 */
@SessionScoped
@ManagedBean(name="BooksReturnBean")
public class BooksReturnBean {
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
	
	
	private String barCode;
	private List<BookStudentDTO> booksStudent;
	private List<BookInstructorDTO> booksIns;
	private BookStudentDTO selectedStudentBooks;
	private BookInstructorDTO selectedInsBooks;
	
	
	@PostConstruct
	public void init()
	{
		selectedStudentBooks=new BookStudentDTO();
		selectedInsBooks=new BookInstructorDTO();
	}
	
	public void getCopy(){
		try{
			BookCopiesDTO checkCopy=new BookCopiesDTO();
			checkCopy=booksCopiesFacade.getByBarCode(getBarCode());
			if(checkCopy!=null){
				if(checkCopy.getId()!=null)
				{
		booksStudent=new ArrayList<BookStudentDTO>();
		booksIns=new ArrayList<BookInstructorDTO>();
		booksStudent=bookReservationFacade.getByBarCodeStudent(getBarCode());
		booksIns=bookReservationFacade.getByBarCodeIns(getBarCode());
				}
				else {
					 JavaScriptMessagesHandler.RegisterErrorMessage(null, "No such recorded book in Database");
				}
			}
			else {
				 JavaScriptMessagesHandler.RegisterErrorMessage(null, "No such recorded book in Database");
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	public void clear(){
		booksStudent=new ArrayList<BookStudentDTO>();
		booksIns=new ArrayList<BookInstructorDTO>();
		barCode=null;
	}
	public void returnBookStudent(BookStudentDTO student)
			{
				
					try{BookCopiesDTO copy=new BookCopiesDTO();
					copy=booksCopiesFacade.getByBarCode(getBarCode());
					copy.setStatus(BookStatusEnum.FREE);
					copy=booksCopiesFacade.update(copy);
					
					BookStudentDTO relation=new BookStudentDTO();
					relation.setDate(Calendar.getInstance());
					relation.setBarCode(getBarCode());
					relation.setStudent(student.getStudent());
					relation.setAction(BookActionEnum.RETURN);
					relation=bookReservationFacade.add(relation);
					if(copy!=null)
					{BookCopiesDTO copy1=new BookCopiesDTO();
					copy1=booksCopiesFacade.getByBarCode(getBarCode());
					copy1.setStatus(BookStatusEnum.FREE);
						  BookDTO book =copy1.getBook();
						    book.setRemaingCopies(copy1.getBook().getRemaingCopies()+1);;
						    booksFacade.update(book);
						    JavaScriptMessagesHandler.RegisterErrorMessage(null, "Book is free now");
							booksStudent=bookReservationFacade.getByBarCodeStudent(getBarCode());
							booksIns=bookReservationFacade.getByBarCodeIns(getBarCode());
					}
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
			
			}
	public void returnBookIns(BookInstructorDTO ins)
	{

		try{
		BookCopiesDTO copy=new BookCopiesDTO();
		copy=booksCopiesFacade.getByBarCode(getBarCode());
		copy.setStatus(BookStatusEnum.FREE);
		copy=booksCopiesFacade.update(copy);
		
		BookInstructorDTO relation=new BookInstructorDTO();
		relation.setDate(Calendar.getInstance());
		relation.setBarCode(getBarCode());
		relation.setInstructor(ins.getInstructor());
		relation.setAction(BookActionEnum.RETURN);
		relation=bookReservationFacade.add(relation);
		if(copy!=null)
		{BookCopiesDTO copy1=new BookCopiesDTO();
		copy1=booksCopiesFacade.getByBarCode(getBarCode());
		copy1.setStatus(BookStatusEnum.FREE);
			  BookDTO book =copy1.getBook();
			    book.setRemaingCopies(copy1.getBook().getRemaingCopies()+1);;
			    booksFacade.update(book);
			    JavaScriptMessagesHandler.RegisterErrorMessage(null, "Book is free now");
				booksStudent=bookReservationFacade.getByBarCodeStudent(getBarCode());
				booksIns=bookReservationFacade.getByBarCodeIns(getBarCode());
		}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	
	}
	public IBookReservationFacade getBookReservationFacade() {
		return bookReservationFacade;
	}

	public void setBookReservationFacade(
			IBookReservationFacade bookReservationFacade) {
		this.bookReservationFacade = bookReservationFacade;
	}

	public IGetLoggedInStudentDataFacade getStudentDataFacade() {
		return studentDataFacade;
	}

	public void setStudentDataFacade(IGetLoggedInStudentDataFacade studentDataFacade) {
		this.studentDataFacade = studentDataFacade;
	}

	public IGetLoggedInInstructorData getGetInsDataFacade() {
		return getInsDataFacade;
	}

	public void setGetInsDataFacade(IGetLoggedInInstructorData getInsDataFacade) {
		this.getInsDataFacade = getInsDataFacade;
	}

	public IStudentFacade getStudentFacade() {
		return studentFacade;
	}

	public void setStudentFacade(IStudentFacade studentFacade) {
		this.studentFacade = studentFacade;
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

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public List<BookStudentDTO> getBooksStudent() {
		return booksStudent;
	}

	public void setBooksStudent(List<BookStudentDTO> booksStudent) {
		this.booksStudent = booksStudent;
	}

	public List<BookInstructorDTO> getBooksIns() {
		return booksIns;
	}

	public void setBooksIns(List<BookInstructorDTO> booksIns) {
		this.booksIns = booksIns;
	}

	public BookStudentDTO getSelectedStudentBooks() {
		return selectedStudentBooks;
	}

	public void setSelectedStudentBooks(BookStudentDTO selectedStudentBooks) {
		this.selectedStudentBooks = selectedStudentBooks;
	}

	public BookInstructorDTO getSelectedInsBooks() {
		return selectedInsBooks;
	}

	public void setSelectedInsBooks(BookInstructorDTO selectedInsBooks) {
		this.selectedInsBooks = selectedInsBooks;
	}
	
}
