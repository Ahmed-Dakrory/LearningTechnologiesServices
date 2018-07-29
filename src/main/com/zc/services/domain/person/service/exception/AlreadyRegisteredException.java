/**
 * 
 */
package main.com.zc.services.domain.person.service.exception;

import org.hibernate.HibernateException;

/**
 * @author Omnya Alaa
 *
 */
public class AlreadyRegisteredException extends HibernateException{

	  public AlreadyRegisteredException(String message) { super(message); }
	  public AlreadyRegisteredException(String message, Throwable cause) { super(message, cause); }
	  public AlreadyRegisteredException(Throwable cause) { super(cause); }
	private static final long serialVersionUID = 1L;

}
