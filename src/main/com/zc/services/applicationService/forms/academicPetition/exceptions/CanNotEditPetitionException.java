/**
 * 
 */
package main.com.zc.services.applicationService.forms.academicPetition.exceptions;

/**
 * @author Omnya Alaa
 *
 */
public class CanNotEditPetitionException extends Exception {
	  public CanNotEditPetitionException(String message) { super(message); }
	  public CanNotEditPetitionException(String message, Throwable cause) { super(message, cause); }
	  public CanNotEditPetitionException(Throwable cause) { super(cause); }
	private static final long serialVersionUID = 1L;
}
