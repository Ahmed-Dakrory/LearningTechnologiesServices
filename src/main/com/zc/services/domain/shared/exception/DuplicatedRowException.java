/**
 * 
 */
package main.com.zc.services.domain.shared.exception;

import org.hibernate.HibernateException;

/**
 * @author Omnya Alaa
 *
 */
public class DuplicatedRowException extends HibernateException{

	public DuplicatedRowException(String s) {
		super(s);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
