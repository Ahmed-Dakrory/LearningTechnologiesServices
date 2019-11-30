/**
 * 
 */
package main.com.zc.service.student;

import main.com.zc.services.domain.person.model.Student;

/**
 * @author Omnya Alaa
 *
 */

public interface IStudentGetDataAppService {
public Student getStudentByPersonMail(String mail);
public Student getStudentByFileNo(Integer id);

}
