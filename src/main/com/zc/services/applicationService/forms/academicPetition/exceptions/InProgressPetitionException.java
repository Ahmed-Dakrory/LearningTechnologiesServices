/**
 * 
 */
package main.com.zc.services.applicationService.forms.academicPetition.exceptions;

/**
 * @author Omnya Alaa
 *
 */
public class InProgressPetitionException extends Exception {
	  public InProgressPetitionException(String message) { super(message); }
	  public InProgressPetitionException(String message, Throwable cause) { super(message, cause); }
	  public InProgressPetitionException(Throwable cause) { super(cause); }
	private static final long serialVersionUID = 1L;
}
